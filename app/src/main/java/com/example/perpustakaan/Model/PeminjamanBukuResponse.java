package com.example.perpustakaan.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PeminjamanBukuResponse {
    @SerializedName("peminjamanList")
    private List<PeminjamanOnline> peminjamanList;
    @SerializedName("bukuList")
    private List<BukuDigital> bukuList;

    public List<PeminjamanOnline> getPeminjamanOnline() {
        return peminjamanList;
    }

    public List<BukuDigital> getBukuDigital() {
        return bukuList;
    }
}



