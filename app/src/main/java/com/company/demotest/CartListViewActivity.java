package com.company.demotest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.company.demotest.Adapter.CartAdapter;
import com.company.demotest.LocalDataBase.DataBaseHelper;
import com.company.demotest.Models.CartDetails;

import java.util.List;

public class CartListViewActivity extends AppCompatActivity {


    RecyclerView cartRecyclerView;
    CartAdapter cartAdapter;
    RadioGroup payMode;
    RadioButton payCash, payOnline;
    TextView shopPriceFinal,gst,shopTotPriceFinal;
    Button pay;
    List<CartDetails> viewCart;
    String payment;
    int serviceTotal;
    int newQty;
    int newPrice;

    int newAddQty;
    int newAddPrice,percent,valueMulti;
    String gstValue="10";
    double perValue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_list_view);
        cartRecyclerView = findViewById(R.id.cartRecyclerViewList);
        payMode = findViewById(R.id.radioGroupPay);
        payCash = findViewById(R.id.radioButtonCash);
        payOnline = findViewById(R.id.radioButtonOnline);
        shopPriceFinal = findViewById(R.id.textViewFPrice);
        gst = findViewById(R.id.textViewDisCountPrice);
        shopTotPriceFinal = findViewById(R.id.textViewFTotalPrice);
        pay = findViewById(R.id.buttonPay);
        percent = Integer.parseInt(gstValue);


        DataBaseHelper db = new DataBaseHelper(CartListViewActivity.this);
        viewCart = db.getCart();

        payMode.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.radioButtonCash:
                        payment = "Cash";
                        Log.e("TAG SelGender   Male", "onCreate: " + payment);
                        pay.setText("Check out");
//                        pay.setVisibility(View.VISIBLE);
                        break;

                    case R.id.radioButtonOnline:
                        payment = "Online";
                        Log.e("TAG SelGender   Female", "onCreate: " + payment);
                        pay.setText("Pay Now");
//                        pay.setVisibility(View.VISIBLE);
                        break;

                }
            }
        });

        databaseLoad();



        if (viewCart == null || viewCart.isEmpty()) {

            Toast.makeText(CartListViewActivity.this, "Add Service to continue", Toast.LENGTH_SHORT).show();


            Intent intent = new Intent(CartListViewActivity.this, ListViewActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);

        } else {


//        for (int i =0; i<viewCart.size();i++){
//
//        }
//                        JSONObject object = jA.getJSONObject(i);
//                        String get = object.getString("price");
//                        servicetotal += Integer.parseInt(get);
            System.out.println("Cart List From DB " + viewCart);


            cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            cartAdapter = new CartAdapter(viewCart, this, new CartAdapter.DeleteClickListener() {

                @Override
                public void onDeleteClick(CartDetails cartdetail) {

                }

                @Override
                public void onDeleteButton(int position) {

                    String getSerId = viewCart.get(position).getProductId();

                    DataBaseHelper dbHelper = new DataBaseHelper(CartListViewActivity.this);
                    dbHelper.deleteItem(getSerId);
                    removeAt(position);
                    cartAdapter.notifyDataSetChanged();


                    databaseLoad();
                    reload();

                    serviceTotal = 0;
                    for (CartDetails list : viewCart) {


                        String getPrice = String.valueOf(list.getTotalPrice());

                        System.out.println("____getPrice_____ Delete" + getPrice);
                        serviceTotal += Integer.parseInt(getPrice);

                        System.out.println("GetttPriceeeOnDelete" + " " + serviceTotal);




                    }
                   /* databaseLoad();
                    cartAdapter.notifyDataSetChanged();*/

                    shopPriceFinal.setText(String.valueOf(serviceTotal));
                    shopTotPriceFinal.setText(shopPriceFinal.getText().toString());


                    if (gstValue.equals("null")) {
                        shopTotPriceFinal.setText(shopPriceFinal.getText().toString());

                    } else {
                        reload();
                    }


                }

                @Override
                public void onAddButton(int position) {
                    String qtyAddTemp = String.valueOf(viewCart.get(position).getProductQty());
                    String serAddId = viewCart.get(position).getProductId();
                    String defaultAddPrice = String.valueOf(viewCart.get(position).getProductPrice());
                    String totalPrice = String.valueOf(viewCart.get(position).getTotalPrice());
                    int qty = Integer.parseInt(qtyAddTemp);


                    newAddQty = qty + 1;
                    newAddPrice = newAddQty * Integer.parseInt(defaultAddPrice);
                    System.out.println("++++++++++++++++++     " + newAddQty);
                    System.out.println("*********" + newAddQty + "*" + defaultAddPrice + "************** =====    " + newAddPrice);

                    viewCart.get(position).setProductQty(Integer.parseInt(String.valueOf(newAddQty)));
                    viewCart.get(position).setTotalPrice(Integer.parseInt(String.valueOf(newAddPrice)));
                    serviceTotal = 0;
                    for (CartDetails list : viewCart) {


                        String getPrice = String.valueOf(list.getTotalPrice());

                        System.out.println("____getPrice_____" + getPrice);
                        serviceTotal += Integer.parseInt(getPrice);

                        System.out.println("GetttPriceee" + " " + serviceTotal);


                    }
                    shopPriceFinal.setText(String.valueOf(serviceTotal));
                    shopPriceFinal.setText(String.valueOf(serviceTotal));
                    shopTotPriceFinal.setText(shopPriceFinal.getText().toString());
                    if (gstValue.equals("null")) {
                        shopTotPriceFinal.setText(shopPriceFinal.getText().toString());

                    } else {
                        reload();
                    }
//                        getServiceQuantity.setText(String.valueOf(up));
//                        getServicePrice.setText(String.valueOf(upPrice));
//                        System.out.println("qqqqqqqqqqqqqqqqqqqqqqqqqqq     " + getServiceQuantity.getText().toString());


                    DataBaseHelper dbHelper = new DataBaseHelper(CartListViewActivity.this);
                    dbHelper.qtyUpdate(String.valueOf(newAddQty), serAddId, String.valueOf(newAddPrice));

                    cartAdapter.notifyDataSetChanged();


//                    Log.e("TAG getSerQty", "onClick: " + getQty);


                }

                @Override
                public void onMinusButton(int position) {

                    String qtyTemp = String.valueOf(viewCart.get(position).getProductQty());
                    String serId = viewCart.get(position).getProductId();
                    String defaultPrice = String.valueOf(viewCart.get(position).getProductPrice());
                    String totalPrice = String.valueOf(viewCart.get(position).getTotalPrice());
                    int qty = Integer.parseInt(qtyTemp);
                    if (qty != 1) {
                        newQty = qty - 1;
                        newPrice = newQty * Integer.parseInt(defaultPrice);


                        viewCart.get(position).setProductQty(Integer.parseInt(String.valueOf(newQty)));
                        viewCart.get(position).setTotalPrice(Integer.parseInt(String.valueOf(newPrice)));


                        DataBaseHelper dbHelper = new DataBaseHelper(CartListViewActivity.this);
                        dbHelper.qtyUpdate(String.valueOf(newQty), serId, String.valueOf(newPrice));


                        serviceTotal = 0;

                        for (CartDetails list : viewCart) {

                            String getPrice = String.valueOf(list.getTotalPrice());
                            serviceTotal += Integer.parseInt(getPrice);

                            System.out.println("GetttPriceee" + " " + serviceTotal);


                        }
                        shopPriceFinal.setText(String.valueOf(serviceTotal));
                        shopPriceFinal.setText(String.valueOf(serviceTotal));
                        shopTotPriceFinal.setText(shopPriceFinal.getText().toString());
                        if (gstValue.equals("null")) {
                            shopTotPriceFinal.setText(shopPriceFinal.getText().toString());

                        } else {
                            reload();
                        }
//                        System.out.println(dbHelper);

                        cartAdapter.notifyDataSetChanged();

                    } else {


                    }

                }


            });
            cartRecyclerView.setAdapter(cartAdapter);
        }


    }


    public void databaseLoad() {

        for (CartDetails list : viewCart) {

            String getPrice = String.valueOf(list.getTotalPrice());
            serviceTotal += Integer.parseInt(getPrice);

        }


        try {

            shopPriceFinal.setText(String.valueOf(serviceTotal));

//            shopTotPriceFinal.setText(shopPriceFinal.getText().toString());



        } catch (NullPointerException exception) {

            System.out.println("try  catch is running");
        }

        try {
            reload();
        } catch (NullPointerException e) {
            shopTotPriceFinal.setText(shopPriceFinal.getText().toString());
        }


    }

    public void reload() {


        Log.e("TAffff gstValue", "reload: " + gstValue);

        /*if (!gstValue.equals("") || gstValue!=null) {

            Log.e("TA offerUptoofferUpto", "reload: "+ offerUpto );
            try {
                upto = Integer.parseInt(offerUpto);
                Log.e("TAg offerUpto 2222", "reload: "+ upto );
            }catch (NumberFormatException numberFormatException){

            }




            int getTPrice = Integer.parseInt(shopPriceFinal.getText().toString());
            perValue = ((double) percent) / 100;
            Log.e("TAG getTPrice Minus", "reload: " + getTPrice);
            Log.e("TAG perValue Minus", "reload: " + perValue);
            valueMulti = (int) (perValue * getTPrice);
            int getFoutput;
            if(valueMulti >= upto) {
                getFoutput = getTPrice - upto;
                discountPrice.setText(String.valueOf(upto));
            } else {
                getFoutput = getTPrice - valueMulti;
                discountPrice.setText(String.valueOf(valueMulti));
                Log.e("TAG getFoutput Minus", "reload: " + getFoutput);
            }

            shopTotPriceFinal.setText(String.valueOf(getFoutput));
            Log.e("TAG valueMulti Minus", "reload: " + valueMulti);

            Log.e("TAG Percentage Minus", "reload: " + shopTotPriceFinal);
        } else {


            shopTotPriceFinal.setText(shopPriceFinal.getText().toString());
        }*/


        if (gstValue.equals("null")) {
            shopTotPriceFinal.setText(shopPriceFinal.getText().toString());

        } else {




            int getTPrice = Integer.parseInt(shopPriceFinal.getText().toString());
            perValue = ((double) percent) / 100;
            Log.e("TAG getTPrice Minus", "reload: " + getTPrice);
            Log.e("TAG perValue Minus", "reload: " + perValue);
            valueMulti = (int) (perValue * getTPrice);
            int getFoutput = getTPrice + valueMulti;
            gst.setText(String.valueOf(valueMulti));


            shopTotPriceFinal.setText(String.valueOf(getFoutput));
            Log.e("TAG valueMulti Minus", "reload: " + valueMulti);

            Log.e("TAG Percentage Minus", "reload: " + shopTotPriceFinal);
        }


    }


    public void removeAt(int position) {
        System.out.println("========================  " + position);
        System.out.println("++++++++++++++++++++++++++++++++++++++ " + viewCart);
        viewCart.remove(position);
        cartAdapter.notifyItemRemoved(position);
        cartAdapter.notifyItemRangeChanged(position, viewCart.size());
        cartAdapter.notifyDataSetChanged();
    }


}