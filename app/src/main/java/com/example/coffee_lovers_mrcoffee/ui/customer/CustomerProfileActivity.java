package com.example.coffee_lovers_mrcoffee.ui.customer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.coffee_lovers_mrcoffee.Container;
import com.example.coffee_lovers_mrcoffee.R;
import com.example.coffee_lovers_mrcoffee.data.models.Customer;
import com.example.coffee_lovers_mrcoffee.services.AuthService;
import com.example.coffee_lovers_mrcoffee.ui.SignInActivity;

import java.text.SimpleDateFormat;

import io.reactivex.rxjava3.disposables.Disposable;

public class CustomerProfileActivity extends AppCompatActivity {

    // dependencies
    private final Container container = Container.instant();
    private final AuthService authService = container.authService;

    // components
    private TextView txt_firstName, txt_lastName, txt_email, txt_phone, txt_birthday;

    // disposals
    private Disposable currentUserDisposer;

    // detects if the current user is being deleted
    private boolean deleteMode = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_profile);

        // init components
        txt_firstName = findViewById(R.id.txt_profile_firstName);
        txt_lastName = findViewById(R.id.txt_profile_lastName);
        txt_email = findViewById(R.id.txt_profile_email);
        txt_phone = findViewById(R.id.txt_profile_phone);
        txt_birthday = findViewById(R.id.txt_profile_birthday);

        // listen for current user changes
        currentUserDisposer = authService
                .currentUser
                .subscribe(
                        this::onUserChanges,
                        throwable -> System.err.println("Error while receiving customer data" + throwable));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        // remove listeners
        currentUserDisposer.dispose();
    }


    // ---- EVENTS

    // current user changes listeners
    @SuppressLint("SimpleDateFormat")
    private void onUserChanges(Customer customer) {
        if(deleteMode)
            return;

        // logout if user is null
        if(customer == Customer.NULL) {

            goToSignIn();

        } else {

            // update fields
            txt_firstName.setText(customer.firstName);
            txt_lastName.setText(customer.lastName);
            txt_email.setText(customer.email);
            txt_phone.setText(customer.phoneNumber);
            txt_birthday.setText(new SimpleDateFormat("yyyy-MM-dd").format(customer.birthday));

        }
    }

    private void goToSignIn() {
        Intent intent = new Intent(this, SignInActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }


    // edit profile button click handler
    public void onEditProfileButtonClick(View v) {
        Intent intent = new Intent(this, CustomerEditProfileActivity.class);
        startActivity(intent);
    }


    // sign out button click
    public void onSignOutClick(View v) {
        authService.SignOut();
    }


    // delete button click
    public void onDeleteButtonClick(View v) {
        deleteMode = true;

        authService.DeleteAccount(task -> {
            deleteMode = false;
            goToSignIn();
        });
    }

}