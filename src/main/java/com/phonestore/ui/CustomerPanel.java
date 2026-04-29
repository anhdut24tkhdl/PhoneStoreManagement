package com.phonestore.ui;

import com.phonestore.dao.CustomerDAO;
import com.phonestore.model.Customer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class CustomerPanel extends JPanel {
    private final CustomerDAO dao = new CustomerDAO();
    private final DefaultTableModel model = UIUtils.nonEditableModel("ID", "Họ tên", "SĐT", "Địa chỉ", "Điểm tích lũy");
    private final JTable table = new JTable(model);

    private final JTextField txtFullName = new JTextField();
    private final JTextField txtPhone = new JTextField();
    private final JTextField txtAddress = new JTextField();
    private final JTextField txtPoints = new JTextField("0");
    private Integer selectedId = null;

    public CustomerPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        add(buildForm(), BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);

        table.setRowHeight(28);
        table.getSelectionModel().addListSelectionListener(e -> fillFormFromSelectedRow());
        loadData();
    }

    private JPanel buildForm() {
        JPanel panel = new JPanel(new GridLayout(2, 4, 8, 8));
        panel.add(new JLabel("Họ tên"));
        panel.add(txtFullName);
        panel.add(new JLabel("SĐT"));
        panel.add(txtPhone);

        panel.add(new JLabel("Địa chỉ"));
        panel.add(txtAddress);
        panel.add(new JLabel("Điểm tích lũy"));
        panel.add(txtPoints);

        JButton btnAdd = new JButton("Thêm");
        JButton btnUpdate = new JButton("Sửa");
        JButton btnDelete = new JButton("Xóa");
        JButton btnClear = new JButton("Làm mới");

        btnAdd.addActionListener(e -> addItem());
        btnUpdate.addActionListener(e -> updateItem());
        btnDelete.addActionListener(e -> deleteItem());
        btnClear.addActionListener(e -> clearForm());

        JPanel buttons = new JPanel(new GridLayout(1, 4, 8, 8));
        buttons.add(btnAdd);
        buttons.add(btnUpdate);
        buttons.add(btnDelete);
        buttons.add(btnClear);

        JPanel wrapper = new JPanel(new BorderLayout(8, 8));
        wrapper.add(panel, BorderLayout.CENTER);
        wrapper.add(buttons, BorderLayout.SOUTH);
        return wrapper;
    }

    private void loadData() {
        model.setRowCount(0);
        for (Customer c : dao.getAll()) {
            model.addRow(new Object[]{c.getId(), c.getFullName(), c.getPhone(), c.getAddress(), c.getLoyaltyPoints()});
        }
    }

    private Customer readForm() {
        Customer item = new Customer();
        item.setId(selectedId == null ? 0 : selectedId);
        item.setFullName(txtFullName.getText().trim());
        item.setPhone(txtPhone.getText().trim());
        item.setAddress(txtAddress.getText().trim());
        item.setLoyaltyPoints(Integer.parseInt(txtPoints.getText().trim()));
        return item;
    }

    private void addItem() {
        try {
            dao.insert(readForm());
            UIUtils.showSuccess(this, "Đã thêm khách hàng");
            clearForm();
            loadData();
        } catch (Exception e) {
            UIUtils.showError(this, e);
        }
    }

    private void updateItem() {
        if (selectedId == null) return;
        try {
            dao.update(readForm());
            UIUtils.showSuccess(this, "Đã cập nhật khách hàng");
            clearForm();
            loadData();
        } catch (Exception e) {
            UIUtils.showError(this, e);
        }
    }

    private void deleteItem() {
        if (selectedId == null) return;
        if (!UIUtils.confirm(this, "Xóa khách hàng đã chọn?")) return;
        try {
            dao.delete(selectedId);
            UIUtils.showSuccess(this, "Đã xóa khách hàng");
            clearForm();
            loadData();
        } catch (Exception e) {
            UIUtils.showError(this, e);
        }
    }

    private void fillFormFromSelectedRow() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            selectedId = Integer.parseInt(model.getValueAt(row, 0).toString());
            txtFullName.setText(String.valueOf(model.getValueAt(row, 1)));
            txtPhone.setText(String.valueOf(model.getValueAt(row, 2)));
            txtAddress.setText(String.valueOf(model.getValueAt(row, 3)));
            txtPoints.setText(String.valueOf(model.getValueAt(row, 4)));
        }
    }

    private void clearForm() {
        selectedId = null;
        txtFullName.setText("");
        txtPhone.setText("");
        txtAddress.setText("");
        txtPoints.setText("0");
        table.clearSelection();
    }
}
