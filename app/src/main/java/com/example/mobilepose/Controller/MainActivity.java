package com.example.mobilepose.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.mobilepose.Model.API.APIInterface;
import com.example.mobilepose.Model.API.Entities.LoginResponse;
import com.example.mobilepose.Model.API.Entities.ResponseBase;
import com.example.mobilepose.Model.API.POSAPISingleton;
import com.example.mobilepose.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements Callback<ResponseBase<LoginResponse>> {

    private EditText userTxt, passTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userTxt = findViewById(R.id.editTextText);
        passTxt = findViewById(R.id.editTextTextPassword);

    }

    public void validateUser(View view) {
        String username = userTxt.getText().toString().trim();
        String password = passTxt.getText().toString().trim();
        String hashedPass = Utils.MD5(password);
        APIInterface api = POSAPISingleton.getOrCreateInstance();
        Call<ResponseBase<LoginResponse>> loginCall = api.Login(username, hashedPass);
        loginCall.enqueue(this);
    }

    @Override
    public void onResponse(Call<ResponseBase<LoginResponse>> call, Response<ResponseBase<LoginResponse>> response) {
        if (response.isSuccessful()) {
            ResponseBase<LoginResponse> loginResponse = response.body();
            if (loginResponse.success) {
                Intent intent = new Intent(MainActivity.this, MyAccount.class);
                intent.putExtra("userinfo", Utils.ToJson(loginResponse.data));
                startActivity(intent);
            }
        }

        // TODO: Add error message
    }

    @Override
    public void onFailure(Call<ResponseBase<LoginResponse>> call, Throwable t) {
        // TODO: Add error message
    }
}