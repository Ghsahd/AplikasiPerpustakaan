package com.example.perpustakaan.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BukuRusak {
    @SerializedName("id_buku_rusak")
    @Expose
    private String idBukuRusak;

    @SerializedName("kode_buku")
    @Expose
    private String kodeBuku;

    @SerializedName("stok_awal")
    @Expose
    private String stokAwal;

    @SerializedName("tanggal_keluar")
    @Expose
    private String tanggalKeluar;

    @SerializedName("jumlah_rusak")
    @Expose
    private String jumlahRusak;

    @SerializedName("keterangan")
    @Expose
    private String keterangan;

    // Getter and Setter for idBukuRusak
    public String getIdBukuRusak() {
        return idBukuRusak;
    }

    public void setIdBukuRusak(String idBukuRusak) {
        this.idBukuRusak = idBukuRusak;
    }

    // Getter and Setter for kodeBuku
    public String getKodeBuku() {
        return kodeBuku;
    }

    public void setKodeBuku(String kodeBuku) {
        this.kodeBuku = kodeBuku;
    }

    // Getter and Setter for stokAwal
    public String getStokAwal() {
        return stokAwal;
    }

    public void setStokAwal(String stokAwal) {
        this.stokAwal = stokAwal;
    }

    // Getter and Setter for tanggalKeluar
    public String getTanggalKeluar() {
        return tanggalKeluar;
    }

    public void setTanggalKeluar(String tanggalKeluar) {
        this.tanggalKeluar = tanggalKeluar;
    }

    // Getter and Setter for jumlahRusak
    public String getJumlahRusak() {
        return jumlahRusak;
    }

    public void setJumlahRusak(String jumlahRusak) {
        this.jumlahRusak = jumlahRusak;
    }

    // Getter and Setter for keterangan
    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }
}
