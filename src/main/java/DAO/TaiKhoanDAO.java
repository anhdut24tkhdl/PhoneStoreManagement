package DAO;

import Database.ConnectionDb;
import Model.TaiKhoan;
import java.sql.*;
import java.util.ArrayList;

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
                tk.setMaNV(rs.getInt("MaNV"));
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
     public ArrayList<TaiKhoan> getAllTaiKhoan() {
        ArrayList<TaiKhoan> list = new ArrayList<>();
        String sql = "SELECT * FROM TaiKhoan";

        try (Connection conn = ConnectionDb.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                TaiKhoan tk = new TaiKhoan();

                tk.setMaTK(rs.getInt("MaTK"));
                tk.setTenDangNhap(rs.getString("TenDangNhap"));
                tk.setMatKhau(rs.getString("MatKhau"));
                tk.setVaiTro(rs.getString("VaiTro"));

                list.add(tk);
            }

        } catch (Exception e) {
            System.out.println("Lỗi lấy danh sách tài khoản: " + e.getMessage());
        }

        return list;
    }

    public boolean themTaiKhoan(TaiKhoan tk) {
        String sql = "INSERT INTO TaiKhoan (TenDangNhap, MatKhau, VaiTro) VALUES (?, ?, ?)";

        try (Connection conn = ConnectionDb.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, tk.getTenDangNhap());
            ps.setString(2, tk.getMatKhau());
            ps.setString(3, tk.getVaiTro());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Lỗi thêm tài khoản: " + e.getMessage());
        }

        return false;
    }

    public boolean suaTaiKhoan(TaiKhoan tk) {
        String sql = "UPDATE TaiKhoan SET TenDangNhap = ?, MatKhau = ?, VaiTro = ? WHERE MaTK = ?";

        try (Connection conn = ConnectionDb.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, tk.getTenDangNhap());
            ps.setString(2, tk.getMatKhau());
            ps.setString(3, tk.getVaiTro());
            ps.setInt(4, tk.getMaTK());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Lỗi sửa tài khoản: " + e.getMessage());
        }

        return false;
    }

    public boolean xoaTaiKhoan(int maTK) {
        String sql = "DELETE FROM TaiKhoan WHERE MaTK = ?";

        try (Connection conn = ConnectionDb.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maTK);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Lỗi xóa tài khoản: " + e.getMessage());
        }

        return false;
    }
}