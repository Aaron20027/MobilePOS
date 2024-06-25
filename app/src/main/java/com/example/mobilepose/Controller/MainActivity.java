package com.example.mobilepose.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mobilepose.Model.API.APICallback;
import com.example.mobilepose.Model.API.APIInterface;
import com.example.mobilepose.Model.API.Entities.FetchProductResponse;
import com.example.mobilepose.Model.API.Entities.LoginResponse;
import com.example.mobilepose.Model.API.Entities.ResponseBase;
import com.example.mobilepose.Model.API.POSAPISingleton;
import com.example.mobilepose.Model.DatabaseSingle;
import com.example.mobilepose.Model.VolleyCallback;
import com.example.mobilepose.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements VolleyCallback {

    private EditText userTxt,passTxt;
    JSONObject jsonObject;
    DatabaseSingle db=DatabaseSingle.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userTxt=findViewById(R.id.editTextText);
        passTxt=findViewById(R.id.editTextTextPassword);

    }

    public void validateUser(View view) {
        String username= userTxt.getText().toString().trim();
        String password= passTxt.getText().toString().trim();

        Map<String, String> params = new HashMap<String, String>();
        params.put("username", username);
        params.put("password", password);


    }

    @Override
    public void onSuccess(String response) {

    }

    @Override
    public void onSearchSuccess(String response) {

    }

    @Override
    public void onError(String error) {

    }
}