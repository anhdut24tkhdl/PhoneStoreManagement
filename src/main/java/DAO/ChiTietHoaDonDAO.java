package DAO;

import Database.ConnectionDb;
import Model.ChiTietHoaDon;
import java.sql.*;
import java.util.ArrayList;

public class ChiTietHoaDonDAO {

    public ArrayList<ChiTietHoaDon> getAllChiTietHoaDon() {
        ArrayList<ChiTietHoaDon> list = new ArrayList<>();
        String sql = "SELECT * FROM ChiTietHoaDon";

        try {
            Connection conn = ConnectionDb.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ChiTietHoaDon ct = new ChiTietHoaDon();

                ct.setMaHD(rs.getInt("MaHD"));
                ct.setMaSP(rs.getInt("MaSP"));
                ct.setSoLuong(rs.getInt("SoLuong"));
                ct.setDonGia(rs.getDouble("DonGia"));
                ct.setThanhTien(rs.getDouble("ThanhTien"));

                list.add(ct);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public ArrayList<ChiTietHoaDon> getByMaHD(int maHD) {
        ArrayList<ChiTietHoaDon> list = new ArrayList<>();
        String sql = "SELECT * FROM ChiTietHoaDon WHERE MaHD=?";

        try {
            Connection conn = ConnectionDb.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, maHD);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ChiTietHoaDon ct = new ChiTietHoaDon();

                ct.setMaHD(rs.getInt("MaHD"));
                ct.setMaSP(rs.getInt("MaSP"));
                ct.setSoLuong(rs.getInt("SoLuong"));
                ct.setDonGia(rs.getDouble("DonGia"));
                ct.setThanhTien(rs.getDouble("ThanhTien"));

                list.add(ct);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public boolean themChiTietHoaDon(ChiTietHoaDon ct) {
        String sql = "INSERT INTO ChiTietHoaDon(MaHD, MaSP, SoLuong, DonGia, ThanhTien) VALUES (?, ?, ?, ?, ?)";

        try {
            Connection conn = ConnectionDb.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, ct.getMaHD());
            ps.setInt(2, ct.getMaSP());
            ps.setInt(3, ct.getSoLuong());
            ps.setDouble(4, ct.getDonGia());
            ps.setDouble(5, ct.getThanhTien());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean suaChiTietHoaDon(ChiTietHoaDon ct) {
        String sql = "UPDATE ChiTietHoaDon SET SoLuong=?, DonGia=?, ThanhTien=? WHERE MaHD=? AND MaSP=?";

        try {
            Connection conn = ConnectionDb.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, ct.getSoLuong());
            ps.setDouble(2, ct.getDonGia());
            ps.setDouble(3, ct.getThanhTien());
            ps.setInt(4, ct.getMaHD());
            ps.setInt(5, ct.getMaSP());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean xoaChiTietHoaDon(int maHD, int maSP) {
        String sql = "DELETE FROM ChiTietHoaDon WHERE MaHD=? AND MaSP=?";

        try {
            Connection conn = ConnectionDb.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, maHD);
            ps.setInt(2, maSP);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean xoaChiTietTheoHoaDon(int maHD) {
        String sql = "DELETE FROM ChiTietHoaDon WHERE MaHD=?";

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