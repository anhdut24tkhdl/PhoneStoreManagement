package DAO;

import Database.ConnectionDb;
import Model.PhieuNhap;
import java.sql.*;
import java.util.ArrayList;

public class PhieuNhapDAO {

    public ArrayList<PhieuNhap> getAllPhieuNhap() {
        ArrayList<PhieuNhap> list = new ArrayList<>();
        String sql = "SELECT * FROM PhieuNhap";

        try {
            Connection conn = ConnectionDb.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                PhieuNhap pn = new PhieuNhap();

                pn.setMaPN(rs.getInt("MaPN"));
                pn.setMaNV(rs.getInt("MaNV"));
                pn.setNgayNhap(rs.getTimestamp("NgayNhap"));
                pn.setTongTien(rs.getDouble("TongTien"));

                list.add(pn);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public boolean themPhieuNhap(PhieuNhap pn) {
        String sql = "INSERT INTO PhieuNhap(MaNV, TongTien) VALUES (?, ?)";

        try {
            Connection conn = ConnectionDb.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, pn.getMaNV());
            ps.setDouble(2, pn.getTongTien());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean suaPhieuNhap(PhieuNhap pn) {
        String sql = "UPDATE PhieuNhap SET MaNV=?, TongTien=? WHERE MaPN=?";

        try {
            Connection conn = ConnectionDb.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, pn.getMaNV());
            ps.setDouble(2, pn.getTongTien());
            ps.setInt(3, pn.getMaPN());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean xoaPhieuNhap(int maPN) {
        String sql = "DELETE FROM PhieuNhap WHERE MaPN=?";

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