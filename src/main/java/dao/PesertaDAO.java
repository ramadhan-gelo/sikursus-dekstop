package dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import config.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.SQLException;
import model.Peserta;

public class PesertaDAO {

    // Method untuk mengambil semua data peserta dari database (SELECT)
    public List<Peserta> getAll() throws SQLException {
        List<Peserta> daftarPeserta = new ArrayList<>();
        String sql = "SELECT * FROM peserta ORDER BY id_peserta ASC";

        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                // Menggunakan constructor kosong + setter agar urutan aman
                Peserta peserta = new Peserta();
                peserta.setIdPeserta(rs.getInt("id_peserta"));
                peserta.setNim(rs.getString("nim"));
                peserta.setNama(rs.getString("nama"));
                peserta.setJenisKelamin(rs.getString("jenis_kelamin"));
                peserta.setProdi(rs.getString("prodi"));
                peserta.setNoHp(rs.getString("no_hp"));
                peserta.setEmail(rs.getString("email"));

                daftarPeserta.add(peserta);
            }
        }
        return daftarPeserta;
    }

    // Method untuk menyimpan data peserta (INSERT)[cite: 27]
    public boolean insert(Peserta p) throws SQLException {
        String sql = "INSERT INTO peserta "
                + "(nim, nama, jenis_kelamin, prodi, no_hp, email) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, p.getNim());
            ps.setString(2, p.getNama());
            ps.setString(3, p.getJenisKelamin());
            ps.setString(4, p.getProdi());
            ps.setString(5, p.getNoHp());
            ps.setString(6, p.getEmail());

            return ps.executeUpdate() > 0;
        }
    }
}