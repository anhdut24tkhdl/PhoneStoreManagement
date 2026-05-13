package DAO;

import Database.ConnectionDb;
import Model.HangDienThoai;
import java.sql.*;
import java.util.ArrayList;

public class HangDienThoaiDAO {

    public ArrayList<HangDienThoai> getAllHangDienThoai() {
        ArrayList<HangDienThoai> list = new ArrayList<>();
        String sql = "SELECT * FROM HangDienThoai";

        try {
            Connection conn = ConnectionDb.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                HangDienThoai h = new HangDienThoai();
                h.setMaHang(rs.getInt("MaHang"));
                h.setTenHang(rs.getString("TenHang"));
                list.add(h);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public boolean themHangDienThoai(HangDienThoai h) {
        String sql = "INSERT INTO HangDienThoai(TenHang) VALUES (?)";

        try {
            Connection conn = ConnectionDb.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, h.getTenHang());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean suaHangDienThoai(HangDienThoai h) {
        String sql = "UPDATE HangDienThoai SET TenHang=? WHERE MaHang=?";

        try {
            Connection conn = ConnectionDb.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, h.getTenHang());
            ps.setInt(2, h.getMaHang());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean xoaHangDienThoai(int maHang) {
        String sql = "DELETE FROM HangDienThoai WHERE MaHang=?";

        try {
            Connection conn = ConnectionDb.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, maHang);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}