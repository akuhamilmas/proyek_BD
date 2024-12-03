package com.example.proyek2.material;

public class Material {
    private Integer id_material;
    private String nama_material;
    private String deskripsi;
    private Integer harga;

    public Material(Integer id_material, String nama_material, String deskripsi, Integer harga) {
        this.id_material = id_material;
        this.nama_material = nama_material;
        this.deskripsi = deskripsi;
        this.harga = harga;
    }

    public Material(String tanggal, String namaMaterial, Integer jumlah, String namaPembeli) {
    }

    public Integer getId_material() {
        return id_material;
    }

    public void setId_material(Integer id_material) {
        this.id_material = id_material;
    }

    public String getNama_material() {
        return nama_material;
    }

    public void setNama_material(String nama_material) {
        this.nama_material = nama_material;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public Integer getHarga() {
        return harga;
    }

    public void setHarga(Integer harga) {
        this.harga = harga;
    }
}

