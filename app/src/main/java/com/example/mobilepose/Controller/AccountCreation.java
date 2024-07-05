package com.example.mobilepose.Controller;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobilepose.Model.User;
import com.example.mobilepose.R;

public class AccountCreation extends Fragment {
    Button createBtn;
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

        createBtn=view.findViewById(R.id.createUserBtn);
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateUser(view);
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

        int typeid=accountTypGrp.getCheckedRadioButtonId();
        RadioButton radioButton = view.findViewById(typeid);
        System.out.println(radioButton.getText().toString());

        User.addAccount(new User(usernameTxt.getText().toString(),
                passwordTxt.getText().toString(),
                firstnameTxt.getText().toString(),
                lastnameTxt.getText().toString(),
                radioButton.getText().toString(),
                "1"),getActivity());

        usernameTxt.setText("");
        firstnameTxt.setText("");
        lastnameTxt.setText("");
        passwordTxt.setText("");
        accountTypGrp.clearCheck();
        AccountStatGrp.clearCheck();

        Toast.makeText(view.getContext(), "User has been added!", Toast.LENGTH_SHORT).show();

    }

}