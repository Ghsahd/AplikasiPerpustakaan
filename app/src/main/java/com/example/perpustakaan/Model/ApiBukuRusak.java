package com.example.perpustakaan.Model;

import com.example.perpustakaan.API.ApiClient;
import com.example.perpustakaan.API.UserService;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiBukuRusak {
    private UserService bukuService;

    public ApiBukuRusak() {
        bukuService = ApiClient.getClient().create(UserService.class);
    }

    public void tambahBukuRusak(String kodeBuku, String stokAwal, String jumlahRusak, String keterangan, final ApiResponseCallback<BukuRusak> callback) {
        Call<BukuRusak> call = bukuService.TambahBukuRusak(kodeBuku, stokAwal, jumlahRusak, keterangan);
        call.enqueue(new Callback<BukuRusak>() {
            @Override
            public void onResponse(Call<BukuRusak> call, Response<BukuRusak> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    String errorBody = null;
                    try {
                        errorBody = response.errorBody().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    callback.onError("Tambah buku rusak gagal: " + response.code() + " - " + errorBody);
                }
            }

            @Override
            public void onFailure(Call<BukuRusak> call, Throwable t) {
                callback.onError("Kesalahan jaringan: " + t.getMessage());
            }
        });
    }
    public void deleteBukuRusak(String bookId, final ApiBukuRusak.ApiResponseCallback<Void> callback) {
        Call<Void> call = bukuService.deleteBukuRusak(bookId);
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
                    callback.onError("Hapus buku gagal: " + response.code() + " - " + errorBody);
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
