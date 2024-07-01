package com.example.mobilepose.Controller;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mobilepose.CouponManagement;
import com.example.mobilepose.Model.API.Entities.LoginResponse;
import com.example.mobilepose.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class MyAccount extends Fragment {

    private TextView username,firstname,lastname,password,contact,email,address,acctype,accstatus;
    private EditText oldPass,newPass,confirmPass;
    private Button cancelButton,saveButton;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //String jsonUserInfo = getActivity().getIntent().getStringExtra("userinfo");
        //LoginResponse loginResponse = Utils.FromJson(jsonUserInfo, LoginResponse.class);

        View view = inflater.inflate(R.layout.fragment_my_account, container, false);
        TextView textView= view.findViewById(R.id.textView92);

        //add image
        username= view.findViewById(R.id.usernameTxt);;
        firstname= view.findViewById(R.id.firstnameTxt);;
        lastname= view.findViewById(R.id.lastnameTxt);;
        password= view.findViewById(R.id.passwordTxt);;
        acctype= view.findViewById(R.id.typeTxt);
        accstatus= view.findViewById(R.id.statusTxt);;

        //username.setText(loginResponse.username);
        //firstname.setText(loginResponse.firstName);
        //lastname.setText(loginResponse.lastName);

        TextView backBtn=view.findViewById(R.id.backMyAccountBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentmanger= getActivity().getSupportFragmentManager();
                fragmentmanger.beginTransaction()
                        .replace(R.id.fragmentContainerView, Home.class,null)
                        .setReorderingAllowed(true)
                        .addToBackStack("name")
                        .commit();
            }
        });

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


        oldPass=bottomSheetView.findViewById(R.id.oldPassEdit);
        newPass=bottomSheetView.findViewById(R.id.newPassEdit);
        confirmPass=bottomSheetView.findViewById(R.id.confirmNewPassEdit);
        cancelButton=bottomSheetView.findViewById(R.id.cancelBtn);
        saveButton=bottomSheetView.findViewById(R.id.saveBtn);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePassword();
            }
        });


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