package com.phonestore.ui;

import com.phonestore.dao.CategoryDAO;
import com.phonestore.dao.ProductDAO;
import com.phonestore.model.Category;
import com.phonestore.model.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ProductPanel extends JPanel {
    private final ProductDAO dao = new ProductDAO();
    private final CategoryDAO categoryDAO = new CategoryDAO();

    private final DefaultTableModel model = UIUtils.nonEditableModel(
            "ID", "Mã", "Tên", "Hãng", "Màu", "Dung lượng", "Giá nhập", "Giá bán", "Tồn kho", "Trạng thái", "Danh mục"
    );
    private final JTable table = new JTable(model);

    private final JTextField txtCode = new JTextField();
    private final JTextField txtName = new JTextField();
    private final JTextField txtBrand = new JTextField();
    private final JTextField txtColor = new JTextField();
    private final JTextField txtStorage = new JTextField();
    private final JTextField txtPurchasePrice = new JTextField();
    private final JTextField txtSalePrice = new JTextField();
    private final JTextField txtStock = new JTextField();
    private final JTextField txtSearch = new JTextField();
    private final JComboBox<String> cbStatus = new JComboBox<>(new String[]{"Available", "Out of stock", "Hidden"});
    private final JComboBox<Category> cbCategory = new JComboBox<>();

    private Integer selectedId = null;

    public ProductPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        add(buildTopPanel(), BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);

        table.setRowHeight(26);
        table.getSelectionModel().addListSelectionListener(e -> fillFormFromSelectedRow());

        loadCategories();
        loadData();
    }

    private JPanel buildTopPanel() {
        JPanel wrapper = new JPanel(new BorderLayout(8, 8));

        JPanel searchPanel = new JPanel(new BorderLayout(8, 8));
        JButton btnSearch = new JButton("Tìm");
        JButton btnReload = new JButton("Tải lại");
        btnSearch.addActionListener(e -> searchData());
        btnReload.addActionListener(e -> loadData());
        searchPanel.add(new JLabel("Tìm sản phẩm"), BorderLayout.WEST);
        searchPanel.add(txtSearch, BorderLayout.CENTER);

        JPanel rightSearch = new JPanel(new GridLayout(1,2,8,8));
        rightSearch.add(btnSearch);
        rightSearch.add(btnReload);
        searchPanel.add(rightSearch, BorderLayout.EAST);

        JPanel form = new JPanel(new GridLayout(4, 6, 8, 8));
        form.add(new JLabel("Mã SP"));
        form.add(txtCode);
        form.add(new JLabel("Tên SP"));
        form.add(txtName);
        form.add(new JLabel("Hãng"));
        form.add(txtBrand);

        form.add(new JLabel("Màu"));
        form.add(txtColor);
        form.add(new JLabel("Dung lượng"));
        form.add(txtStorage);
        form.add(new JLabel("Giá nhập"));
        form.add(txtPurchasePrice);

        form.add(new JLabel("Giá bán"));
        form.add(txtSalePrice);
        form.add(new JLabel("Tồn kho"));
        form.add(txtStock);
        form.add(new JLabel("Trạng thái"));
        form.add(cbStatus);

        form.add(new JLabel("Danh mục"));
        form.add(cbCategory);

        JButton btnAdd = new JButton("Thêm");
        JButton btnUpdate = new JButton("Sửa");
        JButton btnDelete = new JButton("Xóa");
        JButton btnClear = new JButton("Làm mới");
        btnAdd.addActionListener(e -> addProduct());
        btnUpdate.addActionListener(e -> updateProduct());
        btnDelete.addActionListener(e -> deleteProduct());
        btnClear.addActionListener(e -> clearForm());

        form.add(btnAdd);
        form.add(btnUpdate);
        form.add(btnDelete);
        form.add(btnClear);

        wrapper.add(searchPanel, BorderLayout.NORTH);
        wrapper.add(form, BorderLayout.CENTER);
        return wrapper;
    }

    private void loadCategories() {
        cbCategory.removeAllItems();
        for (Category c : categoryDAO.getAll()) {
            cbCategory.addItem(c);
        }
    }

    private void loadData() {
        fillTable(dao.getAll());
    }

    private void searchData() {
        fillTable(dao.search(txtSearch.getText().trim()));
    }

    private void fillTable(List<Product> list) {
        model.setRowCount(0);
        for (Product p : list) {
            model.addRow(new Object[]{
                    p.getId(), p.getCode(), p.getName(), p.getBrand(), p.getColor(), p.getStorage(),
                    p.getPurchasePrice(), p.getSalePrice(), p.getStock(), p.getStatus(), p.getCategoryName()
            });
        }
    }

    private Product readForm() {
        Category category = (Category) cbCategory.getSelectedItem();
        Product p = new Product();
        p.setId(selectedId == null ? 0 : selectedId);
        p.setCode(txtCode.getText().trim());
        p.setName(txtName.getText().trim());
        p.setBrand(txtBrand.getText().trim());
        p.setColor(txtColor.getText().trim());
        p.setStorage(txtStorage.getText().trim());
        p.setPurchasePrice(Double.parseDouble(txtPurchasePrice.getText().trim()));
        p.setSalePrice(Double.parseDouble(txtSalePrice.getText().trim()));
        p.setStock(Integer.parseInt(txtStock.getText().trim()));
        p.setStatus(cbStatus.getSelectedItem().toString());
        p.setCategoryId(category == null ? null : category.getId());
        return p;
    }

    private void addProduct() {
        try {
            dao.insert(readForm());
            UIUtils.showSuccess(this, "Đã thêm sản phẩm");
            clearForm();
            loadData();
        } catch (Exception e) {
            UIUtils.showError(this, e);
        }
    }

    private void updateProduct() {
        if (selectedId == null) return;
        try {
            dao.update(readForm());
            UIUtils.showSuccess(this, "Đã cập nhật sản phẩm");
            clearForm();
            loadData();
        } catch (Exception e) {
            UIUtils.showError(this, e);
        }
    }

    private void deleteProduct() {
        if (selectedId == null) return;
        if (!UIUtils.confirm(this, "Xóa sản phẩm đã chọn?")) return;
        try {
            dao.delete(selectedId);
            UIUtils.showSuccess(this, "Đã xóa sản phẩm");
            clearForm();
            loadData();
        } catch (Exception e) {
            UIUtils.showError(this, e);
        }
    }

    private void fillFormFromSelectedRow() {
        int row = table.getSelectedRow();
        if (row < 0) return;

        selectedId = Integer.parseInt(model.getValueAt(row, 0).toString());
        txtCode.setText(String.valueOf(model.getValueAt(row, 1)));
        txtName.setText(String.valueOf(model.getValueAt(row, 2)));
        txtBrand.setText(String.valueOf(model.getValueAt(row, 3)));
        txtColor.setText(String.valueOf(model.getValueAt(row, 4)));
        txtStorage.setText(String.valueOf(model.getValueAt(row, 5)));
        txtPurchasePrice.setText(String.valueOf(model.getValueAt(row, 6)));
        txtSalePrice.setText(String.valueOf(model.getValueAt(row, 7)));
        txtStock.setText(String.valueOf(model.getValueAt(row, 8)));
        cbStatus.setSelectedItem(String.valueOf(model.getValueAt(row, 9)));

        String categoryName = String.valueOf(model.getValueAt(row, 10));
        for (int i = 0; i < cbCategory.getItemCount(); i++) {
            Category c = cbCategory.getItemAt(i);
            if (c.getName().equals(categoryName)) {
                cbCategory.setSelectedIndex(i);
                break;
            }
        }
    }

    private void clearForm() {
        selectedId = null;
        txtCode.setText("");
        txtName.setText("");
        txtBrand.setText("");
        txtColor.setText("");
        txtStorage.setText("");
        txtPurchasePrice.setText("");
        txtSalePrice.setText("");
        txtStock.setText("");
        cbStatus.setSelectedIndex(0);
        if (cbCategory.getItemCount() > 0) cbCategory.setSelectedIndex(0);
        table.clearSelection();
    }
}
