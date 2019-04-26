package com.niro.resorder;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.niro.resorder.helper.Utils;

public class MainActivity extends AppCompatActivity {

    private static android.support.v4.app.FragmentManager fragmentManager;
    private static final String TAG = MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();

        // If savedinstnacestate is null then replace login fragment
        if (savedInstanceState == null) {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.frameContainer, new LoginFragment(),
                            Utils.LoginFragment).commit();
        }

        // On close icon click finish activity
        findViewById(R.id.close_activity).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        finish();

                    }
                });



        //test/
        //noodles
    }

    @Override
    public void onBackPressed() {
        // Find the tag of signup fragment
        Fragment SignUp_Fragment = fragmentManager
                .findFragmentByTag(Utils.SignupFragment);

        // Check if null or not
        // If not null then replace login fragment else do backpressed
        // task

        if (SignUp_Fragment != null)
            replaceLoginFragment();
        else
            super.onBackPressed();

    }

    // Replace Login Fragment with animation
    protected void replaceLoginFragment() {
        fragmentManager
                .beginTransaction()
                //.setCustomAnimations(R.anim.left_enter, R.anim.right_out)
                .replace(R.id.frameContainer, new LoginFragment(),
                        Utils.LoginFragment).commit();
    }

    protected void replaceSignUpFragment() {
        fragmentManager
                .beginTransaction()
                //.setCustomAnimations(R.anim.left_enter, R.anim.right_out)
                .replace(R.id.frameContainer, new SignUpFragment(),
                        Utils.SignupFragment).commit();
    }

}
