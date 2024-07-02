package com.example.mobilepose.Model.API.Entities;
import com.google.gson.annotations.SerializedName;

public class FetchUserResponse {
    @SerializedName("username")
    public String username;
    @SerializedName("fname")
    public String fname;
    @SerializedName("lname")
    public String lname;
    @SerializedName("accType")
    public int accType;
    @SerializedName("accStatus")
    public String accStatus;
}
