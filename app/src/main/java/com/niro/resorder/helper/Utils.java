package com.niro.resorder.helper;

import android.content.Context;
import android.util.DisplayMetrics;

public class Utils {
    //Email Validation pattern
    public static final String regEx = "\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}\\b";

    //Fragments Tags
    public static final String LoginFragment= "LoginFragment";
    public static final String SignupFragment = "SignupFragment";
    public static final String CategoryFragment = "CategoryFragment";
    public static final String ItemSelectionFragment = "ItemSelectionFragment";
    public static final String AddItemFragment= "AddItemFragment";


    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        return (int) (dpWidth /120);
    }



    //public static final String ForgotPassword_Fragment = "ForgotPassword_Fragment";
}
