package com.example.coffee_lovers_mrcoffee.appcart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.coffee_lovers_mrcoffee.R;

import java.util.Objects;

public class menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_baseline_menu_24);
        setContentView(R.layout.activity_menu);
    }

    public void GetChocolateItem(View view) {
        Intent intent=new Intent(menu.this, chocolate.class);
        startActivity(intent);
    }
    public void GetAmericanoItem(View view) {
        Intent intent = new Intent(menu.this, chocolate.class);
        startActivity(intent);
    }
    public void GetCaramellItem(View view) {
        Intent intent=new Intent(menu.this, chocolate.class);
        startActivity(intent);
    }
    public void GetVanillaItem(View view) {
        Intent intent = new Intent(menu.this, chocolate.class);
        startActivity(intent);
    }

}