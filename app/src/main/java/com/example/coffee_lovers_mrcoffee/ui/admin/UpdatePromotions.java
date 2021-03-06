package com.example.coffee_lovers_mrcoffee.ui.admin;

import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.coffee_lovers_mrcoffee.data.models.Promotion;
import com.example.coffee_lovers_mrcoffee.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class UpdatePromotions extends AppCompatActivity {

    private EditText txtName, txtPrice, txtStartDate, txtEndDate, txtDescription;
    private ImageView ivBanner;
    private FloatingActionButton fabSelectImg;
    private Button btnPost;

    Promotion obPromo;

    StorageReference mStorageRef;
    DatabaseReference db;
    private Uri imguri;
    private StorageTask uploadTask;
    private String downloadUrl;
    private String PromotionKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_promotions);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("     Update Promotion");
        getSupportActionBar().setIcon(R.drawable.ic_menu);

        mStorageRef= FirebaseStorage.getInstance().getReference("PromotionBanner");

        txtName = findViewById(R.id.textInputEditTextName);
        txtPrice = findViewById(R.id.textInputEditTextPrice);
        txtStartDate = findViewById(R.id.textInputEditTextSDate);
        txtEndDate = findViewById(R.id.textInputEditTextEDate);
        txtDescription = findViewById(R.id.textInputEditTextDescription);

        ivBanner = findViewById(R.id.imageView_addPromotion);

        fabSelectImg = findViewById(R.id.floatingActionButton_addPromotion);
        btnPost = findViewById(R.id.btn_addPromotion);

        obPromo = new Promotion();

        Calendar calendar = Calendar.getInstance();

        PromotionKey = getIntent().getStringExtra("PromotionKey");
        txtName.setText(getIntent().getStringExtra("name"));
        txtPrice.setText(getIntent().getStringExtra("price"));
        txtStartDate.setText(getIntent().getStringExtra("startDate"));
        txtEndDate.setText(getIntent().getStringExtra("endDate"));
        txtDescription.setText(getIntent().getStringExtra("description"));
        String imageUrl = getIntent().getStringExtra("imageUrl");
        Picasso.get().load(imageUrl).into(ivBanner);


        fabSelectImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Filechooser();
            }
        });


        txtStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int StartYear = calendar.get(Calendar.YEAR);
                int StartMonth = calendar.get(Calendar.MONTH);
                int StartDay = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(UpdatePromotions.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        txtStartDate.setText(SimpleDateFormat.getDateInstance().format(calendar.getTime()));
                    }
                }, StartYear,StartMonth,StartDay);
                datePickerDialog.show();
            }
        });

        txtEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int EndYear = calendar.get(Calendar.YEAR);
                int EndMonth = calendar.get(Calendar.MONTH);
                int EndDay = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(UpdatePromotions.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        txtEndDate.setText(SimpleDateFormat.getDateInstance().format(calendar.getTime()));
                    }
                }, EndYear,EndMonth, EndDay);
                datePickerDialog.show();
            }
        });
    }

    private void Filechooser() {

        Intent intent=new Intent();
        intent.setType("image/'");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            imguri=data.getData();
            ivBanner.setImageURI(imguri);
        }
    }

    private String getExtension(Uri uri){
        ContentResolver cr=getContentResolver();
        MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
    }

    private void Fileuploader() {
        String imageID;
        imageID = System.currentTimeMillis()+"."+getExtension(imguri);
        StorageReference Ref=mStorageRef.child(imageID).child(PromotionKey);


        uploadTask = Ref.putFile(imguri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        //Get a URL to the Uploaded content
                        Toast.makeText(UpdatePromotions.this, "Image Uploaded succesFully",Toast.LENGTH_LONG).show();

                        Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                downloadUrl = uri.toString();
                                obPromo.setImageUrl(downloadUrl);
                                obPromo.setImageID(imageID);
                                db.setValue(obPromo);
                                Toast.makeText(getApplicationContext(), "Post Updated Successfully", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //Handle unsuccesFull uploads
                    }
                });

    }



    public void UpdatePromotion(View view){

        DatabaseReference updRef = FirebaseDatabase.getInstance().getReference().child("Promotion");
        updRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(PromotionKey)){
                    try {
                        if(uploadTask != null && uploadTask.isInProgress()){
                            Toast.makeText(UpdatePromotions.this, "Upload in progress", Toast.LENGTH_LONG).show();
                        }else {
                            Fileuploader();
                        }


                        if (TextUtils.isEmpty(txtName.getText().toString().trim())) {
                            Toast.makeText(getApplicationContext(), "Please Entere Promotion Name", Toast.LENGTH_SHORT).show();
                        } else if (TextUtils.isEmpty(txtPrice.getText().toString().trim())) {
                            Toast.makeText(getApplicationContext(), "Please Enter Price", Toast.LENGTH_SHORT).show();
                        } else if (TextUtils.isEmpty(txtStartDate.getText().toString().trim())) {
                            Toast.makeText(getApplicationContext(), "Please Enter Promotion Start Date", Toast.LENGTH_SHORT).show();
                        } else if (TextUtils.isEmpty(txtEndDate.getText().toString().trim())) {
                            Toast.makeText(getApplicationContext(), "Please Enter Promotion End Date", Toast.LENGTH_SHORT).show();
                        } else if (TextUtils.isEmpty(txtDescription.getText().toString().trim())) {
                            Toast.makeText(getApplicationContext(), "Please Enter Promotion Description", Toast.LENGTH_SHORT).show();
                        } else {


                            obPromo.setName(txtName.getText().toString().trim());
                            obPromo.setPrice(Float.parseFloat(txtPrice.getText().toString().trim()));
                            obPromo.setStartDate(txtStartDate.getText().toString().trim());
                            obPromo.setEndDate(txtEndDate.getText().toString().trim());
                            obPromo.setDescription(txtDescription.getText().toString().trim());

                            db = FirebaseDatabase.getInstance().getReference().child("Promotion").child(PromotionKey);

                        }

                    }catch (NumberFormatException e){
                        Toast.makeText(getApplicationContext(), "Invalid Inputs", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                    Toast.makeText(getApplicationContext(), "No Source to Update", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}