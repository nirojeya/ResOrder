package com.niro.resorder;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.niro.resorder.helper.Utils;
import com.niro.resorder.service.VolleyPostService;

import javax.xml.validation.Validator;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SignUpFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SignUpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignUpFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

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
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }


    private void initializeView(View view){

        EditText fullName = view.findViewById(R.id.signFullName);
        EditText userName = view.findViewById(R.id.signEmailId);
        EditText mobileNumber = view.findViewById(R.id.signPhone);
        EditText password = view.findViewById(R.id.signPassword);
        EditText confirmPassword = view.findViewById(R.id.signConPassword);
        Button signUpButton = view.findViewById(R.id.signUpBtn);

        signUpButton.setOnClickListener(this);

        if(Utils.checkNotNullEditText(fullName) && Utils.checkNotNullEditText(userName) &&
                Utils.checkNotNullEditText(mobileNumber) && Utils.checkNotNullEditText(password) &&
                Utils.checkNotNullEditText(confirmPassword)){

            if(Utils.getInput(password).equalsIgnoreCase(Utils.getInput(confirmPassword))) {

                ResOrderApp.setFullName(Utils.getInput(fullName));
                ResOrderApp.setUserName(Utils.getInput(userName));
                ResOrderApp.setPassword(Utils.getInput(password));
                ResOrderApp.setMobileNo(Utils.getInput(mobileNumber));

                VolleyPostService.postUser(getActivity(), "", new VolleyPostService.UserSignUpDelegate() {
                    @Override
                    public void processRegisterFinished(String type) {
                        clearPojo();

                        if(!type.equals("error")){
                            new MainActivity().replaceLoginFragment();
                        }
                    }
                });

                fullName.setText("");
                userName.setText("");
                mobileNumber.setText("");
                password.setText("");
                confirmPassword.setText("");

            }else {
                Toast.makeText(getActivity(), "Password and confirm password not match", Toast.LENGTH_SHORT).show();
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
