package com.example.mobilepose.Model.API;

import com.example.mobilepose.Model.API.Entities.FetchProductResponse;
import com.example.mobilepose.Model.API.Entities.FetchUserResponse;
import com.example.mobilepose.Model.API.Entities.LoginResponse;
import com.example.mobilepose.Model.API.Entities.ResponseBase;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIInterface {

    @FormUrlEncoded
    @POST("/api/accounts/login.php")
    Call<ResponseBase<LoginResponse>> Login(
            @Field("username") String username,
            @Field("password") String password
    );

    //kulang ng password
    @FormUrlEncoded
    @POST("/api/accounts/get.php")
    Call<ResponseBase<FetchUserResponse[]>> GetAccount(
            @Field("username") String username
    );

    //kulang ng account status
    @FormUrlEncoded
    @POST("/api/accounts/create.php")
    Call<ResponseBase<Void>> PostAccount(
            @Field("username") String username,
            @Field("password") String password,
            @Field("fname") String fname,
            @Field("lname") String lname,
            @Field("accType") String accType
    );



    @FormUrlEncoded
    @POST("/api/products/get.php")
    Call<ResponseBase<FetchProductResponse[]>> GetProducts(
            @Field("category_id") String categoryId
    );

    @FormUrlEncoded
    @POST("/api/products/create.php")
    Call<ResponseBase<Void>> PostProducts(
            @Field("name") String name,
            @Field("desc") String desc,
            @Field("price") String price,
            @Field("category_id") String categoryId,
            @Field("image") String image,
            @Field("size") String size
    );
}