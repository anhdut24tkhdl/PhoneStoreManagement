/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;
import Dao.TaiKhoanDAO;
import Model.TaiKhoan;
/**
 *
 * @author PC
 */
public class TaiKhoanService {
     private TaiKhoanDAO taiKhoanDAO = new TaiKhoanDAO();
    public TaiKhoan dangNhap(String tenDangNhap, String matKhau) {
      
    if (tenDangNhap.isEmpty() || matKhau.isEmpty()) {
        return null;
    }

    return taiKhoanDAO.dangNhap(tenDangNhap, matKhau);
}
}
