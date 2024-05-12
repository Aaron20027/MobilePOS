package com.example.mobilepose.Controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.mobilepose.Model.DatabaseSingle;
import com.example.mobilepose.Model.User;
import com.example.mobilepose.Model.VolleyCallback;
import com.example.mobilepose.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MyAccount extends AppCompatActivity implements VolleyCallback {

    private TextView usernameTxt,fnameTxt,lnameTxt,passTxt,typeTxt,contactTxt,emailTxt,addressTxt,statusTxt;
    private EditText oldPassEdit, newPassEdit, confirmNewPassEdit;
    ConstraintLayout cons;

    DatabaseSingle db=DatabaseSingle.getInstance();
    JSONObject jsonObject;
    JSONArray jsonArray;
    User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        //this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);




        //User user = (User) getIntent().getSerializableExtra("user");
        String username=getIntent().getStringExtra("username");

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

        db.SearchUserInfo(username,"http://192.168.1.13/Android/searchUser.php", MyAccount.this, this);


    }

    public void showChangePassword(View view){
        LayoutInflater inflater= (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popup=inflater.inflate(R.layout.change_password_pop, null);
        int width= ViewGroup.LayoutParams.WRAP_CONTENT;
        int height= ViewGroup.LayoutParams.MATCH_PARENT;

        boolean focusable=true;

        PopupWindow popupWindow=new PopupWindow(popup,width,height,focusable);
        cons.post(new Runnable() {
            @Override
            public void run() {
                popupWindow.showAtLocation(cons, Gravity.RIGHT,0,0);
            }
        });

        oldPassEdit=popup.findViewById(R.id.oldPassTxt);
        newPassEdit=popup.findViewById(R.id.newPassTxt);
        confirmNewPassEdit=popup.findViewById(R.id.confirmNewPassTxt);

    }

    @Override
    public void onSuccess(String response) {
    }

    @Override
    public void onSearchSuccess(String response) {
        try {
            jsonArray=new JSONArray(response);
            jsonObject=jsonArray.getJSONObject(0);

            user=new User(jsonObject.getString("acc_User"),
                    jsonObject.getString("acc_Pass"),jsonObject.getString("acc_Fname"),
                    jsonObject.getString("acc_Lname"),jsonObject.getString("acc_Type"),
                    jsonObject.getString("acc_Contact"), jsonObject.getString("acc_Email"),
                    jsonObject.getString("acc_Address"), jsonObject.getString("acc_Status"),
                    jsonObject.getString("acc_Image"));

            usernameTxt.setText(user.getUsername());
            fnameTxt.setText(user.getFname());
            lnameTxt.setText(user.getLname());
            passTxt.setText(user.getPassword());
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