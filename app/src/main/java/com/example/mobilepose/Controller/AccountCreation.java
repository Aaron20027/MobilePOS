package com.example.mobilepose.Controller;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.mobilepose.AccountManagement;
import com.example.mobilepose.R;

public class AccountCreation extends Fragment {
    TextView backBtn;

    TextView usernameTxt,firstnameTxt,lastnameTxt,passwordTxt;

    RadioGroup accountTypGrp,AccountStatGrp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_account_creation, container, false);
        backBtn=view.findViewById(R.id.backAccountBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentmanger= getActivity().getSupportFragmentManager();
                fragmentmanger.beginTransaction()
                        .replace(R.id.fragmentContainerView, AccountManagement.class,null)
                        .setReorderingAllowed(true)
                        .addToBackStack("name")
                        .commit();
            }
        });

        usernameTxt=view.findViewById(R.id.usernameEdit);
        firstnameTxt=view.findViewById(R.id.firstnameEdit);
        lastnameTxt=view.findViewById(R.id.lastnameEdit);
        passwordTxt=view.findViewById(R.id.passwordEdit);
        accountTypGrp=view.findViewById(R.id.typeRadio);
        AccountStatGrp=view.findViewById(R.id.statusRadio);

        return view;
    }

    public void CreateUser(View view){
        //code to ADD user to database
    }

}