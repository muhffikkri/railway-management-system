package util;

/**
 * Exception khusus untuk NIK tidak valid.
 * Mechanism: Menandai kondisi ketika NIK tidak memenuhi validasi.
 */
public class InvalidNIKException extends Exception {
    public InvalidNIKException() {
        super();
    }
    public InvalidNIKException(String message) {
        super(message);
    }
}