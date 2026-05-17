package controller;

import model.Passenger;
import model.Schedule;
import model.Ticket;

/*Kelas pengendali untuk proses pemesanan tiket.
Mechanism: Menyediakan operasi untuk membuat tiket baru.*/
public class BookingController {
    /*Mechanism: Memproses pembuatan tiket baru berdasarkan penumpang dan jadwal.
    @param p data penumpang dalam bentuk Passenger.
    @param s data jadwal dalam bentuk Schedule.
    @param nomorKursi nomor kursi dalam bentuk int.
    @return objek tiket yang terbentuk dalam bentuk Ticket.*/
    public Ticket prosesTiketBaru(Passenger p, Schedule s, int nomorKursi) {
        if (p == null || s == null) {
            return null;
        }

        if (s.getSisaKursi() <= 0) {
            return null;
        }

        Ticket t = new Ticket();
        String noTiket = "TKT-" + System.currentTimeMillis();
        t.setNoTiket(noTiket);
        t.setP(p);
        t.setS(s);
        t.setNomorKursi(String.valueOf(nomorKursi));

        // Kurangi sisa kursi pada jadwal
        s.setSisaKursi(s.getSisaKursi() - 1);

        // Hitung harga — gunakan kereta jika tersedia, fallback 0.0
        double harga = 0.0;
        if (s.getKereta() != null) {
            harga = s.getKereta().hitungHarga(1.0);
        }
        t.setTotalBayar(harga);

        return t;
    }
}
