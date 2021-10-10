package com.example.coffee_lovers_mrcoffee.appcart;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.coffee_lovers_mrcoffee.R;

import java.util.Objects;

public class profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_baseline_menu_24);
        setContentView(R.layout.activity_profile);
    }
}