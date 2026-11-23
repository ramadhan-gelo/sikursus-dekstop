package view;

import dao.UserDAO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.SQLException;
import java.util.Arrays;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import model.User;
import util.ThemeBurgundy;
import util.UserSession;

/**
 * Form Login SIKURSUS Desktop bertema Burgundy (sesuai Panduan Praktikum P11).
 */
public class LoginFrame extends JFrame {

    private final UserDAO userDAO = new UserDAO();

    private JTextField txtUsername;
    private JPasswordField pwdPassword;
    private JCheckBox chkShowPassword;
    private JButton btnLogin;
    private JButton btnKeluar;
    private char defaultEchoChar;

    public LoginFrame() {
        initComponents();
        setLocationRelativeTo(null);
        getRootPane().setDefaultButton(btnLogin);
    }

    private void initComponents() {
        setTitle("Login - SIKURSUS Desktop");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(720, 440);
        setResizable(false);
        setLayout(new BorderLayout());

        // --- Panel Kiri (Branding Burgundy) ---
        ThemeBurgundy.BurgundyBannerPanel leftPanel = new ThemeBurgundy.BurgundyBannerPanel(0);
        leftPanel.setPreferredSize(new Dimension(320, 440));
        leftPanel.setLayout(new GridBagLayout());

        JLabel lblBrand = new JLabel("SIKURSUS-DESKTOP", SwingConstants.CENTER);
        lblBrand.setForeground(Color.WHITE);
        lblBrand.setFont(new Font("Segoe UI", Font.BOLD, 24));
        leftPanel.add(lblBrand);

        // --- Panel Kanan (Form Login) ---
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(ThemeBurgundy.BG_WHITE);
        rightPanel.setBorder(BorderFactory.createEmptyBorder(38, 42, 36, 42));
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

        JLabel lblWelcome = new JLabel("Selamat Datang Kembali");
        lblWelcome.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblWelcome.setForeground(ThemeBurgundy.TEXT_PRIMARY);
        lblWelcome.setAlignmentX(LEFT_ALIGNMENT);

        JLabel lblSubtitle = new JLabel("Silakan masuk ke akun Anda untuk melanjutkan");
        lblSubtitle.setFont(ThemeBurgundy.FONT_SMALL);
        lblSubtitle.setForeground(ThemeBurgundy.TEXT_SECONDARY);
        lblSubtitle.setAlignmentX(LEFT_ALIGNMENT);

        JLabel lblUser = new JLabel("Username");
        lblUser.setFont(ThemeBurgundy.FONT_BODY_BOLD);
        lblUser.setForeground(ThemeBurgundy.TEXT_PRIMARY);
        lblUser.setAlignmentX(LEFT_ALIGNMENT);

        txtUsername = new JTextField();
        txtUsername.setFont(ThemeBurgundy.FONT_BODY);
        txtUsername.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        txtUsername.setPreferredSize(new Dimension(300, 38));
        txtUsername.setAlignmentX(LEFT_ALIGNMENT);
        txtUsername.setBorder(ThemeBurgundy.createInputBorder(ThemeBurgundy.BURGUNDY_PRIMARY, 2));

        JLabel lblPass = new JLabel("Password");
        lblPass.setFont(ThemeBurgundy.FONT_BODY_BOLD);
        lblPass.setForeground(ThemeBurgundy.TEXT_PRIMARY);
        lblPass.setAlignmentX(LEFT_ALIGNMENT);

        pwdPassword = new JPasswordField();
        pwdPassword.setFont(ThemeBurgundy.FONT_BODY);
        pwdPassword.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        pwdPassword.setPreferredSize(new Dimension(300, 38));
        pwdPassword.setAlignmentX(LEFT_ALIGNMENT);
        pwdPassword.setBorder(ThemeBurgundy.createInputBorder(ThemeBurgundy.BORDER_INPUT, 1));
        defaultEchoChar = pwdPassword.getEchoChar();

        txtUsername.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                txtUsername.setBorder(ThemeBurgundy.createInputBorder(ThemeBurgundy.BURGUNDY_PRIMARY, 2));
            }

            @Override
            public void focusLost(FocusEvent e) {
                txtUsername.setBorder(ThemeBurgundy.createInputBorder(ThemeBurgundy.BORDER_INPUT, 1));
            }
        });

        pwdPassword.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                pwdPassword.setBorder(ThemeBurgundy.createInputBorder(ThemeBurgundy.BURGUNDY_PRIMARY, 2));
            }

            @Override
            public void focusLost(FocusEvent e) {
                pwdPassword.setBorder(ThemeBurgundy.createInputBorder(ThemeBurgundy.BORDER_INPUT, 1));
            }
        });

        chkShowPassword = new JCheckBox("Tampilkan Password");
        chkShowPassword.setBackground(ThemeBurgundy.BG_WHITE);
        chkShowPassword.setForeground(ThemeBurgundy.TEXT_SECONDARY);
        chkShowPassword.setFont(ThemeBurgundy.FONT_SMALL);
        chkShowPassword.setFocusPainted(false);
        chkShowPassword.setAlignmentX(LEFT_ALIGNMENT);
        chkShowPassword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (chkShowPassword.isSelected()) {
                    pwdPassword.setEchoChar((char) 0);
                } else {
                    pwdPassword.setEchoChar(defaultEchoChar);
                }
            }
        });

        btnLogin = ThemeBurgundy.createRoundedButton(
                "MASUK KE AKUN",
                ThemeBurgundy.BURGUNDY_PRIMARY,
                ThemeBurgundy.BURGUNDY_HOVER,
                Color.WHITE,
                8
        );
        btnLogin.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btnLogin.setPreferredSize(new Dimension(300, 40));
        btnLogin.setAlignmentX(LEFT_ALIGNMENT);

        btnKeluar = ThemeBurgundy.createRoundedButton(
                "KELUAR APLIKASI",
                ThemeBurgundy.BG_SECONDARY_BTN,
                ThemeBurgundy.BG_SECONDARY_HOVER,
                ThemeBurgundy.TEXT_SECONDARY,
                8
        );
        btnKeluar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btnKeluar.setPreferredSize(new Dimension(300, 40));
        btnKeluar.setAlignmentX(LEFT_ALIGNMENT);

        rightPanel.add(lblWelcome);
        rightPanel.add(Box.createVerticalStrut(4));
        rightPanel.add(lblSubtitle);
        rightPanel.add(Box.createVerticalStrut(24));
        rightPanel.add(lblUser);
        rightPanel.add(Box.createVerticalStrut(6));
        rightPanel.add(txtUsername);
        rightPanel.add(Box.createVerticalStrut(14));
        rightPanel.add(lblPass);
        rightPanel.add(Box.createVerticalStrut(6));
        rightPanel.add(pwdPassword);
        rightPanel.add(Box.createVerticalStrut(10));
        rightPanel.add(chkShowPassword);
        rightPanel.add(Box.createVerticalStrut(20));
        rightPanel.add(btnLogin);
        rightPanel.add(Box.createVerticalStrut(10));
        rightPanel.add(btnKeluar);

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnLoginActionPerformed(e);
            }
        });

        btnKeluar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.CENTER);
    }

    private void btnLoginActionPerformed(ActionEvent evt) {
        String username = txtUsername.getText().trim();
        char[] password = pwdPassword.getPassword();

        if (username.isEmpty() || password.length == 0) {
            JOptionPane.showMessageDialog(this, "Username dan password wajib diisi.");
            return;
        }

        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        btnLogin.setEnabled(false);

        try {
            User user = userDAO.authenticate(username, password);
            if (user == null) {
                JOptionPane.showMessageDialog(
                        this,
                        "Login gagal. Periksa username, password, atau status user.",
                        "Login Gagal",
                        JOptionPane.WARNING_MESSAGE
                );
                pwdPassword.setText("");
                pwdPassword.requestFocus();
                return;
            }

            UserSession.setCurrentUser(user);
            dispose();
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new DashboardFrame().setVisible(true);
                }
            });
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Tidak dapat mengakses database: " + ex.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        } finally {
            Arrays.fill(password, '\0');
            btnLogin.setEnabled(true);
            setCursor(Cursor.getDefaultCursor());
        }
    }
}
