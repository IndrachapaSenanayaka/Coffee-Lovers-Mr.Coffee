package com.example.coffee_lovers_mrcoffee.ui.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import com.example.coffee_lovers_mrcoffee.R;
import com.example.coffee_lovers_mrcoffee.adapters.ProductUpdateAdapter;
import com.example.coffee_lovers_mrcoffee.data.models.admin.Product;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateProductLayout extends AppCompatActivity {

    RecyclerView recyclerView;
    ProductUpdateAdapter productUpdateAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product_layout);

        recyclerView = (RecyclerView)findViewById(R.id.viewUpdateProduct);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        FirebaseRecyclerOptions<Product> options =
                new FirebaseRecyclerOptions.Builder<Product>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Product"), Product.class)
                        .build();

        productUpdateAdapter = new ProductUpdateAdapter(options);
        recyclerView.setAdapter(productUpdateAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        productUpdateAdapter.startListening();

    }

    @Override
    protected void onStop() {
        super.onStop();
        productUpdateAdapter.stopListening();
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

        productUpdateAdapter = new ProductUpdateAdapter(options);
        productUpdateAdapter.startListening();
        recyclerView.setAdapter(productUpdateAdapter);
    }

}