package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import controller.CSVHandler;

/**
 * Kelas Model untuk merepresentasikan data Jadwal Perjalanan Kereta Api.
 *
 * Mechanism: Menerapkan konsep Enkapsulasi penuh dengan menyembunyikan atribut 
 * secara private dan menyediakan akses terbatas melalui getter/setter. Kelas ini 
 * juga mengimplementasikan interface Printable untuk memenuhi kontrak polimorfisme.
 */
public class Schedule implements Printable {
    private String idJadwal;
    private Train kereta;
    private Station asal;
    private Station tujuan;
    private LocalDateTime berangkat;
    private LocalDateTime tiba;
    private int sisaKursi;
    // Atribut untuk keperluan audit 
    private String lastModifiedBy; 
    private LocalDateTime lastModifiedAt;

    public Schedule() {
        // Constructor default untuk keperluan deserialisasi atau inisialisasi kosong
    }
    
    /**
     * Mechanism: Constructor untuk menginisialisasi objek Schedule baru di memori 
     * dengan dependensi objek Train dan Station (prinsip Agregasi/Asosiasi antar kelas).
     */
    public Schedule(String idJadwal, Train kereta, Station asal, Station tujuan, 
                    LocalDateTime berangkat, LocalDateTime tiba, int sisaKursi) {
        this.idJadwal = idJadwal;
        this.kereta = kereta;
        this.asal = asal;
        this.tujuan = tujuan;
        this.berangkat = berangkat;
        this.tiba = tiba;
        this.sisaKursi = sisaKursi;
    }

    /**
     * Mechanism: Mengembalikan ID unik dari jadwal.
     * @return idJadwal dalam bentuk String.
     */
    public String getIdJadwal() { return idJadwal; }
    public void setIdJadwal(String idJadwal) { this.idJadwal = idJadwal; }

    /**
     * Mechanism: Mengembalikan referensi objek Kereta yang terikat pada jadwal ini.
     * @return objek Train.
     */
    public Train getKereta() { return kereta; }
    public void setKereta(Train kereta) { this.kereta = kereta; }

    /**
     * Mechanism: Mengembalikan referensi objek Stasiun Asal keberangkatan.
     * @return objek Station.
     */
    public Station getAsal() { return asal; }
    public void setAsal(Station asal) { this.asal = asal; }

    /**
     * Mechanism: Mengembalikan referensi objek Stasiun Tujuan akhir.
     * @return objek Station.
     */
    public Station getTujuan() { return tujuan; }
    public void setTujuan(Station tujuan) { this.tujuan = tujuan; }

    /**
     * Mechanism: Mengembalikan data waktu keberangkatan kereta.
     * @return objek LocalDateTime keberangkatan.
     */
    public LocalDateTime getBerangkat() { return berangkat; }
    public void setBerangkat(LocalDateTime berangkat) { this.berangkat = berangkat; }

    /**
     * Mechanism: Mengembalikan data waktu kedatangan kereta di stasiun tujuan.
     * @return objek LocalDateTime kedatangan.
     */
    public LocalDateTime getTiba() { return tiba; }
    public void setTiba(LocalDateTime tiba) { this.tiba = tiba; }

    /**
     * Mechanism: Mengembalikan sisa kapasitas kursi yang tersedia pada jadwal ini.
     * @return jumlah kursi dalam bentuk integer.
     */
    public int getSisaKursi() { return sisaKursi; }
    public void setSisaKursi(int sisaKursi) { this.sisaKursi = sisaKursi; }

    /**
     * Mechanism : Menyimpan informasi audit terakhir yang memodifikasi jadwal ini, termasuk ID staf dan timestamp.
     * @param staffID ID staf yang melakukan modifikasi dalam bentuk String.
     */
    public void setAuditInfo(String staffID) {
        this.lastModifiedBy = staffID;
        this.lastModifiedAt = LocalDateTime.now();
    }
    
    /**
     * Mechanism: Meng-override metode dari interface Printable untuk menghasilkan ringkasan jadwal formal.
     * * @return String berupa visualisasi data manifes operasional jadwal.
     */
    @Override
    public String printDetail() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== DETIL JADWAL OPERASIONAL [").append(idJadwal).append("] ===\n");
        sb.append("Oleh Staff ID : ").append(lastModifiedBy != null ? lastModifiedBy : "SYSTEM/LOADER").append("\n");
        sb.append("Waktu Audit   : ").append(lastModifiedAt != null ? lastModifiedAt.format(CSVHandler.DTF) : "-").append("\n");
        sb.append("Kereta        : ").append(kereta != null ? kereta.getNamaKereta() + " [" + kereta.getKodeKereta() + "]" : "-").append("\n");
        sb.append("Rute Perjalanan: ").append(asal != null ? asal.getNamaStasiun() : "-").append(" -> ").append(tujuan != null ? tujuan.getNamaStasiun() : "-").append("\n");
        sb.append("Waktu Berangkat: ").append(berangkat != null ? berangkat.format(CSVHandler.DTF) : "-").append("\n");
        sb.append("Waktu Tiba     : ").append(tiba != null ? tiba.format(CSVHandler.DTF) : "-").append("\n");
        sb.append("Ketersediaan   : ").append(sisaKursi).append(" Kursi Tersisa\n");
        sb.append("==========================================");
        return sb.toString();
    }
}