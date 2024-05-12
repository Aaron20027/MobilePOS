package com.example.mobilepose.Model;

import android.content.Context;
import android.provider.MediaStore;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.mobilepose.Controller.MainActivity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class User implements Serializable {
    private String username;
    private String password;
    private String fname;
    private String lname;
    private String type;
    private String contact;
    private String email;
    private String address;
    private String status;
    private String img;

    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public String getEmail() {
        return email;
    }

    public String getType() {
        if (status=="1") {
            return "Manager";
        }else{
            return "Cashier";
        }
    }
    public String getContact() {
        return contact;
    }
    public String getAddress() {
        return address;
    }
    public String getStatus() {
        if (status=="1") {
            return "Activate";
        }else{
            return "Deactivate";
        }
    }
    public String getImg() {
        return img;
    }

    public User(String username, String password, String fname, String lname, String type, String contact, String email, String address, String status, String img) {
        this.username = username;
        this.password = password;
        this.fname = fname;
        this.lname = lname;
        this.type = type;
        this.contact = contact;
        this.email = email;
        this.address = address;
        this.status = status;
        this.img = img;
    }

    public void ChangePassword(String Old, String New, String Confirm, Context context,final VolleyCallback callback){
        if(Old.equals(password)){
            if(New.equals(Confirm)){
                DatabaseSingle db=DatabaseSingle.getInstance();
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", username);
                params.put("password", password);
                db.ManageDatabaseObject("http://192.168.1.13/Android/updateUser.php",context,callback,params);
            }else{
                Toast.makeText(context, "New password does not match!", Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(context, "Old password does not match!", Toast.LENGTH_LONG).show();
        }
    }



}
