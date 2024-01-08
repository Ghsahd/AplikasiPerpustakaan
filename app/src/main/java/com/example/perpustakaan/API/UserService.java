package com.example.perpustakaan.API;

import com.example.perpustakaan.Model.Anggota;
import com.example.perpustakaan.Model.Buku;
import com.example.perpustakaan.Model.BukuDigital;
import com.example.perpustakaan.Model.BukuRusak;
import com.example.perpustakaan.Model.Peminjaman;
import com.example.perpustakaan.Model.PeminjamanOnline;
import com.example.perpustakaan.Model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserService {
    //endpoint user
    @FormUrlEncoded
    @POST("api/login")
    Call<User> loginUser(
            @Field("email") String email,
            @Field("password") String password
    );
    @FormUrlEncoded
    @POST("api/register")
    Call<Anggota> RegistrasiAnggota(
            @Field("nama") String nama,
            @Field("email") String email,
            @Field("alamat") String alamat,
            @Field("nomor_telepon") String nomorTelepon,
            @Field("password") String password
    );
    @GET("api/detailAnggota/{nomor_anggota}")
    Call<Anggota> GetAnggota(@Path("nomor_anggota") String nomorAnggota);
    @PUT("api/anggota/{nomorAnggota}")
    Call<Anggota> updateAnggota(@Path("nomorAnggota") String nomorAnggota, @Body Anggota anggota);
    @GET("api/anggota")
    Call<List<Anggota>>GetAnggota();
    @DELETE("api/anggota/{id_anggota}")
    Call<Void> deleteAgggota(@Path("id_anggota") String IdAnggota);

    //endpoint Buku
    @FormUrlEncoded
    @POST("api/buku")
    Call<Buku> TambahBuku(
            @Field("kode_buku") String kodeBuku,
            @Field("judul_buku") String judulBuku,
            @Field("pengarang") String pengarang,
            @Field("penerbit") String penerbit,
            @Field("tahun_terbit") String tahunTerbit,
            @Field("kategori") String kategori,
            @Field("jumlah") String jumlah,
            @Field("lokasi_buku") String lokasiBuku,
            @Field("lokasi_terbit") String lokasiTerbit
    );
    @GET("api/buku")
    Call<List<Buku>>GetBuku();
    @GET("api/buku/{id_buku}")
    Call<Buku> GetDataBuku(@Path("id_buku") String IdBuku);
    @DELETE("api/buku/{id_buku}")
    Call<Void> deleteBuku(@Path("id_buku") String IdBuku);
    @PUT("api/buku/{IdBuku}")
    Call<Buku> updateBuku(@Path("IdBuku") String IdBuku, @Body Buku buku);

    //endpoint Buku Digital
    @FormUrlEncoded
    @POST("api/bukuDigital")
    Call<BukuDigital> TambahBukuDigital(
            @Field("kode_buku") String kodeBuku,
            @Field("judul_buku") String judulBuku,
            @Field("pengarang") String pengarang,
            @Field("penerbit") String penerbit,
            @Field("tahun_terbit") String tahunTerbit,
            @Field("kategori") String kategori,
            @Field("jumlah") String jumlah,
            @Field("link") String link,
            @Field("link_sampul") String linkSampul
    );
    @GET("api/bukuDigital")
    Call<List<BukuDigital>>GetBukuDigital();
    @DELETE("api/bukuDigital/{id_buku_digital}")
    Call<Void> deleteBukuDigital(@Path("id_buku_digital") String IdBuku);

    //endpoint Peminjaman
    @FormUrlEncoded
    @POST("api/peminjaman")
    Call<Peminjaman> TambahPeminjaman(
            @Field("kode_buku") String kodeBuku,
            @Field("nomor_anggota") String NomorAnggota
    );
    @GET("api/dataPeminjaman")
    Call<List<Peminjaman>>GetPeminjaman();
    @PUT("api/peminjaman/{IdPeminjaman}")
    Call<Peminjaman> updatePeminjaman(@Path("IdPeminjaman") String IdPeminjaman, @Body Peminjaman peminjaman);
    @DELETE("api/peminjaman/{IdPeminjaman}")
    Call<Void> deletePeminjaman(@Path("IdPeminjaman") String IdPeminjaman);

    //endpoint Peminjaman Digital
    @FormUrlEncoded
    @POST("api/peminjamanDigital")
    Call<PeminjamanOnline> TambahPeminjamanOnline(
            @Field("kode_buku") String kodeBuku,
            @Field("nomor_anggota") String NomorAnggota
    );
    @PUT("api/peminjamanDigital/{IdPeminjaman}")
    Call<PeminjamanOnline> updatePeminjamanOnline(@Path("IdPeminjaman") String IdPeminjaman, @Body PeminjamanOnline peminjamanOnline);
    @GET("api/peminjamanDigital")
    Call<List<PeminjamanOnline>>GetPeminjamanOnline();
    @GET("api/RiwayatPeminjamanDigital/{nomorAnggota}")
    Call<List<PeminjamanOnline>> getPeminjamanByNomorAnggota(@Path("nomorAnggota") String nomorAnggota);
    @DELETE("api/peminjamanDigital/{IdPeminjaman}")
    Call<Void> deletePeminjamanOnline(@Path("IdPeminjaman") String IdPeminjaman);

    //endpoint Buku Rusak
    @FormUrlEncoded
    @POST("api/bukuRusak")
    Call<BukuRusak> TambahBukuRusak(
            @Field("kode_buku") String kodeBuku,
            @Field("stok_awal") String stokAwal,
            @Field("jumlah_rusak") String jumlahRusak,
            @Field("keterangan") String keterangan
    );
    @GET("api/bukuRusak")
    Call<List<BukuRusak>>GetBukuRusak();
    @DELETE("api/bukuRusak/{id_buku_rusak}")
    Call<Void> deleteBukuRusak(@Path("id_buku_rusak") String IdBuku);

    //endpoint Pengembalian
    @GET("api/dataPengembalian")
    Call<List<Peminjaman>>GetPengembalian();

    //endpoint Buku Masuk
    @GET("api/bukuMasuk")
    Call<List<Buku>>GetBukuMasuk();




}
