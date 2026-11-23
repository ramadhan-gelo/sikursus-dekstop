package app;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import view.LoginFrame;

/**
 * Titik masuk utama aplikasi SIKURSUS Desktop (sesuai Panduan Praktikum P11).
 */
public class Main {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            // Gunakan Look and Feel default
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginFrame().setVisible(true);
            }
        });
    }
}
