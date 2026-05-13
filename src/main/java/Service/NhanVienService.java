package Service;

import DAO.NhanVienDAO;
import Model.NhanVien;
import java.util.ArrayList;

public class NhanVienService {

    private NhanVienDAO nhanVienDAO = new NhanVienDAO();

    public ArrayList<NhanVien> getAllNhanVien() {
        return nhanVienDAO.getAllNhanVien();
    }

    public boolean kiemTraNhanVien(NhanVien nv) {
        if (nv.getHoTen() == null || nv.getHoTen().trim().isEmpty()) {
            return false;
        }

        if (nv.getSdt() == null || nv.getSdt().trim().isEmpty()) {
            return false;
        }

        if (nv.getChucVu() == null || nv.getChucVu().trim().isEmpty()) {
            return false;
        }

        return true;
    }

    public boolean themNhanVien(NhanVien nv) {
        if (!kiemTraNhanVien(nv)) {
            return false;
        }

        return nhanVienDAO.themNhanVien(nv);
    }

    public boolean suaNhanVien(NhanVien nv) {
        if (nv.getMaNV() <= 0) {
            return false;
        }

        if (!kiemTraNhanVien(nv)) {
            return false;
        }

        return nhanVienDAO.suaNhanVien(nv);
    }

    public boolean xoaNhanVien(int maNV) {
        if (maNV <= 0) {
            return false;
        }

        return nhanVienDAO.xoaNhanVien(maNV);
    }
}