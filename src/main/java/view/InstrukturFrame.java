package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import util.ThemeBurgundy;

/**
 * Form Master Instruktur bertema Burgundy.
 */
public class InstrukturFrame extends JFrame {

    public InstrukturFrame() {
        setTitle("Master Instruktur");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(520, 360);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(ThemeBurgundy.BG_MAIN);

        ThemeBurgundy.BurgundyBannerPanel header = new ThemeBurgundy.BurgundyBannerPanel(0);
        header.setPreferredSize(new Dimension(520, 56));
        header.setLayout(new BorderLayout());
        JLabel lblHeader = new JLabel("Master Data Instruktur", SwingConstants.CENTER);
        lblHeader.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblHeader.setForeground(Color.WHITE);
        header.add(lblHeader, BorderLayout.CENTER);
        add(header, BorderLayout.NORTH);

        JLabel lblCenter = new JLabel("Master Instruktur", SwingConstants.CENTER);
        lblCenter.setFont(ThemeBurgundy.FONT_BODY);
        lblCenter.setForeground(ThemeBurgundy.TEXT_PRIMARY);
        add(lblCenter, BorderLayout.CENTER);
    }
}
