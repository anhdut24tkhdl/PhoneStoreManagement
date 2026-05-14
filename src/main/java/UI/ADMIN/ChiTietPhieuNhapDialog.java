package UI.ADMIN;

import Model.PhieuNhap;
import Model.ChiTietPhieuNhap;
import Service.PhieuNhapService;
import Service.ChiTietPhieuNhapService;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class ChiTietPhieuNhapDialog extends JDialog {

    private int maPN;

    private JLabel lblTitle;
    private JLabel lblMaPN;
    private JLabel lblNhanVien;
    private JLabel lblNgayNhap;
    private JLabel lblTongTien;

    private JTable tblChiTiet;
    private JScrollPane scrollPane;

    private JButton btnDong;

    public ChiTietPhieuNhapDialog(java.awt.Frame parent, boolean modal, int maPN) {
        super(parent, modal);
        this.maPN = maPN;

        initComponents();
        loadThongTinPhieuNhap();
        loadChiTietPhieuNhap();

        setLocationRelativeTo(parent);
    }

    private void initComponents() {
        setTitle("Chi tiết phiếu nhập");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(760, 560);
        setResizable(false);

        JPanel root = new JPanel(new GridBagLayout());
        root.setBackground(new Color(230, 240, 255));

        JPanel card = new JPanel();
        card.setPreferredSize(new Dimension(680, 480));
        card.setBackground(Color.WHITE);
        card.setBorder(new EmptyBorder(25, 35, 25, 35));
        card.setLayout(new BorderLayout(0, 20));

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));

        lblTitle = new JLabel("CHI TIẾT PHIẾU NHẬP");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitle.setForeground(new Color(0, 86, 179));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        headerPanel.add(lblTitle);

        JPanel infoPanel = new JPanel(new GridLayout(2, 2, 15, 10));
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBorder(new EmptyBorder(5, 10, 5, 10));

        lblMaPN = createInfoLabel("Mã phiếu nhập: ");
        lblNhanVien = createInfoLabel("Nhân viên: ");
        lblNgayNhap = createInfoLabel("Ngày nhập: ");
        lblTongTien = createInfoLabel("Tổng tiền: ");

        infoPanel.add(lblMaPN);
        infoPanel.add(lblNhanVien);
        infoPanel.add(lblNgayNhap);
        infoPanel.add(lblTongTien);

        tblChiTiet = new JTable();
        tblChiTiet.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Mã SP", "Số lượng", "Đơn giá", "Thành tiền"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });

        customTable();

        scrollPane = new JScrollPane(tblChiTiet);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(203, 213, 225), 1));
        scrollPane.getViewport().setBackground(Color.WHITE);

        btnDong = createButton("Đóng", new Color(100, 116, 139), 120, 40);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(btnDong);

        JPanel centerPanel = new JPanel(new BorderLayout(0, 15));
        centerPanel.setBackground(Color.WHITE);
        centerPanel.add(infoPanel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        card.add(headerPanel, BorderLayout.NORTH);
        card.add(centerPanel, BorderLayout.CENTER);
        card.add(buttonPanel, BorderLayout.SOUTH);

        root.add(card);
        setContentPane(root);

        btnDong.addActionListener(e -> dispose());
    }

    private JLabel createInfoLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setForeground(new Color(30, 41, 59));
        return label;
    }

    private JButton createButton(String text, Color bgColor, int width, int height) {
        JButton button = new JButton(text);

        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(width, height));
        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.setVerticalAlignment(SwingConstants.CENTER);
        button.setMargin(new Insets(0, 0, 0, 0));

        return button;
    }

    private void customTable() {
        tblChiTiet.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tblChiTiet.setRowHeight(34);
        tblChiTiet.setSelectionBackground(new Color(219, 234, 254));
        tblChiTiet.setSelectionForeground(new Color(15, 23, 42));
        tblChiTiet.setGridColor(new Color(226, 232, 240));
        tblChiTiet.setShowGrid(true);

        tblChiTiet.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tblChiTiet.getTableHeader().setBackground(new Color(37, 99, 235));
        tblChiTiet.getTableHeader().setForeground(Color.WHITE);
        tblChiTiet.getTableHeader().setPreferredSize(new Dimension(0, 38));
    }

    private void loadThongTinPhieuNhap() {
        PhieuNhapService service = new PhieuNhapService();
        ArrayList<PhieuNhap> list = service.getAllPhieuNhap();

        for (PhieuNhap pn : list) {
            if (pn.getMaPN() == maPN) {
                lblMaPN.setText("Mã phiếu nhập: " + pn.getMaPN());
                lblNhanVien.setText("Nhân viên: " + pn.getMaNV());
                lblNgayNhap.setText("Ngày nhập: " + pn.getNgayNhap());
                lblTongTien.setText("Tổng tiền: " + formatTien(pn.getTongTien()));
                return;
            }
        }

        lblMaPN.setText("Mã phiếu nhập: " + maPN);
        lblNhanVien.setText("Nhân viên: Không tìm thấy");
        lblNgayNhap.setText("Ngày nhập: Không tìm thấy");
        lblTongTien.setText("Tổng tiền: 0 VNĐ");
    }

    private void loadChiTietPhieuNhap() {
        ChiTietPhieuNhapService service = new ChiTietPhieuNhapService();
        ArrayList<ChiTietPhieuNhap> list = service.getByMaPN(maPN);

        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[]{
            "Mã SP", "Số lượng", "Đơn giá", "Thành tiền"
        });

        for (ChiTietPhieuNhap ct : list) {
            model.addRow(new Object[]{
                ct.getMaSP(),
                ct.getSoLuong(),
                formatTien(ct.getDonGiaNhap()),
                formatTien(ct.getThanhTien())
            });
        }

        tblChiTiet.setModel(model);
        canChinhCot();
    }

    private void canChinhCot() {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);

        tblChiTiet.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tblChiTiet.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        tblChiTiet.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
        tblChiTiet.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);

        tblChiTiet.getColumnModel().getColumn(0).setPreferredWidth(100);
        tblChiTiet.getColumnModel().getColumn(1).setPreferredWidth(100);
        tblChiTiet.getColumnModel().getColumn(2).setPreferredWidth(180);
        tblChiTiet.getColumnModel().getColumn(3).setPreferredWidth(180);
    }

    private String formatTien(double tien) {
        DecimalFormat df = new DecimalFormat("#,###");
        return df.format(tien) + " VNĐ";
    }
}