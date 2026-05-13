package UI;

import UI.ADMIN.TrangChuAdmin;
import Service.TaiKhoanService;
import Model.TaiKhoan;
import UI.NhanVien.TrangChuNhanVien;
import Utils.Session;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class DangNhap extends JFrame {

    private JTextField txtTaiKhoan;
    private JPasswordField txtMatKhau;
    private JButton btnDangNhap;
    private JButton btnThoat;

    public DangNhap() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Đăng nhập hệ thống");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(820, 600);
        setResizable(false);

        GradientPanel background = new GradientPanel();
        background.setLayout(new GridBagLayout());

        JPanel card = new JPanel();
        card.setPreferredSize(new Dimension(500, 470));
        card.setBackground(Color.WHITE);
        card.setBorder(new EmptyBorder(28, 45, 28, 45));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        JLabel lblTitle1 = new JLabel("QUẢN LÝ CỬA HÀNG");
        lblTitle1.setFont(new Font("Segoe UI", Font.BOLD, 30));
        lblTitle1.setForeground(new Color(0, 86, 179));
        lblTitle1.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblTitle2 = new JLabel("ĐIỆN THOẠI");
        lblTitle2.setFont(new Font("Segoe UI", Font.BOLD, 34));
        lblTitle2.setForeground(new Color(0, 123, 255));
        lblTitle2.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblLogin = new JLabel("Đăng nhập tài khoản");
        lblLogin.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        lblLogin.setForeground(new Color(70, 70, 70));
        lblLogin.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblTaiKhoan = new JLabel("Tên đăng nhập");
        lblTaiKhoan.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTaiKhoan.setForeground(new Color(30, 30, 30));

        txtTaiKhoan = new JTextField();
        txtTaiKhoan.setMaximumSize(new Dimension(360, 40));
        txtTaiKhoan.setPreferredSize(new Dimension(360, 40));
        txtTaiKhoan.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        txtTaiKhoan.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(170, 205, 240), 1),
                new EmptyBorder(5, 12, 5, 12)
        ));
        txtTaiKhoan.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblMatKhau = new JLabel("Mật khẩu");
        lblMatKhau.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblMatKhau.setForeground(new Color(30, 30, 30));

        txtMatKhau = new JPasswordField();
        txtMatKhau.setMaximumSize(new Dimension(360, 40));
        txtMatKhau.setPreferredSize(new Dimension(360, 40));
        txtMatKhau.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        txtMatKhau.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(170, 205, 240), 1),
                new EmptyBorder(5, 12, 5, 12)
        ));
        txtMatKhau.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnDangNhap = new JButton("Đăng nhập");
        btnDangNhap.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnDangNhap.setBackground(new Color(0, 123, 255));
        btnDangNhap.setForeground(Color.WHITE);
        btnDangNhap.setFocusPainted(false);
        btnDangNhap.setBorderPainted(false);
        btnDangNhap.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnDangNhap.setPreferredSize(new Dimension(160, 45));
        btnDangNhap.setHorizontalAlignment(SwingConstants.CENTER);
        btnDangNhap.setVerticalAlignment(SwingConstants.CENTER);
        btnDangNhap.setMargin(new Insets(0, 0, 0, 0));

        btnThoat = new JButton("Thoát");
        btnThoat.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnThoat.setBackground(new Color(220, 53, 69));
        btnThoat.setForeground(Color.WHITE);
        btnThoat.setFocusPainted(false);
        btnThoat.setBorderPainted(false);
        btnThoat.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnThoat.setPreferredSize(new Dimension(130, 45));
        btnThoat.setHorizontalAlignment(SwingConstants.CENTER);
        btnThoat.setVerticalAlignment(SwingConstants.CENTER);
        btnThoat.setMargin(new Insets(0, 0, 0, 0));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 22, 0));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setMaximumSize(new Dimension(390, 60));
        buttonPanel.setPreferredSize(new Dimension(390, 60));
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonPanel.add(btnDangNhap);
        buttonPanel.add(btnThoat);

        card.add(lblTitle1);
        card.add(Box.createVerticalStrut(10));
        card.add(lblTitle2);
        card.add(Box.createVerticalStrut(18));
        card.add(lblLogin);
        card.add(Box.createVerticalStrut(25));

        card.add(createLeftPanel(lblTaiKhoan, 360));
        card.add(Box.createVerticalStrut(7));
        card.add(txtTaiKhoan);
        card.add(Box.createVerticalStrut(18));

        card.add(createLeftPanel(lblMatKhau, 360));
        card.add(Box.createVerticalStrut(7));
        card.add(txtMatKhau);
        card.add(Box.createVerticalStrut(20));

        card.add(buttonPanel);

        background.add(card);
        add(background);

        btnDangNhap.addActionListener(e -> dangNhap());
        btnThoat.addActionListener(e -> System.exit(0));
        txtMatKhau.addActionListener(e -> dangNhap());
        txtTaiKhoan.addActionListener(e -> txtMatKhau.requestFocus());

        setLocationRelativeTo(null);
    }

    private JPanel createLeftPanel(JLabel label, int width) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        panel.setMaximumSize(new Dimension(width, 22));
        panel.setPreferredSize(new Dimension(width, 22));
        panel.setBackground(Color.WHITE);
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(label);
        return panel;
    }

    private void dangNhap() {
        String tenDangNhap = txtTaiKhoan.getText().trim();
        String matKhau = new String(txtMatKhau.getPassword()).trim();

        if (tenDangNhap.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tên đăng nhập!");
            txtTaiKhoan.requestFocus();
            return;
        }

        if (matKhau.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mật khẩu!");
            txtMatKhau.requestFocus();
            return;
        }

        TaiKhoanService tksv = new TaiKhoanService();
        TaiKhoan tk = tksv.dangNhap(tenDangNhap, matKhau);

        if (tk != null) {
            Session.currentUser = tk;
            JOptionPane.showMessageDialog(this, "Đăng nhập thành công!");

            String vaiTro = tk.getVaiTro();

            if ("Admin".equalsIgnoreCase(vaiTro)) {
                TrangChuAdmin tc = new TrangChuAdmin();
                tc.setVisible(true);
                this.dispose();

            } else if ("NhanVien".equalsIgnoreCase(vaiTro)) {
                TrangChuNhanVien tc = new TrangChuNhanVien();
                tc.setVisible(true);
                this.dispose();

            } else {
                JOptionPane.showMessageDialog(this, "Tài khoản chưa được phân quyền hợp lệ!");
            }

        } else {
            JOptionPane.showMessageDialog(this, "Sai tài khoản hoặc mật khẩu!");
            txtMatKhau.setText("");
            txtTaiKhoan.requestFocus();
        }
    }

    class GradientPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

            Color color1 = new Color(0, 123, 255);
            Color color2 = new Color(135, 206, 250);

            GradientPaint gp = new GradientPaint(
                    0, 0, color1,
                    getWidth(), getHeight(), color2
            );

            g2d.setPaint(gp);
            g2d.fillRect(0, 0, getWidth(), getHeight());
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
            new DangNhap().setVisible(true);
        });
    }
}