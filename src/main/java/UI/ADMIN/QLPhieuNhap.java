package UI.ADMIN;

import Model.PhieuNhap;
import Service.PhieuNhapService;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class QLPhieuNhap extends javax.swing.JFrame {

    private JTable tblPhieuNhap;
    private JTextField txtTimKiem;
    private JLabel lblTongPhieu;
    private JLabel lblTongTien;
    private JButton btnTimKiem, btnLamMoi, btnThem, btnXoa, btnChiTiet, btnQuayLai;

    private final Color BG = new Color(245, 247, 250);
    private final Color PRIMARY = new Color(37, 99, 235);
    private final Color SUCCESS = new Color(22, 163, 74);
    private final Color DANGER = new Color(220, 38, 38);
    private final Color WARNING = new Color(249, 115, 22);
    private final Color MUTED = new Color(107, 114, 128);
    private final Color TEXT = new Color(30, 41, 59);

    public QLPhieuNhap() {
        initComponents();
        loadPhieuNhap();
    }

    private void loadPhieuNhap() {
        PhieuNhapService service = new PhieuNhapService();
        ArrayList<PhieuNhap> list = service.getAllPhieuNhap();
        hienThiTable(list);
        capNhatThongKe(list);
    }

    private void hienThiTable(ArrayList<PhieuNhap> list) {
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        model.setColumnIdentifiers(new Object[]{
            "Mã PN", "Mã NV", "Ngày nhập", "Tổng tiền"
        });

        for (PhieuNhap pn : list) {
            model.addRow(new Object[]{
                pn.getMaPN(),
                pn.getMaNV(),
                pn.getNgayNhap(),
                String.format("%,.0f VNĐ", pn.getTongTien())
            });
        }

        tblPhieuNhap.setModel(model);
    }

    private void capNhatThongKe(ArrayList<PhieuNhap> list) {
        double tong = 0;
        for (PhieuNhap pn : list) {
            tong += pn.getTongTien();
        }
        lblTongPhieu.setText("Tổng phiếu: " + list.size());
        lblTongTien.setText("Tổng nhập: " + String.format("%,.0f VNĐ", tong));
    }

    private void timKiem() {
        String keyword = txtTimKiem.getText().trim().toLowerCase();
        PhieuNhapService service = new PhieuNhapService();
        ArrayList<PhieuNhap> list = service.getAllPhieuNhap();
        ArrayList<PhieuNhap> ketQua = new ArrayList<>();

        for (PhieuNhap pn : list) {
            if (String.valueOf(pn.getMaPN()).contains(keyword)
                    || String.valueOf(pn.getMaNV()).contains(keyword)
                    || String.valueOf(pn.getNgayNhap()).toLowerCase().contains(keyword)) {
                ketQua.add(pn);
            }
        }

        hienThiTable(ketQua);
        capNhatThongKe(ketQua);
    }

    private void xoaPhieuNhap() {
        int row = tblPhieuNhap.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn phiếu nhập cần xóa!");
            return;
        }

        int maPN = Integer.parseInt(tblPhieuNhap.getValueAt(row, 0).toString());

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc muốn xóa phiếu nhập #" + maPN + "?",
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            PhieuNhapService service = new PhieuNhapService();

            if (service.xoaPhieuNhap(maPN)) {
                JOptionPane.showMessageDialog(this, "Xóa phiếu nhập thành công!");
                loadPhieuNhap();
            } else {
                JOptionPane.showMessageDialog(this, "Xóa phiếu nhập thất bại!");
            }
        }
    }

    private void xemChiTiet() {
        int row = tblPhieuNhap.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn phiếu nhập cần xem chi tiết!");
            return;
        }

        int maPN = Integer.parseInt(tblPhieuNhap.getValueAt(row, 0).toString());
        ChiTietPhieuNhapDialog dialog = new ChiTietPhieuNhapDialog(this, true, maPN);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void initComponents() {
        setTitle("Quản lý phiếu nhập");
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setSize(880, 580);
        setResizable(false);
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout(0, 18));
        root.setBorder(new EmptyBorder(24, 28, 24, 28));
        root.setBackground(BG);
        setContentPane(root);

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);

        JLabel lblTitle = new JLabel("Quản lý phiếu nhập");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitle.setForeground(TEXT);

        lblTongPhieu = new JLabel("Tổng phiếu: 0");
        lblTongPhieu.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTongPhieu.setForeground(PRIMARY);

        JPanel titleBox = new JPanel(new GridLayout(2, 1, 0, 3));
        titleBox.setOpaque(false);
        titleBox.add(lblTitle);
        titleBox.add(lblTongPhieu);

        btnQuayLai = createButton("Quay lại", MUTED);
        btnQuayLai.addActionListener(e -> {
            TrangChuAdmin tc = new TrangChuAdmin();
            tc.setVisible(true);
            dispose();
        });

        header.add(titleBox, BorderLayout.WEST);
        header.add(btnQuayLai, BorderLayout.EAST);
        root.add(header, BorderLayout.NORTH);

        JPanel card = createCardPanel(new BorderLayout(0, 14));

        txtTimKiem = new JTextField();
        txtTimKiem.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtTimKiem.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240)),
                new EmptyBorder(8, 12, 8, 12)
        ));

        btnTimKiem = createButton("Tìm kiếm", PRIMARY);
        btnLamMoi = createButton("Làm mới", WARNING);
        btnThem = createButton("Thêm", SUCCESS);
        btnXoa = createButton("Xóa", DANGER);
        btnChiTiet = createButton("Chi tiết", PRIMARY);

        btnTimKiem.addActionListener(e -> timKiem());
        btnLamMoi.addActionListener(e -> {
            txtTimKiem.setText("");
            loadPhieuNhap();
        });
        btnThem.addActionListener(e -> {
            ThemPhieuNhap dialog = new ThemPhieuNhap(this, true);
            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);
            loadPhieuNhap();
        });
        btnXoa.addActionListener(e -> xoaPhieuNhap());
        btnChiTiet.addActionListener(e -> xemChiTiet());

        JPanel toolbar = new JPanel(new BorderLayout(10, 0));
        toolbar.setOpaque(false);
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        actions.setOpaque(false);
        actions.add(btnTimKiem);
        actions.add(btnLamMoi);
        actions.add(btnThem);
        actions.add(btnXoa);
        actions.add(btnChiTiet);

        toolbar.add(txtTimKiem, BorderLayout.CENTER);
        toolbar.add(actions, BorderLayout.EAST);
        card.add(toolbar, BorderLayout.NORTH);

        tblPhieuNhap = new JTable();
        customTable(tblPhieuNhap);

        JScrollPane scroll = new JScrollPane(tblPhieuNhap);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240)));
        scroll.getViewport().setBackground(Color.WHITE);
        card.add(scroll, BorderLayout.CENTER);

        lblTongTien = new JLabel("Tổng nhập: 0 VNĐ");
        lblTongTien.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTongTien.setForeground(SUCCESS);

        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footer.setOpaque(false);
        footer.add(lblTongTien);
        card.add(footer, BorderLayout.SOUTH);

        root.add(card, BorderLayout.CENTER);
    }

    private JPanel createCardPanel(LayoutManager layout) {
        JPanel panel = new JPanel(layout);
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240)),
                new EmptyBorder(18, 18, 18, 18)
        ));
        return panel;
    }

    private JButton createButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setForeground(Color.WHITE);
        btn.setBackground(bg);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(new EmptyBorder(9, 16, 9, 16));
        return btn;
    }

    private void customTable(JTable table) {
        table.setRowHeight(34);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(PRIMARY);
        table.getTableHeader().setForeground(Color.WHITE);
        table.setSelectionBackground(new Color(219, 234, 254));
        table.setSelectionForeground(TEXT);
        table.setGridColor(new Color(226, 232, 240));
        table.setShowVerticalLines(false);
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new QLPhieuNhap().setVisible(true));
    }
}
