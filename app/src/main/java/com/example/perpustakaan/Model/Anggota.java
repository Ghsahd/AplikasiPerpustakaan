package com.example.perpustakaan.Model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Anggota {
    @SerializedName("id_anggota")
    @Expose
    private String idAnggota;
    @SerializedName("nomor_anggota")
    @Expose
    private String nomorAnggota;
    @SerializedName("nama")
    @Expose
    private String nama;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("alamat")
    @Expose
    private String alamat;
    @SerializedName("nomor_telepon")
    @Expose
    private String nomorTelepon;
    @SerializedName("password")
    @Expose
    private String password;

    public String getIdAnggota() {
        return idAnggota;
    }

    public void setIdAnggota(String idAnggota) {
        this.idAnggota = idAnggota;
    }

    public String getnomorAnggota() {
        return nomorAnggota;
    }

    public void setNomorAnggota(String nomorAnggota) {
        this.nomorAnggota = nomorAnggota;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNomorTelepon() {
        return nomorTelepon;
    }

    public void setNomorTelepon(String nomorTelepon) {
        this.nomorTelepon = nomorTelepon;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}