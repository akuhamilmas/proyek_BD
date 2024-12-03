package com.example.proyek2.cabang;

public class Cabang {
    String nama_cabang;
    String alamat;
    String kota;
    String telepon;

    public Cabang(String nama_cabang, String alamat, String kota, String telepon) {
        this.nama_cabang = nama_cabang;
        this.alamat = alamat;
        this.kota = kota;
        this.telepon = telepon;
    }

    public String getNama_cabang() {
        return nama_cabang;
    }

    public void setNama_cabang(String nama_cabang) {
        this.nama_cabang = nama_cabang;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getKota() {
        return kota;
    }

    public void setKota(String kota) {
        this.kota = kota;
    }

    public String getTelepon() {
        return telepon;
    }

    public void setTelepon(String telepon) {
        this.telepon = telepon;
    }
}
