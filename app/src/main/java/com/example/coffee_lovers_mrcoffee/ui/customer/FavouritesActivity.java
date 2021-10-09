package com.example.coffee_lovers_mrcoffee.ui.customer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.coffee_lovers_mrcoffee.Container;
import com.example.coffee_lovers_mrcoffee.R;
import com.example.coffee_lovers_mrcoffee.adapters.FavouritesAdapter;
import com.example.coffee_lovers_mrcoffee.services.FavouritesService;

public class FavouritesActivity extends AppCompatActivity {

    // dependencies
    Container container = Container.instant();
    FavouritesService favouritesService = container.favouritesService;

    // favourites recycle view
    RecyclerView favouritesRecycleView;
    FavouritesAdapter favouritesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        // init recycle view
        favouritesService.favouritesAdapterSub.subscribe(favouritesAdapter -> {

            FavouritesActivity.this.favouritesAdapter = favouritesAdapter;
            favouritesRecycleView = findViewById(R.id.favourites_recycleview);
            favouritesRecycleView.setHasFixedSize(true);
            favouritesRecycleView.setLayoutManager(new LinearLayoutManager(FavouritesActivity.this));
            favouritesRecycleView.setAdapter(favouritesAdapter);
            favouritesAdapter.startListening();

        });

    }


    @Override
    protected void onStart() {
        super.onStart();
        if(favouritesAdapter != null)
            favouritesAdapter.startListening();
    }


    @Override
    protected void onStop() {
        super.onStop();
        if(favouritesAdapter != null)
            favouritesAdapter.stopListening();
    }

}