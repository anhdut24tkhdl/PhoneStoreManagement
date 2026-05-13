package UI.ADMIN;

import Model.HoaDon;
import Service.HoaDonService;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class QLDonHang extends javax.swing.JFrame {

    private JTable tblHoaDon;
    private JTextField txtTimKiem;
    private JLabel lblTongDon;
    private JLabel lblTongDoanhThu;
    private JLabel lblDonCaoNhat;
    private JButton btnTimKiem;
    private JButton btnLamMoi;
    private JButton btnXoa;
    private JButton btnChiTiet;
    private JButton btnQuayLai;

    private final Color BG = new Color(245, 247, 250);
    private final Color PRIMARY = new Color(37, 99, 235);
    private final Color SUCCESS = new Color(22, 163, 74);
    private final Color DANGER = new Color(220, 38, 38);
    private final Color WARNING = new Color(249, 115, 22);
    private final Color MUTED = new Color(107, 114, 128);
    private final Color TEXT = new Color(30, 41, 59);

    public QLDonHang() {
        initComponents();
        loadHoaDon();
    }

    private void loadHoaDon() {
        HoaDonService service = new HoaDonService();
        ArrayList<HoaDon> list = service.getAllHoaDon();
        hienThiHoaDon(list);
        capNhatThongKe(list);
    }

    private void hienThiHoaDon(ArrayList<HoaDon> list) {
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        model.setColumnIdentifiers(new Object[]{
            "Mã HĐ", "Mã KH", "Mã NV", "Ngày lập", "Tổng tiền"
        });

        for (HoaDon hd : list) {
            model.addRow(new Object[]{
                hd.getMaHD(),
                hd.getMaKH(),
                hd.getMaNV(),
                hd.getNgayLap(),
                String.format("%,.0f VNĐ", hd.getTongTien())
            });
        }

        tblHoaDon.setModel(model);
        tblHoaDon.getColumnModel().getColumn(0).setPreferredWidth(70);
        tblHoaDon.getColumnModel().getColumn(1).setPreferredWidth(80);
        tblHoaDon.getColumnModel().getColumn(2).setPreferredWidth(80);
        tblHoaDon.getColumnModel().getColumn(3).setPreferredWidth(170);
        tblHoaDon.getColumnModel().getColumn(4).setPreferredWidth(150);
    }

    private void capNhatThongKe(ArrayList<HoaDon> list) {
        double tongDoanhThu = 0;
        double caoNhat = 0;

        for (HoaDon hd : list) {
            tongDoanhThu += hd.getTongTien();
            if (hd.getTongTien() > caoNhat) {
                caoNhat = hd.getTongTien();
            }
        }

        lblTongDon.setText(String.valueOf(list.size()));
        lblTongDoanhThu.setText(String.format("%,.0f VNĐ", tongDoanhThu));
        lblDonCaoNhat.setText(String.format("%,.0f VNĐ", caoNhat));
    }

    private void timKiemHoaDon() {
        String keyword = txtTimKiem.getText().trim().toLowerCase();

        HoaDonService service = new HoaDonService();
        ArrayList<HoaDon> list = service.getAllHoaDon();
        ArrayList<HoaDon> ketQua = new ArrayList<>();

        for (HoaDon hd : list) {
            if (String.valueOf(hd.getMaHD()).contains(keyword)
                    || String.valueOf(hd.getMaKH()).contains(keyword)
                    || String.valueOf(hd.getMaNV()).contains(keyword)
                    || String.valueOf(hd.getNgayLap()).toLowerCase().contains(keyword)) {
                ketQua.add(hd);
            }
        }

        hienThiHoaDon(ketQua);
        capNhatThongKe(ketQua);
    }

    private void xoaHoaDon() {
        int row = tblHoaDon.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn cần xóa!");
            return;
        }

        int maHD = Integer.parseInt(tblHoaDon.getValueAt(row, 0).toString());

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc muốn xóa hóa đơn #" + maHD + "?",
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            HoaDonService service = new HoaDonService();

            if (service.xoaHoaDon(maHD)) {
                JOptionPane.showMessageDialog(this, "Xóa hóa đơn thành công!");
                loadHoaDon();
            } else {
                JOptionPane.showMessageDialog(this, "Xóa hóa đơn thất bại!");
            }
        }
    }

    private void xemChiTiet() {
        int row = tblHoaDon.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn cần xem chi tiết!");
            return;
        }

        int maHD = Integer.parseInt(tblHoaDon.getValueAt(row, 0).toString());
        ChiTietHoaDonDialog dialog = new ChiTietHoaDonDialog(this, true, maHD);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void initComponents() {
        setTitle("Quản lý đơn hàng");
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setSize(980, 620);
        setResizable(false);
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout(0, 18));
        root.setBorder(new EmptyBorder(24, 28, 24, 28));
        root.setBackground(BG);
        setContentPane(root);

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);

        JLabel lblTitle = new JLabel("Quản lý đơn hàng");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitle.setForeground(TEXT);

        JLabel lblSubTitle = new JLabel("Theo dõi hóa đơn, doanh thu và xem chi tiết đơn hàng");
        lblSubTitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSubTitle.setForeground(MUTED);

        JPanel titleBox = new JPanel(new GridLayout(2, 1, 0, 3));
        titleBox.setOpaque(false);
        titleBox.add(lblTitle);
        titleBox.add(lblSubTitle);
        header.add(titleBox, BorderLayout.WEST);

        btnQuayLai = createButton("Quay lại", MUTED);
        btnQuayLai.addActionListener(e -> {
            TrangChuAdmin tc = new TrangChuAdmin();
            tc.setVisible(true);
            dispose();
        });
        header.add(btnQuayLai, BorderLayout.EAST);

        JPanel stats = new JPanel(new GridLayout(1, 3, 16, 0));
        stats.setOpaque(false);

        lblTongDon = new JLabel("0");
        lblTongDoanhThu = new JLabel("0 VNĐ");
        lblDonCaoNhat = new JLabel("0 VNĐ");

        stats.add(createStatCard("Tổng hóa đơn", lblTongDon, PRIMARY));
        stats.add(createStatCard("Tổng doanh thu", lblTongDoanhThu, SUCCESS));
        stats.add(createStatCard("Đơn cao nhất", lblDonCaoNhat, WARNING));

        JPanel top = new JPanel(new BorderLayout(0, 18));
        top.setOpaque(false);
        top.add(header, BorderLayout.NORTH);
        top.add(stats, BorderLayout.CENTER);
        root.add(top, BorderLayout.NORTH);

        JPanel content = createCardPanel(new BorderLayout(0, 14));

        JPanel toolbar = new JPanel(new BorderLayout(10, 0));
        toolbar.setOpaque(false);

        txtTimKiem = new JTextField();
        txtTimKiem.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtTimKiem.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240)),
                new EmptyBorder(8, 12, 8, 12)
        ));
        txtTimKiem.setToolTipText("Tìm theo mã hóa đơn, khách hàng, nhân viên hoặc ngày lập");

        btnTimKiem = createButton("Tìm kiếm", PRIMARY);
        btnLamMoi = createButton("Làm mới", WARNING);
        btnChiTiet = createButton("Chi tiết", SUCCESS);
        btnXoa = createButton("Xóa", DANGER);

        btnTimKiem.addActionListener(e -> timKiemHoaDon());
        btnLamMoi.addActionListener(e -> {
            txtTimKiem.setText("");
            loadHoaDon();
        });
        btnChiTiet.addActionListener(e -> xemChiTiet());
        btnXoa.addActionListener(e -> xoaHoaDon());

        JPanel actionBox = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        actionBox.setOpaque(false);
        actionBox.add(btnTimKiem);
        actionBox.add(btnLamMoi);
        actionBox.add(btnChiTiet);
        actionBox.add(btnXoa);

        toolbar.add(txtTimKiem, BorderLayout.CENTER);
        toolbar.add(actionBox, BorderLayout.EAST);
        content.add(toolbar, BorderLayout.NORTH);

        tblHoaDon = new JTable();
        customTable(tblHoaDon);

        JScrollPane scrollPane = new JScrollPane(tblHoaDon);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240)));
        scrollPane.getViewport().setBackground(Color.WHITE);
        content.add(scrollPane, BorderLayout.CENTER);

        root.add(content, BorderLayout.CENTER);
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

    private JPanel createStatCard(String title, JLabel valueLabel, Color accent) {
        JPanel card = createCardPanel(new BorderLayout(0, 8));

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblTitle.setForeground(MUTED);

        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        valueLabel.setForeground(accent);

        card.add(lblTitle, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);
        return card;
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
        java.awt.EventQueue.invokeLater(() -> new QLDonHang().setVisible(true));
    }
}
