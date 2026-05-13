package UI.ADMIN;

import Model.NhanVien;
import Service.NhanVienService;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class SuaNhanVien extends JDialog {

    private int maNV;
    private String chucVu = "nhanvien";

    private JTextField txtHoVaTen;
    private JComboBox<String> cboGioiTinh;
    private JTextField txtSDT;
    private JTextField txtDiaChi;

    private JButton btnLuu;
    private JButton btnHuy;

    private JLabel lblTitle;
    private JLabel lblSubTitle;

    public SuaNhanVien(java.awt.Frame parent, boolean modal, NhanVien nv) {
        super(parent, modal);

        this.maNV = nv.getMaNV();

        if (nv.getChucVu() != null && !nv.getChucVu().trim().isEmpty()) {
            this.chucVu = nv.getChucVu();
        }

        initComponents();

        txtHoVaTen.setText(nv.getHoTen());
        cboGioiTinh.setSelectedItem(nv.getGioiTinh());
        txtSDT.setText(nv.getSdt());
        txtDiaChi.setText(nv.getDiaChi());

        setLocationRelativeTo(parent);
    }

    private void initComponents() {
        setTitle("Sửa nhân viên");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(560, 480);
        setResizable(false);

        JPanel root = new JPanel(new GridBagLayout());
        root.setBackground(new Color(230, 240, 255));

        JPanel card = new JPanel();
        card.setPreferredSize(new Dimension(460, 390));
        card.setBackground(Color.WHITE);
        card.setBorder(new EmptyBorder(28, 40, 28, 40));
        card.setLayout(new BorderLayout(0, 22));

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));

        lblTitle = new JLabel("SỬA NHÂN VIÊN");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitle.setForeground(new Color(0, 86, 179));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        lblSubTitle = new JLabel("Cập nhật thông tin nhân viên");
        lblSubTitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSubTitle.setForeground(new Color(100, 116, 139));
        lblSubTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        headerPanel.add(lblTitle);
        headerPanel.add(Box.createVerticalStrut(8));
        headerPanel.add(lblSubTitle);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(7, 0, 7, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblHoTen = createLabel("Họ tên");
        JLabel lblGioiTinh = createLabel("Giới tính");
        JLabel lblSDT = createLabel("Số điện thoại");
        JLabel lblDiaChi = createLabel("Địa chỉ");

        txtHoVaTen = createTextField();

        cboGioiTinh = new JComboBox<>(new String[]{"Nam", "Nữ", "Khác"});
        cboGioiTinh.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cboGioiTinh.setPreferredSize(new Dimension(270, 38));
        cboGioiTinh.setBackground(Color.WHITE);

        txtSDT = createTextField();
        txtDiaChi = createTextField();

        addFormRow(formPanel, gbc, 0, lblHoTen, txtHoVaTen);
        addFormRow(formPanel, gbc, 1, lblGioiTinh, cboGioiTinh);
        addFormRow(formPanel, gbc, 2, lblSDT, txtSDT);
        addFormRow(formPanel, gbc, 3, lblDiaChi, txtDiaChi);

        btnLuu = createButton("Lưu", new Color(22, 163, 74), 120, 42);
        btnHuy = createButton("Hủy", new Color(220, 38, 38), 120, 42);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(btnLuu);
        buttonPanel.add(btnHuy);

        card.add(headerPanel, BorderLayout.NORTH);
        card.add(formPanel, BorderLayout.CENTER);
        card.add(buttonPanel, BorderLayout.SOUTH);

        root.add(card);
        setContentPane(root);

        btnLuu.addActionListener(e -> luuNhanVien());
        btnHuy.addActionListener(e -> dispose());
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setForeground(new Color(30, 41, 59));
        return label;
    }

    private JTextField createTextField() {
        JTextField textField = new JTextField();
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textField.setPreferredSize(new Dimension(270, 38));
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 210, 240), 1),
                new EmptyBorder(5, 10, 5, 10)
        ));
        return textField;
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

    private void addFormRow(JPanel panel, GridBagConstraints gbc, int row, JLabel label, JComponent input) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0;
        gbc.insets = new Insets(7, 0, 7, 18);
        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.gridy = row;
        gbc.weightx = 1;
        gbc.insets = new Insets(7, 0, 7, 0);
        panel.add(input, gbc);
    }

    private void luuNhanVien() {
        String hoTen = txtHoVaTen.getText().trim();
        String gioiTinh = cboGioiTinh.getSelectedItem().toString();
        String sdt = txtSDT.getText().trim();
        String diaChi = txtDiaChi.getText().trim();

        if (hoTen.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập họ tên!");
            txtHoVaTen.requestFocus();
            return;
        }

        if (sdt.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số điện thoại!");
            txtSDT.requestFocus();
            return;
        }

        if (!sdt.matches("\\d{9,11}")) {
            JOptionPane.showMessageDialog(this, "Số điện thoại phải có từ 9 đến 11 chữ số!");
            txtSDT.requestFocus();
            return;
        }

        if (diaChi.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập địa chỉ!");
            txtDiaChi.requestFocus();
            return;
        }

        try {
            NhanVien nv = new NhanVien();

            nv.setMaNV(maNV);
            nv.setHoTen(hoTen);
            nv.setGioiTinh(gioiTinh);
            nv.setSdt(sdt);
            nv.setDiaChi(diaChi);
            nv.setChucVu(chucVu);

            NhanVienService service = new NhanVienService();

            if (service.suaNhanVien(nv)) {
                JOptionPane.showMessageDialog(this, "Sửa nhân viên thành công!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Sửa thất bại! Vui lòng kiểm tra dữ liệu.");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
        }
    }
}