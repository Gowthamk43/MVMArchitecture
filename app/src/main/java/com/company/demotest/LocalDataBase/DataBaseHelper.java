package com.company.demotest.LocalDataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.company.demotest.Models.CartDetails;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {


    public static final String SERVICE_TABLE = "SERVICE_TABLE";
    public static final String COLUMN_PRODUCT_ID = "ProdectId";
    public static final String COLUMN_PRODUCT_NAME = "ProDuctName";
    public static final String COLUMN_PRODUCT_PRICE = "ProductPrice";
    public static final String COLUMN_PRODUCT_QTY = "ProductQty";
    public static final String COLUMN_TOTAL_PRICE = "ColumnTotal";

    public DataBaseHelper(@Nullable Context context) {
        super(context, SERVICE_TABLE + ".db", null,  1);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {


        String createTable = "CREATE TABLE " + SERVICE_TABLE + " (ID INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_PRODUCT_ID + " TEXT," + COLUMN_PRODUCT_NAME + " Text," + COLUMN_PRODUCT_PRICE + " INT," + COLUMN_PRODUCT_QTY + " INT," + COLUMN_TOTAL_PRICE + " INT)";
        sqLiteDatabase.execSQL(createTable);
    }

    public boolean add(CartDetails cartDetails){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_PRODUCT_ID,cartDetails.getProductId());
        contentValues.put(COLUMN_PRODUCT_NAME,cartDetails.getProductName());
        contentValues.put(COLUMN_PRODUCT_PRICE,cartDetails.getProductPrice());
        contentValues.put(COLUMN_PRODUCT_QTY,cartDetails.getProductQty());
        contentValues.put(COLUMN_TOTAL_PRICE,cartDetails.getTotalPrice());
        System.out.println("database running After qty Set" + contentValues.get(COLUMN_PRODUCT_ID));
        long insert = database.insert(SERVICE_TABLE,null,contentValues);

        if (insert == -1){
            return false;
        }else {
            return true;
        }
    }

    public List<CartDetails>getCart(){
        List<CartDetails> get = new ArrayList<>();
        String dbData = "Select * From "+SERVICE_TABLE;
        SQLiteDatabase view = this.getReadableDatabase();
        Cursor cursor = view.rawQuery(dbData,null);

        if (cursor.moveToFirst()){
            do {
                int serialNum = cursor.getInt(0);
                String productID = cursor.getString(1);
                String productName = cursor.getString(2);
                int productPrice = cursor.getInt(3);
                int productQty = cursor.getInt(4);
                int productTotalPrice = cursor.getInt(5);

                CartDetails cartDetails = new CartDetails();
                cartDetails.setProductId(productID);
                cartDetails.setProductName(productName);
                cartDetails.setProductPrice(productPrice);
                cartDetails.setProductQty(productQty);
                cartDetails.setTotalPrice(productTotalPrice);

                get.add(cartDetails);
            }while (cursor.moveToNext());
        }else {

        }
        cursor.close();
        view.close();
        return get;
    }

    public void deleteTableF(String SERVICE_TABLE) {

        String selectQuery = "DELETE  FROM " + SERVICE_TABLE;

        SQLiteDatabase db= this.getWritableDatabase();

        db.execSQL(selectQuery);
        //db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = " + SERVICE_TABLE);
        db.execSQL("DELETE FROM  SQLITE_SEQUENCE  WHERE NAME = '" + SERVICE_TABLE + "'");

        db.close();
    }

    public void deleteItem(String COLUMN_PRODUCT_ID){


        SQLiteDatabase db = this.getWritableDatabase();
//        db.execSQL("DELETE FROM " + "SERVICE_TABLE" + " WHERE " + COLUMN_ID + "=\"" + COLUMN_ID + "\";" );
        db.execSQL("DELETE FROM " + SERVICE_TABLE + " WHERE " + "ProdectId" + "= '" + COLUMN_PRODUCT_ID + "'");
        // database.execSQL("DELETE FROM " + TABLE_NAME + " WHERE " + CONTACTS_COLUMN_TITLE + "= '" + title + "'");
        System.out.println("serviceeeeIdddd" + COLUMN_PRODUCT_ID );
        db.close();


    }

    public void qtyUpdate(String COLUMN_PRODUCT_QTY ,String COLUMN_PRODUCT_ID,String COLUMN_TOTAL_PRICE){
        SQLiteDatabase db = this.getWritableDatabase();


        System.out.println(" --------qty--------    " + COLUMN_PRODUCT_QTY + "   ---serId--  " + COLUMN_PRODUCT_ID +" ---------price-----    " + COLUMN_TOTAL_PRICE);

        db.execSQL("UPDATE " + SERVICE_TABLE + " SET " + "ProductQty" +  "= '" + COLUMN_PRODUCT_QTY + "'" +","+ "ColumnTotal" +  "= '" + COLUMN_TOTAL_PRICE + "'"+ "WHERE " + "ProdectId" + "= '" + COLUMN_PRODUCT_ID + "'");

        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public Cursor getData(String  COLUMN_PRODUCT_ID) {
        System.out.println("From DataBase Helper : " + COLUMN_PRODUCT_ID);
        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor res =  db.rawQuery( "SELECT * FROM SERVICE_TABLE WHERE SERVICE_ID ="+COLUMN_SERVICE_ID+"", null );
        Cursor res =  db.rawQuery( "SELECT * FROM SERVICE_TABLE WHERE ProdectId =?",new String[]{COLUMN_PRODUCT_ID});

        return res;
    }


}
