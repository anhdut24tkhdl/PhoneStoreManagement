package UI.ADMIN;

import Model.PhieuNhap;
import Service.PhieuNhapService;
import Utils.Session;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class QLPhieuNhap extends JFrame {

    private JTable tblPhieuNhap;
    private JScrollPane scrollPane;

    private JButton btnChiTiet;
    private JButton btnLamMoi;
    private JButton btnQuayLai;

    private JLabel lblTitle;
    private JLabel lblSubTitle;

    private boolean choPhepSua;

    public QLPhieuNhap() {
        this(false);
    }

    public QLPhieuNhap(boolean choPhepSua) {
        this.choPhepSua = choPhepSua;
        initComponents();
        loadPhieuNhap();
    }

    private void initComponents() {
        setTitle("Quản lý phiếu nhập");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 620);
        setResizable(false);

        JPanel root = new JPanel(new GridBagLayout());
        root.setBackground(new Color(230, 240, 255));

        JPanel card = new JPanel();
        card.setPreferredSize(new Dimension(800, 520));
        card.setBackground(Color.WHITE);
        card.setBorder(new EmptyBorder(28, 35, 28, 35));
        card.setLayout(new BorderLayout(0, 22));

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));

        lblTitle = new JLabel("QUẢN LÝ PHIẾU NHẬP");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblTitle.setForeground(new Color(0, 86, 179));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        lblSubTitle = new JLabel("Danh sách phiếu nhập hàng và tổng tiền nhập kho");
        lblSubTitle.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblSubTitle.setForeground(new Color(100, 116, 139));
        lblSubTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        headerPanel.add(lblTitle);
        headerPanel.add(Box.createVerticalStrut(8));
        headerPanel.add(lblSubTitle);

        tblPhieuNhap = new JTable();
        tblPhieuNhap.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Mã PN", "Mã NV", "Ngày nhập", "Tổng tiền"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });

        customTable();

        scrollPane = new JScrollPane(tblPhieuNhap);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(203, 213, 225), 1));
        scrollPane.getViewport().setBackground(Color.WHITE);

        btnChiTiet = createButton("Chi tiết", new Color(37, 99, 235), 125, 42);
        btnLamMoi = createButton("Làm mới", new Color(14, 165, 233), 125, 42);
        btnQuayLai = createButton("Quay lại", new Color(100, 116, 139), 125, 42);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 18, 0));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(btnChiTiet);
        buttonPanel.add(btnLamMoi);
        buttonPanel.add(btnQuayLai);

        JPanel centerPanel = new JPanel(new BorderLayout(0, 18));
        centerPanel.setBackground(Color.WHITE);
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        centerPanel.add(buttonPanel, BorderLayout.SOUTH);

        card.add(headerPanel, BorderLayout.NORTH);
        card.add(centerPanel, BorderLayout.CENTER);

        root.add(card);
        setContentPane(root);

        btnChiTiet.addActionListener(e -> xemChiTiet());
        btnLamMoi.addActionListener(e -> loadPhieuNhap());
        btnQuayLai.addActionListener(e -> quayLai());

        setLocationRelativeTo(null);
    }

    private void customTable() {
        tblPhieuNhap.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tblPhieuNhap.setRowHeight(34);
        tblPhieuNhap.setSelectionBackground(new Color(219, 234, 254));
        tblPhieuNhap.setSelectionForeground(new Color(15, 23, 42));
        tblPhieuNhap.setGridColor(new Color(226, 232, 240));
        tblPhieuNhap.setShowGrid(true);

        tblPhieuNhap.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tblPhieuNhap.getTableHeader().setBackground(new Color(37, 99, 235));
        tblPhieuNhap.getTableHeader().setForeground(Color.WHITE);
        tblPhieuNhap.getTableHeader().setPreferredSize(new Dimension(0, 38));
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

    private void loadPhieuNhap() {
        PhieuNhapService service = new PhieuNhapService();
        ArrayList<PhieuNhap> list = service.getAllPhieuNhap();

        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[]{
            "Mã PN", "Mã NV", "Ngày nhập", "Tổng tiền"
        });

        for (PhieuNhap pn : list) {
            model.addRow(new Object[]{
                pn.getMaPN(),
                pn.getMaNV(),
                pn.getNgayNhap(),
                formatTongTien(pn.getTongTien())
            });
        }

        tblPhieuNhap.setModel(model);
        canChinhCot();
    }

    private void canChinhCot() {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);

        tblPhieuNhap.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tblPhieuNhap.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        tblPhieuNhap.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        tblPhieuNhap.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);

        tblPhieuNhap.getColumnModel().getColumn(0).setPreferredWidth(90);
        tblPhieuNhap.getColumnModel().getColumn(1).setPreferredWidth(90);
        tblPhieuNhap.getColumnModel().getColumn(2).setPreferredWidth(180);
        tblPhieuNhap.getColumnModel().getColumn(3).setPreferredWidth(180);
    }

    private String formatTongTien(double tongTien) {
        DecimalFormat df = new DecimalFormat("#,###");
        return df.format(tongTien) + " VNĐ";
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

    private void quayLai() {
        if (Session.currentUser != null
                && "NhanVien".equalsIgnoreCase(Session.currentUser.getVaiTro())) {

            UI.NhanVien.TrangChuNhanVien tc = new UI.NhanVien.TrangChuNhanVien();
            tc.setVisible(true);

        } else {
            TrangChuAdmin tc = new TrangChuAdmin();
            tc.setVisible(true);
        }

        dispose();
    }

    public static void main(String args[]) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        EventQueue.invokeLater(() -> {
            new QLPhieuNhap(true).setVisible(true);
        });
    }
}