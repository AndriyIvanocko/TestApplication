package com.example.testapplication.common.app_config;

import com.example.testapplication.BuildConfig;

public class AppConfig {
    public static boolean isDebug() {
        return BuildConfig.DEBUG;
    }

    public static boolean isRelease() {
        return !BuildConfig.DEBUG;
    }
}
