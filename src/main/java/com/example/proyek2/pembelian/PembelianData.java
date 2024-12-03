package com.example.proyek2.pembelian;
public class PembelianData {
    private String nama_pembeli;
    private int totalPembelian;

    public PembelianData(String nama_pembeli, int totalPembelian) {
        this.nama_pembeli = nama_pembeli;
        this.totalPembelian = totalPembelian;
    }

    public String getNama_pembeli() {
        return nama_pembeli;
    }

    public int getTotalPembelian() {
        return totalPembelian;
    }
}
