package com.example.coffee_lovers_mrcoffee;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class activity_Add_Product extends AppCompatActivity {

    EditText txtName, txtPrice, txtDescription;
    Button btnSave;
    DatabaseReference dbRef;
    Product product;

    //Method tto clear all user inputs
    private void clearControls() {
        txtName.setText("");
        txtPrice.setText("");
        txtDescription.setText("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        txtName = findViewById(R.id.ed_name);
        txtPrice = findViewById(R.id.ed_product_price);
        txtDescription = findViewById(R.id.ed_des);

        btnSave = findViewById(R.id.ed_btn_add);

        product = new Product();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });
    }

    public void save() {

        dbRef = FirebaseDatabase.getInstance().getReference().child("Product");

        try{
            if(TextUtils.isEmpty(txtName.getText().toString()))
                Toast.makeText(getApplicationContext(), "Please Enter Product Name", Toast.LENGTH_SHORT).show();
            else if(TextUtils.isEmpty(txtPrice.getText().toString()))
                Toast.makeText(getApplicationContext(), "Please Enter Product Price", Toast.LENGTH_SHORT).show();
            else if(TextUtils.isEmpty(txtDescription.getText().toString()))
                Toast.makeText(getApplicationContext(), "Please Enter Product Description ", Toast.LENGTH_SHORT).show();

            else{
                product.setName(txtName.getText().toString().trim());
                product.setPrice(txtPrice.getText().toString().trim());
                product.setDescription(txtDescription.getText().toString().trim());

                dbRef.push().setValue(product);
//                dbRef.child("htl1").setValue(hotel);

                Toast.makeText(getApplicationContext(), "Data Saved Successfully", Toast.LENGTH_SHORT).show();
                clearControls();
            }
        }
        catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Invalid Data", Toast.LENGTH_SHORT).show();
        }
    }
}