package com.rikzah.assignment_2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.rikzah.assignment_2.R;
import com.rikzah.assignment_2.models.MycartModel;
import com.rikzah.assignment_2.models.OrderHistoryModel;

import java.util.List;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.ViewHolder>{
    Context context;
    List<OrderHistoryModel> OrderModelList;
    FirebaseFirestore firestore;
    FirebaseAuth auth;
    public OrderHistoryAdapter(Context context, List<OrderHistoryModel> orderModelList) {
        this.context = context;
        OrderModelList = orderModelList;
        auth=FirebaseAuth.getInstance();}
    @NonNull
    @Override
    public OrderHistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.orderhistory_item,parent,false));}
    @Override
    public void onBindViewHolder(@NonNull OrderHistoryAdapter.ViewHolder holder, int position) {
        Glide.with(context).load(OrderModelList.get(position).getProductImage()).into(holder.img);
        holder.productname.setText(OrderModelList.get(position).getProductName());
        holder.productprice.setText(OrderModelList.get(position).getProductPrice());
        holder.qtyordered.setText(OrderModelList.get(position).getTotalQuantity());
        holder.totalprice.setText(String.valueOf(OrderModelList.get(position).getTotalprice()));
        holder.orderday.setText(OrderModelList.get(position).getCurrentDate());
        holder.ordertime.setText(OrderModelList.get(position).getCurrentTime());}

    @Override
    public int getItemCount() {
        return OrderModelList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView productname,productprice,qtyordered,totalprice,ordertime,orderday;
        ImageView img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.orderhistory_prdctimg);
            productname=itemView.findViewById(R.id.orderhistory_prdctname);
            productprice=itemView.findViewById(R.id.orderhistory_prdctprice);
            qtyordered=itemView.findViewById(R.id.orderhistory_prdctqty);
            totalprice=itemView.findViewById(R.id.orderhistory_prdcttotalprice);
            orderday=itemView.findViewById(R.id.orderhistory_orderday);
            ordertime=itemView.findViewById(R.id.orderhistory_ordertime);
        }}}
