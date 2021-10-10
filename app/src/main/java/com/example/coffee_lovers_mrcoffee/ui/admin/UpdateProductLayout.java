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
import com.example.coffee_lovers_mrcoffee.adapters.ProductUpdateAdapter;
import com.example.coffee_lovers_mrcoffee.data.models.admin.Product;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class UpdateProductLayout extends AppCompatActivity {

    RecyclerView recyclerView;
    ProductUpdateAdapter productUpdateAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product_layout);

        recyclerView = (RecyclerView) findViewById(R.id.viewUpdateProduct);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        Query query = FirebaseFirestore.getInstance().collection(Constants.KEY_COLLECTION_PRODUCTS);
        FirestoreRecyclerOptions<Product> options =
                new FirestoreRecyclerOptions.Builder<Product>()
                        .setQuery(query, snapshot -> {
                            Product product = snapshot.toObject(Product.class);
                            product.id = snapshot.getId();
                            return product;
                        })
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
                txtSearch(query);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void txtSearch(String str) {
        Query query = FirebaseFirestore.getInstance()
                .collection(Constants.KEY_COLLECTION_PRODUCTS)
                .orderBy("name")
                .startAt(str)
                .endAt(str + "~");
        FirestoreRecyclerOptions<Product> options =
                new FirestoreRecyclerOptions.Builder<Product>()
                        .setQuery(query, snapshot -> {
                            Product product = snapshot.toObject(Product.class);
                            product.id = snapshot.getId();
                            return product;
                        })
                        .build();

        productUpdateAdapter = new ProductUpdateAdapter(options);
        productUpdateAdapter.startListening();
        recyclerView.setAdapter(productUpdateAdapter);
    }

}