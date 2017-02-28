package com.licrafter.cnode.utils;

import android.util.Log;

/**
 * author: shell
 * date 2017/2/24 下午3:35
 **/
public class LogUtils {

    public static final boolean DEBUG = true;

    public static void debug(String tag, String message) {
        if (DEBUG) {
            Log.d(tag, message);
        }
    }

    public static void error(String tag, String message) {
        if (DEBUG) {
            Log.e(tag, message);
        }
    }

    public static void info(String tag, String message) {
        if (DEBUG) {
            Log.i(tag, message);
        }
    }
}
