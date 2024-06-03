package com.example.mobilepose;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyAccount#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyAccount extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView firstname,lastname,password,contact,email,address,acctype,accstatus;

    public MyAccount() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyAccount.
     */
    // TODO: Rename and change types and number of parameters
    public static MyAccount newInstance(String param1, String param2) {
        MyAccount fragment = new MyAccount();
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

        View view = inflater.inflate(R.layout.fragment_my_account, container, false);
        TextView textView= view.findViewById(R.id.textView92);

        //add image
        firstname= view.findViewById(R.id.firstnameTxt);;
        lastname= view.findViewById(R.id.lastnameTxt);;
        password= view.findViewById(R.id.passwordTxt);;
        contact= view.findViewById(R.id.contactTxt);;
        email= view.findViewById(R.id.emailTxt);;
        address= view.findViewById(R.id.addressTxt);;
        acctype= view.findViewById(R.id.typeTxt);
        accstatus= view.findViewById(R.id.statusTxt);;

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowChangePassword();
            }
        });

        return view;
    }

    public void ShowChangePassword(){
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                requireContext(), R.style.BottomSheetDialogTheme
        );
        View bottomSheetView = LayoutInflater.from(requireContext())
                .inflate(
                        R.layout.change_password_pop,
                        (ConstraintLayout) getActivity().findViewById(R.id.changepassword)
                );

        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();
    }

    public void SetInfo(){
        //code to set info of my account

    }

    public void changePassword(){
        //code to change password in database

    }

    public void validatePassword(){
        //code to check if oldd password is same as current old passord
        //check if new password same as confirmpasserd

    }
}