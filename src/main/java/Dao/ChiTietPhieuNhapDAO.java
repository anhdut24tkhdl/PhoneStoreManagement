    package Dao;

import Database.ConnectionDb;
import Model.ChiTietPhieuNhap;
import java.sql.*;
import java.util.ArrayList;

public class ChiTietPhieuNhapDAO {

    public ArrayList<ChiTietPhieuNhap> getAllChiTietPhieuNhap() {
        ArrayList<ChiTietPhieuNhap> list = new ArrayList<>();
        String sql = "SELECT * FROM ChiTietPhieuNhap";

        try {
            Connection conn = ConnectionDb.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ChiTietPhieuNhap ct = new ChiTietPhieuNhap();

                ct.setMaPN(rs.getInt("MaPN"));
                ct.setMaSP(rs.getInt("MaSP"));
                ct.setSoLuong(rs.getInt("SoLuong"));
                ct.setDonGiaNhap(rs.getDouble("DonGiaNhap"));
                ct.setThanhTien(rs.getDouble("ThanhTien"));

                list.add(ct);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public ArrayList<ChiTietPhieuNhap> getByMaPN(int maPN) {
        ArrayList<ChiTietPhieuNhap> list = new ArrayList<>();
        String sql = "SELECT * FROM ChiTietPhieuNhap WHERE MaPN=?";

        try {
            Connection conn = ConnectionDb.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, maPN);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ChiTietPhieuNhap ct = new ChiTietPhieuNhap();

                ct.setMaPN(rs.getInt("MaPN"));
                ct.setMaSP(rs.getInt("MaSP"));
                ct.setSoLuong(rs.getInt("SoLuong"));
                ct.setDonGiaNhap(rs.getDouble("DonGiaNhap"));
                ct.setThanhTien(rs.getDouble("ThanhTien"));

                list.add(ct);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public boolean themChiTietPhieuNhap(ChiTietPhieuNhap ct) {
        String sql = "INSERT INTO ChiTietPhieuNhap(MaPN, MaSP, SoLuong, DonGiaNhap, ThanhTien) VALUES (?, ?, ?, ?, ?)";

        try {
            Connection conn = ConnectionDb.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, ct.getMaPN());
            ps.setInt(2, ct.getMaSP());
            ps.setInt(3, ct.getSoLuong());
            ps.setDouble(4, ct.getDonGiaNhap());
            ps.setDouble(5, ct.getThanhTien());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean suaChiTietPhieuNhap(ChiTietPhieuNhap ct) {
        String sql = "UPDATE ChiTietPhieuNhap SET SoLuong=?, DonGiaNhap=?, ThanhTien=? WHERE MaPN=? AND MaSP=?";

        try {
            Connection conn = ConnectionDb.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, ct.getSoLuong());
            ps.setDouble(2, ct.getDonGiaNhap());
            ps.setDouble(3, ct.getThanhTien());
            ps.setInt(4, ct.getMaPN());
            ps.setInt(5, ct.getMaSP());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean xoaChiTietPhieuNhap(int maPN, int maSP) {
        String sql = "DELETE FROM ChiTietPhieuNhap WHERE MaPN=? AND MaSP=?";

        try {
            Connection conn = ConnectionDb.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, maPN);
            ps.setInt(2, maSP);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean xoaChiTietTheoPhieuNhap(int maPN) {
        String sql = "DELETE FROM ChiTietPhieuNhap WHERE MaPN=?";

        try {
            Connection conn = ConnectionDb.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, maPN);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}