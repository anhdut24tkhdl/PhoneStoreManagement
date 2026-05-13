package Model;

public class KhachHang {
    private int maKH;
    private String hoTen;
    private String sdt;
    private String diaChi;

    public KhachHang() {}

    public KhachHang(int maKH, String hoTen, String sdt, String diaChi) {
        this.maKH = maKH;
        this.hoTen = hoTen;
        this.sdt = sdt;
        this.diaChi = diaChi;
    }

    public int getMaKH() { return maKH; }
    public void setMaKH(int maKH) { this.maKH = maKH; }

    public String getHoTen() { return hoTen; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }

    public String getSdt() { return sdt; }
    public void setSdt(String sdt) { this.sdt = sdt; }

    public String getDiaChi() { return diaChi; }
    public void setDiaChi(String diaChi) { this.diaChi = diaChi; }
}