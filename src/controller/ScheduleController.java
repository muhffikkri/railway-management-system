package controller;

import java.util.List;
import model.Schedule;

/**
 * Kelas pengendali untuk manajemen jadwal.
 *
 * Mechanism: Menyediakan operasi pengecekan bentrok dan penambahan jadwal.
 */
public class ScheduleController {
    private List<Schedule> listJadwal;

    /**
     * Mechanism: Mengambil daftar jadwal yang tersimpan.
     *
     * @return daftar jadwal dalam bentuk List<Schedule>.
     */
    public List<Schedule> getListJadwal() {
        return listJadwal;
    }

    /**
     * Mechanism: Mengatur daftar jadwal yang tersimpan.
     *
     * @param listJadwal daftar jadwal dalam bentuk List<Schedule>.
     * @return tidak ada.
     */
    public void setListJadwal(List<Schedule> listJadwal) {
        this.listJadwal = listJadwal;
    }

    /**
     * Mechanism: Mengecek apakah jadwal baru bentrok dengan daftar jadwal yang ada.
     *
     * @param baru jadwal baru dalam bentuk Schedule.
     * @param list daftar jadwal yang dibandingkan dalam bentuk List<Schedule>.
     * @return status bentrok dalam bentuk boolean.
     */
    public boolean cekBentrok(Schedule baru, List<Schedule> list) {
        return false;
    }

    /**
     * Mechanism: Menambahkan jadwal ke daftar jadwal.
     *
     * @param s jadwal yang akan ditambahkan dalam bentuk Schedule.
     * @return tidak ada.
     */
    public void addSchedule(Schedule s) {
    }
}
