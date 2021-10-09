package com.example.coffee_lovers_mrcoffee;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import org.jetbrains.annotations.NotNull;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainAdapterProduct extends FirebaseRecyclerAdapter<Product, MainAdapterProduct.myViewHolder> {


    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MainAdapterProduct(@NonNull @NotNull FirebaseRecyclerOptions<Product> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull Product model) {
        holder.name.setText(model.getName());
        holder.price.setText(model.getPrice());
        holder.description.setText(model.getDescription());

        Glide.with(holder.img.getContext()).
                load(model.getImage()).
                placeholder(R.drawable.common_google_signin_btn_icon_dark).circleCrop()
                .error(R.drawable.common_google_signin_btn_icon_dark_normal).into(holder.img);
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_main_product, parent, false);
        return new MainAdapterProduct.myViewHolder(view);
    }

    public class myViewHolder extends RecyclerView.ViewHolder{

        CircleImageView img;
        TextView name, price, description;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            img = (CircleImageView)itemView.findViewById(R.id.imageProduct);
            name = (TextView)itemView.findViewById(R.id.nameProduct);
            price = (TextView)itemView.findViewById(R.id.productPrice);
            description = (TextView)itemView.findViewById(R.id.productDes);
        }
    }
}
