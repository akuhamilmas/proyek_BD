package com.example.proyek2.pembelian;
import java.sql.Date;

public class Pembelian {
    private Integer id_material;
    private Integer id_suplier;
    private String nama_pembeli;
    private Integer jumlah;
    private Integer harga;
    private Date tanggal;

    public Integer getId_material() {
        return id_material;
    }

    public void setId_material(Integer id_material) {
        this.id_material = id_material;
    }

    public Integer getId_suplier() {
        return id_suplier;
    }

    public void setId_suplier(Integer id_suplier) {
        this.id_suplier = id_suplier;
    }

    public String getNama_pembeli() {
        return nama_pembeli;
    }

    public void setNama_pembeli(String nama_pembeli) {
        this.nama_pembeli = nama_pembeli;
    }

    public Integer getJumlah() {
        return jumlah;
    }

    public void setJumlah(Integer jumlah) {
        this.jumlah = jumlah;
    }

    public Integer getHarga() {
        return harga;
    }

    public void setHarga(Integer harga) {
        this.harga = harga;
    }

    public Date getTanggal() {
        return tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }

    public Pembelian(Integer id_material, Integer id_suplier, String nama_pembeli, Integer jumlah, Integer harga, Date tanggal) {
        this.id_material = id_material;
        this.id_suplier = id_suplier;
        this.nama_pembeli = nama_pembeli;
        this.jumlah = jumlah;
        this.harga = harga;
        this.tanggal = tanggal;
    }
}
