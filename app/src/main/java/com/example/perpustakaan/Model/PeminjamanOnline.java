package com.example.perpustakaan.Model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PeminjamanOnline {

    @SerializedName("id_peminjaman_online")
    @Expose
    private String idPeminjaman;
    @SerializedName("kode_buku")
    @Expose
    private String kodeBuku;
    @SerializedName("nomor_anggota")
    @Expose
    private String nomorAnggota;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("tanggal_peminjaman")
    @Expose
    private String tanggalPeminjaman;
    @SerializedName("tanggal_pengembalian")
    @Expose
    private String tanggalPengembalian;
    @SerializedName("link")
    @Expose
    private String Link;
    @SerializedName("judul_buku")
    @Expose
    private String judulBuku;
    public String getIdPeminjaman() {
        return idPeminjaman;
    }

    public void setIdPeminjaman(String idPeminjaman) {
        this.idPeminjaman = idPeminjaman;
    }

    public String getKodeBuku() {
        return kodeBuku;
    }

    public void setKodeBuku(String kodeBuku) {
        this.kodeBuku = kodeBuku;
    }

    public String getNomorAnggota() {
        return nomorAnggota;
    }

    public void setNomorAnggota(String nomorAnggota) {
        this.nomorAnggota = nomorAnggota;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTanggalPeminjaman() {
        return tanggalPeminjaman;
    }

    public void setTanggalPeminjaman(String tanggalPeminjaman) {
        this.tanggalPeminjaman = tanggalPeminjaman;
    }

    public String getTanggalPengembalian() {
        return tanggalPengembalian;
    }

    public void setTanggalPengembalian(String tanggalPengembalian) {
        this.tanggalPengembalian = tanggalPengembalian;
    }
    public String getLink() {
        return Link;
    }

    public void setLink(String Link) {
        this.Link = Link;
    }
    public String getJudulBuku() {
        return judulBuku;
    }

    public void setJudulBuku(String judulBuku) {
        this.judulBuku = judulBuku;
    }
}