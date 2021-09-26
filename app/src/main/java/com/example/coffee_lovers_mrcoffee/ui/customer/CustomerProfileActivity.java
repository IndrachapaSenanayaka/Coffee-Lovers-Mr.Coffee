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

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;

public class CustomerProfileActivity extends AppCompatActivity {

    // dependencies
    private final Container container = Container.instant();
    private final AuthService authService = container.authService;

    // components
    private TextView txt_firstName, txt_lastName, txt_email, txt_phone, txt_birthday;

    // disposals
    private Disposable currentUserDisposer;


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
                        this::ListenUserChanges,
                        throwable -> {
                            System.err.println("Error while receiving customer data" + throwable);
                        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        // remove listeners
        currentUserDisposer.dispose();
    }


    // ---- EVENTS

    // current user changes listners
    private void ListenUserChanges(Customer customer) {

        // update fields
        txt_firstName.setText(customer.firstName);
        txt_lastName.setText(customer.lastName);
        txt_email.setText(customer.email);
        txt_phone.setText(customer.phoneNumber);
        txt_birthday.setText(new SimpleDateFormat("yyyy-MM-dd").format(customer.birthday));

    }


    // edit profile button click handler
    public void onEditProfileButtonClick(View v) {
        Intent intent = new Intent(this, CustomerEditProfileActivity.class);
        startActivity(intent);
    }

}