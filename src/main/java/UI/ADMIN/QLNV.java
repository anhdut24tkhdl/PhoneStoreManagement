package UI.ADMIN;

import Model.NhanVien;
import Service.NhanVienService;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class QLNV extends javax.swing.JFrame {

    private JTable tblNhanVien;
    private JScrollPane jScrollPane1;
    private JTextField txtTimKiem;
    private JButton btnTimKiem, btnLamMoi, btnThem, btnSua, btnXoa, btnQuayLai;
    private JLabel lblTitle;

    public QLNV() {
        initComponents();
        customUI();
        loadNhanVien();
    }

    private void customUI() {
        setTitle("Quản lý nhân viên");
        getContentPane().setBackground(new Color(245, 247, 250));

        tblNhanVien.setRowHeight(32);
        tblNhanVien.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tblNhanVien.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tblNhanVien.getTableHeader().setBackground(new Color(37, 99, 235));
        tblNhanVien.getTableHeader().setForeground(Color.WHITE);

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

    private void loadNhanVien() {
        NhanVienService service = new NhanVienService();
        ArrayList<NhanVien> list = service.getAllNhanVien();
        hienThiTable(list);
    }

    private void hienThiTable(ArrayList<NhanVien> list) {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[]{
            "Mã NV", "Họ tên", "Giới tính", "SĐT", "Địa chỉ", "Chức vụ"
        });

        for (NhanVien nv : list) {
            model.addRow(new Object[]{
                nv.getMaNV(),
                nv.getHoTen(),
                nv.getGioiTinh(),
                nv.getSdt(),
                nv.getDiaChi(),
                nv.getChucVu()
            });
        }

        tblNhanVien.setModel(model);
    }

    private void timKiemNhanVien() {
        String keyword = txtTimKiem.getText().trim().toLowerCase();

        NhanVienService service = new NhanVienService();
        ArrayList<NhanVien> list = service.getAllNhanVien();
        ArrayList<NhanVien> ketQua = new ArrayList<>();

        for (NhanVien nv : list) {
            if (String.valueOf(nv.getMaNV()).contains(keyword)
                    || nv.getHoTen().toLowerCase().contains(keyword)
                    || nv.getSdt().toLowerCase().contains(keyword)
                    || nv.getDiaChi().toLowerCase().contains(keyword)
                    || nv.getChucVu().toLowerCase().contains(keyword)) {
                ketQua.add(nv);
            }
        }

        hienThiTable(ketQua);
    }

    private void initComponents() {
        lblTitle = new JLabel("QUẢN LÝ NHÂN VIÊN");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));

        txtTimKiem = new JTextField();
        btnTimKiem = new JButton("Tìm kiếm");
        btnLamMoi = new JButton("Làm mới");

        tblNhanVien = new JTable();
        jScrollPane1 = new JScrollPane(tblNhanVien);

        btnThem = new JButton("Thêm");
        btnSua = new JButton("Sửa");
        btnXoa = new JButton("Xóa");
        btnQuayLai = new JButton("Quay lại");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tblNhanVien.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Mã NV", "Họ tên", "Giới tính", "SĐT", "Địa chỉ", "Chức vụ"}
        ));

        btnTimKiem.addActionListener(e -> timKiemNhanVien());

        btnLamMoi.addActionListener(e -> {
            txtTimKiem.setText("");
            loadNhanVien();
        });

        btnThem.addActionListener(e -> {
            ThemNhanVien dialog = new ThemNhanVien(this, true);
            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);
            loadNhanVien();
        });

        btnSua.addActionListener(e -> {
            int row = tblNhanVien.getSelectedRow();

            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên cần sửa!");
                return;
            }

            int maNV = Integer.parseInt(tblNhanVien.getValueAt(row, 0).toString());
            String hoTen = tblNhanVien.getValueAt(row, 1).toString();
            String gioiTinh = tblNhanVien.getValueAt(row, 2).toString();
            String sdt = tblNhanVien.getValueAt(row, 3).toString();
            String diaChi = tblNhanVien.getValueAt(row, 4).toString();
            String chucVu = tblNhanVien.getValueAt(row, 5).toString();

            NhanVien nv = new NhanVien(maNV, hoTen, gioiTinh, sdt, diaChi, chucVu);

            SuaNhanVienDialog dialog = new SuaNhanVienDialog(this, true, nv);
            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);

            loadNhanVien();
        });

        btnXoa.addActionListener(e -> {
            int row = tblNhanVien.getSelectedRow();

            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên cần xóa!");
                return;
            }

            int maNV = Integer.parseInt(tblNhanVien.getValueAt(row, 0).toString());

            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Bạn có chắc muốn xóa nhân viên này?",
                    "Xác nhận xóa",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                NhanVienService service = new NhanVienService();

                if (service.xoaNhanVien(maNV)) {
                    JOptionPane.showMessageDialog(this, "Xóa nhân viên thành công!");
                    loadNhanVien();
                } else {
                    JOptionPane.showMessageDialog(this, "Xóa nhân viên thất bại!");
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
                        .addComponent(jScrollPane1, 740, 740, 740)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(90)
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
        setSize(820, 580);
        setResizable(false);
        setLocationRelativeTo(null);
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new QLNV().setVisible(true));
    }
}