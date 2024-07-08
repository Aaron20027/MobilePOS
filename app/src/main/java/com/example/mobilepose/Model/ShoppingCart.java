package com.example.mobilepose.Model;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.mobilepose.Model.Callbacks.CouponCallback;
import com.example.mobilepose.Model.Coupon;
import com.example.mobilepose.Model.Order;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart  implements Parcelable{
    List<Order> orders=new ArrayList<Order>();
    Coupon couponApplied;
    double subtotal=0;
    double discountAmt=0;
    double total=0;


    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public double getTotal() {
        return total;
    }

    public double getVat() {
        return Vat;
    }

    public Coupon getCouponApplied() {
        return couponApplied;
    }

    double Vat=0;

    public double getDiscountAmt() {
        return discountAmt;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public ShoppingCart() {
    }

    public void setDiscountAmt(double discountAmt) {
        this.discountAmt = discountAmt;
    }

    public void addOrder(Order order){
        orders.add(order);
    }

    public boolean orderExists(Product product){
        for (Order ord: orders){
            if (ord.getProduct().getProductName().equals(product.getProductName())){
                return true;
            }

        }
        return false;
    }

    public void clearOrder(){
        orders.clear();
    }

    public void calculateAll(){
        if (orders.size()==0){
            discountAmt=0.00;
            subtotal=0.00;
            Vat=0.00;
            total=0.00;
        }else{
            calculateSubTotal();
            calculateTax();
            calculatetTotal();
        }
    }


    private void calculateSubTotal(){
        subtotal=0;
        for (Order order:orders){
            subtotal+=order.getOrderTotal();
        }
    }

    private void calculateTax(){
        Vat=this.subtotal*(12 / 100.0f);
    }

    private void calculatetTotal(){
        total=this.subtotal+this.Vat-this.discountAmt;
    }


    public void applyDiscount(Coupon coupon) {
        if(coupon.getCouponType()==0){
            this.discountAmt=this.subtotal*(coupon.getCouponAmmnt() / 100.0f);
        }else {
            this.discountAmt=this.subtotal-coupon.getCouponAmmnt();
        }
    }

    public void validateCoupon(String couponCode, Context context, CouponCallback couponCallback) {
        Coupon.getCoupons(new CouponCallback() {
            @Override
            public void onProductsFetched(List<Coupon> coupons) {
                for (Coupon coupon : coupons) {
                    if (coupon.getCouponCode().equals(couponCode)) {
                        applyDiscount(coupon);
                        couponCallback.onProductsFetched(coupons); // Notify the callback
                        return;
                    }
                }
                Toast.makeText(context, "Coupon is Invalid!", Toast.LENGTH_SHORT).show();
                couponCallback.onError(new Exception("Invalid coupon code"));
            }

            @Override
            public void onError(Throwable error) {
                couponCallback.onError(error);
            }
        });
    }

    public double calculateChange(double ammount){
        return ammount-total;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeList(this.orders);
    }

    protected ShoppingCart(Parcel in) {
        subtotal = in.readDouble();
        discountAmt = in.readDouble();
        total = in.readDouble();
        Vat = in.readDouble();
    }

    public static final Creator<ShoppingCart> CREATOR = new Creator<ShoppingCart>() {
        @Override
        public ShoppingCart createFromParcel(Parcel in) {
            return new ShoppingCart(in);
        }

        @Override
        public ShoppingCart[] newArray(int size) {
            return new ShoppingCart[size];
        }
    };
}
