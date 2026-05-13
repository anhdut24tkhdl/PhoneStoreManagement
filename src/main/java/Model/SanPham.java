package Model;

public class SanPham {
    private int maSP;
    private String tenSP;
    private int maHang;
    private double giaBan;
    private int soLuong;
    private String moTa;

    public SanPham() {
    }

    public SanPham(int maSP, String tenSP, int maHang, double giaBan, int soLuong, String moTa) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.maHang = maHang;
        this.giaBan = giaBan;
        this.soLuong = soLuong;
        this.moTa = moTa;
    }

    public int getMaSP() {
        return maSP;
    }

    public void setMaSP(int maSP) {
        this.maSP = maSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public int getMaHang() {
        return maHang;
    }

    public void setMaHang(int maHang) {
        this.maHang = maHang;
    }

    public double getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(double giaBan) {
        this.giaBan = giaBan;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }
}