package com.example.coffee_lovers_mrcoffee;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class admin_landing_page extends AppCompatActivity {

    Button promotions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_landing_page);

        promotions = findViewById(R.id.promotions);

        promotions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(admin_landing_page.this, AdminViewPromotionsList.class);
                startActivity(intent);
            }
        });
    }


}