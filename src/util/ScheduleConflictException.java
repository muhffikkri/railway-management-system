package util;

/**
 * Exception khusus untuk konflik jadwal.
 *
 * Mechanism: Menandai kondisi ketika jadwal bertabrakan.
 */
public class ScheduleConflictException extends Exception {
    /**
     * Mechanism: Membuat exception konflik jadwal tanpa pesan.
     *
     * @return tidak ada.
     */
    public ScheduleConflictException() {
        super();
    }

    /**
     * Mechanism: Membuat exception konflik jadwal dengan pesan.
     *
     * @param message pesan error dalam bentuk String.
     * @return tidak ada.
     */
    public ScheduleConflictException(String message) {
        super(message);
    }
}
