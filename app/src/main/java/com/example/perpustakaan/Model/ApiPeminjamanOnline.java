package com.example.perpustakaan.Model;

import com.example.perpustakaan.API.ApiClient;
import com.example.perpustakaan.API.UserService;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiPeminjamanOnline {

    private UserService peminjamanService;

    public ApiPeminjamanOnline() {
        peminjamanService = ApiClient.getClient().create(UserService.class);
    }

    public void TambahPeminjamanOnline(String kodeBuku, String nomorAnggota, final ApiResponseCallback<PeminjamanOnline> callback) {
        Call<PeminjamanOnline> call = peminjamanService.TambahPeminjamanOnline(kodeBuku, nomorAnggota);
        call.enqueue(new Callback<PeminjamanOnline>() {
            @Override
            public void onResponse(Call<PeminjamanOnline> call, Response<PeminjamanOnline> response) {
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
            public void onFailure(Call<PeminjamanOnline> call, Throwable t) {
                callback.onError("Kesalahan jaringan: " + t.getMessage());
            }
        });
    }

    public void updatePeminjamanOnline(String IdPeminjaman, PeminjamanOnline peminjamanOnline, final ApiResponseCallback<PeminjamanOnline> callback) {
        Call<PeminjamanOnline> call = peminjamanService.updatePeminjamanOnline(IdPeminjaman, peminjamanOnline);
        call.enqueue(new Callback<PeminjamanOnline>() {
            @Override
            public void onResponse(Call<PeminjamanOnline> call, Response<PeminjamanOnline> response) {
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
            public void onFailure(Call<PeminjamanOnline> call, Throwable t) {
                callback.onError("Network error: " + t.getMessage());
            }
        });
    }
    public void deletePeminjamanOnline(String peminjamanId, final ApiPeminjamanOnline.ApiResponseCallback<Void> callback) {
        Call<Void> call = peminjamanService.deletePeminjamanOnline(peminjamanId);
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

    public void onSuccess(PeminjamanOnline response) {
        // Implementasi logika sukses jika diperlukan
    }

    public void onError(String error) {
        // Implementasi logika error jika diperlukan
    }

    public interface ApiResponseCallback<T> {
        void onSuccess(T response);

        void onError(String error);
    }
}
