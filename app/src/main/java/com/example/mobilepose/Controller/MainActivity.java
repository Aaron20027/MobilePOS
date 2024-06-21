package com.example.mobilepose.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mobilepose.Model.API.APICallback;
import com.example.mobilepose.Model.API.APIInterface;
import com.example.mobilepose.Model.API.Entities.FetchProductResponse;
import com.example.mobilepose.Model.API.Entities.LoginResponse;
import com.example.mobilepose.Model.API.Entities.ResponseBase;
import com.example.mobilepose.Model.API.POSAPISingleton;
import com.example.mobilepose.R;

import retrofit2.Call;

public class MainActivity extends AppCompatActivity {

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
        loginCall.enqueue(new APICallback<>(
                response ->
                {
                    Intent intent = new Intent(MainActivity.this, MyAccount.class);
                    intent.putExtra("userinfo", Utils.ToJson(response));
                    startActivity(intent);

                    Toast.makeText(this, Utils.ToJson(response).toString(), Toast.LENGTH_SHORT).show();
                },
                error -> {
                    Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show();
                }
        ));
    }
}