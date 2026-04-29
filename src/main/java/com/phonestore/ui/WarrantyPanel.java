package com.phonestore.ui;

import com.phonestore.dao.CustomerDAO;
import com.phonestore.dao.ProductDAO;
import com.phonestore.dao.WarrantyDAO;
import com.phonestore.model.Customer;
import com.phonestore.model.Product;
import com.phonestore.model.Warranty;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;

public class WarrantyPanel extends JPanel {
    private final WarrantyDAO dao = new WarrantyDAO();
    private final ProductDAO productDAO = new ProductDAO();
    private final CustomerDAO customerDAO = new CustomerDAO();

    private final JComboBox<Product> cbProduct = new JComboBox<>();
    private final JComboBox<Customer> cbCustomer = new JComboBox<>();
    private final JTextField txtIssueDate = new JTextField(LocalDate.now().toString());
    private final JTextField txtExpiryDate = new JTextField(LocalDate.now().plusMonths(12).toString());
    private final JTextField txtStatus = new JTextField("Active");
    private final JTextField txtNote = new JTextField();

    private final DefaultTableModel model = UIUtils.nonEditableModel(
            "ID", "Sản phẩm", "Khách hàng", "Ngày BH", "Hết hạn", "Trạng thái", "Ghi chú"
    );
    private final JTable table = new JTable(model);

    public WarrantyPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        add(buildForm(), BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);

        table.setRowHeight(28);
        loadCombos();
        loadData();
    }

    private JPanel buildForm() {
        JPanel form = new JPanel(new GridLayout(3, 4, 8, 8));
        form.add(new JLabel("Sản phẩm"));
        form.add(cbProduct);
        form.add(new JLabel("Khách hàng"));
        form.add(cbCustomer);

        form.add(new JLabel("Ngày bắt đầu"));
        form.add(txtIssueDate);
        form.add(new JLabel("Ngày hết hạn"));
        form.add(txtExpiryDate);

        form.add(new JLabel("Trạng thái"));
        form.add(txtStatus);
        form.add(new JLabel("Ghi chú"));
        form.add(txtNote);

        JButton btnAdd = new JButton("Thêm bảo hành");
        JButton btnDelete = new JButton("Xóa bản ghi");
        btnAdd.addActionListener(e -> addItem());
        btnDelete.addActionListener(e -> deleteItem());

        JPanel bottom = new JPanel(new GridLayout(1, 2, 8, 8));
        bottom.add(btnAdd);
        bottom.add(btnDelete);

        JPanel wrapper = new JPanel(new BorderLayout(8, 8));
        wrapper.add(form, BorderLayout.CENTER);
        wrapper.add(bottom, BorderLayout.SOUTH);
        return wrapper;
    }

    private void loadCombos() {
        cbProduct.removeAllItems();
        for (Product p : productDAO.getAll()) cbProduct.addItem(p);
        cbProduct.setRenderer(productRenderer());

        cbCustomer.removeAllItems();
        for (Customer c : customerDAO.getAll()) cbCustomer.addItem(c);
    }

    private DefaultListCellRenderer productRenderer() {
        return new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Product p = (Product) value;
                String text = p == null ? "" : (p.getId() + " - " + p.getName());
                return super.getListCellRendererComponent(list, text, index, isSelected, cellHasFocus);
            }
        };
    }

    private void loadData() {
        model.setRowCount(0);
        for (Warranty w : dao.getAll()) {
            model.addRow(new Object[]{
                    w.getId(), w.getProductName(), w.getCustomerName(), w.getIssueDate(), w.getExpiryDate(), w.getStatus(), w.getNote()
            });
        }
    }

    private void addItem() {
        try {
            Product product = (Product) cbProduct.getSelectedItem();
            Customer customer = (Customer) cbCustomer.getSelectedItem();

            dao.insert(product.getId(), customer == null ? null : customer.getId(),
                    txtIssueDate.getText().trim(), txtExpiryDate.getText().trim(),
                    txtStatus.getText().trim(), txtNote.getText().trim());

            UIUtils.showSuccess(this, "Đã thêm phiếu bảo hành");
            loadData();
        } catch (Exception e) {
            UIUtils.showError(this, e);
        }
    }

    private void deleteItem() {
        int row = table.getSelectedRow();
        if (row < 0) return;
        if (!UIUtils.confirm(this, "Xóa phiếu bảo hành đã chọn?")) return;
        try {
            dao.delete(Integer.parseInt(model.getValueAt(row, 0).toString()));
            UIUtils.showSuccess(this, "Đã xóa");
            loadData();
        } catch (Exception e) {
            UIUtils.showError(this, e);
        }
    }
}
