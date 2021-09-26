package com.example.coffee_lovers_mrcoffee.services;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.coffee_lovers_mrcoffee.data.models.Customer;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Objects;

import io.reactivex.rxjava3.subjects.PublishSubject;

public class AuthService {

    private final FirebaseAuth fAuth;
    private final FirebaseFirestore fireStore;

    private final String KEY_COLLECTION_CUSTOMERS = "customers";

    public PublishSubject<Customer> currentUser = PublishSubject.create();

    public AuthService() {
        fAuth = FirebaseAuth.getInstance();
        fireStore = FirebaseFirestore.getInstance();

        TrackCurrentUser();
    }


    /**
     * Start tracking changes of current user
     */
    private void TrackCurrentUser() {

        FirebaseUser fUser = fAuth.getCurrentUser();

        if (fUser == null) {
            currentUser.onNext(null);
        } else {

            fireStore.collection(KEY_COLLECTION_CUSTOMERS)
                    .document(fUser.getUid())
                    .addSnapshotListener((value, error) -> {
                        if (error != null) {
                            System.err.println("Current user error: " + error);
                        } else {
                            currentUser.onNext(value.toObject(Customer.class));
                        }

                        System.out.println("Current user changes: " + (fUser == null ? "NULL" : fUser.getUid()));
                    });

        }

    }


    /**
     * Sign up a new user using Email and Password
     */
    public void SignUp(@NonNull Customer customer, OnCompleteListener onComplete, OnFailureListener onFailure) {

        // auth using firebase auth
        fAuth.createUserWithEmailAndPassword(customer.email, customer.password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        customer.id = Objects.requireNonNull(task.getResult()).getUser().getUid();
                        CreateUserData(customer, task1 -> {
                            if (task1.isSuccessful()) {
                                TrackCurrentUser();
                                onComplete.onComplete(Tasks.forResult(null));
                            } else {
                                fAuth.signOut();
                                onFailure.onFailure(task1.getException());
                            }
                        });
                    } else {
                        onFailure.onFailure(task.getException());
                    }
                });

    }


    /**
     * Create user data
     */
    private void CreateUserData(Customer cus, OnCompleteListener onComplete) {

        fireStore.collection(KEY_COLLECTION_CUSTOMERS)
                .document(cus.id)
                .set(cus)
                .addOnCompleteListener(onComplete);

    }


    /**
     * Sign in with email and password
     */
    public void SignIn(String email, String password, OnCompleteListener onComplete, OnFailureListener onFailure) {

        fAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        onComplete.onComplete(Tasks.forResult(null));
                        TrackCurrentUser();
                    } else {
                        onFailure.onFailure(task.getException());
                    }
                })
                .addOnFailureListener(e -> onFailure.onFailure(e));

    }

}
