package controller;

import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;
import model.Schedule;
import util.ScheduleConflictException;

/**
 * Controller untuk mengelola operasional Jadwal (Schedule).
 * Menerapkan Logika Bisnis, Koleksi, dan Penanganan Exception.
 */
public class ScheduleController {
    // Koleksi bertipe Generik List untuk menyimpan data di memori (Koleksi)
    private List<Schedule> listJadwal;

    public ScheduleController() {
        this.listJadwal = new ArrayList<>();
    }

    public List<Schedule> getListJadwal() {
        return listJadwal;
    }

    /**
     * FUNGSI UTAMA "GET SCHEDULE"
     * Mechanism: Mencari objek Schedule berdasarkan ID Jadwal.
     * Sangat berguna untuk integrasi dengan modul pemesanan tiket Anggota 4.
     * * @param idJadwal ID yang dicari dalam bentuk String.
     * @return Objek Schedule jika ditemukan, null jika tidak ada.
     */
    public Schedule getScheduleById(String idJadwal) {
        if (idJadwal == null || listJadwal == null) return null;
        
        for (Schedule s : listJadwal) {
            if (s != null && s.getIdJadwal().equalsIgnoreCase(idJadwal)) {
                return s; // Mengembalikan objek jadwal utuh
            }
        }
        return null;
    }

    /**
     * LOGIKA CONFLICT CHECK (Pengecekan Jadwal Tumpang Tindih)
     * * @return true jika waktu bentrok, false jika aman.
     */
    public boolean cekBentrok(Schedule baru, List<Schedule> list) {
        if (baru == null || list == null || baru.getKereta() == null) return false;

        for (Schedule s : list) {
            if (s == null || s.getKereta() == null) continue;

            // Pengecekan hanya berlaku jika kereta yang digunakan sama
            if (baru.getKereta().getKodeKereta().equals(s.getKereta().getKodeKereta())) {
                LocalDateTime bStart = baru.getBerangkat();
                LocalDateTime bEnd = baru.getTiba();
                LocalDateTime sStart = s.getBerangkat();
                LocalDateTime sEnd = s.getTiba();

                if (bStart != null && bEnd != null && sStart != null && sEnd != null) {
                    // Rumus Overlap: Jadwal Baru mulai sebelum Jadwal Lama selesai 
                    // DAN Jadwal Baru selesai setelah Jadwal Lama mulai.
                    if (bStart.isBefore(sEnd) && bEnd.isAfter(sStart)) {
                        return true; 
                    }
                }
            }
        }
        return false;
    }

    /**
     * MENAMBAHKAN JADWAL BARU (Dengan Pelemparan Exception)
     * * @throws ScheduleConflictException jika validasi aturan bisnis dilanggar.
     */
    public void addSchedule(Schedule s) throws ScheduleConflictException {
        if (s == null) {
            throw new ScheduleConflictException("Data jadwal tidak boleh kosong (null).");
        }

        // Aturan Bisnis 1: Validasi logika waktu mendasar
        if (s.getBerangkat() == null || s.getTiba() == null || !s.getTiba().isAfter(s.getBerangkat())) {
            throw new ScheduleConflictException("Validasi Gagal: Waktu tiba harus diatur setelah waktu keberangkatan.");
        }

        // Aturan Bisnis 2: Validasi bentrok armada via metode cekBentrok
        if (cekBentrok(s, this.listJadwal)) {
            throw new ScheduleConflictException("Konflik Jadwal: Kereta " + s.getKereta().getKodeKereta() + " sudah terikat pada jadwal lain di rentang waktu tersebut.");
        }

        // Jika semua validasi aman, masukkan ke koleksi memori
        this.listJadwal.add(s);
    }

    /**
     * MANAGEMEN EXCEPTION & PERSISTENSI (Sisi Pemrosesan Aplikasi)
     * Menjalankan fungsi simpan dengan perlindungan blok Try-Catch terpusat.
     */
    public void prosesInputJadwal(Schedule jadwalBaru) {
        try {
            // Jalankan penambahan (Bisa memicu ScheduleConflictException)
            this.addSchedule(jadwalBaru);
            
            System.out.println("[SUKSES] Jadwal baru berhasil divalidasi dan ditambahkan ke sistem.");
            
        } catch (ScheduleConflictException e) {
            // Menangkap custom exception yang kita rancang khusus
            System.err.println("[OPERASI DITOLAK] " + e.getMessage());
            
        } catch (Exception e) {
            // Menangkap error umum atau I/O tak terduga agar program tidak crash fatal
            System.err.println("[ERROR SISTEM] Gagal menyimpan data karena masalah sistem: " + e.getMessage());
        }
    }
}