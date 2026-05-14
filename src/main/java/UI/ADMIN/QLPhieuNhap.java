package UI.ADMIN;

import Model.PhieuNhap;
import Service.PhieuNhapService;
import Utils.Session;

import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class QLPhieuNhap extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(QLPhieuNhap.class.getName());

    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblPhieuNhap;

    private javax.swing.JButton btnChiTiet;
    private javax.swing.JButton btnLamMoi;
    private javax.swing.JButton btnQuayLai;

    private boolean choPhepSua;

    public QLPhieuNhap() {
        this(false);
    }

    public QLPhieuNhap(boolean choPhepSua) {
        this.choPhepSua = choPhepSua;

        initComponents();
        getContentPane().setBackground(new java.awt.Color(135, 206, 235));
        customTable();
        loadPhieuNhap();
    }

    private void customTable() {
        tblPhieuNhap.setRowHeight(30);
        tblPhieuNhap.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13));

        tblPhieuNhap.getTableHeader().setFont(
                new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14)
        );

        tblPhieuNhap.getTableHeader().setBackground(java.awt.Color.BLACK);
        tblPhieuNhap.getTableHeader().setForeground(java.awt.Color.WHITE);

        tblPhieuNhap.setShowGrid(true);
        tblPhieuNhap.setGridColor(java.awt.Color.GRAY);
    }

    private void loadPhieuNhap() {
        PhieuNhapService service = new PhieuNhapService();
        ArrayList<PhieuNhap> list = service.getAllPhieuNhap();

        DefaultTableModel model = new DefaultTableModel();

        model.setColumnIdentifiers(new Object[]{
            "Mã PN",
            "Mã NV",
            "Ngày nhập",
            "Tổng tiền"
        });

        for (PhieuNhap pn : list) {
            model.addRow(new Object[]{
                pn.getMaPN(),
                pn.getMaNV(),
                pn.getNgayNhap(),
                String.format("%.1f triệu", pn.getTongTien() / 1000000)
            });
        }

        tblPhieuNhap.setModel(model);
    }


    private void xemChiTiet() {
        int row = tblPhieuNhap.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn phiếu nhập cần xem chi tiết!");
            return;
        }

        int maPN = Integer.parseInt(tblPhieuNhap.getValueAt(row, 0).toString());

        ChiTietPhieuNhapDialog dialog =
                new ChiTietPhieuNhapDialog(this, true, maPN);

        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void quayLai() {
        if (Session.currentUser != null
                && "NhanVien".equalsIgnoreCase(Session.currentUser.getVaiTro())) {

            UI.NhanVien.TrangChuNhanVien tc =
                    new UI.NhanVien.TrangChuNhanVien();

            tc.setVisible(true);

        } else {
            TrangChuAdmin tc = new TrangChuAdmin();
            tc.setVisible(true);
        }

        this.dispose();
    }

    private void initComponents() {
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPhieuNhap = new javax.swing.JTable();

        btnChiTiet = new javax.swing.JButton();
        btnLamMoi = new javax.swing.JButton();
        btnQuayLai = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tblPhieuNhap.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "Mã PN",
                    "Mã NV",
                    "Ngày nhập",
                    "Tổng tiền"
                }
        ));

        jScrollPane1.setViewportView(tblPhieuNhap);


        btnChiTiet.setText("Chi Tiết");
        btnChiTiet.addActionListener(e -> xemChiTiet());

        btnLamMoi.setText("Làm mới");
        btnLamMoi.addActionListener(e -> loadPhieuNhap());

        btnQuayLai.setText("Quay Lại");
        btnQuayLai.addActionListener(e -> quayLai());

        javax.swing.GroupLayout layout =
                new javax.swing.GroupLayout(getContentPane());

        getContentPane().setLayout(layout);

        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)

                        .addGroup(layout.createSequentialGroup()
                                .addGap(143)

                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)

                                        .addComponent(jScrollPane1,
                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                452,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)

                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(btnChiTiet)
                                                .addGap(30)
                                                .addComponent(btnLamMoi)
                                                .addGap(30)
                                                .addComponent(btnQuayLai)))

                                .addContainerGap(143, Short.MAX_VALUE))
        );

        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)

                        .addGroup(layout.createSequentialGroup()
                                .addGap(20)

                                .addComponent(jScrollPane1,
                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                        310,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)

                                .addGap(18)

                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                     
                                        .addComponent(btnChiTiet)
                                        .addComponent(btnLamMoi)
                                        .addComponent(btnQuayLai))

                                .addContainerGap(68, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info
                    : javax.swing.UIManager.getInstalledLookAndFeels()) {

                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }

        } catch (ReflectiveOperationException
                | javax.swing.UnsupportedLookAndFeelException ex) {

            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(
                () -> new QLPhieuNhap(true).setVisible(true)
        );
    }
}