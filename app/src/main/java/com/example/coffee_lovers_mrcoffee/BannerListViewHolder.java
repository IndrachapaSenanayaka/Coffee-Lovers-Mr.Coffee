package com.example.coffee_lovers_mrcoffee;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BannerListViewHolder extends RecyclerView.ViewHolder{
    ImageView imageView;
    View v;

    public BannerListViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView=itemView.findViewById(R.id.image_view_banner_customer_list);
        v=itemView;
    }
}
