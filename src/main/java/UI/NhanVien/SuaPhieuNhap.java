package UI.NhanVien;

import Model.ChiTietPhieuNhap;
import Model.SanPham;
import Service.ChiTietPhieuNhapService;
import Service.SanPhamService;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class SuaPhieuNhap extends JDialog {

    private int maPN;

    private JTable tblChiTiet;
    private JTextField txtDonGiaNhap;
    private JSpinner spnSoLuong;

    private JButton btnCapNhatDong;
    private JButton btnXoaDong;
    private JButton btnLuu;
    private JButton btnHuy;

    private JLabel lblTongTien;

    private double tongTien = 0;

    public SuaPhieuNhap(Frame parent, boolean modal, int maPN) {
        super(parent, modal);
        this.maPN = maPN;

        initComponents();
        customUI();
        loadChiTiet();
        tinhTongTien();
    }

    private void customUI() {
        setTitle("Sửa phiếu nhập #" + maPN);
        getContentPane().setBackground(new Color(245, 247, 250));

        tblChiTiet.setRowHeight(30);
        tblChiTiet.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        tblChiTiet.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tblChiTiet.getTableHeader().setBackground(new Color(37, 99, 235));
        tblChiTiet.getTableHeader().setForeground(Color.WHITE);

        tblChiTiet.setShowGrid(true);
        tblChiTiet.setGridColor(Color.LIGHT_GRAY);

        Font btnFont = new Font("Segoe UI", Font.BOLD, 13);

        JButton[] buttons = {
            btnCapNhatDong,
            btnXoaDong,
            btnLuu,
            btnHuy
        };

        for (JButton btn : buttons) {
            btn.setFont(btnFont);
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        btnCapNhatDong.setBackground(new Color(37, 99, 235));
        btnXoaDong.setBackground(new Color(220, 38, 38));
        btnLuu.setBackground(new Color(22, 163, 74));
        btnHuy.setBackground(new Color(107, 114, 128));
    }

    private void loadChiTiet() {
        ChiTietPhieuNhapService service = new ChiTietPhieuNhapService();
        ArrayList<ChiTietPhieuNhap> list = service.getByMaPN(maPN);

        DefaultTableModel model = new DefaultTableModel();

        model.setColumnIdentifiers(new Object[]{
            "Mã SP",
            "Tên SP",
            "Số lượng",
            "Đơn giá nhập",
            "Thành tiền"
        });

        SanPhamService spService = new SanPhamService();
        ArrayList<SanPham> dsSP = spService.getAllSanPham();

        for (ChiTietPhieuNhap ct : list) {
            String tenSP = "";

            for (SanPham sp : dsSP) {
                if (sp.getMaSP() == ct.getMaSP()) {
                    tenSP = sp.getTenSP();
                    break;
                }
            }

            model.addRow(new Object[]{
                ct.getMaSP(),
                tenSP,
                ct.getSoLuong(),
                ct.getDonGiaNhap(),
                ct.getThanhTien()
            });
        }

        tblChiTiet.setModel(model);
    }

    private void chonDong() {
        int row = tblChiTiet.getSelectedRow();

        if (row == -1) {
            return;
        }

        spnSoLuong.setValue(
                Integer.parseInt(tblChiTiet.getValueAt(row, 2).toString())
        );

        txtDonGiaNhap.setText(
                tblChiTiet.getValueAt(row, 3).toString()
        );
    }

    private void capNhatDong() {
        int row = tblChiTiet.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng cần sửa!");
            return;
        }

        int soLuong = Integer.parseInt(spnSoLuong.getValue().toString());

        double donGiaNhap;

        try {
            donGiaNhap = Double.parseDouble(txtDonGiaNhap.getText().trim());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Đơn giá nhập phải là số!");
            return;
        }

        if (soLuong <= 0) {
            JOptionPane.showMessageDialog(this, "Số lượng phải lớn hơn 0!");
            return;
        }

        if (donGiaNhap <= 0) {
            JOptionPane.showMessageDialog(this, "Đơn giá nhập phải lớn hơn 0!");
            return;
        }

        double thanhTien = soLuong * donGiaNhap;

        tblChiTiet.setValueAt(soLuong, row, 2);
        tblChiTiet.setValueAt(donGiaNhap, row, 3);
        tblChiTiet.setValueAt(thanhTien, row, 4);

        tinhTongTien();
    }

    private void xoaDong() {
        int row = tblChiTiet.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng cần xóa!");
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
                "Tổng tiền: " + String.format("%,.0f VNĐ", tongTien)
        );
    }

    private void luuThayDoi() {
        DefaultTableModel model = (DefaultTableModel) tblChiTiet.getModel();

        if (model.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Phiếu nhập phải có ít nhất 1 sản phẩm!");
            return;
        }

        ChiTietPhieuNhapService ctService = new ChiTietPhieuNhapService();

        ctService.xoaChiTietTheoPhieuNhap(maPN);

        boolean ok = true;

        for (int i = 0; i < model.getRowCount(); i++) {
            int maSP = Integer.parseInt(model.getValueAt(i, 0).toString());
            int soLuong = Integer.parseInt(model.getValueAt(i, 2).toString());
            double donGiaNhap = Double.parseDouble(model.getValueAt(i, 3).toString());
            double thanhTien = Double.parseDouble(model.getValueAt(i, 4).toString());

            ChiTietPhieuNhap ct = new ChiTietPhieuNhap();

            ct.setMaPN(maPN);
            ct.setMaSP(maSP);
            ct.setSoLuong(soLuong);
            ct.setDonGiaNhap(donGiaNhap);
            ct.setThanhTien(thanhTien);

            if (!ctService.themChiTietPhieuNhap(ct)) {
                ok = false;
                break;
            }
        }

        if (ok) {
            JOptionPane.showMessageDialog(this, "Sửa phiếu nhập thành công!");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Sửa phiếu nhập thất bại!");
        }
    }

    private void initComponents() {
        JLabel lblTitle = new JLabel("SỬA PHIẾU NHẬP #" + maPN);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setForeground(new Color(0, 86, 179));

        tblChiTiet = new JTable();

        tblChiTiet.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "Mã SP",
                    "Tên SP",
                    "Số lượng",
                    "Đơn giá nhập",
                    "Thành tiền"
                }
        ));

        tblChiTiet.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                chonDong();
            }
        });

        JScrollPane scrollPane = new JScrollPane(tblChiTiet);

        spnSoLuong = new JSpinner(new SpinnerNumberModel(1, 1, 999, 1));
        txtDonGiaNhap = new JTextField();

        spnSoLuong.setPreferredSize(new Dimension(80, 30));
        txtDonGiaNhap.setPreferredSize(new Dimension(130, 30));

        btnCapNhatDong = new JButton("Cập nhật dòng");
        btnXoaDong = new JButton("Xóa dòng");
        btnLuu = new JButton("Lưu thay đổi");
        btnHuy = new JButton("Hủy");

        btnCapNhatDong.addActionListener(e -> capNhatDong());
        btnXoaDong.addActionListener(e -> xoaDong());
        btnLuu.addActionListener(e -> luuThayDoi());
        btnHuy.addActionListener(e -> dispose());

        lblTongTien = new JLabel("Tổng tiền: 0 VNĐ");
        lblTongTien.setFont(new Font("Segoe UI", Font.BOLD, 17));
        lblTongTien.setForeground(new Color(220, 38, 38));

        JPanel panelMain = new JPanel(new BorderLayout(12, 12));
        panelMain.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));
        panelMain.setBackground(new Color(245, 247, 250));

        JPanel panelInput = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 5));
        panelInput.setBackground(new Color(245, 247, 250));

        panelInput.add(new JLabel("Số lượng:"));
        panelInput.add(spnSoLuong);
        panelInput.add(new JLabel("Đơn giá nhập:"));
        panelInput.add(txtDonGiaNhap);
        panelInput.add(btnCapNhatDong);
        panelInput.add(btnXoaDong);

        JPanel panelTop = new JPanel(new BorderLayout());
        panelTop.setBackground(new Color(245, 247, 250));
        panelTop.add(lblTitle, BorderLayout.WEST);
        panelTop.add(panelInput, BorderLayout.SOUTH);

        JPanel panelBottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBottom.setBackground(new Color(245, 247, 250));
        panelBottom.add(lblTongTien);
        panelBottom.add(btnLuu);
        panelBottom.add(btnHuy);

        panelMain.add(panelTop, BorderLayout.NORTH);
        panelMain.add(scrollPane, BorderLayout.CENTER);
        panelMain.add(panelBottom, BorderLayout.SOUTH);

        setContentPane(panelMain);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(760, 520);
        setLocationRelativeTo(null);
    }
}