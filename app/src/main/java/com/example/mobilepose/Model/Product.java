package com.example.mobilepose.Model;

import android.content.Context;

import com.example.mobilepose.Model.API.APICallback;
import com.example.mobilepose.Model.API.APIInterface;
import com.example.mobilepose.Model.API.Entities.CategoryResponse;
import com.example.mobilepose.Model.API.Entities.FetchProductResponse;
import com.example.mobilepose.Model.API.Entities.ResponseBase;
import com.example.mobilepose.Model.API.POSAPISingleton;
import com.example.mobilepose.Model.Adapters.CategoriesCallback;
import com.example.mobilepose.Model.Adapters.ProductCallback;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class Product {
    private int productID;
    private String productName;
    private float productPrice;
    private String productDescription;
    private int productCategory;

    private String productImage;

    public int getProductID() {
        return productID;
    }

    public String getProductName() {
        return productName;
    }
    public float getProductPrice() {
        return productPrice;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public int getProductCategory() {
        return productCategory;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setProductPrice(float productPrice) {
        this.productPrice = productPrice;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public void setProductCategory(int productCategory) {
        this.productCategory = productCategory;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public Product(int productID, String productName, float productPrice, String productDescription, int productCategory, String productImage) {
        this.productID = productID;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productDescription = productDescription;
        this.productCategory = productCategory;
        this.productImage = productImage;
    }

    public static void getProducts(String category, ProductCallback callback) {
        List<Product> products = new ArrayList<>();


        APIInterface api = POSAPISingleton.getOrCreateInstance();
        Call<ResponseBase<FetchProductResponse[]>> prod = api.GetProducts(category); // 0, 1, 2, or null
        prod.enqueue(new APICallback<>(
                response -> {
                    FetchProductResponse[] productArray = response;
                    for (FetchProductResponse fetchProductResponse : productArray) {
                        Product newProduct = new Product(
                                fetchProductResponse.productId,
                                fetchProductResponse.productName,
                                fetchProductResponse.price,
                                fetchProductResponse.productDescription,
                                fetchProductResponse.productCategory,
                                fetchProductResponse.productImage
                        );
                        products.add(newProduct);
                    }
                    callback.onProductsFetched(products);
                },
                error -> {
                    callback.onError(error);
                }
        ));
    }


    public static void addProducts(Product product,Context context) {

        APIInterface api = POSAPISingleton.getOrCreateInstance();
        Call<ResponseBase<Void>> prod = api.PostProducts(product.getProductName(),
                product.getProductDescription(),
                String.valueOf(product.getProductPrice()),
                String.valueOf(product.getProductCategory()),
                product.getProductImage(),
                String.valueOf(product.getProductID()));

        prod.enqueue(new APICallback<>(
                response -> {

                },
                error -> {

                }
        ));
    }

    public static void deleteProduct(Product product,Context context) {

        APIInterface api = POSAPISingleton.getOrCreateInstance();
        Call<ResponseBase<Void>> prod = api.DeleteProducts(product.getProductID());

        prod.enqueue(new APICallback<>(
                response -> {

                },
                error -> {

                }
        ));
    }

    public static void updateProduct(Product product,Context context) {
        APIInterface api = POSAPISingleton.getOrCreateInstance();
        Call<ResponseBase<Void>> prod = api.UpdateProducts(product.getProductID(),
                product.getProductName(),
                product.getProductDescription(),
                product.getProductPrice(),
                product.getProductCategory(),
                "",
                "",
                1);
        ;
        prod.enqueue(new APICallback<>(
                response -> {

                },
                error -> {

                }
        ));
    }

    public static void addCategories(String category, Context context){
        APIInterface api = POSAPISingleton.getOrCreateInstance();
        Call<ResponseBase<Void>> prod = api.PostCategory(category);

        prod.enqueue(new APICallback<>(
                response -> {

                },
                error -> {

                }
        ));

    }

    public static List<Category> getCategories(CategoriesCallback callback){
        List<Category> categories = new ArrayList<>();

        APIInterface api= POSAPISingleton.getOrCreateInstance();
        Call<ResponseBase<CategoryResponse[]>> cat = api.GetCategory(0);
        cat.enqueue(new APICallback<>(
                response ->
                {
                    CategoryResponse[] categoryArray = response;
                    for (CategoryResponse categoryResponse : categoryArray) {
                        Category category= new Category(categoryResponse.categoryId,categoryResponse.categoryName);
                        categories.add(category);

                    }
                    callback.onProductsFetched(categories);

                },
                error ->
                {
                    callback.onError(error);

                }
        ));

        return categories;

    }




}

