package model;

import util.InvalidNIKException;

public class Passenger extends Person {
    private String passengerID;

    public Passenger() {}

    public Passenger(String nama, String nik, String telepon, String passengerID) throws InvalidNIKException {
        if (nik == null || nik.length() != 16 || !nik.matches("\\d+")) {
            throw new InvalidNIKException("NIK harus 16 digit angka");
        }
        this.setNama(nama);
        super.setNik(nik);
        this.setTelepon(telepon);
        this.passengerID = passengerID;
    }

    public Passenger(String nama, String nik, String telepon) throws InvalidNIKException {
        this(nama, nik, telepon, "P-" + System.currentTimeMillis());
    }

    public String getPassengerID() {
        return passengerID;
    }

    public void setPassengerID(String passengerID) {
        this.passengerID = passengerID;
    }

    @Override
    public void setNik(String nik) {
        if (nik != null && nik.length() == 16 && nik.matches("\\d+")) {
            super.setNik(nik);
        } else {
            throw new IllegalArgumentException("NIK harus 16 digit angka");
        }
    }

    @Override
    public void displayInfo() {
        System.out.println("Passenger ID : " + passengerID);
        System.out.println("Nama         : " + getNama());
        System.out.println("NIK          : " + getNik());
        System.out.println("Telepon      : " + getTelepon());
    }
}
