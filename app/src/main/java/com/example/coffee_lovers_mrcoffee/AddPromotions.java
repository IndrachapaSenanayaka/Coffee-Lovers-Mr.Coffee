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

public class AddPromotions extends AppCompatActivity {

    EditText txtName, txtPrice, txtStartDate, txtEndDate, txtDescription;
    Button btnPost;

    Promotion obPromo;

    DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_menu);
        setContentView(R.layout.activity_add_promotions);

        txtName = findViewById(R.id.textInputEditTextName);
        txtPrice = findViewById(R.id.textInputEditTextPrice);
        txtStartDate = findViewById(R.id.textInputEditTextSDate);
        txtEndDate = findViewById(R.id.textInputEditTextEDate);
        txtDescription = findViewById(R.id.textInputEditTextDescription);

        btnPost = findViewById(R.id.btn_saveChanges);

        obPromo = new Promotion();
    }

    public void Save (View view){

        db = FirebaseDatabase.getInstance().getReference().child("Promotions");

               try{
                    if(TextUtils.isEmpty(txtName.getText().toString().trim())){
                        Toast.makeText(getApplicationContext(), "Please Entere Promotion Name", Toast.LENGTH_SHORT).show();
                    }else if(TextUtils.isEmpty(txtPrice.getText().toString().trim())){
                        Toast.makeText(getApplicationContext(), "Please Enter Price", Toast.LENGTH_SHORT).show();
                    }else if(TextUtils.isEmpty(txtStartDate.getText().toString().trim())){
                        Toast.makeText(getApplicationContext(), "Please Enter Promotion Start Date", Toast.LENGTH_SHORT).show();
                    }else if(TextUtils.isEmpty(txtEndDate.getText().toString().trim())){
                        Toast.makeText(getApplicationContext(), "Please Enter Promotion End Date", Toast.LENGTH_SHORT).show();
                    }else if(TextUtils.isEmpty(txtDescription.getText().toString().trim())){
                        Toast.makeText(getApplicationContext(), "Please Enter Promotion Description", Toast.LENGTH_SHORT).show();
                    }else {
                        obPromo.setName(txtName.getText().toString().trim());
                        obPromo.setPrice(txtPrice.getText().toString().trim());
                        obPromo.setStartDate(txtStartDate.getText().toString().trim());
                        obPromo.setEndDate(txtEndDate.getText().toString().trim());
                        obPromo.setDescription(txtDescription.getText().toString().trim());

                        db.push().setValue(obPromo);
                        Toast.makeText(getApplicationContext(), "Post Added Successfully", Toast.LENGTH_SHORT).show();

                    }

               }catch (NumberFormatException e){
                    Toast.makeText(getApplicationContext(), "Invalid Inputs", Toast.LENGTH_SHORT).show();
                }
    }
}