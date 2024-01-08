package com.example.perpustakaan.Model;
import com.example.perpustakaan.API.ApiClient;
import com.example.perpustakaan.API.UserService;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiBukuDigital {
    private UserService bukuService;

    public ApiBukuDigital() {
        bukuService = ApiClient.getClient().create(UserService.class);
    }

    public void tambahBukuDigital(String kodeBuku, String judulBuku, String pengarang, String penerbit, String tahunTerbit, String kategori, String jumlah, String link,String linkSampul, final ApiResponseCallback<BukuDigital> callback) {
        Call<BukuDigital> call = bukuService.TambahBukuDigital(kodeBuku, judulBuku, pengarang, penerbit, tahunTerbit, kategori, jumlah, link,linkSampul);
        call.enqueue(new Callback<BukuDigital>() {
            @Override
            public void onResponse(Call<BukuDigital> call, Response<BukuDigital> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    String errorBody = null;
                    try {
                        errorBody = response.errorBody().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    callback.onError("Tambah buku gagal: " + response.code() + " - " + errorBody);
                }
            }

            @Override
            public void onFailure(Call<BukuDigital> call, Throwable t) {
                callback.onError("Kesalahan jaringan: " + t.getMessage());
            }
        });
    }
    public void deleteBukuDigital(String bookId, final ApiBukuDigital.ApiResponseCallback<Void> callback) {
        Call<Void> call = bukuService.deleteBukuDigital(bookId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(null);
                } else {
                    String errorBody = null;
                    try {
                        errorBody = response.errorBody().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    callback.onError("Hapus buku digital gagal: " + response.code() + " - " + errorBody);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                callback.onError("Kesalahan jaringan: " + t.getMessage());
            }
        });
    }

    public interface ApiResponseCallback<T> {
        void onSuccess(T response);

        void onError(String error);
    }
}

