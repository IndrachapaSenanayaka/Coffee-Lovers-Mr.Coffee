package com.example.coffee_lovers_mrcoffee;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
public class ProductUpdateAdapter extends FirebaseRecyclerAdapter<Product, ProductUpdateAdapter.myViewHolder> {


    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ProductUpdateAdapter(@NonNull @NotNull FirebaseRecyclerOptions<Product> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, final int position, @NonNull Product model) {
        holder.name.setText(model.getName());
        holder.price.setText(model.getPrice());
        holder.description.setText(model.getDescription());

        Glide.with(holder.img.getContext()).
                load(model.getImage()).
                placeholder(R.drawable.common_google_signin_btn_icon_dark).circleCrop()
                .error(R.drawable.common_google_signin_btn_icon_dark_normal).into(holder.img);

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.img.getContext())
                        .setContentHolder(new ViewHolder(R.layout.activity_product_update_popup))
                        .setExpanded(true,1100)
                        .create();

                View view = dialogPlus.getHolderView();

                EditText name = view.findViewById(R.id.productName);
                EditText price = view.findViewById(R.id.product_Price);
                EditText description = view.findViewById(R.id.product_des);

                Button btnUpdate = view.findViewById(R.id.btnUpdate);

                name.setText(model.getName());
                price.setText(model.getPrice());
                description.setText(model.getDescription());

                dialogPlus.show();

                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String, Object> map =  new HashMap<>();
                        map.put("name", name.getText().toString());
                        map.put("price", price.getText().toString());
                        map.put("description", description.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("Product")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(holder.name.getContext(), "Updated Successfully", Toast.LENGTH_LONG).show();
                                        dialogPlus.dismiss();

                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(Exception e) {
                                        Toast.makeText(holder.name.getContext(), "Error while updating", Toast.LENGTH_LONG).show();
                                        dialogPlus.dismiss();
                                    }
                                });
                    }
                });
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.name.getContext());
                builder.setTitle("Are you sure you want to delete");
                builder.setMessage("Once you deleted this that action cannot be undone");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference().child("Product")
                                .child(getRef(position).getKey()).removeValue();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(holder.name.getContext(), "Cancelled", Toast.LENGTH_LONG).show();
                    }
                });
                builder.show();
            }
        });
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_update_products, parent, false);
        return new ProductUpdateAdapter.myViewHolder(view);
    }

    public class myViewHolder extends RecyclerView.ViewHolder{

        CircleImageView img;
        TextView name, price, description;
        Button btnEdit, btnDelete;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            img = (CircleImageView)itemView.findViewById(R.id.ImageProduct);
            name = (TextView)itemView.findViewById(R.id.product_Name);
            price = (TextView)itemView.findViewById(R.id.pPrice);
            description = (TextView)itemView.findViewById(R.id.pDes);

            btnEdit = (Button)itemView.findViewById(R.id.buttonEditProduct);
            btnDelete = (Button)itemView.findViewById(R.id.buttonDeleteProduct);

        }
    }
}
