package com.phonestore.model;

public class Employee {
    private int id;
    private String fullName;
    private String username;
    private String role;
    private String phone;

    public Employee() {}

    public Employee(int id, String fullName, String username, String role, String phone) {
        this.id = id;
        this.fullName = fullName;
        this.username = username;
        this.role = role;
        this.phone = phone;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    @Override
    public String toString() { return id + " - " + fullName; }
}
