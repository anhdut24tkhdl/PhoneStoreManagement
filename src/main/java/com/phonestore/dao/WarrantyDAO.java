package com.phonestore.dao;

import com.phonestore.db.DBConnection;
import com.phonestore.model.Warranty;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WarrantyDAO {
    public List<Warranty> getAll() {
        List<Warranty> list = new ArrayList<>();
        String sql = """
            SELECT w.*, p.name product_name, c.full_name customer_name
            FROM warranties w
            JOIN products p ON w.product_id = p.id
            LEFT JOIN customers c ON w.customer_id = c.id
            ORDER BY w.id DESC
        """;
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Warranty(
                        rs.getInt("id"),
                        rs.getInt("product_id"),
                        (Integer) rs.getObject("customer_id"),
                        rs.getString("issue_date"),
                        rs.getString("expiry_date"),
                        rs.getString("status"),
                        rs.getString("note"),
                        rs.getString("product_name"),
                        rs.getString("customer_name")
                ));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public void insert(int productId, Integer customerId, String issueDate, String expiryDate, String status, String note) {
        String sql = "INSERT INTO warranties(product_id, customer_id, issue_date, expiry_date, status, note) VALUES(?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, productId);
            if (customerId == null) ps.setNull(2, Types.INTEGER); else ps.setInt(2, customerId);
            ps.setString(3, issueDate);
            ps.setString(4, expiryDate);
            ps.setString(5, status);
            ps.setString(6, note);
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(int id) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM warranties WHERE id=?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
