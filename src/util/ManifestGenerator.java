package util;

import java.util.List;
import java.time.format.DateTimeFormatter;
import model.Schedule;
import model.Ticket;

/**
 * Kelas utilitas untuk menghasilkan manifest perjalanan.
 *
 * Mechanism: Menyusun data tiket menjadi teks manifest.
 */
public class ManifestGenerator {
    /**
     * Mechanism: Menghasilkan manifest berdasarkan jadwal dan daftar tiket.
     * Menggunakan StringBuilder untuk efisiensi memori saat menyusun teks.
     *
     * @param s data jadwal dalam bentuk Schedule.
     * @param listTiket daftar tiket dalam bentuk List<Ticket>.
     * @return hasil manifest dalam bentuk String.
     */
    public String generate(Schedule s, List<Ticket> listTiket) {
        // Validasi input agar tidak terjadi NullPointerException
        if (s == null || listTiket == null) {
            return "ERROR: Data jadwal atau daftar tiket tidak valid.";
        }

        StringBuilder manifest = new StringBuilder();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        // --- Menyusun Header Manifes ---
        manifest.append("=================================================\n");
        manifest.append("           MANIFES PERJALANAN KERETA API         \n");
        manifest.append("=================================================\n");
        manifest.append("ID Jadwal      : ").append(s.getIdJadwal()).append("\n");
        manifest.append("Kereta         : ").append(s.getKereta() != null ? s.getKereta().getKodeKereta() : "-").append("\n");
        manifest.append("Rute           : ").append(s.getAsal() != null ? s.getAsal().getKodeStasiun() : "-")
                .append(" -> ")
                .append(s.getTujuan() != null ? s.getTujuan().getKodeStasiun() : "-").append("\n");
        manifest.append("Waktu Berangkat: ").append(s.getBerangkat() != null ? s.getBerangkat().format(formatter) : "-").append("\n");
        manifest.append("=================================================\n");
        
        // --- Menyusun Daftar Penumpang ---
        manifest.append("DAFTAR PENUMPANG:\n");
        manifest.append("-------------------------------------------------\n");
        manifest.append(String.format("%-7s | %-16s | %-20s\n", "KURSI", "NIK", "NAMA PENUMPANG"));
        manifest.append("-------------------------------------------------\n");

        int totalPenumpang = 0;

        // Menggunakan getS() untuk mendapatkan jadwal dari tiket, sesuai
        for (Ticket tiket : listTiket) {
            if (tiket != null && tiket.getS() != null && 
                tiket.getS().getIdJadwal().equals(s.getIdJadwal())) {
                
                String kursi = tiket.getNomorKursi() != null ? tiket.getNomorKursi() : "-";
                // Menggunakan getP() untuk mendapatkan penumpang dari tiket
                String nik = (tiket.getP() != null && tiket.getP().getNik() != null) ? tiket.getP().getNik() : "-";
                String nama = (tiket.getP() != null && tiket.getP().getNama() != null) ? tiket.getP().getNama() : "-";
                
                manifest.append(String.format("%-7s | %-16s | %-20s\n", kursi, nik, nama));
                totalPenumpang++;
            }
        }

        // --- Menyusun Footer ---
        manifest.append("-------------------------------------------------\n");
        manifest.append("Total Penumpang: ").append(totalPenumpang).append(" orang\n");
        manifest.append("=================================================\n");

        return manifest.toString();
    }
}