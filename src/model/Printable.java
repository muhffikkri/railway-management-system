package model;

/**
 * Interface untuk objek yang dapat mencetak detail informasi.
 *
 * Mechanism: Menyediakan kontrak metode printDetail() untuk konsistensi pencetakan data.
 */
public interface Printable {
    /**
     * Mechanism: Mencetak detail informasi objek.
     *
     * @return hasil detail informasi dalam bentuk String.
     */
    String printDetail();
}
