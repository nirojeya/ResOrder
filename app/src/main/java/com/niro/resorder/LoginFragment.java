package com.niro.resorder;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.niro.resorder.helper.AppSettings;
import com.niro.resorder.helper.ToastMessageHelper;
import com.niro.resorder.helper.Utils;
import com.niro.resorder.service.VolleyGetService;

import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    //private String mParam1;
    //private String mParam2;

   // private OnFragmentInteractionListener mListener;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }*/

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);

        initializeView(rootView);

        return rootView;
    }

    private void initializeView(View view){
        final Button button = view.findViewById(R.id.loginBtn);
        final EditText userName = view.findViewById(R.id.login_user_name);
        final EditText password = view.findViewById(R.id.login_password);
        TextView createNew = view.findViewById(R.id.createAccount);

        createNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MainActivity().replaceSignUpFragment();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Utils.checkNotNullEditText(userName)
                        && Utils.checkNotNullEditText(password)){

                    String un = Utils.getInput(userName);
                    String pwd = Utils.getInput(password);

                    if(un.equalsIgnoreCase("Admin") && pwd.equalsIgnoreCase("1234")){
                        ResOrderApp.setUserName(un);
                        ResOrderApp.setUserDesignation("Admin");

                        ResOrderApp.setUserId("100");

                        AppSettings.setUserSession(Objects.requireNonNull(getActivity()),1);

                        startActivity(new Intent(getActivity(),OrderSelection.class));

                    }else if(un.equalsIgnoreCase("User") && pwd.equalsIgnoreCase("1111")){
                        ResOrderApp.setUserName(un);
                        ResOrderApp.setUserDesignation("User");

                        ResOrderApp.setUserId("101");


                        AppSettings.setUserSession(Objects.requireNonNull(getActivity()),1);


                        startActivity(new Intent(getActivity(),OrderSelection.class));

                    }else {
                        //Toast.makeText(getActivity(), "Incorrect username or password", Toast.LENGTH_SHORT).show();
                        button.setEnabled(false);
                        VolleyGetService.syncAllUsers(getActivity(), "http://prod.kalesystems.com:3000/api/auth/users", un, pwd, new VolleyGetService.LoginUserDelegate() {
                            @Override
                            public void checkValidUser(boolean isValid) {
                                button.setEnabled(true);

                                if(isValid){
                                    AppSettings.setUserSession(Objects.requireNonNull(getActivity()),1);


                                    startActivity(new Intent(getActivity(),OrderSelection.class));
                                    getActivity().finish();


                                }else {
                                    ToastMessageHelper.customErrToast(Objects.requireNonNull(getActivity()),"Wrong user name or password");
                                    //Toast.makeText(getActivity(), "Wrong user name or password", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
                    }
                }else {
                    Toast.makeText(getActivity(), "Can not be empty", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        /*if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }*/
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        /*if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
       // mListener = null;
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
    /*public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }*/
}
