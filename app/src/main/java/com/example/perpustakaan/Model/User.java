package com.example.perpustakaan.Model;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("id_user")
    private int id_user;

    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    @SerializedName("role")
    private String role;

    @SerializedName("nomor_anggota")
    private String nomor_anggota;

    // Buat getter dan setter untuk atribut-atribut di sini

    public int getId() {
        return id_user;
    }
    public void setId(int id) {
        this.id_user = id;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }

    public String getNomorAnggota() {return nomor_anggota;}
    public void  setNomorAnggota(String NomorAnggota){this.nomor_anggota = nomor_anggota ;}


}
