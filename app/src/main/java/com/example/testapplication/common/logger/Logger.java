package com.example.testapplication.common.logger;

import android.util.Log;

import com.example.testapplication.common.app_config.AppConfig;

public class Logger {
    private final static String TAG = "TAG";
    private static StringBuilder str = new StringBuilder();

    public static void i(String tag, Object message) {
        if (AppConfig.isDebug()) {
            str.setLength(0);
            str.append(message);
            Log.i(tag, str.toString());
        }
    }

    public static void i(Object message) {
        if (AppConfig.isDebug()) i(TAG, message);
    }
}
