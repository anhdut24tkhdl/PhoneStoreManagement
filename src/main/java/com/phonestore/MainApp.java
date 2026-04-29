package com.phonestore;

import com.phonestore.db.DatabaseInitializer;
import com.phonestore.ui.MainFrame;

import javax.swing.*;

public class MainApp {
    public static void main(String[] args) {
        DatabaseInitializer.initialize();

        SwingUtilities.invokeLater(() -> {
            setSystemLookAndFeel();
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }

    private static void setSystemLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }
    }
}
