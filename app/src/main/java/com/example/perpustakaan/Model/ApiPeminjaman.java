package com.example.perpustakaan.Model;

import com.example.perpustakaan.API.ApiClient;
import com.example.perpustakaan.API.UserService;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiPeminjaman {
    private UserService peminjamanService;

    public ApiPeminjaman() {
        peminjamanService = ApiClient.getClient().create(UserService.class);
    }

    public void TambahPeminjaman(String kodeBuku, String nomorAnggota, final ApiResponseCallback<Peminjaman> callback) {
        Call<Peminjaman> call = peminjamanService.TambahPeminjaman(kodeBuku, nomorAnggota);
        call.enqueue(new Callback<Peminjaman>() {
            @Override
            public void onResponse(Call<Peminjaman> call, Response<Peminjaman> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    String errorBody = null;
                    try {
                        errorBody = response.errorBody().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    callback.onError("Tambah peminjaman gagal: " + response.code() + " - " + errorBody);
                }
            }

            @Override
            public void onFailure(Call<Peminjaman> call, Throwable t) {
                callback.onError("Kesalahan jaringan: " + t.getMessage());
            }
        });
    }
    public void updatePeminjaman(String IdPeminjaman, Peminjaman peminjaman, final ApiPeminjaman.ApiResponseCallback<Peminjaman> callback) {
        Call<Peminjaman> call = peminjamanService.updatePeminjaman(IdPeminjaman, peminjaman);
        call.enqueue(new Callback<Peminjaman>() {
            @Override
            public void onResponse(Call<Peminjaman> call, Response<Peminjaman> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    String errorBody = null;
                    try {
                        errorBody = response.errorBody().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    callback.onError("Pembaruan Pengembalian Gagal: " + response.code() + " - " + errorBody);
                }
            }
            @Override
            public void onFailure(Call<Peminjaman> call, Throwable t) {
                callback.onError("Network error: " + t.getMessage());
            }
        });
    }
    public void deletePeminjaman(String peminjamanId, final ApiPeminjaman.ApiResponseCallback<Void> callback) {
        Call<Void> call = peminjamanService.deletePeminjaman(peminjamanId);
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
                    callback.onError("Hapus peminjaman gagal: " + response.code() + " - " + errorBody);
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
