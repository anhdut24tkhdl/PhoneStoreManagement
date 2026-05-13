package Service;

import DAO.ChiTietHoaDonDAO;
import Model.ChiTietHoaDon;
import java.util.ArrayList;

public class ChiTietHoaDonService {

    private ChiTietHoaDonDAO chiTietHoaDonDAO = new ChiTietHoaDonDAO();

    public ArrayList<ChiTietHoaDon> getAllChiTietHoaDon() {
        return chiTietHoaDonDAO.getAllChiTietHoaDon();
    }

    public ArrayList<ChiTietHoaDon> getByMaHD(int maHD) {
        if (maHD <= 0) {
            return new ArrayList<>();
        }

        return chiTietHoaDonDAO.getByMaHD(maHD);
    }

    public boolean kiemTraChiTietHoaDon(ChiTietHoaDon ct) {
        if (ct.getMaHD() <= 0) {
            return false;
        }

        if (ct.getMaSP() <= 0) {
            return false;
        }

        if (ct.getSoLuong() <= 0) {
            return false;
        }

        if (ct.getDonGia() <= 0) {
            return false;
        }

        ct.setThanhTien(ct.getSoLuong() * ct.getDonGia());

        return true;
    }

    public boolean themChiTietHoaDon(ChiTietHoaDon ct) {
        if (!kiemTraChiTietHoaDon(ct)) {
            return false;
        }

        return chiTietHoaDonDAO.themChiTietHoaDon(ct);
    }

    public boolean suaChiTietHoaDon(ChiTietHoaDon ct) {
        if (!kiemTraChiTietHoaDon(ct)) {
            return false;
        }

        return chiTietHoaDonDAO.suaChiTietHoaDon(ct);
    }

    public boolean xoaChiTietHoaDon(int maHD, int maSP) {
        if (maHD <= 0 || maSP <= 0) {
            return false;
        }

        return chiTietHoaDonDAO.xoaChiTietHoaDon(maHD, maSP);
    }

    public boolean xoaChiTietTheoHoaDon(int maHD) {
        if (maHD <= 0) {
            return false;
        }

        return chiTietHoaDonDAO.xoaChiTietTheoHoaDon(maHD);
    }
}