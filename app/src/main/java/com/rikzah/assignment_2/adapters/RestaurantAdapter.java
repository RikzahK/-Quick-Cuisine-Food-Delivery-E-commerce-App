package com.rikzah.assignment_2.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.rikzah.assignment_2.Activities.ViewAll_Activity;
import com.rikzah.assignment_2.R;
import com.rikzah.assignment_2.models.RestaurantModel;

import java.util.List;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.ViewHolder>{
    Context context;
    List<RestaurantModel> list;
    public RestaurantAdapter(Context context, java.util.List<RestaurantModel> list) {
        this.context = context;
        this.list = list;}
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_item,parent,false));}
    @Override
    public void onBindViewHolder(@NonNull RestaurantAdapter.ViewHolder holder, int position) {
        Glide.with(context).load(list.get(position).getImg_url()).into(holder.imageView);
        holder.name.setText(list.get(position).getName());
        holder.description.setText(list.get(position).getDescription());
        holder.rating.setText(list.get(position).getRating());
        holder.delivery_time.setText(list.get(position).getDelivery_time());
        holder.discount.setText(list.get(position).getDiscount());
        holder.deliveryfee.setText(list.get(position).getDelivery_fee());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    Intent intent = new Intent(context, ViewAll_Activity.class);
                    intent.putExtra("popular", list.get(adapterPosition).getName());
                    context.startActivity(intent);}}});}
    @Override
    public int getItemCount() {
        return list.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name,description,rating,delivery_time,discount,deliveryfee;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.rest_img);
            name=itemView.findViewById(R.id.rest_name);
            description=itemView.findViewById(R.id.rest_des);
            rating=itemView.findViewById(R.id.rest_rating);
            delivery_time=itemView.findViewById(R.id.rest_time);
            discount=itemView.findViewById(R.id.rest_discount);
            deliveryfee=itemView.findViewById(R.id.rest_fee);}}}
