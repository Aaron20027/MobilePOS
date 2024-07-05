package com.example.mobilepose;

import com.example.mobilepose.Model.Product;

public class Order {
    private int id;
    private Product product;
    private int quantity;
    private double orderTotal;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getOrderTotal() {
        return product.getProductPrice()*quantity;
    }


    public Order(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
        this.orderTotal = product.getProductPrice()*quantity;
    }
}
