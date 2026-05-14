package model;

/**
 * Kelas abstrak untuk merepresentasikan informasi umum orang di sistem ERMS.
 *
 * Mechanism: Menyediakan atribut dasar dan kontrak method untuk menampilkan informasi.
 */
public abstract class Person {
    private String nama;
    private String nik;
    private String telepon;

    /**
     * Mechanism: Mengambil nama orang.
     *
     * @return nilai nama dalam bentuk String.
     */
    public String getNama() {
        return nama;
    }

    /**
     * Mechanism: Mengatur nama orang.
     *
     * @param nama nilai nama dalam bentuk String.
     * @return tidak ada.
     */
    public void setNama(String nama) {
        this.nama = nama;
    }

    /**
     * Mechanism: Mengambil NIK orang.
     *
     * @return nilai NIK dalam bentuk String.
     */
    public String getNik() {
        return nik;
    }

    /**
     * Mechanism: Mengatur NIK orang.
     *
     * @param nik nilai NIK dalam bentuk String.
     * @return tidak ada.
     */
    public void setNik(String nik) {
        this.nik = nik;
    }

    /**
     * Mechanism: Mengambil nomor telepon orang.
     *
     * @return nilai telepon dalam bentuk String.
     */
    public String getTelepon() {
        return telepon;
    }

    /**
     * Mechanism: Mengatur nomor telepon orang.
     *
     * @param telepon nilai telepon dalam bentuk String.
     * @return tidak ada.
     */
    public void setTelepon(String telepon) {
        this.telepon = telepon;
    }

    /**
     * Mechanism: Menampilkan informasi orang sesuai implementasi kelas turunan.
     *
     * @return tidak ada.
     */
    public abstract void displayInfo();
}
