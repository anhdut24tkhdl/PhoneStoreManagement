package com.phonestore.model;

public class ReturnExchange {
    private int id;
    private int productId;
    private Integer customerId;
    private String actionType;
    private String actionDate;
    private String reason;
    private String status;
    private String productName;
    private String customerName;

    public ReturnExchange() {}

    public ReturnExchange(int id, int productId, Integer customerId, String actionType, String actionDate,
                          String reason, String status, String productName, String customerName) {
        this.id = id;
        this.productId = productId;
        this.customerId = customerId;
        this.actionType = actionType;
        this.actionDate = actionDate;
        this.reason = reason;
        this.status = status;
        this.productName = productName;
        this.customerName = customerName;
    }

    public int getId() { return id; }
    public int getProductId() { return productId; }
    public Integer getCustomerId() { return customerId; }
    public String getActionType() { return actionType; }
    public String getActionDate() { return actionDate; }
    public String getReason() { return reason; }
    public String getStatus() { return status; }
    public String getProductName() { return productName; }
    public String getCustomerName() { return customerName; }
}
