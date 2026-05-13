package DAO;

import Database.ConnectionDb;
import Model.NhanVien;
import java.sql.*;
import java.util.ArrayList;

public class NhanVienDAO {

    public ArrayList<NhanVien> getAllNhanVien() {
        ArrayList<NhanVien> list = new ArrayList<>();
        String sql = "SELECT * FROM NhanVien";

        try {
            Connection conn = ConnectionDb.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                NhanVien nv = new NhanVien();

                nv.setMaNV(rs.getInt("MaNV"));
                nv.setHoTen(rs.getString("HoTen"));
                nv.setGioiTinh(rs.getString("GioiTinh"));
                nv.setSdt(rs.getString("SDT"));
                nv.setDiaChi(rs.getString("DiaChi"));
                nv.setChucVu(rs.getString("ChucVu"));

                list.add(nv);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public boolean themNhanVien(NhanVien nv) {
        String sql = "INSERT INTO NhanVien(HoTen, GioiTinh, SDT, DiaChi, ChucVu) VALUES (?, ?, ?, ?, ?)";

        try {
            Connection conn = ConnectionDb.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, nv.getHoTen());
            ps.setString(2, nv.getGioiTinh());
            ps.setString(3, nv.getSdt());
            ps.setString(4, nv.getDiaChi());
            ps.setString(5, nv.getChucVu());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean suaNhanVien(NhanVien nv) {
        String sql = "UPDATE NhanVien SET HoTen=?, GioiTinh=?, SDT=?, DiaChi=?, ChucVu=? WHERE MaNV=?";

        try {
            Connection conn = ConnectionDb.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, nv.getHoTen());
            ps.setString(2, nv.getGioiTinh());
            ps.setString(3, nv.getSdt());
            ps.setString(4, nv.getDiaChi());
            ps.setString(5, nv.getChucVu());
            ps.setInt(6, nv.getMaNV());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean xoaNhanVien(int maNV) {
        String sql = "DELETE FROM NhanVien WHERE MaNV=?";

        try {
            Connection conn = ConnectionDb.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, maNV);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}