package UI.ADMIN;

import Model.ChiTietHoaDon;
import Service.ChiTietHoaDonService;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class ChiTietHoaDonDialog extends javax.swing.JDialog {

    private int maHD;
    private JTable tblChiTietHoaDon;
    private JLabel lblTongTien;

    private final Color BG = new Color(245, 247, 250);
    private final Color PRIMARY = new Color(37, 99, 235);
    private final Color DANGER = new Color(220, 38, 38);
    private final Color TEXT = new Color(30, 41, 59);
    private final Color MUTED = new Color(107, 114, 128);

    public ChiTietHoaDonDialog(java.awt.Frame parent, boolean modal, int maHD) {
        super(parent, modal);
        this.maHD = maHD;
        initComponents();
        loadChiTietHoaDon();
    }

    private void loadChiTietHoaDon() {
        ChiTietHoaDonService service = new ChiTietHoaDonService();
        ArrayList<ChiTietHoaDon> list = service.getByMaHD(maHD);

        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        model.setColumnIdentifiers(new Object[]{
            "Mã HĐ", "Mã SP", "Số lượng", "Đơn giá", "Thành tiền"
        });

        double tongTien = 0;

        for (ChiTietHoaDon ct : list) {
            tongTien += ct.getThanhTien();
            model.addRow(new Object[]{
                ct.getMaHD(),
                ct.getMaSP(),
                ct.getSoLuong(),
                String.format("%,.0f VNĐ", ct.getDonGia()),
                String.format("%,.0f VNĐ", ct.getThanhTien())
            });
        }

        tblChiTietHoaDon.setModel(model);
        lblTongTien.setText("Tổng chi tiết: " + String.format("%,.0f VNĐ", tongTien));
    }

    private void initComponents() {
        setTitle("Chi tiết hóa đơn");
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setSize(720, 480);
        setResizable(false);
        setLocationRelativeTo(getParent());

        JPanel root = new JPanel(new BorderLayout(0, 16));
        root.setBorder(new EmptyBorder(22, 24, 22, 24));
        root.setBackground(BG);
        setContentPane(root);

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);

        JLabel lblTieuDe = new JLabel("Chi tiết hóa đơn #" + maHD);
        lblTieuDe.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTieuDe.setForeground(TEXT);

        JLabel lblSub = new JLabel("Danh sách sản phẩm trong hóa đơn");
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblSub.setForeground(MUTED);

        JPanel titleBox = new JPanel(new GridLayout(2, 1, 0, 3));
        titleBox.setOpaque(false);
        titleBox.add(lblTieuDe);
        titleBox.add(lblSub);

        header.add(titleBox, BorderLayout.WEST);
        root.add(header, BorderLayout.NORTH);

        tblChiTietHoaDon = new JTable();
        customTable(tblChiTietHoaDon);

        JScrollPane scrollPane = new JScrollPane(tblChiTietHoaDon);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240)));
        scrollPane.getViewport().setBackground(Color.WHITE);

        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240)),
                new EmptyBorder(16, 16, 16, 16)
        ));
        card.add(scrollPane, BorderLayout.CENTER);
        root.add(card, BorderLayout.CENTER);

        JPanel footer = new JPanel(new BorderLayout());
        footer.setOpaque(false);

        lblTongTien = new JLabel("Tổng chi tiết: 0 VNĐ");
        lblTongTien.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTongTien.setForeground(DANGER);

        JButton btnDong = createButton("Đóng", PRIMARY);
        btnDong.addActionListener(e -> dispose());

        footer.add(lblTongTien, BorderLayout.WEST);
        footer.add(btnDong, BorderLayout.EAST);
        root.add(footer, BorderLayout.SOUTH);
    }

    private JButton createButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setForeground(Color.WHITE);
        btn.setBackground(bg);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(new EmptyBorder(9, 18, 9, 18));
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
}
