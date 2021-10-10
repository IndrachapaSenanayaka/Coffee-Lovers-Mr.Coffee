package com.example.coffee_lovers_mrcoffee;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class admin_landing_page extends AppCompatActivity {

    List<Order> list;
    FloatingActionButton floatingActionButton;
    Bitmap bmp, scaledbmp;
    int pageWidth = 1200;
    DatabaseReference db;
    Button promotions, button4;

    String id, date, name, address, itemName;
    Integer y=950;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_landing_page);

        promotions = findViewById(R.id.promotions);
        button4 = findViewById(R.id.button4);

        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.pdf_header);
        scaledbmp = Bitmap.createScaledBitmap(bmp, 1200, 518, false);


        db = FirebaseDatabase.getInstance().getReference().child("Order");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list = new ArrayList<>();
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    Order order = ds.getValue(Order.class);
                    list.add(order);
                    Log.d("TAG", order.getCustomerName());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        db.addListenerForSingleValueEvent(valueEventListener);



        promotions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(admin_landing_page.this, AdminViewPromotionsList.class);
                startActivity(intent);
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPDF();
            }
        });
    }


    private void createPDF() {

        PdfDocument orderPdfDocument = new PdfDocument();
        Paint orderPaint = new Paint();
        Paint titlePaint = new Paint();

        PdfDocument.PageInfo orderPageInfo1 = new PdfDocument.PageInfo.Builder(1200,2010,1).create();
        PdfDocument.Page orderPage1 =  orderPdfDocument.startPage(orderPageInfo1);
        Canvas canvas = orderPage1.getCanvas();

        canvas.drawBitmap(scaledbmp, 0,0,orderPaint);

        titlePaint.setTextAlign(Paint.Align.CENTER);
        titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titlePaint.setTextSize(70);
        canvas.drawText("Mr.Coffee",pageWidth/2,270,titlePaint);

        titlePaint.setTextAlign(Paint.Align.CENTER);
        titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.ITALIC));
        titlePaint.setTextSize(70);
        canvas.drawText("Order Report",pageWidth/2,500,titlePaint);

        orderPaint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText("Report No.:"+"23454", pageWidth-20,590,orderPaint);
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        canvas.drawText("Date: "+currentDate,pageWidth-20,640,orderPaint);
        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        canvas.drawText("Time: "+currentTime,pageWidth-20,690,orderPaint);

        orderPaint.setStyle(Paint.Style.STROKE);
        orderPaint.setStrokeWidth(2);
        canvas.drawRect(20,780,pageWidth-20,860,orderPaint);

        orderPaint.setTextAlign(Paint.Align.LEFT);
        orderPaint.setStyle(Paint.Style.FILL);
        canvas.drawText("Or. No.", 40,830,orderPaint);
        canvas.drawText("Date", 100,830,orderPaint);
        canvas.drawText("Customer Name", 300,830,orderPaint);
        canvas.drawText("Address", 500,830,orderPaint);
        canvas.drawText("ItemName", 700,830,orderPaint);

        canvas.drawLine(80,790,80,840,orderPaint);
        canvas.drawLine(280,790,280,840,orderPaint);
        canvas.drawLine(480,790,480,840,orderPaint);
        canvas.drawLine(680,790,680,840,orderPaint);

        db = FirebaseDatabase.getInstance().getReference().child("Order");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list = new ArrayList<>();
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    Order order = ds.getValue(Order.class);
                    list.add(order);
                    Log.d("TAG", order.getCustomerName());
                    id = order.getOrderId().toString();
                    date =order.getDate();
                    name = order.getCustomerName();
                    address = order.getAddress();
                    itemName = order.getItemName();


                    for(int index = 0; index< list.size(); index++)
                    {

                        canvas.drawText(id,40,y,orderPaint);
                        canvas.drawText(date,200,y,orderPaint);
                        canvas.drawText(name,700,y,orderPaint);
                        canvas.drawText(address,900,y,orderPaint);
                        canvas.drawText(itemName,1050,y,orderPaint);
                        y = y + 100;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        db.addListenerForSingleValueEvent(valueEventListener);

//        int y=950;
//        Order order = new Order();
//        int index;
//        for(index = 0; index< list.size(); index++)
//        {
//            order = list.get(index);
//            canvas.drawText(order.getOrderId().toString(),40,y,orderPaint);
//            canvas.drawText(order.getDate(),200,y,orderPaint);
//            canvas.drawText(order.getCustomerName(),700,y,orderPaint);
//            canvas.drawText(order.getAddress(),900,y,orderPaint);
//            canvas.drawText(order.getItemName(),1050,y,orderPaint);
//            y = y + 100;
//        }

//
//        canvas.drawText("1",40,1050,orderPaint);
//        canvas.drawText("20/03/2021",200,950,orderPaint);
//        canvas.drawText("Senanayake",700,950,orderPaint);
//        canvas.drawText("Kosgolla,Kosgolla",900,950,orderPaint);
//        canvas.drawText("Cuppuccinno",1050,950,orderPaint);
//
//        canvas.drawText("2",40,1050,orderPaint);
//        canvas.drawText("11/04/2021",200,1050,orderPaint);
//        canvas.drawText("Desanayake",700,1050,orderPaint);
//        canvas.drawText("Ibulgoda,Rathnapura",900,1050,orderPaint);
//        canvas.drawText("Ice Coffee",1050,1050,orderPaint);
//
//        canvas.drawText("3",40,1150,orderPaint);
//        canvas.drawText("21/07/2021",200,1150,orderPaint);
//        canvas.drawText("Rathnayake",700,1150,orderPaint);
//        canvas.drawText("Kirillawala,Gampaha",900,1150,orderPaint);
//        canvas.drawText("Black Coffee",1050,1150,orderPaint);
//
//        canvas.drawText("4",40,1250,orderPaint);
//        canvas.drawText("28/10/2021",200,1250,orderPaint);
//        canvas.drawText("Subasinghe",700,1250,orderPaint);
//        canvas.drawText("Kilinochchi, Ampara",900,1250,orderPaint);
//        canvas.drawText("Espresso",1050,1250,orderPaint);
//
//        canvas.drawText("5",40,1350,orderPaint);
//        canvas.drawText("4/09/2021",200,1350,orderPaint);
//        canvas.drawText("Namali",700,1350,orderPaint);
//        canvas.drawText("Uyandana, Kurunegala",900,1350,orderPaint);
//        canvas.drawText("Chocolate frappuccino",1050,1350,orderPaint);




        orderPdfDocument.finishPage(orderPage1);

        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/Download","orderReport.pdf");


        try{
            orderPdfDocument.writeTo(new FileOutputStream(file));
            Toast.makeText(getApplicationContext(), "Order Report Generated", Toast.LENGTH_SHORT).show();
        }catch (IOException e){
            e.printStackTrace();
        }

        orderPdfDocument.close();
    }


}