package com.rikzah.assignment_2.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rikzah.assignment_2.Activities.Detailed_Activity;
import com.rikzah.assignment_2.Activities.ViewAll_Activity;
import com.rikzah.assignment_2.R;
import com.rikzah.assignment_2.models.newproductmodel;

import java.util.List;


public class newproductadapter extends RecyclerView.Adapter<com.rikzah.assignment_2.adapters.newproductadapter.ViewHolder> {
    Context context;
    List<newproductmodel> list;
    public newproductadapter() {}
    public newproductadapter(Context context, List list) {
        this.context = context;
        this.list = list;}
    @NonNull
    @Override
    public newproductadapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new newproductadapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.newproductitem,parent,false));}

    @Override
    public void onBindViewHolder(@NonNull newproductadapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Glide.with(context).load(list.get(position).getImg_url()).into(holder.imageView);
        holder.name.setText(list.get(position).getName());
        holder.description.setText(list.get(position).getDescription());
        holder.price.setText(list.get(position).getPrice());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context, Detailed_Activity.class);
                intent.putExtra("detail",list.get(position));
                context.startActivity(intent);}});}

    @Override
    public int getItemCount() {
        return list.size(); }
    public class ViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;
            TextView name,description,price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.dish_image);
            name = itemView.findViewById(R.id.dish_name);
            description = itemView.findViewById(R.id.dish_des);
            price=itemView.findViewById(R.id.dish_price);}}}