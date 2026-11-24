package view;

import dao.PesertaDAO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import model.Peserta;
import report.ReportService;
import util.ThemeBurgundy;

/**
 * Form Master Data Peserta bertema Burgundy.
 */
public class PesertaFrame extends JFrame {

    private JTextField txtNim;
    private JTextField txtNama;
    private JRadioButton rbLaki;
    private JRadioButton rbPerempuan;
    private JTextField txtProdi;
    private JTextField txtNoHp;
    private JTextField txtEmail;
    private JTextField txtCari;

    private JButton btnSimpan;
    private JButton btnUbah;
    private JButton btnHapus;
    private JButton btnReset;
    private JButton btnCetak;
    private JButton btnCari;

    private JTable tblPeserta;
    private DefaultTableModel tableModel;
    private final PesertaDAO pesertaDAO = new PesertaDAO();
    private int selectedIdPeserta = -1;

    public PesertaFrame() {
        initComponents();
        loadDataPeserta();
    }

    private void initComponents() {
        setTitle("SIKURSUS Desktop - Master Data Peserta");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(720, 680);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(ThemeBurgundy.BG_MAIN);

        // --- Header Banner Burgundy ---
        ThemeBurgundy.BurgundyBannerPanel header = new ThemeBurgundy.BurgundyBannerPanel(0);
        header.setPreferredSize(new Dimension(720, 64));
        header.setLayout(new BorderLayout());
        JLabel lblHeader = new JLabel("Master Data Peserta", SwingConstants.CENTER);
        lblHeader.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 22));
        lblHeader.setForeground(Color.WHITE);
        header.add(lblHeader, BorderLayout.CENTER);
        add(header, BorderLayout.NORTH);

        // --- Panel Konten Utama ---
        JPanel content = new JPanel(new BorderLayout(0, 12));
        content.setBackground(ThemeBurgundy.BG_MAIN);
        content.setBorder(BorderFactory.createEmptyBorder(14, 18, 16, 18));

        // 1. Form Peserta
        JPanel formCard = new JPanel(new GridBagLayout());
        formCard.setBackground(ThemeBurgundy.BG_WHITE);
        formCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ThemeBurgundy.BORDER_LIGHT, 1),
                BorderFactory.createEmptyBorder(14, 18, 14, 18)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 6, 5, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        JLabel lblFormTitle = new JLabel("Form Peserta");
        lblFormTitle.setFont(ThemeBurgundy.FONT_BODY_BOLD);
        lblFormTitle.setForeground(ThemeBurgundy.BURGUNDY_PRIMARY);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        formCard.add(lblFormTitle, gbc);
        gbc.gridwidth = 1;

        txtNim = createStyledTextField();
        txtNama = createStyledTextField();
        txtProdi = createStyledTextField();
        txtNoHp = createStyledTextField();
        txtEmail = createStyledTextField();

        rbLaki = new JRadioButton("Laki-laki");
        rbPerempuan = new JRadioButton("Perempuan");
        rbLaki.setBackground(ThemeBurgundy.BG_WHITE);
        rbPerempuan.setBackground(ThemeBurgundy.BG_WHITE);
        rbLaki.setFont(ThemeBurgundy.FONT_BODY);
        rbPerempuan.setFont(ThemeBurgundy.FONT_BODY);
        rbLaki.setSelected(true);
        ButtonGroup bgJk = new ButtonGroup();
        bgJk.add(rbLaki);
        bgJk.add(rbPerempuan);

        JPanel jkPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        jkPanel.setOpaque(false);
        jkPanel.add(rbLaki);
        jkPanel.add(rbPerempuan);

        addFormRow(formCard, gbc, 1, "NIM", txtNim);
        addFormRow(formCard, gbc, 2, "NAMA", txtNama);
        addFormRow(formCard, gbc, 3, "Jenis kelamin", jkPanel);
        addFormRow(formCard, gbc, 4, "Program Studi", txtProdi);
        addFormRow(formCard, gbc, 5, "Nomor HP", txtNoHp);
        addFormRow(formCard, gbc, 6, "Email", txtEmail);

        // Tombol Aksi
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 4));
        btnPanel.setOpaque(false);
        btnSimpan = createActionBtn("Simpan", true);
        btnUbah = createActionBtn("Ubah", false);
        btnHapus = createActionBtn("Hapus", false);
        btnReset = createActionBtn("Reset", false);
        btnCetak = createActionBtn("Cetak", false);

        btnPanel.add(btnSimpan);
        btnPanel.add(btnUbah);
        btnPanel.add(btnHapus);
        btnPanel.add(btnReset);
        btnPanel.add(btnCetak);

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 6, 2, 6);
        formCard.add(btnPanel, gbc);

        // 2. Panel Data Peserta & Tabel
        JPanel tableCard = new JPanel(new BorderLayout(0, 8));
        tableCard.setBackground(ThemeBurgundy.BG_WHITE);
        tableCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ThemeBurgundy.BORDER_LIGHT, 1),
                BorderFactory.createEmptyBorder(12, 16, 14, 16)
        ));

        JPanel topTablePanel = new JPanel(new BorderLayout(0, 8));
        topTablePanel.setOpaque(false);

        JLabel lblTableTitle = new JLabel("Data Peserta");
        lblTableTitle.setFont(ThemeBurgundy.FONT_BODY_BOLD);
        lblTableTitle.setForeground(ThemeBurgundy.BURGUNDY_PRIMARY);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        searchPanel.setOpaque(false);
        JLabel lblCari = new JLabel("Pencarian");
        lblCari.setFont(ThemeBurgundy.FONT_BODY);
        txtCari = createStyledTextField();
        txtCari.setPreferredSize(new Dimension(220, 30));
        btnCari = createActionBtn("Cari", true);
        btnCari.setPreferredSize(new Dimension(76, 30));
        searchPanel.add(lblCari);
        searchPanel.add(txtCari);
        searchPanel.add(btnCari);

        topTablePanel.add(lblTableTitle, BorderLayout.NORTH);
        topTablePanel.add(searchPanel, BorderLayout.SOUTH);

        String[] cols = {"ID", "NIM", "Nama", "JK", "Prodi", "No. HP", "Email"};
        tableModel = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblPeserta = new JTable(tableModel);
        tblPeserta.setFont(ThemeBurgundy.FONT_SMALL);
        tblPeserta.setRowHeight(24);
        tblPeserta.setSelectionBackground(ThemeBurgundy.BURGUNDY_PRIMARY);
        tblPeserta.setSelectionForeground(Color.WHITE);
        tblPeserta.setGridColor(ThemeBurgundy.BORDER_LIGHT);

        JTableHeader tblHeader = tblPeserta.getTableHeader();
        tblHeader.setOpaque(true);
        tblHeader.setBackground(ThemeBurgundy.BURGUNDY_PRIMARY);
        tblHeader.setForeground(Color.WHITE);
        tblHeader.setFont(ThemeBurgundy.FONT_BODY_BOLD);
        tblHeader.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                JLabel lbl = (JLabel) super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);
                lbl.setOpaque(true);
                lbl.setBackground(ThemeBurgundy.BURGUNDY_PRIMARY);
                lbl.setForeground(Color.WHITE);
                lbl.setFont(ThemeBurgundy.FONT_BODY_BOLD);
                lbl.setHorizontalAlignment(SwingConstants.CENTER);
                lbl.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, Color.WHITE));
                return lbl;
            }
        });

        JScrollPane scrollPane = new JScrollPane(tblPeserta);
        scrollPane.setPreferredSize(new Dimension(640, 200));

        tableCard.add(topTablePanel, BorderLayout.NORTH);
        tableCard.add(scrollPane, BorderLayout.CENTER);

        content.add(formCard, BorderLayout.NORTH);
        content.add(tableCard, BorderLayout.CENTER);
        add(content, BorderLayout.CENTER);

        wireEvents();
    }

    private JTextField createStyledTextField() {
        JTextField tf = new JTextField();
        tf.setFont(ThemeBurgundy.FONT_BODY);
        tf.setPreferredSize(new Dimension(340, 30));
        tf.setBorder(ThemeBurgundy.createInputBorder(ThemeBurgundy.BORDER_INPUT, 1));
        return tf;
    }

    private JButton createActionBtn(String text, boolean primary) {
        JButton btn;
        if (primary) {
            btn = ThemeBurgundy.createRoundedButton(
                    text, ThemeBurgundy.BURGUNDY_PRIMARY, ThemeBurgundy.BURGUNDY_HOVER, Color.WHITE, 6
            );
        } else {
            btn = ThemeBurgundy.createRoundedButton(
                    text, ThemeBurgundy.BG_SECONDARY_BTN, ThemeBurgundy.BG_SECONDARY_HOVER,
                    ThemeBurgundy.TEXT_PRIMARY, 6
            );
        }
        btn.setFont(ThemeBurgundy.FONT_SMALL);
        btn.setPreferredSize(new Dimension(100, 30));
        return btn;
    }

    private void addFormRow(JPanel panel, GridBagConstraints gbc, int row, String label, Component comp) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.25;
        JLabel lbl = new JLabel(label);
        lbl.setFont(ThemeBurgundy.FONT_BODY);
        lbl.setForeground(ThemeBurgundy.TEXT_PRIMARY);
        panel.add(lbl, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.75;
        panel.add(comp, gbc);
    }

    private void loadDataPeserta() {
        try {
            List<Peserta> list = pesertaDAO.getAll();
            tampilkanKeTabel(list);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(
                    this,
                    "Gagal memuat data peserta dari database: " + ex.getMessage(),
                    "Error Database",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void tampilkanKeTabel(List<Peserta> list) {
        tableModel.setRowCount(0);
        for (Peserta p : list) {
            tableModel.addRow(new Object[]{
                p.getIdPeserta(),
                p.getNim(),
                p.getNama(),
                p.getJenisKelamin(),
                p.getProdi(),
                p.getNoHp(),
                p.getEmail()
            });
        }
    }

    private void wireEvents() {
        tblPeserta.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = tblPeserta.getSelectedRow();
                if (row >= 0) {
                    selectedIdPeserta = Integer.parseInt(String.valueOf(tableModel.getValueAt(row, 0)));
                    txtNim.setText(String.valueOf(tableModel.getValueAt(row, 1)));
                    txtNama.setText(String.valueOf(tableModel.getValueAt(row, 2)));
                    String jk = String.valueOf(tableModel.getValueAt(row, 3));
                    if ("L".equalsIgnoreCase(jk)) {
                        rbLaki.setSelected(true);
                    } else {
                        rbPerempuan.setSelected(true);
                    }
                    txtProdi.setText(String.valueOf(tableModel.getValueAt(row, 4)));
                    txtNoHp.setText(String.valueOf(tableModel.getValueAt(row, 5)));
                    txtEmail.setText(String.valueOf(tableModel.getValueAt(row, 6)));
                }
            }
        });

        btnSimpan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (txtNim.getText().trim().isEmpty() || txtNama.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(PesertaFrame.this, "NIM dan Nama wajib diisi!");
                    return;
                }
                String jk = rbLaki.isSelected() ? "L" : "P";
                Peserta p = new Peserta(
                        txtNim.getText().trim(),
                        txtNama.getText().trim(),
                        jk,
                        txtProdi.getText().trim(),
                        txtNoHp.getText().trim(),
                        txtEmail.getText().trim()
                );
                try {
                    if (pesertaDAO.insert(p)) {
                        JOptionPane.showMessageDialog(PesertaFrame.this, "Data peserta berhasil disimpan.");
                        loadDataPeserta();
                        resetForm();
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(PesertaFrame.this, "Gagal menyimpan data: " + ex.getMessage());
                }
            }
        });

        btnUbah.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedIdPeserta <= 0) {
                    JOptionPane.showMessageDialog(PesertaFrame.this, "Pilih baris peserta terlebih dahulu.");
                    return;
                }
                String jk = rbLaki.isSelected() ? "L" : "P";
                Peserta p = new Peserta(
                        selectedIdPeserta,
                        txtNama.getText().trim(),
                        txtNoHp.getText().trim(),
                        txtNim.getText().trim(),
                        jk,
                        txtProdi.getText().trim(),
                        txtEmail.getText().trim()
                );
                try {
                    if (pesertaDAO.update(p)) {
                        JOptionPane.showMessageDialog(PesertaFrame.this, "Data peserta berhasil diubah.");
                        loadDataPeserta();
                        resetForm();
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(PesertaFrame.this, "Gagal mengubah data: " + ex.getMessage());
                }
            }
        });

        btnHapus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedIdPeserta <= 0) {
                    JOptionPane.showMessageDialog(PesertaFrame.this, "Pilih baris yang ingin dihapus.");
                    return;
                }
                int konfirmasi = JOptionPane.showConfirmDialog(
                        PesertaFrame.this,
                        "Yakin ingin menghapus data peserta ini?",
                        "Konfirmasi Hapus",
                        JOptionPane.YES_NO_OPTION
                );
                if (konfirmasi == JOptionPane.YES_OPTION) {
                    try {
                        if (pesertaDAO.delete(selectedIdPeserta)) {
                            JOptionPane.showMessageDialog(PesertaFrame.this, "Data peserta berhasil dihapus.");
                            loadDataPeserta();
                            resetForm();
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(PesertaFrame.this, "Gagal menghapus data: " + ex.getMessage());
                    }
                }
            }
        });

        btnReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetForm();
                loadDataPeserta();
            }
        });

        btnCari.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String query = txtCari.getText().trim();
                try {
                    if (query.isEmpty()) {
                        loadDataPeserta();
                    } else {
                        tampilkanKeTabel(pesertaDAO.search(query));
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(PesertaFrame.this, "Gagal mencari data: " + ex.getMessage());
                }
            }
        });

        btnCetak.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ReportService.tampilkanLaporanPeserta();
            }
        });
    }

    private void resetForm() {
        selectedIdPeserta = -1;
        txtNim.setText("");
        txtNama.setText("");
        rbLaki.setSelected(true);
        txtProdi.setText("");
        txtNoHp.setText("");
        txtEmail.setText("");
        txtCari.setText("");
        tblPeserta.clearSelection();
    }
}