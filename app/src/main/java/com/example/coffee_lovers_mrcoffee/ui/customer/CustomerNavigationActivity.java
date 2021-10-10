package com.example.coffee_lovers_mrcoffee.ui.customer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.coffee_lovers_mrcoffee.Container;
import com.example.coffee_lovers_mrcoffee.R;
import com.example.coffee_lovers_mrcoffee.data.enums.Gender;
import com.example.coffee_lovers_mrcoffee.data.models.customer.Customer;
import com.example.coffee_lovers_mrcoffee.services.AuthService;
import com.example.coffee_lovers_mrcoffee.ui.SignInActivity;

import io.reactivex.rxjava3.disposables.Disposable;

public class CustomerNavigationActivity extends AppCompatActivity {

    // dependencies
    private final Container container = Container.instant();
    private final AuthService authService = container.authService;

    // disposals
    private Disposable currentUserDisposer;

    // components
    private TextView txt_welcome;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_navigation);

        // init components
        txt_welcome = findViewById(R.id.cus_nav_txt_welcome);

        // listen for current user changes
        currentUserDisposer = authService
                .currentUser
                .subscribe(this::onUserChanges);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        currentUserDisposer.dispose();
    }


    // --MECHANISMS

    // start an activity from this activity
    private void startActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }


    // --EVENTS

    // view profile click
    public void onViewProfileClick(View v) {
        startActivity(CustomerProfileActivity.class);
    }


    // favorites click
    public void onViewFavouritesClick(View v) {
        startActivity(FavouritesActivity.class);
    }


    // sign-out click
    public void onSignOutClick(View v) {
        authService.SignOut();
    }


    // current user changes listeners
    private void onUserChanges(Customer customer) {
        // logout if user is null
        if(customer == Customer.NULL) {

            // goto sign in
            Intent intent = new Intent(this, SignInActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

        } else {

            String fName = customer.firstName;
            String gender = customer.gender == Gender.Male ? "Mr." : "Mrs.";
            String welcomeText = "Welcome back \n" + gender + " " + fName;
            txt_welcome.setText(welcomeText);

        }
    }

}