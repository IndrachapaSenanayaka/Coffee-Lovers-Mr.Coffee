package com.example.coffee_lovers_mrcoffee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class admin_landing_page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_landing_page);
    }

    public void addProductDetails(View view) {
        Intent intent = new Intent(this, AddProduct.class);
        startActivity(intent);
    }

    public void viewProduct(View view) {
        Intent intent = new Intent(this, ShowProduct.class);
        startActivity(intent);
    }

    public void updateProductData(View view) {
        Intent intent = new Intent(this, UpdateProductLayout.class);
        startActivity(intent);
    }
}