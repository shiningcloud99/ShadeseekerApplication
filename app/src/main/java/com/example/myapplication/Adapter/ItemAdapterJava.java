package com.example.myapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.BrandsProduct;
import com.example.myapplication.Item;
import com.example.myapplication.R;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ItemAdapterJava extends RecyclerView.Adapter<ItemAdapterJava.MyViewHolder> {

    Context context;
    ArrayList<Item> itemArrayList;
    OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(Item item);
    }

    public ItemAdapterJava(Context context, ArrayList<Item> itemArrayList, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.itemArrayList = itemArrayList;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.list_item_brand, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Item item = itemArrayList.get(position);
        holder.tvHeading.setText(item.getHeading());
        Picasso.get().load(item.getImageUrl()).into(holder.titleImage);

        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvHeading;
        ShapeableImageView titleImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvHeading = itemView.findViewById(R.id.tvHeading);
            titleImage = itemView.findViewById(R.id.title_image);
        }
    }
}
