package com.niro.resorder;

import android.app.Application;
import android.content.Context;

public class ResOrderApp extends Application {
    private static Context applicationContext;

    private String userName = "";
    private String userDesignation = "";

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

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserDesignation() {
        return userDesignation;
    }

    public void setUserDesignation(String userDesignation) {
        this.userDesignation = userDesignation;
    }
}
