package com.niro.resorder;

import android.app.Application;
import android.content.Context;

public class ResOrderApp extends Application {
    private static Context applicationContext;
    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = this;
    }

    public static Context getContext(){
        return applicationContext;
    }
}
