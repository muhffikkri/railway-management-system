package model;

import java.time.LocalDateTime;

/**
 * Kelas untuk merepresentasikan jadwal perjalanan kereta.
 *
 * Mechanism: Menyimpan data jadwal, kereta, dan stasiun terkait.
 */
public class Schedule {
    private String idJadwal;
    private Train kereta;
    private Station asal;
    private Station tujuan;
    private LocalDateTime berangkat;
    private LocalDateTime tiba;
    private int sisaKursi;

    /**
     * Mechanism: Mengambil ID jadwal.
     *
     * @return nilai ID jadwal dalam bentuk String.
     */
    public String getIdJadwal() {
        return idJadwal;
    }

    /**
     * Mechanism: Mengatur ID jadwal.
     *
     * @param idJadwal nilai ID jadwal dalam bentuk String.
     * @return tidak ada.
     */
    public void setIdJadwal(String idJadwal) {
        this.idJadwal = idJadwal;
    }

    /**
     * Mechanism: Mengambil kereta pada jadwal.
     *
     * @return objek kereta dalam bentuk Train.
     */
    public Train getKereta() {
        return kereta;
    }

    /**
     * Mechanism: Mengatur kereta pada jadwal.
     *
     * @param kereta objek kereta dalam bentuk Train.
     * @return tidak ada.
     */
    public void setKereta(Train kereta) {
        this.kereta = kereta;
    }

    /**
     * Mechanism: Mengambil stasiun asal.
     *
     * @return objek stasiun asal dalam bentuk Station.
     */
    public Station getAsal() {
        return asal;
    }

    /**
     * Mechanism: Mengatur stasiun asal.
     *
     * @param asal objek stasiun asal dalam bentuk Station.
     * @return tidak ada.
     */
    public void setAsal(Station asal) {
        this.asal = asal;
    }

    /**
     * Mechanism: Mengambil stasiun tujuan.
     *
     * @return objek stasiun tujuan dalam bentuk Station.
     */
    public Station getTujuan() {
        return tujuan;
    }

    /**
     * Mechanism: Mengatur stasiun tujuan.
     *
     * @param tujuan objek stasiun tujuan dalam bentuk Station.
     * @return tidak ada.
     */
    public void setTujuan(Station tujuan) {
        this.tujuan = tujuan;
    }

    /**
     * Mechanism: Mengambil waktu keberangkatan.
     *
     * @return waktu berangkat dalam bentuk LocalDateTime.
     */
    public LocalDateTime getBerangkat() {
        return berangkat;
    }

    /**
     * Mechanism: Mengatur waktu keberangkatan.
     *
     * @param berangkat waktu berangkat dalam bentuk LocalDateTime.
     * @return tidak ada.
     */
    public void setBerangkat(LocalDateTime berangkat) {
        this.berangkat = berangkat;
    }

    /**
     * Mechanism: Mengambil waktu tiba.
     *
     * @return waktu tiba dalam bentuk LocalDateTime.
     */
    public LocalDateTime getTiba() {
        return tiba;
    }

    /**
     * Mechanism: Mengatur waktu tiba.
     *
     * @param tiba waktu tiba dalam bentuk LocalDateTime.
     * @return tidak ada.
     */
    public void setTiba(LocalDateTime tiba) {
        this.tiba = tiba;
    }

    /**
     * Mechanism: Mengambil sisa kursi pada jadwal.
     *
     * @return jumlah sisa kursi dalam bentuk int.
     */
    public int getSisaKursi() {
        return sisaKursi;
    }

    /**
     * Mechanism: Mengatur sisa kursi pada jadwal.
     *
     * @param sisaKursi jumlah sisa kursi dalam bentuk int.
     * @return tidak ada.
     */
    public void setSisaKursi(int sisaKursi) {
        this.sisaKursi = sisaKursi;
    }
}
