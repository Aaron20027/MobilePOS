package com.example.mobilepose.Controller;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.text.TextUtils;
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
    RadioGroup accountTypGrp;

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

        return view;
    }

    public void CreateUser(View view){
        if (TextUtils.isEmpty(usernameTxt.getText().toString()) || TextUtils.isEmpty(firstnameTxt.getText().toString()) ||
                TextUtils.isEmpty(lastnameTxt.getText().toString()) || TextUtils.isEmpty(passwordTxt.getText().toString())
                || accountTypGrp.getCheckedRadioButtonId() == -1) {
            Toast.makeText(getActivity(), "Please fill in all fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (usernameTxt.length() < 6 || usernameTxt.length() > 30) {
            Toast.makeText(getActivity(), "Username must be between 6 to 30 characters.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (firstnameTxt.length() < 6 || firstnameTxt.length() > 35) {
            Toast.makeText(getActivity(), "Firstname must be between 6 to 35 characters.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (lastnameTxt.length() < 6 || lastnameTxt.length() > 35) {
            Toast.makeText(getActivity(), "Lastname must be between 6 to 35 characters.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (passwordTxt.length() < 8 || passwordTxt.length() > 50) {
            Toast.makeText(getActivity(), "Password must be between 8 to 50 characters.", Toast.LENGTH_SHORT).show();
            return;
        }

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

        Toast.makeText(view.getContext(), "User has been added!", Toast.LENGTH_SHORT).show();

    }

}