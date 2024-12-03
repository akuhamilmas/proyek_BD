package com.example.proyek2.material;

public class MaterialJumlah {
    private String nama_material;
    private int totalJumlah;

    public MaterialJumlah(String nama_material, int totalJumlah) {
        this.nama_material = nama_material;
        this.totalJumlah = totalJumlah;
    }

    public String getNama_material() {
        return nama_material;
    }

    public int getTotalJumlah() {
        return totalJumlah;
    }
}