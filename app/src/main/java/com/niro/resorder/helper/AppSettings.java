package com.niro.resorder.helper;

import android.content.Context;
import android.content.SharedPreferences;

public class AppSettings {

    public static int getUserSession(Context context){
        int DEFAULT = 0;

        SharedPreferences sharedPreferences = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);

        return sharedPreferences.getInt("userSessionId",DEFAULT);
        //printerData [1] = sharedPreferences.getInt("productId",DEFAULT);



    }
    public static int getUniqueId(Context context){
        int DEFAULT = 10;

        SharedPreferences sharedPreferences = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);

        return sharedPreferences.getInt("inititalId",DEFAULT);

    }
    public static void setUniqueId(Context context, int state){

        SharedPreferences sharedPrefence = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefence.edit();

        editor.putInt("inititalId",state);
        //editor.putInt("vendorId",vendId);

        editor.apply();

    }

    public static void setUserSession(Context context, int id){

        SharedPreferences sharedPrefence = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefence.edit();

        editor.putInt("userSessionId",id);
        //editor.putInt("vendorId",vendId);

        editor.apply();

    }

}
