package util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.List;
import model.Schedule;
import model.Ticket;

/**
 * Kelas utilitas untuk menghasilkan manifest perjalanan.
 *
 * Mechanism: Menyusun data tiket menjadi teks manifest dan menyimpannya ke file .txt.
 */
public class ManifestGenerator {

    private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    /**
     * Mechanism: Menghasilkan manifest berdasarkan jadwal dan daftar tiket.
     * Menggunakan StringBuilder untuk efisiensi memori saat menyusun teks.
     *
     * @param s data jadwal dalam bentuk Schedule.
     * @param listTiket daftar tiket dalam bentuk List<Ticket>.
     * @return hasil manifest dalam bentuk String.
     */
    public String generate(Schedule s, List<Ticket> listTiket) {
        if (s == null || listTiket == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("=========================================\n");
        sb.append("          MANIFES PENUMPANG\n");
        sb.append("=========================================\n");
        sb.append("Jadwal       : ").append(s.getIdJadwal() != null ? s.getIdJadwal() : "-").append("\n");
        sb.append("Kereta       : ").append(s.getKereta() != null ? s.getKereta().getNamaKereta() : "-").append("\n");
        sb.append("Rute         : ")
                .append(s.getAsal() != null ? s.getAsal().getNamaStasiun() : "-")
                .append(" -> ")
                .append(s.getTujuan() != null ? s.getTujuan().getNamaStasiun() : "-").append("\n");
        sb.append("Berangkat    : ").append(s.getBerangkat() != null ? s.getBerangkat().format(DTF) : "-").append("\n");
        sb.append("Tiba         : ").append(s.getTiba() != null ? s.getTiba().format(DTF) : "-").append("\n");
        sb.append("-----------------------------------------\n");
        sb.append(String.format("%-4s %-15s %-18s %-20s %-6s%n", "No", "No.Tiket", "NIK", "Nama", "Kursi"));
        sb.append("-----------------------------------------\n");

        int no = 1;
        for (Ticket t : listTiket) {
            if (t == null) continue;
            sb.append(String.format("%-4d %-15s %-18s %-20s %-6s%n",
                    no++,
                    t.getNoTiket() != null ? t.getNoTiket() : "-",
                    t.getP() != null && t.getP().getNik() != null ? t.getP().getNik() : "-",
                    t.getP() != null && t.getP().getNama() != null ? t.getP().getNama() : "-",
                    t.getNomorKursi() != null ? t.getNomorKursi() : "-"));
        }

        sb.append("-----------------------------------------\n");
        int totalPenumpang = 0;
        for (Ticket ticket : listTiket) {
            if (ticket != null) {
                totalPenumpang++;
            }
        }
        sb.append("Total Penumpang: ").append(totalPenumpang).append("\n");
        sb.append("=========================================\n");

        String idManifest = s.getIdJadwal() != null ? s.getIdJadwal() : "UNKNOWN";
        String fileName = "manifest_" + idManifest + ".txt";
        try {
            Path outputDir = Paths.get("output");
            Files.createDirectories(outputDir);
            Files.writeString(outputDir.resolve(fileName), sb.toString());
        } catch (IOException e) {
            System.err.println("Gagal menyimpan manifest: " + e.getMessage());
        }

        return sb.toString();
    }
}
