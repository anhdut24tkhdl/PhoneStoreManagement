package UI.ADMIN;

import Model.HangDienThoai;
import Service.HangDienThoaiService;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class QLHangDienThoai extends javax.swing.JFrame {

    private JTable tblHangDienThoai;
    private JTextField txtTimKiem;
    private JButton btnTimKiem, btnLamMoi, btnThem, btnXoa, btnSua, btnQuayLai;
    private JLabel lblSoHang;

    private final Color BG = new Color(245, 247, 250);
    private final Color PRIMARY = new Color(37, 99, 235);
    private final Color SUCCESS = new Color(22, 163, 74);
    private final Color DANGER = new Color(220, 38, 38);
    private final Color WARNING = new Color(249, 115, 22);
    private final Color MUTED = new Color(107, 114, 128);
    private final Color TEXT = new Color(30, 41, 59);

    public QLHangDienThoai() {
        initComponents();
        loadHangDienThoai();
    }

    private void loadHangDienThoai() {
        HangDienThoaiService service = new HangDienThoaiService();
        ArrayList<HangDienThoai> list = service.getAllHangDienThoai();
        hienThiTable(list);
    }

    private void hienThiTable(ArrayList<HangDienThoai> list) {
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        model.setColumnIdentifiers(new Object[]{"Mã hãng", "Tên hãng"});

        for (HangDienThoai h : list) {
            model.addRow(new Object[]{h.getMaHang(), h.getTenHang()});
        }

        tblHangDienThoai.setModel(model);
        lblSoHang.setText("Tổng hãng: " + list.size());
    }

    private void timKiem() {
        String keyword = txtTimKiem.getText().trim().toLowerCase();
        HangDienThoaiService service = new HangDienThoaiService();
        ArrayList<HangDienThoai> list = service.getAllHangDienThoai();
        ArrayList<HangDienThoai> ketQua = new ArrayList<>();

        for (HangDienThoai h : list) {
            if (String.valueOf(h.getMaHang()).contains(keyword)
                    || h.getTenHang().toLowerCase().contains(keyword)) {
                ketQua.add(h);
            }
        }

        hienThiTable(ketQua);
    }

    private void initComponents() {
        setTitle("Quản lý hãng điện thoại");
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setSize(760, 520);
        setResizable(false);
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout(0, 18));
        root.setBorder(new EmptyBorder(24, 28, 24, 28));
        root.setBackground(BG);
        setContentPane(root);

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);

        JLabel lblTitle = new JLabel("Quản lý hãng điện thoại");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitle.setForeground(TEXT);

        lblSoHang = new JLabel("Tổng hãng: 0");
        lblSoHang.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblSoHang.setForeground(PRIMARY);

        JPanel titleBox = new JPanel(new GridLayout(2, 1, 0, 3));
        titleBox.setOpaque(false);
        titleBox.add(lblTitle);
        titleBox.add(lblSoHang);

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
        btnSua = createButton("Sửa", PRIMARY);
        btnXoa = createButton("Xóa", DANGER);

        btnTimKiem.addActionListener(e -> timKiem());
        btnLamMoi.addActionListener(e -> {
            txtTimKiem.setText("");
            loadHangDienThoai();
        });

        JPanel toolbar = new JPanel(new BorderLayout(10, 0));
        toolbar.setOpaque(false);
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        actions.setOpaque(false);
        actions.add(btnTimKiem);
        actions.add(btnLamMoi);
        actions.add(btnThem);
        actions.add(btnSua);
        actions.add(btnXoa);

        toolbar.add(txtTimKiem, BorderLayout.CENTER);
        toolbar.add(actions, BorderLayout.EAST);
        card.add(toolbar, BorderLayout.NORTH);

        tblHangDienThoai = new JTable();
        customTable(tblHangDienThoai);
        JScrollPane scroll = new JScrollPane(tblHangDienThoai);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240)));
        scroll.getViewport().setBackground(Color.WHITE);
        card.add(scroll, BorderLayout.CENTER);

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
        java.awt.EventQueue.invokeLater(() -> new QLHangDienThoai().setVisible(true));
    }
}
