//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package android.serialport;

import android.util.Log;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class SerialPort {
    private static final String TAG = "SerialPortNative";
    private int fdx = -1;
    private String str;
    private int writelen;

    static {
        System.loadLibrary("serial_port");
    }

    public SerialPort() {
    }

    private native void closeport(int port);

    private boolean isUTF8(byte[] var1) {
        boolean var2 = false;
        Log.d("SerialPortNative", "begian to set codeset");
        int var3 = 0;

        boolean var4;
        while(true) {
            if (var3 >= var1.length) {
                var4 = true;
                break;
            }

            if (var1[var3] < 0) {
                if (var1[var3] >>> 5 == 134217726) {
                    var4 = var2;
                    if (var3 + 1 >= var1.length) {
                        break;
                    }

                    var4 = var2;
                    if (var1[var3 + 1] >>> 6 != 67108862) {
                        break;
                    }

                    var3 += 2;
                } else {
                    var4 = var2;
                    if (var1[var3] >>> 4 != 268435454) {
                        break;
                    }

                    var4 = var2;
                    if (var3 + 2 >= var1.length) {
                        break;
                    }

                    var4 = var2;
                    if (var1[var3 + 1] >>> 6 != 67108862) {
                        break;
                    }

                    var4 = var2;
                    if (var1[var3 + 2] >>> 6 != 67108862) {
                        break;
                    }

                    var3 += 3;
                }
            } else {
                ++var3;
            }
        }

        return var4;
    }

    private native int openport(String var1, int var2, int var3, int var4, int var5);

    private native int openport_easy(String var1, int var2);

    private native byte[] readport(int var1, int var2, int var3);

    private native int writeport(int var1, byte[] var2);

    public static native int writestring(int var0, String var1, int var2);

    public void CloseSerial(int port) {
        this.closeport(port);
    }

    public void OpenSerial(String var1, int var2) throws SecurityException, IOException {
        this.fdx = this.openport_easy(var1, var2);
        if (this.fdx < 0) {
            Log.e("SerialPortNative", "native open returns null");
            throw new IOException();
        }
    }

    public void OpenSerial(String var1, int var2, int var3, int var4, int var5) throws SecurityException, IOException {
        System.out.println("open");
        this.fdx = this.openport(var1, var2, var3, var4, var5);
        if (this.fdx < 0) {
            Log.e("SerialPortNative", "native open returns null");
            throw new IOException();
        }
    }

    public byte[] ReadSerial(int var1, int var2) throws UnsupportedEncodingException {
        byte[] var3 = this.readport(var1, var2, 50);
        byte[] var4 = var3;
        if (var3 == null) {
            var4 = null;
        }

        return var4;
    }

    public String ReadSerialString(int var1, int var2) throws UnsupportedEncodingException {
        byte[] var3 = this.readport(var1, var2, 50);
        String var4;
        if (var3 == null) {
            var4 = null;
        } else {
            if (this.isUTF8(var3)) {
                this.str = new String(var3, "utf8");
                Log.d("SerialPortNative", "is a utf8 string");
            } else {
                this.str = new String(var3, "gbk");
                Log.d("SerialPortNative", "is a gbk string");
            }

            var4 = this.str;
        }

        return var4;
    }

    public int WriteSerialByte(int var1, byte[] var2) {
        this.writelen = this.writeport(var1, var2);
        if (this.writelen >= 0) {
            Log.d("SerialPortNative", "write success");
        }

        return this.writelen;
    }

    public int WriteSerialString(int var1, String var2, int var3) {
        this.writelen = writestring(var1, var2, var3);
        return this.writelen;
    }

    public int getFd() {
        return this.fdx;
    }
}
