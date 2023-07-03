package com.company.demotest.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.appbroker.roundedimageview.RoundedImageView;
import com.company.demotest.Models.Product;
import com.company.demotest.R;

import java.util.List;

public class ListViewAdapter extends RecyclerView.Adapter<ListViewAdapter.ViewHolder>{
    List<Product>products;
    Context context;

    public ListViewAdapter(List<Product> products, Context context) {
        this.products = products;
        this.context = context;
    }

    @NonNull
    @Override
    public ListViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list_item,parent,false);

        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewAdapter.ViewHolder holder, int position) {

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
    }

    public void getAllMobile(List<Product>products){
        this.products.size();
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView personName;
        public RoundedImageView personImage;
        TextView price, cate, cart;
        int position;
        Button add;
        TextView showQty;
        Button book;
        Button addSer, minusSer;
        ConstraintLayout buttonLayout;
        public ViewHolder(@NonNull View itemView) {
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
        }
    }
}
