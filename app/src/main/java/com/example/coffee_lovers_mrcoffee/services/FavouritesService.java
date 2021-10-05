package com.example.coffee_lovers_mrcoffee.services;

import androidx.annotation.Nullable;

import com.example.coffee_lovers_mrcoffee.Container;
import com.example.coffee_lovers_mrcoffee.data.models.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.subjects.BehaviorSubject;

public class FavouritesService {

    private final FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private final String KEY_COLLECTION_PRODUCTS = "products";

    // dependencies
    private final Container container = Container.instant();
    private final AuthService authService = container.authService;

    public BehaviorSubject<List<Product>> favouritesSub = BehaviorSubject.create();


    // constructor
    public FavouritesService() {

        // listen favourites changes
        firestore
                .collection(KEY_COLLECTION_PRODUCTS)
                .addSnapshotListener(this::listenFavouritesChanges);

    }


    // listen for products collection changes
    private void listenFavouritesChanges(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

        if(value == null) {
            favouritesSub.onNext(new ArrayList<>());
        } else {
            // get favourites
            List<Product> products = value.getDocuments()
                    .stream()
                    .map(d -> d.toObject(Product.class))
                    .filter((p -> p.isFavourite && p.uid.equals(authService.currentUserID)))
                    .collect(Collectors.toList());
            // emmit
            favouritesSub.onNext(products);
        }

    }


    // remove an item from the favourites
    public void removeFromFavourites(String productId, OnCompleteListener<Void> onComplete) {
        firestore
                .collection(KEY_COLLECTION_PRODUCTS)
                .document(productId)
                .delete()
                .addOnCompleteListener(onComplete);
    }

}
