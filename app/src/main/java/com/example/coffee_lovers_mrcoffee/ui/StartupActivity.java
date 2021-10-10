package com.example.coffee_lovers_mrcoffee.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.coffee_lovers_mrcoffee.Container;
import com.example.coffee_lovers_mrcoffee.R;
import com.example.coffee_lovers_mrcoffee.data.models.customer.Customer;
import com.example.coffee_lovers_mrcoffee.services.AuthService;
import com.example.coffee_lovers_mrcoffee.ui.customer.CustomerNavigationActivity;

import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.rxjava3.disposables.Disposable;

public class StartupActivity extends AppCompatActivity {

    // dependencies
    Container container = Container.instant();
    AuthService authService = container.authService;

    // disposables
    Disposable customerListener;

    // delay
    private final int DELAY = 1500;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);

        // check if customer is already logged in
        customerListener = authService.currentUser.subscribe(customer -> {

            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {

                    // stop subscription
                    customerListener.dispose();

                    // notify changes
                    onCustomerChanges(customer);

                }
            }, DELAY);

        });

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (!customerListener.isDisposed())
            customerListener.dispose();
    }


    // listen for customer changes
    private void onCustomerChanges(Customer customer) {

        Intent intent;

        if (customer == Customer.NULL) {
            intent = new Intent(StartupActivity.this, SignInActivity.class);
        } else {
            intent = new Intent(StartupActivity.this, CustomerNavigationActivity.class);
        }

        startActivity(intent);
        finish();

    }

}