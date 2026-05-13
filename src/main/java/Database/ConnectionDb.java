package Database;


import java.sql.*;
public class ConnectionDb {

    private static final String URL = "jdbc:mysql://localhost:3306/phonestoremanagement";
    private static final String USER = "root";
    private static final String PASSWORD = "123123123";

    public static Connection getConnection() {
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Ket noi database thanh cong!");
        } catch (SQLException e) {
            System.out.println("Ket noi database that bai!");
           
        }

        return conn;
    }
   

    

       
    }
