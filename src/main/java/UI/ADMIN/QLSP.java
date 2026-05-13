package UI.ADMIN;

import Model.SanPham;
import Service.SanPhamService;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class QLSP extends javax.swing.JFrame {

    private JTable tblSanPham;
    private JScrollPane jScrollPane1;
    private JTextField txtTimKiem;
    private JButton btnTimKiem, btnLamMoi, btnThem, btnSua, btnXoa, btnQuayLai;
    private JLabel lblTitle;

    public QLSP() {
        initComponents();
        customUI();
        loadSanPham();
    }

    private void customUI() {
        setTitle("Quản lý sản phẩm");
        getContentPane().setBackground(new Color(245, 247, 250));

        tblSanPham.setRowHeight(32);
        tblSanPham.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tblSanPham.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tblSanPham.getTableHeader().setBackground(new Color(37, 99, 235));
        tblSanPham.getTableHeader().setForeground(Color.WHITE);

        Font btnFont = new Font("Segoe UI", Font.BOLD, 13);
        JButton[] buttons = {btnTimKiem, btnLamMoi, btnThem, btnSua, btnXoa, btnQuayLai};

        for (JButton btn : buttons) {
            btn.setFont(btnFont);
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        btnTimKiem.setBackground(new Color(124, 58, 237));
        btnLamMoi.setBackground(new Color(249, 115, 22));
        btnThem.setBackground(new Color(22, 163, 74));
        btnSua.setBackground(new Color(37, 99, 235));
        btnXoa.setBackground(new Color(220, 38, 38));
        btnQuayLai.setBackground(new Color(107, 114, 128));
    }

    private void loadSanPham() {
        SanPhamService service = new SanPhamService();
        ArrayList<SanPham> list = service.getAllSanPham();
        hienThiTable(list);
    }

    private void hienThiTable(ArrayList<SanPham> list) {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[]{
            "Mã SP", "Tên SP", "Mã hãng", "Giá bán", "Số lượng", "Mô tả"
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
    }

    private void timKiemSanPham() {
        String keyword = txtTimKiem.getText().trim().toLowerCase();

        SanPhamService service = new SanPhamService();
        ArrayList<SanPham> list = service.getAllSanPham();
        ArrayList<SanPham> ketQua = new ArrayList<>();

        for (SanPham sp : list) {
            if (String.valueOf(sp.getMaSP()).contains(keyword)
                    || sp.getTenSP().toLowerCase().contains(keyword)
                    || String.valueOf(sp.getMaHang()).contains(keyword)
                    || sp.getMoTa().toLowerCase().contains(keyword)) {
                ketQua.add(sp);
            }
        }

        hienThiTable(ketQua);
    }

    private void initComponents() {
        lblTitle = new JLabel("QUẢN LÝ SẢN PHẨM");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));

        txtTimKiem = new JTextField();
        btnTimKiem = new JButton("Tìm kiếm");
        btnLamMoi = new JButton("Làm mới");

        tblSanPham = new JTable();
        jScrollPane1 = new JScrollPane(tblSanPham);

        btnThem = new JButton("Thêm");
        btnSua = new JButton("Sửa");
        btnXoa = new JButton("Xóa");
        btnQuayLai = new JButton("Quay lại");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tblSanPham.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Mã SP", "Tên SP", "Mã hãng", "Giá bán", "Số lượng", "Mô tả"}
        ));

        btnTimKiem.addActionListener(e -> timKiemSanPham());

        btnLamMoi.addActionListener(e -> {
            txtTimKiem.setText("");
            loadSanPham();
        });

        btnThem.addActionListener(e -> {
            ThemSanPham dialog = new ThemSanPham(this, true);
            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);
            loadSanPham();
        });

        btnSua.addActionListener(e -> {
            int row = tblSanPham.getSelectedRow();

            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm cần sửa!");
                return;
            }

            int maSP = Integer.parseInt(tblSanPham.getValueAt(row, 0).toString());
            String tenSP = tblSanPham.getValueAt(row, 1).toString();
            int maHang = Integer.parseInt(tblSanPham.getValueAt(row, 2).toString());
            double giaBan = Double.parseDouble(tblSanPham.getValueAt(row, 3).toString());
            int soLuong = Integer.parseInt(tblSanPham.getValueAt(row, 4).toString());
            String moTa = tblSanPham.getValueAt(row, 5).toString();

            SanPham sp = new SanPham(maSP, tenSP, maHang, giaBan, soLuong, moTa);

            SuaSanPham dialog = new SuaSanPham(this, true, sp);
            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);

            loadSanPham();
        });

        btnXoa.addActionListener(e -> {
            int row = tblSanPham.getSelectedRow();

            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm cần xóa!");
                return;
            }

            int maSP = Integer.parseInt(tblSanPham.getValueAt(row, 0).toString());

            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Bạn có chắc muốn xóa sản phẩm này?",
                    "Xác nhận xóa",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                SanPhamService service = new SanPhamService();

                if (service.xoaSanPham(maSP)) {
                    JOptionPane.showMessageDialog(this, "Xóa sản phẩm thành công!");
                    loadSanPham();
                } else {
                    JOptionPane.showMessageDialog(this, "Xóa sản phẩm thất bại!");
                }
            }
        });

        btnQuayLai.addActionListener(e -> {
            TrangChuAdmin tc = new TrangChuAdmin();
            tc.setVisible(true);
            dispose();
        });

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(245, 247, 250));

        GroupLayout layout = new GroupLayout(mainPanel);
        mainPanel.setLayout(layout);

        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(30)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(lblTitle)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(txtTimKiem, 330, 330, 330)
                            .addGap(10)
                            .addComponent(btnTimKiem, 120, 120, 120)
                            .addGap(10)
                            .addComponent(btnLamMoi, 120, 120, 120))
                        .addComponent(jScrollPane1, 800, 800, 800)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(120)
                            .addComponent(btnThem, 120, 120, 120)
                            .addGap(20)
                            .addComponent(btnSua, 120, 120, 120)
                            .addGap(20)
                            .addComponent(btnXoa, 120, 120, 120)
                            .addGap(20)
                            .addComponent(btnQuayLai, 120, 120, 120)))
                    .addGap(30))
        );

        layout.setVerticalGroup(
            layout.createSequentialGroup()
                .addGap(20)
                .addComponent(lblTitle)
                .addGap(20)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTimKiem, 35, 35, 35)
                    .addComponent(btnTimKiem, 35, 35, 35)
                    .addComponent(btnLamMoi, 35, 35, 35))
                .addGap(20)
                .addComponent(jScrollPane1, 360, 360, 360)
                .addGap(25)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThem, 38, 38, 38)
                    .addComponent(btnSua, 38, 38, 38)
                    .addComponent(btnXoa, 38, 38, 38)
                    .addComponent(btnQuayLai, 38, 38, 38))
                .addGap(25)
        );

        setContentPane(mainPanel);
        setSize(880, 580);
        setResizable(false);
        setLocationRelativeTo(null);
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new QLSP().setVisible(true));
    }
}