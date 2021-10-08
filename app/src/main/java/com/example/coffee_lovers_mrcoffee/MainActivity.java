package com.example.coffee_lovers_mrcoffee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void addProduct(View view) {
        Intent intent = new Intent(this, AddProduct.class);
        startActivity(intent);
    }

    public void showAll(View view) {
        Intent intent = new Intent(this, ShowProduct.class);
        startActivity(intent);
    }

    public void updateProduct(View view) {
        Intent intent = new Intent(this, UpdateProductLayout.class);
        startActivity(intent);
    }
}