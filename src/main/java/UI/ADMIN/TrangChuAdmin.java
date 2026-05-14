package UI.ADMIN;

import UI.DangNhap;

import Utils.Session;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class TrangChuAdmin extends JFrame {

    private JButton btnQLNV;
    private JButton btnQLSP;
    private JButton btnQLH;
    private JButton btnQLHD;
    private JButton btnQLPN;
    private JButton btnThoat;

    private JLabel lblTitle;
    private JLabel lblXinChao;
    private JLabel lblVaiTro;

    public TrangChuAdmin() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Trang chủ Admin");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(820, 580);
        setResizable(false);

        JPanel root = new JPanel(new GridBagLayout());
        root.setBackground(new Color(230, 240, 255));

        JPanel card = new JPanel();
        card.setPreferredSize(new Dimension(700, 480));
        card.setBackground(Color.WHITE);
        card.setBorder(new EmptyBorder(30, 45, 30, 45));
        card.setLayout(new BorderLayout(0, 25));

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));

        lblTitle = new JLabel("TRANG CHỦ ADMIN");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 34));
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
            lblXinChao.setText("Xin chào, Admin");
            lblVaiTro.setText("Vai trò: Admin");
        }

        headerPanel.add(lblTitle);
        headerPanel.add(Box.createVerticalStrut(15));
        headerPanel.add(lblXinChao);
        headerPanel.add(Box.createVerticalStrut(5));
        headerPanel.add(lblVaiTro);

        btnQLNV = createMenuButton("Quản lý nhân viên", new Color(37, 99, 235));
        btnQLSP = createMenuButton("Quản lý sản phẩm", new Color(22, 163, 74));
        btnQLH = createMenuButton("Quản lý hãng điện thoại", new Color(124, 58, 237));
        btnQLHD = createMenuButton("Quản lý hóa đơn", new Color(249, 115, 22));
        btnQLPN = createMenuButton("Quản lý phiếu nhập", new Color(14, 165, 233));
        btnThoat = createMenuButton("Đăng xuất", new Color(220, 38, 38));

        JPanel buttonGrid = new JPanel(new GridLayout(3, 2, 20, 20));
        buttonGrid.setBackground(Color.WHITE);
        buttonGrid.setPreferredSize(new Dimension(600, 250));

        buttonGrid.add(btnQLNV);
        buttonGrid.add(btnQLSP);
        buttonGrid.add(btnQLH);
        buttonGrid.add(btnQLHD);
        buttonGrid.add(btnQLPN);
        buttonGrid.add(btnThoat);

        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        centerPanel.setBackground(Color.WHITE);
        centerPanel.add(buttonGrid);

        card.add(headerPanel, BorderLayout.NORTH);
        card.add(centerPanel, BorderLayout.CENTER);

        root.add(card);
        setContentPane(root);

        btnQLNV.addActionListener(e -> {
            QLNV qlnv = new QLNV();
            qlnv.setVisible(true);
            dispose();
        });

        btnQLSP.addActionListener(e -> {
            QLSP qlsp = new QLSP();
            qlsp.setVisible(true);
            dispose();
        });

        btnQLH.addActionListener(e -> {
            QLHangDienThoai qlhdt = new QLHangDienThoai();
            qlhdt.setVisible(true);
            dispose();
        });

        btnQLHD.addActionListener(e -> {
            QLDonHang qldh = new QLDonHang();
            qldh.setVisible(true);
            dispose();
        });

        btnQLPN.addActionListener(e -> {
            QLPhieuNhap qlpn = new QLPhieuNhap(false);
            qlpn.setVisible(true);
            dispose();
        });

        btnThoat.addActionListener(e -> {
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
            new TrangChuAdmin().setVisible(true);
        });
    }
}