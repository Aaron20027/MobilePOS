package com.example.mobilepose.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mobilepose.Model.API.APICallback;
import com.example.mobilepose.Model.API.APIInterface;
import com.example.mobilepose.Model.API.Entities.CategoryResponse;
import com.example.mobilepose.Model.API.Entities.EmployeeReportResponse;
import com.example.mobilepose.Model.API.Entities.EmployeeReportResponse1;
import com.example.mobilepose.Model.API.Entities.FetchProductResponse;
import com.example.mobilepose.Model.API.Entities.LoginResponse;
import com.example.mobilepose.Model.API.Entities.ProductReportResponse;
import com.example.mobilepose.Model.API.Entities.ProductReportResponse1;
import com.example.mobilepose.Model.API.Entities.ResponseBase;
import com.example.mobilepose.Model.API.Entities.SalesResponse;
import com.example.mobilepose.Model.API.Entities.SalesResponse1;
import com.example.mobilepose.Model.API.POSAPISingleton;
import com.example.mobilepose.Model.Category;
import com.example.mobilepose.Model.Product;
import com.example.mobilepose.R;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;


import java.util.ArrayList;
import java.util.List;

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


        APIInterface api = POSAPISingleton.getOrCreateInstance();
        Call<ResponseBase<EmployeeReportResponse1[]>> rep1 = api.getEmployee1(1);
        rep1.enqueue(new APICallback<>(
                response ->
                {
                    EmployeeReportResponse1[] employeeReportResponse1 = response;
                    for (EmployeeReportResponse1 empReportResponse: employeeReportResponse1) {
                        System.out.println(empReportResponse.acc);
                        System.out.println(empReportResponse.disCount);
                    }

                },
                error ->
                {


                }
        ));


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
                    LoginResponse LOGIN= response;
                    System.out.println(LOGIN.accountType);
                    Intent intent = new Intent(Login.this, homeView.class);
                    intent.putExtra("userinfo", Utils.ToJson(response));
                    intent.putExtra("passCount", password.length());
                    startActivity(intent);
                },
                error -> {
                    Toast.makeText(this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
        ));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}