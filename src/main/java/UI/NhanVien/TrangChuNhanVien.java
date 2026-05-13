package UI.NhanVien;

import UI.DangNhap;
import Utils.Session;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class TrangChuNhanVien extends JFrame {

    private JButton btnSPTK;
    private JButton btnQLKH;
    private JButton btnQLDH;
    private JButton btnDangXuat;

    private JLabel lblTitle;
    private JLabel lblXinChao;
    private JLabel lblVaiTro;

    public TrangChuNhanVien() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Trang chủ nhân viên");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(760, 560);
        setResizable(false);

        JPanel root = new JPanel(new GridBagLayout());
        root.setBackground(new Color(230, 240, 255));

        JPanel card = new JPanel();
        card.setPreferredSize(new Dimension(620, 440));
        card.setBackground(Color.WHITE);
        card.setBorder(new EmptyBorder(32, 50, 32, 50));
        card.setLayout(new BorderLayout(0, 28));

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));

        lblTitle = new JLabel("TRANG CHỦ NHÂN VIÊN");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblTitle.setForeground(new Color(0, 86, 179));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        lblXinChao = new JLabel();
        lblXinChao.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblXinChao.setForeground(new Color(30, 41, 59));
        lblXinChao.setAlignmentX(Component.CENTER_ALIGNMENT);

        lblVaiTro = new JLabel();
        lblVaiTro.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblVaiTro.setForeground(new Color(100, 116, 139));
        lblVaiTro.setAlignmentX(Component.CENTER_ALIGNMENT);

        if (Session.currentUser != null) {
            lblXinChao.setText("Xin chào, " + Session.currentUser.getTenDangNhap());
            lblVaiTro.setText("Vai trò: " + Session.currentUser.getVaiTro());
        } else {
            lblXinChao.setText("Xin chào");
            lblVaiTro.setText("Vai trò: Nhân viên");
        }

        headerPanel.add(lblTitle);
        headerPanel.add(Box.createVerticalStrut(16));
        headerPanel.add(lblXinChao);
        headerPanel.add(Box.createVerticalStrut(5));
        headerPanel.add(lblVaiTro);

        btnSPTK = createMenuButton("Xem sản phẩm tồn kho", new Color(37, 99, 235));
        btnQLKH = createMenuButton("Quản lý khách hàng", new Color(22, 163, 74));
        btnQLDH = createMenuButton("Quản lý đơn hàng", new Color(249, 115, 22));
        btnDangXuat = createMenuButton("Đăng xuất", new Color(220, 38, 38));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setLayout(new GridLayout(2, 2, 20, 20));
        buttonPanel.setPreferredSize(new Dimension(500, 170));

        buttonPanel.add(btnSPTK);
        buttonPanel.add(btnQLKH);
        buttonPanel.add(btnQLDH);
        buttonPanel.add(btnDangXuat);

        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        centerPanel.setBackground(Color.WHITE);
        centerPanel.add(buttonPanel);

        card.add(headerPanel, BorderLayout.NORTH);
        card.add(centerPanel, BorderLayout.CENTER);

        root.add(card);
        setContentPane(root);

        btnSPTK.addActionListener(e -> {
            SanPham sp = new SanPham();
            sp.setVisible(true);
            dispose();
        });

        btnQLKH.addActionListener(e -> {
            QLKH qlkh = new QLKH();
            qlkh.setVisible(true);
            dispose();
        });

        btnQLDH.addActionListener(e -> {
            HoaDon hd = new HoaDon();
            hd.setVisible(true);
            dispose();
        });

        btnDangXuat.addActionListener(e -> {
            Session.currentUser = null;
            DangNhap dn = new DangNhap();
            dn.setVisible(true);
            dispose();
        });

        setLocationRelativeTo(null);
    }

    private JButton createMenuButton(String text, Color bgColor) {
        JButton button = new JButton(text);

        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.setVerticalAlignment(SwingConstants.CENTER);
        button.setMargin(new Insets(0, 0, 0, 0));

        return button;
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
            new TrangChuNhanVien().setVisible(true);
        });
    }
}