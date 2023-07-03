package com.company.demotest.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.company.demotest.Models.CartDetails;
import com.company.demotest.R;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder>  {
    List<CartDetails> cartdetails;

    String getCartId;

    //    ListToShow listToShow;
    int servicetotal ;

    //    String defaultPrice;
    //    List<CartModel> cartModelList;
    DeleteClickListener deleteClickListener;
    Context context;
    LayoutInflater mInflater;



    public CartAdapter( List<CartDetails> cartdetails, Context context,DeleteClickListener deleteClickListener) {
//        this.cartModelList = cartModelList;
        this.cartdetails =cartdetails;
        this.context = context;
        this.deleteClickListener = deleteClickListener;
        this.mInflater = LayoutInflater.from(context);

    }

    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.cart_layout,parent,false);
        return new ViewHolder(view,deleteClickListener);
    }


    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, int position) {


        holder.itemView.setOnClickListener(view -> {
            Log.e("test000000000000000000","");
            deleteClickListener.onDeleteClick(cartdetails.get(position));
        });
        Log.e("method invoking","");


//        String serviceId;




        String getServiceName = cartdetails.get(position).getProductName();
        String getServicePrice = String.valueOf(cartdetails.get(position).getProductPrice());
        String getServiceQuantity = String.valueOf(cartdetails.get(position).getProductQty());
//        String getServiceSty = cartdetails.get(position).getStylist();
        String getTotalPrice = String.valueOf(cartdetails.get(position).getTotalPrice());

        getCartId = String.valueOf(cartdetails.get(position).getProductId());

//        String getSerialNum = String.valueOf(cartdetails.get(position).getSno());
//        serviceId = cartdetails.get(position).getServiceid();
        holder.getServiceName.setText(getServiceName);
        holder.cartIdGet = getCartId.toString();
//        holder.slNum.setText(getSerialNum);
        holder.getServicePrice.setText(getTotalPrice);
        holder.getServiceQuantity.setText(getServiceQuantity);
//        holder.stylistName.setText(getServiceSty);
        holder.defaultPrices.setText(getServicePrice);
        System.out.println("cart size : "+ cartdetails.size());

//        for (int i = 0 ; i<cartdetails.size();i++) {
////            total = cartdetails.get(position).getPrice();
//            System.out.println("get price : "+ cartdetails.get(i).getPrice());
////            servicetotal += Integer.parseInt( cartdetails.get(i).getPrice());
//
//        //    Integer.parseInt(et.getText().toString());
//        }
//        Log.e("total price ",""+servicetotal);
//        Log.e("TAGTotallllllllll", "onBindViewHolder: "+total );





    }
//    System.out.println("cart size : "+ cartdetails.size());
//        for (int i = 0 ; i<cartdetails.size();i++) {
////            total = cartdetails.get(position).getPrice();
//        System.out.println("get price : "+ cartdetails.get(i).getPrice());
//        servicetotal += Integer.parseInt( cartdetails.get(i).getPrice());
//
//        //    Integer.parseInt(et.getText().toString());
//    }
//        Log.e("total price ",""+servicetotal);

    @Override
    public int getItemCount() {
        return cartdetails.size();
    }
    public  interface DeleteClickListener{
        void onDeleteClick(CartDetails cartdetail);
        void  onDeleteButton(int position);
        void onAddButton(int position);
        void onMinusButton(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView getServiceName,getServicePrice,getServiceQuantity,slNum,stylistName,defaultPrices;
        Button delete,add,minus;
        String cartIdGet;




//        ImageView add,minus,delete;


        public ViewHolder(@NonNull View itemView,DeleteClickListener deleteClickListener) {
            super(itemView);
            getServiceName = itemView.findViewById(R.id.textViewGetServiceName);
            getServicePrice = itemView.findViewById(R.id.textViewGetServicePrice);
            getServiceQuantity = itemView.findViewById(R.id.textViewServiceCount);
//            stylistName = itemView.findViewById(R.id.serviceStylist);

            defaultPrices = itemView.findViewById(R.id.textViewDefaultPrice);


//            slNum = itemView.findViewById(R.id.textViewSlNo);
            delete = itemView.findViewById(R.id.ViewDeleteButton);
            minus = itemView.findViewById(R.id.buttonMinus);
            add = itemView.findViewById(R.id.buttonStyAdd);

            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deleteClickListener.onAddButton(getAbsoluteAdapterPosition());

                }
            });

            minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    deleteClickListener.onMinusButton(getAbsoluteAdapterPosition());






                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deleteClickListener.onDeleteButton(getAbsoluteAdapterPosition());



                }
            });



        }

    }


}

