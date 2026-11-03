
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

    // MENAMPILKAN SEMUA DATA PESERTA
    public List<Peserta> getAll() throws SQLException {
        List<Peserta> daftarPeserta = new ArrayList<>();
        String sql = "SELECT * FROM peserta ORDER BY id_peserta ASC";

        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
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

    // MENAMBAHKAN DATA PESERTA
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

    // MENGUBAH DATA PESERTA
    public boolean update(Peserta p) throws SQLException {
        String sql = "UPDATE peserta SET nim=?, nama=?, "
                + "jenis_kelamin=?, prodi=?, no_hp=?, email=? "
                + "WHERE id_peserta=?";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, p.getNim());
            ps.setString(2, p.getNama());
            ps.setString(3, p.getJenisKelamin());
            ps.setString(4, p.getProdi());
            ps.setString(5, p.getNoHp());
            ps.setString(6, p.getEmail());
            ps.setInt(7, p.getIdPeserta());

            return ps.executeUpdate() > 0;
        }
    }

    // MENGHAPUS DATA PESERTA
   public boolean delete(int idPeserta) throws SQLException {
    System.out.println("=== METHOD DELETE DIPANGGIL ===");
    System.out.println("ID yang diterima: " + idPeserta);

    String sql = "DELETE FROM peserta WHERE id_peserta = ?";

    try (Connection conn = Database.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, idPeserta);

        int hasil = ps.executeUpdate();

        System.out.println("Jumlah data terhapus: " + hasil);

        return hasil > 0;
    }
}

    // MENCARI DATA PESERTA
    public List<Peserta> search(String keyword) throws SQLException {
        List<Peserta> daftarPeserta = new ArrayList<>();

        String sql = "SELECT * FROM peserta "
                + "WHERE nim LIKE ? OR nama LIKE ? "
                + "ORDER BY nama ASC";

        String pattern = "%" + keyword.trim() + "%";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, pattern);
            ps.setString(2, pattern);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
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
        }

        return daftarPeserta;
    }
}
