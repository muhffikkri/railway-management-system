package controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import model.Schedule;
import model.Staff;
import util.ScheduleConflictException;

/**
 * Controller untuk mengelola operasional Jadwal (Schedule).
 * Menerapkan Logika Bisnis, Koleksi, CRUD, dan Penanganan Exception.
 */
public class ScheduleController {
    private final List<Schedule> listJadwal;
    private String scheduleCsvPath;

    public ScheduleController() {
        this(new ArrayList<>(), "data/jadwal.csv");
    }

    public ScheduleController(List<Schedule> initialList) {
        this(initialList, "data/jadwal.csv");
    }

    public ScheduleController(List<Schedule> initialList, String scheduleCsvPath) {
        this.listJadwal = new ArrayList<>();
        setListJadwal(initialList);
        this.scheduleCsvPath = scheduleCsvPath != null ? scheduleCsvPath : "data/jadwal.csv";
    }

    public List<Schedule> getListJadwal() {
        return listJadwal;
    }

    public void setListJadwal(List<Schedule> jadwalBaru) {
        this.listJadwal.clear();
        if (jadwalBaru != null) {
            this.listJadwal.addAll(jadwalBaru);
        }
    }

    public void setScheduleCsvPath(String scheduleCsvPath) {
        this.scheduleCsvPath = scheduleCsvPath;
    }

    public void loadFromCsv() {
        loadFromCsv(this.scheduleCsvPath);
    }

    public void loadFromCsv(String filePath) {
        CSVHandler<Schedule> handler = CSVHandler.forSchedules(filePath);
        setListJadwal(handler.read());
        this.scheduleCsvPath = filePath;
    }

    public void saveToCsv() {
        saveToCsv(this.scheduleCsvPath);
    }

    public void saveToCsv(String filePath) {
        CSVHandler<Schedule> handler = CSVHandler.forSchedules(filePath);
        handler.write(this.listJadwal);
        this.scheduleCsvPath = filePath;
    }

    /**
     * Mencari objek Schedule berdasarkan ID Jadwal.
     * @param idJadwal ID yang dicari dalam bentuk String.
     * @return Objek Schedule jika ditemukan, null jika tidak ada.
     */
    public Schedule getScheduleById(String idJadwal) {
        if (idJadwal == null) {
            return null;
        }

        for (Schedule s : listJadwal) {
            if (s != null && s.getIdJadwal() != null && s.getIdJadwal().equalsIgnoreCase(idJadwal)) {
                return s;
            }
        }
        return null;
    }

    public boolean updateSchedule(String idJadwal, Schedule jadwalBaru, Staff staff) throws ScheduleConflictException {
        if (idJadwal == null || jadwalBaru == null) {
            throw new ScheduleConflictException("Data jadwal tidak boleh kosong.");
        }

        int index = -1;
        for (int i = 0; i < listJadwal.size(); i++) {
            Schedule existing = listJadwal.get(i);
            if (existing != null && existing.getIdJadwal() != null && existing.getIdJadwal().equalsIgnoreCase(idJadwal)) {
                index = i;
                break;
            }
        }

        if (index < 0) {
            throw new ScheduleConflictException("Jadwal dengan ID " + idJadwal + " tidak ditemukan.");
        }

        validateSchedule(jadwalBaru);

        if (jadwalBaru.getIdJadwal() != null) {
            Schedule duplicate = getScheduleById(jadwalBaru.getIdJadwal());
            if (duplicate != null && !jadwalBaru.getIdJadwal().equalsIgnoreCase(idJadwal)) {
                throw new ScheduleConflictException("ID jadwal " + jadwalBaru.getIdJadwal() + " sudah digunakan.");
            }
        }

        if (cekBentrok(jadwalBaru, this.listJadwal, idJadwal)) {
            throw new ScheduleConflictException("Konflik Jadwal: Kereta " + jadwalBaru.getKereta().getKodeKereta() + " sudah terikat pada jadwal lain di rentang waktu tersebut.");
        }

        if (staff != null) {
            jadwalBaru.setAuditInfo(staff.getStaffID());
        }

        listJadwal.set(index, jadwalBaru);
        return true;
    }

    public boolean deleteSchedule(String idJadwal) {
        if (idJadwal == null) {
            return false;
        }

        for (int i = 0; i < listJadwal.size(); i++) {
            Schedule existing = listJadwal.get(i);
            if (existing != null && existing.getIdJadwal() != null && existing.getIdJadwal().equalsIgnoreCase(idJadwal)) {
                listJadwal.remove(i);
                return true;
            }
        }
        return false;
    }

    public boolean cekBentrok(Schedule baru, List<Schedule> list) {
        return cekBentrok(baru, list, null);
    }

    public boolean cekBentrok(Schedule baru, List<Schedule> list, String excludeId) {
        if (baru == null || list == null || baru.getKereta() == null) return false;

        for (Schedule s : list) {
            if (s == null || s.getKereta() == null) continue;

            if (excludeId != null && s.getIdJadwal() != null && excludeId.equalsIgnoreCase(s.getIdJadwal())) {
                continue;
            }

            if (baru.getKereta().getKodeKereta().equals(s.getKereta().getKodeKereta())) {
                LocalDateTime bStart = baru.getBerangkat();
                LocalDateTime bEnd = baru.getTiba();
                LocalDateTime sStart = s.getBerangkat();
                LocalDateTime sEnd = s.getTiba();

                if (bStart != null && bEnd != null && sStart != null && sEnd != null) {
                    if (bStart.isBefore(sEnd) && bEnd.isAfter(sStart)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void addSchedule(Schedule s, Staff staff) throws ScheduleConflictException {
        if (s == null) {
            throw new ScheduleConflictException("Data jadwal tidak boleh kosong (null).");
        }

        validateSchedule(s);

        if (getScheduleById(s.getIdJadwal()) != null) {
            throw new ScheduleConflictException("Jadwal dengan ID " + s.getIdJadwal() + " sudah ada.");
        }

        if (cekBentrok(s, this.listJadwal)) {
            throw new ScheduleConflictException("Konflik Jadwal: Kereta " + s.getKereta().getKodeKereta() + " sudah terikat pada jadwal lain di rentang waktu tersebut.");
        }

        if (staff != null) {
            s.setAuditInfo(staff.getStaffID());
        }

        this.listJadwal.add(s);
    }

    public void prosesInputJadwal(Schedule jadwalBaru, Staff staff) {
        try {
            this.addSchedule(jadwalBaru, staff);
            saveToCsv();
            System.out.println("[SUKSES] Jadwal berhasil divalidasi dan disimpan.");
        } catch (ScheduleConflictException e) {
            System.err.println("[OPERASI DITOLAK] " + e.getMessage());
        } catch (Exception e) {
            System.err.println("[ERROR SISTEM] " + e.getMessage());
        }
    }

    private void validateSchedule(Schedule schedule) throws ScheduleConflictException {
        if (schedule.getIdJadwal() == null || schedule.getIdJadwal().isBlank()) {
            throw new ScheduleConflictException("ID jadwal tidak boleh kosong.");
        }

        if (schedule.getKereta() == null) {
            throw new ScheduleConflictException("Kereta pada jadwal tidak boleh kosong.");
        }

        if (schedule.getBerangkat() == null || schedule.getTiba() == null || !schedule.getTiba().isAfter(schedule.getBerangkat())) {
            throw new ScheduleConflictException("Validasi Gagal: Waktu tiba harus diatur setelah waktu keberangkatan.");
        }
    }

}