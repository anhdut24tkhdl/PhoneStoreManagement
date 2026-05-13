package DAO;

import Database.ConnectionDb;
import Model.SanPham;
import java.sql.*;
import java.util.ArrayList;

public class SanPhamDAO {

    public ArrayList<SanPham> getAllSanPham() {
        ArrayList<SanPham> list = new ArrayList<>();
        String sql = "SELECT * FROM SanPham";

        try {
            Connection conn = ConnectionDb.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                SanPham sp = new SanPham();
                sp.setMaSP(rs.getInt("MaSP"));
                sp.setTenSP(rs.getString("TenSP"));
                sp.setMaHang(rs.getInt("MaHang"));
                sp.setGiaBan(rs.getDouble("GiaBan"));
                sp.setSoLuong(rs.getInt("SoLuong"));
                sp.setMoTa(rs.getString("MoTa"));
                list.add(sp);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public boolean themSanPham(SanPham sp) {
        String sql = "INSERT INTO SanPham(TenSP, MaHang, GiaBan, SoLuong, MoTa) VALUES (?, ?, ?, ?, ?)";

        try {
            Connection conn = ConnectionDb.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, sp.getTenSP());
            ps.setInt(2, sp.getMaHang());
            ps.setDouble(3, sp.getGiaBan());
            ps.setInt(4, sp.getSoLuong());
            ps.setString(5, sp.getMoTa());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean suaSanPham(SanPham sp) {
        String sql = "UPDATE SanPham SET TenSP=?, MaHang=?, GiaBan=?, SoLuong=?, MoTa=? WHERE MaSP=?";

        try {
            Connection conn = ConnectionDb.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, sp.getTenSP());
            ps.setInt(2, sp.getMaHang());
            ps.setDouble(3, sp.getGiaBan());
            ps.setInt(4, sp.getSoLuong());
            ps.setString(5, sp.getMoTa());
            ps.setInt(6, sp.getMaSP());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean xoaSanPham(int maSP) {
        String sql = "DELETE FROM SanPham WHERE MaSP=?";

        try {
            Connection conn = ConnectionDb.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, maSP);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
    public boolean giamSoLuong(int maSP, int soLuongBan) {
        String sql = "UPDATE SanPham SET SoLuong = SoLuong - ? WHERE MaSP = ? AND SoLuong >= ?";

        try {
            Connection conn = ConnectionDb.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, soLuongBan);
            ps.setInt(2, maSP);
            ps.setInt(3, soLuongBan);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}