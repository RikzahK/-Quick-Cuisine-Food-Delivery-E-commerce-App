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
import com.rikzah.assignment_2.models.HomeCategory;

import java.util.List;

public class HomeAdapters extends RecyclerView.Adapter<HomeAdapters.ViewHolder> {

    Context context;
    List<HomeCategory> categoryList;

    public HomeAdapters(Context context, List<HomeCategory> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public HomeAdapters.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.home_cat_items,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull HomeAdapters.ViewHolder holder, int position) {
        Glide.with(context).load(categoryList.get(position).getImg_url()).into(holder.catImg);
        holder.name.setText(categoryList.get(position).getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    Intent intent = new Intent(context, ViewAll_Activity.class);
                    intent.putExtra("type", categoryList.get(adapterPosition).getType());
                    context.startActivity(intent);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView catImg;
        TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            catImg=itemView.findViewById(R.id.home_cat_img);
            name=itemView.findViewById(R.id.home_cat_name);

        }
    }
}
