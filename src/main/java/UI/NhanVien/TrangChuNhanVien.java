package UI.NhanVien;

import Utils.Session;
import java.awt.*;
import javax.swing.*;

public class TrangChuNhanVien extends javax.swing.JFrame {

    private JButton btnSPTK;
    private JButton btnQLKH;
    private JButton btnQLDH;

    private JLabel lblTitle;
    private JLabel lblXinChao;
    private JLabel lblVaiTro;

    public TrangChuNhanVien() {
        initComponents();
        customUI();
    }

    private void customUI() {

        setTitle("Trang chủ nhân viên");

        getContentPane().setBackground(
                new Color(245, 247, 250)
        );

        Font btnFont =
                new Font("Segoe UI",
                        Font.BOLD,
                        17);

        JButton[] buttons = {
            btnSPTK,
            btnQLKH,
            btnQLDH
        };

        for (JButton btn : buttons) {

            btn.setFont(btnFont);

            btn.setForeground(Color.WHITE);

            btn.setFocusPainted(false);

            btn.setCursor(
                    new Cursor(Cursor.HAND_CURSOR)
            );

            btn.setPreferredSize(
                    new Dimension(320, 65)
            );
        }

        btnSPTK.setBackground(
                new Color(37, 99, 235)
        );

        btnQLKH.setBackground(
                new Color(22, 163, 74)
        );

        btnQLDH.setBackground(
                new Color(249, 115, 22)
        );

        lblTitle.setFont(
                new Font("Segoe UI",
                        Font.BOLD,
                        32)
        );

        lblTitle.setForeground(
                new Color(30, 41, 59)
        );

        lblXinChao.setFont(
                new Font("Segoe UI",
                        Font.BOLD,
                        15)
        );

        lblVaiTro.setFont(
                new Font("Segoe UI",
                        Font.PLAIN,
                        13)
        );

        if (Session.currentUser != null) {

            lblXinChao.setText(
                    "Xin chào, "
                    + Session.currentUser.getTenDangNhap()
            );

            lblVaiTro.setText(
                    "Vai trò: "
                    + Session.currentUser.getVaiTro()
            );

        } else {

            lblXinChao.setText("Xin chào");
            lblVaiTro.setText("Vai trò: Nhân viên");
        }
    }

    private void initComponents() {

        lblTitle = new JLabel();
        lblXinChao = new JLabel();
        lblVaiTro = new JLabel();

        btnSPTK = new JButton();
        btnQLKH = new JButton();
        btnQLDH = new JButton();

        setDefaultCloseOperation(
                javax.swing.WindowConstants.EXIT_ON_CLOSE
        );

        lblTitle.setText("TRANG CHỦ NHÂN VIÊN");

        btnSPTK.setText("Xem Sản Phẩm Tồn Kho");

        btnSPTK.addActionListener(e -> {

            SanPham sp = new SanPham();

            sp.setVisible(true);

            dispose();
        });

        btnQLKH.setText("Quản Lý Khách Hàng");

        btnQLKH.addActionListener(e -> {

            QLKH qlkh = new QLKH();

            qlkh.setVisible(true);

            dispose();
        });

        btnQLDH.setText("Quản Lý Đơn Hàng");

        btnQLDH.addActionListener(e -> {

            HoaDon hd = new HoaDon();

            hd.setVisible(true);

            dispose();
        });

        JPanel topPanel = new JPanel();

        topPanel.setBackground(
                new Color(245, 247, 250)
        );

        topPanel.setLayout(
                new GridLayout(2, 1)
        );

        topPanel.add(lblXinChao);
        topPanel.add(lblVaiTro);

        JPanel buttonPanel = new JPanel();

        buttonPanel.setBackground(
                new Color(245, 247, 250)
        );

        buttonPanel.setLayout(
                new GridLayout(3, 1, 0, 25)
        );

        buttonPanel.add(btnSPTK);
        buttonPanel.add(btnQLKH);
        buttonPanel.add(btnQLDH);

        JPanel centerPanel = new JPanel();

        centerPanel.setBackground(
                new Color(245, 247, 250)
        );

        centerPanel.setLayout(
                new FlowLayout(
                        FlowLayout.CENTER,
                        0,
                        0
                )
        );

        centerPanel.add(buttonPanel);

        JPanel mainPanel = new JPanel();

        mainPanel.setBackground(
                new Color(245, 247, 250)
        );

        GroupLayout layout =
                new GroupLayout(mainPanel);

        mainPanel.setLayout(layout);

        layout.setHorizontalGroup(
                layout.createParallelGroup(
                        GroupLayout.Alignment.LEADING)

                        .addGroup(layout.createSequentialGroup()
                                .addGap(20)
                                .addComponent(topPanel,
                                        250,
                                        250,
                                        250))

                        .addGroup(layout.createSequentialGroup()
                                .addGap(110)
                                .addComponent(lblTitle))

                        .addComponent(centerPanel,
                                560,
                                560,
                                560)
        );

        layout.setVerticalGroup(
                layout.createSequentialGroup()

                        .addGap(20)

                        .addComponent(topPanel,
                                45,
                                45,
                                45)

                        .addGap(20)

                        .addComponent(lblTitle)

                        .addGap(45)

                        .addComponent(centerPanel,
                                280,
                                280,
                                280)

                        .addGap(20)
        );

        setContentPane(mainPanel);

        setSize(580, 520);

        setResizable(false);

        setLocationRelativeTo(null);
    }

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(() ->
                new TrangChuNhanVien().setVisible(true));
    }
}