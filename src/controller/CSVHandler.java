package controller;

import java.util.List;

/**
 * Kelas generik untuk menangani operasi CSV.
 *
 * Mechanism: Menyediakan operasi baca, tulis, dan append untuk data bertipe T.
 *
 * @param <T> tipe data record yang dikelola.
 */
public class CSVHandler<T> {
    private String filePath;

    /**
     * Mechanism: Mengambil path file CSV.
     *
     * @return nilai path file dalam bentuk String.
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * Mechanism: Mengatur path file CSV.
     *
     * @param filePath nilai path file dalam bentuk String.
     * @return tidak ada.
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Mechanism: Membaca data dari file CSV.
     *
     * @return daftar record dalam bentuk List<T>.
     */
    public List<T> read() {
        return null;
    }

    /**
     * Mechanism: Menulis data ke file CSV.
     *
     * @param data daftar record dalam bentuk List<T>.
     * @return tidak ada.
     */
    public void write(List<T> data) {
    }

    /**
     * Mechanism: Menambahkan satu record ke file CSV.
     *
     * @param record data record dalam bentuk T.
     * @return tidak ada.
     */
    public void append(T record) {
    }
}
