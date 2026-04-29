package com.phonestore.ui;

import com.phonestore.dao.CategoryDAO;
import com.phonestore.model.Category;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CategoryPanel extends JPanel {
    private final CategoryDAO dao = new CategoryDAO();
    private final DefaultTableModel model = UIUtils.nonEditableModel("ID", "Tên danh mục", "Mô tả");
    private final JTable table = new JTable(model);
    private final JTextField txtName = new JTextField();
    private final JTextField txtDescription = new JTextField();
    private Integer selectedId = null;

    public CategoryPanel() {
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
        panel.add(new JLabel("Tên danh mục"));
        panel.add(txtName);
        panel.add(new JLabel("Mô tả"));
        panel.add(txtDescription);

        JButton btnAdd = new JButton("Thêm");
        JButton btnUpdate = new JButton("Sửa");
        JButton btnDelete = new JButton("Xóa");
        JButton btnClear = new JButton("Làm mới");

        btnAdd.addActionListener(e -> addCategory());
        btnUpdate.addActionListener(e -> updateCategory());
        btnDelete.addActionListener(e -> deleteCategory());
        btnClear.addActionListener(e -> clearForm());

        panel.add(btnAdd);
        panel.add(btnUpdate);
        panel.add(btnDelete);
        panel.add(btnClear);
        return panel;
    }

    private void loadData() {
        model.setRowCount(0);
        List<Category> list = dao.getAll();
        for (Category c : list) {
            model.addRow(new Object[]{c.getId(), c.getName(), c.getDescription()});
        }
    }

    private void addCategory() {
        try {
            dao.insert(txtName.getText().trim(), txtDescription.getText().trim());
            UIUtils.showSuccess(this, "Đã thêm danh mục");
            clearForm();
            loadData();
        } catch (Exception e) {
            UIUtils.showError(this, e);
        }
    }

    private void updateCategory() {
        if (selectedId == null) return;
        try {
            dao.update(selectedId, txtName.getText().trim(), txtDescription.getText().trim());
            UIUtils.showSuccess(this, "Đã cập nhật danh mục");
            clearForm();
            loadData();
        } catch (Exception e) {
            UIUtils.showError(this, e);
        }
    }

    private void deleteCategory() {
        if (selectedId == null) return;
        if (!UIUtils.confirm(this, "Xóa danh mục đã chọn?")) return;
        try {
            dao.delete(selectedId);
            UIUtils.showSuccess(this, "Đã xóa danh mục");
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
            txtName.setText(model.getValueAt(row, 1).toString());
            txtDescription.setText(String.valueOf(model.getValueAt(row, 2)));
        }
    }

    private void clearForm() {
        selectedId = null;
        txtName.setText("");
        txtDescription.setText("");
        table.clearSelection();
    }
}
