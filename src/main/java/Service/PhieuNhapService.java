package Service;

import DAO.PhieuNhapDAO;
import Model.PhieuNhap;
import java.util.ArrayList;

public class PhieuNhapService {

    private PhieuNhapDAO phieuNhapDAO = new PhieuNhapDAO();

    public ArrayList<PhieuNhap> getAllPhieuNhap() {
        return phieuNhapDAO.getAllPhieuNhap();
    }

    public boolean kiemTraPhieuNhap(PhieuNhap pn) {
        if (pn.getMaNV() <= 0) {
            return false;
        }

        if (pn.getTongTien() < 0) {
            return false;
        }

        return true;
    }

    public boolean themPhieuNhap(PhieuNhap pn) {
        if (!kiemTraPhieuNhap(pn)) {
            return false;
        }

        return phieuNhapDAO.themPhieuNhap(pn);
    }

    public int themPhieuNhapLayMaPN(PhieuNhap pn) {
        if (!kiemTraPhieuNhap(pn)) {
            return -1;
        }

        return phieuNhapDAO.themPhieuNhapLayMaPN(pn);
    }

    public boolean suaPhieuNhap(PhieuNhap pn) {
        if (pn.getMaPN() <= 0) {
            return false;
        }

        if (!kiemTraPhieuNhap(pn)) {
            return false;
        }

        return phieuNhapDAO.suaPhieuNhap(pn);
    }

    public boolean xoaPhieuNhap(int maPN) {
        if (maPN <= 0) {
            return false;
        }

        return phieuNhapDAO.xoaPhieuNhap(maPN);
    }
}