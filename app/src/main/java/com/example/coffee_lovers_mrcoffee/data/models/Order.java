package com.example.coffee_lovers_mrcoffee.data.models;

public class Order {
    private Integer orderId;
    private String date;
    private String customerName;
    private String address;
    private String itemName;
    private Integer quantity;
    private Float price;

    public Order() {
    }

    public Order(Integer orderId, String date, String customerName, String address, String itemName, Integer quantity, Float price) {
        this.orderId = orderId;
        this.date = date;
        this.customerName = customerName;
        this.address = address;
        this.itemName = itemName;
        this.quantity = quantity;
        this.price = price;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public String getDate() {
        return date;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getAddress() {
        return address;
    }

    public String getItemName() {
        return itemName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Float getPrice() {
        return price;
    }
}
