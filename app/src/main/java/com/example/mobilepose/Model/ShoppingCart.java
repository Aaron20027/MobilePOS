package com.example.mobilepose.Model;

import com.example.mobilepose.Model.Coupon;
import com.example.mobilepose.Model.Order;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    List<Order> orders=new ArrayList<Order>();
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

    double Vat=0;

    public double getDiscountAmt() {
        return discountAmt;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void addOrder(Order order){
        orders.add(order);
        calculateSubTotal();
        calculateTax();
        calculatetTotal();
    }

    public void calculateSubTotal(){
        for (Order order:orders){
            subtotal+=order.getOrderTotal();
        }
    }

    public void calculateTax(){
        Vat=this.subtotal*(12 / 100.0f);
    }

    public void calculatetTotal(){
        total=this.subtotal+this.Vat-this.discountAmt;
    }


    public void applyDiscount(Coupon coupon) {
        if(coupon.getCouponType()==0){
            this.discountAmt=this.subtotal*(coupon.getCouponAmmnt() / 100.0f);
            calculatetTotal();

        }else {
            this.discountAmt=this.subtotal-coupon.getCouponAmmnt();
            calculatetTotal();

        }
    }
}
