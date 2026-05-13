package Service;

import DAO.SanPhamDAO;

import Model.SanPham;
import java.util.ArrayList;

public class SanPhamService {

    private SanPhamDAO sanPhamDAO = new SanPhamDAO();

    public ArrayList<SanPham> getAllSanPham() {
        return sanPhamDAO.getAllSanPham();
    }

    public boolean kiemTraSanPham(SanPham sp) {
        if (sp.getTenSP() == null || sp.getTenSP().trim().isEmpty()) {
            return false;
        }

        if (sp.getMaHang() <= 0) {
            return false;
        }

        if (sp.getGiaBan() <= 0) {
            return false;
        }

        if (sp.getSoLuong() < 0) {
            return false;
        }

        return true;
    }

    public boolean themSanPham(SanPham sp) {
        if (!kiemTraSanPham(sp)) {
            return false;
        }

        return sanPhamDAO.themSanPham(sp);
    }

    public boolean suaSanPham(SanPham sp) {
        if (sp.getMaSP() <= 0) {
            return false;
        }

        if (!kiemTraSanPham(sp)) {
            return false;
        }

        return sanPhamDAO.suaSanPham(sp);
    }

    public boolean xoaSanPham(int maSP) {
        if (maSP <= 0) {
            return false;
        }

        return sanPhamDAO.xoaSanPham(maSP);
    }

    public double tinhThanhTien(int soLuong, double donGia) {
        return soLuong * donGia;
    }
    
    public boolean giamSoLuong(int maSP, int soLuongBan) {
        if (maSP <= 0 || soLuongBan <= 0) {
            return false;
        }

        return sanPhamDAO.giamSoLuong(maSP, soLuongBan);
    }
}