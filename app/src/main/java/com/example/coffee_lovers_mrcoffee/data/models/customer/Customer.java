package com.example.coffee_lovers_mrcoffee.data.models.customer;

import com.example.coffee_lovers_mrcoffee.data.enums.Gender;

import java.util.Date;

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

}
