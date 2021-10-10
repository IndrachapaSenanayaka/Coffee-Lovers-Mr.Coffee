package com.example.coffee_lovers_mrcoffee.services;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.coffee_lovers_mrcoffee.Constants;
import com.example.coffee_lovers_mrcoffee.adapters.FavouritesAdapter;
import com.example.coffee_lovers_mrcoffee.data.models.customer.Customer;
import com.example.coffee_lovers_mrcoffee.data.models.customer.Product;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.firebase.ui.firestore.SnapshotParser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;

import java.util.List;

import io.reactivex.rxjava3.subjects.BehaviorSubject;

public class FavouritesService {

    private final FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    // recycle adapter
    public BehaviorSubject<FavouritesAdapter> favouritesAdapterSub = BehaviorSubject.create();

    // dependencies
    private final AuthService authService;

    public BehaviorSubject<List<Product>> favouritesSub = BehaviorSubject.create();


    // constructor
    public FavouritesService(AuthService authService) {

        // resolve dependencies
        this.authService = authService;

        // get user
        this.authService.currentUser.subscribe(customer -> {
            if(customer != Customer.NULL) {

                getFavourites(customer);

                // build firestore recycle view
                Query favouritesQuery = firestore
                        .collection(Constants.KEY_COLLECTION_CUSTOMERS)
                        .document(authService.currentUserID)
                        .collection(Constants.KEY_COLLECTION_FAVOURITES);
                FirestoreRecyclerOptions<Product> options = new FirestoreRecyclerOptions.Builder<Product>()
                        .setQuery(favouritesQuery, snapshot -> {
                            Product product = snapshot.toObject(Product.class);
                            product.customerId = authService.currentUserID;
                            product.id = snapshot.getId();
                            return product;
                        })
                        .build();
                FavouritesAdapter favouritesAdapter = new FavouritesAdapter(options);
                favouritesAdapterSub.onNext(favouritesAdapter);

            }
        });

    }


    // build query for favourites
    private void getFavourites(Customer customer) {

        Query favouritesQuery = firestore
                .collection(Constants.KEY_COLLECTION_PRODUCTS)
                .whereIn("id", customer.favorites2);

    }

}
