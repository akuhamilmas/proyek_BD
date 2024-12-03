package com.example.proyek2.industri;

import java.util.ArrayList;

public class Industri {
   public ArrayList <String> nama_industri;
    String deskripsi=null;
    String alamat;
    String kota;
    String telepon;

    public Industri(ArrayList<String> nama_industri, String deskripsi, String alamat, String kota, String telepon) {
        this.nama_industri = nama_industri;
        this.deskripsi = deskripsi;
        this.alamat = alamat;
        this.kota = kota;
        this.telepon = telepon;
    }

    public ArrayList<String> getNama_industri() {
        return nama_industri;
    }

    public void setNama_industri(ArrayList<String> nama_industri) {
        this.nama_industri = nama_industri;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
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
