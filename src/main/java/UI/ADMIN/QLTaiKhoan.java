package UI.ADMIN;

import Model.TaiKhoan;
import Service.TaiKhoanService;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class QLTaiKhoan extends JFrame {

    private JTable tblTaiKhoan;
    private JScrollPane scrollPane;
    private JTextField txtTimKiem;

    private JButton btnTimKiem;
    private JButton btnLamMoi;
    private JButton btnThem;
    private JButton btnSua;
    private JButton btnXoa;
    private JButton btnQuayLai;

    private JLabel lblTitle;
    private JLabel lblSubTitle;

    private TaiKhoanService service = new TaiKhoanService();

    public QLTaiKhoan() {
        initComponents();
        loadTaiKhoan();
    }

    private void initComponents() {
        setTitle("Quản lý tài khoản");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 620);
        setResizable(false);

        JPanel root = new JPanel(new GridBagLayout());
        root.setBackground(new Color(230, 240, 255));

        JPanel card = new JPanel();
        card.setPreferredSize(new Dimension(820, 540));
        card.setBackground(Color.WHITE);
        card.setBorder(new EmptyBorder(25, 35, 25, 35));
        card.setLayout(new BorderLayout(0, 22));

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));

        lblTitle = new JLabel("QUẢN LÝ TÀI KHOẢN");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblTitle.setForeground(new Color(0, 86, 179));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        lblSubTitle = new JLabel("Quản lý tài khoản đăng nhập và phân quyền người dùng");
        lblSubTitle.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblSubTitle.setForeground(new Color(100, 116, 139));
        lblSubTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        headerPanel.add(lblTitle);
        headerPanel.add(Box.createVerticalStrut(8));
        headerPanel.add(lblSubTitle);

        txtTimKiem = new JTextField();
        txtTimKiem.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtTimKiem.setPreferredSize(new Dimension(360, 40));
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

        tblTaiKhoan = new JTable();
        tblTaiKhoan.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Mã TK", "Tên đăng nhập", "Mật khẩu", "Vai trò"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });

        tblTaiKhoan.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tblTaiKhoan.setRowHeight(34);
        tblTaiKhoan.setSelectionBackground(new Color(219, 234, 254));
        tblTaiKhoan.setSelectionForeground(new Color(15, 23, 42));
        tblTaiKhoan.setGridColor(new Color(226, 232, 240));
        tblTaiKhoan.setShowGrid(true);

        tblTaiKhoan.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tblTaiKhoan.getTableHeader().setBackground(new Color(37, 99, 235));
        tblTaiKhoan.getTableHeader().setForeground(Color.WHITE);
        tblTaiKhoan.getTableHeader().setPreferredSize(new Dimension(0, 38));

        scrollPane = new JScrollPane(tblTaiKhoan);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(203, 213, 225), 1));
        scrollPane.getViewport().setBackground(Color.WHITE);

        btnThem = createButton("Thêm", new Color(22, 163, 74), 120, 42);
        btnSua = createButton("Sửa", new Color(249, 115, 22), 120, 42);
        btnXoa = createButton("Xóa", new Color(220, 38, 38), 120, 42);
        btnQuayLai = createButton("Quay lại", new Color(100, 116, 139), 120, 42);

        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 18, 0));
        actionPanel.setBackground(Color.WHITE);
        actionPanel.add(btnThem);
        actionPanel.add(btnSua);
        actionPanel.add(btnXoa);
        actionPanel.add(btnQuayLai);

        JPanel centerPanel = new JPanel(new BorderLayout(0, 16));
        centerPanel.setBackground(Color.WHITE);
        centerPanel.add(searchPanel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        centerPanel.add(actionPanel, BorderLayout.SOUTH);

        card.add(headerPanel, BorderLayout.NORTH);
        card.add(centerPanel, BorderLayout.CENTER);

        root.add(card);
        setContentPane(root);

        btnTimKiem.addActionListener(e -> timKiemTaiKhoan());
        txtTimKiem.addActionListener(e -> timKiemTaiKhoan());

        btnLamMoi.addActionListener(e -> {
            txtTimKiem.setText("");
            loadTaiKhoan();
        });

        btnThem.addActionListener(e -> themTaiKhoan());
        btnSua.addActionListener(e -> suaTaiKhoan());
        btnXoa.addActionListener(e -> xoaTaiKhoan());

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

    private void loadTaiKhoan() {
        ArrayList<TaiKhoan> list = service.getAllTaiKhoan();
        hienThiTable(list);
    }

    private void hienThiTable(ArrayList<TaiKhoan> list) {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[]{
            "Mã TK", "Tên đăng nhập", "Mật khẩu", "Vai trò"
        });

        for (TaiKhoan tk : list) {
            model.addRow(new Object[]{
                tk.getMaTK(),
                tk.getTenDangNhap(),
                tk.getMatKhau(),
                tk.getVaiTro()
            });
        }

        tblTaiKhoan.setModel(model);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        tblTaiKhoan.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tblTaiKhoan.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);

        tblTaiKhoan.getColumnModel().getColumn(0).setPreferredWidth(80);
        tblTaiKhoan.getColumnModel().getColumn(1).setPreferredWidth(220);
        tblTaiKhoan.getColumnModel().getColumn(2).setPreferredWidth(220);
        tblTaiKhoan.getColumnModel().getColumn(3).setPreferredWidth(120);
    }

    private void timKiemTaiKhoan() {
        String keyword = txtTimKiem.getText().trim().toLowerCase();

        ArrayList<TaiKhoan> list = service.getAllTaiKhoan();
        ArrayList<TaiKhoan> ketQua = new ArrayList<>();

        if (keyword.isEmpty()) {
            hienThiTable(list);
            return;
        }

        for (TaiKhoan tk : list) {
            if (String.valueOf(tk.getMaTK()).contains(keyword)
                    || tk.getTenDangNhap().toLowerCase().contains(keyword)
                    || tk.getVaiTro().toLowerCase().contains(keyword)) {
                ketQua.add(tk);
            }
        }

        hienThiTable(ketQua);
    }

    private void themTaiKhoan() {
        JTextField txtTenDangNhap = new JTextField();
        JTextField txtMatKhau = new JTextField();

        JComboBox<String> cboVaiTro = new JComboBox<>(new String[]{
            "Admin", "NhanVien"
        });

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.add(new JLabel("Tên đăng nhập:"));
        panel.add(txtTenDangNhap);
        panel.add(new JLabel("Mật khẩu:"));
        panel.add(txtMatKhau);
        panel.add(new JLabel("Vai trò:"));
        panel.add(cboVaiTro);

        int result = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Thêm tài khoản",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            String tenDangNhap = txtTenDangNhap.getText().trim();
            String matKhau = txtMatKhau.getText().trim();
            String vaiTro = cboVaiTro.getSelectedItem().toString();

            if (tenDangNhap.isEmpty() || matKhau.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
                return;
            }

            TaiKhoan tk = new TaiKhoan();
            tk.setTenDangNhap(tenDangNhap);
            tk.setMatKhau(matKhau);
            tk.setVaiTro(vaiTro);

            if (service.themTaiKhoan(tk)) {
                JOptionPane.showMessageDialog(this, "Thêm tài khoản thành công!");
                loadTaiKhoan();
            } else {
                JOptionPane.showMessageDialog(this, "Thêm tài khoản thất bại!");
            }
        }
    }

    private void suaTaiKhoan() {
        int row = tblTaiKhoan.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn tài khoản cần sửa!");
            return;
        }

        int maTK = Integer.parseInt(tblTaiKhoan.getValueAt(row, 0).toString());
        String tenDangNhapCu = tblTaiKhoan.getValueAt(row, 1).toString();
        String matKhauCu = tblTaiKhoan.getValueAt(row, 2).toString();
        String vaiTroCu = tblTaiKhoan.getValueAt(row, 3).toString();

        JTextField txtTenDangNhap = new JTextField(tenDangNhapCu);
        JTextField txtMatKhau = new JTextField(matKhauCu);

        JComboBox<String> cboVaiTro = new JComboBox<>(new String[]{
            "Admin", "NhanVien"
        });
        cboVaiTro.setSelectedItem(vaiTroCu);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.add(new JLabel("Tên đăng nhập:"));
        panel.add(txtTenDangNhap);
        panel.add(new JLabel("Mật khẩu:"));
        panel.add(txtMatKhau);
        panel.add(new JLabel("Vai trò:"));
        panel.add(cboVaiTro);

        int result = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Sửa tài khoản",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            String tenDangNhap = txtTenDangNhap.getText().trim();
            String matKhau = txtMatKhau.getText().trim();
            String vaiTro = cboVaiTro.getSelectedItem().toString();

            if (tenDangNhap.isEmpty() || matKhau.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
                return;
            }

            TaiKhoan tk = new TaiKhoan();
            tk.setMaTK(maTK);
            tk.setTenDangNhap(tenDangNhap);
            tk.setMatKhau(matKhau);
            tk.setVaiTro(vaiTro);

            if (service.suaTaiKhoan(tk)) {
                JOptionPane.showMessageDialog(this, "Sửa tài khoản thành công!");
                loadTaiKhoan();
            } else {
                JOptionPane.showMessageDialog(this, "Sửa tài khoản thất bại!");
            }
        }
    }

    private void xoaTaiKhoan() {
        int row = tblTaiKhoan.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn tài khoản cần xóa!");
            return;
        }

        int maTK = Integer.parseInt(tblTaiKhoan.getValueAt(row, 0).toString());

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc muốn xóa tài khoản này?",
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            if (service.xoaTaiKhoan(maTK)) {
                JOptionPane.showMessageDialog(this, "Xóa tài khoản thành công!");
                loadTaiKhoan();
            } else {
                JOptionPane.showMessageDialog(this, "Xóa tài khoản thất bại!");
            }
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            new QLTaiKhoan().setVisible(true);
        });
    }
}