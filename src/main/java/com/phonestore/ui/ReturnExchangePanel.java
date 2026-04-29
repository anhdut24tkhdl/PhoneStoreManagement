package com.phonestore.ui;

import com.phonestore.dao.CustomerDAO;
import com.phonestore.dao.ProductDAO;
import com.phonestore.dao.ReturnExchangeDAO;
import com.phonestore.model.Customer;
import com.phonestore.model.Product;
import com.phonestore.model.ReturnExchange;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;

public class ReturnExchangePanel extends JPanel {
    private final ReturnExchangeDAO dao = new ReturnExchangeDAO();
    private final ProductDAO productDAO = new ProductDAO();
    private final CustomerDAO customerDAO = new CustomerDAO();

    private final JComboBox<Product> cbProduct = new JComboBox<>();
    private final JComboBox<Customer> cbCustomer = new JComboBox<>();
    private final JComboBox<String> cbType = new JComboBox<>(new String[]{"Return", "Exchange"});
    private final JTextField txtDate = new JTextField(LocalDate.now().toString());
    private final JTextField txtReason = new JTextField();
    private final JTextField txtStatus = new JTextField("Pending");

    private final DefaultTableModel model = UIUtils.nonEditableModel(
            "ID", "Sản phẩm", "Khách hàng", "Loại", "Ngày", "Lý do", "Trạng thái"
    );
    private final JTable table = new JTable(model);

    public ReturnExchangePanel() {
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

        form.add(new JLabel("Loại xử lý"));
        form.add(cbType);
        form.add(new JLabel("Ngày"));
        form.add(txtDate);

        form.add(new JLabel("Lý do"));
        form.add(txtReason);
        form.add(new JLabel("Trạng thái"));
        form.add(txtStatus);

        JButton btnAdd = new JButton("Thêm đổi / trả");
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
        cbProduct.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Product p = (Product) value;
                String text = p == null ? "" : (p.getId() + " - " + p.getName());
                return super.getListCellRendererComponent(list, text, index, isSelected, cellHasFocus);
            }
        });

        cbCustomer.removeAllItems();
        for (Customer c : customerDAO.getAll()) cbCustomer.addItem(c);
    }

    private void loadData() {
        model.setRowCount(0);
        for (ReturnExchange r : dao.getAll()) {
            model.addRow(new Object[]{
                    r.getId(), r.getProductName(), r.getCustomerName(), r.getActionType(), r.getActionDate(), r.getReason(), r.getStatus()
            });
        }
    }

    private void addItem() {
        try {
            Product product = (Product) cbProduct.getSelectedItem();
            Customer customer = (Customer) cbCustomer.getSelectedItem();

            dao.insert(product.getId(), customer == null ? null : customer.getId(),
                    cbType.getSelectedItem().toString(),
                    txtDate.getText().trim(),
                    txtReason.getText().trim(),
                    txtStatus.getText().trim());

            UIUtils.showSuccess(this, "Đã thêm bản ghi đổi / trả");
            loadData();
        } catch (Exception e) {
            UIUtils.showError(this, e);
        }
    }

    private void deleteItem() {
        int row = table.getSelectedRow();
        if (row < 0) return;
        if (!UIUtils.confirm(this, "Xóa bản ghi đã chọn?")) return;
        try {
            dao.delete(Integer.parseInt(model.getValueAt(row, 0).toString()));
            UIUtils.showSuccess(this, "Đã xóa");
            loadData();
        } catch (Exception e) {
            UIUtils.showError(this, e);
        }
    }
}
