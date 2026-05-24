import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import controller.BookingController;
import controller.CSVHandler;
import controller.ScheduleController;
import model.Passenger;
import model.Schedule;
import model.Station;
import model.Staff;
import model.Ticket;
import model.Train;
import util.InvalidBookingException;
import util.InvalidNIKException;
import util.ScheduleConflictException;

/**
 * Entry point untuk aplikasi ERMS.
 *
 * Mechanism: Menjalankan menu utama aplikasi.
 */
public class Main {
	private static List<Train> listKereta;
    private static List<Station> listStasiun;
    private static List<Schedule> listJadwal;

    private static ScheduleController scheduleController;
    private static BookingController bookingController;
    public static CSVHandler<Schedule> scheduleHandler;

    private static final String SCHEDULE_FILE = "data/jadwal.csv";

	/**
	 * Mechanism: Menjalankan aplikasi melalui menu utama.
	 *
	 * @param args argumen baris perintah dalam bentuk String array.
	 * @return tidak ada.
	 */
	public static void main(String[] args) {
        listKereta = CSVHandler.forTrains("data/kereta.csv").read();
        listStasiun = CSVHandler.forStations("data/stasiun.csv").read();

        scheduleHandler = CSVHandler.forSchedules(SCHEDULE_FILE);
        listJadwal = scheduleHandler.read();

        relinkSchedules(listJadwal);

        scheduleController = new ScheduleController(listJadwal, SCHEDULE_FILE);
        listJadwal = scheduleController.getListJadwal();
        bookingController = new BookingController();

        System.out.println("Berhasil memuat " + listKereta.size() + " Kereta, "
                           + listStasiun.size() + " Stasiun, dan "
                           + listJadwal.size() + " Jadwal.\n");

        tampilkanSkenarioContoh();
        showMainMenu();
    }

    private static void relinkSchedules(List<Schedule> schedules) {
        Map<String, Train> trainMap = new HashMap<>();
        for (Train t : listKereta) {
            if (t != null) {
                trainMap.put(t.getKodeKereta(), t);
            }
        }

        Map<String, Station> stationMap = new HashMap<>();
        for (Station s : listStasiun) {
            if (s != null) {
                stationMap.put(s.getKodeStasiun(), s);
            }
        }

        for (Schedule s : schedules) {
            if (s == null) {
                continue;
            }

            if (s.getKereta() != null) {
                Train realTrain = trainMap.get(s.getKereta().getKodeKereta());
                if (realTrain != null) {
                    s.setKereta(realTrain);
                }
            }
            if (s.getAsal() != null) {
                Station realAsal = stationMap.get(s.getAsal().getKodeStasiun());
                if (realAsal != null) {
                    s.setAsal(realAsal);
                }
            }
            if (s.getTujuan() != null) {
                Station realTujuan = stationMap.get(s.getTujuan().getKodeStasiun());
                if (realTujuan != null) {
                    s.setTujuan(realTujuan);
                }
            }
        }
    }

	/**
	 * Mechanism: Menampilkan menu utama aplikasi.
	 *
	 * @return tidak ada.
	 */
	private static void showMainMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;

        while (isRunning) {
            System.out.println("=========================================");
            System.out.println("       RAILWAY MANAGEMENT SYSTEM         ");
            System.out.println("=========================================");
            System.out.println("1. Tampilkan Daftar Armada Kereta");
            System.out.println("2. Tampilkan Detail Semua Jadwal");
            System.out.println("3. CRUD Jadwal");
            System.out.println("4. Jalankan Skenario Contoh");
            System.out.println("5. Keluar");
            System.out.print("Pilih menu (1-5): ");

            String pilihan = scanner.nextLine();

            switch (pilihan) {
                case "1":
                    tampilkanDaftarKereta();
                    break;
                case "2":
                    tampilkanSemuaJadwal();
                    break;
                case "3":
                    showScheduleCrudMenu(scanner);
                    break;
                case "4":
                    tampilkanSkenarioContoh();
                    break;
                case "5":
                    System.out.println("Sistem ditutup. Terima kasih!");
                    isRunning = false;
                    break;
                default:
                    System.out.println("Pilihan tidak valid!\n");
            }
        }
        scanner.close();
    }

    private static void tampilkanDaftarKereta() {
        System.out.println("\n--- DAFTAR KERETA ---");
        for (Train t : listKereta) {
            if (t == null) {
                continue;
            }
            String tipe = t.getClass().getSimpleName().replace("Train", "");
            System.out.println(t.getKodeKereta() + " - " + t.getNamaKereta()
                    + " (Tipe: " + tipe + ", Kapasitas: " + t.getKapasitas()
                    + ", Status: " + (t.isOperasional() ? "Aktif" : "Tidak Aktif") + ")");
        }
        System.out.println();
    }

    private static void tampilkanSemuaJadwal() {
        System.out.println("\n--- DAFTAR JADWAL LENGKAP ---");
        for (Schedule s : listJadwal) {
            if (s != null) {
                System.out.println(s.printDetail());
                System.out.println();
            }
        }
        System.out.println();
    }

    private static void showScheduleCrudMenu(Scanner scanner) {
        boolean isRunning = true;

        while (isRunning) {
            System.out.println("\n--- CRUD JADWAL ---");
            System.out.println("1. Tambah Jadwal");
            System.out.println("2. Ubah Jadwal");
            System.out.println("3. Hapus Jadwal");
            System.out.println("4. Kembali");
            System.out.print("Pilih menu (1-4): ");

            String pilihan = scanner.nextLine();
            switch (pilihan) {
                case "1":
                    tambahJadwalDariInput(scanner);
                    break;
                case "2":
                    ubahJadwalDariInput(scanner);
                    break;
                case "3":
                    hapusJadwalDariInput(scanner);
                    break;
                case "4":
                    isRunning = false;
                    break;
                default:
                    System.out.println("Pilihan tidak valid!");
            }
        }
    }

    private static void tambahJadwalDariInput(Scanner scanner) {
        try {
            Schedule jadwal = bacaJadwalDariInput(scanner, null);
            if (jadwal == null) {
                return;
            }

            Staff staff = buatStaffDemo();
            scheduleController.addSchedule(jadwal, staff);
            scheduleController.saveToCsv();
            listJadwal = scheduleController.getListJadwal();
            relinkSchedules(listJadwal);
            System.out.println("Jadwal berhasil ditambahkan dan disimpan.");
        } catch (ScheduleConflictException e) {
            System.out.println("Gagal menambah jadwal: " + e.getMessage());
        }
    }

    private static void ubahJadwalDariInput(Scanner scanner) {
        System.out.print("Masukkan ID jadwal yang akan diubah: ");
        String idJadwal = scanner.nextLine().trim();
        Schedule existing = scheduleController.getScheduleById(idJadwal);
        if (existing == null) {
            System.out.println("Jadwal tidak ditemukan.");
            return;
        }

        try {
            Schedule jadwalBaru = bacaJadwalDariInput(scanner, idJadwal);
            if (jadwalBaru == null) {
                return;
            }

            Staff staff = buatStaffDemo();
            scheduleController.updateSchedule(idJadwal, jadwalBaru, staff);
            scheduleController.saveToCsv();
            listJadwal = scheduleController.getListJadwal();
            relinkSchedules(listJadwal);
            System.out.println("Jadwal berhasil diperbarui dan disimpan.");
        } catch (ScheduleConflictException e) {
            System.out.println("Gagal memperbarui jadwal: " + e.getMessage());
        }
    }

    private static void hapusJadwalDariInput(Scanner scanner) {
        System.out.print("Masukkan ID jadwal yang akan dihapus: ");
        String idJadwal = scanner.nextLine().trim();
        if (scheduleController.deleteSchedule(idJadwal)) {
            scheduleController.saveToCsv();
            listJadwal = scheduleController.getListJadwal();
            System.out.println("Jadwal berhasil dihapus.");
        } else {
            System.out.println("Jadwal tidak ditemukan.");
        }
    }

    private static Schedule bacaJadwalDariInput(Scanner scanner, String defaultId) {
        System.out.print("ID jadwal" + (defaultId != null ? " [" + defaultId + "]" : "") + ": ");
        String idJadwal = scanner.nextLine().trim();
        if (idJadwal.isEmpty() && defaultId != null) {
            idJadwal = defaultId;
        }

        System.out.print("Kode kereta: ");
        Train train = cariKereta(scanner.nextLine().trim());
        if (train == null) {
            System.out.println("Kode kereta tidak ditemukan.");
            return null;
        }

        System.out.print("Kode stasiun asal: ");
        Station asal = cariStasiun(scanner.nextLine().trim());
        if (asal == null) {
            System.out.println("Kode stasiun asal tidak ditemukan.");
            return null;
        }

        System.out.print("Kode stasiun tujuan: ");
        Station tujuan = cariStasiun(scanner.nextLine().trim());
        if (tujuan == null) {
            System.out.println("Kode stasiun tujuan tidak ditemukan.");
            return null;
        }

        System.out.print("Waktu berangkat (dd/MM/yyyy HH:mm): ");
        LocalDateTime berangkat = parseDateTime(scanner.nextLine().trim());
        if (berangkat == null) {
            System.out.println("Format waktu berangkat tidak valid.");
            return null;
        }

        System.out.print("Waktu tiba (dd/MM/yyyy HH:mm): ");
        LocalDateTime tiba = parseDateTime(scanner.nextLine().trim());
        if (tiba == null) {
            System.out.println("Format waktu tiba tidak valid.");
            return null;
        }

        System.out.print("Sisa kursi: ");
        int sisaKursi;
        try {
            sisaKursi = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Sisa kursi harus berupa angka.");
            return null;
        }

        return new Schedule(idJadwal, train, asal, tujuan, berangkat, tiba, sisaKursi);
    }

    private static Train cariKereta(String kodeKereta) {
        if (kodeKereta == null) {
            return null;
        }

        for (Train train : listKereta) {
            if (train != null && train.getKodeKereta() != null && train.getKodeKereta().equalsIgnoreCase(kodeKereta)) {
                return train;
            }
        }
        return null;
    }

    private static Station cariStasiun(String kodeStasiun) {
        if (kodeStasiun == null) {
            return null;
        }

        for (Station station : listStasiun) {
            if (station != null && station.getKodeStasiun() != null && station.getKodeStasiun().equalsIgnoreCase(kodeStasiun)) {
                return station;
            }
        }
        return null;
    }

    private static LocalDateTime parseDateTime(String value) {
        try {
            return LocalDateTime.parse(value, CSVHandler.DTF);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    private static Staff buatStaffDemo() {
        return new Staff("Admin Demo", "1234567890123456", "081234567890", "STF-001", "Admin");
    }

    private static void tampilkanSkenarioContoh() {
        System.out.println("\n=== SKENARIO CONTOH SISTEM ===");

        if (listKereta.isEmpty() || listStasiun.isEmpty() || listJadwal.isEmpty()) {
            System.out.println("Data belum lengkap, skenario tidak dapat dijalankan.");
            return;
        }

        Schedule jadwalAsli = listJadwal.get(0);
        if (jadwalAsli == null) {
            System.out.println("Jadwal awal tidak tersedia.");
            return;
        }

        System.out.println("1) Read jadwal awal:");
        System.out.println(jadwalAsli.printDetail());

        Train train = jadwalAsli.getKereta() != null ? jadwalAsli.getKereta() : listKereta.get(0);
        Station asal = jadwalAsli.getAsal() != null ? jadwalAsli.getAsal() : listStasiun.get(0);
        Station tujuan = jadwalAsli.getTujuan() != null ? jadwalAsli.getTujuan() : listStasiun.get(Math.min(1, listStasiun.size() - 1));
        LocalDateTime berangkat = jadwalAsli.getBerangkat() != null ? jadwalAsli.getBerangkat().plusDays(1) : LocalDateTime.now().plusDays(1);
        LocalDateTime tiba = jadwalAsli.getTiba() != null ? jadwalAsli.getTiba().plusDays(1) : berangkat.plusHours(2);

        Schedule demoJadwal = new Schedule("DEMO-001", train, asal, tujuan, berangkat, tiba, 2);
        ScheduleController demoController = new ScheduleController(new ArrayList<>(listJadwal), SCHEDULE_FILE);

        try {
            demoController.addSchedule(demoJadwal, buatStaffDemo());
            System.out.println("2) Create jadwal demo: berhasil ditambahkan.");

            Schedule jadwalUpdate = new Schedule("DEMO-001", train, asal, tujuan, berangkat.plusHours(1), tiba.plusHours(1), 3);
            demoController.updateSchedule("DEMO-001", jadwalUpdate, buatStaffDemo());
            System.out.println("3) Update jadwal demo: berhasil diperbarui.");
            System.out.println(demoController.getScheduleById("DEMO-001").printDetail());

            demoController.deleteSchedule("DEMO-001");
            System.out.println("4) Delete jadwal demo: berhasil dihapus. Sisa jadwal demo = " + demoController.getListJadwal().size());
        } catch (ScheduleConflictException e) {
            System.out.println("Skenario jadwal gagal: " + e.getMessage());
        }

        try {
            Passenger penumpangDemo = new Passenger("Budi Demo", "1234567890123456", "081234567890", "P-DEMO-1");
            Schedule bookingDemo = new Schedule("BOOK-DEMO", train, asal, tujuan, berangkat, tiba, 2);
            Ticket tiket = bookingController.prosesTiketBaru(penumpangDemo, bookingDemo, 1);
            System.out.println("5) Create tiket: " + tiket.getNoTiket());
            System.out.println(tiket.printDetail());

            bookingController.ubahNomorKursi(tiket.getNoTiket(), 2);
            System.out.println("6) Update tiket: nomor kursi berubah menjadi " + bookingController.getTicketByNo(tiket.getNoTiket()).getNomorKursi());

            String manifest = bookingController.cetakManifest(bookingDemo);
            System.out.println("7) Read manifest tiket:\n" + manifest);

            bookingController.batalkanTiket(tiket.getNoTiket());
            System.out.println("8) Delete tiket: tiket dibatalkan, total tiket tersisa = " + bookingController.getListTiket().size());
        } catch (InvalidNIKException | InvalidBookingException e) {
            System.out.println("Skenario booking gagal: " + e.getMessage());
        }

        System.out.println("=== AKHIR SKENARIO CONTOH ===\n");
    }
}