//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package net.luculent.dnalib.serial;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.serialport.SerialPort;

import net.luculent.dnalib.DataConvertUtils;
import net.luculent.dnalib.Logger;

import java.util.concurrent.atomic.AtomicInteger;

public class SerialReadThread extends Thread {

    private final AtomicInteger cmdCount = new AtomicInteger(0);
    private static final int TIME_OUT = 300;//默认每次的采样间隔为300ms
    private static final int TIME_COUNT = 60 * 1000 / TIME_OUT;//如果连续超过一分钟都没有数据，认为没有发送命令

    private final Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            String result = DataConvertUtils.bytesToHexString((byte[]) msg.obj);
            if (DataConvertUtils.checkValid(result) && receivedListener != null) {
                receivedListener.onDataReceived(result);
            }
        }
    };
    private final SerialPort mSerialPort;
    private SerialHelper.OnDataReceivedListener receivedListener;

    public SerialReadThread(SerialPort serialPort) {
        this.mSerialPort = serialPort;
    }

    public void setReceivedListener(SerialHelper.OnDataReceivedListener receivedListener) {
        this.receivedListener = receivedListener;
    }

    public void run() {
        super.run();
        while (!this.isInterrupted() && this.mSerialPort.getFd() > 0) {
            int count = cmdCount.get();
            if (count <= 0) {
                continue;
            }
            try {
                byte[] data = null;
                int timeCount = 0;
                while (true) {
                    timeCount++;
                    data = mSerialPort.ReadSerial(mSerialPort.getFd(), 8206, TIME_OUT);
                    if (data != null) {
                        break;
                    }
                    if (timeCount >= TIME_COUNT) {
                        break;
                    }
                }
                Message message = Message.obtain();
                message.obj = data;
                this.handler.sendMessage(message);
                if (timeCount >= TIME_COUNT) {//数据读取超时了，认为没有命令
                    Logger.log("read time out, wait cmd");
                    cmdCount.set(0);
                } else {
                    cmdCount.decrementAndGet();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void unlock() {
        cmdCount.incrementAndGet();
    }
}
