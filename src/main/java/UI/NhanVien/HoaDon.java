package UI.NhanVien;

import Model.ChiTietHoaDon;
import Model.KhachHang;
import Model.SanPham;
import Service.ChiTietHoaDonService;
import Service.HoaDonService;
import Service.KhachHangService;
import Service.SanPhamService;
import Utils.Session;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class HoaDon extends javax.swing.JFrame {

    private JTable tblHoaDon;
    private JTable tblKhachHang;
    private JTable tblSanPham;
    private JTable tblChiTiet;

    private JTextField txtTimHoaDon;
    private JTextField txtTimKhachHang;
    private JTextField txtTimSanPham;
    private JTextField txtKhachHangDaChon;
    private JSpinner spnSoLuong;

    private JLabel lblTongTien;

    private JButton btnTimHoaDon;
    private JButton btnTimKhachHang;
    private JButton btnTimSanPham;
    private JButton btnThemSP;
    private JButton btnXoaSP;
    private JButton btnTaoHoaDon;
    private JButton btnXoaHoaDon;
    private JButton btnLamMoi;
    private JButton btnQuayLai;

    private int maKHChon = -1;
    private double tongTien = 0;

    private ArrayList<KhachHang> listKhachHang = new ArrayList<>();
    private ArrayList<SanPham> listSanPham = new ArrayList<>();

    public HoaDon() {
        initComponents();
        customUI();
        loadHoaDon();
        loadKhachHang();
        loadSanPham();
    }

    private void customUI() {
        setTitle("Quản lý đơn hàng - Tạo hóa đơn");
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(245, 247, 250));

        customTable(tblHoaDon);
        customTable(tblKhachHang);
        customTable(tblSanPham);
        customTable(tblChiTiet);

        Font btnFont = new Font("Segoe UI", Font.BOLD, 13);

        JButton[] buttons = {
            btnTimHoaDon,
            btnTimKhachHang,
            btnTimSanPham,
            btnThemSP,
            btnXoaSP,
            btnTaoHoaDon,
            btnXoaHoaDon,
            btnLamMoi,
            btnQuayLai
        };

        for (JButton btn : buttons) {
            btn.setFont(btnFont);
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        btnTimHoaDon.setBackground(new Color(124, 58, 237));
        btnTimKhachHang.setBackground(new Color(124, 58, 237));
        btnTimSanPham.setBackground(new Color(124, 58, 237));

        btnThemSP.setBackground(new Color(22, 163, 74));
        btnTaoHoaDon.setBackground(new Color(37, 99, 235));

        btnXoaSP.setBackground(new Color(220, 38, 38));
        btnXoaHoaDon.setBackground(new Color(220, 38, 38));

        btnLamMoi.setBackground(new Color(249, 115, 22));
        btnQuayLai.setBackground(new Color(107, 114, 128));
    }

    private void customTable(JTable table) {
        table.setRowHeight(28);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        table.getTableHeader().setBackground(new Color(37, 99, 235));
        table.getTableHeader().setForeground(Color.WHITE);

        table.setShowGrid(true);
        table.setGridColor(Color.LIGHT_GRAY);
        table.setSelectionBackground(new Color(219, 234, 254));
        table.setSelectionForeground(Color.BLACK);
    }

    private void loadHoaDon() {
        HoaDonService service = new HoaDonService();
        ArrayList<Model.HoaDon> list = service.getAllHoaDon();

        DefaultTableModel model = new DefaultTableModel();

        model.setColumnIdentifiers(new Object[]{
            "Mã HĐ",
            "Mã KH",
            "Mã NV",
            "Ngày lập",
            "Tổng tiền"
        });

        for (Model.HoaDon hd : list) {
            model.addRow(new Object[]{
                hd.getMaHD(),
                hd.getMaKH(),
                hd.getMaNV(),
                hd.getNgayLap(),
                hd.getTongTien()/1000000 +"Triệu"
            });
        }

        tblHoaDon.setModel(model);
    }

    private void loadKhachHang() {
        KhachHangService service = new KhachHangService();
        listKhachHang = service.getAllKhachHang();
        hienThiKhachHang(listKhachHang);
    }

    private void hienThiKhachHang(ArrayList<KhachHang> list) {
        DefaultTableModel model = new DefaultTableModel();

        model.setColumnIdentifiers(new Object[]{
            "Mã KH",
            "Họ tên",
            "SĐT",
            "Địa chỉ"
        });

        for (KhachHang kh : list) {
            model.addRow(new Object[]{
                kh.getMaKH(),
                kh.getHoTen(),
                kh.getSdt(),
                kh.getDiaChi()
            });
        }

        tblKhachHang.setModel(model);
    }

    private void loadSanPham() {
        SanPhamService service = new SanPhamService();
        listSanPham = service.getAllSanPham();
        hienThiSanPham(listSanPham);
    }

    private void hienThiSanPham(ArrayList<SanPham> list) {
        DefaultTableModel model = new DefaultTableModel();

        model.setColumnIdentifiers(new Object[]{
            "Mã SP",
            "Tên SP",
            "Giá bán",
            "Tồn kho",
            "Mô tả"
        });

        for (SanPham sp : list) {
            model.addRow(new Object[]{
                sp.getMaSP(),
                sp.getTenSP(),
                sp.getGiaBan(),
                sp.getSoLuong(),
                sp.getMoTa()
            });
        }

        tblSanPham.setModel(model);
    }

    private void timHoaDon() {
        String keyword = txtTimHoaDon.getText().trim().toLowerCase();

        HoaDonService service = new HoaDonService();
        ArrayList<Model.HoaDon> list = service.getAllHoaDon();

        DefaultTableModel model = new DefaultTableModel();

        model.setColumnIdentifiers(new Object[]{
            "Mã HĐ",
            "Mã KH",
            "Mã NV",
            "Ngày lập",
            "Tổng tiền"
        });

        for (Model.HoaDon hd : list) {
            if (String.valueOf(hd.getMaHD()).contains(keyword)
                    || String.valueOf(hd.getMaKH()).contains(keyword)
                    || String.valueOf(hd.getMaNV()).contains(keyword)) {

                model.addRow(new Object[]{
                    hd.getMaHD(),
                    hd.getMaKH(),
                    hd.getMaNV(),
                    hd.getNgayLap(),
                    hd.getTongTien()
                });
            }
        }

        tblHoaDon.setModel(model);
    }

    private void timKhachHang() {
        String keyword = txtTimKhachHang.getText().trim().toLowerCase();

        KhachHangService service = new KhachHangService();
        ArrayList<KhachHang> all = service.getAllKhachHang();
        ArrayList<KhachHang> ketQua = new ArrayList<>();

        for (KhachHang kh : all) {
            if (kh.getHoTen().toLowerCase().contains(keyword)
                    || kh.getSdt().toLowerCase().contains(keyword)
                    || kh.getDiaChi().toLowerCase().contains(keyword)
                    || String.valueOf(kh.getMaKH()).contains(keyword)) {

                ketQua.add(kh);
            }
        }

        listKhachHang = ketQua;
        hienThiKhachHang(ketQua);
    }

    private void timSanPham() {
        String keyword = txtTimSanPham.getText().trim().toLowerCase();

        SanPhamService service = new SanPhamService();
        ArrayList<SanPham> all = service.getAllSanPham();
        ArrayList<SanPham> ketQua = new ArrayList<>();

        for (SanPham sp : all) {
            if (sp.getTenSP().toLowerCase().contains(keyword)) {
                ketQua.add(sp);
            }
        }

        listSanPham = ketQua;
        hienThiSanPham(ketQua);
    }

    private void chonKhachHang() {
        int row = tblKhachHang.getSelectedRow();

        if (row == -1) {
            return;
        }

        maKHChon = Integer.parseInt(
                tblKhachHang.getValueAt(row, 0).toString()
        );

        String tenKH = tblKhachHang.getValueAt(row, 1).toString();
        String sdt = tblKhachHang.getValueAt(row, 2).toString();

        txtKhachHangDaChon.setText(
                maKHChon + " - " + tenKH + " - " + sdt
        );
    }

    private int getSoLuongTrongGio(int maSP) {
        DefaultTableModel model =
                (DefaultTableModel) tblChiTiet.getModel();

        int tong = 0;

        for (int i = 0; i < model.getRowCount(); i++) {
            int maSPTrongGio =
                    Integer.parseInt(
                            model.getValueAt(i, 0).toString()
                    );

            if (maSPTrongGio == maSP) {
                tong += Integer.parseInt(
                        model.getValueAt(i, 2).toString()
                );
            }
        }

        return tong;
    }

    private void themSanPhamVaoHoaDon() {
        int row = tblSanPham.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Vui lòng chọn sản phẩm!"
            );
            return;
        }

        int maSP = Integer.parseInt(
                tblSanPham.getValueAt(row, 0).toString()
        );

        String tenSP =
                tblSanPham.getValueAt(row, 1).toString();

        double donGia = Double.parseDouble(
                tblSanPham.getValueAt(row, 2).toString()
        );

        int tonKho = Integer.parseInt(
                tblSanPham.getValueAt(row, 3).toString()
        );

        int soLuong = Integer.parseInt(
                spnSoLuong.getValue().toString()
        );

        if (soLuong <= 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "Số lượng phải lớn hơn 0!"
            );
            return;
        }

        int daCoTrongGio = getSoLuongTrongGio(maSP);

        if (soLuong + daCoTrongGio > tonKho) {
            JOptionPane.showMessageDialog(
                    this,
                    "Không đủ hàng tồn kho!"
            );
            return;
        }

        double thanhTien = soLuong * donGia;

        DefaultTableModel model =
                (DefaultTableModel) tblChiTiet.getModel();

        model.addRow(new Object[]{
            maSP,
            tenSP,
            soLuong,
            donGia,
            thanhTien
        });

        tinhTongTien();
    }

    private void xoaSanPhamKhoiHoaDon() {
        int row = tblChiTiet.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Chọn sản phẩm cần xóa khỏi hóa đơn!"
            );
            return;
        }

        DefaultTableModel model =
                (DefaultTableModel) tblChiTiet.getModel();

        model.removeRow(row);

        tinhTongTien();
    }

    private void tinhTongTien() {
        DefaultTableModel model =
                (DefaultTableModel) tblChiTiet.getModel();

        tongTien = 0;

        for (int i = 0; i < model.getRowCount(); i++) {
            tongTien += Double.parseDouble(
                    model.getValueAt(i, 4).toString()
            );
        }

        lblTongTien.setText(
                "Tổng tiền: " + String.format("%,.0f VNĐ", tongTien)
        );
    }

    private void taoHoaDon() {
        if (maKHChon <= 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "Vui lòng chọn khách hàng!"
            );
            return;
        }

        if (Session.currentUser == null
                || Session.currentUser.getMaNV() <= 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Không lấy được mã nhân viên đang đăng nhập!"
            );
            return;
        }

        DefaultTableModel model =
                (DefaultTableModel) tblChiTiet.getModel();

        if (model.getRowCount() == 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "Hóa đơn chưa có sản phẩm!"
            );
            return;
        }

        Model.HoaDon hd = new Model.HoaDon();

        hd.setMaKH(maKHChon);
        hd.setMaNV(Session.currentUser.getMaNV());
        hd.setTongTien(tongTien);

        HoaDonService hoaDonService =
                new HoaDonService();

        int maHD =
                hoaDonService.themHoaDonLayMaHD(hd);

        if (maHD <= 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "Tạo hóa đơn thất bại!"
            );
            return;
        }

        ChiTietHoaDonService ctService =
                new ChiTietHoaDonService();

        SanPhamService spService =
                new SanPhamService();

        boolean ok = true;

        for (int i = 0; i < model.getRowCount(); i++) {
            int maSP = Integer.parseInt(
                    model.getValueAt(i, 0).toString()
            );

            int soLuong = Integer.parseInt(
                    model.getValueAt(i, 2).toString()
            );

            double donGia = Double.parseDouble(
                    model.getValueAt(i, 3).toString()
            );

            double thanhTien = Double.parseDouble(
                    model.getValueAt(i, 4).toString()
            );

            ChiTietHoaDon ct = new ChiTietHoaDon();

            ct.setMaHD(maHD);
            ct.setMaSP(maSP);
            ct.setSoLuong(soLuong);
            ct.setDonGia(donGia);
            ct.setThanhTien(thanhTien);

            if (!ctService.themChiTietHoaDon(ct)) {
                ok = false;
                break;
            }

            if (!spService.giamSoLuong(maSP, soLuong)) {
                ok = false;
                break;
            }
        }

        if (ok) {
            JOptionPane.showMessageDialog(
                    this,
                    "Tạo hóa đơn thành công! Mã HĐ: " + maHD
            );

            lamMoi();

        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Có lỗi khi lưu chi tiết hóa đơn hoặc trừ kho!"
            );
        }
    }

    private void xoaHoaDon() {
        int row = tblHoaDon.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Chọn hóa đơn cần xóa!"
            );
            return;
        }

        int maHD = Integer.parseInt(
                tblHoaDon.getValueAt(row, 0).toString()
        );

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc muốn xóa hóa đơn này?",
                "Xác nhận",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        ChiTietHoaDonService ctService =
                new ChiTietHoaDonService();

        HoaDonService hdService =
                new HoaDonService();

        ctService.xoaChiTietTheoHoaDon(maHD);

        if (hdService.xoaHoaDon(maHD)) {
            JOptionPane.showMessageDialog(
                    this,
                    "Xóa hóa đơn thành công!"
            );

            loadHoaDon();

        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Xóa hóa đơn thất bại!"
            );
        }
    }

    private void lamMoi() {
        maKHChon = -1;
        tongTien = 0;

        txtTimHoaDon.setText("");
        txtTimKhachHang.setText("");
        txtTimSanPham.setText("");
        txtKhachHangDaChon.setText("");

        spnSoLuong.setValue(1);

        DefaultTableModel model =
                (DefaultTableModel) tblChiTiet.getModel();

        model.setRowCount(0);

        lblTongTien.setText("Tổng tiền: 0 VNĐ");

        loadHoaDon();
        loadKhachHang();
        loadSanPham();
    }

    private void initComponents() {
        JLabel lblTitle =
                new JLabel("QUẢN LÝ ĐƠN HÀNG - TẠO HÓA ĐƠN");

        lblTitle.setFont(
                new Font("Segoe UI", Font.BOLD, 24)
        );

        txtTimHoaDon = new JTextField();
        txtTimKhachHang = new JTextField();
        txtTimSanPham = new JTextField();

        txtKhachHangDaChon = new JTextField();
        txtKhachHangDaChon.setEditable(false);

        spnSoLuong =
                new JSpinner(
                        new SpinnerNumberModel(1, 1, 999, 1)
                );

        btnTimHoaDon = new JButton("Tìm HĐ");
        btnTimKhachHang = new JButton("Tìm KH");
        btnTimSanPham = new JButton("Tìm SP");

        btnThemSP = new JButton("Thêm SP");
        btnXoaSP = new JButton("Xóa SP");

        btnTaoHoaDon = new JButton("Tạo hóa đơn");
        btnXoaHoaDon = new JButton("Xóa hóa đơn");
        btnLamMoi = new JButton("Làm mới");
        btnQuayLai = new JButton("Quay lại");

        tblHoaDon = new JTable();
        tblKhachHang = new JTable();
        tblSanPham = new JTable();
        tblChiTiet = new JTable();

        tblHoaDon.setModel(
                new DefaultTableModel(
                        new Object[][]{},
                        new String[]{
                            "Mã HĐ",
                            "Mã KH",
                            "Mã NV",
                            "Ngày lập",
                            "Tổng tiền"
                        }
                )
        );

        tblKhachHang.setModel(
                new DefaultTableModel(
                        new Object[][]{},
                        new String[]{
                            "Mã KH",
                            "Họ tên",
                            "SĐT",
                            "Địa chỉ"
                        }
                )
        );

        tblSanPham.setModel(
                new DefaultTableModel(
                        new Object[][]{},
                        new String[]{
                            "Mã SP",
                            "Tên SP",
                            "Giá bán",
                            "Tồn kho",
                            "Mô tả"
                        }
                )
        );

        tblChiTiet.setModel(
                new DefaultTableModel(
                        new Object[][]{},
                        new String[]{
                            "Mã SP",
                            "Tên SP",
                            "Số lượng",
                            "Đơn giá",
                            "Thành tiền"
                        }
                )
        );

        tblKhachHang.addMouseListener(
                new java.awt.event.MouseAdapter() {
            public void mouseClicked(
                    java.awt.event.MouseEvent evt) {

                chonKhachHang();
            }
        });

        btnTimHoaDon.addActionListener(e -> timHoaDon());
        btnTimKhachHang.addActionListener(e -> timKhachHang());
        btnTimSanPham.addActionListener(e -> timSanPham());

        btnThemSP.addActionListener(e -> themSanPhamVaoHoaDon());
        btnXoaSP.addActionListener(e -> xoaSanPhamKhoiHoaDon());

        btnTaoHoaDon.addActionListener(e -> taoHoaDon());
        btnXoaHoaDon.addActionListener(e -> xoaHoaDon());
        btnLamMoi.addActionListener(e -> lamMoi());

        btnQuayLai.addActionListener(e -> {
            TrangChuNhanVien tc = new TrangChuNhanVien();
            tc.setVisible(true);
            dispose();
        });

        lblTongTien = new JLabel("Tổng tiền: 0 VNĐ");

        lblTongTien.setFont(
                new Font("Segoe UI", Font.BOLD, 18)
        );

        lblTongTien.setForeground(
                new Color(220, 38, 38)
        );

        JScrollPane scrollHoaDon =
                new JScrollPane(tblHoaDon);

        JScrollPane scrollKhachHang =
                new JScrollPane(tblKhachHang);

        JScrollPane scrollSanPham =
                new JScrollPane(tblSanPham);

        JScrollPane scrollChiTiet =
                new JScrollPane(tblChiTiet);

        JPanel panelMain =
                new JPanel(new BorderLayout(15, 15));

        panelMain.setBorder(
                BorderFactory.createEmptyBorder(
                        20,
                        20,
                        20,
                        20
                )
        );

        panelMain.setBackground(
                new Color(245, 247, 250)
        );

        JPanel panelTop =
                new JPanel(new BorderLayout());

        panelTop.setBackground(
                new Color(245, 247, 250)
        );

        panelTop.add(lblTitle, BorderLayout.WEST);

        JPanel panelContent =
                new JPanel(new GridLayout(2, 2, 15, 15));

        panelContent.setBackground(
                new Color(245, 247, 250)
        );

        JPanel panelHoaDon =
                taoPanel(
                        "Danh sách hóa đơn",
                        txtTimHoaDon,
                        btnTimHoaDon,
                        scrollHoaDon
                );

        JPanel panelKhachHang =
                taoPanel(
                        "Chọn khách hàng",
                        txtTimKhachHang,
                        btnTimKhachHang,
                        scrollKhachHang
                );

        JPanel panelSanPham =
                taoPanelSanPham(scrollSanPham);

        JPanel panelChiTiet =
                new JPanel(new BorderLayout(10, 10));

        panelChiTiet.setBorder(
                BorderFactory.createTitledBorder(
                        "Chi tiết hóa đơn đang tạo"
                )
        );

        panelChiTiet.setBackground(Color.WHITE);

        JPanel panelInput =
                new JPanel(new FlowLayout(FlowLayout.LEFT));

        panelInput.setBackground(Color.WHITE);

        panelInput.add(new JLabel("Khách hàng:"));

        txtKhachHangDaChon.setPreferredSize(
                new Dimension(260, 30)
        );

        panelInput.add(txtKhachHangDaChon);
        panelInput.add(btnXoaSP);

        panelChiTiet.add(panelInput, BorderLayout.NORTH);
        panelChiTiet.add(scrollChiTiet, BorderLayout.CENTER);

        JPanel panelBottomChiTiet =
                new JPanel(new FlowLayout(FlowLayout.RIGHT));

        panelBottomChiTiet.setBackground(Color.WHITE);
        panelBottomChiTiet.add(lblTongTien);

        panelChiTiet.add(
                panelBottomChiTiet,
                BorderLayout.SOUTH
        );

        panelContent.add(panelHoaDon);
        panelContent.add(panelKhachHang);
        panelContent.add(panelSanPham);
        panelContent.add(panelChiTiet);

        JPanel panelBottom =
                new JPanel(new FlowLayout(FlowLayout.RIGHT));

        panelBottom.setBackground(
                new Color(245, 247, 250)
        );

        panelBottom.add(btnTaoHoaDon);
        panelBottom.add(btnXoaHoaDon);
        panelBottom.add(btnLamMoi);
        panelBottom.add(btnQuayLai);

        panelMain.add(panelTop, BorderLayout.NORTH);
        panelMain.add(panelContent, BorderLayout.CENTER);
        panelMain.add(panelBottom, BorderLayout.SOUTH);

        setContentPane(panelMain);

        setDefaultCloseOperation(
                javax.swing.WindowConstants.EXIT_ON_CLOSE
        );

        setSize(1200, 760);
        setLocationRelativeTo(null);
    }

    private JPanel taoPanel(
            String title,
            JTextField txtSearch,
            JButton btnSearch,
            JScrollPane scrollPane) {

        JPanel panel =
                new JPanel(new BorderLayout(8, 8));

        panel.setBorder(
                BorderFactory.createTitledBorder(title)
        );

        panel.setBackground(Color.WHITE);

        JPanel searchPanel =
                new JPanel(new BorderLayout(8, 8));

        searchPanel.setBackground(Color.WHITE);

        searchPanel.add(txtSearch, BorderLayout.CENTER);
        searchPanel.add(btnSearch, BorderLayout.EAST);

        panel.add(searchPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel taoPanelSanPham(JScrollPane scrollPane) {
        JPanel panel =
                new JPanel(new BorderLayout(8, 8));

        panel.setBorder(
                BorderFactory.createTitledBorder("Chọn sản phẩm")
        );

        panel.setBackground(Color.WHITE);

        JPanel searchPanel =
                new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 5));

        searchPanel.setBackground(Color.WHITE);

        txtTimSanPham.setPreferredSize(
                new Dimension(210, 32)
        );

        spnSoLuong.setPreferredSize(
                new Dimension(70, 32)
        );

        searchPanel.add(txtTimSanPham);
        searchPanel.add(btnTimSanPham);

        searchPanel.add(new JLabel("Số lượng:"));
        searchPanel.add(spnSoLuong);

        searchPanel.add(btnThemSP);

        panel.add(searchPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(
                () -> new HoaDon().setVisible(true)
        );
    }
}