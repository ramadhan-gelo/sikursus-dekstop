package app;

import java.util.ArrayList;
import java.util.List;
import model.Instruktur;
import model.Orang;
import model.Peserta;

public class DemoPolymorphism {

    public static void tampilkanInfo(Orang orang) {
        System.out.println(orang.getInfo());
    }

    public static void main(String[] args) {
        // 1. Pengujian Validasi (Exception Handling)
        try {
            Peserta pSalah = new Peserta(-1, "", "abc", "", "");
        } catch (IllegalArgumentException ex) {
            System.out.println("DITOLAK: " + ex.getMessage());
        }

        // 2. Inisialisasi Objek Valid
        Peserta peserta = new Peserta(1, "Ahmad Fauzan", "081234567801", "252001", "Informatika");
        Instruktur instruktur = new Instruktur(2, "Dina Pratama", "081211110001", "Java Desktop");

        // 3. Simpan dalam List<Orang> (Polimorfisme Koleksi)
        List<Orang> daftarOrang = new ArrayList<>();
        daftarOrang.add(peserta);
        daftarOrang.add(instruktur);

        System.out.println("\n--- OUTPUT LOOP POLIMORFISME ---");
        for (Orang o : daftarOrang) {
            System.out.println(o.getInfo()); // Dynamic Dispatch
        }

        System.out.println("\n--- OUTPUT POLYMORPHIC ARGUMENT ---");
        tampilkanInfo(peserta);
        tampilkanInfo(instruktur);

        System.out.println("\n--- PENGGUNAAN INSTANCEOF TERKONTROL ---");
        for (Orang o : daftarOrang) {
            if (o instanceof Peserta) {
                Peserta p = (Peserta) o;
                System.out.println("NIM khusus peserta ditemukan: " + p.getNim());
            }
        }
    }
}