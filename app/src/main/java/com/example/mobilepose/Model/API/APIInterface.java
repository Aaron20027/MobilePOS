package com.example.mobilepose.Model.API;

import com.example.mobilepose.Model.API.Entities.CategoryResponse;
import com.example.mobilepose.Model.API.Entities.DiscountResponse;
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

    //ACCOUNTS

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
    @POST("/api/accounts/delete.php")
    Call<ResponseBase<Void>> DeleteAccount(
            @Field("username") String username
    );


    @FormUrlEncoded
    @POST("/api/accounts/update.php")
    Call<ResponseBase<Void>> UpdateAccount(
            @Field("username") String username,
            @Field("password") String password,
            @Field("fname") String fname,
            @Field("lname") String lname,
            @Field("accType") String accType,
            @Field("accStatus") String accStatus
    );

    //Coupons
    //kulang start date, enddate, availabilty
    @FormUrlEncoded
    @POST("/api/discounts/get.php")
    Call<ResponseBase<DiscountResponse[]>> GetCoupons(
            @Field("id") int id
    );

    @FormUrlEncoded
    @POST("/api/discounts/create.php")
    Call<ResponseBase<Void>> PostCoupon(
            @Field("title") String title,
            @Field("desc") String desc,
            @Field("type") int type,
            @Field("value") float value,
            @Field("avail") int avail
    );

    @FormUrlEncoded
    @POST("/api/discounts/delete.php")
    Call<ResponseBase<Void>> DeleteCoupon(
            @Field("id") int id
    );

    @FormUrlEncoded
    @POST("/api/discounts/update.php")
    Call<ResponseBase<Void>> UpdateCoupon(
            @Field("id") int id,
            @Field("title") String title,
            @Field("desc") String desc,
            @Field("type") int type,
            @Field("value") float value,
            @Field("avail") int avail
    );




    //PRODUCTS
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

    @FormUrlEncoded
    @POST("/api/products/delete.php")
    Call<ResponseBase<Void>> DeleteProducts(
            @Field("id") int id
    );

    @FormUrlEncoded
    @POST("/api/products/update.php")
    Call<ResponseBase<Void>> UpdateProducts(
            @Field("id") int id,
            @Field("name") String name,
            @Field("desc") String desc,
            @Field("price") float price,
            @Field("category_id") int categoryId,
            @Field("image") String image,
            @Field("size") String size,
            @Field("availability") int availability
    );



    @FormUrlEncoded
    @POST("/api/categories/create.php")
    Call<ResponseBase<Void>> PostCategory(
            @Field("name") String name
    );

    @FormUrlEncoded
    @POST("/api/categories/get.php")
    Call<ResponseBase<CategoryResponse[]>> GetCategory(
            @Field("name") int name
    );
}