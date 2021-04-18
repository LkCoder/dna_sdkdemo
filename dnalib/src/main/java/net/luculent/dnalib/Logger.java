//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package net.luculent.dnalib;

import android.util.Log;

public class Logger {
    private static final String TAG = "SerialLogger";

    public Logger() {
    }

    public static void log(String msg) {
        Log.i(TAG, "log: " + msg);
    }
}
