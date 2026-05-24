package model;

import util.InvalidNIKException;

/**
 * Kelas untuk merepresentasikan penumpang.
 *
 * Mechanism: Menyimpan data penumpang sebagai turunan dari Person.
 */
public class Passenger extends Person {
    private String passengerID;

    /**
     * Mechanism: Membuat objek Passenger tanpa data awal.
     *
     * @return tidak ada.
     */
    public Passenger() {}

    /**
     * Mechanism: Membuat objek Passenger dengan data lengkap.
     *
     * @param nama nilai nama penumpang dalam bentuk String.
     * @param nik nilai NIK penumpang dalam bentuk String.
     * @param telepon nilai nomor telepon dalam bentuk String.
     * @param passengerID nilai ID penumpang dalam bentuk String.
     * @throws InvalidNIKException jika NIK tidak valid.
     * @return tidak ada.
     */
    public Passenger(String nama, String nik, String telepon, String passengerID) throws InvalidNIKException {
        if (nik == null || nik.length() != 16 || !nik.matches("\\d+")) {
            throw new InvalidNIKException("NIK harus 16 digit angka");
        }
        this.setNama(nama);
        super.setNik(nik);
        this.setTelepon(telepon);
        this.passengerID = passengerID;
    }

    /**
     * Mechanism: Membuat objek Passenger dengan ID otomatis.
     *
     * @param nama nilai nama penumpang dalam bentuk String.
     * @param nik nilai NIK penumpang dalam bentuk String.
     * @param telepon nilai nomor telepon dalam bentuk String.
     * @throws InvalidNIKException jika NIK tidak valid.
     * @return tidak ada.
     */
    public Passenger(String nama, String nik, String telepon) throws InvalidNIKException {
        this(nama, nik, telepon, "P-" + System.currentTimeMillis());
    }

    /**
     * Mechanism: Mengambil ID penumpang.
     *
     * @return nilai ID penumpang dalam bentuk String.
     */
    public String getPassengerID() {
        return passengerID;
    }

    /**
     * Mechanism: Mengatur ID penumpang.
     *
     * @param passengerID nilai ID penumpang dalam bentuk String.
     * @return tidak ada.
     */
    public void setPassengerID(String passengerID) {
        this.passengerID = passengerID;
    }

    /**
     * Mechanism: Mengatur NIK dengan validasi panjang 16 digit angka.
     *
     * @param nik nilai NIK dalam bentuk String.
     * @return tidak ada.
     */
    @Override
    public void setNik(String nik) {
        if (nik != null && nik.length() == 16 && nik.matches("\\d+")) {
            super.setNik(nik);
        } else {
            throw new IllegalArgumentException("NIK harus 16 digit angka");
        }
    }

    /**
     * Mechanism: Menampilkan informasi penumpang.
     *
     * @return tidak ada.
     */
    @Override
    public void displayInfo() {
        System.out.println("Passenger ID : " + passengerID);
        System.out.println("Nama         : " + getNama());
        System.out.println("NIK          : " + getNik());
        System.out.println("Telepon      : " + getTelepon());
    }
}
