package com.example.coffee_lovers_mrcoffee.ui.customer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffee_lovers_mrcoffee.data.models.Promotion;
import com.example.coffee_lovers_mrcoffee.R;
import com.example.coffee_lovers_mrcoffee.ui.admin.BannerListViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class CustomerViewPromotionsList extends AppCompatActivity {

    RecyclerView recycler_customer;
    FirebaseRecyclerOptions<Promotion> options;
    FirebaseRecyclerAdapter<Promotion, BannerListViewHolder> adapter;
    DatabaseReference dataRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_view_promotions_list);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("     Promotions");
        getSupportActionBar().setIcon(R.drawable.ic_menu);

        dataRef = FirebaseDatabase.getInstance().getReference().child("Promotion");

        recycler_customer = findViewById(R.id.recycler_customer);
        recycler_customer.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recycler_customer.setHasFixedSize(true);
        
        LoadData();
    }

    private void LoadData() {

        options=new FirebaseRecyclerOptions.Builder<Promotion>().setQuery(dataRef,Promotion.class).build();
        adapter=new FirebaseRecyclerAdapter<Promotion, BannerListViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull BannerListViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull Promotion model) {
                Picasso.get().load(model.getImageUrl()).into(holder.imageView);
                holder.v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(CustomerViewPromotionsList.this,CustomerViewPromotion.class);
                        intent.putExtra("PromotionKey",getRef(position).getKey());
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public BannerListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_banner_customer,parent,false);
                return new BannerListViewHolder(v);
            }
        };
        adapter.startListening();
        recycler_customer.setAdapter(adapter);
    }
}