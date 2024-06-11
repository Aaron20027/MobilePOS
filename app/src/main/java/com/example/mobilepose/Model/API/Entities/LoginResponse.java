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
    @SerializedName("accountType")
    public UserAccountType accountType;
}
