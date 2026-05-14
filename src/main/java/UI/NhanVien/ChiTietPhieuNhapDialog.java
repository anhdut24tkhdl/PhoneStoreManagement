package UI.NhanVien;

import Model.ChiTietPhieuNhap;
import Model.SanPham;
import Service.ChiTietPhieuNhapService;
import Service.SanPhamService;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ChiTietPhieuNhapDialog extends JDialog {

    private int maPN;

    private JTable tblChiTiet;
    private JScrollPane jScrollPane1;
    private JButton btnDong;
    private JLabel lblTitle;

    public ChiTietPhieuNhapDialog(Frame parent, boolean modal, int maPN) {
        super(parent, modal);
        this.maPN = maPN;

        initComponents();
        customUI();
        loadChiTiet();
    }

    private void customUI() {
        setTitle("Chi tiết phiếu nhập");
        getContentPane().setBackground(new Color(245, 247, 250));

        tblChiTiet.setRowHeight(30);
        tblChiTiet.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        tblChiTiet.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tblChiTiet.getTableHeader().setBackground(new Color(37, 99, 235));
        tblChiTiet.getTableHeader().setForeground(Color.WHITE);

        tblChiTiet.setShowGrid(true);
        tblChiTiet.setGridColor(Color.LIGHT_GRAY);

        btnDong.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnDong.setForeground(Color.WHITE);
        btnDong.setBackground(new Color(107, 114, 128));
        btnDong.setFocusPainted(false);
        btnDong.setBorderPainted(false);
        btnDong.setOpaque(true);
        btnDong.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void loadChiTiet() {
        ChiTietPhieuNhapService ctService = new ChiTietPhieuNhapService();
        ArrayList<ChiTietPhieuNhap> list = ctService.getByMaPN(maPN);

        SanPhamService spService = new SanPhamService();
        ArrayList<SanPham> dsSP = spService.getAllSanPham();

        DefaultTableModel model = new DefaultTableModel();

        model.setColumnIdentifiers(new Object[]{
            "Mã PN",
            "Mã SP",
            "Tên SP",
            "Số lượng",
            "Đơn giá nhập",
            "Thành tiền"
        });

        for (ChiTietPhieuNhap ct : list) {
            String tenSP = "";

            for (SanPham sp : dsSP) {
                if (sp.getMaSP() == ct.getMaSP()) {
                    tenSP = sp.getTenSP();
                    break;
                }
            }

            model.addRow(new Object[]{
                ct.getMaPN(),
                ct.getMaSP(),
                tenSP,
                ct.getSoLuong(),
                String.format("%,.0f VNĐ", ct.getDonGiaNhap()),
                String.format("%,.0f VNĐ", ct.getThanhTien())
            });
        }

        tblChiTiet.setModel(model);
    }

    private void initComponents() {
        lblTitle = new JLabel("CHI TIẾT PHIẾU NHẬP #" + maPN);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setForeground(new Color(0, 86, 179));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);

        tblChiTiet = new JTable();

        tblChiTiet.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "Mã PN",
                    "Mã SP",
                    "Tên SP",
                    "Số lượng",
                    "Đơn giá nhập",
                    "Thành tiền"
                }
        ));

        jScrollPane1 = new JScrollPane(tblChiTiet);

        btnDong = new JButton("Đóng");
        btnDong.addActionListener(e -> dispose());

        JPanel panelMain = new JPanel(new BorderLayout(12, 12));
        panelMain.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelMain.setBackground(new Color(245, 247, 250));

        JPanel panelBottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBottom.setBackground(new Color(245, 247, 250));
        panelBottom.add(btnDong);

        panelMain.add(lblTitle, BorderLayout.NORTH);
        panelMain.add(jScrollPane1, BorderLayout.CENTER);
        panelMain.add(panelBottom, BorderLayout.SOUTH);

        setContentPane(panelMain);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(780, 460);
        setLocationRelativeTo(null);
    }
}