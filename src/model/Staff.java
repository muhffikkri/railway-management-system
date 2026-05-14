package model;

/**
 * Kelas untuk merepresentasikan staf internal perusahaan.
 *
 * Mechanism: Menyimpan data staf dan menyediakan operasi login sederhana.
 */
public class Staff extends Person {
    private String staffID;
    private String role;

    /**
     * Mechanism: Mengambil ID staf.
     *
     * @return nilai ID staf dalam bentuk String.
     */
    public String getStaffID() {
        return staffID;
    }

    /**
     * Mechanism: Mengatur ID staf.
     *
     * @param staffID nilai ID staf dalam bentuk String.
     * @return tidak ada.
     */
    public void setStaffID(String staffID) {
        this.staffID = staffID;
    }

    /**
     * Mechanism: Mengambil peran staf.
     *
     * @return nilai peran staf dalam bentuk String.
     */
    public String getRole() {
        return role;
    }

    /**
     * Mechanism: Mengatur peran staf.
     *
     * @param role nilai peran staf dalam bentuk String.
     * @return tidak ada.
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Mechanism: Melakukan proses autentikasi sederhana berdasarkan ID dan password.
     *
     * @param id nilai ID staf dalam bentuk String.
     * @param pass nilai kata sandi dalam bentuk String.
     * @return status keberhasilan login dalam bentuk boolean.
     */
    public boolean login(String id, String pass) {
        return false;
    }

    /**
     * Mechanism: Menampilkan informasi staf.
     *
     * @return tidak ada.
     */
    @Override
    public void displayInfo() {
    }
}
