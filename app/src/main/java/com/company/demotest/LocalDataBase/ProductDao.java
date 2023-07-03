package com.company.demotest.LocalDataBase;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.company.demotest.Models.Product;

import java.util.List;

import retrofit2.http.DELETE;

@Dao
public interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Product>products);

    @Query("SELECT * FROM mobiles")
    LiveData<List<Product>> getAllProduct();
    @Query("DELETE FROM mobiles")
    void deleteAll();
}
