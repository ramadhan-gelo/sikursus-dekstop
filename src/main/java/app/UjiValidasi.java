package app;

import javax.swing.JOptionPane;

public class UjiValidasi {

    public static void harusBerhasil(String nama, String aksi) {
        // Logika pengujian sukses
        System.out.println("BERHASIL: " + nama + " - " + aksi);
    }

    public static void harusDitolak(String nama, String aksi) {
        // Logika pengujian ditolak
        System.out.println("DITOLAK: " + nama + " - " + aksi);
    }

    public static void main(String[] args) {
        // Contoh pemanggilan konfirmasi reset atau validasi
        int pilihan = JOptionPane.showConfirmDialog(
            null, 
            "Kosongkan seluruh input?", 
            "Konfirmasi Reset", 
            JOptionPane.YES_NO_OPTION, 
            JOptionPane.QUESTION_MESSAGE
        );

        if (pilihan == JOptionPane.YES_OPTION) {
            harusBerhasil("Form Pendaftaran", "Reset Data");
        } else {
            harusDitolak("Form Pendaftaran", "Batal Reset");
        }
    }
}