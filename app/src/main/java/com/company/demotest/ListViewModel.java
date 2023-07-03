package com.company.demotest;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.company.demotest.Models.Product;
import com.company.demotest.Models.ProductList;
import com.company.demotest.RetrofitUtils.APIService;
import com.company.demotest.RetrofitUtils.RetroInstance;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListViewModel extends ViewModel {
    private MutableLiveData<List<Product>> productList;

    public ListViewModel(){
        productList = new MutableLiveData<>();
    }

    public MutableLiveData<List<Product>> getMoviesListObserver() {
        return productList;

    }

    public void makeApiCall() {
        APIService apiService = RetroInstance.getRetroClient().create(APIService.class);
//        Call<List<Product>> call = apiService.getProducts();

//        Call<List<Product>>call = apiService.getProducts();
     /*   call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                productList.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                productList.postValue(null);
            }
        });*/
    }


}
