package controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Schedule;
import model.Station;
import model.Train;

/**
 * Contoh pemakaian `CSVHandler` untuk memuat dan menghubungkan data dari folder `data/`.
 */
public class DataLoader {
    /**
     * Entrypoint contoh: memuat file `kereta.csv`, `stasiun.csv`, `jadwal.csv` dan
     * menghubungkan referensi kode pada `Schedule` ke objek `Train`/`Station` nyata.
     */
    public static void main(String[] args) {
        String base = "data";

        CSVHandler<Train> trainHandler = CSVHandler.forTrains(base + "/kereta.csv");
        CSVHandler<Station> stationHandler = CSVHandler.forStations(base + "/stasiun.csv");
        CSVHandler<Schedule> scheduleHandler = CSVHandler.forSchedules(base + "/jadwal.csv");

        List<Train> trains = trainHandler.read();
        List<Station> stations = stationHandler.read();
        List<Schedule> schedules = scheduleHandler.read();

        Map<String, Train> trainMap = new HashMap<>();
        for (Train t : trains) if (t != null) trainMap.put(t.getKodeKereta(), t);

        Map<String, Station> stationMap = new HashMap<>();
        for (Station s : stations) if (s != null) stationMap.put(s.getKodeStasiun(), s);

        // Hubungkan schedule ke objek sebenarnya bila memungkinkan
        for (Schedule s : schedules) {
            if (s == null) continue;
            if (s.getKereta() != null) {
                Train real = trainMap.get(s.getKereta().getKodeKereta());
                if (real != null) s.setKereta(real);
            }
            if (s.getAsal() != null) {
                Station a = stationMap.get(s.getAsal().getKodeStasiun());
                if (a != null) s.setAsal(a);
            }
            if (s.getTujuan() != null) {
                Station t = stationMap.get(s.getTujuan().getKodeStasiun());
                if (t != null) s.setTujuan(t);
            }
        }

        System.out.println("Trains loaded: " + trains.size());
        System.out.println("Stations loaded: " + stations.size());
        System.out.println("Schedules loaded: " + schedules.size());
    }
}
