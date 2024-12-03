package com.example.proyek2.suplier;

public class Suplier {
    private Integer id_suplier;
    private String nama_suplier;
    private String alamat;
    private String kota;
    private String telepon;

    // Konstruktor, setter, getter, dan metode lainnya
    // ...

    public Suplier(Integer id_suplier, String nama_suplier, String alamat, String kota, String telepon) {
        this.id_suplier = id_suplier;
        this.nama_suplier = nama_suplier;
        this.alamat = alamat;
        this.kota = kota;
        this.telepon = telepon;
    }

    public Integer getId_suplier() {
        return id_suplier;
    }

    public void setId_suplier(Integer id_suplier) {
        this.id_suplier = id_suplier;
    }

    public String getNama_suplier() {
        return nama_suplier;
    }

    public void setNama_suplier(String nama_suplier) {
        this.nama_suplier = nama_suplier;
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