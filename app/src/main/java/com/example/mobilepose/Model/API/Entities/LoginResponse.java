package com.example.mobilepose.Model.API.Entities;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("username")
    public String username;
    @SerializedName("sessionToken")
    public String sessionToken;
    @SerializedName("firstName")
    public String firstName;
    @SerializedName("lastName")
    public String lastName;
    @SerializedName("email")
    public String email;
    @SerializedName("accountType")
    public UserAccountType accountType;
    @SerializedName("address")
    public String address;
    @SerializedName("profileImage")
    public String profileImage; // base64 string
}
