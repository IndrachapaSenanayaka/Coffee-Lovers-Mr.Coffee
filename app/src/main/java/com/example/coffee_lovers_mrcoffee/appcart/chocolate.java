package com.example.coffee_lovers_mrcoffee.appcart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.coffee_lovers_mrcoffee.R;

import java.util.Objects;

public class chocolate extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_baseline_menu_24);
        setContentView(R.layout.activity_chocolate);
    }

    public void AddDelivery(View view) {
        Intent intent=new Intent(chocolate.this, delivery.class);
        startActivity(intent);
    }
}