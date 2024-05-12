package com.example.mobilepose.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobilepose.Model.DatabaseSingle;
import com.example.mobilepose.Model.User;
import com.example.mobilepose.Model.VolleyCallback;
import com.example.mobilepose.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AccountManagement extends AppCompatActivity implements VolleyCallback {

    JSONObject jsonObject;
    JSONArray jsonArray;
    DatabaseSingle db=DatabaseSingle.getInstance();
    private EditText usernameTxt, fnameTxt,lnameTxt,passTxt,contactTxt,emailTxt,addressTxt;
    private TextView usernameError, fnameError,lnameError,passError,contactError,emailError,addressError, typeError, statusError;
    private RadioGroup typeRadio,statusRadio;
    private RadioButton typeRadioBtn,statusRadioBtn;

    String path;
    Uri uri;
    private ImageView captureImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_management);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        usernameTxt=findViewById(R.id.userEdit);
        fnameTxt=findViewById(R.id.firstEdit);
        lnameTxt=findViewById(R.id.lastEdit);
        passTxt=findViewById(R.id.passEdit);
        contactTxt=findViewById(R.id.contactEdit);
        emailTxt=findViewById(R.id.emailEdit);
        addressTxt=findViewById(R.id.addressEdit);

        typeRadio=findViewById(R.id.typeRadio);
        statusRadio=findViewById(R.id.statusRadio);


        usernameError=findViewById(R.id.userError);
        fnameError=findViewById(R.id.firstError);
        lnameError=findViewById(R.id.lastError);
        passError=findViewById(R.id.passError);
        contactError=findViewById(R.id.contactError);
        emailError=findViewById(R.id.emailError);
        addressError=findViewById(R.id.codeError);
        typeError=findViewById(R.id.passTxt);
        statusError=findViewById(R.id.statusError);



        SetErrorState(View.INVISIBLE, usernameError, fnameError,lnameError,passError,contactError,emailError,addressError, typeError, statusError);
    }

    public void CreateUser(View view){
        int selectedIdType = typeRadio.getCheckedRadioButtonId();
        int selectedIdStatus = statusRadio.getCheckedRadioButtonId();
        typeRadioBtn=findViewById(selectedIdType);
        statusRadioBtn=findViewById(selectedIdStatus);
        String typeString=typeRadioBtn.getText().toString();
        String statusString=statusRadioBtn.getText().toString();




        Map<String, String> params = new HashMap<String, String>();
        params.put("username", usernameTxt.getText().toString());
        params.put("fname", fnameTxt.getText().toString());
        params.put("lname", lnameTxt.getText().toString());
        params.put("password", passTxt.getText().toString());
        params.put("type", );
        params.put("contact", contactTxt.getText().toString());
        params.put("email", emailTxt.getText().toString());

        //radio
        params.put("status", emailTxt.getText().toString());
        params.put("image", "TEST");


        db.ManageDatabaseObject("http://192.168.1.13/Android/createUser.php",this,this,params);

        /*
        if(usernameTxt.getText().toString().trim()=="" || passwordTxt.getText().toString().trim()=="" || nameTxt.getText().toString().trim()==""
        || accountTypeTxt.getText().toString().trim()=="" || contactTxt.getText().toString().trim()=="" || addressTxt.getText().toString().trim()==""){
            Toast.makeText(AccountManagement.this, "Some required fields are blank.!", Toast.LENGTH_LONG).show();
        }else{
            db.SearchUserInfo(usernameTxt.getText().toString(),"http://192.168.1.13/Android/createUser.php", AccountManagement.this, this);
        }

         */
    }
    public void SearchUser(View view){
        if(usernameTxt.getText().toString().trim()==""){
            Toast.makeText(AccountManagement.this, "Username field is blank!", Toast.LENGTH_LONG).show();
        }else{
            db.SearchUserInfo(usernameTxt.getText().toString(),"http://192.168.1.13/Android/searchUser.php", AccountManagement.this, this);
        }
    }

    // Fix update and php
    public void UpdateUser(View view){
        if(usernameTxt.getText().toString().trim()==""){
            Toast.makeText(AccountManagement.this, "Username field is blank!", Toast.LENGTH_LONG).show();
        }else{
            db.UpdateUserInfo(usernameTxt.getText().toString(),"http://192.168.1.13/Android/searchUser.php", AccountManagement.this, this);
        }
    }
    public void DeleteUser(View view){
        if(usernameTxt.getText().toString().trim()==""){
            Toast.makeText(AccountManagement.this, "Username field is blank!", Toast.LENGTH_LONG).show();
        }else{
            db.UpdateUserInfo(usernameTxt.getText().toString(),"http://192.168.1.13/Android/deleteUser.php", AccountManagement.this, this);
        }
    }

    public void AddUserImage(View view){


    }


    public void SetErrorState(int view, TextView... texts){
        for(TextView text : texts){
            text.setVisibility(view);
        }

    }

    @Override
    public void onSuccess(String response) {
        try {
            jsonObject=new JSONObject(response);
            Toast.makeText(AccountManagement.this, jsonObject.getString("status"), Toast.LENGTH_LONG).show();

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onSearchSuccess(String response) {
        try {
            jsonArray=new JSONArray(response);
            jsonObject=jsonArray.getJSONObject(0);

            User user=new User(jsonObject.getString("acc_User"),
                    jsonObject.getString("acc_Pass"),jsonObject.getString("acc_Fname"),
                    jsonObject.getString("acc_Lname"),jsonObject.getString("acc_Type"),
                    jsonObject.getString("acc_Contact"), jsonObject.getString("acc_Email"),
                    jsonObject.getString("acc_Address"), jsonObject.getString("acc_Status"),
                    jsonObject.getString("acc_Image"));

            usernameTxt.setText(user.getUsername());
            fnameTxt.setText(user.getFname());
            lnameTxt.setText(user.getLname());
            passTxt.setText(user.getPassword());
            contactTxt.setText(user.getContact());
            emailTxt.setText(user.getEmail());
            addressTxt.setText(user.getAddress());

            //typeRadio.check();
            //statusTxt.setText(user.getStatus());

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onError(String error) {

    }
}