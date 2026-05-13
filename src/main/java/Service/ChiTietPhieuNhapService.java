package Service;

import Dao.ChiTietPhieuNhapDAO;
import Model.ChiTietPhieuNhap;
import java.util.ArrayList;

public class ChiTietPhieuNhapService {

    private ChiTietPhieuNhapDAO chiTietPhieuNhapDAO = new ChiTietPhieuNhapDAO();

    public ArrayList<ChiTietPhieuNhap> getAllChiTietPhieuNhap() {
        return chiTietPhieuNhapDAO.getAllChiTietPhieuNhap();
    }

    public ArrayList<ChiTietPhieuNhap> getByMaPN(int maPN) {
        if (maPN <= 0) {
            return new ArrayList<>();
        }

        return chiTietPhieuNhapDAO.getByMaPN(maPN);
    }

    public boolean kiemTraChiTietPhieuNhap(ChiTietPhieuNhap ct) {
        if (ct.getMaPN() <= 0) {
            return false;
        }

        if (ct.getMaSP() <= 0) {
            return false;
        }

        if (ct.getSoLuong() <= 0) {
            return false;
        }

        if (ct.getDonGiaNhap() <= 0) {
            return false;
        }

        ct.setThanhTien(ct.getSoLuong() * ct.getDonGiaNhap());

        return true;
    }

    public boolean themChiTietPhieuNhap(ChiTietPhieuNhap ct) {
        if (!kiemTraChiTietPhieuNhap(ct)) {
            return false;
        }

        return chiTietPhieuNhapDAO.themChiTietPhieuNhap(ct);
    }

    public boolean suaChiTietPhieuNhap(ChiTietPhieuNhap ct) {
        if (!kiemTraChiTietPhieuNhap(ct)) {
            return false;
        }

        return chiTietPhieuNhapDAO.suaChiTietPhieuNhap(ct);
    }

    public boolean xoaChiTietPhieuNhap(int maPN, int maSP) {
        if (maPN <= 0 || maSP <= 0) {
            return false;
        }

        return chiTietPhieuNhapDAO.xoaChiTietPhieuNhap(maPN, maSP);
    }

    public boolean xoaChiTietTheoPhieuNhap(int maPN) {
        if (maPN <= 0) {
            return false;
        }

        return chiTietPhieuNhapDAO.xoaChiTietTheoPhieuNhap(maPN);
    }
}