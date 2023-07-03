package com.company.demotest.LocalDataBase;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.company.demotest.Models.Product;


@Database(entities = {Product.class},version = 2)
public abstract class ProductDataBase extends RoomDatabase {

    private static final String DATABASE_NAME ="MobileDataBase";

    public abstract ProductDao productDao();

    private  static volatile ProductDataBase INSTANCE;

    public static ProductDataBase getInstance(Context context){
        if (INSTANCE == null){
            synchronized (ProductDataBase.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context,ProductDataBase.class,DATABASE_NAME).addCallback(callback).fallbackToDestructiveMigration().build();

                }
            }
        }
        return INSTANCE;
    }

    static Callback callback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new AsynTaskProduct(INSTANCE);
        }
    };

    static class AsynTaskProduct extends AsyncTask<Void,Void,Void>{

        private ProductDao productDao;
        AsynTaskProduct(ProductDataBase productDataBase){
            productDao = productDataBase.productDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            productDao.deleteAll();
            return null;
        }
    }


}
