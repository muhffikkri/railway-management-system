package model;

/**
 * Kelas untuk kereta eksekutif.
 *
 * Mechanism: Mengimplementasikan perhitungan harga khusus eksekutif.
 */
public class ExecutiveTrain extends Train {
    /**
     * Mechanism: Menghitung harga perjalanan untuk kereta eksekutif.
     *
     * @param jarak jarak perjalanan dalam bentuk double.
     * @return nilai harga perjalanan dalam bentuk double.
     */
    @Override
    public double hitungHarga(double jarak) {
        return 0.0;
    }
}
