package com.example.coffee_lovers_mrcoffee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.coffee_lovers_mrcoffee.data.enums.Gender;
import com.example.coffee_lovers_mrcoffee.data.models.Customer;
import com.example.coffee_lovers_mrcoffee.services.AuthService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;

import java.util.Date;
import java.util.concurrent.ExecutionException;

public class SignUpActivity extends AppCompatActivity {

    private final AuthService authService = new AuthService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }

    public void onSignUpClick(View v) {

        // TODO : Sign up

    }

}