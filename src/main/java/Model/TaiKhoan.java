package Model;

public class TaiKhoan {
    private int maTK;
    private int maNV; // thêm dòng này
    private String tenDangNhap;
    private String matKhau;
    private String vaiTro;

    public TaiKhoan() {}

    public TaiKhoan(int maTK, int maNV, String tenDangNhap, String matKhau, String vaiTro) {
        this.maTK = maTK;
        this.maNV = maNV;
        this.tenDangNhap = tenDangNhap;
        this.matKhau = matKhau;
        this.vaiTro = vaiTro;
    }

    public int getMaTK() { return maTK; }
    public void setMaTK(int maTK) { this.maTK = maTK; }

    public int getMaNV() { return maNV; }
    public void setMaNV(int maNV) { this.maNV = maNV; }

    public String getTenDangNhap() { return tenDangNhap; }
    public void setTenDangNhap(String tenDangNhap) { this.tenDangNhap = tenDangNhap; }

    public String getMatKhau() { return matKhau; }
    public void setMatKhau(String matKhau) { this.matKhau = matKhau; }

    public String getVaiTro() { return vaiTro; }
    public void setVaiTro(String vaiTro) { this.vaiTro = vaiTro; }
}