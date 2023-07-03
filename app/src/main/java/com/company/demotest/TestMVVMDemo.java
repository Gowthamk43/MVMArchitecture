package com.company.demotest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.company.demotest.Adapter.ListViewAdapter;
import com.company.demotest.Adapter.ProductViewModel;
import com.company.demotest.Adapter.RecyclerAdapter;
import com.company.demotest.LocalDataBase.ProductDao;
import com.company.demotest.LocalDataBase.ProductRespositry;
import com.company.demotest.Models.CartDetails;
import com.company.demotest.Models.Product;
import com.company.demotest.Models.ProductList;
import com.company.demotest.RetrofitUtils.APIService;
import com.company.demotest.RetrofitUtils.RetroInstance;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TestMVVMDemo extends AppCompatActivity {

    private ProductViewModel productViewModel;

    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;
    ListViewAdapter listViewAdapter;
    List<Product>products;
    ProductRespositry productRespositry;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_mvvmdemo);
        recyclerView = findViewById(R.id.recyclerViewAll);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.hasFixedSize();
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        productRespositry = new ProductRespositry(getApplication());

        products = new ArrayList<>();
        listViewAdapter = new ListViewAdapter(products,this);

        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);

        apiCall();
        productViewModel.getGetAllProduct().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {

                listViewAdapter.getAllMobile(products);
                recyclerView.setAdapter(listViewAdapter);
                Log.e("TAG", "onChanged: " + products );
            }
        });


        getDataFromAPI();
    }

    public void apiCall(){

        System.out.println("ApiCallllll");
        APIService apiService = RetroInstance.getRetroClient().create(APIService.class);
        JsonObject jsonObject = new JsonObject();
        Call<JsonObject>call = apiService.getProducts(jsonObject);

        System.out.println("Caaaaaaaa : " +call.request());
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                System.out.println("apicallllllll: "+response.body().toString());
                Gson gson = new Gson();
                ProductList productList = gson.fromJson(response.body(),ProductList.class);

                List<Product>products11=new ArrayList<>();
                for (Product p: productList.getProducts()
                     ) {
                    products11.add(p);
                }
                productRespositry.insert(products11);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                System.out.println("erroirrrrr : " + t.getMessage());
            }
        });

       /* call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {

                System.out.println("apicallllllll: "+response.body().toString());
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                System.out.println("erroirrrrr : " + t.getMessage());

            }
        });*/

    }

    private void getDataFromAPI() {

        String url = "https://dummyjson.com/products/";


        System.out.println("url print Increment :  " + url);



        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                System.out.println("responseeeee : " + response.toString());



            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("responseeeeeeeeeeeeeeeeeeeeeee : _+ " + error.toString());

            }
        });


        RequestQueue requestQueue = Volley.newRequestQueue(TestMVVMDemo.this);
        requestQueue.add(stringRequest);
    }
}