package com.example.coffee_lovers_mrcoffee;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class AdminViewPromotion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_menu);
        setContentView(R.layout.activity_admin_view_promotion);
    }
}