package UI.NhanVien;

import Service.SanPhamService;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

public class SanPham extends javax.swing.JFrame {

    private javax.swing.JTable tblSP;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton btnQuayLai, btnLamMoi, btnTimKiem;
    private javax.swing.JTextField txtTimKiem;
    private javax.swing.JLabel lblTieuDe, lblTimKiem;

    public SanPham() {
        initComponents();
        customUI();
        loadSanPham();
    }

    private void customUI() {
        setTitle("Xem sản phẩm tồn kho");
        setLocationRelativeTo(null);
        getContentPane().setBackground(new java.awt.Color(245, 247, 250));

        tblSP.setRowHeight(32);
        tblSP.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 13));

        tblSP.getTableHeader().setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
        tblSP.getTableHeader().setBackground(new java.awt.Color(37, 99, 235));
        tblSP.getTableHeader().setForeground(java.awt.Color.WHITE);

        tblSP.setShowGrid(true);
        tblSP.setGridColor(new java.awt.Color(200, 200, 200));

        java.awt.Font btnFont = new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13);

        btnTimKiem.setFont(btnFont);
        btnLamMoi.setFont(btnFont);
        btnQuayLai.setFont(btnFont);

        btnTimKiem.setBackground(new java.awt.Color(37, 99, 235));
        btnLamMoi.setBackground(new java.awt.Color(22, 163, 74));
        btnQuayLai.setBackground(new java.awt.Color(220, 38, 38));

        btnTimKiem.setForeground(java.awt.Color.WHITE);
        btnLamMoi.setForeground(java.awt.Color.WHITE);
        btnQuayLai.setForeground(java.awt.Color.WHITE);

        btnTimKiem.setFocusPainted(false);
        btnLamMoi.setFocusPainted(false);
        btnQuayLai.setFocusPainted(false);
    }

    private void loadSanPham() {
        SanPhamService service = new SanPhamService();
        ArrayList<Model.SanPham> list = service.getAllSanPham();
        hienThiSanPham(list);
    }

    private void hienThiSanPham(ArrayList<Model.SanPham> list) {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[]{
            "Mã SP", "Tên sản phẩm", "Mã hãng", "Giá bán", "Số lượng tồn", "Mô tả"
        });

        for (Model.SanPham sp : list) {
            model.addRow(new Object[]{
                sp.getMaSP(),
                sp.getTenSP(),
                sp.getMaHang(),
                String.format("%,.0f VNĐ", sp.getGiaBan()),
                sp.getSoLuong(),
                sp.getMoTa()
            });
        }

        tblSP.setModel(model);

        tblSP.getColumnModel().getColumn(0).setPreferredWidth(60);
        tblSP.getColumnModel().getColumn(1).setPreferredWidth(180);
        tblSP.getColumnModel().getColumn(2).setPreferredWidth(80);
        tblSP.getColumnModel().getColumn(3).setPreferredWidth(130);
        tblSP.getColumnModel().getColumn(4).setPreferredWidth(100);
        tblSP.getColumnModel().getColumn(5).setPreferredWidth(180);
    }

    private void timKiemSanPham() {
        String keyword = txtTimKiem.getText().trim().toLowerCase();

        SanPhamService service = new SanPhamService();
        ArrayList<Model.SanPham> list = service.getAllSanPham();
        ArrayList<Model.SanPham> ketQua = new ArrayList<>();

        for (Model.SanPham sp : list) {
            if (sp.getTenSP().toLowerCase().contains(keyword)
                    || sp.getMoTa().toLowerCase().contains(keyword)
                    || String.valueOf(sp.getMaSP()).contains(keyword)) {
                ketQua.add(sp);
            }
        }

        hienThiSanPham(ketQua);
    }

    private void initComponents() {

        lblTieuDe = new javax.swing.JLabel();
        lblTimKiem = new javax.swing.JLabel();
        txtTimKiem = new javax.swing.JTextField();
        btnTimKiem = new javax.swing.JButton();
        btnLamMoi = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSP = new javax.swing.JTable();
        btnQuayLai = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblTieuDe.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 24));
        lblTieuDe.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTieuDe.setText("DANH SÁCH SẢN PHẨM TỒN KHO");

        lblTimKiem.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
        lblTimKiem.setText("Tìm kiếm:");

        btnTimKiem.setText("Tìm kiếm");
        btnTimKiem.addActionListener(e -> timKiemSanPham());

        btnLamMoi.setText("Làm mới");
        btnLamMoi.addActionListener(e -> {
            txtTimKiem.setText("");
            loadSanPham();
        });

        tblSP.setModel(new javax.swing.table.DefaultTableModel(
            new Object[][]{},
            new String[]{"Mã SP", "Tên sản phẩm", "Mã hãng", "Giá bán", "Số lượng tồn", "Mô tả"}
        ));
        jScrollPane1.setViewportView(tblSP);

        btnQuayLai.setText("Quay lại");
        btnQuayLai.addActionListener(e -> {
            TrangChuNhanVien tc = new TrangChuNhanVien();
            tc.setVisible(true);
            dispose();
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(30)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lblTieuDe, 740, 740, 740)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(lblTimKiem)
                            .addGap(15)
                            .addComponent(txtTimKiem, 300, 300, 300)
                            .addGap(15)
                            .addComponent(btnTimKiem, 100, 100, 100)
                            .addGap(15)
                            .addComponent(btnLamMoi, 100, 100, 100))
                        .addComponent(jScrollPane1, 740, 740, 740)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(310)
                            .addComponent(btnQuayLai, 120, 120, 120)))
                    .addGap(30))
        );

        layout.setVerticalGroup(
            layout.createSequentialGroup()
                .addGap(20)
                .addComponent(lblTieuDe, 35, 35, 35)
                .addGap(20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTimKiem)
                    .addComponent(txtTimKiem, 32, 32, 32)
                    .addComponent(btnTimKiem, 32, 32, 32)
                    .addComponent(btnLamMoi, 32, 32, 32))
                .addGap(20)
                .addComponent(jScrollPane1, 320, 320, 320)
                .addGap(20)
                .addComponent(btnQuayLai, 35, 35, 35)
                .addGap(25)
        );

        pack();
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new SanPham().setVisible(true));
    }
}