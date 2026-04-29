package com.phonestore.model;

public class Product {
    private int id;
    private String code;
    private String name;
    private String brand;
    private String color;
    private String storage;
    private double purchasePrice;
    private double salePrice;
    private int stock;
    private String status;
    private Integer categoryId;
    private String categoryName;

    public Product() {}

    public Product(int id, String code, String name, String brand, String color, String storage,
                   double purchasePrice, double salePrice, int stock, String status, Integer categoryId, String categoryName) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.brand = brand;
        this.color = color;
        this.storage = storage;
        this.purchasePrice = purchasePrice;
        this.salePrice = salePrice;
        this.stock = stock;
        this.status = status;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
    public String getStorage() { return storage; }
    public void setStorage(String storage) { this.storage = storage; }
    public double getPurchasePrice() { return purchasePrice; }
    public void setPurchasePrice(double purchasePrice) { this.purchasePrice = purchasePrice; }
    public double getSalePrice() { return salePrice; }
    public void setSalePrice(double salePrice) { this.salePrice = salePrice; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Integer getCategoryId() { return categoryId; }
    public void setCategoryId(Integer categoryId) { this.categoryId = categoryId; }
    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }


    @Override
    public String toString() {
        return id + " - " + name;
    }
}
