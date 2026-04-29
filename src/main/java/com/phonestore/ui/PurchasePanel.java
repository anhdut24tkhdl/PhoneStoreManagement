package com.phonestore.ui;

import com.phonestore.dao.EmployeeDAO;
import com.phonestore.dao.ProductDAO;
import com.phonestore.dao.PurchaseDAO;
import com.phonestore.dao.SupplierDAO;
import com.phonestore.model.Employee;
import com.phonestore.model.Product;
import com.phonestore.model.Supplier;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.Vector;

public class PurchasePanel extends JPanel {
    private final PurchaseDAO dao = new PurchaseDAO();
    private final SupplierDAO supplierDAO = new SupplierDAO();
    private final EmployeeDAO employeeDAO = new EmployeeDAO();
    private final ProductDAO productDAO = new ProductDAO();

    private final JComboBox<Supplier> cbSupplier = new JComboBox<>();
    private final JComboBox<Employee> cbEmployee = new JComboBox<>();
    private final JComboBox<Product> cbProduct = new JComboBox<>();
    private final JTextField txtDate = new JTextField(LocalDate.now().toString());
    private final JTextField txtQuantity = new JTextField();
    private final JTextField txtUnitPrice = new JTextField();
    private final JTextField txtNote = new JTextField();

    private final DefaultTableModel model = UIUtils.nonEditableModel(
            "ID", "Ngày nhập", "Nhà cung cấp", "Nhân viên", "Sản phẩm", "SL", "Đơn giá", "Thành tiền", "Ghi chú"
    );
    private final JTable table = new JTable(model);

    public PurchasePanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        add(buildForm(), BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);

        table.setRowHeight(28);
        loadCombos();
        loadData();
    }

    private JPanel buildForm() {
        JPanel panel = new JPanel(new GridLayout(3, 4, 8, 8));
        panel.add(new JLabel("Nhà cung cấp"));
        panel.add(cbSupplier);
        panel.add(new JLabel("Nhân viên"));
        panel.add(cbEmployee);

        panel.add(new JLabel("Sản phẩm"));
        panel.add(cbProduct);
        panel.add(new JLabel("Ngày nhập (yyyy-MM-dd)"));
        panel.add(txtDate);

        panel.add(new JLabel("Số lượng"));
        panel.add(txtQuantity);
        panel.add(new JLabel("Đơn giá nhập"));
        panel.add(txtUnitPrice);

        JPanel wrapper = new JPanel(new BorderLayout(8, 8));
        wrapper.add(panel, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new GridLayout(1, 3, 8, 8));
        bottom.add(new JLabel("Ghi chú"));
        bottom.add(txtNote);
        JButton btnCreate = new JButton("Tạo phiếu nhập");
        btnCreate.addActionListener(e -> createPurchase());
        bottom.add(btnCreate);
        wrapper.add(bottom, BorderLayout.SOUTH);
        return wrapper;
    }

    private void loadCombos() {
        cbSupplier.removeAllItems();
        for (Supplier s : supplierDAO.getAll()) cbSupplier.addItem(s);

        cbEmployee.removeAllItems();
        for (Employee e : employeeDAO.getAll()) cbEmployee.addItem(e);

        cbProduct.removeAllItems();
        for (Product p : productDAO.getAll()) cbProduct.addItem(p);
        cbProduct.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Product p = (Product) value;
                String text = p == null ? "" : (p.getId() + " - " + p.getName() + " (Tồn: " + p.getStock() + ")");
                return super.getListCellRendererComponent(list, text, index, isSelected, cellHasFocus);
            }
        });
    }

    private void loadData() {
        model.setRowCount(0);
        for (Vector<Object> row : dao.getAllPurchaseRows()) {
            model.addRow(row);
        }
    }

    private void createPurchase() {
        try {
            Supplier supplier = (Supplier) cbSupplier.getSelectedItem();
            Employee employee = (Employee) cbEmployee.getSelectedItem();
            Product product = (Product) cbProduct.getSelectedItem();

            dao.createPurchase(
                    supplier.getId(),
                    employee == null ? null : employee.getId(),
                    txtDate.getText().trim(),
                    product.getId(),
                    Integer.parseInt(txtQuantity.getText().trim()),
                    Double.parseDouble(txtUnitPrice.getText().trim()),
                    txtNote.getText().trim()
            );

            UIUtils.showSuccess(this, "Đã tạo phiếu nhập và cộng tồn kho");
            txtQuantity.setText("");
            txtUnitPrice.setText("");
            txtNote.setText("");
            loadCombos();
            loadData();
        } catch (Exception e) {
            UIUtils.showError(this, e);
        }
    }
}
