/*
 * Copyright 2009 Cedric Priscal
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package android.serialport;

import net.luculent.dnalib.Logger;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class SerialPort {

    public static final String TAG = "SerialPortNative";

    private int fdx = -1;
    private int writelen;
    private String str;

    public SerialPort() {
    }

    public void OpenSerial(String dev, int brd) throws SecurityException, IOException {
        // int result = 0;
        fdx = openport(dev, brd, 8, 1, 0);
        if (fdx < 0) {
            Logger.log("native open returns null");
            throw new IOException();
        }
    }

    public void OpenSerial(String device, int baudrate, int databit, int stopbit, int crc) throws SecurityException,
            IOException {
        System.out.println("open");
        fdx = openport(device, baudrate, databit, stopbit, crc);
        if (fdx < 0) {
            Logger.log("native open returns null");
            throw new IOException();
        }
    }

    public int getFd() {
        return fdx;
    }

    public int WriteSerialByte(int fd, byte[] str) {
        clearPortBuf(fd);
        writelen = writeport(fd, str);
        if (writelen >= 0) {
            Logger.log("write success");
        } else {
            Logger.log("write failed");
        }
        return writelen;
    }


    private int timeout = 300;

    public byte[] ReadSerial(int fd, int len) throws UnsupportedEncodingException {
        byte[] tmp = readport(fd, len, timeout);
        if (tmp != null) {
            Logger.log("read---success");
        }
        return tmp;
    }

    public byte[] ReadSerial(int fd, int len, int delay) throws UnsupportedEncodingException {
        byte[] tmp = readport(fd, len, delay);
        if (tmp != null) {
            Logger.log("read---success");
        }
        return tmp;
    }

    public byte[] ReadSerial(int fd, int len, boolean isClear) throws UnsupportedEncodingException {
        byte[] tmp = readport(fd, len, timeout);
        if (tmp != null) {
            Logger.log("read---success");
            if (isClear)
                clearPortBuf(fd);
        } else {
            Logger.log("read---null");
        }
        return tmp;
    }

    public String ReadSerialString(int fd, int len) throws UnsupportedEncodingException {
        byte[] tmp;
        tmp = readport(fd, len, 50);
        if (tmp == null) {
            return null;
        }
        if (isUTF8(tmp)) {
            str = new String(tmp, "utf8");
            Logger.log("is a utf8 string");
        } else {
            str = new String(tmp, "gbk");
            Logger.log("is a gbk string");
        }
        return str;
    }

    public void CloseSerial(int fd) {
        closeport(fd);
    }

    private boolean isUTF8(byte[] sx) {
        Logger.log("begian to set codeset");
        for (int i = 0; i < sx.length; ) {
            if (sx[i] < 0) {
                if ((sx[i] >>> 5) == 0x7FFFFFE) {
                    if (((i + 1) < sx.length)
                            && ((sx[i + 1] >>> 6) == 0x3FFFFFE)) {
                        i = i + 2;
                    } else {
                        return false;
                    }
                } else if ((sx[i] >>> 4) == 0xFFFFFFE) {
                    if (((i + 2) < sx.length)
                            && ((sx[i + 1] >>> 6) == 0x3FFFFFE)
                            && ((sx[i + 2] >>> 6) == 0x3FFFFFE)) {
                        i = i + 3;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            } else {
                i++;
            }
        }
        return true;
    }

    public void clearPortBuf(int fd) {
        Logger.log("clearPortBuf---");
        clearportbuf(fd);
    }

    // JNI

    private native int openport(String port, int brd, int bit, int stop, int crc);

    private native void closeport(int fd);

    private native byte[] readport(int fd, int count, int delay);

    private native int writeport(int fd, byte[] buf);

    public native void clearportbuf(int fd);


    static {
        System.loadLibrary("serial_port");
    }

}
