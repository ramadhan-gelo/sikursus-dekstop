package model;

public class Peserta extends Orang {
    private int idPeserta;
    private String nim;
    private String jenisKelamin;
    private String prodi;
    private String email;

    // 1. Constructor Kosong
    public Peserta() {
        super(0, "-", "");
    }

    // 2. Constructor untuk Form / INSERT (6 parameter)
    public Peserta(String nim, String nama, String jenisKelamin, String prodi, String noHp, String email) {
        super(0, nama, noHp);
        this.nim = nim;
        this.jenisKelamin = jenisKelamin;
        this.prodi = prodi;
        this.email = email;
    }

    // 3. Constructor 7 parameter (mengakomodasi file demo: id, nama, noHp, nim, jenisKelamin, prodi, email)
    public Peserta(int idPeserta, String nama, String noHp, String nim, String jenisKelamin, String prodi, String email) {
        super(idPeserta, nama, noHp);
        this.idPeserta = idPeserta;
        this.nim = nim;
        this.jenisKelamin = jenisKelamin;
        this.prodi = prodi;
        this.email = email;
    }

    // Method getInfo untuk file demo
    @Override
    public String getInfo() {
        return "Peserta [ID: " + getId() + ", NIM: " + nim + ", Nama: " + getNama() + ", JK: " + jenisKelamin + ", Prodi: " + prodi + ", No HP: " + getNoHp() + ", Email: " + email + "]";
    }

    // Getter & Setter
    public int getIdPeserta() { return idPeserta; }
    public void setIdPeserta(int idPeserta) { 
        this.idPeserta = idPeserta;
        super.setId(idPeserta);
    }

    public String getNim() { return nim; }
    public void setNim(String nim) { this.nim = nim; }

    public String getNama() { return super.getNama(); }
    public void setNama(String nama) { super.setNama(nama); }

    public String getJenisKelamin() { return jenisKelamin; }
    public void setJenisKelamin(String jenisKelamin) { this.jenisKelamin = jenisKelamin; }

    public String getProdi() { return prodi; }
    public void setProdi(String prodi) { this.prodi = prodi; }

    public String getNoHp() { return super.getNoHp(); }
    public void setNoHp(String noHp) { super.setNoHp(noHp); }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}