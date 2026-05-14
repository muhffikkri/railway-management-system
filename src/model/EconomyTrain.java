package model;

/**
 * Kelas untuk kereta ekonomi.
 *
 * Mechanism: Mengimplementasikan perhitungan harga khusus ekonomi.
 */
public class EconomyTrain extends Train {
    /**
     * Mechanism: Menghitung harga perjalanan untuk kereta ekonomi.
     *
     * @param jarak jarak perjalanan dalam bentuk double.
     * @return nilai harga perjalanan dalam bentuk double.
     */
    @Override
    public double hitungHarga(double jarak) {
        return 0.0;
    }
}
