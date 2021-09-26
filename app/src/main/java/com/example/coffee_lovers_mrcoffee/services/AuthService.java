package com.example.coffee_lovers_mrcoffee.services;

import androidx.annotation.NonNull;

import com.example.coffee_lovers_mrcoffee.data.models.Customer;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;

import java.util.Objects;

import io.reactivex.rxjava3.subjects.BehaviorSubject;

public class AuthService {

    private final FirebaseAuth fAuth;
    private final FirebaseFirestore fireStore;
    private ListenerRegistration userDataListener;
    private FirebaseUser firebaseCUser;

    private final String KEY_COLLECTION_CUSTOMERS = "customers";

    public BehaviorSubject<Customer> currentUser = BehaviorSubject.create();

    public AuthService() {
        fAuth = FirebaseAuth.getInstance();
        fireStore = FirebaseFirestore.getInstance();

        TrackCurrentUser();
    }


    /**
     * Start tracking changes of current user
     */
    private void TrackCurrentUser() {

        fAuth.addAuthStateListener(firebaseAuth -> {
            firebaseCUser = fAuth.getCurrentUser();

            if (firebaseCUser == null) {
                currentUser.onNext(null);
                userDataListener.remove();
            } else {
                TrackCurrentUser(firebaseCUser.getUid());
            }
        });

    }


    /**
     * Track current user data
     */
    private void TrackCurrentUser(String userId) {
        userDataListener = fireStore.collection(KEY_COLLECTION_CUSTOMERS)
                .document(userId)
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        System.err.println("Current user error: " + error);
                        currentUser.onError(error);
                    } else {
                        currentUser.onNext(value.toObject(Customer.class));
                    }
                });
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


    /**
     * Update current user data
     */
    public void UpdateCurrentUser(Customer customer, OnCompleteListener onComplete, OnFailureListener onFailure) {

        fireStore.collection(KEY_COLLECTION_CUSTOMERS)
                .document(firebaseCUser.getUid())
                .set(customer)
                .addOnCompleteListener(task -> {
                    onComplete.onComplete(null);
                })
                .addOnFailureListener(e -> {
                    onFailure.onFailure(e);
                });

    }

}
