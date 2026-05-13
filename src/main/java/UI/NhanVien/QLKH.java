package UI.NhanVien;

import Model.KhachHang;
import Service.KhachHangService;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class QLKH extends javax.swing.JFrame {

    private javax.swing.JTable tblKH;
    private javax.swing.JScrollPane jScrollPane1;

    private javax.swing.JTextField txtMaKH;
    private javax.swing.JTextField txtHoTen;
    private javax.swing.JTextField txtSDT;
    private javax.swing.JTextField txtDiaChi;
    private javax.swing.JTextField txtTimKiem;

    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnXoa;
    private javax.swing.JButton btnLamMoi;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnQuayLai;

    private javax.swing.JLabel lblTitle;

    private javax.swing.JLabel lblMaKH;
    private javax.swing.JLabel lblHoTen;
    private javax.swing.JLabel lblSDT;
    private javax.swing.JLabel lblDiaChi;

    public QLKH() {
        initComponents();
        customUI();
        loadKhachHang();
    }

    private void customUI() {

        setTitle("Quản lý khách hàng");
        setLocationRelativeTo(null);

        getContentPane().setBackground(new java.awt.Color(245, 247, 250));

        tblKH.setRowHeight(32);

        tblKH.setFont(
                new java.awt.Font("Segoe UI",
                        java.awt.Font.PLAIN,
                        13)
        );

        tblKH.getTableHeader().setFont(
                new java.awt.Font("Segoe UI",
                        java.awt.Font.BOLD,
                        14)
        );

        tblKH.getTableHeader().setBackground(
                new java.awt.Color(37, 99, 235)
        );

        tblKH.getTableHeader().setForeground(
                java.awt.Color.WHITE
        );

        java.awt.Font btnFont =
                new java.awt.Font("Segoe UI",
                        java.awt.Font.BOLD,
                        13);

        btnThem.setFont(btnFont);
        btnSua.setFont(btnFont);
        btnXoa.setFont(btnFont);
        btnLamMoi.setFont(btnFont);
        btnTimKiem.setFont(btnFont);
        btnQuayLai.setFont(btnFont);

        btnThem.setBackground(new java.awt.Color(22, 163, 74));
        btnSua.setBackground(new java.awt.Color(37, 99, 235));
        btnXoa.setBackground(new java.awt.Color(220, 38, 38));
        btnLamMoi.setBackground(new java.awt.Color(249, 115, 22));
        btnTimKiem.setBackground(new java.awt.Color(124, 58, 237));
        btnQuayLai.setBackground(new java.awt.Color(107, 114, 128));

        btnThem.setForeground(java.awt.Color.WHITE);
        btnSua.setForeground(java.awt.Color.WHITE);
        btnXoa.setForeground(java.awt.Color.WHITE);
        btnLamMoi.setForeground(java.awt.Color.WHITE);
        btnTimKiem.setForeground(java.awt.Color.WHITE);
        btnQuayLai.setForeground(java.awt.Color.WHITE);

        btnThem.setFocusPainted(false);
        btnSua.setFocusPainted(false);
        btnXoa.setFocusPainted(false);
        btnLamMoi.setFocusPainted(false);
        btnTimKiem.setFocusPainted(false);
        btnQuayLai.setFocusPainted(false);

        java.awt.Font labelFont =
                new java.awt.Font(
                        "Segoe UI",
                        java.awt.Font.BOLD,
                        14
                );

        lblMaKH.setFont(labelFont);
        lblHoTen.setFont(labelFont);
        lblSDT.setFont(labelFont);
        lblDiaChi.setFont(labelFont);
    }

    private void loadKhachHang() {

        KhachHangService service =
                new KhachHangService();

        ArrayList<KhachHang> list =
                service.getAllKhachHang();

        hienThiTable(list);
    }

    private void hienThiTable(ArrayList<KhachHang> list) {

        DefaultTableModel model =
                new DefaultTableModel();

        model.setColumnIdentifiers(
                new Object[]{
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

        tblKH.setModel(model);
    }

    private void themKhachHang() {

        KhachHang kh = new KhachHang();

        kh.setHoTen(txtHoTen.getText());
        kh.setSdt(txtSDT.getText());
        kh.setDiaChi(txtDiaChi.getText());

        KhachHangService service =
                new KhachHangService();

        if (service.themKhachHang(kh)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Thêm khách hàng thành công!"
            );

            loadKhachHang();
            clearForm();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Thêm thất bại!"
            );
        }
    }

    private void suaKhachHang() {

        if (txtMaKH.getText().isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Chọn khách hàng cần sửa!"
            );

            return;
        }

        KhachHang kh = new KhachHang();

        kh.setMaKH(
                Integer.parseInt(txtMaKH.getText())
        );

        kh.setHoTen(txtHoTen.getText());
        kh.setSdt(txtSDT.getText());
        kh.setDiaChi(txtDiaChi.getText());

        KhachHangService service =
                new KhachHangService();

        if (service.suaKhachHang(kh)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Sửa thành công!"
            );

            loadKhachHang();
            clearForm();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Sửa thất bại!"
            );
        }
    }

    private void xoaKhachHang() {

        if (txtMaKH.getText().isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Chọn khách hàng cần xóa!"
            );

            return;
        }

        int confirm =
                JOptionPane.showConfirmDialog(
                        this,
                        "Bạn chắc chắn muốn xóa?",
                        "Xác nhận",
                        JOptionPane.YES_NO_OPTION
                );

        if (confirm == JOptionPane.YES_OPTION) {

            int maKH =
                    Integer.parseInt(txtMaKH.getText());

            KhachHangService service =
                    new KhachHangService();

            if (service.xoaKhachHang(maKH)) {

                JOptionPane.showMessageDialog(
                        this,
                        "Xóa thành công!"
                );

                loadKhachHang();
                clearForm();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Xóa thất bại!"
                );
            }
        }
    }

    private void timKiemKhachHang() {

        String keyword =
                txtTimKiem.getText().toLowerCase();

        KhachHangService service =
                new KhachHangService();

        ArrayList<KhachHang> list =
                service.getAllKhachHang();

        ArrayList<KhachHang> ketQua =
                new ArrayList<>();

        for (KhachHang kh : list) {

            if (kh.getHoTen().toLowerCase().contains(keyword)
                    || kh.getSdt().toLowerCase().contains(keyword)
                    || kh.getDiaChi().toLowerCase().contains(keyword)) {

                ketQua.add(kh);
            }
        }

        hienThiTable(ketQua);
    }

    private void clearForm() {

        txtMaKH.setText("");
        txtHoTen.setText("");
        txtSDT.setText("");
        txtDiaChi.setText("");
    }

    private void initComponents() {

        lblTitle = new javax.swing.JLabel();

        lblMaKH = new javax.swing.JLabel();
        lblHoTen = new javax.swing.JLabel();
        lblSDT = new javax.swing.JLabel();
        lblDiaChi = new javax.swing.JLabel();

        txtTimKiem = new javax.swing.JTextField();

        btnTimKiem = new javax.swing.JButton();
        btnLamMoi = new javax.swing.JButton();

        jScrollPane1 = new javax.swing.JScrollPane();

        tblKH = new javax.swing.JTable();

        txtMaKH = new javax.swing.JTextField();
        txtHoTen = new javax.swing.JTextField();
        txtSDT = new javax.swing.JTextField();
        txtDiaChi = new javax.swing.JTextField();

        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnQuayLai = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblTitle.setFont(
                new java.awt.Font("Segoe UI",
                        java.awt.Font.BOLD,
                        24)
        );

        lblTitle.setText("QUẢN LÝ KHÁCH HÀNG");

        lblMaKH.setText("Mã KH:");
        lblHoTen.setText("Họ tên:");
        lblSDT.setText("SĐT:");
        lblDiaChi.setText("Địa chỉ:");

        btnTimKiem.setText("Tìm kiếm");

        btnTimKiem.addActionListener(e ->
                timKiemKhachHang());

        btnLamMoi.setText("Làm mới");

        btnLamMoi.addActionListener(e -> {

            txtTimKiem.setText("");
            loadKhachHang();
        });

        tblKH.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "Mã KH",
                    "Họ tên",
                    "SĐT",
                    "Địa chỉ"
                }
        ));

        tblKH.addMouseListener(
                new java.awt.event.MouseAdapter() {

            public void mouseClicked(
                    java.awt.event.MouseEvent evt) {

                int row =
                        tblKH.getSelectedRow();

                txtMaKH.setText(
                        tblKH.getValueAt(row, 0).toString()
                );

                txtHoTen.setText(
                        tblKH.getValueAt(row, 1).toString()
                );

                txtSDT.setText(
                        tblKH.getValueAt(row, 2).toString()
                );

                txtDiaChi.setText(
                        tblKH.getValueAt(row, 3).toString()
                );
            }
        });

        jScrollPane1.setViewportView(tblKH);

        txtMaKH.setEditable(false);

        btnThem.setText("Thêm");
        btnThem.addActionListener(e -> themKhachHang());

        btnSua.setText("Sửa");
        btnSua.addActionListener(e -> suaKhachHang());

        btnXoa.setText("Xóa");
        btnXoa.addActionListener(e -> xoaKhachHang());

        btnQuayLai.setText("Quay lại");

        btnQuayLai.addActionListener(e -> {

            TrangChuNhanVien tc =
                    new TrangChuNhanVien();

            tc.setVisible(true);

            dispose();
        });

        javax.swing.GroupLayout layout =
                new javax.swing.GroupLayout(getContentPane());

        getContentPane().setLayout(layout);

        layout.setHorizontalGroup(
                layout.createParallelGroup(
                        javax.swing.GroupLayout.Alignment.LEADING)

                        .addGroup(layout.createSequentialGroup()
                                .addGap(30)

                                .addGroup(layout.createParallelGroup(
                                        javax.swing.GroupLayout.Alignment.LEADING)

                                        .addComponent(lblTitle)

                                        .addGroup(layout.createSequentialGroup()

                                                .addComponent(txtTimKiem,
                                                        300,
                                                        300,
                                                        300)

                                                .addGap(10)

                                                .addComponent(btnTimKiem,
                                                        120,
                                                        120,
                                                        120)

                                                .addGap(10)

                                                .addComponent(btnLamMoi,
                                                        120,
                                                        120,
                                                        120))

                                        .addComponent(jScrollPane1,
                                                740,
                                                740,
                                                740)

                                        .addGroup(layout.createSequentialGroup()

                                                .addGroup(layout.createParallelGroup(
                                                        javax.swing.GroupLayout.Alignment.LEADING)

                                                        .addComponent(lblMaKH)
                                                        .addComponent(lblHoTen)
                                                        .addComponent(lblSDT)
                                                        .addComponent(lblDiaChi))

                                                .addGap(15)

                                                .addGroup(layout.createParallelGroup(
                                                        javax.swing.GroupLayout.Alignment.LEADING)

                                                        .addComponent(txtMaKH,
                                                                250,
                                                                250,
                                                                250)

                                                        .addComponent(txtHoTen,
                                                                250,
                                                                250,
                                                                250)

                                                        .addComponent(txtSDT,
                                                                250,
                                                                250,
                                                                250)

                                                        .addComponent(txtDiaChi,
                                                                250,
                                                                250,
                                                                250))

                                                .addGap(50)

                                                .addGroup(layout.createParallelGroup(
                                                        javax.swing.GroupLayout.Alignment.LEADING)

                                                        .addComponent(btnThem,
                                                                140,
                                                                140,
                                                                140)

                                                        .addComponent(btnSua,
                                                                140,
                                                                140,
                                                                140)

                                                        .addComponent(btnXoa,
                                                                140,
                                                                140,
                                                                140)

                                                        .addComponent(btnQuayLai,
                                                                140,
                                                                140,
                                                                140))))

                                .addGap(30))
        );

        layout.setVerticalGroup(

                layout.createSequentialGroup()

                        .addGap(20)

                        .addComponent(lblTitle)

                        .addGap(20)

                        .addGroup(layout.createParallelGroup(
                                javax.swing.GroupLayout.Alignment.BASELINE)

                                .addComponent(txtTimKiem,
                                        35,
                                        35,
                                        35)

                                .addComponent(btnTimKiem,
                                        35,
                                        35,
                                        35)

                                .addComponent(btnLamMoi,
                                        35,
                                        35,
                                        35))

                        .addGap(20)

                        .addComponent(jScrollPane1,
                                300,
                                300,
                                300)

                        .addGap(20)

                        .addGroup(layout.createParallelGroup(
                                javax.swing.GroupLayout.Alignment.BASELINE)

                                .addComponent(lblMaKH)

                                .addComponent(txtMaKH,
                                        35,
                                        35,
                                        35)

                                .addComponent(btnThem,
                                        35,
                                        35,
                                        35))

                        .addGap(10)

                        .addGroup(layout.createParallelGroup(
                                javax.swing.GroupLayout.Alignment.BASELINE)

                                .addComponent(lblHoTen)

                                .addComponent(txtHoTen,
                                        35,
                                        35,
                                        35)

                                .addComponent(btnSua,
                                        35,
                                        35,
                                        35))

                        .addGap(10)

                        .addGroup(layout.createParallelGroup(
                                javax.swing.GroupLayout.Alignment.BASELINE)

                                .addComponent(lblSDT)

                                .addComponent(txtSDT,
                                        35,
                                        35,
                                        35)

                                .addComponent(btnXoa,
                                        35,
                                        35,
                                        35))

                        .addGap(10)

                        .addGroup(layout.createParallelGroup(
                                javax.swing.GroupLayout.Alignment.BASELINE)

                                .addComponent(lblDiaChi)

                                .addComponent(txtDiaChi,
                                        35,
                                        35,
                                        35)

                                .addComponent(btnQuayLai,
                                        35,
                                        35,
                                        35))

                        .addGap(20)
        );

        pack();
        setLocationRelativeTo(null);
    }

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(() ->
                new QLKH().setVisible(true));
    }
}