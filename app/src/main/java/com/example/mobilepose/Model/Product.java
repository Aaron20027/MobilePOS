package com.example.mobilepose.Model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;

import com.example.mobilepose.Model.API.APICallback;
import com.example.mobilepose.Model.API.APIInterface;
import com.example.mobilepose.Model.API.Entities.CategoryResponse;
import com.example.mobilepose.Model.API.Entities.FetchProductResponse;
import com.example.mobilepose.Model.API.Entities.ResponseBase;
import com.example.mobilepose.Model.API.POSAPISingleton;
import com.example.mobilepose.Model.Callbacks.CategoriesCallback;
import com.example.mobilepose.Model.Callbacks.ProductCallback;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;

public class Product {
    private int productID;
    private String productName;
    private float productPrice;
    private String productDescription;
    private int productCategory;
    private int productAvailability;
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

    public int getProductAvailability() {
        return productAvailability;
    }

    public String getProductAvailability(int availability) {
        if (availability == 0) {
            return "Available";
        } else {
            return "Unavailable";
        }
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

    public Product(int productID, String productName, float productPrice, String productDescription, int productCategory, String productImage, int productAvailability) {
        this.productID = productID;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productDescription = productDescription;
        this.productCategory = productCategory;
        this.productImage = productImage;
        this.productAvailability = productAvailability;

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
                               "",
                                fetchProductResponse.available

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


    public static void addProducts(Product product, Context context) {

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



    public static void deleteProduct(Product product, Context context) {

        APIInterface api = POSAPISingleton.getOrCreateInstance();
        Call<ResponseBase<Void>> prod = api.DeleteProducts(product.getProductID());

        prod.enqueue(new APICallback<>(
                response -> {

                },
                error -> {

                }
        ));
    }

    public static void updateProduct(Product product, Context context) {
        APIInterface api = POSAPISingleton.getOrCreateInstance();
        Call<ResponseBase<Void>> prod = api.UpdateProducts(product.getProductID(),
                product.getProductName(),
                product.getProductDescription(),
                product.getProductPrice(),
                product.getProductCategory(),
                product.getProductImage(),
                "",
                product.getProductAvailability());
        ;
        prod.enqueue(new APICallback<>(
                response -> {

                },
                error -> {

                }
        ));
    }

    public static void addCategories(String category, Context context) {
        APIInterface api = POSAPISingleton.getOrCreateInstance();
        Call<ResponseBase<Void>> prod = api.PostCategory(category);

        prod.enqueue(new APICallback<>(
                response -> {

                },
                error -> {

                }
        ));

    }

    public static List<Category> getCategories(CategoriesCallback callback) {
        List<Category> categories = new ArrayList<>();

        APIInterface api = POSAPISingleton.getOrCreateInstance();
        Call<ResponseBase<CategoryResponse[]>> cat = api.GetCategory(0);
        cat.enqueue(new APICallback<>(
                response ->
                {
                    CategoryResponse[] categoryArray = response;
                    for (CategoryResponse categoryResponse : categoryArray) {
                        Category category = new Category(categoryResponse.categoryId, categoryResponse.categoryName);
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

    public static Bitmap decodeImage(String imgString) {
        byte[] bytes = Base64.decode(imgString, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        return bitmap;
    }

    public static String encodeImage(Context context, Uri uriImage) {
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uriImage);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] bytes = stream.toByteArray();
            String sImage = Base64.encodeToString(bytes, Base64.DEFAULT);
            return sImage;
        } catch (IOException e) {
            e.printStackTrace();

        }
        return "None";

    }




}

