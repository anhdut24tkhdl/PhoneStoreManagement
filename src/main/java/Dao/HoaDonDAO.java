package DAO;

import Database.ConnectionDb;
import Model.HoaDon;
import java.sql.*;
import java.util.ArrayList;

public class HoaDonDAO {

    public ArrayList<HoaDon> getAllHoaDon() {
        ArrayList<HoaDon> list = new ArrayList<>();
        String sql = "SELECT * FROM HoaDon";

        try {
            Connection conn = ConnectionDb.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                HoaDon hd = new HoaDon();
                hd.setMaHD(rs.getInt("MaHD"));
                hd.setMaKH(rs.getInt("MaKH"));
                hd.setMaNV(rs.getInt("MaNV"));
                hd.setNgayLap(rs.getTimestamp("NgayLap"));
                hd.setTongTien(rs.getDouble("TongTien"));
                list.add(hd);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public boolean themHoaDon(HoaDon hd) {
        String sql = "INSERT INTO HoaDon(MaKH, MaNV, TongTien) VALUES (?, ?, ?)";

        try {
            Connection conn = ConnectionDb.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, hd.getMaKH());
            ps.setInt(2, hd.getMaNV());
            ps.setDouble(3, hd.getTongTien());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean suaHoaDon(HoaDon hd) {
        String sql = "UPDATE HoaDon SET MaKH=?, MaNV=?, TongTien=? WHERE MaHD=?";

        try {
            Connection conn = ConnectionDb.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, hd.getMaKH());
            ps.setInt(2, hd.getMaNV());
            ps.setDouble(3, hd.getTongTien());
            ps.setInt(4, hd.getMaHD());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean xoaHoaDon(int maHD) {
        String sql = "DELETE FROM HoaDon WHERE MaHD=?";

        try {
            Connection conn = ConnectionDb.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, maHD);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}