package com.example.coffee_lovers_mrcoffee.ui.admin;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffee_lovers_mrcoffee.Constants;
import com.example.coffee_lovers_mrcoffee.R;
import com.example.coffee_lovers_mrcoffee.adapters.MainAdapterProduct;
import com.example.coffee_lovers_mrcoffee.data.models.admin.Product;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class ShowProduct extends AppCompatActivity {

    RecyclerView recyclerView;
    MainAdapterProduct mainAdapterProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_product);

        recyclerView = (RecyclerView) findViewById(R.id.viewAllProducts);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Query query = FirebaseFirestore.getInstance().collection(Constants.KEY_COLLECTION_PRODUCTS);
        FirestoreRecyclerOptions<Product> options = new FirestoreRecyclerOptions.Builder<Product>()
                .setQuery(query, Product.class)
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
                txtSearch(query);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void txtSearch(String str) {
        Query query = FirebaseFirestore.getInstance().collection("Product")
                .orderBy("name")
                .startAt(str)
                .endAt(str + "~");
        FirestoreRecyclerOptions<Product> options = new FirestoreRecyclerOptions.Builder<Product>()
                .setQuery(query, Product.class)
                .build();

        mainAdapterProduct = new MainAdapterProduct(options);
        mainAdapterProduct.startListening();
        recyclerView.setAdapter(mainAdapterProduct);
    }
}