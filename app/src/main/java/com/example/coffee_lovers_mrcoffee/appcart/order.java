package com.example.coffee_lovers_mrcoffee.appcart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.coffee_lovers_mrcoffee.R;

import java.util.Objects;

public class order extends AppCompatActivity {
    TextView etCity, etPno, etHno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_baseline_menu_24);
        setContentView(R.layout.activity_order);


        etCity = findViewById(R.id.textView33);
        etPno = findViewById(R.id.textView34);
        etHno = findViewById(R.id.textView35);

        String name, pno, hno;
        name = getIntent().getStringExtra("name");
        pno = getIntent().getStringExtra("sno");
        hno = getIntent().getStringExtra("hno");

        etCity.setText(name);
        etPno.setText(pno);
        etHno.setText(hno);

    }
    public void setOrder (View view){
        Intent intent = new Intent (this, payment.class);
        startActivity(intent);
    }


}