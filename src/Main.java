import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import controller.CSVHandler;
import model.Schedule;
import model.Station;
import model.Train;

/**
 * Entry point untuk aplikasi ERMS.
 *
 * Mechanism: Menjalankan menu utama aplikasi.
 */
public class Main {
	private static List<Train> listKereta;
    private static List<Station> listStasiun;
    private static List<Schedule> listJadwal;
    
    public static CSVHandler<Schedule> scheduleHandler;

	/**
	 * Mechanism: Menjalankan aplikasi melalui menu utama.
	 *
	 * @param args argumen baris perintah dalam bentuk String array.
	 * @return tidak ada.
	 */
	public static void main(String[] args) {
        
        // 1. Proses Load (Pembacaan File)
        listKereta = CSVHandler.forTrains("data/kereta.csv").read();
        listStasiun = CSVHandler.forStations("data/stasiun.csv").read();
        
        scheduleHandler = CSVHandler.forSchedules("data/jadwal.csv");
        listJadwal = scheduleHandler.read();

        // 2. MENGHUBUNGKAN RELASI OBJEK (Mengambil logika dari DataLoader)
        Map<String, Train> trainMap = new HashMap<>();
        for (Train t : listKereta) if (t != null) trainMap.put(t.getKodeKereta(), t);
        
        Map<String, Station> stationMap = new HashMap<>();
        for (Station s : listStasiun) if (s != null) stationMap.put(s.getKodeStasiun(), s);
        
        for (Schedule s : listJadwal) {
            if (s == null) continue;
            
            if (s.getKereta() != null) {
                Train realTrain = trainMap.get(s.getKereta().getKodeKereta());
                if (realTrain != null) s.setKereta(realTrain);
            }
            if (s.getAsal() != null) {
                Station realAsal = stationMap.get(s.getAsal().getKodeStasiun());
                if (realAsal != null) s.setAsal(realAsal);
            }
            if (s.getTujuan() != null) {
                Station realTujuan = stationMap.get(s.getTujuan().getKodeStasiun());
                if (realTujuan != null) s.setTujuan(realTujuan);
            }
        }
        
        System.out.println("Berhasil memuat " + listKereta.size() + " Kereta, " 
                           + listStasiun.size() + " Stasiun, dan " 
                           + listJadwal.size() + " Jadwal.\n");
        showMainMenu();
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
            System.out.println("3. Keluar");
            System.out.print("Pilih menu (1-3): ");
            
            String pilihan = scanner.nextLine();

            switch (pilihan) {
                case "1":
                    System.out.println("\n--- DAFTAR KERETA ---");
                    for (Train t : listKereta) {
                        System.out.println(t.getKodeKereta() + " - " + t.getNamaKereta() + " (Kapasitas: " + t.getKapasitas() + ")");
                    }
                    System.out.println();
                    break;
                case "2":
                    System.out.println("\n--- DAFTAR JADWAL LENGKAP ---");
                    for (Schedule s : listJadwal) {
                        s.printDetail(); 
                    }
                    System.out.println();
                    break;
                case "3":
                    System.out.println("Sistem ditutup. Terima kasih!");
                    isRunning = false;
                    break;
                default:
                    System.out.println("Pilihan tidak valid!\n");
            }
        }
        scanner.close();
    }
}
