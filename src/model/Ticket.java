package model;

/**
 * Kelas untuk merepresentasikan tiket perjalanan.
 *
 * Mechanism: Menyimpan informasi tiket, penumpang, dan jadwal terkait.
 */
public class Ticket {
    private String noTiket;
    private Passenger p;
    private Schedule s;
    private String nomorKursi;
    private double totalBayar;

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
}
