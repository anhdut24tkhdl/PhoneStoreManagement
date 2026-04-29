package com.phonestore.model;

public class Warranty {
    private int id;
    private int productId;
    private Integer customerId;
    private String issueDate;
    private String expiryDate;
    private String status;
    private String note;
    private String productName;
    private String customerName;

    public Warranty() {}

    public Warranty(int id, int productId, Integer customerId, String issueDate, String expiryDate, String status,
                    String note, String productName, String customerName) {
        this.id = id;
        this.productId = productId;
        this.customerId = customerId;
        this.issueDate = issueDate;
        this.expiryDate = expiryDate;
        this.status = status;
        this.note = note;
        this.productName = productName;
        this.customerName = customerName;
    }

    public int getId() { return id; }
    public int getProductId() { return productId; }
    public Integer getCustomerId() { return customerId; }
    public String getIssueDate() { return issueDate; }
    public String getExpiryDate() { return expiryDate; }
    public String getStatus() { return status; }
    public String getNote() { return note; }
    public String getProductName() { return productName; }
    public String getCustomerName() { return customerName; }
}
