package UI.NhanVien;

import Model.ChiTietPhieuNhap;
import Model.PhieuNhap;
import Model.SanPham;
import Service.ChiTietPhieuNhapService;
import Service.PhieuNhapService;
import Service.SanPhamService;
import Utils.Session;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ThemPhieuNhap extends JDialog {

    private JTable tblSanPham;
    private JTable tblChiTiet;

    private JTextField txtTimKiem;
    private JSpinner spnSoLuong;

    private JButton btnTimKiem;
    private JButton btnThemSP;
    private JButton btnXoaSP;
    private JButton btnLuu;
    private JButton btnHuy;

    private JLabel lblTongTien;

    private double tongTien = 0;

    public ThemPhieuNhap(Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        customUI();
        loadSanPham();
    }

    private void customUI() {
        setTitle("Tạo phiếu nhập hàng");
        getContentPane().setBackground(new Color(245, 247, 250));

        customTable(tblSanPham);
        customTable(tblChiTiet);

        Font btnFont = new Font("Segoe UI", Font.BOLD, 13);

        JButton[] buttons = {
            btnTimKiem,
            btnThemSP,
            btnXoaSP,
            btnLuu,
            btnHuy
        };

        for (JButton btn : buttons) {
            btn.setFont(btnFont);
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);
            btn.setBorderPainted(false);
            btn.setOpaque(true);
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        btnTimKiem.setBackground(new Color(37, 99, 235));
        btnThemSP.setBackground(new Color(22, 163, 74));
        btnXoaSP.setBackground(new Color(220, 38, 38));
        btnLuu.setBackground(new Color(37, 99, 235));
        btnHuy.setBackground(new Color(107, 114, 128));
    }

    private void customTable(JTable table) {
        table.setRowHeight(30);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        table.getTableHeader().setBackground(new Color(37, 99, 235));
        table.getTableHeader().setForeground(Color.WHITE);

        table.setShowGrid(true);
        table.setGridColor(Color.LIGHT_GRAY);
        table.setSelectionBackground(new Color(219, 234, 254));
        table.setSelectionForeground(Color.BLACK);
    }

    private void loadSanPham() {
        SanPhamService service = new SanPhamService();
        ArrayList<SanPham> list = service.getAllSanPham();
        hienThiSanPham(list);
    }

    private void hienThiSanPham(ArrayList<SanPham> list) {
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        model.setColumnIdentifiers(new Object[]{
            "Mã SP",
            "Tên SP",
            "Mã hãng",
            "Giá bán",
            "Tồn kho",
            "Mô tả"
        });

        for (SanPham sp : list) {
            model.addRow(new Object[]{
                sp.getMaSP(),
                sp.getTenSP(),
                sp.getMaHang(),
                sp.getGiaBan(),
                sp.getSoLuong(),
                sp.getMoTa()
            });
        }

        tblSanPham.setModel(model);

        tblSanPham.getColumnModel().getColumn(0).setPreferredWidth(60);
        tblSanPham.getColumnModel().getColumn(1).setPreferredWidth(140);
        tblSanPham.getColumnModel().getColumn(2).setPreferredWidth(80);
        tblSanPham.getColumnModel().getColumn(3).setPreferredWidth(110);
        tblSanPham.getColumnModel().getColumn(4).setPreferredWidth(80);
        tblSanPham.getColumnModel().getColumn(5).setPreferredWidth(140);
    }

    private void timSanPham() {
        String keyword = txtTimKiem.getText().trim().toLowerCase();

        SanPhamService service = new SanPhamService();
        ArrayList<SanPham> list = service.getAllSanPham();
        ArrayList<SanPham> ketQua = new ArrayList<>();

        for (SanPham sp : list) {
            String tenSP = sp.getTenSP() == null ? "" : sp.getTenSP().toLowerCase();
            String moTa = sp.getMoTa() == null ? "" : sp.getMoTa().toLowerCase();

            if (String.valueOf(sp.getMaSP()).contains(keyword)
                    || tenSP.contains(keyword)
                    || String.valueOf(sp.getMaHang()).contains(keyword)
                    || moTa.contains(keyword)) {

                ketQua.add(sp);
            }
        }

        hienThiSanPham(ketQua);
    }

    private boolean daCoTrongBang(int maSP) {
        DefaultTableModel model = (DefaultTableModel) tblChiTiet.getModel();

        for (int i = 0; i < model.getRowCount(); i++) {
            int maSPTrongBang = Integer.parseInt(
                    model.getValueAt(i, 0).toString()
            );

            if (maSPTrongBang == maSP) {
                return true;
            }
        }

        return false;
    }

    private double laySoTuBang(Object value) {
        String text = value.toString()
                .replace("VNĐ", "")
                .replace(",", "")
                .replace(" ", "")
                .trim();

        return Double.parseDouble(text);
    }

    private void themSanPhamVaoPhieu() {
        int row = tblSanPham.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm cần nhập!");
            return;
        }

        int maSP = Integer.parseInt(tblSanPham.getValueAt(row, 0).toString());
        String tenSP = tblSanPham.getValueAt(row, 1).toString();

        if (daCoTrongBang(maSP)) {
            JOptionPane.showMessageDialog(this, "Sản phẩm này đã có trong phiếu nhập!");
            return;
        }

        int soLuong = Integer.parseInt(spnSoLuong.getValue().toString());

        if (soLuong <= 0) {
            JOptionPane.showMessageDialog(this, "Số lượng nhập phải lớn hơn 0!");
            return;
        }

        double giaNhap = laySoTuBang(tblSanPham.getValueAt(row, 3));
        double thanhTien = soLuong * giaNhap;

        DefaultTableModel model = (DefaultTableModel) tblChiTiet.getModel();

        model.addRow(new Object[]{
            maSP,
            tenSP,
            soLuong,
            giaNhap,
            thanhTien
        });

        tinhTongTien();
    }

    private void xoaSanPhamKhoiPhieu() {
        int row = tblChiTiet.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm cần xóa khỏi phiếu!");
            return;
        }

        DefaultTableModel model = (DefaultTableModel) tblChiTiet.getModel();
        model.removeRow(row);

        tinhTongTien();
    }

    private void tinhTongTien() {
        DefaultTableModel model = (DefaultTableModel) tblChiTiet.getModel();

        tongTien = 0;

        for (int i = 0; i < model.getRowCount(); i++) {
            tongTien += Double.parseDouble(model.getValueAt(i, 4).toString());
        }

        lblTongTien.setText(
                "Tổng tiền nhập: " + String.format("%,.0f VNĐ", tongTien)
        );
    }

    private void luuPhieuNhap() {
        if (Session.currentUser == null || Session.currentUser.getMaNV() <= 0) {
            JOptionPane.showMessageDialog(this, "Không lấy được mã nhân viên đang đăng nhập!");
            return;
        }

        DefaultTableModel model = (DefaultTableModel) tblChiTiet.getModel();

        if (model.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Phiếu nhập chưa có sản phẩm!");
            return;
        }

        PhieuNhap pn = new PhieuNhap();
        pn.setMaNV(Session.currentUser.getMaNV());
        pn.setTongTien(tongTien);

        PhieuNhapService pnService = new PhieuNhapService();

        int maPN = pnService.themPhieuNhapLayMaPN(pn);

        if (maPN <= 0) {
            JOptionPane.showMessageDialog(this, "Tạo phiếu nhập thất bại!");
            return;
        }

        ChiTietPhieuNhapService ctService = new ChiTietPhieuNhapService();
        SanPhamService spService = new SanPhamService();

        boolean ok = true;

        for (int i = 0; i < model.getRowCount(); i++) {
            int maSP = Integer.parseInt(model.getValueAt(i, 0).toString());
            int soLuong = Integer.parseInt(model.getValueAt(i, 2).toString());
            double giaNhap = Double.parseDouble(model.getValueAt(i, 3).toString());
            double thanhTien = Double.parseDouble(model.getValueAt(i, 4).toString());

            ChiTietPhieuNhap ct = new ChiTietPhieuNhap();

            ct.setMaPN(maPN);
            ct.setMaSP(maSP);
            ct.setSoLuong(soLuong);
            ct.setDonGiaNhap(giaNhap);
            ct.setThanhTien(thanhTien);

            if (!ctService.themChiTietPhieuNhap(ct)) {
                ok = false;
                break;
            }

            if (!spService.tangSoLuong(maSP, soLuong)) {
                ok = false;
                break;
            }
        }

        if (ok) {
            JOptionPane.showMessageDialog(
                    this,
                    "Tạo phiếu nhập thành công! Mã PN: " + maPN
            );

            dispose();

        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Có lỗi khi lưu chi tiết phiếu nhập hoặc cộng tồn kho!"
            );
        }
    }

    private void initComponents() {
        JLabel lblTitle = new JLabel("TẠO PHIẾU NHẬP HÀNG");

        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 30));
        lblTitle.setForeground(new Color(0, 86, 179));

        txtTimKiem = new JTextField();

        spnSoLuong = new JSpinner(
                new SpinnerNumberModel(1, 1, 999, 1)
        );

        btnTimKiem = new JButton("Tìm SP");
        btnThemSP = new JButton("Thêm vào phiếu");
        btnXoaSP = new JButton("Xóa khỏi phiếu");
        btnLuu = new JButton("Lưu phiếu nhập");
        btnHuy = new JButton("Hủy");

        tblSanPham = new JTable();
        tblChiTiet = new JTable();

        tblSanPham.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "Mã SP",
                    "Tên SP",
                    "Mã hãng",
                    "Giá bán",
                    "Tồn kho",
                    "Mô tả"
                }
        ));

        tblChiTiet.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "Mã SP",
                    "Tên SP",
                    "Số lượng nhập",
                    "Giá nhập",
                    "Thành tiền"
                }
        ));

        JScrollPane scrollSanPham = new JScrollPane(tblSanPham);
        JScrollPane scrollChiTiet = new JScrollPane(tblChiTiet);

        btnTimKiem.addActionListener(e -> timSanPham());
        btnThemSP.addActionListener(e -> themSanPhamVaoPhieu());
        btnXoaSP.addActionListener(e -> xoaSanPhamKhoiPhieu());
        btnLuu.addActionListener(e -> luuPhieuNhap());
        btnHuy.addActionListener(e -> dispose());

        lblTongTien = new JLabel("Tổng tiền nhập: 0 VNĐ");
        lblTongTien.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTongTien.setForeground(new Color(220, 38, 38));

        JPanel panelMain = new JPanel(new BorderLayout(15, 15));
        panelMain.setBorder(BorderFactory.createEmptyBorder(25, 25, 20, 25));
        panelMain.setBackground(new Color(245, 247, 250));

        JPanel panelTop = new JPanel(new BorderLayout());
        panelTop.setBackground(new Color(245, 247, 250));
        panelTop.add(lblTitle, BorderLayout.WEST);

        JPanel panelContent = new JPanel(new GridLayout(1, 2, 20, 20));
        panelContent.setBackground(new Color(245, 247, 250));

        JPanel panelSanPham = new JPanel(new BorderLayout(8, 8));
        panelSanPham.setBorder(BorderFactory.createTitledBorder("Danh sách sản phẩm"));
        panelSanPham.setBackground(Color.WHITE);

        JPanel searchPanel = new JPanel(new BorderLayout(8, 8));
        searchPanel.setBackground(Color.WHITE);

        btnTimKiem.setPreferredSize(new Dimension(95, 34));

        searchPanel.add(txtTimKiem, BorderLayout.CENTER);
        searchPanel.add(btnTimKiem, BorderLayout.EAST);

        panelSanPham.add(searchPanel, BorderLayout.NORTH);
        panelSanPham.add(scrollSanPham, BorderLayout.CENTER);

        JPanel panelChiTiet = new JPanel(new BorderLayout(8, 8));
        panelChiTiet.setBorder(BorderFactory.createTitledBorder("Sản phẩm đang nhập"));
        panelChiTiet.setBackground(Color.WHITE);

        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 6));
        inputPanel.setBackground(Color.WHITE);

        spnSoLuong.setPreferredSize(new Dimension(70, 32));

        btnThemSP.setPreferredSize(new Dimension(130, 34));
        btnXoaSP.setPreferredSize(new Dimension(125, 34));

        inputPanel.add(new JLabel("Số lượng:"));
        inputPanel.add(spnSoLuong);
        inputPanel.add(btnThemSP);
        inputPanel.add(btnXoaSP);

        JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        totalPanel.setBackground(Color.WHITE);
        totalPanel.add(lblTongTien);

        panelChiTiet.add(inputPanel, BorderLayout.NORTH);
        panelChiTiet.add(scrollChiTiet, BorderLayout.CENTER);
        panelChiTiet.add(totalPanel, BorderLayout.SOUTH);

        panelContent.add(panelSanPham);
        panelContent.add(panelChiTiet);

        JPanel panelBottom = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        panelBottom.setBackground(new Color(245, 247, 250));

        btnLuu.setPreferredSize(new Dimension(150, 36));
        btnHuy.setPreferredSize(new Dimension(85, 36));

        panelBottom.add(btnLuu);
        panelBottom.add(btnHuy);

        panelMain.add(panelTop, BorderLayout.NORTH);
        panelMain.add(panelContent, BorderLayout.CENTER);
        panelMain.add(panelBottom, BorderLayout.SOUTH);

        setContentPane(panelMain);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(1360, 770);
        setLocationRelativeTo(null);
    }
}