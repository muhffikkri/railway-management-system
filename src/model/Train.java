package model;

/**
 * Kelas abstrak untuk merepresentasikan kereta.
 *
 * Mechanism: Menyediakan atribut dasar kereta dan kontrak perhitungan harga.
 */
public abstract class Train {
    private String kodeKereta;
    private String namaKereta;
    private int kapasitas;
    private boolean isOperasional;

    // Default Constructor
    protected Train() {
        this.kodeKereta = "";
        this.namaKereta = "";
        this.kapasitas = 0;
        this.isOperasional = false;
    }

    // Parameterized Constructor
    protected Train(String kodeKereta, String namaKereta, int kapasitas, boolean isOperasional) {
        assert kapasitas > 0 : "Kapasitas kereta tidak boleh 0 atau negatif";
        this.kodeKereta = kodeKereta;
        this.namaKereta = namaKereta;
        this.kapasitas = kapasitas;
        this.isOperasional = isOperasional;
    }

    /**
     * Mechanism: Mengambil kode kereta.
     *
     * @return nilai kode kereta dalam bentuk String.
     */
    public String getKodeKereta() {
        return kodeKereta;
    }

    /**
     * Mechanism: Mengatur kode kereta.
     *
     * @param kodeKereta nilai kode kereta dalam bentuk String.
     * @return tidak ada.
     */
    public void setKodeKereta(String kodeKereta) {
        this.kodeKereta = kodeKereta;
    }

    /**
     * Mechanism: Mengambil nama kereta.
     *
     * @return nilai nama kereta dalam bentuk String.
     */
    public String getNamaKereta() {
        return namaKereta;
    }

    /**
     * Mechanism: Mengatur nama kereta.
     *
     * @param namaKereta nilai nama kereta dalam bentuk String.
     * @return tidak ada.
     */
    public void setNamaKereta(String namaKereta) {
        this.namaKereta = namaKereta;
    }

    /**
     * Mechanism: Mengambil kapasitas kereta.
     *
     * @return nilai kapasitas dalam bentuk int.
     */
    public int getKapasitas() {
        return kapasitas;
    }

    /**
     * Mechanism: Mengatur kapasitas kereta.
     *
     * @param kapasitas nilai kapasitas dalam bentuk int.
     * @return tidak ada.
     */
    public void setKapasitas(int kapasitas) {
        assert kapasitas > 0 : "Kapasitas kereta tidak boleh 0 atau negatif";
        this.kapasitas = kapasitas;
    }

    /**
     * Mechanism: Mengecek status operasional kereta.
     *
     * @return status operasional dalam bentuk boolean.
     */
    public boolean isOperasional() {
        return isOperasional;
    }

    /**
     * Mechanism: Mengatur status operasional kereta.
     *
     * @param isOperasional status operasional dalam bentuk boolean.
     * @return tidak ada.
     */
    public void setOperasional(boolean isOperasional) {
        this.isOperasional = isOperasional;
    }

    /**
     * Mechanism: Menghitung harga perjalanan berdasarkan jarak.
     *
     * @param jarak jarak perjalanan dalam bentuk double.
     * @return nilai harga perjalanan dalam bentuk double.
     */
    public abstract double hitungHarga(double jarak);
}
