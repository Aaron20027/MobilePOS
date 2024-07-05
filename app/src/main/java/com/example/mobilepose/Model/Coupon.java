package com.example.mobilepose.Model;

import android.content.Context;
import android.widget.Toast;

import com.example.mobilepose.Model.API.APICallback;
import com.example.mobilepose.Model.API.APIInterface;
import com.example.mobilepose.Model.API.Entities.DiscountResponse;
import com.example.mobilepose.Model.API.Entities.ResponseBase;
import com.example.mobilepose.Model.API.POSAPISingleton;
import com.example.mobilepose.Model.Adapters.CouponCallback;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class Coupon {
    private int couponId;
    private String couponCode;
    private String couponDesc;
    private int couponType;
    private float couponAmmnt;
    private String couponStart;
    private String couponEnd;
    private int couponAvail;

    public Coupon(int Id, String couponCode, String couponDesc, int couponType, float couponAmmnt, int couponAvail) {
        this.couponId = Id;
        this.couponCode = couponCode;
        this.couponDesc = couponDesc;
        this.couponType = couponType;
        this.couponAmmnt = couponAmmnt;
        this.couponAvail = couponAvail;
    }
    public int getCouponId() {
        return couponId;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public String getCouponDesc() {
        return couponDesc;
    }

    public int getCouponType() {
        return couponType;
    }

    public String getCouponType(int type) {
        if (type==0) {
            return "Percentage";
        }else{
            return "Fixed Ammount";
        }
    }

    public float getCouponAmmnt() {
        return couponAmmnt;
    }

    public String getCouponStart() {
        return couponStart;
    }

    public String getCouponEnd() {
        return couponEnd;
    }

    public int getCouponAvail() {
        return couponAvail;
    }

    public String getCouponAvail(int type) {
        if (type==0) {
            return "Available";
        }else{
            return "Unavailable";
        }
    }

    public void setCouponId(int couponId) {
        this.couponId = couponId;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public void setCouponDesc(String couponDesc) {
        this.couponDesc = couponDesc;
    }

    public void setCouponType(int couponType) {
        this.couponType = couponType;
    }

    public void setCouponAmmnt(float couponAmmnt) {
        this.couponAmmnt = couponAmmnt;
    }

    public void setCouponStart(String couponStart) {
        this.couponStart = couponStart;
    }

    public void setCouponEnd(String couponEnd) {
        this.couponEnd = couponEnd;
    }

    public void setCouponAvail(int couponAvail) {
        this.couponAvail = couponAvail;
    }

    public void setCouponAvail(String avail) {
        if (avail.equals("Available")) {
            this.couponAvail=0;
        }else{
            this.couponAvail=1;
        }
    }

    public static List<Coupon> getCoupons(CouponCallback callback){
        List<Coupon> coupons = new ArrayList<>();

        APIInterface api= POSAPISingleton.getOrCreateInstance();
        Call<ResponseBase<DiscountResponse[]>> prod = api.GetCoupons(1);
        prod.enqueue(new APICallback<>(
                response ->
                {
                    DiscountResponse[] couponArray = response;
                    for (DiscountResponse discountResponse : couponArray) {
                        Coupon coupon= new Coupon(discountResponse.id,
                                discountResponse.title,
                                discountResponse.description,
                                discountResponse.discount_type,
                                discountResponse.discount_value,
                                discountResponse.discount_avail
                        );
                        coupons.add(coupon);

                    }
                    callback.onProductsFetched(coupons);

                },
                error ->
                {
                    callback.onError(error);

                }
        ));

        return coupons;

    }

    public static void addCoupon(Coupon coupon,Context context) {

        APIInterface api = POSAPISingleton.getOrCreateInstance();
        Call<ResponseBase<Void>> account = api.PostCoupon(coupon.getCouponCode(),
                coupon.getCouponDesc(),
                coupon.getCouponType(),
                coupon.getCouponAmmnt(),
                coupon.getCouponAvail());

        account.enqueue(new APICallback<>(
                response -> {

                },
                error -> {
                    Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
                }
        ));
    }


    public static void deleteCoupon(Coupon coupon,Context context) {

        APIInterface api = POSAPISingleton.getOrCreateInstance();
        Call<ResponseBase<Void>> account = api.DeleteCoupon(coupon.getCouponId());

        account.enqueue(new APICallback<>(
                response -> {

                },
                error -> {
                    Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
                }
        ));
    }

    public static void updateCoupon(Coupon coupon,Context context) {
        APIInterface api = POSAPISingleton.getOrCreateInstance();
        Call<ResponseBase<Void>> coup = api.UpdateCoupon(coupon.getCouponId(),
                coupon.getCouponCode(),
                coupon.getCouponDesc(),
                coupon.getCouponType(),
                coupon.getCouponAmmnt(),
                coupon.getCouponAvail());

        coup.enqueue(new APICallback<>(
                response -> {

                },
                error -> {
                    Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
                }
        ));
    }
}
