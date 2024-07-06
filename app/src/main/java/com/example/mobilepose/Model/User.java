package com.example.mobilepose.Model;

import android.content.Context;
import android.widget.Toast;

import com.example.mobilepose.Controller.Utils;
import com.example.mobilepose.Model.API.APICallback;
import com.example.mobilepose.Model.API.APIInterface;
import com.example.mobilepose.Model.API.Entities.FetchUserResponse;
import com.example.mobilepose.Model.API.Entities.PasswordResponse;
import com.example.mobilepose.Model.API.Entities.ResponseBase;
import com.example.mobilepose.Model.API.POSAPISingleton;
import com.example.mobilepose.Model.Callbacks.PasswordCallback;
import com.example.mobilepose.Model.Callbacks.UserCallback;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

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
    public String getPasswordHashed() {
        return Utils.MD5(password);
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public String getType() {
        return type;

    }

    public String getType(int type) {
        if (type==0) {
            return "Manager";
        }else{
            return "Cashier";
        }
    }

    public String getStatus() {
        return status;

    }

    public String getStatus(int type) {
        if (type==0) {
            return "Activate";
        }else{
            return "Deactivate";
        }
    }


    public void setPassword(String password) {
        this.password = password;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public void setType(String type) {
        if (type.equals("Manager")) {
            this.type="0";
        }else{
            this.type="1";
        }
    }

    public void setType(int type) {
        this.type=String.valueOf(type);
    }

    public void setStatus(String status) {
        if (status.equals("Activate")) {
            this.status="0";
        }else{
            this.status="1";
        }
    }

    public void setStatus(int status) {
        this.status=String.valueOf(status);
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

    public static void getUsers(UserCallback callback) {
        List<User> users = new ArrayList<>();

        APIInterface api = POSAPISingleton.getOrCreateInstance();
        Call<ResponseBase<FetchUserResponse[]>> acc = api.GetAccount("test");
        acc.enqueue(new APICallback<>(
                response -> {
                    FetchUserResponse[] userArray = response;
                    for (FetchUserResponse fetchUserResponse : userArray) {
                        User user = new User(
                                fetchUserResponse.username,
                                fetchUserResponse.pass,
                                fetchUserResponse.fname,
                                fetchUserResponse.lname,
                                String.valueOf(fetchUserResponse.accType),
                                fetchUserResponse.accStatus
                        );

                        users.add(user);
                    }
                    callback.onProductsFetched(users);
                },
                error -> {
                    callback.onError(error);
                }
        ));
    }

    public static void addAccount(User user,Context context) {

        APIInterface api = POSAPISingleton.getOrCreateInstance();
        Call<ResponseBase<Void>> account = api.PostAccount(user.getUsername(),
                user.getPassword(),
                user.getFname(),
                user.getLname(),
                user.getType());

        account.enqueue(new APICallback<>(
                response -> {

                },
                error -> {
                    Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
                }
        ));
    }

    public static void deleteAccount(User user,Context context) {

        APIInterface api = POSAPISingleton.getOrCreateInstance();
        Call<ResponseBase<Void>> account = api.DeleteAccount(user.getUsername());

        account.enqueue(new APICallback<>(
                response -> {

                },
                error -> {
                    Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
                }
        ));
    }

    public static void updateAccount(User user,Context context) {
        APIInterface api = POSAPISingleton.getOrCreateInstance();
        Call<ResponseBase<Void>> account = api.UpdateAccount(user.getUsername(),
                user.getPassword(),
                user.getFname(),
                user.getLname(),
                user.getType(),
                user.getStatus());
        ;
        account.enqueue(new APICallback<>(
                response -> {

                },
                error -> {

                }
        ));
    }

    public static void updatePassword(User user,String password,Context context) {
        APIInterface api = POSAPISingleton.getOrCreateInstance();
        Call<ResponseBase<Void>> account = api.UpdatePassword(user.getUsername(),
                password);
        ;
        account.enqueue(new APICallback<>(
                response -> {

                },
                error -> {

                }
        ));
    }

    public static void getPassword(String user, PasswordCallback passwordCallback) {
        APIInterface api = POSAPISingleton.getOrCreateInstance();
        Call<ResponseBase<PasswordResponse>> account = api.GetPassword(user);
        account.enqueue(new APICallback<>(
                response -> {
                    PasswordResponse passwordResponse=response;
                    passwordCallback.onProductsFetched(passwordResponse.password);

                },
                error -> {
                    passwordCallback.onError(error);

                }
        ));
    }




}
