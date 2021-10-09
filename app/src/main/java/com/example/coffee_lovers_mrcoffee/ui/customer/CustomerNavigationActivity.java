package com.example.coffee_lovers_mrcoffee.ui.customer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.coffee_lovers_mrcoffee.Container;
import com.example.coffee_lovers_mrcoffee.R;
import com.example.coffee_lovers_mrcoffee.services.AuthService;
import com.example.coffee_lovers_mrcoffee.ui.SignInActivity;

public class CustomerNavigationActivity extends AppCompatActivity {

    // dependencies
    private final Container container = Container.instant();
    private final AuthService authService = container.authService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_navigation);
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

        // goto sign in
        Intent intent = new Intent(this, SignInActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

}