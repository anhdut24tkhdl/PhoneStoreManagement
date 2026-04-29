package com.phonestore.db;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String DB_DIR = "data";
    private static final String DB_URL = "jdbc:sqlite:data/phone_store.db";

    public static Connection getConnection() throws SQLException {
        ensureDirectory();
        return DriverManager.getConnection(DB_URL);
    }

    private static void ensureDirectory() {
        File dir = new File(DB_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }
}
