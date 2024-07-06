package com.example.mobilepose.Model.API;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class POSAPISingleton {
    private static APIInterface apiInstance = null;
    final static String API_HOST ="https://blimpish-spray.000webhostapp.com/";


    //192.168.254.108
    public static APIInterface getOrCreateInstance() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        if(apiInstance == null)
        {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(API_HOST)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();

            apiInstance = retrofit.create(APIInterface.class);
        }

        return apiInstance;
    }
}
