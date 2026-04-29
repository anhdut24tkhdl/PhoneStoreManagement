package com.phonestore.dao;

import com.phonestore.db.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class PurchaseDAO {
    private final ProductDAO productDAO = new ProductDAO();

    public void createPurchase(int supplierId, Integer employeeId, String orderDate, int productId, int quantity, double unitPrice, String note) {
        String orderSql = "INSERT INTO purchase_orders(supplier_id, employee_id, order_date, total_amount, note) VALUES(?, ?, ?, ?, ?)";
        String itemSql = "INSERT INTO purchase_order_items(purchase_order_id, product_id, quantity, unit_price, total) VALUES(?, ?, ?, ?, ?)";
        double total = quantity * unitPrice;

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            int purchaseOrderId;
            try (PreparedStatement ps = conn.prepareStatement(orderSql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, supplierId);
                if (employeeId == null) ps.setNull(2, Types.INTEGER); else ps.setInt(2, employeeId);
                ps.setString(3, orderDate);
                ps.setDouble(4, total);
                ps.setString(5, note);
                ps.executeUpdate();

                ResultSet keys = ps.getGeneratedKeys();
                keys.next();
                purchaseOrderId = keys.getInt(1);
            }

            try (PreparedStatement ps = conn.prepareStatement(itemSql)) {
                ps.setInt(1, purchaseOrderId);
                ps.setInt(2, productId);
                ps.setInt(3, quantity);
                ps.setDouble(4, unitPrice);
                ps.setDouble(5, total);
                ps.executeUpdate();
            }

            conn.commit();
            productDAO.increaseStock(productId, quantity);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi tạo phiếu nhập: " + e.getMessage(), e);
        }
    }

    public List<Vector<Object>> getAllPurchaseRows() {
        List<Vector<Object>> rows = new ArrayList<>();
        String sql = """
            SELECT po.id, po.order_date, s.name supplier_name, e.full_name employee_name,
                   p.name product_name, poi.quantity, poi.unit_price, poi.total, po.note
            FROM purchase_orders po
            JOIN suppliers s ON po.supplier_id = s.id
            LEFT JOIN employees e ON po.employee_id = e.id
            JOIN purchase_order_items poi ON po.id = poi.purchase_order_id
            JOIN products p ON poi.product_id = p.id
            ORDER BY po.id DESC
        """;
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getInt("id"));
                row.add(rs.getString("order_date"));
                row.add(rs.getString("supplier_name"));
                row.add(rs.getString("employee_name"));
                row.add(rs.getString("product_name"));
                row.add(rs.getInt("quantity"));
                row.add(rs.getDouble("unit_price"));
                row.add(rs.getDouble("total"));
                row.add(rs.getString("note"));
                rows.add(row);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return rows;
    }
}
