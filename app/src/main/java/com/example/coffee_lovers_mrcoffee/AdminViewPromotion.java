package com.example.coffee_lovers_mrcoffee;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class AdminViewPromotion extends AppCompatActivity {

    private ImageView imageView;
    private TextView pName, pDescription, sDate, eDate, pPrice;
    private Button btnEdit, btnDelete;

    private String name, price, startDate, endDate, description, imageUrl;
    
    DatabaseReference ref,DataRef;
    StorageReference StorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_promotion);

        imageView=findViewById(R.id.image_view_banner_admin);
        pName=findViewById(R.id.tv_promotionName_admin);
        pDescription=findViewById(R.id.tv_description_admin);
        sDate=findViewById(R.id.tv_start_date_admin);
        eDate=findViewById(R.id.tv_end_date_admin);
        pPrice=findViewById(R.id.tv_price_admin);
        btnEdit=findViewById(R.id.btn_editPromotion_admin);
        btnDelete=findViewById(R.id.btn_deletePromotion_admin);

        ref= FirebaseDatabase.getInstance().getReference().child("Promotion");

        String PromotionKey=getIntent().getStringExtra("PromotionKey");
        DataRef= FirebaseDatabase.getInstance().getReference().child("Promotion").child(PromotionKey);
        //StorageRef=FirebaseDatabase.getInstance().getReference().child("PromotionBanner").child();

        ref.child(PromotionKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){
                    name=dataSnapshot.child("name").getValue().toString();
                    price=dataSnapshot.child("price").getValue().toString();
                    startDate=dataSnapshot.child("startDate").getValue().toString();
                    endDate=dataSnapshot.child("endDate").getValue().toString();
                    description=dataSnapshot.child("description").getValue().toString();
                    imageUrl=dataSnapshot.child("imageUrl").getValue().toString();

                    Picasso.get().load(imageUrl).into(imageView);
                    pName.setText(name);
                    pDescription.setText(description);
                    pPrice.setText(price);
                    sDate.setText(startDate);
                    eDate.setText(endDate);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DataRef.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                          startActivity(new Intent(getApplicationContext(),CustomerViewPromotionsList.class));
//                        StorageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void unused) {
//                                startActivity(new Intent(getApplicationContext(),CustomerViewPromotionsList.class));
//                            }
//                        });
                    }
                });
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AdminViewPromotion.this,UpdatePromotions.class);
                intent.putExtra("PromotionKey",PromotionKey);
                intent.putExtra("name",name);
                intent.putExtra("price",price);
                intent.putExtra("startDate",startDate);
                intent.putExtra("endDate",endDate);
                intent.putExtra("description",description);
                intent.putExtra("imageUrl",imageUrl);
                startActivity(intent);

            }
        });

        
    }
}