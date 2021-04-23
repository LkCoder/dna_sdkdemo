//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package net.luculent.dnalib.serial;

import android.serialport.SerialPort;

public class SerialHelper {
    static final String actual_path = "/sys/class/misc/mtgpio/pin";
    static final int baudrate = 115200;
    static final int crc = 0;
    static final int databit = 8;
    private static SerialHelper instance;
    static final String power_off = "-wdout93 0";
    static final String power_on = "-wdout93 1";
    static final String serial = "/dev/ttyMT3";
    static final int stopbit = 1;
    private final CmdManager cmdManager;
    private DeviceControl deviceControl;
    private final SerialPort serialPort;

    private SerialHelper() {
        serialPort = new SerialPort();
        try {
            deviceControl = new DeviceControl(actual_path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.cmdManager = new CmdManager(this.serialPort);
    }

    private void closeSerial() {
        this.serialPort.CloseSerial(this.serialPort.getFd());
    }

    public static SerialHelper getInstance() {
        if (instance == null) {
            synchronized (SerialHelper.class) {
                if (instance == null) {
                    instance = new SerialHelper();
                }
            }
        }
        return instance;
    }

    private int openSerial() {
        if (this.serialPort.getFd() < 0) {
            try {
                this.serialPort.OpenSerial(serial, baudrate, databit, stopbit, crc);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return this.serialPort.getFd();
    }

    private boolean powerOff() {
        try {
            this.deviceControl.PowerOffDevice(power_off);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean powerOn() {
        try {
            this.deviceControl.PowerOnDevice(power_on);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean close() {
        this.cmdManager.stop();
        return this.powerOff();
    }

    public CmdManager getCmdManager() {
        return this.cmdManager;
    }

    public boolean open() {
        if (powerOn() && openSerial() > 0) {
            cmdManager.start();
            return true;
        }
        return false;
    }

    public void setReadThreadListener(SerialHelper.OnDataReceivedListener listener) {
        cmdManager.setReadThreadListener(listener);
    }

    public synchronized void sendCmd(ICmd.CmdTyp cmdTyp) {
        cmdManager.sendCmd(cmdTyp);
    }

    public interface OnDataReceivedListener {
        void onDataReceived(String result);
    }
}
