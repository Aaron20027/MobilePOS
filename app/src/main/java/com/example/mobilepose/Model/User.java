package com.example.mobilepose.Model;

import android.content.Context;
import android.widget.Toast;

import com.example.mobilepose.Controller.MainActivity;

import java.io.Serializable;

public class User implements Serializable {
    private String username;
    private String password;
    private String fname;
    private String lname;
    private String type;
    private String status;

    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }

    public String getPasswordProtected() {
        return password.replaceAll(".","*");
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }



    public String getType() {
        if (type.equals("1")) {
            return "Manager";
        }else{
            return "Cashier";
        }
    }
    public String getStatus() {
        if (status.equals("1")) {
            return "Active";
        }else{
            return "Deactive";
        }
    }

    public User(String username, String password, String fname, String lname, String type, String status) {
        this.username = username;
        this.password = password;
        this.fname = fname;
        this.lname = lname;
        this.type = type;
        this.status = status;
    }

    public boolean ChangePassword(String Old, String New, String Confirm, Context context){
        if(Old.equals(password)){
            if(New.equals(Confirm)){
                password=New;
                return true;
            }else{
                Toast.makeText(context, "New password does not match!", Toast.LENGTH_LONG).show();
                return false;
            }
        }else{
            Toast.makeText(context, "Old password does not match!", Toast.LENGTH_LONG).show();
            return false;
        }
    }



}
