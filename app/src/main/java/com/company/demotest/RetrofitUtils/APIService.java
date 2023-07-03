package com.company.demotest.RetrofitUtils;

import com.company.demotest.Models.Product;
import com.company.demotest.Models.ProductList;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIService {


    @POST("products/")
    Call<JsonObject> getProducts(@Body JsonObject jsonObject);
}
