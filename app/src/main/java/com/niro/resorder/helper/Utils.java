package com.niro.resorder.helper;

import android.content.Context;
import android.util.DisplayMetrics;
import android.widget.EditText;
import android.widget.TextView;

public class Utils {
    //Email Validation pattern
    public static final String regEx = "\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}\\b";

    //Fragments Tags
    public static final String LoginFragment= "LoginFragment";
    public static final String SignupFragment = "SignupFragment";
    public static final String CategoryFragment = "CategoryFragment";
    public static final String ItemSelectionFragment = "ItemSelectionFragment";
    public static final String AddItemFragment= "AddItemFragment";
    public static final String ViewOrderFragment= "ViewOrderFragment";



    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        return (int) (dpWidth /120);
    }

    public static boolean checkNotNullEditText(EditText editText){
        return editText.getText().toString().trim().length() > 0;
    }

    public static boolean checkNotNullTextView(TextView textView){
        return !textView.getText().toString().equals("0");
    }

    public static String getInput(EditText editText){
        return editText.getText().toString().trim();
    }



    //public static final String ForgotPassword_Fragment = "ForgotPassword_Fragment";
}
