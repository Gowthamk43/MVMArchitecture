package com.company.demotest.Adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.appbroker.roundedimageview.RoundedImageView;
import com.company.demotest.LocalDataBase.DataBaseHelper;
import com.company.demotest.Models.Product;
import com.company.demotest.R;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.personViewHolder> {


    public int[] count;
    int minusI;
    String  getMisSer;
//    List<Cartdetail> viewCart;
    String getId, userEmail, getSTime, getETime, getSalonId, shopName;


    List<Product> products;
//    ArrayList<CartModel> cartModels = new ArrayList<CartModel>();
    int serviceTotal;
    //    CartModel cartModel;
//    private  List<CartModel> cartModels;
    Context context;

    ItemSelectionClickListener itemSelectionClickListener;
    //    List<TableBookingList> cartList ;
    final static String sh = "asdfgh";
//    private CallBackUs mCallBackus;
//    private HomeCallBack homeCallBack;


    public RecyclerAdapter(List<Product> products, Context context, int[] count, ItemSelectionClickListener itemSelectionClickListener) {
        this.products = products;
        this.context = context;
        this.itemSelectionClickListener = itemSelectionClickListener;
        this.count = count;

    }


//    public RecyclerAdapter(List<SaloonDatum> personList, Context context, ItemSelectionClickListener itemSelectionClickListener,int[] count) {
//        this.personList = personList;
//        this.context = context;
//        this.itemSelectionClickListener = itemSelectionClickListener;
//        this.count = count;
//    }


    @NonNull
    @Override
    public personViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list_item,parent,false);

        return  new personViewHolder(view);

    }


    @Override
    public void onBindViewHolder(@NonNull personViewHolder holder, @SuppressLint("RecyclerView") int position) {



        holder.itemView.setOnClickListener(view -> {
            itemSelectionClickListener.onItemClick(products.get(position));
        });
        String serviceList = products.get(position).getTitle();
        String cateList = products.get(position).getCategory();
        String priceList = String.valueOf(products.get(position).getPrice());
        String qty = products.get(position).getQty();
        System.out.println("From apiiiiiiiiiiiiiiiiiiiiiii Qtyqqqqqqqqqqqqqqqqq : " + qty);






//        holder.cate.setText(cateList);
        holder.personName.setText(serviceList);
        holder.price.setText(priceList);
        if ( qty == null ||qty.equals("") ||qty.equals("null")) {
            holder.showQty.setText("0");
        }else {
            holder.showQty.setText(qty);
        }

        if (holder.getCountText().getText().toString().equals("0") || holder.getCountText().getText().toString().equals("")){
            holder.add.setVisibility(View.VISIBLE);
            holder.buttonLayout.setVisibility(View.INVISIBLE);
        }else {
            holder.add.setVisibility(View.INVISIBLE);
            holder.buttonLayout.setVisibility(View.VISIBLE);

        }

        Log.d("TAG  ====== Service", "onBindViewHolder: " + serviceList);
        Log.d("TAG  ====== Cate", "onBindViewHolder: " + cateList);

        if (count[position] > 0) {
            holder.getCountText().setText(String.valueOf(count[position]));
            holder.getAddButton().setVisibility(View.GONE);
            holder.getLl().setVisibility(View.VISIBLE);
        }

        holder.getPlusButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Checking Position : " + position);
                System.out.println("Service Id for Plus : " + products.get(position).getId());
                String serviceId = String.valueOf(products.get(position).getId());

                DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
                Cursor cursor = dataBaseHelper.getData(serviceId);
                cursor.moveToFirst();
                @SuppressLint("Range") String qty = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_PRODUCT_QTY));
                @SuppressLint("Range") String price = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_PRODUCT_PRICE));
                System.out.println("DataBase Qty  : " + qty);
                SharedPreferences sharedPreferences = context.getSharedPreferences("service_Details",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("serviceId",serviceId);
                editor.putString("serviceQty",qty);
                editor.putString("servicePrice",price);
                editor.apply();

               int co = Integer.parseInt(holder.getCountText().getText().toString());

               int plus = co+1;


                System.out.println("adapter Plus qtryyyyyyyy : "+ plus);
                count[position] = plus;
                System.out.println("Counytyttt : " + position);
            //    holder.showQty.setText(String.valueOf(plus));
              //  notifyDataSetChanged();
                notifyItemChanged(position);
                System.out.println("adapter Plus qtryyyyyyyy 1111111111111 : " +holder.showQty.getText().toString());
                itemSelectionClickListener.addSer(position);



            }
        });

        holder.getAddButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count[position]++;
                System.out.println("Service Id for Add : " + products.get(position).getId());
                holder.getCountText().setText(String.valueOf(count[position]));
                holder.getAddButton().setVisibility(View.GONE);
                holder.getLl().setVisibility(View.VISIBLE);
                itemSelectionClickListener.onButtonClick(position);
                System.out.println("On if processss : " + count[position]);
            }
        });

        holder.getMinusButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Service Id for Minus : " + products.get(position).getId());
//                System.out.println("Service Id for Minus : " + products.get(position).getId().length());
                System.out.println("Test 1");
                System.out.println("    --------------   " + count[position]);

                int co = Integer.parseInt(holder.getCountText().getText().toString());

                int plus = co-1;
                count[position] = plus;
                minusI =count[position];
                notifyItemChanged(position);
                System.out.println("decerement value  :"  +minusI);
                getMisSer = String.valueOf(products.get(position).getId());
                System.out.println("minussssss dddd  :"  +getMisSer + getMisSer.length());
                notifyItemChanged(position);
                DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
                Cursor cursor = dataBaseHelper.getData(getMisSer);
                cursor.moveToFirst();
                @SuppressLint("Range") String qty = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_PRODUCT_QTY));
                @SuppressLint("Range") String price = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_PRODUCT_PRICE));
                System.out.println("DataBase Qty  : " + qty);

                SharedPreferences sharedPreferences1 = context.getSharedPreferences("service_Details",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences1.edit();
                editor.putString("serviceId",getMisSer);
                editor.putString("serviceQty",qty);
                editor.putString("servicePrice",price);
                editor.apply();
                notifyItemChanged(position);


                SharedPreferences sharedPreferences = context.getSharedPreferences("minus", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor1 = sharedPreferences.edit();
//                 String loc = getLocation.toString();
                editor1.putString("minus", String.valueOf(minusI));
                editor1.apply();
                if (minusI==0){
                    System.out.println("Qty is less  then 1");
                    System.out.println("updated qty : " +minusI);
                    holder.getCountText().setText(String.valueOf(minusI));
                    holder.getLl().setVisibility(View.GONE);
                    holder.getAddButton().setVisibility(View.VISIBLE);
                    notifyItemChanged(position);

                    System.out.println("********************" + holder.getCountText());
                }else if (minusI>=1){
                    System.out.println("Qty is greater then 1");
                    System.out.println("updated qty : " +minusI);
                    holder.getCountText().setText(String.valueOf(minusI));
                    notifyItemChanged(position);
                } else {
                    System.out.println("SomeThing Went Wrong");
                    notifyItemChanged(position);
                }
//                if (count[position] > 1) {
//                    System.out.println("Test 1.1");
//                    count[position]--;
//                    holder.getCountText().setText(String.valueOf(count[position]));
//                    System.out.println("On if processss");
//                } else if (count[position]==0){
//                    System.out.println("Test 2");
//                    try {
//                        System.out.println("check qty : " +count[position]);
//                        Integer po =count[position]-1;
//                        System.out.println("zero position :  " +po);
//                        if (po==0){
//                            System.out.println("Testing ");
//
//                        holder.getAddButton().setVisibility(View.VISIBLE);
//                        holder.getLl().setVisibility(View.GONE);
//                        System.out.println("On else processss" + count[position]);
//                        }
//                    }catch (Exception ex){
//
//                        System.out.println("Exception occured : " + ex.getMessage());
//                    }
//
//
//                }
                itemSelectionClickListener.minusSer(position);
            }
        });



//
//        if(selecteditem==position){
//            add.setVisibility(View.GONE);
//            addSer.setVisibility(View.VISIBLE);
//            minusSer.setVisibility(View.VISIBLE);
//            showQty.setVisibility(View.VISIBLE);
//            Log.e("TAG position", "onBindViewHolder: " + selecteditem  + "" + position);
//        }

//        if (cateList.equals("MEN")){
//            personImage.setImageResource(R.drawable.womenpic);
//        }
//        else{
//            holder.add.setVisibility(View.INVISIBLE);
//
//        }

//        if (personList.get(position).getCategory().equals("COLOURING")) {
////            holder.icon.setImageResource(R.drawable.medical);
//        }
//        if (personList.get(position).getCategory().equals("HAIR CARE")) {
////            holder.icon.setImageResource(R.drawable.spanner);
//        }
//        if (personList.get(position).getCategory().equals("HAIRCUT")) {
////            holder.icon.setImageResource(R.drawable.shopping);
//        }
//        if (personList.get(position).getCategory().equals("SKIN CARE")) {
////            holder.icon.setImageResource(R.drawable.education);
//        }
//
//        //setting first icon always visible
//        if (position == 0) {
////            holder.icon.setVisibility(View.VISIBLE);
//        }
//
//        //setting bottom view visible for dividing
//        if (position + 1 < getItemCount()) {
//            if (!personList.get(position).getCategory().substring(0, 1).equals
//                    (personList.get(position + 1).getCategory().substring(0, 1))) {
//                holder.itemView.setVisibility(View.VISIBLE);
//            }
//        }
//
//        //setting icon visible for group of profession
//        if (position > 0) {
//            if (!personList.get(position).getCategory().substring(0, 1).equals
//                    (personList.get(position - 1).getCategory().substring(0, 1))) {
////                holder.icon.setVisibility(View.VISIBLE);
//            }
//        }





//        if(count == 0) {
//            holder.add.setVisibility(View.VISIBLE);
//            addSer.setVisibility(View.INVISIBLE);
//            minusSer.setVisibility(View.INVISIBLE);
//            showQty.setVisibility(View.INVISIBLE);
//        } else {
//            addSer.setVisibility(View.VISIBLE);
//            minusSer.setVisibility(View.VISIBLE);
//            showQty.setVisibility(View.VISIBLE);
//            add.setVisibility(View.INVISIBLE);
//
//        }


    }

    public void getAllMobile(List<Product>productList){
        this.products.size();
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public interface ItemSelectionClickListener {

        void onItemClick(Product product);

        void onButtonClick(int position);

        void addSer(int position);

        void minusSer(int position);
        void qty(int position);


    }

//    public void setCarts(List<StoreCart>carts){
//        this.personList =personList;
//        notifyDataSetChanged();
//
//    }

    public static class personViewHolder extends RecyclerView.ViewHolder {

        public TextView personName;
        public RoundedImageView personImage;
        TextView price, cate, cart;
        int position;
        Button add;
        TextView showQty;
        Button book;
        Button addSer, minusSer;
        ConstraintLayout buttonLayout;


        public personViewHolder(@NonNull View itemView) {
            super(itemView);

            personImage = itemView.findViewById(R.id.profile_image);
            personName = itemView.findViewById(R.id.profile_name);
            add = itemView.findViewById(R.id.buttonAdd);
            price = itemView.findViewById(R.id.textViewPrice);
//            cate = itemView.findViewById(R.id.textViewCate);
            addSer = itemView.findViewById(R.id.buttonAddSer);
            minusSer = itemView.findViewById(R.id.buttonMinusSer);
            showQty = itemView.findViewById(R.id.textViewViewSer);
            buttonLayout = itemView.findViewById(R.id.buttonLayout);


//            multiButton = itemView.findViewById(R.id.buttonMulti);
//            multiButton.setVisibility(View.GONE);
//            multiButton.setOnClickListener((ElegantNumberButton.OnClickListener) view -> {
//                String number = multiButton.getNumber();
//
//            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("click ", "clicked" + position + " " + personName.getText().toString());


                }
            });

         /*   add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemSelectionClickListener.onButtonClick(getAbsoluteAdapterPosition());


//                    addSer.setVisibility(View.VISIBLE);
//                    minusSer.setVisibility(View.VISIBLE);
//                    add.setVisibility(View.GONE);
//                    multiButton.setVisibility(View.VISIBLE);

//                    String jk = personName.getText().toString();


                }
            });*/
//            addSer.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//
//                }
//            });
//            minusSer.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//
//                }
//            });

//            add.setOnClickListener(new View.OnClickListener() {
//                @SuppressLint("SetTextI18n")
//                @Override
//                public void onClick(View view) {
////                    BottomSheetStylist();
//
//
//
//                    Log.d("button" , "onClick" + " "+ personName.getText().toString() );
////                    Log.d("price","price Clicked" + "  "+price.getText().toString());
//                   String getName = personName.getText().toString();
//                   String getPrice = price.getText().toString();
////
//                   SessionManagement sessionManagement = new SessionManagement(view.getContext());
////                   String getId = sessionManagement.getSession();
//                   String shopId = sessionManagement.getSession1();
////                   SessionManagement sessionManagement1 = new SessionManagement(view.getContext());
//                    String id = sessionManagement.getSession();
//                    if (id .isEmpty()){
//                        alertDialogBox();
//
//                    }else {
//                        JSONObject jsonObject = new JSONObject();
//                        try {
//                            jsonObject.put("servicename",getName);
//                            jsonObject.put("price",getPrice);
//                            jsonObject.put("custid",id);
//                            jsonObject.put("salonid",shopId);
//                            jsonObject.put("qty",quantity);
////                        jsonObject.put("pincode", strPinCode);
////                        jsonObject.put("dob",strDob);
////                        jsonObject.put("gender",strGender);
//                            Log.e("TAG", "onClick: 0000000000"+jsonObject );
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, url,
//                                jsonObject, new Response.Listener<JSONObject>() {
//                            @Override
//                            public void onResponse(JSONObject response) {
//
////                            Toast.makeText(, response.toString(), Toast.LENGTH_SHORT).show();
//
//                            }
//                        }, new Response.ErrorListener() {
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
////                            Toast.makeText(view.getContext(), error.toString(), Toast.LENGTH_SHORT).show();
//                            }
//                        });
//
//
//                        RequestQueue requestQueue = Volley.newRequestQueue(view.getContext());
//                        requestQueue.add(stringRequest) ;
//
//                    }
////                    SessionManagement sessionManagement = new SessionManagement(view.getContext());
////                   String getId = sessionManagement.getSession();
//                    Log.e("TAG get iddddddd", "onClick: "+id);
//


//
//                    Toast.makeText(view.getContext(),"service added successfully", Toast.LENGTH_SHORT).show();
////////                   list.add(getName);
////                   list.add(getName);
//
//
//
//
//
//
//
//
//
////                    Toast.makeText(view.getContext(), getName + " : " + getPrice, Toast.LENGTH_SHORT).show();
////                    Intent intent = new Intent(view.getContext(),ListToShow.class);
////                    intent.putExtra("getService",getName);
////                    intent.putExtra("getPrice",getPrice);
////                    view.getContext().startActivity(intent);
//
//
//
//
//
////                    = new ArrayList<>();
////                    TableBookingList bookingList = new TableBookingList();
////                    bookingList.setPrice(getPrice);
////                    System.out.println("Booking List"  +   bookingList.getPrice());
//
//
//
////                    List<PhotoViewModel> pho = new ArrayList<>();
////                    PhotoViewModel p = new PhotoViewModel();
////                    p.setImagePath(photo.getImage_path());
////                    pho.add(p);


//
//
//
//
//
//
//                }
//
////                private void BottomSheetStylist() {
////                    List<StylistDatum> list = new ArrayList<>();
////                    list.add(new StylistDatum().setEmpname("Gowtham"));
////                    BottomSheetFragment bottomSheetFragment = new BottomSheetFragment(list, new StylistAdapter.StylistClickListener() {
////                        @Override
////                        public void stylistClick(StylistDatum list) {
////
////                        }
////                    });
////
////
////                }
//            });
//
//
//
//        }
        }

        public Button getAddButton() {
            return add;
        }

        public ConstraintLayout getLl() {
            return buttonLayout;
        }

        public Button getPlusButton() {
            return addSer;
        }

        public Button getMinusButton() {
            return minusSer;
        }

        public TextView getCountText() {
            return showQty;
        }
//    public  void SendService(){
//        String url = "http://192.168.4.179:2002/cartAdd";
//        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put("cu",strName);
//            jsonObject.put("email",strEmail);
//            jsonObject.put("password",strPassword);
//            jsonObject.put("mobileno",strMobileNum);
//            jsonObject.put("pincode", strPinCode);
//            jsonObject.put("dob",strDob);
//            jsonObject.put("gender",strGender);
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, url,
//                jsonObject, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                Toast.makeText(, response.toString(), Toast.LENGTH_SHORT).show();
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(SignUpActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
//            }
//        });
//
//
//        RequestQueue requestQueue = Volley.newRequestQueue(SignUpActivity.this);
//        requestQueue.add(stringRequest) ;
//
//    }
//public static void alertDialogBox(){
//
//
//    AlertDialog.Builder builder = new AlertDialog.Builder(getContext);
//    builder.setMessage("Please login to add service in cart" );
//    builder.setCancelable(true);
//    builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
//        @Override
//        public void onClick(DialogInterface dialogInterface, int i) {
//
////            dataName.setText(setName);
////            dataDate.setText(setDate);
////            dataTime.setText(setTime);
////            dataService.setText(setService);
////            dataStylist.setText(setStylist);
//
//
//        }
//    });
//    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//        @Override
//        public void onClick(DialogInterface dialogInterface, int i) {
////            CancelApproval();
//
//
//        }
//    });
//    AlertDialog alertDialog = builder.create();
//    alertDialog.show();
//}
    }

}
