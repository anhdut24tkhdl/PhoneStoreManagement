package UI.NhanVien;

import Model.PhieuNhap;
import Service.ChiTietPhieuNhapService;
import Service.PhieuNhapService;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class QLPhieuNhap extends JFrame {

    private JTable tblPhieuNhap;
    private JScrollPane jScrollPane1;

    private JButton btnThem;
    private JButton btnSua;
    private JButton btnXoa;
    private JButton btnChiTiet;
    private JButton btnLamMoi;
    private JButton btnQuayLai;

    private JLabel lblTitle;

    public QLPhieuNhap() {
        initComponents();
        customUI();
        loadPhieuNhap();
    }

    private void customUI() {
        setTitle("Quản lý phiếu nhập - Nhân viên");
        getContentPane().setBackground(new Color(230, 240, 255));

        tblPhieuNhap.setRowHeight(32);
        tblPhieuNhap.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        tblPhieuNhap.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tblPhieuNhap.getTableHeader().setBackground(new Color(37, 99, 235));
        tblPhieuNhap.getTableHeader().setForeground(Color.WHITE);

        tblPhieuNhap.setShowGrid(true);
        tblPhieuNhap.setGridColor(Color.LIGHT_GRAY);
        tblPhieuNhap.setSelectionBackground(new Color(219, 234, 254));
        tblPhieuNhap.setSelectionForeground(Color.BLACK);

        Font btnFont = new Font("Segoe UI", Font.BOLD, 13);

        JButton[] buttons = {
            btnThem,
            btnSua,
            btnXoa,
            btnChiTiet,
            btnLamMoi,
            btnQuayLai
        };

        for (JButton btn : buttons) {
            btn.setFont(btnFont);
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);
            btn.setBorderPainted(false);
            btn.setOpaque(true);
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        btnThem.setBackground(new Color(22, 163, 74));
        btnSua.setBackground(new Color(249, 115, 22));
        btnXoa.setBackground(new Color(220, 38, 38));
        btnChiTiet.setBackground(new Color(37, 99, 235));
        btnLamMoi.setBackground(new Color(14, 165, 233));
        btnQuayLai.setBackground(new Color(107, 114, 128));
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
                String.format("%,.0f VNĐ", pn.getTongTien())
            });
        }

        tblPhieuNhap.setModel(model);

        tblPhieuNhap.getColumnModel().getColumn(0).setPreferredWidth(70);
        tblPhieuNhap.getColumnModel().getColumn(1).setPreferredWidth(70);
        tblPhieuNhap.getColumnModel().getColumn(2).setPreferredWidth(180);
        tblPhieuNhap.getColumnModel().getColumn(3).setPreferredWidth(140);
    }

    private int getMaPNDangChon() {
        int row = tblPhieuNhap.getSelectedRow();

        if (row == -1) {
            return -1;
        }

        return Integer.parseInt(tblPhieuNhap.getValueAt(row, 0).toString());
    }

    private void themPhieuNhap() {
        ThemPhieuNhap dialog = new ThemPhieuNhap(this, true);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);

        loadPhieuNhap();
    }

    private void suaPhieuNhap() {
        int maPN = getMaPNDangChon();

        if (maPN == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn phiếu nhập cần sửa!");
            return;
        }

        SuaPhieuNhap dialog = new SuaPhieuNhap(this, true, maPN);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);

        loadPhieuNhap();
    }

    private void xoaPhieuNhap() {
        int maPN = getMaPNDangChon();

        if (maPN == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn phiếu nhập cần xóa!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc muốn xóa phiếu nhập này không?",
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        ChiTietPhieuNhapService ctService = new ChiTietPhieuNhapService();
        PhieuNhapService pnService = new PhieuNhapService();

        ctService.xoaChiTietTheoPhieuNhap(maPN);

        if (pnService.xoaPhieuNhap(maPN)) {
            JOptionPane.showMessageDialog(this, "Xóa phiếu nhập thành công!");
            loadPhieuNhap();
        } else {
            JOptionPane.showMessageDialog(this, "Xóa phiếu nhập thất bại!");
        }
    }

    private void xemChiTiet() {
        int maPN = getMaPNDangChon();

        if (maPN == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn phiếu nhập cần xem chi tiết!");
            return;
        }

        ChiTietPhieuNhapDialog dialog = new ChiTietPhieuNhapDialog(this, true, maPN);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void quayLai() {
        TrangChuNhanVien tc = new TrangChuNhanVien();
        tc.setVisible(true);
        dispose();
    }

    private void initComponents() {
        lblTitle = new JLabel("QUẢN LÝ PHIẾU NHẬP");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitle.setForeground(new Color(0, 86, 179));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);

        tblPhieuNhap = new JTable();
        jScrollPane1 = new JScrollPane(tblPhieuNhap);

        btnThem = new JButton("Thêm");
        btnSua = new JButton("Sửa");
        btnXoa = new JButton("Xóa");
        btnChiTiet = new JButton("Chi Tiết");
        btnLamMoi = new JButton("Làm mới");
        btnQuayLai = new JButton("Quay Lại");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        tblPhieuNhap.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "Mã PN",
                    "Mã NV",
                    "Ngày nhập",
                    "Tổng tiền"
                }
        ));

        jScrollPane1.setViewportView(tblPhieuNhap);

        btnThem.addActionListener(e -> themPhieuNhap());
        btnSua.addActionListener(e -> suaPhieuNhap());
        btnXoa.addActionListener(e -> xoaPhieuNhap());
        btnChiTiet.addActionListener(e -> xemChiTiet());
        btnLamMoi.addActionListener(e -> loadPhieuNhap());
        btnQuayLai.addActionListener(e -> quayLai());

        JPanel root = new JPanel(new GridBagLayout());
        root.setBackground(new Color(230, 240, 255));

        JPanel card = new JPanel(new BorderLayout(15, 15));
        card.setPreferredSize(new Dimension(820, 520));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));

        JPanel panelButton = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        panelButton.setBackground(Color.WHITE);

        panelButton.add(btnThem);
        panelButton.add(btnSua);
        panelButton.add(btnXoa);
        panelButton.add(btnChiTiet);
        panelButton.add(btnLamMoi);
        panelButton.add(btnQuayLai);

        card.add(lblTitle, BorderLayout.NORTH);
        card.add(jScrollPane1, BorderLayout.CENTER);
        card.add(panelButton, BorderLayout.SOUTH);

        root.add(card);
        setContentPane(root);

        setSize(960, 650);
        setResizable(false);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new QLPhieuNhap().setVisible(true));
    }
}