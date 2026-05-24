package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import model.Passenger;
import model.Schedule;
import model.Ticket;
import util.InvalidBookingException;
import util.InvalidNIKException;
import util.ManifestGenerator;

/**
 * Kelas pengendali untuk memproses seluruh alur transaksi pemesanan tiket.
 * Mechanism: Menyediakan CRUD tiket di memori, validasi NIK, pengecekan kursi,
 * dan pembuatan manifest per jadwal.
 */
public class BookingController {
    private final List<Ticket> listTiket;
    private final ManifestGenerator manifestGenerator;

    public BookingController() {
        this.listTiket = new ArrayList<>();
        this.manifestGenerator = new ManifestGenerator();
    }

    /**
     * Mechanism: Memproses pembuatan tiket baru berdasarkan penumpang dan jadwal.
     * @param p data penumpang dalam bentuk Passenger.
     * @param s data jadwal dalam bentuk Schedule.
     * @param nomorKursi nomor kursi dalam bentuk int.
     * @return objek tiket yang terbentuk dalam bentuk Ticket.
     */
    public Ticket prosesTiketBaru(Passenger p, Schedule s, int nomorKursi) throws InvalidNIKException, InvalidBookingException {
        if (p == null || s == null) {
            throw new InvalidBookingException("Gagal: Data penumpang atau jadwal tidak boleh kosong!");
        }

        if (p.getNik() == null || !p.getNik().matches("\\d{16}")) {
            throw new InvalidNIKException("NIK harus 16 digit angka.");
        }

        if (nomorKursi <= 0) {
            throw new InvalidBookingException("Nomor kursi harus bernilai positif.");
        }

        if (s.getKereta() != null && nomorKursi > s.getKereta().getKapasitas()) {
            throw new InvalidBookingException("Nomor kursi melebihi kapasitas kereta.");
        }

        if (s.getSisaKursi() <= 0) {
            throw new InvalidBookingException("Gagal: Maaf, kursi untuk jadwal ini sudah habis terjual!");
        }

        String nomorKursiBaru = String.valueOf(nomorKursi);
        if (isNomorKursiTerpakai(s, nomorKursiBaru)) {
            throw new InvalidBookingException("Nomor kursi " + nomorKursiBaru + " sudah dipakai pada jadwal ini.");
        }

        Ticket t = new Ticket();
        t.setNoTiket("TKT-" + System.currentTimeMillis());
        t.setP(p);
        t.setS(s);
        t.setNomorKursi(nomorKursiBaru);

        s.setSisaKursi(s.getSisaKursi() - 1);

        double harga = 0.0;
        if (s.getKereta() != null) {
            harga = s.getKereta().hitungHarga(1.0);
        }
        t.setTotalBayar(harga);

        listTiket.add(t);
        return t;
    }

    /**
     * Mechanism: Mengambil data seluruh riwayat tiket yang terdaftar di memori lokal controller saat ini.
     * @return Koleksi List objek Ticket operasional perusahaan.
     */
    public List<Ticket> getListTiket() {
        return listTiket;
    }

    public Ticket getTicketByNo(String noTiket) {
        if (noTiket == null) {
            return null;
        }

        for (Ticket ticket : listTiket) {
            if (ticket != null && ticket.getNoTiket() != null && ticket.getNoTiket().equalsIgnoreCase(noTiket)) {
                return ticket;
            }
        }
        return null;
    }

    public boolean ubahNomorKursi(String noTiket, int nomorKursiBaru) throws InvalidBookingException {
        Ticket ticket = getTicketByNo(noTiket);
        if (ticket == null) {
            throw new InvalidBookingException("Tiket dengan nomor " + noTiket + " tidak ditemukan.");
        }

        if (nomorKursiBaru <= 0) {
            throw new InvalidBookingException("Nomor kursi harus bernilai positif.");
        }

        Schedule schedule = ticket.getS();
        if (schedule != null && schedule.getKereta() != null && nomorKursiBaru > schedule.getKereta().getKapasitas()) {
            throw new InvalidBookingException("Nomor kursi melebihi kapasitas kereta.");
        }

        String kursiBaru = String.valueOf(nomorKursiBaru);
        if (isNomorKursiTerpakai(schedule, kursiBaru)) {
            throw new InvalidBookingException("Nomor kursi " + kursiBaru + " sudah dipakai pada jadwal ini.");
        }

        ticket.setNomorKursi(kursiBaru);
        return true;
    }

    public boolean batalkanTiket(String noTiket) {
        Ticket ticket = getTicketByNo(noTiket);
        if (ticket == null) {
            return false;
        }

        Schedule schedule = ticket.getS();
        if (schedule != null) {
            schedule.setSisaKursi(schedule.getSisaKursi() + 1);
        }
        return listTiket.remove(ticket);
    }

    public String cetakManifest(Schedule schedule) {
        return manifestGenerator.generate(schedule, ambilTiketUntukJadwal(schedule));
    }

    /**
     * Mechanism: Membaca ulang data tiket.
     * Implementasi saat ini membersihkan cache memori agar siap diisi ulang dari sumber lain.
     */
    public void reloadTicketsFromCSV() {
        this.listTiket.clear();
    }

    private boolean isNomorKursiTerpakai(Schedule schedule, String nomorKursi) {
        if (schedule == null || nomorKursi == null) {
            return false;
        }

        for (Ticket ticket : listTiket) {
            if (ticket == null || ticket.getS() == null) {
                continue;
            }

            boolean scheduleSama = schedule.getIdJadwal() != null
                    && schedule.getIdJadwal().equalsIgnoreCase(ticket.getS().getIdJadwal());
            boolean kursiSama = nomorKursi.equalsIgnoreCase(ticket.getNomorKursi());
            if (scheduleSama && kursiSama) {
                return true;
            }
        }

        return false;
    }

    private List<Ticket> ambilTiketUntukJadwal(Schedule schedule) {
        if (schedule == null || schedule.getIdJadwal() == null) {
            return new ArrayList<>();
        }

        return listTiket.stream()
                .filter(ticket -> ticket != null && ticket.getS() != null
                        && schedule.getIdJadwal().equalsIgnoreCase(ticket.getS().getIdJadwal()))
                .collect(Collectors.toList());
    }
}