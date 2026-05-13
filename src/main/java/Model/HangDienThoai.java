package Model;

public class HangDienThoai {
    private int maHang;
    private String tenHang;

    public HangDienThoai() {}

    public HangDienThoai(int maHang, String tenHang) {
        this.maHang = maHang;
        this.tenHang = tenHang;
    }

    public int getMaHang() { return maHang; }
    public void setMaHang(int maHang) { this.maHang = maHang; }

    public String getTenHang() { return tenHang; }
    public void setTenHang(String tenHang) { this.tenHang = tenHang; }
}