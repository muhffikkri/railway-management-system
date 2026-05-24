package model;

import controller.CSVHandler;

/**
 * Kelas untuk merepresentasikan tiket perjalanan.
 *
 * Mechanism: Menyimpan informasi tiket, penumpang, dan jadwal terkait.
 */
public class Ticket implements Printable {
    private String noTiket;
    private Passenger p;
    private Schedule s;
    private String nomorKursi;
    private double totalBayar;

    /**
     * Mechanism: Membuat objek Ticket tanpa data awal.
     *
     * @return tidak ada.
     */
    public Ticket() {}

    /**
     * Mechanism: Membuat objek Ticket dengan data lengkap.
     *
     * @param noTiket nilai nomor tiket dalam bentuk String.
     * @param p objek penumpang dalam bentuk Passenger.
     * @param s objek jadwal dalam bentuk Schedule.
     * @param nomorKursi nilai nomor kursi dalam bentuk String.
     * @param totalBayar nilai total pembayaran dalam bentuk double.
     * @return tidak ada.
     */
    public Ticket(String noTiket, Passenger p, Schedule s, String nomorKursi, double totalBayar) {
        this.noTiket = noTiket;
        this.p = p;
        this.s = s;
        this.nomorKursi = nomorKursi;
        this.totalBayar = totalBayar;
    }

    /**
     * Mechanism: Mengambil nomor tiket.
     *
     * @return nilai nomor tiket dalam bentuk String.
     */
    public String getNoTiket() {
        return noTiket;
    }

    /**
     * Mechanism: Mengatur nomor tiket.
     *
     * @param noTiket nilai nomor tiket dalam bentuk String.
     * @return tidak ada.
     */
    public void setNoTiket(String noTiket) {
        this.noTiket = noTiket;
    }

    /**
     * Mechanism: Mengambil penumpang pada tiket.
     *
     * @return objek penumpang dalam bentuk Passenger.
     */
    public Passenger getP() {
        return p;
    }

    /**
     * Mechanism: Mengatur penumpang pada tiket.
     *
     * @param p objek penumpang dalam bentuk Passenger.
     * @return tidak ada.
     */
    public void setP(Passenger p) {
        this.p = p;
    }

    /**
     * Mechanism: Mengambil jadwal pada tiket.
     *
     * @return objek jadwal dalam bentuk Schedule.
     */
    public Schedule getS() {
        return s;
    }

    /**
     * Mechanism: Mengatur jadwal pada tiket.
     *
     * @param s objek jadwal dalam bentuk Schedule.
     * @return tidak ada.
     */
    public void setS(Schedule s) {
        this.s = s;
    }

    /**
     * Mechanism: Mengambil nomor kursi.
     *
     * @return nilai nomor kursi dalam bentuk String.
     */
    public String getNomorKursi() {
        return nomorKursi;
    }

    /**
     * Mechanism: Mengatur nomor kursi.
     *
     * @param nomorKursi nilai nomor kursi dalam bentuk String.
     * @return tidak ada.
     */
    public void setNomorKursi(String nomorKursi) {
        this.nomorKursi = nomorKursi;
    }

    /**
     * Mechanism: Mengambil total pembayaran.
     *
     * @return nilai total bayar dalam bentuk double.
     */
    public double getTotalBayar() {
        return totalBayar;
    }

    /**
     * Mechanism: Mengatur total pembayaran.
     *
     * @param totalBayar nilai total bayar dalam bentuk double.
     * @return tidak ada.
     */
    public void setTotalBayar(double totalBayar) {
        this.totalBayar = totalBayar;
    }

    /**
     * Mechanism: Menampilkan detail tiket dalam format tercetak.
     *
     * @return hasil detail tiket dalam bentuk String.
     */
    @Override
    public String printDetail() {
        StringBuilder sb = new StringBuilder();
        sb.append("=========================================\n");
        sb.append("            TIKET KERETA API\n");
        sb.append("=========================================\n");
        sb.append("No. Tiket    : ").append(noTiket != null ? noTiket : "-").append("\n");
        sb.append("Penumpang    : ").append(p != null ? p.getNama() : "-").append("\n");
        sb.append("NIK          : ").append(p != null ? p.getNik() : "-").append("\n");
        sb.append("Kereta       : ").append(s != null && s.getKereta() != null ? s.getKereta().getNamaKereta() : "-").append("\n");
        sb.append("Rute         : ")
                .append(s != null && s.getAsal() != null ? s.getAsal().getNamaStasiun() : "-")
            .append(" -> ")
                .append(s != null && s.getTujuan() != null ? s.getTujuan().getNamaStasiun() : "-").append("\n");
        sb.append("Berangkat    : ").append(s != null && s.getBerangkat() != null ? s.getBerangkat().format(CSVHandler.DTF) : "-").append("\n");
        sb.append("Tiba         : ").append(s != null && s.getTiba() != null ? s.getTiba().format(CSVHandler.DTF) : "-").append("\n");
        sb.append("Kursi        : ").append(nomorKursi != null ? nomorKursi : "-").append("\n");
        sb.append("Total Bayar  : Rp ").append(String.format("%,.2f", totalBayar)).append("\n");
        sb.append("=========================================\n");
        return sb.toString();
    }

    /**
     * Mechanism: Mengurangi sisa kursi pada jadwal jika masih tersedia.
     *
     * @return status keberhasilan pengurangan dalam bentuk boolean.
     */
    public boolean kurangiSisaKursi() {
        if (s != null && s.getSisaKursi() > 0) {
            s.setSisaKursi(s.getSisaKursi() - 1);
            return true;
        }
        return false;
    }
}
