package view;

import java.awt.BorderLayout;
import java.awt.Color;
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
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;
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
    private TableRowSorter<DefaultTableModel> rowSorter;
    private int nextId = 6;

    public PesertaFrame() {
        initComponents();
        loadSampleData();
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
        btnCetak = createActionBtn("Cetak Semua", false);

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
        tblHeader.setBackground(ThemeBurgundy.BURGUNDY_PRIMARY);
        tblHeader.setForeground(Color.WHITE);
        tblHeader.setFont(ThemeBurgundy.FONT_BODY_BOLD);

        rowSorter = new TableRowSorter<DefaultTableModel>(tableModel);
        tblPeserta.setRowSorter(rowSorter);

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

    private void addFormRow(JPanel panel, GridBagConstraints gbc, int row, String label, java.awt.Component comp) {
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

    private void loadSampleData() {
        tableModel.addRow(new Object[]{1, "252001", "Ahmad Fauzan", "L", "Informatika", "081234567801", "ahmad@example.com"});
        tableModel.addRow(new Object[]{2, "252002", "Siti Rahmah", "P", "Informatika", "081234567802", "siti@example.com"});
    }

    private void wireEvents() {
        tblPeserta.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int viewRow = tblPeserta.getSelectedRow();
                if (viewRow >= 0) {
                    int modelRow = tblPeserta.convertRowIndexToModel(viewRow);
                    txtNim.setText(String.valueOf(tableModel.getValueAt(modelRow, 1)));
                    txtNama.setText(String.valueOf(tableModel.getValueAt(modelRow, 2)));
                    String jk = String.valueOf(tableModel.getValueAt(modelRow, 3));
                    if ("L".equalsIgnoreCase(jk)) {
                        rbLaki.setSelected(true);
                    } else {
                        rbPerempuan.setSelected(true);
                    }
                    txtProdi.setText(String.valueOf(tableModel.getValueAt(modelRow, 4)));
                    txtNoHp.setText(String.valueOf(tableModel.getValueAt(modelRow, 5)));
                    txtEmail.setText(String.valueOf(tableModel.getValueAt(modelRow, 6)));
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
                tableModel.addRow(new Object[]{
                    nextId++,
                    txtNim.getText().trim(),
                    txtNama.getText().trim(),
                    jk,
                    txtProdi.getText().trim(),
                    txtNoHp.getText().trim(),
                    txtEmail.getText().trim()
                });
                resetForm();
            }
        });

        btnUbah.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int viewRow = tblPeserta.getSelectedRow();
                if (viewRow < 0) {
                    JOptionPane.showMessageDialog(PesertaFrame.this, "Pilih baris peserta terlebih dahulu.");
                    return;
                }
                int modelRow = tblPeserta.convertRowIndexToModel(viewRow);
                String jk = rbLaki.isSelected() ? "L" : "P";
                tableModel.setValueAt(txtNim.getText().trim(), modelRow, 1);
                tableModel.setValueAt(txtNama.getText().trim(), modelRow, 2);
                tableModel.setValueAt(jk, modelRow, 3);
                tableModel.setValueAt(txtProdi.getText().trim(), modelRow, 4);
                tableModel.setValueAt(txtNoHp.getText().trim(), modelRow, 5);
                tableModel.setValueAt(txtEmail.getText().trim(), modelRow, 6);
                resetForm();
            }
        });

        btnHapus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int viewRow = tblPeserta.getSelectedRow();
                if (viewRow < 0) {
                    JOptionPane.showMessageDialog(PesertaFrame.this, "Pilih baris yang ingin dihapus.");
                    return;
                }
                int modelRow = tblPeserta.convertRowIndexToModel(viewRow);
                tableModel.removeRow(modelRow);
                resetForm();
            }
        });

        btnReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetForm();
            }
        });

        btnCari.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String query = txtCari.getText().trim();
                if (query.isEmpty()) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + query));
                }
            }
        });

        btnCetak.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    tblPeserta.print();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(PesertaFrame.this, "Gagal mencetak: " + ex.getMessage());
                }
            }
        });
    }

    private void resetForm() {
        txtNim.setText("");
        txtNama.setText("");
        rbLaki.setSelected(true);
        txtProdi.setText("");
        txtNoHp.setText("");
        txtEmail.setText("");
        tblPeserta.clearSelection();
    }
}
