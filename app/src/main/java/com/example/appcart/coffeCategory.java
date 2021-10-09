package com.example.appcart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class coffeCategory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_baseline_menu_24);
        setContentView(R.layout.activity_coffe_category);


    }

    public void GetCappucinno(View view) {
        Intent intent=new Intent(coffeCategory.this,menu.class);
        startActivity(intent);
    }
    public void GetFrappucinno(View view) {
        Intent intent = new Intent(coffeCategory.this, menu.class);
        startActivity(intent);
    }
    public void GetSmoothies(View view) {
        Intent intent = new Intent(coffeCategory.this, menu.class);
        startActivity(intent);
    }
}