package com.niro.resorder;

import android.app.Application;
import android.content.Context;

public class ResOrderApp extends Application {
    private static Context applicationContext;

    private static String fullName = "";
    private static String userName = "";
    private static String password = "";
    private static String userDesignation = "";
    private static String userAddress = "address";
    private static String mobileNo = "";


    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = this;
    }

    public static Context getContext(){
        return applicationContext;
    }

    public static String getUserName() {
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

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        ResOrderApp.password = password;
    }

    public static String getUserAddress() {
        return userAddress;
    }

    public static void setUserAddress(String userAddress) {
        ResOrderApp.userAddress = userAddress;
    }

    public static String getMobileNo() {
        return mobileNo;
    }

    public static void setMobileNo(String mobileNo) {
        ResOrderApp.mobileNo = mobileNo;
    }

    public static String getFullName() {
        return fullName;
    }

    public static void setFullName(String fullName) {
        ResOrderApp.fullName = fullName;
    }
}
