package com.phonestore.ui;

import com.phonestore.dao.SupplierDAO;
import com.phonestore.model.Supplier;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class SupplierPanel extends JPanel {
    private final SupplierDAO dao = new SupplierDAO();
    private final DefaultTableModel model = UIUtils.nonEditableModel("ID", "Tên NCC", "SĐT", "Địa chỉ", "Email");
    private final JTable table = new JTable(model);

    private final JTextField txtName = new JTextField();
    private final JTextField txtPhone = new JTextField();
    private final JTextField txtAddress = new JTextField();
    private final JTextField txtEmail = new JTextField();
    private Integer selectedId = null;

    public SupplierPanel() {
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
        panel.add(new JLabel("Tên NCC"));
        panel.add(txtName);
        panel.add(new JLabel("SĐT"));
        panel.add(txtPhone);

        panel.add(new JLabel("Địa chỉ"));
        panel.add(txtAddress);
        panel.add(new JLabel("Email"));
        panel.add(txtEmail);

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
        for (Supplier c : dao.getAll()) {
            model.addRow(new Object[]{c.getId(), c.getName(), c.getPhone(), c.getAddress(), c.getEmail()});
        }
    }

    private Supplier readForm() {
        Supplier item = new Supplier();
        item.setId(selectedId == null ? 0 : selectedId);
        item.setName(txtName.getText().trim());
        item.setPhone(txtPhone.getText().trim());
        item.setAddress(txtAddress.getText().trim());
        item.setEmail(txtEmail.getText().trim());
        return item;
    }

    private void addItem() {
        try {
            dao.insert(readForm());
            UIUtils.showSuccess(this, "Đã thêm nhà cung cấp");
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
            UIUtils.showSuccess(this, "Đã cập nhật nhà cung cấp");
            clearForm();
            loadData();
        } catch (Exception e) {
            UIUtils.showError(this, e);
        }
    }

    private void deleteItem() {
        if (selectedId == null) return;
        if (!UIUtils.confirm(this, "Xóa nhà cung cấp đã chọn?")) return;
        try {
            dao.delete(selectedId);
            UIUtils.showSuccess(this, "Đã xóa nhà cung cấp");
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
            txtName.setText(String.valueOf(model.getValueAt(row, 1)));
            txtPhone.setText(String.valueOf(model.getValueAt(row, 2)));
            txtAddress.setText(String.valueOf(model.getValueAt(row, 3)));
            txtEmail.setText(String.valueOf(model.getValueAt(row, 4)));
        }
    }

    private void clearForm() {
        selectedId = null;
        txtName.setText("");
        txtPhone.setText("");
        txtAddress.setText("");
        txtEmail.setText("");
        table.clearSelection();
    }
}
