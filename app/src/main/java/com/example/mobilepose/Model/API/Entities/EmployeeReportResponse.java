package com.example.mobilepose.Model.API.Entities;

import com.google.gson.annotations.SerializedName;

public class EmployeeReportResponse {
    @SerializedName("acc")
    public String acc;
    @SerializedName("disCount")
    public int disCount;
    @SerializedName("acc1")
    public String acc1;
    @SerializedName("avgOrdersPerEmployee")
    public float avgOrdersPerEmployee;
}
