package com.example.mobilepose.Model.API;

import com.example.mobilepose.Model.API.Entities.ResponseBase;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class APICallback<T> implements Callback<ResponseBase<T>> {
    private final SuccessLambda<T> successLambda;
    private final ErrorLambda errorLambda;

    public APICallback(SuccessLambda<T> s, ErrorLambda e) {
        this.successLambda = s;
        this.errorLambda = e;
    }

    @Override
    public void onResponse(Call<ResponseBase<T>> call, Response<ResponseBase<T>> response) {
        if (!response.isSuccessful())
        {
            this.errorLambda.onError(new Exception("Unknown error."));
            return;
        }

        ResponseBase<T> apiResp = response.body();
        if (!apiResp.success)
        {
            this.errorLambda.onError(new Exception(apiResp.message));
            return;
        }

        this.successLambda.onSuccess(apiResp.data);
    }

    @Override
    public void onFailure(Call<ResponseBase<T>> call, Throwable t) {
        this.errorLambda.onError(t);
    }
}
