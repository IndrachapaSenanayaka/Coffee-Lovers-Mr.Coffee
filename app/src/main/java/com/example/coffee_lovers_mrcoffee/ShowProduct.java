package com.example.coffee_lovers_mrcoffee;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class ShowProduct extends AppCompatActivity {

    RecyclerView recyclerView;
    MainAdapterProduct mainAdapterProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_product);

        recyclerView = (RecyclerView)findViewById(R.id.viewAllProducts);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        FirebaseRecyclerOptions<Product> options =
                new FirebaseRecyclerOptions.Builder<Product>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Product"), Product.class)
                        .build();

        mainAdapterProduct = new MainAdapterProduct(options);
        recyclerView.setAdapter(mainAdapterProduct);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mainAdapterProduct.startListening();
    }


    @Override
    protected void onStop() {
        super.onStop();
        mainAdapterProduct.stopListening();
    }

    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.activity_search, menu);
        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                txtSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                txtSearch(query );
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void txtSearch(String str){
        FirebaseRecyclerOptions<Product> options =
                new FirebaseRecyclerOptions.Builder<Product>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Product").orderByChild("name").startAt(str).endAt(str+"~"), Product.class)
                        .build();

        mainAdapterProduct = new MainAdapterProduct(options);
        mainAdapterProduct.startListening();
        recyclerView.setAdapter(mainAdapterProduct);
    }
}