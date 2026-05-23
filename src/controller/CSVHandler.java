package controller;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import model.EconomyTrain;
import model.ExecutiveTrain;
import model.Schedule;
import model.Station;
import model.Train;

/*Generic CSV handler yang menyediakan operasi I/O pada file CSV bersama
mapper untuk mengonversi antar baris teks dan objek domain `T`.
<p>
Responsibility: handling file read/write/append; conversion logic disediakan
melalui fungsi mapper yang bisa di-inject atau menggunakan factory helpers.
@param <T> tipe record yang dibaca/ditulis.*/
public class CSVHandler<T> {

    //Path ke file CSV yang akan dibaca/ditulis.
    private String filePath;

    //Mapper untuk mengonversi baris CSV menjadi objek `T`.
    private Function<String, T> fromLine;

    //Mapper untuk mengonversi objek `T` menjadi baris CSV. 
    private Function<T, String> toLine;

    //Konstanta untuk pemisah kolom CSV dan format tanggal/waktu yang digunakan dalam file jadwal.
    public static final String SEP = ";";
    public static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    //Constructor default dan constructor lengkap.
    public CSVHandler() {}

    //Constructor lengkap untuk menginisialisasi semua field sekaligus.
    public CSVHandler(String filePath, Function<String, T> fromLine, Function<T, String> toLine) {
        this.filePath = filePath;
        this.fromLine = fromLine;
        this.toLine = toLine;
    }

    
    //Mapper: mengubah satu baris CSV `kereta.csv` menjadi objek `Train`.
    //Format: kode;nama;tipe;kapasitas;status_aktif
    public static Function<String, Train> trainFromLine() {
        return line -> {
            String[] parts = line.split(";", -1);
            if (parts.length < 5){
                return null;
            }

            String kode = parts[0].trim();
            String nama = parts[1].trim();
            String tipe = parts[2].trim();
            String kapasitasS = parts[3].trim();
            String status = parts[4].trim();
            Train t;

            if (tipe.equalsIgnoreCase("Eksekutif")) {
                t = new ExecutiveTrain();
            } 
            else {
                t = new EconomyTrain();
            }
            t.setKodeKereta(kode);
            t.setNamaKereta(nama);
            try { 
                t.setKapasitas(Integer.parseInt(kapasitasS));
            } 
            catch (Exception e) {
                t.setKapasitas(0);
            }
            t.setOperasional(status.equalsIgnoreCase("Aktif"));
            return t;
        };
    }


    //mapper: mengubah objek `Train` menjadi baris CSV sesuai format `kereta.csv`.
    //Format: kode;nama;tipe;kapasitas;status_aktif
    public static Function<Train, String> trainToLine() {
        return t -> {
            if (t == null) return "";
            String tipe = (t instanceof ExecutiveTrain) ? "Eksekutif" : "Ekonomi";
            String status = t.isOperasional() ? "Aktif" : "Tidak Aktif";
            return String.join(SEP,
                    safe(t.getKodeKereta()),
                    safe(t.getNamaKereta()),
                    tipe,
                    String.valueOf(t.getKapasitas()),
                    status);
        };
    }

    //Mapper: mengubah satu baris CSV `stasiun.csv` menjadi objek `Station`.
    //Format: kode_stasiun;nama_stasiun;kota
    public static Function<String, Station> stationFromLine() {
        return line -> {
            String[] parts = line.split(";", -1);
            if (parts.length < 3){
                return null;
            } 
            Station s = new Station();
            s.setKodeStasiun(parts[0].trim());
            s.setNamaStasiun(parts[1].trim());
            s.setKota(parts[2].trim());
            return s;
        };
    }

    //mapper: mengubah satu baris CSV `stasiun.csv` menjadi objek `Station`.
    //Format: kode_stasiun;nama_stasiun;kota
    public static Function<Station, String> stationToLine() {
        return s -> String.join(SEP,
                safe(s.getKodeStasiun()),
                safe(s.getNamaStasiun()),
                safe(s.getKota()));
    }

    //mapper: mengubah satu baris CSV `jadwal.csv` menjadi objek `Schedule` dengan pemetaan langsung ke objek `Train`/`Station` aktual bila memungkinkan.
    //Format: id_jadwal;kode_kereta;kode_stasiun_asal;kode_stasiun_tujuan;waktu_berangkat;waktu_tiba;sisa_kursi
    public static Function<String, Schedule> scheduleFromLineRaw() {
        return line -> {
            String[] parts = line.split(";", -1);
            if (parts.length < 7) return null;
            Schedule s = new Schedule();
            s.setIdJadwal(parts[0].trim());

            Train t;
            String kodeKereta = parts[1].trim();
            if (kodeKereta.isEmpty()) t = null;
            else {
                t = new ExecutiveTrain();
                t.setKodeKereta(kodeKereta);
            }
            s.setKereta(t);

            Station asal = new Station();
            asal.setKodeStasiun(parts[2].trim());
            s.setAsal(asal);

            Station tujuan = new Station();
            tujuan.setKodeStasiun(parts[3].trim());
            s.setTujuan(tujuan);

            try { s.setBerangkat(LocalDateTime.parse(parts[4].trim(), DTF)); } catch (Exception e) {}
            try { s.setTiba(LocalDateTime.parse(parts[5].trim(), DTF)); } catch (Exception e) {}

            String sisa = parts[6].trim().replaceAll("[^0-9]", "");
            try { s.setSisaKursi(Integer.parseInt(sisa)); } catch (Exception e) { s.setSisaKursi(0); }

            return s;
        };
    }

    //Mapper: mengubah satu baris CSV `jadwal.csv` menjadi objek `Schedule` dengan pemetaan langsung ke objek `Train`/`Station` aktual bila memungkinkan.
    //Format: id_jadwal;kode_kereta;kode_stasiun_asal;kode_stasiun_tujuan;waktu_berangkat;waktu_tiba;sisa_kursi
    public static Function<Schedule, String> scheduleToLine() {
        return s -> String.join(SEP,
                safe(s.getIdJadwal()),
                safe(s.getKereta() == null ? "" : s.getKereta().getKodeKereta()),
                safe(s.getAsal() == null ? "" : s.getAsal().getKodeStasiun()),
                safe(s.getTujuan() == null ? "" : s.getTujuan().getKodeStasiun()),
                s.getBerangkat() == null ? "" : s.getBerangkat().format(DTF),
                s.getTiba() == null ? "" : s.getTiba().format(DTF),
                String.valueOf(s.getSisaKursi()));
    }

    //Mapper: mengubah nilai string menjadi format yang aman untuk ditulis ke CSV.
    //Jika nilai null, kembalikan string kosong; jika tidak, kembalikan nilai asli.
    private static String safe(String v) { 
        if (v == null) return "";
        else return v;
    }


    //Factory helper untuk membuat `CSVHandler` yang sudah dikonfigurasi untuk file `kereta.csv`, `stasiun.csv`, atau `jadwal.csv` dengan mapper yang sesuai.
    public static CSVHandler<Train> forTrains(String filePath) {
        return new CSVHandler<>(filePath, trainFromLine(), trainToLine());
    }

    //Factory helper untuk membuat `CSVHandler` yang sudah dikonfigurasi untuk file `stasiun.csv` dengan mapper yang sesuai.
    public static CSVHandler<Station> forStations(String filePath) {
        //Factory: CSVHandler pra-konfigurasi untuk file stasiun. */
        return new CSVHandler<>(filePath, stationFromLine(), stationToLine());
    }

    //Factory helper untuk membuat `CSVHandler` yang sudah dikonfigurasi untuk file `jadwal.csv` dengan mapper yang sesuai.
    public static CSVHandler<Schedule> forSchedules(String filePath) {
        //Factory: CSVHandler pra-konfigurasi untuk file jadwal. */
        return new CSVHandler<>(filePath, scheduleFromLineRaw(), scheduleToLine());
    }

    //Getter dan setter untuk field `filePath`, `fromLine`, dan `toLine`.
    public String getFilePath() {
        return filePath;
    }

    //Getter dan setter untuk field `filePath`, `fromLine`, dan `toLine`.
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    //Getter dan setter untuk field `filePath`, `fromLine`, dan `toLine`.
    public void setFromLine(Function<String, T> fromLine) {
        this.fromLine = fromLine;
    }
    
    public void setToLine(Function<T, String> toLine) {
        this.toLine = toLine;
    }

    //Membaca semua baris dari CSV dan mengonversinya ke objek T menggunakan mapper.
    //Jika file tidak ada, mengembalikan list kosong.
    public List<T> read() {
        if (filePath == null || fromLine == null) {
            throw new IllegalStateException("filePath and fromLine mapper must be set");
        }

        Path p = Paths.get(filePath);
        if (!Files.exists(p)) {
            return Collections.emptyList();
        }

        try {
            return Files.lines(p, StandardCharsets.UTF_8)
                    .map(String::trim)
                    .filter(l -> !l.isEmpty())
                    .map(fromLine)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    //Menulis seluruh daftar record ke file CSV (menimpa file).
    public void write(List<T> data) {
        if (filePath == null || toLine == null) {
            throw new IllegalStateException("filePath and toLine mapper must be set");
        }

        Path p = Paths.get(filePath);
        try {
            Files.createDirectories(p.getParent());
            List<String> lines = data == null ? Collections.emptyList()
                    : data.stream().map(toLine).collect(Collectors.toList());
            Files.write(p, lines, StandardCharsets.UTF_8);
        } catch (IOException e) {
            // swallow for now — callers can check file presence if needed
        }
    }

    //Menambahkan satu record ke akhir file CSV (membuat file bila perlu).
    public void append(T record) {
        if (filePath == null || toLine == null) {
            throw new IllegalStateException("filePath and toLine mapper must be set");
        }

        Path p = Paths.get(filePath);
        String line = toLine.apply(record) + System.lineSeparator();
        try {
            Files.createDirectories(p.getParent());
            Files.write(p, line.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            // swallow
        }
    }
}
