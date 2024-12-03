package com.example.proyek2.pengiriman;

import java.sql.Date;

public class Pengiriman {
    private Integer id_pengiriman;
    private String nama_pembeli;
    private Date tanggal_pengiriman;
    private Integer jumlah_pengiriman;

    public Pengiriman(Integer id_pengiriman, String nama_pembeli, Date tanggal_pengiriman, Integer jumlah_pengiriman) {
        this.id_pengiriman = id_pengiriman;
        this.nama_pembeli = nama_pembeli;
        this.tanggal_pengiriman = tanggal_pengiriman;
        this.jumlah_pengiriman = jumlah_pengiriman;
    }

    public Integer getId_pengiriman() {
        return id_pengiriman;
    }

    public void setId_pengiriman(Integer id_pengiriman) {
        this.id_pengiriman = id_pengiriman;
    }

    public String getNama_pembeli() {
        return nama_pembeli;
    }

    public void setNama_pembeli(String nama_pembeli) {
        this.nama_pembeli = nama_pembeli;
    }

    public Date getTanggal_pengiriman() {
        return tanggal_pengiriman;
    }

    public void setTanggal_pengiriman(Date tanggal_pengiriman) {
        this.tanggal_pengiriman = tanggal_pengiriman;
    }

    public Integer getJumlah_pengiriman() {
        return jumlah_pengiriman;
    }

    public void setJumlah_pengiriman(Integer jumlah_pengiriman) {
        this.jumlah_pengiriman = jumlah_pengiriman;
    }
}
