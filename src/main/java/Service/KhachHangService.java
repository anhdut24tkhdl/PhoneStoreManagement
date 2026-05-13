package Service;

import DAO.KhachHangDAO;
import Model.KhachHang;
import java.util.ArrayList;

public class KhachHangService {

    private KhachHangDAO khachHangDAO = new KhachHangDAO();

    public ArrayList<KhachHang> getAllKhachHang() {
        return khachHangDAO.getAllKhachHang();
    }

    public boolean kiemTraKhachHang(KhachHang kh) {
        if (kh.getHoTen() == null || kh.getHoTen().trim().isEmpty()) {
            return false;
        }

        if (kh.getSdt() == null || kh.getSdt().trim().isEmpty()) {
            return false;
        }

        return true;
    }

    public boolean themKhachHang(KhachHang kh) {
        if (!kiemTraKhachHang(kh)) {
            return false;
        }

        return khachHangDAO.themKhachHang(kh);
    }

    public boolean suaKhachHang(KhachHang kh) {
        if (kh.getMaKH() <= 0) {
            return false;
        }

        if (!kiemTraKhachHang(kh)) {
            return false;
        }

        return khachHangDAO.suaKhachHang(kh);
    }

    public boolean xoaKhachHang(int maKH) {
        if (maKH <= 0) {
            return false;
        }

        return khachHangDAO.xoaKhachHang(maKH);
    }
}