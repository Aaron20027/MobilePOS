package com.example.mobilepose.Model.API.Entities;

import com.google.gson.annotations.SerializedName;

public class FetchProductResponse {
    @SerializedName("productId")
    public int productId;
    @SerializedName("productName")
    public String productName;
    @SerializedName("productDescription")
    public String productDescription;
    @SerializedName("price")
    public float price;
    @SerializedName("productCategory")
    public int productCategory;
    @SerializedName("productImage")
    public String productImage; // base64
    @SerializedName("available")
    public int available;


}
