package com.example.coffee_lovers_mrcoffee.services;

import androidx.annotation.NonNull;

import com.example.coffee_lovers_mrcoffee.data.models.Customer;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class AuthService {

    private final FirebaseAuth fAuth;
    private final FirebaseFirestore fireStore;

    public AuthService() {
        fAuth = FirebaseAuth.getInstance();
        fireStore = FirebaseFirestore.getInstance();
    }


    /**
     * Sign up a new user using Email and Password
     */
    public void SignUp(Customer customer, OnCompleteListener onComplete, OnFailureListener onFailure) {

        // auth using firebase auth
        fAuth.createUserWithEmailAndPassword(customer.email, customer.password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        customer.id = Objects.requireNonNull(task.getResult()).getUser().getUid();
                        CreateUserData(customer, task1 -> {
                            if (task1.isSuccessful()) {
                                onComplete.onComplete(Tasks.forResult(null));
                            } else {
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

        fireStore.collection("customers")
                .document(cus.id)
                .set(cus)
                .addOnCompleteListener(onComplete);

    }

}
