package model;

/**
 * Kelas konkret yang merepresentasikan staf internal/administrator perusahaan kereta api.
 * * Mechanism: Mewarisi karakteristik umum dari kelas Person serta menambahkan atribut internal 
 * operasional berupa ID Staf dan Peran Kerja (Role) untuk keperluan autentikasi sistem.
 */
public class Staff extends Person {
    private String staffID;
    private String role;

    /**
     * Mechanism: Membuat objek Staff kosong (default constructor) untuk inisialisasi dinamis.
     */
    public Staff() {
        super();
    }

    /**
     * Mechanism: Constructor berparameter untuk instansiasi Staf dengan data lengkap.
     * * @param nama     Nama lengkap staf dalam bentuk String.
     * @param nik      Nomor NIK staf dalam bentuk String.
     * @param telepon  Nomor telepon aktif dalam bentuk String.
     * @param staffID  ID Unik kepegawaian staf dalam bentuk String.
     * @param role     Peran operasional staf (misal: "Admin", "Ops", "Ticketing") dalam bentuk String.
     */
    public Staff(String nama, String nik, String telepon, String staffID, String role) {
        this.setNama(nama);
        this.setNik(nik);
        this.setTelepon(telepon);
        this.staffID = staffID;
        this.role = role;
    }
    
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
     * Mechanism: Melakukan verifikasi kredensial masuk sistem berdasarkan ID staf dan kata sandi.
     * * @param id   Kredensial ID staf masukan dalam bentuk String.
     * @param pass Kredensial kata sandi masukan dalam bentuk String.
     * @return Status kelayakan akses masuk sistem dalam bentuk boolean (true jika berhasil).
     */
    public boolean login(String id, String pass) {
        if (id == null || pass == null) return false;
        return this.staffID.equalsIgnoreCase(id) && pass.equals("erms123"); // Dummy password standar operasional
    }

    /**
     * Mechanism: Manifes data internal staf ke konsol.
     */
    @Override
    public void displayInfo() {
        System.out.println("=== BIODATA INTERNAL STAF ===");
        System.out.println("ID Staf    : " + this.staffID);
        System.out.println("Nama Staf  : " + getNama());
        System.out.println("Jabatan/Role: " + this.role);
        System.out.println("No. Telepon: " + getTelepon());
        System.out.println("=============================");
    }
}
