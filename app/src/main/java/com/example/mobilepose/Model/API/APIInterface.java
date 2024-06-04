package com.example.mobilepose.Model.API;

import com.example.mobilepose.Model.API.Entities.FetchProductResponse;
import com.example.mobilepose.Model.API.Entities.LoginResponse;
import com.example.mobilepose.Model.API.Entities.ResponseBase;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIInterface {

    @FormUrlEncoded
    @POST("/api/auth/login.php")
    Call<ResponseBase<LoginResponse>> Login(
            @Field("username") String username,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("/api/products/get.php")
    Call<ResponseBase<FetchProductResponse[]>> GetProducts(
            @Field("category_id") String categoryId
    );
}