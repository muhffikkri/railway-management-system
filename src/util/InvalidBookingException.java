package util;

/**
 * Checked Exception untuk kegagalan transaksi pemesanan di loket.
 * * Mechanism: Dilempar ketika syarat administrasi tiket perusahaan dilanggar, seperti data 
 * kosong atau kegagalan kuota kursi jadwal perjalanan.
 */
public class InvalidBookingException extends Exception {
    /**
     * Mechanism: Membuat instance pengecualian tanpa pesan detail tambahan.
     */
    public InvalidBookingException() {
        super();
    }

    /**
     * Mechanism: Membuat instance pengecualian disertai penjelasan penyebab kesalahan regulasi bisnis.
     * * @param message String teks berisi detail kegagalan.
     */
    public InvalidBookingException(String message) {
        super(message);
    }
}