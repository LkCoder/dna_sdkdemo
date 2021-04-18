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

public class SerialReadThread extends Thread {

    Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            String result = DataConvertUtils.bytesToHexString((byte[]) msg.obj);
            if (DataConvertUtils.checkValid(result) && receivedListener != null) {
                receivedListener.onDataReceived(result);
            }
        }
    };
    private SerialPort mSerialPort;
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
            try {
                byte[] data = this.mSerialPort.ReadSerial(this.mSerialPort.getFd(), 8206);
                Message message = Message.obtain();
                message.obj = data;
                this.handler.sendMessage(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
