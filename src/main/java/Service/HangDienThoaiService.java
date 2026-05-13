package Service;

import Dao.HangDienThoaiDAO;
import Model.HangDienThoai;
import java.util.ArrayList;

public class HangDienThoaiService {

    private HangDienThoaiDAO hangDienThoaiDAO = new HangDienThoaiDAO();

    public ArrayList<HangDienThoai> getAllHangDienThoai() {
        return hangDienThoaiDAO.getAllHangDienThoai();
    }

    public boolean kiemTraHangDienThoai(HangDienThoai h) {
        if (h.getTenHang() == null || h.getTenHang().trim().isEmpty()) {
            return false;
        }

        return true;
    }

    public boolean themHangDienThoai(HangDienThoai h) {
        if (!kiemTraHangDienThoai(h)) {
            return false;
        }

        return hangDienThoaiDAO.themHangDienThoai(h);
    }

    public boolean suaHangDienThoai(HangDienThoai h) {
        if (h.getMaHang() <= 0) {
            return false;
        }

        if (!kiemTraHangDienThoai(h)) {
            return false;
        }

        return hangDienThoaiDAO.suaHangDienThoai(h);
    }

    public boolean xoaHangDienThoai(int maHang) {
        if (maHang <= 0) {
            return false;
        }

        return hangDienThoaiDAO.xoaHangDienThoai(maHang);
    }
}