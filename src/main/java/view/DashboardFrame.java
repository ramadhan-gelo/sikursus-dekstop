package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.function.Supplier;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import model.User;
import util.ThemeBurgundy;
import util.UserSession;
import util.WindowManager;

/**
 * Dashboard utama SIKURSUS Desktop bertema Burgundy sesuai Panduan Praktikum P11.
 */
public class DashboardFrame extends JFrame {

    private JLabel lblUser;
    private JLabel lblRole;
    private JLabel lblGreeting;
    private JLabel lblSection;

    private JMenu menuMaster;
    private JMenu menuTransaksi;
    private JMenu menuLaporan;
    private JMenu menuKeluar;

    private JMenuItem miPeserta;
    private JMenuItem miInstruktur;
    private JMenuItem miKursus;
    private JMenuItem miKelas;
    private JMenuItem miPendaftaran;
    private JMenuItem miPembayaran;
    private JMenuItem miLaporanPeserta;
    private JMenuItem miLaporanPendaftaran;
    private JMenuItem miLogout;
    private JMenuItem miTutup;

    private JPanel gridPanel;
    private JPopupMenu popupDashboard;

    public DashboardFrame() {
        initComponents();
        setLocationRelativeTo(null);
        applySession();
    }

    private void initComponents() {
        setTitle("Dashboard - SIKURSUS Desktop");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(860, 620);
        setMinimumSize(new Dimension(800, 580));
        setLayout(new BorderLayout());
        getContentPane().setBackground(ThemeBurgundy.BG_MAIN);

        setJMenuBar(buildMenuBar());
        add(buildHeaderBar(), BorderLayout.NORTH);

        JPanel mainContent = new JPanel();
        mainContent.setBackground(ThemeBurgundy.BG_MAIN);
        mainContent.setBorder(BorderFactory.createEmptyBorder(18, 28, 18, 28));
        mainContent.setLayout(new BoxLayout(mainContent, BoxLayout.Y_AXIS));

        mainContent.add(buildHeroBanner());
        mainContent.add(Box.createVerticalStrut(20));

        lblSection = new JLabel("Akses Cepat Modul");
        lblSection.setFont(ThemeBurgundy.FONT_SECTION);
        lblSection.setForeground(ThemeBurgundy.TEXT_PRIMARY);
        lblSection.setAlignmentX(LEFT_ALIGNMENT);
        mainContent.add(lblSection);
        mainContent.add(Box.createVerticalStrut(12));

        gridPanel = new JPanel(new GridLayout(2, 3, 14, 14));
        gridPanel.setOpaque(false);
        gridPanel.setAlignmentX(LEFT_ALIGNMENT);
        gridPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 260));
        mainContent.add(gridPanel);
        mainContent.add(Box.createVerticalStrut(16));

        JLabel lblTips = new JLabel(
                "Tips: Klik kartu di atas untuk membuka modul, atau klik kanan di area kosong untuk menu pintasan."
        );
        lblTips.setFont(ThemeBurgundy.FONT_SMALL_ITALIC);
        lblTips.setForeground(ThemeBurgundy.TEXT_MUTED);
        lblTips.setAlignmentX(LEFT_ALIGNMENT);
        mainContent.add(lblTips);

        popupDashboard = buildPopupMenu();
        mainContent.setComponentPopupMenu(popupDashboard);

        add(mainContent, BorderLayout.CENTER);
    }

    private JMenuBar buildMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(ThemeBurgundy.BG_WHITE);
        menuBar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, ThemeBurgundy.BORDER_LIGHT));

        menuMaster = new JMenu("Master");
        menuMaster.setFont(ThemeBurgundy.FONT_BODY_BOLD);

        miPeserta = new JMenuItem("Data Peserta",
                loadIconOrFallback("/icons/peserta.png", "P", new Color(41, 98, 168)));
        miInstruktur = new JMenuItem("Data Instruktur",
                loadIconOrFallback("/icons/instruktur.png", "I", new Color(34, 139, 87)));
        miKursus = new JMenuItem("Data Kursus",
                loadIconOrFallback("/icons/kursus.png", "K", ThemeBurgundy.BURGUNDY_PRIMARY));
        miKelas = new JMenuItem("Data Kelas",
                loadIconOrFallback("/icons/kelas.png", "K", new Color(115, 65, 165)));

        miPeserta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                WindowManager.open(PesertaFrame.class, new Supplier<PesertaFrame>() {
                    @Override
                    public PesertaFrame get() {
                        return new PesertaFrame();
                    }
                });
            }
        });
        miInstruktur.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                WindowManager.open(InstrukturFrame.class, new Supplier<InstrukturFrame>() {
                    @Override
                    public InstrukturFrame get() {
                        return new InstrukturFrame();
                    }
                });
            }
        });
        miKursus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                WindowManager.open(KursusFrame.class, new Supplier<KursusFrame>() {
                    @Override
                    public KursusFrame get() {
                        return new KursusFrame();
                    }
                });
            }
        });
        miKelas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                WindowManager.open(KelasFrame.class, new Supplier<KelasFrame>() {
                    @Override
                    public KelasFrame get() {
                        return new KelasFrame();
                    }
                });
            }
        });

        menuMaster.add(miPeserta);
        menuMaster.add(miInstruktur);
        menuMaster.add(miKursus);
        menuMaster.add(miKelas);

        menuTransaksi = new JMenu("Transaksi");
        menuTransaksi.setFont(ThemeBurgundy.FONT_BODY_BOLD);
        miPendaftaran = new JMenuItem("Pendaftaran");
        miPembayaran = new JMenuItem("Pembayaran");
        miPendaftaran.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(DashboardFrame.this,
                        "Modul Pendaftaran sedang dikembangkan pada tahap project berikutnya.");
            }
        });
        miPembayaran.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(DashboardFrame.this,
                        "Modul Pembayaran sedang dikembangkan pada tahap project berikutnya.");
            }
        });
        menuTransaksi.add(miPendaftaran);
        menuTransaksi.add(miPembayaran);

        menuLaporan = new JMenu("Laporan");
        menuLaporan.setFont(ThemeBurgundy.FONT_BODY_BOLD);
        miLaporanPeserta = new JMenuItem("Laporan Peserta");
        miLaporanPendaftaran = new JMenuItem("Laporan Pendaftaran");
        miLaporanPeserta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(DashboardFrame.this,
                        "Modul laporan diaktifkan pada Pertemuan 12 dengan Jaspersoft Studio.");
            }
        });
        miLaporanPendaftaran.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(DashboardFrame.this,
                        "Modul laporan diaktifkan pada Pertemuan 12 dengan Jaspersoft Studio.");
            }
        });
        menuLaporan.add(miLaporanPeserta);
        menuLaporan.add(miLaporanPendaftaran);

        menuKeluar = new JMenu("Keluar");
        menuKeluar.setFont(ThemeBurgundy.FONT_BODY_BOLD);
        miLogout = new JMenuItem("Logout");
        miTutup = new JMenuItem("Tutup Aplikasi");
        miLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                miLogoutActionPerformed();
            }
        });
        miTutup.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                miTutupActionPerformed();
            }
        });
        menuKeluar.add(miLogout);
        menuKeluar.addSeparator();
        menuKeluar.add(miTutup);

        menuBar.add(menuMaster);
        menuBar.add(menuTransaksi);
        menuBar.add(menuLaporan);
        menuBar.add(menuKeluar);

        return menuBar;
    }

    private JPanel buildHeaderBar() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(ThemeBurgundy.BG_WHITE);
        header.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, ThemeBurgundy.BORDER_LIGHT),
                BorderFactory.createEmptyBorder(10, 28, 10, 28)
        ));

        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        left.setOpaque(false);
        JLabel dot = new JLabel("\u25CF");
        dot.setForeground(ThemeBurgundy.BURGUNDY_PRIMARY);
        dot.setFont(new Font("Segoe UI", Font.BOLD, 14));
        JLabel title = new JLabel("SIKURSUS DESKTOP");
        title.setFont(new Font("Segoe UI", Font.BOLD, 15));
        title.setForeground(ThemeBurgundy.TEXT_PRIMARY);
        left.add(dot);
        left.add(title);

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        right.setOpaque(false);

        JLabel lblUserTitle = new JLabel("User:");
        lblUserTitle.setFont(ThemeBurgundy.FONT_SMALL);
        lblUserTitle.setForeground(ThemeBurgundy.TEXT_SECONDARY);

        lblUser = new JLabel("-");
        lblUser.setFont(ThemeBurgundy.FONT_BODY_BOLD);
        lblUser.setForeground(ThemeBurgundy.TEXT_PRIMARY);

        lblRole = new JLabel(" - ");
        lblRole.setOpaque(true);
        lblRole.setBackground(ThemeBurgundy.BURGUNDY_SOFT_BG);
        lblRole.setForeground(ThemeBurgundy.BURGUNDY_PRIMARY);
        lblRole.setFont(ThemeBurgundy.FONT_BADGE);
        lblRole.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));

        JButton btnLogout = new JButton("Logout");
        btnLogout.setFont(ThemeBurgundy.FONT_SMALL);
        btnLogout.setBackground(ThemeBurgundy.BG_WHITE);
        btnLogout.setForeground(ThemeBurgundy.BURGUNDY_PRIMARY);
        btnLogout.setFocusPainted(false);
        btnLogout.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnLogout.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ThemeBurgundy.BURGUNDY_ACCENT, 1),
                BorderFactory.createEmptyBorder(4, 12, 4, 12)
        ));
        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                miLogoutActionPerformed();
            }
        });

        right.add(lblUserTitle);
        right.add(lblUser);
        right.add(lblRole);
        right.add(btnLogout);

        header.add(left, BorderLayout.WEST);
        header.add(right, BorderLayout.EAST);
        return header;
    }

    private JPanel buildHeroBanner() {
        ThemeBurgundy.BurgundyBannerPanel banner = new ThemeBurgundy.BurgundyBannerPanel(14);
        banner.setLayout(new BoxLayout(banner, BoxLayout.Y_AXIS));
        banner.setBorder(BorderFactory.createEmptyBorder(22, 24, 22, 24));
        banner.setMaximumSize(new Dimension(Integer.MAX_VALUE, 96));
        banner.setAlignmentX(LEFT_ALIGNMENT);

        lblGreeting = new JLabel("Selamat Datang!");
        lblGreeting.setFont(new Font("Segoe UI", Font.BOLD, 21));
        lblGreeting.setForeground(Color.WHITE);
        lblGreeting.setAlignmentX(LEFT_ALIGNMENT);

        JLabel lblDesc = new JLabel(
                "Kelola data peserta, instruktur, kursus, jadwal kelas, dan transaksi pelatihan melalui satu panel terpadu."
        );
        lblDesc.setFont(ThemeBurgundy.FONT_SMALL);
        lblDesc.setForeground(new Color(255, 240, 244));
        lblDesc.setAlignmentX(LEFT_ALIGNMENT);

        banner.add(lblGreeting);
        banner.add(Box.createVerticalStrut(6));
        banner.add(lblDesc);
        return banner;
    }

    private void applySession() {
        User user = UserSession.getCurrentUser();
        if (user == null) {
            JOptionPane.showMessageDialog(this, "Session tidak ditemukan. Silakan login ulang.");
            dispose();
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new LoginFrame().setVisible(true);
                }
            });
            return;
        }

        lblUser.setText(user.getUsername());
        lblRole.setText(" " + user.getRole() + " ");
        lblGreeting.setText("Selamat Datang, " + user.getUsername() + "!");

        boolean admin = user.isAdmin();
        miInstruktur.setEnabled(admin);
        miKursus.setEnabled(admin);
        miKelas.setEnabled(admin);

        miPeserta.setEnabled(true);
        menuTransaksi.setEnabled(true);
        menuLaporan.setEnabled(true);

        lblSection.setText(admin
                ? "Akses Cepat Modul (Mode Administrator - Akses Penuh)"
                : "Akses Cepat Modul (Mode Operator - Akses Terbatas)");

        populateCards(admin);
    }

    private void populateCards(boolean admin) {
        gridPanel.removeAll();

        gridPanel.add(createModuleCard(
                "PESERTA", "Data Peserta", "Kelola biodata & riwayat peserta", true,
                new Runnable() {
                    @Override
                    public void run() {
                        miPeserta.doClick();
                    }
                }
        ));
        gridPanel.add(createModuleCard(
                "INSTRUKTUR", "Data Instruktur",
                admin ? "Manajemen pengajar & keahlian" : "Khusus hak akses Administrator",
                admin,
                new Runnable() {
                    @Override
                    public void run() {
                        miInstruktur.doClick();
                    }
                }
        ));
        gridPanel.add(createModuleCard(
                "KURSUS", "Program Kursus",
                admin ? "Daftar materi, kurikulum & biaya" : "Khusus hak akses Administrator",
                admin,
                new Runnable() {
                    @Override
                    public void run() {
                        miKursus.doClick();
                    }
                }
        ));
        gridPanel.add(createModuleCard(
                "KELAS", "Jadwal Kelas",
                admin ? "Atur ruang, kuota & jadwal" : "Khusus hak akses Administrator",
                admin,
                new Runnable() {
                    @Override
                    public void run() {
                        miKelas.doClick();
                    }
                }
        ));
        gridPanel.add(createModuleCard(
                "TRANSAKSI", "Pendaftaran", "Registrasi peserta ke kelas baru", true,
                new Runnable() {
                    @Override
                    public void run() {
                        miPendaftaran.doClick();
                    }
                }
        ));
        gridPanel.add(createModuleCard(
                "KEUANGAN", "Pembayaran", "Verifikasi tagihan & bukti bayar", true,
                new Runnable() {
                    @Override
                    public void run() {
                        miPembayaran.doClick();
                    }
                }
        ));

        gridPanel.revalidate();
        gridPanel.repaint();
    }

    private JPanel createModuleCard(String badgeText, String title, String subtitle,
            boolean unlocked, final Runnable onClick) {
        Color bg = unlocked ? ThemeBurgundy.BG_WHITE : ThemeBurgundy.BG_DISABLED;
        final ThemeBurgundy.RoundedCardPanel card = new ThemeBurgundy.RoundedCardPanel(
                bg, ThemeBurgundy.BORDER_LIGHT, 12
        );
        card.setLayout(new BorderLayout(0, 8));
        card.setBorder(BorderFactory.createEmptyBorder(14, 16, 14, 16));

        JPanel topRow = new JPanel(new BorderLayout());
        topRow.setOpaque(false);

        JLabel lblBadge = new JLabel(" " + badgeText + " ");
        lblBadge.setOpaque(true);
        lblBadge.setFont(ThemeBurgundy.FONT_BADGE);
        lblBadge.setBorder(BorderFactory.createEmptyBorder(3, 7, 3, 7));
        if (unlocked) {
            lblBadge.setBackground(ThemeBurgundy.BURGUNDY_SOFT_BG);
            lblBadge.setForeground(ThemeBurgundy.BURGUNDY_PRIMARY);
        } else {
            lblBadge.setBackground(new Color(224, 218, 221));
            lblBadge.setForeground(ThemeBurgundy.TEXT_SECONDARY);
        }
        topRow.add(lblBadge, BorderLayout.WEST);

        if (unlocked) {
            JLabel lblArrow = new JLabel("\u2192");
            lblArrow.setFont(ThemeBurgundy.FONT_BODY_BOLD);
            lblArrow.setForeground(ThemeBurgundy.TEXT_SECONDARY);
            topRow.add(lblArrow, BorderLayout.EAST);
        } else {
            JLabel lblLock = new JLabel(" TERKUNCI (ADMIN) ");
            lblLock.setOpaque(true);
            lblLock.setBackground(ThemeBurgundy.LOCKED_BG);
            lblLock.setForeground(ThemeBurgundy.LOCKED_TEXT);
            lblLock.setFont(ThemeBurgundy.FONT_BADGE);
            lblLock.setBorder(BorderFactory.createEmptyBorder(3, 6, 3, 6));
            topRow.add(lblLock, BorderLayout.EAST);
        }

        JPanel textPanel = new JPanel();
        textPanel.setOpaque(false);
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblTitle.setForeground(unlocked ? ThemeBurgundy.TEXT_PRIMARY : ThemeBurgundy.TEXT_MUTED);

        JLabel lblSub = new JLabel(subtitle);
        lblSub.setFont(ThemeBurgundy.FONT_SMALL);
        lblSub.setForeground(unlocked ? ThemeBurgundy.TEXT_SECONDARY : ThemeBurgundy.TEXT_MUTED);

        textPanel.add(Box.createVerticalStrut(6));
        textPanel.add(lblTitle);
        textPanel.add(Box.createVerticalStrut(4));
        textPanel.add(lblSub);

        card.add(topRow, BorderLayout.NORTH);
        card.add(textPanel, BorderLayout.CENTER);

        if (unlocked) {
            card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            card.setToolTipText("Klik untuk membuka modul");
            card.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    card.setCardStyle(ThemeBurgundy.BG_WHITE, ThemeBurgundy.BURGUNDY_PRIMARY, 2);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    card.setCardStyle(ThemeBurgundy.BG_WHITE, ThemeBurgundy.BORDER_LIGHT, 1);
                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    if (SwingUtilities.isLeftMouseButton(e) && onClick != null) {
                        onClick.run();
                    }
                }
            });
        } else {
            card.setToolTipText("Modul ini hanya dapat diakses oleh Administrator");
        }

        return card;
    }

    private JPopupMenu buildPopupMenu() {
        JPopupMenu popup = new JPopupMenu();
        JMenuItem miTentang = new JMenuItem("Tentang Aplikasi");
        JMenuItem miRefresh = new JMenuItem("Refresh");
        JMenuItem miPopPeserta = new JMenuItem("Buka Data Peserta");

        miTentang.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(
                        DashboardFrame.this,
                        "SIKURSUS Desktop\nProject PBO - Program Studi Informatika"
                );
            }
        });
        miRefresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                applySession();
            }
        });
        miPopPeserta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                miPeserta.doClick();
            }
        });

        popup.add(miPopPeserta);
        popup.add(miRefresh);
        popup.addSeparator();
        popup.add(miTentang);
        return popup;
    }

    private Icon loadIconOrFallback(String path, String letter, Color fallbackBg) {
        URL url = getClass().getResource(path);
        if (url != null) {
            return new ImageIcon(url);
        }
        return ThemeBurgundy.createLetterIcon(letter, fallbackBg);
    }

    private void miLogoutActionPerformed() {
        int pilihan = JOptionPane.showConfirmDialog(
                this,
                "Logout dari aplikasi?",
                "Konfirmasi Logout",
                JOptionPane.YES_NO_OPTION
        );
        if (pilihan == JOptionPane.YES_OPTION) {
            WindowManager.closeAll();
            UserSession.clear();
            dispose();
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new LoginFrame().setVisible(true);
                }
            });
        }
    }

    private void miTutupActionPerformed() {
        int pilihan = JOptionPane.showConfirmDialog(
                this,
                "Tutup aplikasi SIKURSUS?",
                "Konfirmasi",
                JOptionPane.YES_NO_OPTION
        );
        if (pilihan == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}
