package com.phonestore.dao;

import com.phonestore.db.DBConnection;
import com.phonestore.model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class SaleDAO {
    private final ProductDAO productDAO = new ProductDAO();

    public void createSale(Integer customerId, Integer employeeId, String saleDate, int productId, int quantity, double discount, String note) {
        Product product = productDAO.getAll().stream()
                .filter(p -> p.getId() == productId)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm"));

        if (product.getStock() < quantity) {
            throw new RuntimeException("Số lượng tồn kho không đủ");
        }

        double total = quantity * product.getSalePrice();
        double finalAmount = total - discount;

        String saleSql = "INSERT INTO sales(customer_id, employee_id, sale_date, total_amount, discount, final_amount, note) VALUES(?, ?, ?, ?, ?, ?, ?)";
        String itemSql = "INSERT INTO sale_items(sale_id, product_id, quantity, unit_price, total) VALUES(?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            int saleId;
            try (PreparedStatement ps = conn.prepareStatement(saleSql, Statement.RETURN_GENERATED_KEYS)) {
                if (customerId == null) ps.setNull(1, Types.INTEGER); else ps.setInt(1, customerId);
                if (employeeId == null) ps.setNull(2, Types.INTEGER); else ps.setInt(2, employeeId);
                ps.setString(3, saleDate);
                ps.setDouble(4, total);
                ps.setDouble(5, discount);
                ps.setDouble(6, finalAmount);
                ps.setString(7, note);
                ps.executeUpdate();

                ResultSet keys = ps.getGeneratedKeys();
                keys.next();
                saleId = keys.getInt(1);
            }

            try (PreparedStatement ps = conn.prepareStatement(itemSql)) {
                ps.setInt(1, saleId);
                ps.setInt(2, productId);
                ps.setInt(3, quantity);
                ps.setDouble(4, product.getSalePrice());
                ps.setDouble(5, total);
                ps.executeUpdate();
            }

            conn.commit();
            productDAO.decreaseStock(productId, quantity);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi tạo hóa đơn bán: " + e.getMessage(), e);
        }
    }

    public List<Vector<Object>> getAllSaleRows() {
        List<Vector<Object>> rows = new ArrayList<>();
        String sql = """
            SELECT s.id, s.sale_date, c.full_name customer_name, e.full_name employee_name,
                   p.name product_name, si.quantity, si.unit_price, s.discount, s.final_amount, s.note
            FROM sales s
            LEFT JOIN customers c ON s.customer_id = c.id
            LEFT JOIN employees e ON s.employee_id = e.id
            JOIN sale_items si ON s.id = si.sale_id
            JOIN products p ON si.product_id = p.id
            ORDER BY s.id DESC
        """;
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getInt("id"));
                row.add(rs.getString("sale_date"));
                row.add(rs.getString("customer_name"));
                row.add(rs.getString("employee_name"));
                row.add(rs.getString("product_name"));
                row.add(rs.getInt("quantity"));
                row.add(rs.getDouble("unit_price"));
                row.add(rs.getDouble("discount"));
                row.add(rs.getDouble("final_amount"));
                row.add(rs.getString("note"));
                rows.add(row);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return rows;
    }
}
