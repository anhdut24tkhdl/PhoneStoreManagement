package com.phonestore.ui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public MainFrame() {
        setTitle("Hệ thống quản lý cửa hàng điện thoại - Java Swing");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1450, 820);
        setLocationRelativeTo(null);

        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(new Font("Segoe UI", Font.BOLD, 14));

        tabs.addTab("Tổng quan", new DashboardPanel());
        tabs.addTab("Danh mục", new CategoryPanel());
        tabs.addTab("Sản phẩm", new ProductPanel());
        tabs.addTab("Khách hàng", new CustomerPanel());
        tabs.addTab("Nhà cung cấp", new SupplierPanel());
        tabs.addTab("Nhân viên", new EmployeePanel());
        tabs.addTab("Nhập hàng", new PurchasePanel());
        tabs.addTab("Bán hàng", new SalePanel());
        tabs.addTab("Bảo hành", new WarrantyPanel());
        tabs.addTab("Đổi / Trả", new ReturnExchangePanel());

        add(tabs, BorderLayout.CENTER);
    }
}
