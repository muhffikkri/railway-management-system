package model;

/**
 * Kelas untuk merepresentasikan penumpang.
 *
 * Mechanism: Menyimpan data penumpang sebagai turunan dari Person.
 */
public class Passenger extends Person {
    private String passengerID;

    /**
     * Mechanism: Mengambil ID penumpang.
     *
     * @return nilai ID penumpang dalam bentuk String.
     */
    public String getPassengerID() {
        return passengerID;
    }

    /**
     * Mechanism: Mengatur ID penumpang.
     *
     * @param passengerID nilai ID penumpang dalam bentuk String.
     * @return tidak ada.
     */
    public void setPassengerID(String passengerID) {
        this.passengerID = passengerID;
    }

    /**
     * Mechanism: Menampilkan informasi penumpang.
     *
     * @return tidak ada.
     */
    @Override
    public void displayInfo() {
    }
}
