package util;

/**
 * Exception khusus untuk konflik jadwal.
 * Mechanism: Menandai kondisi ketika jadwal bertabrakan atau tidak valid.
 */
public class ScheduleConflictException extends Exception {
    public ScheduleConflictException() {
        super();
    }
    public ScheduleConflictException(String message) {
        super(message);
    }
}