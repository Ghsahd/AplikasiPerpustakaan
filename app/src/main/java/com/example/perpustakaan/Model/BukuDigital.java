package com.example.perpustakaan.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BukuDigital {
    @SerializedName("id_buku_digital")
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
    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("link_sampul")
    @Expose
    private String linkSampul;

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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link= link;
    }

    public String getLinkSampul() {
        return linkSampul;
    }

    public void setLinkSampul(String linkSampul) {
        this.linkSampul = linkSampul;
    }

}