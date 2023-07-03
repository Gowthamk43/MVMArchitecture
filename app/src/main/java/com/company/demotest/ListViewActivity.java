package com.company.demotest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.company.demotest.Adapter.RecyclerAdapter;
import com.company.demotest.LocalDataBase.DataBaseHelper;
import com.company.demotest.Models.CartDetails;
import com.company.demotest.Models.Product;
import com.company.demotest.Models.ProductList;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ListViewActivity extends AppCompatActivity {


    List<Product> productListList;

    RecyclerView recyclerView;
    ListViewModel viewModel;
    int skip = 0;
    int limit = 10;
    CartDetails cd = new CartDetails();
    RecyclerAdapter recyclerAdapter;
    int[] count;

    String id, name, getSerIdCheck, getCartQty;
    int price, cartSize, serviceTotal, newAddQty, newAddPrice, newPrice, newQty;
    ConstraintLayout constraintLayout;
    TextView tvPrice, tvItems, tvNext;

    List<CartDetails> cart;
    List<CartDetails> viewCart;
    String lastUrlCall;

    ProgressBar loadingPB;
    NestedScrollView nestedSV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        recyclerView = findViewById(R.id.recyclerViewList);
        constraintLayout = findViewById(R.id.bottomConstrainLayout);
        tvPrice = findViewById(R.id.textViewProductPrice);
        tvItems = findViewById(R.id.textViewItems);
        tvNext = findViewById(R.id.textViewNext);
        loadingPB = findViewById(R.id.idPBLoading);
        nestedSV = findViewById(R.id.idNestedSV);

        getDataFromAPI(limit, skip);

        loadDb();
//        getDataFromAPI();


        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListViewActivity.this, CartListViewActivity.class);
                startActivity(intent);
            }
        });
        nestedSV.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                // on scroll change we are checking when users scroll as bottom.
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    // in this method we are incrementing page number,
                    // making progress bar visible and calling get data method.
                    skip++;
                    loadingPB.setVisibility(View.VISIBLE);
                    getDataFromAPI(limit, skip);
                }
            }
        });


    }

//    private void getDataFromAPI() {

    private void getDataFromAPI(int limit, int skip) {



        if (skip > limit) {
            // checking if the page number is greater than limit.
            // displaying toast message in this case when page>limit.
            Toast.makeText(this, "That's all the data..", Toast.LENGTH_SHORT).show();

//            System.out.println("Skip : " + skip + "limit"+limit);

            System.out.println("limit"+limit +"skip :" +skip);

            loadingPB.setVisibility(View.GONE);
            return;
        }

        String url = "https://dummyjson.com/products?limit="+limit+"&skip="+skip;


        System.out.println("url print Increment :  " + url);
        lastUrlCall = url;


        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                System.out.println("responseeeee : " + response.toString());


                try {
                    JSONObject object = new JSONObject(String.valueOf(response));
                    String get = object.getString("products");
//                    JSONObject object2 = new JSONObject(get);
                    JSONArray jsonArray = new JSONArray(get);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        System.out.println(jsonObject.getString("id"));

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Gson gson = new Gson();
                ProductList productList = gson.fromJson(String.valueOf(response), ProductList.class);


//                setRecyclerView(productList);




                if (getSerIdCheck != null) {
                    ProductList productList2 = new ProductList();
                    List<Product> products1 = new ArrayList<>();

                    for (Product kkk : productList.getProducts()
                    ) {
                        for (CartDetails c : cart
                        ) {
                            System.out.println("Q12222211222P cartttt : " + c.getProductId());
                            if (Objects.equals(String.valueOf(c.getProductId()), String.valueOf(kkk.getId()))) {
                                System.out.println("carttttttttttttttttyuyuuuii");
                                String cc = String.valueOf(c.getProductQty());
                                kkk.setQty(cc);
                                products1.add(kkk);
                            }

                        }
                        System.out.println("QWERTERTYUUTYUIOP  apiiiiiii : " + kkk.getId());

                    }
                    System.out.println("Women Serviceeeeee list   sizeeee: " + products1.size());
//                        count = saloonData2.size();
                    productList2.setProducts(products1);
                } else {
                    System.out.println("  issssss  nulllllllllll");
                }

                ProductList productList1 = new ProductList();
                List<Product> products = new ArrayList<>();
                for (Product pro : productList.getProducts()
                ) {
                    products.add(pro);
                }
                productList1.setProducts(products);

                count = new int[products.size()];
                System.out.println("countttttttttttttttttttttttt :" + Arrays.toString(count));


                loadingPB.setVisibility(View.GONE);
                setRecyclerView(productList1);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("responseeeeeeeeeeeeeeeeeeeeeee : _+ " + error.toString());

            }
        });


        RequestQueue requestQueue = Volley.newRequestQueue(ListViewActivity.this);
        requestQueue.add(stringRequest);
    }

    private void setRecyclerView(ProductList productList) {

        GridLayoutManager gridLayoutManager = new GridLayoutManager(ListViewActivity.this, 1);

        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerAdapter = new RecyclerAdapter(productList.getProducts(), ListViewActivity.this, count, new RecyclerAdapter.ItemSelectionClickListener() {
            @Override
            public void onItemClick(Product product) {

            }

            @Override
            public void onButtonClick(int position) {


                System.out.println("onADD Button Click");

                Product product = productList.getProducts().get(position);

                int qty = 1;
                id = String.valueOf(product.getId());
                name = product.getTitle();
                price = product.getPrice();
                cd.setProductId(id);
                cd.setProductName(name);
                cd.setProductPrice(price);
                cd.setTotalPrice(price);
                cd.setProductQty(qty);

                DataBaseHelper dataBaseHelper = new DataBaseHelper(ListViewActivity.this);
                dataBaseHelper.add(cd);
                recyclerAdapter.notifyDataSetChanged();
                loadDb();

                constraintLayout.setVisibility(View.VISIBLE);


            }

            @Override
            public void addSer(int position) {

                SharedPreferences sharedPreferences = ListViewActivity.this.getSharedPreferences("service_Details", Context.MODE_PRIVATE);

                String qtyAddTemp = sharedPreferences.getString("serviceQty", "");
                System.out.println("AddddddQtyyyyyyyy" + qtyAddTemp);
                String serAddId = sharedPreferences.getString("serviceId", "");
                System.out.println("Cart value Position" + serAddId);
                String defaultAddPrice = sharedPreferences.getString("servicePrice", "");

                System.out.println("Testing DbPrice  in DB value : " + defaultAddPrice);
//                        String totalPrice = viewCart.get(position).getTotalPrice();
                int qty = Integer.parseInt(qtyAddTemp);


                newAddQty = qty + 1;
                newAddPrice = newAddQty * Integer.parseInt(defaultAddPrice);
                System.out.println("++++++++++++++++++     " + newAddQty);
                System.out.println("*********" + newAddQty + "*" + defaultAddPrice + "************** =====    " + newAddPrice);

                if (newAddQty > 1) {

                    DataBaseHelper dbHelper = new DataBaseHelper(ListViewActivity.this);
                    dbHelper.qtyUpdate(String.valueOf(newAddQty), serAddId, String.valueOf(newAddPrice));

                    recyclerAdapter.notifyDataSetChanged();
                    System.out.println("update the service qty,price,");
                } else {
                    System.out.println(" **************something wrong ********");
                }


//

//                        viewCart.get(position).setTotalPrice(String.valueOf(newAddPrice));
                serviceTotal = 0;
                DataBaseHelper db = new DataBaseHelper(ListViewActivity.this);
                viewCart = db.getCart();
                System.out.println("AddddddQtyyyyyyyy  Carttttt" + viewCart);

                for (CartDetails list : viewCart) {


                    String getPrice = String.valueOf(list.getTotalPrice());

                    System.out.println("____getPrice_____" + getPrice);
                    serviceTotal += Integer.parseInt(getPrice);

                    String getQty = String.valueOf(list.getProductQty());

                    getCartQty = getQty;

                    System.out.println("GetttPriceee" + " " + serviceTotal);


                }
                //  showQty.setText(String.valueOf(getCartQty));
                tvPrice.setText(String.valueOf(serviceTotal));

            }

            @Override
            public void minusSer(int position) {

                SharedPreferences sharedPreferencesLoc = ListViewActivity.this.getSharedPreferences("minus", Context.MODE_PRIVATE);
                String decQty = sharedPreferencesLoc.getString("minus", "");
                int minusI = Integer.parseInt(decQty);
                System.out.println("Entering into adapter : " + minusI);
                loadDb();


                SharedPreferences sharedPreferences = ListViewActivity.this.getSharedPreferences("service_Details", Context.MODE_PRIVATE);
                String qtyTemp = sharedPreferences.getString("serviceQty", "");
                System.out.println("&4543236312454566666%%%%5");
                String serId = sharedPreferences.getString("serviceId", "");
                String defaultPrice = sharedPreferences.getString("servicePrice", "");
//                            String totalPrice = viewCart.get(position).getTotalPrice();
                Log.e("TAG minus processs", "minusSer: " + qtyTemp + " 0000 " + serId + " 1111111 " + defaultPrice);
                int qty = Integer.parseInt(qtyTemp);
//                if (qty >= 1) {
                newQty = qty - 1;
                newPrice = newQty * Integer.parseInt(defaultPrice);
//                comboAdapter.notifyDataSetChanged();


                if (newQty >= 1) {
//                                viewCart.get(position).setQty(String.valueOf(newQty));
//                                viewCart.get(position).setTotalPrice(String.valueOf(newPrice));


                    DataBaseHelper dbHelper = new DataBaseHelper(ListViewActivity.this);
                    dbHelper.qtyUpdate(String.valueOf(newQty), serId, String.valueOf(newPrice));
                    loadDb();
                    serviceTotal = 0;
                    DataBaseHelper db1 = new DataBaseHelper(ListViewActivity.this);
                    viewCart = db1.getCart();

                    for (CartDetails list : viewCart) {

                        String getPrice = String.valueOf(list.getTotalPrice());
                        serviceTotal += Integer.parseInt(getPrice);
                        String getQty = String.valueOf(list.getProductQty());

                        getCartQty = getQty;
                        System.out.println("GetttPriceee" + " " + serviceTotal);


                    }
                    //  showQty.setText(String.valueOf(getCartQty));
                    tvPrice.setText(String.valueOf(serviceTotal));
                   /* try {
                        if (Objects.equals(shopGender, "Unisex")) {
//                                cartCountUni.setText(String.valueOf(cart.size()));
                            uniTotalPrice.setText(String.valueOf(serviceTotal));
                            Log.e("TAG uniTotalPrice", "loadDb: " + uniTotalPrice);

                        } else {
//                                womenCartCount.setText(String.valueOf(cart.size()));
                            totalMenPrice.setText(String.valueOf(serviceTotal));
                        }


                    } catch (NullPointerException ignored) {

                    }*/
//                    comboAdapter.notifyDataSetChanged();

                    recyclerAdapter.notifyDataSetChanged();
                } else if (newQty == 0) {
                    DataBaseHelper dbHelper1 = new DataBaseHelper(ListViewActivity.this);
                    dbHelper1.deleteItem(serId);
                    System.out.println("  Service Deleted");
                    loadDb();
                    getData();
                    Log.e("TAG ", "minusSer On minus service: ");
                    System.out.println("DBBBBBB CART Size After Delete Item : " + dbHelper1.getCart().size());

                    recyclerAdapter.notifyDataSetChanged();
                    loadDb();
                    if (dbHelper1.getCart().size() == 0) {
                        DataBaseHelper db = new DataBaseHelper(ListViewActivity.this);
                        db.deleteTableF(DataBaseHelper.SERVICE_TABLE);
                        System.out.println("Table was Deleted");
                        getData();
                        constraintLayout.setVisibility(View.GONE);
                       /* if (Objects.equals(shopGender, "Unisex")) {
                            if (Objects.equals(getSalonForCheck, getId)) {
                                conUni.setVisibility(View.INVISIBLE);

                            } else {
                                constraintMen.setVisibility(View.INVISIBLE);

                            }

                        }*/
                        loadDb();

                    } else {
                        System.out.println("After DB size is not Zeroooo");
                        loadDb();

                    }
                } else {
                    System.out.println("On Minus Else Part ");
                }

            }

            @Override
            public void qty(int position) {

            }
        });
        recyclerView.setAdapter(recyclerAdapter);

    }


    public void loadDb() {
//        DataBaseHelper db = new DataBaseHelper(getContext());
//        viewCart = db.getCart();
        DataBaseHelper db = new DataBaseHelper(ListViewActivity.this);
        cart = db.getCart();
        Log.e("TAG load Dbbbbb", "loadDb: " + cart.size());
        cartSize = cart.size();
        System.out.println("Dbbbbbbbbbbbb load cart sizeeeeeeee : " + cartSize);
        if (cart.size() == 0) {
            getSerIdCheck = null;
        } else {
            serviceTotal = 0;
            for (CartDetails list : cart) {

                int getPrice = list.getTotalPrice();
                serviceTotal += getPrice;

                getSerIdCheck = list.getProductId();
//            getETime = list.geteTime();
//            getSTime = list.getSTime();
                Log.e("TAG serviceTotal", "loadDb: " + serviceTotal);
                Log.e("TAG ServiceeeeeeeeeId", "loadDb: " + getSerIdCheck);
                try {
                    //if (Objects.equals(shopGender, "Unisex")) {
                    tvItems.setText(String.valueOf(cart.size()));
                    tvPrice.setText(String.valueOf(serviceTotal));
                    Log.e("TAG uniTotalPrice", "loadDb: " + tvPrice.getText().toString());


                } catch (NullPointerException ignored) {

                }

                if (cart.size() > 1) {
                    tvItems.setText(cartSize + " ITEMS");
                } else {
                    tvItems.setText(cartSize + " ITEM");
                }

                if (cart.size() > 0) {
                    constraintLayout.setVisibility(View.VISIBLE);
                } else {
                    constraintLayout.setVisibility(View.GONE);
                }

            }
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        loadDb();
        getData();
    }

    private void getData() {
       /* if (skip > limit) {
            // checking if the page number is greater than limit.
            // displaying toast message in this case when page>limit.
            Toast.makeText(this, "That's all the data..", Toast.LENGTH_SHORT).show();


            return;
        }*/

        String url = lastUrlCall;


        System.out.println("url print :  " + url);


        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                System.out.println("responseeeee : " + response.toString());
                Gson gson = new Gson();
                ProductList productList = gson.fromJson(String.valueOf(response), ProductList.class);

//                setRecyclerView(productList);


                if (getSerIdCheck != null) {
                    ProductList productList2 = new ProductList();
                    List<Product> products1 = new ArrayList<>();

                    for (Product kkk : productList.getProducts()
                    ) {
                        for (CartDetails c : cart
                        ) {
                            System.out.println("Q12222211222P cartttt : " + c.getProductId());
                            if (Objects.equals(String.valueOf(c.getProductId()), String.valueOf(kkk.getId()))) {
                                System.out.println("carttttttttttttttttyuyuuuii");
                                String cc = String.valueOf(c.getProductQty());
                                kkk.setQty(cc);
                                products1.add(kkk);
                            }

                        }
                        System.out.println("QWERTERTYUUTYUIOP  apiiiiiii : " + kkk.getId());

                    }
                    System.out.println("Women Serviceeeeee list   sizeeee: " + products1.size());
//                        count = saloonData2.size();
                    productList2.setProducts(products1);
                } else {
                    System.out.println("  issssss  nulllllllllll");
                }

                ProductList productList1 = new ProductList();
                List<Product> products = new ArrayList<>();
                for (Product pro : productList.getProducts()
                ) {
                    products.add(pro);
                }
                productList1.setProducts(products);

                count = new int[products.size()];
                System.out.println("countttttttttttttttttttttttt :" + Arrays.toString(count));


                loadingPB.setVisibility(View.GONE);
                setRecyclerView(productList1);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("responseeeeeeeeeeeeeeeeeeeeeee : _+ " + error.toString());

            }
        });


        RequestQueue requestQueue = Volley.newRequestQueue(ListViewActivity.this);
        requestQueue.add(stringRequest);
    }
}