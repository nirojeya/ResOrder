package com.niro.resorder;

import android.app.Application;
import android.content.Context;

public class ResOrderApp extends Application {
    private static Context applicationContext;

    private static String userName = "";
    private static String userDesignation = "";

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = this;
    }

    public static Context getContext(){
        return applicationContext;
    }

    public String getUserName() {
        return userName;
    }

    public static void setUserName(String un) {
        userName = un;
    }

    public static String getUserDesignation() {
        return userDesignation;
    }

    public static void setUserDesignation(String ud) {
        userDesignation = ud;
    }
}
