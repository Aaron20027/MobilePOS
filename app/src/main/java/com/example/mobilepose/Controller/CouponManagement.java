package com.example.mobilepose.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobilepose.Model.DatabaseSingle;
import com.example.mobilepose.Model.VolleyCallback;
import com.example.mobilepose.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CouponManagement extends AppCompatActivity implements VolleyCallback {
    JSONObject jsonObject;
    JSONArray jsonArray;
    DatabaseSingle db=DatabaseSingle.getInstance();

    EditText codeEdit,descEdit,statusEdit,percEdit,startEdit,endEdit;
    TextView codeError,descError,statusError,PercError,startError,endError,availabiltyError;
    RadioGroup availabiltyGRP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon_management);

        codeEdit=findViewById(R.id.couponCodeEdit);
        descEdit=findViewById(R.id.couponDescEdit);
        //statusEdit=findViewById(R.id.discountStatusEdit);
        percEdit=findViewById(R.id.percentageEdit);
        startEdit=findViewById(R.id.startEdit);
        endEdit=findViewById(R.id.endEdit);

        codeError=findViewById(R.id.codeError);
        descError=findViewById(R.id.descError);
        statusError=findViewById(R.id.statError);
        PercError=findViewById(R.id.ammntError);
        startError=findViewById(R.id.startError);
        endError=findViewById(R.id.endError);
        availabiltyError=findViewById(R.id.availabiltyError);

        SetErrorState(View.INVISIBLE,codeError,descError,statusError,PercError,startError,endError,availabiltyError);

    }

    public void CreateCoupon(View view){

    }

    public void SearchCoupon(View view){
        if(codeEdit.getText().toString().trim()==""){
            SetErrorState(View.VISIBLE, codeError);
            Toast.makeText(CouponManagement.this, "Coupon Code field is blank!", Toast.LENGTH_LONG).show();
        }else{
            SetErrorState(View.INVISIBLE, codeError);
            Map<String, String> params = new HashMap<String, String>();
            params.put("couponcode", codeEdit.getText().toString());
            db.ManageDatabaseObject("Android/searchCoupon.php",CouponManagement.this,CouponManagement.this,params);
        }

    }

    public void UpdateCoupon(View view){
        if(codeEdit.getText().toString().trim()==""){
            SetErrorState(View.VISIBLE, codeError);
            Toast.makeText(CouponManagement.this, "Coupon Code field is blank!", Toast.LENGTH_LONG).show();
        }else{
            SetErrorState(View.INVISIBLE, codeError);
            Map<String, String> params = new HashMap<String, String>();
            params.put("couponcode", codeEdit.getText().toString());
            params.put("coupondesc", descEdit.getText().toString());
            params.put("couponstatus", statusEdit.getText().toString());
            params.put("couponpercent", percEdit.getText().toString());
            params.put("couponstart", startEdit.getText().toString());
            params.put("couponend", endEdit.getText().toString());
            params.put("couponavail", "");

            db.ManageDatabaseObject("Android/updateCoupon.php",this,this,params);
        }

    }

    public void DeleteCoupon(View view){
        if(codeEdit.getText().toString().trim()==""){
            SetErrorState(View.VISIBLE, codeError);
            Toast.makeText(CouponManagement.this, "Coupon Code field is blank!", Toast.LENGTH_LONG).show();
        }else{
            SetErrorState(View.INVISIBLE, codeError);
            Map<String, String> params = new HashMap<String, String>();
            params.put("couponcode", codeEdit.getText().toString());
            db.ManageDatabaseObject("Android/deleteCoupon.php",CouponManagement.this,CouponManagement.this,params);
        }

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
            Toast.makeText(CouponManagement.this, jsonObject.getString("status"), Toast.LENGTH_LONG).show();
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