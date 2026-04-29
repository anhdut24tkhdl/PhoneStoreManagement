package com.phonestore.db;

import java.sql.Connection;
import java.sql.Statement;

public class DatabaseInitializer {
    public static void initialize() {
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement()) {

            st.executeUpdate("""
                CREATE TABLE IF NOT EXISTS categories (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT NOT NULL UNIQUE,
                    description TEXT
                )
            """);

            st.executeUpdate("""
                CREATE TABLE IF NOT EXISTS products (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    code TEXT NOT NULL UNIQUE,
                    name TEXT NOT NULL,
                    brand TEXT,
                    color TEXT,
                    storage TEXT,
                    purchase_price REAL NOT NULL DEFAULT 0,
                    sale_price REAL NOT NULL DEFAULT 0,
                    stock INTEGER NOT NULL DEFAULT 0,
                    status TEXT DEFAULT 'Available',
                    category_id INTEGER,
                    FOREIGN KEY (category_id) REFERENCES categories(id)
                )
            """);

            st.executeUpdate("""
                CREATE TABLE IF NOT EXISTS customers (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    full_name TEXT NOT NULL,
                    phone TEXT,
                    address TEXT,
                    loyalty_points INTEGER DEFAULT 0
                )
            """);

            st.executeUpdate("""
                CREATE TABLE IF NOT EXISTS suppliers (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT NOT NULL,
                    phone TEXT,
                    address TEXT,
                    email TEXT
                )
            """);

            st.executeUpdate("""
                CREATE TABLE IF NOT EXISTS employees (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    full_name TEXT NOT NULL,
                    username TEXT NOT NULL UNIQUE,
                    role TEXT NOT NULL,
                    phone TEXT
                )
            """);

            st.executeUpdate("""
                CREATE TABLE IF NOT EXISTS purchase_orders (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    supplier_id INTEGER NOT NULL,
                    employee_id INTEGER,
                    order_date TEXT NOT NULL,
                    total_amount REAL NOT NULL DEFAULT 0,
                    note TEXT,
                    FOREIGN KEY (supplier_id) REFERENCES suppliers(id),
                    FOREIGN KEY (employee_id) REFERENCES employees(id)
                )
            """);

            st.executeUpdate("""
                CREATE TABLE IF NOT EXISTS purchase_order_items (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    purchase_order_id INTEGER NOT NULL,
                    product_id INTEGER NOT NULL,
                    quantity INTEGER NOT NULL,
                    unit_price REAL NOT NULL,
                    total REAL NOT NULL,
                    FOREIGN KEY (purchase_order_id) REFERENCES purchase_orders(id),
                    FOREIGN KEY (product_id) REFERENCES products(id)
                )
            """);

            st.executeUpdate("""
                CREATE TABLE IF NOT EXISTS sales (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    customer_id INTEGER,
                    employee_id INTEGER,
                    sale_date TEXT NOT NULL,
                    total_amount REAL NOT NULL DEFAULT 0,
                    discount REAL NOT NULL DEFAULT 0,
                    final_amount REAL NOT NULL DEFAULT 0,
                    note TEXT,
                    FOREIGN KEY (customer_id) REFERENCES customers(id),
                    FOREIGN KEY (employee_id) REFERENCES employees(id)
                )
            """);

            st.executeUpdate("""
                CREATE TABLE IF NOT EXISTS sale_items (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    sale_id INTEGER NOT NULL,
                    product_id INTEGER NOT NULL,
                    quantity INTEGER NOT NULL,
                    unit_price REAL NOT NULL,
                    total REAL NOT NULL,
                    FOREIGN KEY (sale_id) REFERENCES sales(id),
                    FOREIGN KEY (product_id) REFERENCES products(id)
                )
            """);

            st.executeUpdate("""
                CREATE TABLE IF NOT EXISTS warranties (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    product_id INTEGER NOT NULL,
                    customer_id INTEGER,
                    issue_date TEXT NOT NULL,
                    expiry_date TEXT,
                    status TEXT,
                    note TEXT,
                    FOREIGN KEY (product_id) REFERENCES products(id),
                    FOREIGN KEY (customer_id) REFERENCES customers(id)
                )
            """);

            st.executeUpdate("""
                CREATE TABLE IF NOT EXISTS returns_exchange (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    product_id INTEGER NOT NULL,
                    customer_id INTEGER,
                    action_type TEXT NOT NULL,
                    action_date TEXT NOT NULL,
                    reason TEXT,
                    status TEXT,
                    FOREIGN KEY (product_id) REFERENCES products(id),
                    FOREIGN KEY (customer_id) REFERENCES customers(id)
                )
            """);

            seedData(st);
        } catch (Exception e) {
            throw new RuntimeException("Không thể khởi tạo database: " + e.getMessage(), e);
        }
    }

    private static void seedData(Statement st) throws Exception {
        st.executeUpdate("""
            INSERT OR IGNORE INTO categories(id, name, description) VALUES
            (1, 'Điện thoại', 'Các dòng smartphone'),
            (2, 'Phụ kiện', 'Tai nghe, sạc, cáp...')
        """);

        st.executeUpdate("""
            INSERT OR IGNORE INTO suppliers(id, name, phone, address, email) VALUES
            (1, 'Công ty Samsung Distributor', '0901000001', 'Đà Nẵng', 'samsung@supplier.com'),
            (2, 'Apple Vietnam Partner', '0901000002', 'Hà Nội', 'apple@supplier.com')
        """);

        st.executeUpdate("""
            INSERT OR IGNORE INTO employees(id, full_name, username, role, phone) VALUES
            (1, 'Nguyễn Văn Quản Lý', 'admin', 'Manager', '0909999999'),
            (2, 'Trần Thị Bán Hàng', 'sales01', 'Sales', '0908888888')
        """);

        st.executeUpdate("""
            INSERT OR IGNORE INTO customers(id, full_name, phone, address, loyalty_points) VALUES
            (1, 'Lê Minh Khách', '0911222333', 'Đà Nẵng', 10),
            (2, 'Phạm Thu Khách', '0944556677', 'Huế', 20)
        """);

        st.executeUpdate("""
            INSERT OR IGNORE INTO products(id, code, name, brand, color, storage, purchase_price, sale_price, stock, status, category_id) VALUES
            (1, 'IP15PM-256-BLK', 'iPhone 15 Pro Max', 'Apple', 'Black', '256GB', 27000000, 31000000, 8, 'Available', 1),
            (2, 'SS-S24U-256-GRY', 'Samsung S24 Ultra', 'Samsung', 'Gray', '256GB', 23000000, 28000000, 10, 'Available', 1),
            (3, 'PD20W-APL', 'Củ sạc nhanh 20W', 'Apple', 'White', '-', 250000, 450000, 25, 'Available', 2)
        """);
    }
}
