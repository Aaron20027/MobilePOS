package com.example.mobilepose;

import com.example.mobilepose.Model.Coupon;

import java.util.List;

public interface CouponCallback {
    void onProductsFetched(List<Coupon> coupons);
    void onError(Throwable error);
}
