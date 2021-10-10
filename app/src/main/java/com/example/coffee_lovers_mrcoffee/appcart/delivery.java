package com.example.coffee_lovers_mrcoffee.appcart;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.coffee_lovers_mrcoffee.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class delivery extends AppCompatActivity {

     EditText etCity, etSno,etHno,etPno;
     Button submit;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_baseline_menu_24);
        setContentView(R.layout.activity_delivery);

        etCity = findViewById(R.id.et_city);
        etSno = findViewById(R.id.et_sno);
        etHno = findViewById(R.id.et_hno);
        etPno =  findViewById(R.id.et_pno);
        submit = findViewById(R.id.bt_submit);



    }

    public void AddDelivery(View view) {
        firebaseDatabase = FirebaseDatabase.getInstance("https://cafechocolate-d5faf-default-rtdb.firebaseio.com/");
        databaseReference = firebaseDatabase.getReference("delivery");

        String name = etCity.getText().toString();
        String sno = etSno.getText().toString();
        String hno = etHno.getText().toString();
        String pno = etPno.getText().toString();



            if (!TextUtils.isEmpty(pno)) {
                if (!TextUtils.isEmpty(hno)) {
                    if (!TextUtils.isEmpty(sno)) {
                        if (!TextUtils.isEmpty(name)) {


                            del delivery = new del(name, sno, hno, pno);

                            databaseReference.child(name).setValue(delivery);
                            Toast.makeText(com.example.coffee_lovers_mrcoffee.appcart.delivery.this, "Delivery Details filled Successfully", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(), deliveryupdate.class);
                            intent.putExtra("name", etCity.getText().toString());
                            intent.putExtra("sno", etSno.getText().toString());
                            intent.putExtra("hno", etHno.getText().toString());
                            intent.putExtra("pno", etPno.getText().toString());
                            startActivity(intent);


                    } else {
                        Toast.makeText(delivery.this, "Please Enter Your Name ", Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(delivery.this, "Please Street Number", Toast.LENGTH_LONG).show();
                }

            } else {
                Toast.makeText(delivery.this, "Please Enter House Number", Toast.LENGTH_LONG).show();
            }

        } else{
            Toast.makeText(delivery.this, "Please Enter Mobile Number", Toast.LENGTH_LONG).show();

        }

    }
    }
