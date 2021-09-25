package com.example.coffee_lovers_mrcoffee.ui;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.coffee_lovers_mrcoffee.R;
import com.example.coffee_lovers_mrcoffee.services.AuthService;

public class SignUpActivity extends AppCompatActivity {

    private final AuthService authService = new AuthService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }


    // sign up button click handler
    public void onSignUpClick(View v) {

        // TODO : Sign up

    }

}