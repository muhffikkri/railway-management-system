package model;

import controller.CSVHandler;

public class Ticket implements Printable {
    private String noTiket;
    private Passenger p;
    private Schedule s;
    private String nomorKursi;
    private double totalBayar;

    public Ticket() {}

    public Ticket(String noTiket, Passenger p, Schedule s, String nomorKursi, double totalBayar) {
        this.noTiket = noTiket;
        this.p = p;
        this.s = s;
        this.nomorKursi = nomorKursi;
        this.totalBayar = totalBayar;
    }

    public String getNoTiket() {
        return noTiket;
    }

    public void setNoTiket(String noTiket) {
        this.noTiket = noTiket;
    }

    public Passenger getP() {
        return p;
    }

    public void setP(Passenger p) {
        this.p = p;
    }

    public Schedule getS() {
        return s;
    }

    public void setS(Schedule s) {
        this.s = s;
    }

    public String getNomorKursi() {
        return nomorKursi;
    }

    public void setNomorKursi(String nomorKursi) {
        this.nomorKursi = nomorKursi;
    }

    public double getTotalBayar() {
        return totalBayar;
    }

    public void setTotalBayar(double totalBayar) {
        this.totalBayar = totalBayar;
    }

    @Override
    public String printDetail() {
        StringBuilder sb = new StringBuilder();
        sb.append("=========================================\n");
        sb.append("            TIKET KERETA API\n");
        sb.append("=========================================\n");
        sb.append("No. Tiket    : ").append(noTiket != null ? noTiket : "-").append("\n");
        sb.append("Penumpang    : ").append(p != null ? p.getNama() : "-").append("\n");
        sb.append("NIK          : ").append(p != null ? p.getNik() : "-").append("\n");
        sb.append("Kereta       : ").append(s != null && s.getKereta() != null ? s.getKereta().getNamaKereta() : "-").append("\n");
        sb.append("Rute         : ")
                .append(s != null && s.getAsal() != null ? s.getAsal().getNamaStasiun() : "-")
                .append(" → ")
                .append(s != null && s.getTujuan() != null ? s.getTujuan().getNamaStasiun() : "-").append("\n");
        sb.append("Berangkat    : ").append(s != null && s.getBerangkat() != null ? s.getBerangkat().format(CSVHandler.DTF) : "-").append("\n");
        sb.append("Tiba         : ").append(s != null && s.getTiba() != null ? s.getTiba().format(CSVHandler.DTF) : "-").append("\n");
        sb.append("Kursi        : ").append(nomorKursi != null ? nomorKursi : "-").append("\n");
        sb.append("Total Bayar  : Rp ").append(String.format("%,.2f", totalBayar)).append("\n");
        sb.append("=========================================\n");
        return sb.toString();
    }

    public boolean kurangiSisaKursi() {
        if (s != null && s.getSisaKursi() > 0) {
            s.setSisaKursi(s.getSisaKursi() - 1);
            return true;
        }
        return false;
    }
}
