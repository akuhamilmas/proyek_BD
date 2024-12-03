package com.example.proyek2.stock;

public class Stock {
    private int idMaterial;
    private String namaMaterial;
    private String namaIndustri;
    private int harga;
    private int stok;

    public Stock(int idMaterial, String namaMaterial, String namaIndustri, int harga, int stok) {
        this.idMaterial = idMaterial;
        this.namaMaterial = namaMaterial;
        this.namaIndustri = namaIndustri;
        this.harga = harga;
        this.stok = stok;
    }

    public int getIdMaterial() {
        return idMaterial;
    }

    public void setIdMaterial(int idMaterial) {
        this.idMaterial = idMaterial;
    }

    public String getNamaMaterial() {
        return namaMaterial;
    }

    public void setNamaMaterial(String namaMaterial) {
        this.namaMaterial = namaMaterial;
    }

    public String getNamaIndustri() {
        return namaIndustri;
    }

    public void setNamaIndustri(String namaIndustri) {
        this.namaIndustri = namaIndustri;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    public int getStok() {
        return stok;
    }

    public void setStok(int stok) {
        this.stok = stok;
    }
}