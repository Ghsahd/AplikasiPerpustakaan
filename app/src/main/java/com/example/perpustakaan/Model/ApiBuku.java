package com.example.perpustakaan.Model;

import com.example.perpustakaan.API.ApiClient;
import com.example.perpustakaan.API.UserService;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiBuku {
    private UserService bukuService;

    public ApiBuku() {
        bukuService = ApiClient.getClient().create(UserService.class);
    }

    public void tambahBuku(String kodeBuku, String judulBuku, String pengarang, String penerbit, String tahunTerbit, String kategori, String jumlah, String lokasiBuku, String lokasiTerbit, final ApiResponseCallback<Buku> callback) {
        Call<Buku> call = bukuService.TambahBuku(kodeBuku, judulBuku, pengarang, penerbit, tahunTerbit, kategori, jumlah, lokasiBuku, lokasiTerbit);
        call.enqueue(new Callback<Buku>() {
            @Override
            public void onResponse(Call<Buku> call, Response<Buku> response) {
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
            public void onFailure(Call<Buku> call, Throwable t) {
                callback.onError("Kesalahan jaringan: " + t.getMessage());
            }
        });
    }
    public void getDataBuku(String idBuku, final ApiBuku.ApiResponseCallback<Buku> callback) {
        Call<Buku> call = bukuService.GetDataBuku(idBuku);
        call.enqueue(new Callback<Buku>() {
            @Override
            public void onResponse(Call<Buku> call, Response<Buku> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    String errorBody = null;
                    try {
                        errorBody = response.errorBody().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    callback.onError("Gagal mengambil data anggota: " + response.code() + " - " + errorBody);
                }
            }

            @Override
            public void onFailure(Call<Buku> call, Throwable t) {
                callback.onError("Network error: " + t.getMessage());
            }
        });
    }

    public void deleteBuku(String bookId, final ApiResponseCallback<Void> callback) {
        Call<Void> call = bukuService.deleteBuku(bookId);
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
    public void updateBuku(String idBuku, Buku buku, final ApiBuku.ApiResponseCallback<Buku> callback) {
        Call<Buku> call = bukuService.updateBuku(idBuku, buku);
        call.enqueue(new Callback<Buku>() {
            @Override
            public void onResponse(Call<Buku> call, Response<Buku> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    String errorBody = null;
                    try {
                        errorBody = response.errorBody().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    callback.onError("Pembaruan buku gagal: " + response.code() + " - " + errorBody);
                }
            }

            @Override
            public void onFailure(Call<Buku> call, Throwable t) {
                callback.onError("Network error: " + t.getMessage());
            }
        });
    }


    public interface ApiResponseCallback<T> {
        void onSuccess(T response);

        void onError(String error);
    }
}
