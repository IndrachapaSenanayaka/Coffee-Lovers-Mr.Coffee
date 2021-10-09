package com.example.coffee_lovers_mrcoffee.services;

import static com.example.coffee_lovers_mrcoffee.Constants.KEY_COLLECTION_CUSTOMERS;

import androidx.annotation.NonNull;

import com.example.coffee_lovers_mrcoffee.data.models.Customer;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
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
    private FirebaseUser firebaseUser;

    public BehaviorSubject<Customer> currentUser = BehaviorSubject.create();
    public String currentUserID = null;

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
            firebaseUser = fAuth.getCurrentUser();

            if (firebaseUser == null) {
                currentUserID = null;
                currentUser.onNext(Customer.NULL);
                if (userDataListener != null)
                    userDataListener.remove();
            } else {
                currentUserID = firebaseUser.getUid();
                TrackCurrentUser(firebaseUser.getUid());
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
                        Customer customer = value != null ? value.toObject(Customer.class) : null;
                        if (customer == null)
                            currentUser.onNext(Customer.NULL);
                        else
                            currentUser.onNext(customer);
                    }
                });
    }


    /**
     * Sign up a new user using Email and Password
     */
    public void SignUp(@NonNull Customer customer, OnCompleteListener<Void> onComplete, OnFailureListener onFailure) {

        // auth using firebase auth
        fAuth.createUserWithEmailAndPassword(customer.email, customer.password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null && task.getResult().getUser() != null) {
                        customer.id = task.getResult().getUser().getUid();
                        CreateUserData(customer, task1 -> {
                            if (task1.isSuccessful()) {
                                SignIn(customer.email, customer.password,
                                        task2 -> onComplete.onComplete(Tasks.forResult(null)),
                                        onFailure);
                            } else if(task1.getException() != null) {
                                fAuth.signOut();
                                onFailure.onFailure(task1.getException());
                            }
                        });
                    } else {
                        onFailure.onFailure(Objects.requireNonNull(task.getException()));
                    }
                });

    }


    /**
     * Create user data
     */
    private void CreateUserData(Customer cus, OnCompleteListener<Void> onComplete) {

        fireStore.collection(KEY_COLLECTION_CUSTOMERS)
                .document(cus.id)
                .set(cus)
                .addOnCompleteListener(onComplete);

    }


    /**
     * Sign in with email and password
     */
    public void SignIn(String email, String password, OnCompleteListener<Void> onComplete, OnFailureListener onFailure) {

        fAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        onComplete.onComplete(Tasks.forResult(null));
                    } else if(task.getException() != null) {
                        onFailure.onFailure(task.getException());
                    }
                })
                .addOnFailureListener(onFailure);

    }


    /**
     * Update current user data
     */
    public void UpdateCurrentUser(Customer customer, OnCompleteListener<Void> onComplete, OnFailureListener onFailure) {

        fireStore.collection(KEY_COLLECTION_CUSTOMERS)
                .document(firebaseUser.getUid())
                .set(customer)
                .addOnCompleteListener(task -> onComplete.onComplete(Tasks.forResult(null)))
                .addOnFailureListener(onFailure);

    }


    /**
     * Sign out current user
     */
    public void SignOut() {
        fAuth.signOut();
    }


    /**
     * Delete current user account
     */
    public void DeleteAccount(OnCompleteListener<Void> onComplete) {
        if(fAuth.getUid() == null)
            return;

        // first delete account data
        fireStore
                .collection(KEY_COLLECTION_CUSTOMERS)
                .document(fAuth.getUid())
                .delete()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        firebaseUser
                                .delete()
                                .addOnCompleteListener(task1 -> onComplete.onComplete(Tasks.forResult(null)));
                    }
                });
    }


    /**
     * Change current user password
     */
    public void ChangePassword(String password, OnCompleteListener<Void> onComplete) {

        firebaseUser
                .updatePassword(password)
                .addOnCompleteListener(onComplete);

    }

}
