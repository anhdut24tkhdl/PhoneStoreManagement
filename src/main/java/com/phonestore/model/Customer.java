package com.phonestore.model;

public class Customer {
    private int id;
    private String fullName;
    private String phone;
    private String address;
    private int loyaltyPoints;

    public Customer() {}

    public Customer(int id, String fullName, String phone, String address, int loyaltyPoints) {
        this.id = id;
        this.fullName = fullName;
        this.phone = phone;
        this.address = address;
        this.loyaltyPoints = loyaltyPoints;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public int getLoyaltyPoints() { return loyaltyPoints; }
    public void setLoyaltyPoints(int loyaltyPoints) { this.loyaltyPoints = loyaltyPoints; }

    @Override
    public String toString() { return id + " - " + fullName; }
}
