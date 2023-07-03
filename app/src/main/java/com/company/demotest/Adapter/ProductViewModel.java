package com.company.demotest.Adapter;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.company.demotest.LocalDataBase.ProductDao;
import com.company.demotest.LocalDataBase.ProductDataBase;
import com.company.demotest.LocalDataBase.ProductRespositry;
import com.company.demotest.Models.Product;

import java.util.List;

public class ProductViewModel extends AndroidViewModel {

    private ProductRespositry productRespositry;
    private LiveData<List<Product>> getAllProduct;
    public ProductViewModel(@NonNull Application application) {
        super(application);

        productRespositry = new ProductRespositry(application);

        getAllProduct = productRespositry.getGetAllProduct();
    }

    public void insert(List<Product>list){
      productRespositry.insert(list);
    }

    public LiveData<List<Product>>getGetAllProduct(){
        return getAllProduct;
    }
}
