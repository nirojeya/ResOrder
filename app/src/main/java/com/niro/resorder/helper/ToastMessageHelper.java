package com.niro.resorder.helper;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.niro.resorder.R;


public class ToastMessageHelper {
    private static Toast warningToast;
    public static void optimizeToast(Context context, String message){
        if (warningToast != null){
            warningToast.cancel();
        }
        warningToast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        warningToast.show();
    }

    @SuppressLint("NewApi")
    public static void customErrToast(Activity activity, String message){
        LayoutInflater inflater = activity.getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout,
                (ViewGroup) activity.findViewById(R.id.toast_layout_root));

        layout.setBackground(ContextCompat.getDrawable(activity, R.drawable.err_toast_design));

        ImageView image = (ImageView) layout.findViewById(R.id.image);
        image.setImageResource(R.drawable.err_toast);
        TextView text = (TextView) layout.findViewById(R.id.text);
        text.setText(message);

        if (warningToast != null){
            warningToast.cancel();
        }

        warningToast = new Toast(activity.getApplicationContext());
        warningToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        warningToast.setDuration(Toast.LENGTH_LONG);
        warningToast.setView(layout);
        warningToast.show();
    }

    // @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void customSuccToast(Activity activity, String message){
        LayoutInflater inflater = activity.getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout,
                (ViewGroup) activity.findViewById(R.id.toast_layout_root));

        layout.setBackground(ContextCompat.getDrawable(activity, R.drawable.succ_toast_design));

        ImageView image = (ImageView) layout.findViewById(R.id.image);
        image.setImageResource(R.drawable.succ_toast_img);
        TextView text = (TextView) layout.findViewById(R.id.text);
        text.setText(message);

        if (warningToast != null){
            warningToast.cancel();
        }

        warningToast = new Toast(activity.getApplicationContext());
        warningToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        warningToast.setDuration(Toast.LENGTH_SHORT);
        warningToast.setView(layout);
        warningToast.show();
    }


}

