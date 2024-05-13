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
    final String IP="http://192.168.1.13/";
    private DatabaseSingle(){}

    public static DatabaseSingle getInstance(){
        if(instance==null){
            instance=new DatabaseSingle();
        }
        return instance;
    }

    public void CheckLoginInfo(String username, String password, String URL, Context context, final VolleyCallback callback){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
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
                        Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", username);
                params.put("password", password);
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

    public void ManageDatabaseObject(String URL, Context context, final VolleyCallback callback, Map<String, String> params){
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

    public void CreateUserInfo(String username, String password, String name, String type, String contact, String address, String status,String image, String URL, Context context, final VolleyCallback callback){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
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
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", username);
                params.put("password", password);
                params.put("name", name);
                params.put("type", type);
                params.put("contact", contact);
                params.put("address", address);
                params.put("status", status);
                params.put("image", image);
                return params;
            }
        };
        rQueue = Volley.newRequestQueue(context);
        rQueue.add(stringRequest);
    }

    public void SearchUserInfo(String username, String URL, Context context,final VolleyCallback callback){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
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
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", username);
                return params;
            }
        };
        rQueue = Volley.newRequestQueue(context);
        rQueue.add(stringRequest);
    }

    public void UpdateUserInfo(String username, String URL, Context context,final VolleyCallback callback){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
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
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", username);
                return params;
            }
        };
        rQueue = Volley.newRequestQueue(context);
        rQueue.add(stringRequest);
    }









    public void CreateProductInfo(String username, String password, String name, String type, String contact, String address, String status,String image, String URL, Context context, final VolleyCallback callback){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
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
                Map<String, String> params = new HashMap<String, String>();
                params.put("productname", username);
                params.put("password", password);
                params.put("name", name);
                params.put("type", type);
                params.put("contact", contact);
                params.put("address", address);
                params.put("status", status);
                params.put("image", image);
                return params;
            }
        };
        rQueue = Volley.newRequestQueue(context);
        rQueue.add(stringRequest);
    }

    public void SearchProductInfo(String username, String URL, Context context,final VolleyCallback callback){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
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
                Map<String, String> params = new HashMap<String, String>();
                params.put("productname", username);
                return params;
            }
        };
        rQueue = Volley.newRequestQueue(context);
        rQueue.add(stringRequest);
    }

    public void UpdateProductInfo(String username, String URL, Context context,final VolleyCallback callback){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
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
                Map<String, String> params = new HashMap<String, String>();
                params.put("productname", username);
                return params;
            }
        };
        rQueue = Volley.newRequestQueue(context);
        rQueue.add(stringRequest);
    }





}
