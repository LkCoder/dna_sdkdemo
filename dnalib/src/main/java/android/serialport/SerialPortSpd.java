//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package android.serialport;

import android.util.Log;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class SerialPortSpd {
    public static final String TAG = "SerialPortNative";
    private int fdx = -1;
    private int writelen;
    private int timeout = 50;

    public void OpenSerial(String dev, int brd) throws SecurityException, IOException {
        this.fdx = this.openport(dev, brd, 8, 1, 0);
        if (this.fdx < 0) {
            Log.e("SerialPortNative", "native open returns null");
            throw new IOException();
        }
    }

    public void OpenSerial(String device, int baudrate, int databit, int stopbit, int crc) throws SecurityException,
            IOException {
        System.out.println("open");
        this.fdx = this.openport(device, baudrate, databit, stopbit, crc);
        if (this.fdx < 0) {
            Log.e("SerialPortNative", "native open returns null");
            throw new IOException();
        }
    }

    public int getFd() {
        return this.fdx;
    }

    public int WriteSerialByte(int fd, byte[] str) {
        this.clearPortBuf(fd);
        this.writelen = this.writeport(fd, str);
        if (this.writelen >= 0) {
            Log.d("SerialPortNative", "write success");
        }

        return this.writelen;
    }

    public byte[] ReadSerial(int fd, int len) throws UnsupportedEncodingException {
        byte[] tmp = this.readport(fd, len, this.timeout);

        for (int count = 0; tmp == null && count < 10; ++count) {
            tmp = this.readport(fd, len, this.timeout);
        }

        if (tmp != null) {
            Log.d(TAG, "read success---");
        } else {
            Log.e(TAG, "read---null");
        }
        return tmp;
    }

    public void CloseSerial(int fd) {
        this.closeport(fd);
    }

    public void clearPortBuf(int fd) {
        Log.d(TAG, "clearPortBuf---");
        this.clearportbuf(fd);
    }

    private native int setparam(int var1, int var2, int var3, int var4, int var5);

    private native byte[] write_then_read(int var1, byte[] var2, int var3, int var4, int var5, int var6, int var7,
                                          int var8);

    private native int openport(String var1, int var2, int var3, int var4, int var5);

    private native void closeport(int var1);

    private native byte[] readport(int var1, int var2, int var3);

    private native int writeport(int var1, byte[] var2);

    public native void clearportbuf(int var1);

    static {
        System.loadLibrary("serial_port_spd");
    }
}
