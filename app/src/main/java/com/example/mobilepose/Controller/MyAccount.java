package com.example.mobilepose.Controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobilepose.Model.API.Entities.LoginResponse;
import com.example.mobilepose.Model.DatabaseSingle;
import com.example.mobilepose.Model.User;
import com.example.mobilepose.Model.VolleyCallback;
import com.example.mobilepose.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MyAccount extends AppCompatActivity implements VolleyCallback {

    private TextView usernameTxt,fnameTxt,lnameTxt,passTxt,typeTxt,contactTxt,emailTxt,addressTxt,statusTxt;
    private EditText oldPassEdit, newPassEdit, confirmNewPassEdit;

    private Button cancelBtn,saveBtn;
    ConstraintLayout cons;

    DatabaseSingle db=DatabaseSingle.getInstance();
    JSONObject jsonObject;
    JSONArray jsonArray;
    User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        String jsonUserInfo = getIntent().getStringExtra("userinfo");
        LoginResponse loginResponse = Utils.FromJson(jsonUserInfo, LoginResponse.class);

        usernameTxt=findViewById(R.id.passError);
        fnameTxt=findViewById(R.id.firstTxt);
        lnameTxt=findViewById(R.id.lastTxt);
        passTxt=findViewById(R.id.passTxt);
        typeTxt=findViewById(R.id.typeTxt);
        contactTxt=findViewById(R.id.contactTxt);
        emailTxt=findViewById(R.id.emailTxt);
        addressTxt=findViewById(R.id.addressTxt);
        statusTxt=findViewById(R.id.statusTxt);

        cons=findViewById(R.id.popupCons);

        usernameTxt.setText(loginResponse.username);
        fnameTxt.setText(loginResponse.firstName);
        lnameTxt.setText(loginResponse.lastName);

        //db.SearchUserInfo(username,"http://192.168.1.13/Android/searchUser.php", MyAccount.this, this)

        //db.ManageDatabaseArray("Android/searchUser.php",MyAccount.this,MyAccount.this,params);

    }

    public void showChangePassword(View view){
      LayoutInflater inflater= (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View overlay = findViewById(R.id.overlay);
        View popup=inflater.inflate(R.layout.change_password_pop, null);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        PopupWindow popupWindow=new PopupWindow(popup,displayMetrics.widthPixels / 2,ViewGroup.LayoutParams.MATCH_PARENT,true);
        popupWindow.setOnDismissListener(() -> overlay.setVisibility(View.GONE));
        cons.post(new Runnable() {
            @Override
            public void run() {
                popupWindow.showAtLocation(cons, Gravity.RIGHT,0,0);
                overlay.setVisibility(View.VISIBLE);
            }
        });

        oldPassEdit=popup.findViewById(R.id.oldPassTxt);
        newPassEdit=popup.findViewById(R.id.newPassTxt);
        confirmNewPassEdit=popup.findViewById(R.id.confirmNewPassTxt);
        cancelBtn=popup.findViewById(R.id.cancelBtn);
        saveBtn=popup.findViewById(R.id.saveBtn);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user.ChangePassword(oldPassEdit.getText().toString(),newPassEdit.getText().toString(),confirmNewPassEdit.getText().toString(),MyAccount.this)){
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("username", user.getUsername());
                    params.put("password", newPassEdit.getText().toString());
                    db.ManageDatabaseObject("Android/updateUser.php",MyAccount.this,MyAccount.this,params);
                    popupWindow.dismiss();
                }
            }
        });

    }

    @Override
    public void onSuccess(String response) {
        try {
            jsonObject=new JSONObject(response);
            System.out.println(jsonObject.optString("status"));
            Toast.makeText(MyAccount.this, jsonObject.optString("status") , Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSearchSuccess(String response) {
        try {
            jsonArray=new JSONArray(response);
            jsonObject=jsonArray.getJSONObject(0);
            //System.out.println(jsonObject.getString("acc_Status"));

            user=new User(jsonObject.getString("acc_User"),
                    jsonObject.getString("acc_Pass"),jsonObject.getString("acc_Fname"),
                    jsonObject.getString("acc_Lname"),jsonObject.getString("acc_Type"),
                    jsonObject.getString("acc_Contact"), jsonObject.getString("acc_Email"),
                    jsonObject.getString("acc_Address"), jsonObject.getString("acc_Status"),
                    jsonObject.getString("acc_Image"));

            usernameTxt.setText(user.getUsername());
            fnameTxt.setText(user.getFname());
            lnameTxt.setText(user.getLname());
            passTxt.setText(user.getPasswordProtected());
            typeTxt.setText(user.getType());
            contactTxt.setText(user.getContact());
            emailTxt.setText(user.getEmail());
            addressTxt.setText(user.getAddress());
            statusTxt.setText(user.getStatus());

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onError(String error) {
    }
}