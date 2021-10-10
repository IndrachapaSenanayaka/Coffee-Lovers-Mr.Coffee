package com.example.coffee_lovers_mrcoffee.ui.admin;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.coffee_lovers_mrcoffee.Constants;
import com.example.coffee_lovers_mrcoffee.R;
import com.example.coffee_lovers_mrcoffee.data.models.admin.Product;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.InputStream;
import java.util.Random;

//import org.jetbrains.annotations.NotNull;

public class AddProduct extends AppCompatActivity {

    ImageButton imageButton;
    Uri filepath;
    Bitmap bitmap;
//    CircleImageView ed_image;
    ImageView ed_image;

    EditText pName, pPrice, pDescription;
    Button btnAdd;
    CollectionReference dbRef;
    Product pdt;

    //public AddProduct(UploadTask.TaskSnapshot taskSnapshot) {
    //    this.taskSnapshot = taskSnapshot;
    //}

    //Method to clear all user inputs
    private void clearControls() {
        pName.setText("");
        pPrice.setText("");
        pDescription.setText("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        pName = findViewById(R.id.ed_name);
        pPrice = findViewById(R.id.ed_price);
        pDescription = findViewById(R.id.ed_des);

        btnAdd = findViewById(R.id.ed_add_btn);
        imageButton = findViewById(R.id.imageButton);
        ed_image = findViewById(R.id.productImage);

        pdt = new Product();

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                openFileChooser();
                Dexter.withActivity(AddProduct.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {

                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response)
                            {

                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {
                                Intent intent=new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                startActivityForResult(Intent.createChooser(intent,"Select Image File"),1);
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        }).check();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });
    }

    private void choosePicture() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1  && resultCode==RESULT_OK)
        {
            filepath=data.getData();
            try{
                InputStream inputStream=getContentResolver().openInputStream(filepath);
                bitmap= BitmapFactory.decodeStream(inputStream);
                ed_image.setImageBitmap(bitmap);
            }catch (Exception ex)
            {

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }



    public void save() {

        if(filepath == null){
            Toast.makeText(this, "Please Select a Hotel Image", Toast.LENGTH_SHORT).show();
        }else {
            dbRef = FirebaseFirestore.getInstance().collection(Constants.KEY_COLLECTION_PRODUCTS);

            try {
                if (TextUtils.isEmpty(pName.getText().toString()))
                    Toast.makeText(getApplicationContext(), "Add Product Name : ", Toast.LENGTH_SHORT).show();
                else if (TextUtils.isEmpty(pPrice.getText().toString()))
                    Toast.makeText(getApplicationContext(), "Add Product Price : ", Toast.LENGTH_SHORT).show();
                else if (TextUtils.isEmpty(pDescription.getText().toString()))
                    Toast.makeText(getApplicationContext(), "Add Product Description : ", Toast.LENGTH_SHORT).show();

                else {

                    final ProgressDialog dialog = new ProgressDialog(this);
                    dialog.setTitle("File Uploader");
                    dialog.show();

                    FirebaseStorage storage = FirebaseStorage.getInstance();
                    final StorageReference uploader = storage.getReference("ImageHotel" + new Random().nextInt(50));
                    uploader.putFile(filepath)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    uploader.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {

                                            dialog.dismiss();

                                            ed_image.setImageResource(R.drawable.ic_launcher_background);

                                            //Take inputs from the user and assigning them to this instance (pdt) of the Product...
                                            pdt.setName(pName.getText().toString().trim());
                                            pdt.setPrice(pPrice.getText().toString().trim());
                                            pdt.setDescription(pDescription.getText().toString().trim());
                                            pdt.setImage(uri.toString());

                                            //Insert into the database...
                                            dbRef.add(pdt);

                                            //Feedback to the user via a Toast...
                                            Toast.makeText(getApplicationContext(), "Product details Saved Successfully...", Toast.LENGTH_SHORT).show();
                                            clearControls();

                                        }
                                    });
                                }
                            })
                            .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                    float percent = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                                    dialog.setMessage("Uploaded :" + (int) percent + " %");
                                }
                            });

                }
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Invalid Details", Toast.LENGTH_SHORT).show();
            }
        }
    }
}