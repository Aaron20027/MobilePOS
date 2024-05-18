package com.example.mobilepose.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mobilepose.Model.DatabaseSingle;
import com.example.mobilepose.Model.User;
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

        db.ManageDatabaseObject("Android/login.php",MainActivity.this,this,params);
    }

    @Override
    public void onSuccess(String response) {
        try {
            jsonObject=new JSONObject(response);
            String username= userTxt.getText().toString().trim();

            if (jsonObject.optString("status").equals("Login Successful!")){
                Intent intent=new Intent(MainActivity.this, MyAccount.class);
                intent.putExtra("username",username);
                startActivity(intent);

            }else{
                Toast.makeText(MainActivity.this, jsonObject.optString("status"), Toast.LENGTH_SHORT).show();

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSearchSuccess(String response) {

    }

    @Override
    public void onError(String error) {

    }
}