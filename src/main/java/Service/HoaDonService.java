package Service;

import DAO.HoaDonDAO;
import Model.ChiTietHoaDon;
import Model.HoaDon;
import java.util.ArrayList;

public class HoaDonService {

    private HoaDonDAO hoaDonDAO = new HoaDonDAO();

    public ArrayList<HoaDon> getAllHoaDon() {
        return hoaDonDAO.getAllHoaDon();
    }

    public boolean kiemTraHoaDon(HoaDon hd) {
        if (hd.getMaKH() <= 0) {
            return false;
        }

        if (hd.getMaNV() <= 0) {
            return false;
        }

        if (hd.getTongTien() < 0) {
            return false;
        }

        return true;
    }

    public boolean themHoaDon(HoaDon hd) {
        if (!kiemTraHoaDon(hd)) {
            return false;
        }

        return hoaDonDAO.themHoaDon(hd);
    }

    public boolean suaHoaDon(HoaDon hd) {
        if (hd.getMaHD() <= 0) {
            return false;
        }

        if (!kiemTraHoaDon(hd)) {
            return false;
        }

        return hoaDonDAO.suaHoaDon(hd);
    }

    public boolean xoaHoaDon(int maHD) {
        if (maHD <= 0) {
            return false;
        }

        return hoaDonDAO.xoaHoaDon(maHD);
    }

    public double tinhTongTien(ArrayList<ChiTietHoaDon> list) {
        double tong = 0;

        for (ChiTietHoaDon ct : list) {
            tong += ct.getThanhTien();
        }

        return tong;
    }
    
    public int themHoaDonLayMaHD(HoaDon hd) {
        if (!kiemTraHoaDon(hd)) {
            return -1;
        }

        return hoaDonDAO.themHoaDonLayMaHD(hd);
    }
}