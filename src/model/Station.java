package model;

/**
 * Kelas untuk merepresentasikan stasiun.
 *
 * Mechanism: Menyimpan informasi identitas stasiun.
 */
public class Station {
    private String kodeStasiun;
    private String namaStasiun;
    private String kota;

    /**
     * Mechanism: Mengambil kode stasiun.
     *
     * @return nilai kode stasiun dalam bentuk String.
     */
    public String getKodeStasiun() {
        return kodeStasiun;
    }

    /**
     * Mechanism: Mengatur kode stasiun.
     *
     * @param kodeStasiun nilai kode stasiun dalam bentuk String.
     * @return tidak ada.
     */
    public void setKodeStasiun(String kodeStasiun) {
        this.kodeStasiun = kodeStasiun;
    }

    /**
     * Mechanism: Mengambil nama stasiun.
     *
     * @return nilai nama stasiun dalam bentuk String.
     */
    public String getNamaStasiun() {
        return namaStasiun;
    }

    /**
     * Mechanism: Mengatur nama stasiun.
     *
     * @param namaStasiun nilai nama stasiun dalam bentuk String.
     * @return tidak ada.
     */
    public void setNamaStasiun(String namaStasiun) {
        this.namaStasiun = namaStasiun;
    }

    /**
     * Mechanism: Mengambil kota stasiun.
     *
     * @return nilai kota dalam bentuk String.
     */
    public String getKota() {
        return kota;
    }

    /**
     * Mechanism: Mengatur kota stasiun.
     *
     * @param kota nilai kota dalam bentuk String.
     * @return tidak ada.
     */
    public void setKota(String kota) {
        this.kota = kota;
    }
}
