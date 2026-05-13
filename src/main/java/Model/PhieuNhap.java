package Model;

import java.util.Date;

public class PhieuNhap {
    private int maPN;
    private int maNV;
    private Date ngayNhap;
    private double tongTien;

    public PhieuNhap() {}

    public PhieuNhap(int maPN, int maNV, Date ngayNhap, double tongTien) {
        this.maPN = maPN;
        this.maNV = maNV;
        this.ngayNhap = ngayNhap;
        this.tongTien = tongTien;
    }

    public int getMaPN() { return maPN; }
    public void setMaPN(int maPN) { this.maPN = maPN; }

    public int getMaNV() { return maNV; }
    public void setMaNV(int maNV) { this.maNV = maNV; }

    public Date getNgayNhap() { return ngayNhap; }
    public void setNgayNhap(Date ngayNhap) { this.ngayNhap = ngayNhap; }

    public double getTongTien() { return tongTien; }
    public void setTongTien(double tongTien) { this.tongTien = tongTien; }
}