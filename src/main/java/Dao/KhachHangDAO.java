package DAO;

import Database.ConnectionDb;
import Model.KhachHang;
import java.sql.*;
import java.util.ArrayList;

public class KhachHangDAO {

    public ArrayList<KhachHang> getAllKhachHang() {
        ArrayList<KhachHang> list = new ArrayList<>();
        String sql = "SELECT * FROM KhachHang";

        try {
            Connection conn = ConnectionDb.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                KhachHang kh = new KhachHang();
                kh.setMaKH(rs.getInt("MaKH"));
                kh.setHoTen(rs.getString("HoTen"));
                kh.setSdt(rs.getString("SDT"));
                kh.setDiaChi(rs.getString("DiaChi"));
                list.add(kh);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public boolean themKhachHang(KhachHang kh) {
        String sql = "INSERT INTO KhachHang(HoTen, SDT, DiaChi) VALUES (?, ?, ?)";

        try {
            Connection conn = ConnectionDb.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, kh.getHoTen());
            ps.setString(2, kh.getSdt());
            ps.setString(3, kh.getDiaChi());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean suaKhachHang(KhachHang kh) {
        String sql = "UPDATE KhachHang SET HoTen=?, SDT=?, DiaChi=? WHERE MaKH=?";

        try {
            Connection conn = ConnectionDb.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, kh.getHoTen());
            ps.setString(2, kh.getSdt());
            ps.setString(3, kh.getDiaChi());
            ps.setInt(4, kh.getMaKH());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean xoaKhachHang(int maKH) {
        String sql = "DELETE FROM KhachHang WHERE MaKH=?";

        try {
            Connection conn = ConnectionDb.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, maKH);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}