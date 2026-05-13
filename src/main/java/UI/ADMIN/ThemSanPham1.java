package UI.ADMIN;

import Model.SanPham;
import Service.SanPhamService;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class ThemSanPham1 extends JDialog {

    private JTextField txtTenSP;
    private JTextField txtMaHang;
    private JTextField txtGiaBan;
    private JTextField txtSoLuong;
    private JTextField txtMoTa;

    private JButton btnLuu;
    private JButton btnHuy;

    private JLabel lblTitle;
    private JLabel lblSubTitle;

    public ThemSanPham1(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    private void initComponents() {
        setTitle("Thêm sản phẩm");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(580, 540);
        setResizable(false);

        JPanel root = new JPanel(new GridBagLayout());
        root.setBackground(new Color(230, 240, 255));

        JPanel card = new JPanel();
        card.setPreferredSize(new Dimension(480, 450));
        card.setBackground(Color.WHITE);
        card.setBorder(new EmptyBorder(28, 40, 28, 40));
        card.setLayout(new BorderLayout(0, 22));

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));

        lblTitle = new JLabel("THÊM SẢN PHẨM");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitle.setForeground(new Color(0, 86, 179));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        lblSubTitle = new JLabel("Nhập thông tin sản phẩm mới");
        lblSubTitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSubTitle.setForeground(new Color(100, 116, 139));
        lblSubTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        headerPanel.add(lblTitle);
        headerPanel.add(Box.createVerticalStrut(8));
        headerPanel.add(lblSubTitle);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblTenSP = createLabel("Tên sản phẩm");
        JLabel lblMaHang = createLabel("Mã hãng");
        JLabel lblGiaBan = createLabel("Giá bán");
        JLabel lblSoLuong = createLabel("Số lượng");
        JLabel lblMoTa = createLabel("Mô tả");

        txtTenSP = createTextField();
        txtMaHang = createTextField();
        txtGiaBan = createTextField();
        txtSoLuong = createTextField();
        txtMoTa = createTextField();

        addFormRow(formPanel, gbc, 0, lblTenSP, txtTenSP);
        addFormRow(formPanel, gbc, 1, lblMaHang, txtMaHang);
        addFormRow(formPanel, gbc, 2, lblGiaBan, txtGiaBan);
        addFormRow(formPanel, gbc, 3, lblSoLuong, txtSoLuong);
        addFormRow(formPanel, gbc, 4, lblMoTa, txtMoTa);

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

        btnLuu.addActionListener(e -> luuSanPham());
        btnHuy.addActionListener(e -> dispose());

        setLocationRelativeTo(getParent());
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
        gbc.insets = new Insets(8, 0, 8, 18);
        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.gridy = row;
        gbc.weightx = 1;
        gbc.insets = new Insets(8, 0, 8, 0);
        panel.add(input, gbc);
    }

    private void luuSanPham() {
        String tenSP = txtTenSP.getText().trim();
        String maHangText = txtMaHang.getText().trim();
        String giaBanText = txtGiaBan.getText().trim();
        String soLuongText = txtSoLuong.getText().trim();
        String moTa = txtMoTa.getText().trim();

        if (tenSP.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tên sản phẩm!");
            txtTenSP.requestFocus();
            return;
        }

        if (maHangText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã hãng!");
            txtMaHang.requestFocus();
            return;
        }

        if (giaBanText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập giá bán!");
            txtGiaBan.requestFocus();
            return;
        }

        if (soLuongText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số lượng!");
            txtSoLuong.requestFocus();
            return;
        }

        try {
            int maHang = Integer.parseInt(maHangText);
            double giaBan = Double.parseDouble(giaBanText);
            int soLuong = Integer.parseInt(soLuongText);

            if (maHang <= 0) {
                JOptionPane.showMessageDialog(this, "Mã hãng phải lớn hơn 0!");
                txtMaHang.requestFocus();
                return;
            }

            if (giaBan <= 0) {
                JOptionPane.showMessageDialog(this, "Giá bán phải lớn hơn 0!");
                txtGiaBan.requestFocus();
                return;
            }

            if (soLuong < 0) {
                JOptionPane.showMessageDialog(this, "Số lượng không được nhỏ hơn 0!");
                txtSoLuong.requestFocus();
                return;
            }

            SanPham sp = new SanPham();
            sp.setTenSP(tenSP);
            sp.setMaHang(maHang);
            sp.setGiaBan(giaBan);
            sp.setSoLuong(soLuong);
            sp.setMoTa(moTa);

            SanPhamService service = new SanPhamService();

            if (service.themSanPham(sp)) {
                JOptionPane.showMessageDialog(this, "Thêm sản phẩm thành công!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Thêm sản phẩm thất bại!");
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Mã hãng, giá bán và số lượng phải là số hợp lệ!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
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
            ThemSanPham1 dialog = new ThemSanPham1(new JFrame(), true);
            dialog.setVisible(true);
        });
    }
}