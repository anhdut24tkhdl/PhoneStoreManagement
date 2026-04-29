package com.phonestore.ui;

import com.phonestore.dao.StatsDAO;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class DashboardPanel extends JPanel {
    private final StatsDAO statsDAO = new StatsDAO();
    private final JPanel cardsPanel = new JPanel(new GridLayout(0, 3, 16, 16));

    public DashboardPanel() {
        setLayout(new BorderLayout(12, 12));
        setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        JButton refreshButton = new JButton("Làm mới thống kê");
        refreshButton.addActionListener(e -> loadStats());

        JLabel title = new JLabel("Tổng quan hệ thống quản lý cửa hàng điện thoại");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));

        JPanel top = new JPanel(new BorderLayout());
        top.add(title, BorderLayout.WEST);
        top.add(refreshButton, BorderLayout.EAST);

        add(top, BorderLayout.NORTH);
        add(new JScrollPane(cardsPanel), BorderLayout.CENTER);

        loadStats();
    }

    private void loadStats() {
        cardsPanel.removeAll();
        Map<String, String> stats = statsDAO.getDashboardStats();
        for (Map.Entry<String, String> entry : stats.entrySet()) {
            cardsPanel.add(createCard(entry.getKey(), entry.getValue()));
        }
        revalidate();
        repaint();
    }

    private JPanel createCard(String title, String value) {
        JPanel panel = new JPanel(new BorderLayout(8, 8));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 210, 210)),
                BorderFactory.createEmptyBorder(16, 16, 16, 16)
        ));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", Font.PLAIN, 26));

        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(valueLabel, BorderLayout.CENTER);
        return panel;
    }
}
