package com.example.mobilepose.Model;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DatabaseSingle {
    private RequestQueue rQueue;
    public static DatabaseSingle instance;
    final String IP="http://192.168.2.203/";
    private DatabaseSingle(){}

    public static DatabaseSingle getInstance(){
        if(instance==null){
            instance=new DatabaseSingle();
        }
        return instance;
    }

    public void ManageDatabaseObject(String URL, Context context, final VolleyCallback callback, Map<String, String> params){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, IP+URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        rQueue.getCache().clear();
                        callback.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onError(error.getMessage());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                return params;
            }
        };
        rQueue = Volley.newRequestQueue(context);
        rQueue.add(stringRequest);
    }

    public void ManageDatabaseArray(String URL, Context context, final VolleyCallback callback, Map<String, String> params){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, IP+URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        rQueue.getCache().clear();
                        callback.onSearchSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onError(error.getMessage());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                return params;
            }
        };
        rQueue = Volley.newRequestQueue(context);
        rQueue.add(stringRequest);
    }
}
