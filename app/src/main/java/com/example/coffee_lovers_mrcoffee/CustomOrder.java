package com.example.coffee_lovers_mrcoffee;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CustomOrder extends AppCompatActivity {

    private TextView price;
    private float sizePrice=50.00f, milkPrice=75.00f, espressoPrice=55.00f, flavorsPrice=25.00f;
    private Integer sizePosition=0, milkPosition=0, espressoPosition=0, flavorsPosition=0;
    private float totalPrice=0.00f;

    String[] size = {"Short          50/=","Tall          100/=","Grande          150/=","Venti          200/="};
    String[] milk = {"Whole Milk          75/=","Heavy Cream          100/=","Almond          125/=","Coconut          50/=","Oatmilk          120/=","Soy          85/="};
    String[] espresso = {"1 Shots          55/=","2 Shots          110/=","3 Shots          140/=","4 Shots          215/="};
    String[] flavors = {"Apple Brown Sugar Syrup   25/=","Brown Sugar Syrup   15/=","Caramel Syrup   30/=","Cinnamon Dolce Syrup   37/=","Hazelnut Syrup   48/=","Peppermint Syrup   50/=","Raspberry Syrup   45/="};

    AutoCompleteTextView TextView_size, TextView_milk, TextView_espresso, TextView_flavors;

    ArrayAdapter<String> adapterItems_size, adapterItems_milk, adapterItems_espresso, adapterItems_flavors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_order);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("     Custom Order");
        getSupportActionBar().setIcon(R.drawable.ic_menu);

        price = findViewById(R.id.price);

        TextView_size = findViewById(R.id.autoCompleteTextView_size);
        TextView_milk = findViewById(R.id.autoCompleteTextView_milk);
        TextView_espresso = findViewById(R.id.autoCompleteTextView_espresso);
        TextView_flavors = findViewById(R.id.autoCompleteTextView_flavors);

        adapterItems_size = new ArrayAdapter<String>(this,R.layout.dropdown_item,size);
        adapterItems_milk = new ArrayAdapter<String>(this,R.layout.dropdown_item,milk);
        adapterItems_espresso = new ArrayAdapter<String>(this,R.layout.dropdown_item,espresso);
        adapterItems_flavors = new ArrayAdapter<String>(this,R.layout.dropdown_item,flavors);

        TextView_size.setAdapter(adapterItems_size);
        TextView_milk.setAdapter(adapterItems_milk);
        TextView_espresso.setAdapter(adapterItems_espresso);
        TextView_flavors.setAdapter(adapterItems_flavors);

        TextView_size.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sizePosition = position;
                TotalPrice(sizePosition, milkPosition, espressoPosition, flavorsPosition);

            }
        });

        TextView_milk.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                milkPosition = position;
                TotalPrice(sizePosition, milkPosition, espressoPosition, flavorsPosition);

            }
        });

        TextView_espresso.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                espressoPosition = position;
                TotalPrice(sizePosition, milkPosition, espressoPosition, flavorsPosition);

            }
        });

        TextView_flavors.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                flavorsPosition = position;
                TotalPrice(sizePosition, milkPosition, espressoPosition, flavorsPosition);

            }
        });
    }

    public float TotalPriceWhenSizeChange(int size){
        switch (size){
            case 0:
                sizePrice=50.00f;
                break;
            case 1:
                sizePrice=100.00f;
                break;
            case 2:
                sizePrice=150.00f;
                break;
            case 3:
                sizePrice=200.00f;
                break;
            default:
                sizePrice=50.00f;
        }

        return sizePrice;
    }

    public float TotalPriceWhenMilkChange(int milk){
        switch (milk){
            case 0:
                milkPrice=75.00f;
                break;
            case 1:
                milkPrice=100.00f;
                break;
            case 2:
                milkPrice=125.00f;
                break;
            case 3:
                milkPrice=50.00f;
                break;
            case 4:
                milkPrice=120.00f;
                break;
            case 5:
                milkPrice=85.00f;
                break;
            default:
                milkPrice=75.00f;
        }

        return milkPrice;
    }

    public float TotalPriceWhenEspressoChange(int espresso){
        switch (espresso){
            case 0:
                espressoPrice=55.00f;
                break;
            case 1:
                espressoPrice=110.00f;
                break;
            case 2:
                espressoPrice=140.00f;
                break;
            case 3:
                espressoPrice=215.00f;
                break;
            default:
                espressoPrice=55.00f;
        }

        return espressoPrice;
    }

    public float TotalPriceWhenFlavorsChange(int flavors){
        switch (flavors){
            case 0:
                flavorsPrice=25.00f;
                break;
            case 1:
                flavorsPrice=15.00f;
                break;
            case 2:
                flavorsPrice=30.00f;
                break;
            case 3:
                flavorsPrice=37.00f;
                break;
            case 4:
                flavorsPrice=48.00f;
                break;
            case 5:
                flavorsPrice=50.00f;
                break;
            case 6:
                flavorsPrice=45.00f;
                break;
            default:
                flavorsPrice=25.00f;
        }

        return flavorsPrice;
    }

    protected float TotalPrice(int size, int milk, int espresso, int flavors){


        totalPrice = TotalPriceWhenSizeChange(size) + TotalPriceWhenMilkChange(milk) + TotalPriceWhenEspressoChange(espresso) + TotalPriceWhenFlavorsChange(flavors);
        Float tPrice = totalPrice;
        price.setText(tPrice.toString());

        return totalPrice;
    }
}