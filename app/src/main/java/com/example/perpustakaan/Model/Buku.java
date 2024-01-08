package com.example.perpustakaan.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Buku {
    @SerializedName("id_buku")
    @Expose
    private String idBuku;
    @SerializedName("kode_buku")
    @Expose
    private String kodeBuku;
    @SerializedName("judul_buku")
    @Expose
    private String judulBuku;
    @SerializedName("pengarang")
    @Expose
    private String pengarang;
    @SerializedName("penerbit")
    @Expose
    private String penerbit;
    @SerializedName("tahun_terbit")
    @Expose
    private String tahunTerbit;
    @SerializedName("kategori")
    @Expose
    private String kategori;
    @SerializedName("jumlah")
    @Expose
    private String jumlah;
    @SerializedName("lokasi_buku")
    @Expose
    private String lokasiBuku;
    @SerializedName("lokasi_terbit")
    @Expose
    private String lokasiTerbit;

    public String getIdBuku() {
        return idBuku;
    }

    public void setIdBuku(String idBuku) {
        this.idBuku = idBuku;
    }

    public String getKodeBuku() {
        return kodeBuku;
    }

    public void setKodeBuku(String kodeBuku) {
        this.kodeBuku = kodeBuku;
    }

    public String getJudulBuku() {
        return judulBuku;
    }

    public void setJudulBuku(String judulBuku) {
        this.judulBuku = judulBuku;
    }

    public String getPengarang() {
        return pengarang;
    }

    public void setPengarang(String pengarang) {
        this.pengarang = pengarang;
    }

    public String getPenerbit() {
        return penerbit;
    }

    public void setPenerbit(String penerbit) {
        this.penerbit = penerbit;
    }

    public String getTahunTerbit() {
        return tahunTerbit;
    }

    public void setTahunTerbit(String tahunTerbit) {
        this.tahunTerbit = tahunTerbit;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public String getLokasiBuku() {
        return lokasiBuku;
    }

    public void setLokasiBuku(String lokasiBuku) {
        this.lokasiBuku = lokasiBuku;
    }

    public String getLokasiTerbit() {
        return lokasiTerbit;
    }

    public void setLokasiTerbit(String lokasiTerbit) {
        this.lokasiTerbit = lokasiTerbit;
    }

}