package com.phonestore.dao;

import com.phonestore.db.DBConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.Map;

public class StatsDAO {
    public Map<String, String> getDashboardStats() {
        Map<String, String> stats = new LinkedHashMap<>();
        stats.put("Số sản phẩm", getScalar("SELECT COUNT(*) FROM products"));
        stats.put("Tổng tồn kho", getScalar("SELECT COALESCE(SUM(stock),0) FROM products"));
        stats.put("Số khách hàng", getScalar("SELECT COUNT(*) FROM customers"));
        stats.put("Số nhà cung cấp", getScalar("SELECT COUNT(*) FROM suppliers"));
        stats.put("Số nhân viên", getScalar("SELECT COUNT(*) FROM employees"));
        stats.put("Tổng doanh thu", formatMoney(getScalarDouble("SELECT COALESCE(SUM(final_amount),0) FROM sales")));
        stats.put("Tổng nhập hàng", formatMoney(getScalarDouble("SELECT COALESCE(SUM(total_amount),0) FROM purchase_orders")));
        stats.put("Số phiếu bảo hành", getScalar("SELECT COUNT(*) FROM warranties"));
        return stats;
    }

    private String getScalar(String sql) {
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            return String.valueOf(rs.getObject(1));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private double getScalarDouble(String sql) {
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            return rs.getDouble(1);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String formatMoney(double value) {
        return String.format("%,.0f VND", value);
    }
}
