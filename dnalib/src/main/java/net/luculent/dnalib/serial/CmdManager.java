package net.luculent.dnalib.serial;

import android.serialport.SerialPort;

import net.luculent.dnalib.DataConvertUtils;
import net.luculent.dnalib.Logger;

public class CmdManager {
    private ICmd.CmdTyp cmdTyp;
    private SerialPort mSerialPort;
    private SerialReadThread readThread;
    private SerialHelper.OnDataReceivedListener listener;

    public CmdManager(SerialPort var1) {
        this.mSerialPort = var1;
    }

    public ICmd.CmdTyp getCmdTyp() {
        return this.cmdTyp;
    }

    public void modifySeq(float seq) {
        this.cmdTyp = ICmd.CmdTyp.MODIFY_SEQ;
        String cmd = String.format("%04x", (int) (100.0F * seq));
        cmd = "0119" + cmd;
        this.sendCmd(cmd + DataConvertUtils.getLrcString(cmd));
    }

    public void mofifySen(float sen) {
        this.cmdTyp = ICmd.CmdTyp.MODIFY_SENSE;
        String cmd = Integer.toHexString(Float.floatToIntBits(sen));
        cmd = "0149" + cmd;
        this.sendCmd(cmd + DataConvertUtils.getLrcString(cmd));
    }

    public void sendCmd(String cmd) {
        if (cmd != null) {
            Logger.log("send cmd = " + cmd);
            try {
                this.mSerialPort.WriteSerialByte(this.mSerialPort.getFd(), DataConvertUtils.hexString2Bytes(cmd));
                if (readThread != null) {
                    readThread.unlock();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void sendCmd(ICmd.CmdTyp cmd) {
        this.cmdTyp = cmd;
        this.sendCmd(cmd.cmd);
    }

    public synchronized void start() {
        if (this.readThread == null) {
            this.readThread = new SerialReadThread(this.mSerialPort);
            readThread.setReceivedListener(listener);
            readThread.start();
        }
    }

    public void setReadThreadListener(SerialHelper.OnDataReceivedListener listener) {
        this.listener = listener;
        if (readThread != null) {
            readThread.setReceivedListener(listener);
        }
    }

    public synchronized void stop() {
        if (this.readThread != null) {
            this.readThread.interrupt();
            this.readThread = null;
        }
    }
}
