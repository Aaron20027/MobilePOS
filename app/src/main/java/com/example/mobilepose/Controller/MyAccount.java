package com.example.mobilepose.Controller;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobilepose.Model.API.Entities.LoginResponse;
import com.example.mobilepose.Model.Callbacks.PasswordCallback;
import com.example.mobilepose.Model.User;
import com.example.mobilepose.R;
import com.example.mobilepose.Model.Callbacks.UserCallback;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.sql.SQLOutput;
import java.util.List;

public class MyAccount extends Fragment {

    private TextView username,firstname,lastname,password,contact,email,address,acctype,accstatus;
    private EditText oldPass,newPass,confirmPass;
    private Button cancelButton,saveButton;
    String loginUserInfo;
    int passLength;
    LoginResponse loginResponse;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (getArguments() != null) {
            loginUserInfo = getArguments().getString("loginUserInfo");
            passLength= getArguments().getInt("passCount");
            loginResponse = Utils.FromJson(loginUserInfo, LoginResponse.class);

        }


        View view = inflater.inflate(R.layout.fragment_my_account, container, false);
        TextView textView= view.findViewById(R.id.textView92);


        username= view.findViewById(R.id.usernameTxt);;
        firstname= view.findViewById(R.id.firstnameTxt);;
        lastname= view.findViewById(R.id.lastnameTxt);;
        password= view.findViewById(R.id.passwordTxt);;
        acctype= view.findViewById(R.id.typeTxt);
        accstatus= view.findViewById(R.id.statusTxt);;

        User.getUsers(new UserCallback() {
            @Override
            public void onProductsFetched(List<User> users) {
                for(User user: users){
                    if (loginResponse.username.equals(user.getUsername())){
                        username.setText("@"+user.getUsername());
                        firstname.setText(user.getFname());
                        lastname.setText(user.getLname());
                        password.setText(new String(new char[passLength]).replace("\0", "*"));
                        acctype.setText(user.getType(Integer.parseInt(user.getType())));
                        accstatus.setText(user.getStatus(Integer.parseInt(user.getStatus())));
                    }
                }

            }

            @Override
            public void onError(Throwable error) {

            }
        });


        TextView backBtn=view.findViewById(R.id.backMyAccountBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("loginUserInfo", loginUserInfo);
                bundle.putInt("passCount", passLength);

                Home home = new Home();
                home.setArguments(bundle);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView, home)
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
                changePassword(bottomSheetDialog);
            }
        });


        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();


    }


    public void changePassword(BottomSheetDialog bottomSheetDialog){
        User.getUsers(new UserCallback() {
            @Override
            public void onProductsFetched(List<User> users) {
                for(User user:users){
                    if(user.getUsername().equals(loginResponse.username)){
                        if (TextUtils.isEmpty(oldPass.getText().toString()) || TextUtils.isEmpty(newPass.getText().toString()) ||
                                TextUtils.isEmpty(confirmPass.getText().toString())) {
                            Toast.makeText(getActivity(), "Please fill in all fields.", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (!Utils.MD5(oldPass.getText().toString()).equals(user.getPassword())) {
                            Toast.makeText(getActivity(), "Old Password does not match!", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (newPass.length() < 8 || newPass.length() > 50) {
                            Toast.makeText(getActivity(), "New Password must be between 8 to 50 characters.", Toast.LENGTH_SHORT).show();
                            return;
                        }


                        if (!newPass.getText().toString().equals(confirmPass.getText().toString())) {
                            Toast.makeText(getActivity(), "New Password does not match!", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        user.setPassword(newPass.getText().toString());
                        oldPass.setText("");
                        newPass.setText("");
                        confirmPass.setText("");
                        User.updateAccount(user,getActivity());
                        bottomSheetDialog.dismiss();
                        Toast.makeText(getActivity(), "Password has been changed!", Toast.LENGTH_SHORT).show();



                    }
                }

            }

            @Override
            public void onError(Throwable error) {

            }
        });

    }






}