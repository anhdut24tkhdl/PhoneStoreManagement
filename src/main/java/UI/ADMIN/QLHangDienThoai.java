package UI.ADMIN;

import Model.HangDienThoai;
import Service.HangDienThoaiService;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class QLHangDienThoai extends JFrame {

    private JTable tblHangDienThoai;
    private JTextField txtTimKiem;

    private JButton btnTimKiem;
    private JButton btnLamMoi;
    private JButton btnThem;
    private JButton btnSua;
    private JButton btnXoa;
    private JButton btnQuayLai;

    private JLabel lblTitle;
    private JLabel lblSubTitle;
    private JLabel lblSoHang;

    private final Color BG = new Color(230, 240, 255);
    private final Color PRIMARY = new Color(37, 99, 235);
    private final Color SUCCESS = new Color(22, 163, 74);
    private final Color DANGER = new Color(220, 38, 38);
    private final Color WARNING = new Color(249, 115, 22);
    private final Color MUTED = new Color(100, 116, 139);
    private final Color TEXT = new Color(30, 41, 59);

    public QLHangDienThoai() {
        initComponents();
        loadHangDienThoai();
    }

    private void initComponents() {
        setTitle("Quản lý hãng điện thoại");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(920, 620);
        setResizable(false);

        JPanel root = new JPanel(new GridBagLayout());
        root.setBackground(BG);

        JPanel card = new JPanel();
        card.setPreferredSize(new Dimension(820, 520));
        card.setBackground(Color.WHITE);
        card.setBorder(new EmptyBorder(28, 35, 28, 35));
        card.setLayout(new BorderLayout(0, 22));

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));

        lblTitle = new JLabel("QUẢN LÝ HÃNG ĐIỆN THOẠI");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 30));
        lblTitle.setForeground(new Color(0, 86, 179));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        lblSubTitle = new JLabel("Quản lý danh sách thương hiệu điện thoại trong hệ thống");
        lblSubTitle.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblSubTitle.setForeground(new Color(100, 116, 139));
        lblSubTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        lblSoHang = new JLabel("Tổng hãng: 0");
        lblSoHang.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblSoHang.setForeground(PRIMARY);
        lblSoHang.setAlignmentX(Component.CENTER_ALIGNMENT);

        headerPanel.add(lblTitle);
        headerPanel.add(Box.createVerticalStrut(8));
        headerPanel.add(lblSubTitle);
        headerPanel.add(Box.createVerticalStrut(8));
        headerPanel.add(lblSoHang);

        txtTimKiem = new JTextField();
        txtTimKiem.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtTimKiem.setPreferredSize(new Dimension(350, 40));
        txtTimKiem.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 210, 240), 1),
                new EmptyBorder(5, 12, 5, 12)
        ));

        btnTimKiem = createButton("Tìm kiếm", PRIMARY, 115, 40);
        btnLamMoi = createButton("Làm mới", WARNING, 115, 40);
        btnThem = createButton("Thêm", SUCCESS, 105, 40);
        btnSua = createButton("Sửa", PRIMARY, 105, 40);
        btnXoa = createButton("Xóa", DANGER, 105, 40);
        btnQuayLai = createButton("Quay lại", MUTED, 115, 40);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        searchPanel.setBackground(Color.WHITE);
        searchPanel.add(txtTimKiem);
        searchPanel.add(btnTimKiem);
        searchPanel.add(btnLamMoi);

        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 14, 0));
        actionPanel.setBackground(Color.WHITE);
        actionPanel.add(btnThem);
        actionPanel.add(btnSua);
        actionPanel.add(btnXoa);
        actionPanel.add(btnQuayLai);

        tblHangDienThoai = new JTable();
        tblHangDienThoai.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Mã hãng", "Tên hãng"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });

        customTable();

        JScrollPane scrollPane = new JScrollPane(tblHangDienThoai);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(203, 213, 225), 1));
        scrollPane.getViewport().setBackground(Color.WHITE);

        JPanel centerPanel = new JPanel(new BorderLayout(0, 16));
        centerPanel.setBackground(Color.WHITE);
        centerPanel.add(searchPanel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        centerPanel.add(actionPanel, BorderLayout.SOUTH);

        card.add(headerPanel, BorderLayout.NORTH);
        card.add(centerPanel, BorderLayout.CENTER);

        root.add(card);
        setContentPane(root);

        btnTimKiem.addActionListener(e -> timKiem());
        txtTimKiem.addActionListener(e -> timKiem());

        btnLamMoi.addActionListener(e -> {
            txtTimKiem.setText("");
            loadHangDienThoai();
        });

        btnThem.addActionListener(e -> themHangDienThoai());
        btnSua.addActionListener(e -> suaHangDienThoai());
        btnXoa.addActionListener(e -> xoaHangDienThoai());

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

    private void customTable() {
        tblHangDienThoai.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tblHangDienThoai.setRowHeight(36);
        tblHangDienThoai.setSelectionBackground(new Color(219, 234, 254));
        tblHangDienThoai.setSelectionForeground(TEXT);
        tblHangDienThoai.setGridColor(new Color(226, 232, 240));
        tblHangDienThoai.setShowGrid(true);

        tblHangDienThoai.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tblHangDienThoai.getTableHeader().setBackground(PRIMARY);
        tblHangDienThoai.getTableHeader().setForeground(Color.WHITE);
        tblHangDienThoai.getTableHeader().setPreferredSize(new Dimension(0, 38));
    }

    private void loadHangDienThoai() {
        HangDienThoaiService service = new HangDienThoaiService();
        ArrayList<HangDienThoai> list = service.getAllHangDienThoai();
        hienThiTable(list);
    }

    private void hienThiTable(ArrayList<HangDienThoai> list) {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[]{"Mã hãng", "Tên hãng"});

        for (HangDienThoai h : list) {
            model.addRow(new Object[]{
                    h.getMaHang(),
                    h.getTenHang()
            });
        }

        tblHangDienThoai.setModel(model);
        lblSoHang.setText("Tổng hãng: " + list.size());

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        tblHangDienThoai.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tblHangDienThoai.getColumnModel().getColumn(0).setPreferredWidth(120);
        tblHangDienThoai.getColumnModel().getColumn(1).setPreferredWidth(500);
    }

    private void timKiem() {
        String keyword = txtTimKiem.getText().trim().toLowerCase();

        HangDienThoaiService service = new HangDienThoaiService();
        ArrayList<HangDienThoai> list = service.getAllHangDienThoai();
        ArrayList<HangDienThoai> ketQua = new ArrayList<>();

        if (keyword.isEmpty()) {
            hienThiTable(list);
            return;
        }

        for (HangDienThoai h : list) {
            if (String.valueOf(h.getMaHang()).contains(keyword)
                    || h.getTenHang().toLowerCase().contains(keyword)) {
                ketQua.add(h);
            }
        }

        hienThiTable(ketQua);
    }

    private void themHangDienThoai() {
        JTextField txtTenHang = createInputField();

        JPanel panel = createDialogPanel("Tên hãng", txtTenHang);

        int result = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Thêm hãng điện thoại",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            String tenHang = txtTenHang.getText().trim();

            if (tenHang.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập tên hãng!");
                return;
            }

            HangDienThoai h = new HangDienThoai();
            h.setTenHang(tenHang);

            HangDienThoaiService service = new HangDienThoaiService();

            if (service.themHangDienThoai(h)) {
                JOptionPane.showMessageDialog(this, "Thêm hãng điện thoại thành công!");
                loadHangDienThoai();
            } else {
                JOptionPane.showMessageDialog(this, "Thêm hãng điện thoại thất bại!");
            }
        }
    }

    private void suaHangDienThoai() {
        int row = tblHangDienThoai.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hãng cần sửa!");
            return;
        }

        int maHang = Integer.parseInt(tblHangDienThoai.getValueAt(row, 0).toString());
        String tenHangCu = tblHangDienThoai.getValueAt(row, 1).toString();

        JTextField txtTenHang = createInputField();
        txtTenHang.setText(tenHangCu);

        JPanel panel = createDialogPanel("Tên hãng", txtTenHang);

        int result = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Sửa hãng điện thoại",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            String tenHangMoi = txtTenHang.getText().trim();

            if (tenHangMoi.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Tên hãng không được để trống!");
                return;
            }

            HangDienThoai h = new HangDienThoai();
            h.setMaHang(maHang);
            h.setTenHang(tenHangMoi);

            HangDienThoaiService service = new HangDienThoaiService();

            if (service.suaHangDienThoai(h)) {
                JOptionPane.showMessageDialog(this, "Sửa hãng điện thoại thành công!");
                loadHangDienThoai();
            } else {
                JOptionPane.showMessageDialog(this, "Sửa hãng điện thoại thất bại!");
            }
        }
    }

    private void xoaHangDienThoai() {
        int row = tblHangDienThoai.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hãng cần xóa!");
            return;
        }

        int maHang = Integer.parseInt(tblHangDienThoai.getValueAt(row, 0).toString());
        String tenHang = tblHangDienThoai.getValueAt(row, 1).toString();

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc muốn xóa hãng \"" + tenHang + "\" không?",
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            HangDienThoaiService service = new HangDienThoaiService();

            if (service.xoaHangDienThoai(maHang)) {
                JOptionPane.showMessageDialog(this, "Xóa hãng điện thoại thành công!");
                loadHangDienThoai();
            } else {
                JOptionPane.showMessageDialog(this, "Xóa thất bại! Hãng này có thể đang được sản phẩm sử dụng.");
            }
        }
    }

    private JTextField createInputField() {
        JTextField textField = new JTextField();
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textField.setPreferredSize(new Dimension(260, 38));
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 210, 240), 1),
                new EmptyBorder(5, 10, 5, 10)
        ));
        return textField;
    }

    private JPanel createDialogPanel(String labelText, JTextField textField) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new EmptyBorder(12, 12, 12, 12));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 0, 6, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(textField, gbc);

        return panel;
    }

    public static void main(String args[]) {
        EventQueue.invokeLater(() -> new QLHangDienThoai().setVisible(true));
    }
}