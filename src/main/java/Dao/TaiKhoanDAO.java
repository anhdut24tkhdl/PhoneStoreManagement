package Dao;

import Database.ConnectionDb;
import Model.TaiKhoan;
import java.sql.*;

public class TaiKhoanDAO {

    public TaiKhoan dangNhap(String tenDangNhap, String matKhau) {
        String sql = "SELECT * FROM TaiKhoan WHERE TenDangNhap = ? AND MatKhau = ?";

        try {
            Connection conn = ConnectionDb.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, tenDangNhap);
            ps.setString(2, matKhau);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                TaiKhoan tk = new TaiKhoan();

                tk.setMaTK(rs.getInt("MaTK"));
                tk.setTenDangNhap(rs.getString("TenDangNhap"));
                tk.setMatKhau(rs.getString("MatKhau"));
                tk.setVaiTro(rs.getString("VaiTro"));

                return tk;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}