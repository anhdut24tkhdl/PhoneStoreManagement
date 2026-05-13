    package Model;

public class ChiTietPhieuNhap {
    private int maPN;
    private int maSP;
    private int soLuong;
    private double donGiaNhap;
    private double thanhTien;

    public ChiTietPhieuNhap() {}

    public ChiTietPhieuNhap(int maPN, int maSP, int soLuong, double donGiaNhap, double thanhTien) {
        this.maPN = maPN;
        this.maSP = maSP;
        this.soLuong = soLuong;
        this.donGiaNhap = donGiaNhap;
        this.thanhTien = thanhTien;
    }

    public int getMaPN() { return maPN; }
    public void setMaPN(int maPN) { this.maPN = maPN; }

    public int getMaSP() { return maSP; }
    public void setMaSP(int maSP) { this.maSP = maSP; }

    public int getSoLuong() { return soLuong; }
    public void setSoLuong(int soLuong) { this.soLuong = soLuong; }

    public double getDonGiaNhap() { return donGiaNhap; }
    public void setDonGiaNhap(double donGiaNhap) { this.donGiaNhap = donGiaNhap; }

    public double getThanhTien() { return thanhTien; }
    public void setThanhTien(double thanhTien) { this.thanhTien = thanhTien; }
}