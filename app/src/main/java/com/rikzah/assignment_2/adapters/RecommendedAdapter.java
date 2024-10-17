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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.rikzah.assignment_2.Activities.Detailed_Activity;
import com.rikzah.assignment_2.R;
import com.rikzah.assignment_2.models.RecommendedModel;
import com.rikzah.assignment_2.models.RestaurantModel;

import java.util.HashMap;
import java.util.List;

public class RecommendedAdapter extends RecyclerView.Adapter<RecommendedAdapter.ViewHolder> {
    Context context;
    List<RecommendedModel> list;

    public RecommendedAdapter() {}
    public RecommendedAdapter(Context context, List<RecommendedModel> list) {
        this.context = context;
        this.list = list;}
    @NonNull
    @Override
    public RecommendedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecommendedAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recommended_item,parent,false));}
    @Override
    public void onBindViewHolder(@NonNull RecommendedAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Glide.with(context).load(list.get(position).getImg_url()).into(holder.imageView);
        holder.name.setText(list.get(position).getName());
        holder.price.setText(list.get(position).getPrice());
        holder.orderbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecommendedModel clickedItem = list.get(position);

                if (context instanceof Detailed_Activity) {
                    ((Detailed_Activity) context).addedToCart(clickedItem);}}});}
    @Override
    public int getItemCount() {
        return list.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name,price;
        LinearLayout orderbutton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.recommended_img);
            name=itemView.findViewById(R.id.recommended_name);
            price=itemView.findViewById(R.id.recommended_price);
            orderbutton=itemView.findViewById(R.id.orderbutton);}}}
