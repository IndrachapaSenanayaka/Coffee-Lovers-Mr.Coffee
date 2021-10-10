package com.example.coffee_lovers_mrcoffee.data.models.customer;

import com.example.coffee_lovers_mrcoffee.data.enums.Gender;
import com.google.firebase.firestore.DocumentReference;

import java.util.Date;
import java.util.List;

public class Customer {

    public static final Customer NULL = new Customer();


    public String id;
    public String firstName;
    public String lastName;
    public String email;
    public String phoneNumber;
    public Date birthday;
    public String password;
    public Gender gender;
    public List<DocumentReference> favorites;
    public List<String> favorites2;

}
