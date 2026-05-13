package UI.ADMIN;

import Utils.Session;
import java.awt.*;
import javax.swing.*;

public class TrangChuAdmin extends javax.swing.JFrame {

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
        customUI();
    }

    private void customUI() {
        setTitle("Trang chủ Admin");

        getContentPane().setBackground(new Color(245, 247, 250));

        Font btnFont = new Font("Segoe UI", Font.BOLD, 17);

        JButton[] buttons = {
            btnQLNV, btnQLSP, btnQLH, btnQLHD, btnQLPN, btnThoat
        };

        for (JButton btn : buttons) {
            btn.setFont(btnFont);
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btn.setPreferredSize(new Dimension(340, 58));
        }

        btnQLNV.setBackground(new Color(37, 99, 235));
        btnQLSP.setBackground(new Color(22, 163, 74));
        btnQLH.setBackground(new Color(124, 58, 237));
        btnQLHD.setBackground(new Color(249, 115, 22));
        btnQLPN.setBackground(new Color(14, 165, 233));
        btnThoat.setBackground(new Color(220, 38, 38));

        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblTitle.setForeground(new Color(30, 41, 59));

        lblXinChao.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblVaiTro.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        if (Session.currentUser != null) {
            lblXinChao.setText("Xin chào, " + Session.currentUser.getTenDangNhap());
            lblVaiTro.setText("Vai trò: " + Session.currentUser.getVaiTro());
        } else {
            lblXinChao.setText("Xin chào, Admin");
            lblVaiTro.setText("Vai trò: Admin");
        }
    }

    private void initComponents() {
        lblTitle = new JLabel("TRANG CHỦ ADMIN");
        lblXinChao = new JLabel();
        lblVaiTro = new JLabel();

        btnQLNV = new JButton("Quản lý nhân viên");
        btnQLSP = new JButton("Quản lý sản phẩm");
        btnQLH = new JButton("Quản lý hãng điện thoại");
        btnQLHD = new JButton("Quản lý hóa đơn");
        btnQLPN = new JButton("Quản lý phiếu nhập");
        btnThoat = new JButton("Thoát");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

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
            QLPhieuNhap qlpn = new QLPhieuNhap();
            qlpn.setVisible(true);
            dispose();
        });

        btnThoat.addActionListener(e -> System.exit(0));

        JPanel topPanel = new JPanel(new GridLayout(2, 1));
        topPanel.setBackground(new Color(245, 247, 250));
        topPanel.add(lblXinChao);
        topPanel.add(lblVaiTro);

        JPanel buttonPanel = new JPanel(new GridLayout(6, 1, 0, 16));
        buttonPanel.setBackground(new Color(245, 247, 250));
        buttonPanel.add(btnQLNV);
        buttonPanel.add(btnQLSP);
        buttonPanel.add(btnQLH);
        buttonPanel.add(btnQLHD);
        buttonPanel.add(btnQLPN);
        buttonPanel.add(btnThoat);

        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        centerPanel.setBackground(new Color(245, 247, 250));
        centerPanel.add(buttonPanel);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(245, 247, 250));

        GroupLayout layout = new GroupLayout(mainPanel);
        mainPanel.setLayout(layout);

        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(20)
                    .addComponent(topPanel, 250, 250, 250))
                .addGroup(layout.createSequentialGroup()
                    .addGap(155)
                    .addComponent(lblTitle))
                .addComponent(centerPanel, 650, 650, 650)
        );

        layout.setVerticalGroup(
            layout.createSequentialGroup()
                .addGap(20)
                .addComponent(topPanel, 45, 45, 45)
                .addGap(20)
                .addComponent(lblTitle)
                .addGap(35)
                .addComponent(centerPanel, 440, 440, 440)
                .addGap(20)
        );

        setContentPane(mainPanel);

        setSize(650, 620);
        setResizable(false);
        setLocationRelativeTo(null);
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new TrangChuAdmin().setVisible(true));
    }
}