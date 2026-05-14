package Service;

import DAO.TaiKhoanDAO;
import Model.TaiKhoan;
import java.util.ArrayList;

public class TaiKhoanService {

    private TaiKhoanDAO taiKhoanDAO = new TaiKhoanDAO();

    public TaiKhoan dangNhap(String tenDangNhap, String matKhau) {
        if (tenDangNhap.isEmpty() || matKhau.isEmpty()) {
            return null;
        }

        return taiKhoanDAO.dangNhap(tenDangNhap, matKhau);
    }

    public ArrayList<TaiKhoan> getAllTaiKhoan() {
        return taiKhoanDAO.getAllTaiKhoan();
    }

    public boolean themTaiKhoan(TaiKhoan tk) {
        if (tk.getTenDangNhap().isEmpty() || tk.getMatKhau().isEmpty()) {
            return false;
        }

        return taiKhoanDAO.themTaiKhoan(tk);
    }

    public boolean suaTaiKhoan(TaiKhoan tk) {
        if (tk.getTenDangNhap().isEmpty() || tk.getMatKhau().isEmpty()) {
            return false;
        }

        return taiKhoanDAO.suaTaiKhoan(tk);
    }

    public boolean xoaTaiKhoan(int maTK) {
        return taiKhoanDAO.xoaTaiKhoan(maTK);
    }
}