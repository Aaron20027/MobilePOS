package com.example.mobilepose.Model.API.Entities;
import com.google.gson.annotations.SerializedName;

public class FetchUserResponse {
    @SerializedName("username")
    public String username;
    @SerializedName("firstName")
    public String fname;
    @SerializedName("lastName")
    public String lname;
    @SerializedName("pass")
    public String pass;
    @SerializedName("accountType")
    public int accType;
    @SerializedName("status")
    public String accStatus;
}
