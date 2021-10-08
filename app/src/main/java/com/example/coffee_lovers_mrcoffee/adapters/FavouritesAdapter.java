package com.example.coffee_lovers_mrcoffee.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffee_lovers_mrcoffee.Constants;
import com.example.coffee_lovers_mrcoffee.R;
import com.example.coffee_lovers_mrcoffee.data.models.Product;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class FavouritesAdapter extends FirestoreRecyclerAdapter<Product,FavouritesAdapter.ViewHolder> {


    public FavouritesAdapter(@NonNull FirestoreRecyclerOptions<Product> options) {
        super(options);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favourite_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Product model) {

        // remove favourite
        holder.heartIcon_img.setOnClickListener(view -> {
            FirebaseFirestore.getInstance()
                    .collection(Constants.KEY_COLLECTION_CUSTOMERS)
                    .document(model.customerId)
                    .collection(Constants.KEY_COLLECTION_FAVOURITES)
                    .document(model.id)
                    .delete();
        });

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView heartIcon_img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            heartIcon_img = itemView.findViewById(R.id.favourite_heart_icon);
        }

    }

}
