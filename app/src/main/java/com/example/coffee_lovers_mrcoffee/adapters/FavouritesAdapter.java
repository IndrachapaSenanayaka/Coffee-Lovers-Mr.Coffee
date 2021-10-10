package com.example.coffee_lovers_mrcoffee.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.coffee_lovers_mrcoffee.Constants;
import com.example.coffee_lovers_mrcoffee.R;
import com.example.coffee_lovers_mrcoffee.data.models.customer.Product;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;

public class FavouritesAdapter extends FirestoreRecyclerAdapter<Product,FavouritesAdapter.ViewHolder> {

    private Context context;


    public FavouritesAdapter(@NonNull FirestoreRecyclerOptions<Product> options) {
        super(options);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favourite_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Product model) {

        // set values
        holder.txt_price.setText(model.price + "");
        holder.txt_name.setText(model.name);
        Glide.with(context).load(model.img).into(holder.img_background);

        // remove favourite
        holder.img_heartIcon.setOnClickListener(view -> {
            FirebaseFirestore.getInstance()
                    .collection(Constants.KEY_COLLECTION_CUSTOMERS)
                    .document(model.customerId)
                    .collection(Constants.KEY_COLLECTION_FAVOURITES)
                    .document(model.id)
                    .delete();
        });

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView img_heartIcon, img_background;
        public TextView txt_name, txt_price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            img_heartIcon = itemView.findViewById(R.id.favourite_heart_icon);
            img_background = itemView.findViewById(R.id.favourite_img);
            txt_name = itemView.findViewById(R.id.favourite_name_txt);
            txt_price = itemView.findViewById(R.id.favourite_price);
        }

    }

}
