package controller;

import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;
import model.Schedule;

//Kelas pengendali untuk manajemen jadwal.
//Mechanism: Menyediakan operasi pengecekan bentrok dan penambahan jadwal.
public class ScheduleController {

    //ATRIBUT

    //List jadwal yang dikelola oleh controller ini.
    //Bisa berisi objek `Schedule` yang dimuat dari CSV atau sumber lain.
    private List<Schedule> listJadwal;

    //METHOD

    //Mechanism: Mengambil daftar jadwal yang tersimpan.
    //@return daftar jadwal dalam bentuk List<Schedule>.
    public List<Schedule> getListJadwal() {
        return listJadwal;
    }

    /*Mechanism: Mengatur daftar jadwal yang tersimpan.
    @param listJadwal daftar jadwal dalam bentuk List<Schedule>.
    @return tidak ada.*/
    public void setListJadwal(List<Schedule> listJadwal) {
        this.listJadwal = listJadwal;
    }

    /*Mechanism: Mengecek apakah jadwal baru bentrok dengan daftar jadwal yang ada.
    @param baru jadwal baru dalam bentuk Schedule.
    @param list daftar jadwal yang dibandingkan dalam bentuk List<Schedule>.
    @return status bentrok dalam bentuk boolean.*/
    public boolean cekBentrok(Schedule baru, List<Schedule> list) {
        if (baru == null || list == null) {
            return false;
        }
        for (Schedule s : list) {
            if (s == null) continue;
            if (baru.getKereta() != null && s.getKereta() != null
                    && baru.getKereta().getKodeKereta() != null
                    && baru.getKereta().getKodeKereta().equals(s.getKereta().getKodeKereta())) {
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

    /*Mechanism: Menambahkan jadwal ke daftar jadwal.
    @param s jadwal yang akan ditambahkan dalam bentuk Schedule.
    @return tidak ada.*/
    public void addSchedule(Schedule s) {
        if (this.listJadwal == null) {
            this.listJadwal = new ArrayList<>();
        }
        this.listJadwal.add(s);
    }
}
