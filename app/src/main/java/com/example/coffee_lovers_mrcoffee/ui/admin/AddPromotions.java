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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;

//add new promotion to the system
public class AddPromotions extends AppCompatActivity {

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_promotions);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("     New Promotion");
        getSupportActionBar().setIcon(R.drawable.ic_menu);
        //database reference
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

        //image choosing button click listner
        fabSelectImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Filechooser();
            }
        });

        //date choosing function
        txtStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int StartYear = calendar.get(Calendar.YEAR);
                int StartMonth = calendar.get(Calendar.MONTH);
                int StartDay = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddPromotions.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        txtStartDate.setText(SimpleDateFormat.getDateInstance().format(calendar.getTime()));
                    }
                }, StartYear,StartMonth,StartDay);
                datePickerDialog.show();
            }
        });
        //date choosing function
        txtEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int EndYear = calendar.get(Calendar.YEAR);
                int EndMonth = calendar.get(Calendar.MONTH);
                int EndDay = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddPromotions.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        txtEndDate.setText(SimpleDateFormat.getDateInstance().format(calendar.getTime()));
                    }
                }, EndYear,EndMonth, EndDay);
                datePickerDialog.show();
            }
        });
    }

    //new image choosing function
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
    //get image extention
    private String getExtension(Uri uri){
        ContentResolver cr=getContentResolver();
        MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
    }
    //image file uploader
    private void Fileuploader() {
        String imageID;
        imageID = System.currentTimeMillis()+"."+getExtension(imguri);
        StorageReference Ref=mStorageRef.child(imageID);


            uploadTask = Ref.putFile(imguri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //Get a URL to the Uploaded content
                            Toast.makeText(AddPromotions.this, "Image Uploaded succesFully",Toast.LENGTH_LONG).show();

                            Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    downloadUrl = uri.toString();
                                    obPromo.setImageUrl(downloadUrl);
                                    obPromo.setImageID(imageID);
                                    db.push().setValue(obPromo);
                                    Toast.makeText(getApplicationContext(), "Post Added Successfully", Toast.LENGTH_SHORT).show();
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
    //upload function to the data base
    public void AddPromotion(View view){

        db = FirebaseDatabase.getInstance().getReference().child("Promotion");




        try {

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
                if(uploadTask != null && uploadTask.isInProgress()){
                    Toast.makeText(AddPromotions.this, "Upload in progress", Toast.LENGTH_LONG).show();
                }else {
                    Fileuploader();
                }

                obPromo.setName(txtName.getText().toString().trim());
                obPromo.setPrice(Float.parseFloat(txtPrice.getText().toString().trim()));
                obPromo.setStartDate(txtStartDate.getText().toString().trim());
                obPromo.setEndDate(txtEndDate.getText().toString().trim());
                obPromo.setDescription(txtDescription.getText().toString().trim());

            }

        }catch (NumberFormatException e){
            Toast.makeText(getApplicationContext(), "Invalid Inputs", Toast.LENGTH_SHORT).show();
        }
    }

}