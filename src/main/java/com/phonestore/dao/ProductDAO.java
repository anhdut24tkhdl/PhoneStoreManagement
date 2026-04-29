package com.phonestore.dao;

import com.phonestore.db.DBConnection;
import com.phonestore.model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    public List<Product> getAll() {
        List<Product> list = new ArrayList<>();
        String sql = """
            SELECT p.*, c.name AS category_name
            FROM products p
            LEFT JOIN categories c ON p.category_id = c.id
            ORDER BY p.id DESC
        """;
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(map(rs));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public List<Product> search(String keyword) {
        List<Product> list = new ArrayList<>();
        String sql = """
            SELECT p.*, c.name AS category_name
            FROM products p
            LEFT JOIN categories c ON p.category_id = c.id
            WHERE p.code LIKE ? OR p.name LIKE ? OR p.brand LIKE ?
            ORDER BY p.id DESC
        """;
        String key = "%" + keyword + "%";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, key);
            ps.setString(2, key);
            ps.setString(3, key);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(map(rs));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public void insert(Product p) {
        String sql = """
            INSERT INTO products(code, name, brand, color, storage, purchase_price, sale_price, stock, status, category_id)
            VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            fillStatement(ps, p);
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Lỗi thêm sản phẩm: " + e.getMessage(), e);
        }
    }

    public void update(Product p) {
        String sql = """
            UPDATE products
            SET code = ?, name = ?, brand = ?, color = ?, storage = ?, purchase_price = ?, sale_price = ?, stock = ?, status = ?, category_id = ?
            WHERE id = ?
        """;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            fillStatement(ps, p);
            ps.setInt(11, p.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Lỗi sửa sản phẩm: " + e.getMessage(), e);
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM products WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Không thể xóa sản phẩm vì có thể đang liên kết dữ liệu khác.", e);
        }
    }

    public void increaseStock(int productId, int qty) {
        updateStock(productId, qty);
    }

    public void decreaseStock(int productId, int qty) {
        updateStock(productId, -qty);
    }

    private void updateStock(int productId, int delta) {
        String sql = "UPDATE products SET stock = stock + ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, delta);
            ps.setInt(2, productId);
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Lỗi cập nhật tồn kho: " + e.getMessage(), e);
        }
    }

    private Product map(ResultSet rs) throws SQLException {
        return new Product(
                rs.getInt("id"),
                rs.getString("code"),
                rs.getString("name"),
                rs.getString("brand"),
                rs.getString("color"),
                rs.getString("storage"),
                rs.getDouble("purchase_price"),
                rs.getDouble("sale_price"),
                rs.getInt("stock"),
                rs.getString("status"),
                (Integer) rs.getObject("category_id"),
                rs.getString("category_name")
        );
    }

    private void fillStatement(PreparedStatement ps, Product p) throws SQLException {
        ps.setString(1, p.getCode());
        ps.setString(2, p.getName());
        ps.setString(3, p.getBrand());
        ps.setString(4, p.getColor());
        ps.setString(5, p.getStorage());
        ps.setDouble(6, p.getPurchasePrice());
        ps.setDouble(7, p.getSalePrice());
        ps.setInt(8, p.getStock());
        ps.setString(9, p.getStatus());
        if (p.getCategoryId() == null) {
            ps.setNull(10, Types.INTEGER);
        } else {
            ps.setInt(10, p.getCategoryId());
        }
    }
}
