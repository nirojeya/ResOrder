package com.niro.resorder;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.niro.resorder.helper.ToastMessageHelper;
import com.niro.resorder.helper.Utils;
import com.niro.resorder.service.VolleyPostService;

import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link SignUpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignUpFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private EditText fullName, userName, mobileNumber,
            password, confirmPassword;

    private Button signUpButton;
    private TextView loginHere;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public SignUpFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignUpFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SignUpFragment newInstance(String param1, String param2) {
        SignUpFragment fragment = new SignUpFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        initializeView(view);
        return view;
    }


    private void initializeView(View view){

        fullName = view.findViewById(R.id.signFullName);
        userName = view.findViewById(R.id.signEmailId);
        mobileNumber = view.findViewById(R.id.signPhone);
        password = view.findViewById(R.id.signPassword);
        confirmPassword = view.findViewById(R.id.signConPassword);

        signUpButton = view.findViewById(R.id.signUpBtn);
        loginHere = view.findViewById(R.id.already_user);

        signUpButton.setOnClickListener(this);
        loginHere.setOnClickListener(this);





    }

    private void processSignUp(){
        if(Utils.checkNotNullEditText(fullName) && Utils.checkNotNullEditText(userName) &&
                Utils.checkNotNullEditText(mobileNumber) && Utils.checkNotNullEditText(password) &&
                Utils.checkNotNullEditText(confirmPassword)){

            if(Utils.getInput(password).equalsIgnoreCase(Utils.getInput(confirmPassword))) {

                ResOrderApp.setFullName(Utils.getInput(fullName));
                ResOrderApp.setUserName(Utils.getInput(userName));
                ResOrderApp.setPassword(Utils.getInput(password));
                ResOrderApp.setMobileNo(Utils.getInput(mobileNumber));

                signUpButton.setEnabled(false);

                VolleyPostService.postUser(Objects.requireNonNull(getActivity()), "http://prod.kalesystems.com:3000/api/auth/user", new VolleyPostService.UserSignUpDelegate() {
                    @Override
                    public void processRegisterFinished(String type) {
                        clearPojo();
                        signUpButton.setEnabled(true);

                        if(!type.equals("error")){
                            ToastMessageHelper.customSuccToast(Objects.requireNonNull(getActivity()),"Account create successfully!");
                            //Toast.makeText(getActivity(), "Account create successfully!", Toast.LENGTH_SHORT).show();
                            new MainActivity().replaceLoginFragment();
                        }else {
                            ToastMessageHelper.customErrToast(Objects.requireNonNull(getActivity()),"Something went wrong!");

                           // Toast.makeText(getActivity(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                fullName.setText("");
                userName.setText("");
                mobileNumber.setText("");
                password.setText("");
                confirmPassword.setText("");

            }else {
                ToastMessageHelper.customErrToast(getActivity(),"Password and confirm password not match");
                //Toast.makeText(getActivity(), "Password and confirm password not match", Toast.LENGTH_SHORT).show();
            }

        }

    }

    private void clearPojo(){
        ResOrderApp.setFullName("");
        ResOrderApp.setUserName("");
        ResOrderApp.setPassword("");
        ResOrderApp.setMobileNo("");
    }


    // TODO: Rename method, update argument and hook method into UI event




    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.signUpBtn){
            processSignUp();
        } else if(view.getId() == R.id.already_user){
            new MainActivity().replaceLoginFragment();
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

}
