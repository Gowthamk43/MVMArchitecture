package com.company.demotest.LocalDataBase;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.company.demotest.Models.Product;

import java.util.List;

public class ProductRespositry {
    private  ProductDataBase dataBase;
    private LiveData<List<Product>>getAllProduct;


    public ProductRespositry(Application application){
        dataBase = ProductDataBase.getInstance(application);
        getAllProduct = dataBase.productDao().getAllProduct();
    }


    public void insert(List<Product>productList){

        new InsertAsynTask(dataBase).execute(productList);

    }

    public LiveData<List<Product>> getGetAllProduct(){
        return getAllProduct;
    }

    class InsertAsynTask extends AsyncTask<List<Product>,Void,Void>{


        private ProductDao productDao;
        InsertAsynTask(ProductDataBase productDataBase){
            productDao = productDataBase.productDao();
        }
        @Override
        protected Void doInBackground(List<Product>... lists) {
            productDao.insert(lists[0]);
            return null;
        }
    }
}
