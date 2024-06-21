package com.example.mobilepose.Model;

import android.app.Activity;
import android.widget.Toast;

import com.example.mobilepose.Controller.Utils;
import com.example.mobilepose.Model.API.APICallback;
import com.example.mobilepose.Model.API.APIInterface;
import com.example.mobilepose.Model.API.Entities.FetchProductResponse;
import com.example.mobilepose.Model.API.Entities.ResponseBase;
import com.example.mobilepose.Model.API.POSAPISingleton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class Product {
    private String productID;
    private String productName;

    public String getProductPrice() {
        return productPrice;
    }

    private String productPrice;
    private String productDescription;
    private String productCategory;
    private String productImage;

    public String getProductID() {
        return productID;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public String getProductImage() {
        return productImage;
    }

    public Product(String productID, String productName,String productPrice, String productDescription, String productCategory, String productImage) {
        this.productID = productID;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productDescription = productDescription;
        this.productCategory = productCategory;
        this.productImage = productImage;
    }

    public static List<Product> getProducts(){
        List<Product> products = new ArrayList<>();

        APIInterface api= POSAPISingleton.getOrCreateInstance();
        Call<ResponseBase<FetchProductResponse[]>> prod = api.GetProducts("0"); // 0, 1, 2, or null
        prod.enqueue(new APICallback<>(
                response ->
                {
                    System.out.println(response);
                    System.out.println(Utils.ToJson(response));
                    //products.add(new Product());
                    //Toast.makeText(activity, Utils.ToJson(response), Toast.LENGTH_SHORT).show();
                    //Product item=new Product();
                    //products.add(item);
                },
                error ->
                {
                    //Toast.makeText(activity, Utils.ToJson(error.toString()), Toast.LENGTH_SHORT).show();

                }
        ));

        return products;

    }

    private static void getCategories(){

        //loop through categories

        List<String> categories = new ArrayList<>();
        //add to array

        //return array

    }


}

