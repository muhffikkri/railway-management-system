package util;

/**
 * Exception khusus untuk NIK tidak valid.
 *
 * Mechanism: Menandai kondisi ketika NIK tidak memenuhi validasi.
 */
public class InvalidNIKException extends Exception {
    /**
     * Mechanism: Membuat exception NIK tidak valid tanpa pesan.
     *
     * @return tidak ada.
     */
    public InvalidNIKException() {
        super();
    }

    /**
     * Mechanism: Membuat exception NIK tidak valid dengan pesan.
     *
     * @param message pesan error dalam bentuk String.
     * @return tidak ada.
     */
    public InvalidNIKException(String message) {
        super(message);
    }
}
