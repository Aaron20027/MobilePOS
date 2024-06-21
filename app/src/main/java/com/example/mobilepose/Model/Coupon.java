package com.example.mobilepose.Model;

import com.example.mobilepose.Controller.Utils;
import com.example.mobilepose.Model.API.APICallback;
import com.example.mobilepose.Model.API.APIInterface;
import com.example.mobilepose.Model.API.Entities.FetchProductResponse;
import com.example.mobilepose.Model.API.Entities.ResponseBase;
import com.example.mobilepose.Model.API.POSAPISingleton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class Coupon {
    private String couponCode;
    private String couponDesc;
    private String couponStat;
    private String couponAmmnt;
    private String couponStart;
    private String couponEnd;
    private String couponAvail;

    public Coupon(String couponCode, String couponDesc, String couponStat, String couponAmmnt, String couponStart, String couponEnd, String couponAvail) {
        this.couponCode = couponCode;
        this.couponDesc = couponDesc;
        this.couponStat = couponStat;
        this.couponAmmnt = couponAmmnt;
        this.couponStart = couponStart;
        this.couponEnd = couponEnd;
        this.couponAvail = couponAvail;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public String getCouponDesc() {
        return couponDesc;
    }

    public String getCouponStat() {
        return couponStat;
    }

    public String getCouponAmmnt() {
        return couponAmmnt;
    }

    public String getCouponStart() {
        return couponStart;
    }

    public String getCouponEnd() {
        return couponEnd;
    }

    public String getCouponAvail() {
        return couponAvail;
    }

    public static List<Coupon> getCoupons(){
        List<Coupon> coupons = new ArrayList<>();

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

        return coupons;

    }
}
