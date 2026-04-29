package com.phonestore.dao;

import com.phonestore.db.DBConnection;
import com.phonestore.model.ReturnExchange;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReturnExchangeDAO {
    public List<ReturnExchange> getAll() {
        List<ReturnExchange> list = new ArrayList<>();
        String sql = """
            SELECT r.*, p.name product_name, c.full_name customer_name
            FROM returns_exchange r
            JOIN products p ON r.product_id = p.id
            LEFT JOIN customers c ON r.customer_id = c.id
            ORDER BY r.id DESC
        """;
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new ReturnExchange(
                        rs.getInt("id"),
                        rs.getInt("product_id"),
                        (Integer) rs.getObject("customer_id"),
                        rs.getString("action_type"),
                        rs.getString("action_date"),
                        rs.getString("reason"),
                        rs.getString("status"),
                        rs.getString("product_name"),
                        rs.getString("customer_name")
                ));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public void insert(int productId, Integer customerId, String actionType, String actionDate, String reason, String status) {
        String sql = "INSERT INTO returns_exchange(product_id, customer_id, action_type, action_date, reason, status) VALUES(?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, productId);
            if (customerId == null) ps.setNull(2, Types.INTEGER); else ps.setInt(2, customerId);
            ps.setString(3, actionType);
            ps.setString(4, actionDate);
            ps.setString(5, reason);
            ps.setString(6, status);
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(int id) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM returns_exchange WHERE id=?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
