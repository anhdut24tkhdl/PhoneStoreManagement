package UI.ADMIN;
import java.text.DecimalFormat;
import Model.SanPham;
import Service.SanPhamService;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class QLSP extends JFrame {

    private JTable tblSanPham;
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

    public QLSP() {
        initComponents();
        loadSanPham();
    }

    private void initComponents() {
        setTitle("Quản lý sản phẩm");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 650);
        setResizable(false);

        JPanel root = new JPanel(new GridBagLayout());
        root.setBackground(new Color(230, 240, 255));

        JPanel card = new JPanel();
        card.setPreferredSize(new Dimension(920, 570));
        card.setBackground(Color.WHITE);
        card.setBorder(new EmptyBorder(25, 35, 25, 35));
        card.setLayout(new BorderLayout(0, 22));

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));

        lblTitle = new JLabel("QUẢN LÝ SẢN PHẨM");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblTitle.setForeground(new Color(0, 86, 179));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        lblSubTitle = new JLabel("Danh sách sản phẩm, giá bán, số lượng và thông tin mô tả");
        lblSubTitle.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblSubTitle.setForeground(new Color(100, 116, 139));
        lblSubTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        headerPanel.add(lblTitle);
        headerPanel.add(Box.createVerticalStrut(8));
        headerPanel.add(lblSubTitle);

        txtTimKiem = new JTextField();
        txtTimKiem.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtTimKiem.setPreferredSize(new Dimension(420, 40));
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

        tblSanPham = new JTable();
        tblSanPham.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Mã SP", "Tên SP", "Mã hãng", "Giá bán", "Số lượng", "Mô tả"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });

        tblSanPham.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tblSanPham.setRowHeight(34);
        tblSanPham.setSelectionBackground(new Color(219, 234, 254));
        tblSanPham.setSelectionForeground(new Color(15, 23, 42));
        tblSanPham.setGridColor(new Color(226, 232, 240));
        tblSanPham.setShowGrid(true);

        tblSanPham.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tblSanPham.getTableHeader().setBackground(new Color(37, 99, 235));
        tblSanPham.getTableHeader().setForeground(Color.WHITE);
        tblSanPham.getTableHeader().setPreferredSize(new Dimension(0, 38));

        jScrollPane1 = new JScrollPane(tblSanPham);
        jScrollPane1.setBorder(BorderFactory.createLineBorder(new Color(203, 213, 225), 1));
        jScrollPane1.getViewport().setBackground(Color.WHITE);

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

        btnTimKiem.addActionListener(e -> timKiemSanPham());

        txtTimKiem.addActionListener(e -> timKiemSanPham());

        btnLamMoi.addActionListener(e -> {
            txtTimKiem.setText("");
            loadSanPham();
        });

        btnThem.addActionListener(e -> {
            ThemSanPham1 dialog = new ThemSanPham1(this, true);
            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);
            loadSanPham();
        });

        btnSua.addActionListener(e -> suaSanPham());

        btnXoa.addActionListener(e -> xoaSanPham());

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

    private void loadSanPham() {
        SanPhamService service = new SanPhamService();
        ArrayList<SanPham> list = service.getAllSanPham();
        hienThiTable(list);
    }

    private void hienThiTable(ArrayList<SanPham> list) {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[]{
            "Mã SP", "Tên SP", "Mã hãng", "Giá bán", "Số lượng", "Mô tả"
        });

        for (SanPham sp : list) {
            model.addRow(new Object[]{
                sp.getMaSP(),
                sp.getTenSP(),
                sp.getMaHang(),
                 formatGiaBan(sp.getGiaBan()),
                sp.getSoLuong(),
                sp.getMoTa()
            });
        }

        tblSanPham.setModel(model);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        tblSanPham.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tblSanPham.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        tblSanPham.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        tblSanPham.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);

        tblSanPham.getColumnModel().getColumn(0).setPreferredWidth(70);
        tblSanPham.getColumnModel().getColumn(1).setPreferredWidth(220);
        tblSanPham.getColumnModel().getColumn(2).setPreferredWidth(90);
        tblSanPham.getColumnModel().getColumn(3).setPreferredWidth(120);
        tblSanPham.getColumnModel().getColumn(4).setPreferredWidth(90);
        tblSanPham.getColumnModel().getColumn(5).setPreferredWidth(280);
    }

    private void timKiemSanPham() {
        String keyword = txtTimKiem.getText().trim().toLowerCase();

        SanPhamService service = new SanPhamService();
        ArrayList<SanPham> list = service.getAllSanPham();
        ArrayList<SanPham> ketQua = new ArrayList<>();

        if (keyword.isEmpty()) {
            hienThiTable(list);
            return;
        }

        for (SanPham sp : list) {
            String moTa = sp.getMoTa() == null ? "" : sp.getMoTa();

            if (String.valueOf(sp.getMaSP()).contains(keyword)
                    || sp.getTenSP().toLowerCase().contains(keyword)
                    || String.valueOf(sp.getMaHang()).contains(keyword)
                    || String.valueOf(sp.getGiaBan()).contains(keyword)
                    || String.valueOf(sp.getSoLuong()).contains(keyword)
                    || moTa.toLowerCase().contains(keyword)) {
                ketQua.add(sp);
            }
        }

        hienThiTable(ketQua);
    }

    private void suaSanPham() {
        int row = tblSanPham.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm cần sửa!");
            return;
        }

        int maSP = Integer.parseInt(tblSanPham.getValueAt(row, 0).toString());
        String tenSP = tblSanPham.getValueAt(row, 1).toString();
        int maHang = Integer.parseInt(tblSanPham.getValueAt(row, 2).toString());
        double giaBan = Double.parseDouble(tblSanPham.getValueAt(row, 3).toString());
        int soLuong = Integer.parseInt(tblSanPham.getValueAt(row, 4).toString());
        String moTa = tblSanPham.getValueAt(row, 5).toString();

        SanPham sp = new SanPham(maSP, tenSP, maHang, giaBan, soLuong, moTa);

        SuaSanPham1 dialog = new SuaSanPham1(this, true, sp);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);

        loadSanPham();
    }

    private void xoaSanPham() {
        int row = tblSanPham.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm cần xóa!");
            return;
        }

        int maSP = Integer.parseInt(tblSanPham.getValueAt(row, 0).toString());

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc muốn xóa sản phẩm này?",
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            SanPhamService service = new SanPhamService();

            if (service.xoaSanPham(maSP)) {
                JOptionPane.showMessageDialog(this, "Xóa sản phẩm thành công!");
                loadSanPham();
            } else {
                JOptionPane.showMessageDialog(this, "Xóa sản phẩm thất bại!");
            }
        }
    }
private String formatGiaBan(double giaBan) {
    DecimalFormat df = new DecimalFormat("#");
    return df.format(giaBan);
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
            new QLSP().setVisible(true);
        });
    }
}