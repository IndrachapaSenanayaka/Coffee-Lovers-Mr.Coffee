package com.example.appcart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class deliveryupdate extends AppCompatActivity {

    EditText etCity, etSno,etHno,etPno;
    Button submit;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_baseline_menu_24);
        setContentView(R.layout.activity_update_delivery);

        etCity = findViewById(R.id.et_city);
        etSno = findViewById(R.id.et_sno);
        etHno = findViewById(R.id.et_hno);
        etPno =  findViewById(R.id.et_pno);
        submit = findViewById(R.id.bt_submit);

        String name,sno,hno,pno;
        name =getIntent().getStringExtra("name");
        sno =getIntent().getStringExtra("sno");
        hno =getIntent().getStringExtra("hno");
        pno =getIntent().getStringExtra("pno");

        etCity.setText(name);
        etSno.setText(sno);
        etHno.setText(hno);
        etPno.setText(pno);
    }

    public void Confirm(View view) {
        firebaseDatabase = FirebaseDatabase.getInstance("https://cafechocolate-d5faf-default-rtdb.firebaseio.com/");
        databaseReference = firebaseDatabase.getReference("delivery");

        String name = etCity.getText().toString();
        String sno = etSno.getText().toString();
        String hno = etHno.getText().toString();
        String pno = etPno.getText().toString();

        del delivery = new del(name, sno, hno, pno);

        databaseReference.child(name).setValue(delivery);
        Toast.makeText(com.example.appcart.deliveryupdate.this, "Delivery Updated Successfully", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getApplicationContext(), order.class);
        intent.putExtra("name", etCity.getText().toString());
        intent.putExtra("pno", etPno.getText().toString());
        intent.putExtra("hno", etHno.getText().toString());
        startActivity(intent);
    }

    public void Delete(View view) {
        String name = etCity.getText().toString().trim();

        databaseReference.child(name).setValue(null);
        Toast.makeText(com.example.appcart.deliveryupdate.this, "Delivery Cancelled ", Toast.LENGTH_LONG).show();
        etCity.setText("");
        etSno.setText("");
        etHno.setText("");
        etPno.setText("");

        Intent intent = new Intent(getApplicationContext(), delivery.class);
        startActivity(intent);
    }
}
