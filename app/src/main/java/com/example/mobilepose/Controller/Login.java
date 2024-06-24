package com.example.mobilepose.Controller;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mobilepose.Model.API.APICallback;
import com.example.mobilepose.Model.API.APIInterface;
import com.example.mobilepose.Model.API.Entities.LoginResponse;
import com.example.mobilepose.Model.API.Entities.ResponseBase;
import com.example.mobilepose.Model.API.POSAPISingleton;
import com.example.mobilepose.R;

import retrofit2.Call;

public class Login extends AppCompatActivity {


    private EditText userTxt, passTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        userTxt = findViewById(R.id.usernameEdit);
        passTxt = findViewById(R.id.passwordEdit);

    }

    public void loginUser(View view){
        String username = userTxt.getText().toString().trim();
        String password = passTxt.getText().toString().trim();

        String hashedPass = Utils.MD5(password);
        APIInterface api = POSAPISingleton.getOrCreateInstance();

        Call<ResponseBase<LoginResponse>> loginCall = api.Login(username, hashedPass);
        loginCall.enqueue(new APICallback<>(
                response ->
                {
                    Intent intent = new Intent(Login.this, MyAccount.class);
                    intent.putExtra("userinfo", Utils.ToJson(response));
                    startActivity(intent);
                },
                error -> {
                    Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show();
                }
        ));
    }

}