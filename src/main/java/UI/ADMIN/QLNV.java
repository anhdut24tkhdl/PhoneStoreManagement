package UI.ADMIN;

import Model.NhanVien;
import Service.NhanVienService;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class QLNV extends JFrame {

    private JTable tblNhanVien;
    private JScrollPane jScrollPane1;
    private JTextField txtTimKiem;

    private JButton btnTimKiem;
    private JButton btnLamMoi;
    private JButton btnThem;
    private JButton btnSua;
    private JButton btnXoa;
    private JButton btnQuayLai;

    private JLabel lblTitle;
    private JLabel lblSubTitle;

    public QLNV() {
        initComponents();
        loadNhanVien();
    }

    private void initComponents() {
        setTitle("Quản lý nhân viên");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(980, 650);
        setResizable(false);

        JPanel root = new JPanel(new GridBagLayout());
        root.setBackground(new Color(230, 240, 255));

        JPanel card = new JPanel();
        card.setPreferredSize(new Dimension(900, 570));
        card.setBackground(Color.WHITE);
        card.setBorder(new EmptyBorder(25, 35, 25, 35));
        card.setLayout(new BorderLayout(0, 22));

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));

        lblTitle = new JLabel("QUẢN LÝ NHÂN VIÊN");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblTitle.setForeground(new Color(0, 86, 179));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        lblSubTitle = new JLabel("Danh sách và thông tin nhân viên trong hệ thống");
        lblSubTitle.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblSubTitle.setForeground(new Color(100, 116, 139));
        lblSubTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        headerPanel.add(lblTitle);
        headerPanel.add(Box.createVerticalStrut(8));
        headerPanel.add(lblSubTitle);

        txtTimKiem = new JTextField();
        txtTimKiem.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtTimKiem.setPreferredSize(new Dimension(400, 40));
        txtTimKiem.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 210, 240), 1),
                new EmptyBorder(5, 12, 5, 12)
        ));

        btnTimKiem = createButton("Tìm kiếm", new Color(37, 99, 235), 120, 40);
        btnLamMoi = createButton("Làm mới", new Color(14, 165, 233), 120, 40);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        searchPanel.setBackground(Color.WHITE);
        searchPanel.add(txtTimKiem);
        searchPanel.add(btnTimKiem);
        searchPanel.add(btnLamMoi);

        tblNhanVien = new JTable();
        tblNhanVien.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Mã NV", "Họ tên", "Giới tính", "SĐT", "Địa chỉ", "Chức vụ"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });

        tblNhanVien.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tblNhanVien.setRowHeight(34);
        tblNhanVien.setSelectionBackground(new Color(219, 234, 254));
        tblNhanVien.setSelectionForeground(new Color(15, 23, 42));
        tblNhanVien.setGridColor(new Color(226, 232, 240));
        tblNhanVien.setShowGrid(true);

        tblNhanVien.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tblNhanVien.getTableHeader().setBackground(new Color(37, 99, 235));
        tblNhanVien.getTableHeader().setForeground(Color.WHITE);
        tblNhanVien.getTableHeader().setPreferredSize(new Dimension(0, 38));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        tblNhanVien.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tblNhanVien.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        tblNhanVien.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        tblNhanVien.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);

        tblNhanVien.getColumnModel().getColumn(0).setPreferredWidth(70);
        tblNhanVien.getColumnModel().getColumn(1).setPreferredWidth(180);
        tblNhanVien.getColumnModel().getColumn(2).setPreferredWidth(90);
        tblNhanVien.getColumnModel().getColumn(3).setPreferredWidth(120);
        tblNhanVien.getColumnModel().getColumn(4).setPreferredWidth(250);
        tblNhanVien.getColumnModel().getColumn(5).setPreferredWidth(120);

        jScrollPane1 = new JScrollPane(tblNhanVien);
        jScrollPane1.setBorder(BorderFactory.createLineBorder(new Color(203, 213, 225), 1));
        jScrollPane1.getViewport().setBackground(Color.WHITE);

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);
        tablePanel.add(searchPanel, BorderLayout.NORTH);
        tablePanel.add(Box.createVerticalStrut(15), BorderLayout.CENTER);

        JPanel tableWrapper = new JPanel(new BorderLayout());
        tableWrapper.setBackground(Color.WHITE);
        tableWrapper.add(searchPanel, BorderLayout.NORTH);
        tableWrapper.add(Box.createVerticalStrut(15), BorderLayout.CENTER);
        tableWrapper.add(jScrollPane1, BorderLayout.SOUTH);

        btnThem = createButton("Thêm", new Color(22, 163, 74), 125, 42);
        btnSua = createButton("Sửa", new Color(249, 115, 22), 125, 42);
        btnXoa = createButton("Xóa", new Color(220, 38, 38), 125, 42);
        btnQuayLai = createButton("Quay lại", new Color(100, 116, 139), 125, 42);

        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 18, 0));
        actionPanel.setBackground(Color.WHITE);
        actionPanel.add(btnThem);
        actionPanel.add(btnSua);
        actionPanel.add(btnXoa);
        actionPanel.add(btnQuayLai);

        JPanel centerPanel = new JPanel(new BorderLayout(0, 16));
        centerPanel.setBackground(Color.WHITE);
        centerPanel.add(searchPanel, BorderLayout.NORTH);
        centerPanel.add(jScrollPane1, BorderLayout.CENTER);
        centerPanel.add(actionPanel, BorderLayout.SOUTH);

        card.add(headerPanel, BorderLayout.NORTH);
        card.add(centerPanel, BorderLayout.CENTER);

        root.add(card);
        setContentPane(root);

        btnTimKiem.addActionListener(e -> timKiemNhanVien());

        txtTimKiem.addActionListener(e -> timKiemNhanVien());

        btnLamMoi.addActionListener(e -> {
            txtTimKiem.setText("");
            loadNhanVien();
        });

        btnThem.addActionListener(e -> {
            ThemNhanVien1 dialog = new ThemNhanVien1(this, true);
            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);
            loadNhanVien();
        });

        btnSua.addActionListener(e -> suaNhanVien());

        btnXoa.addActionListener(e -> xoaNhanVien());

        btnQuayLai.addActionListener(e -> {
            TrangChuAdmin tc = new TrangChuAdmin();
            tc.setVisible(true);
            dispose();
        });

        setLocationRelativeTo(null);
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

    private void loadNhanVien() {
        NhanVienService service = new NhanVienService();
        ArrayList<NhanVien> list = service.getAllNhanVien();
        hienThiTable(list);
    }

    private void hienThiTable(ArrayList<NhanVien> list) {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[]{
            "Mã NV", "Họ tên", "Giới tính", "SĐT", "Địa chỉ", "Chức vụ"
        });

        for (NhanVien nv : list) {
            model.addRow(new Object[]{
                nv.getMaNV(),
                nv.getHoTen(),
                nv.getGioiTinh(),
                nv.getSdt(),
                nv.getDiaChi(),
                nv.getChucVu()
            });
        }

        tblNhanVien.setModel(model);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        tblNhanVien.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tblNhanVien.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        tblNhanVien.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        tblNhanVien.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);

        tblNhanVien.getColumnModel().getColumn(0).setPreferredWidth(70);
        tblNhanVien.getColumnModel().getColumn(1).setPreferredWidth(180);
        tblNhanVien.getColumnModel().getColumn(2).setPreferredWidth(90);
        tblNhanVien.getColumnModel().getColumn(3).setPreferredWidth(120);
        tblNhanVien.getColumnModel().getColumn(4).setPreferredWidth(250);
        tblNhanVien.getColumnModel().getColumn(5).setPreferredWidth(120);
    }

    private void timKiemNhanVien() {
        String keyword = txtTimKiem.getText().trim().toLowerCase();

        NhanVienService service = new NhanVienService();
        ArrayList<NhanVien> list = service.getAllNhanVien();
        ArrayList<NhanVien> ketQua = new ArrayList<>();

        if (keyword.isEmpty()) {
            hienThiTable(list);
            return;
        }

        for (NhanVien nv : list) {
            if (String.valueOf(nv.getMaNV()).contains(keyword)
                    || nv.getHoTen().toLowerCase().contains(keyword)
                    || nv.getSdt().toLowerCase().contains(keyword)
                    || nv.getDiaChi().toLowerCase().contains(keyword)
                    || nv.getChucVu().toLowerCase().contains(keyword)) {
                ketQua.add(nv);
            }
        }

        hienThiTable(ketQua);
    }

    private void suaNhanVien() {
        int row = tblNhanVien.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên cần sửa!");
            return;
        }

        int maNV = Integer.parseInt(tblNhanVien.getValueAt(row, 0).toString());
        String hoTen = tblNhanVien.getValueAt(row, 1).toString();
        String gioiTinh = tblNhanVien.getValueAt(row, 2).toString();
        String sdt = tblNhanVien.getValueAt(row, 3).toString();
        String diaChi = tblNhanVien.getValueAt(row, 4).toString();
        String chucVu = tblNhanVien.getValueAt(row, 5).toString();

        NhanVien nv = new NhanVien(maNV, hoTen, gioiTinh, sdt, diaChi, chucVu);

        SuaNhanVien dialog = new SuaNhanVien(this, true, nv);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);

        loadNhanVien();
    }

    private void xoaNhanVien() {
        int row = tblNhanVien.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên cần xóa!");
            return;
        }

        int maNV = Integer.parseInt(tblNhanVien.getValueAt(row, 0).toString());

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc muốn xóa nhân viên này?",
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            NhanVienService service = new NhanVienService();

            if (service.xoaNhanVien(maNV)) {
                JOptionPane.showMessageDialog(this, "Xóa nhân viên thành công!");
                loadNhanVien();
            } else {
                JOptionPane.showMessageDialog(this, "Xóa nhân viên thất bại!");
            }
        }
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
            new QLNV().setVisible(true);
        });
    }
}