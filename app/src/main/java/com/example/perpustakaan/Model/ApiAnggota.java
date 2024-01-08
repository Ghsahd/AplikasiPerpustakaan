package com.example.perpustakaan.Model;

import com.example.perpustakaan.API.ApiClient;
import com.example.perpustakaan.API.UserService;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiAnggota {
    private UserService userService;

    public ApiAnggota() {
        userService = ApiClient.getClient().create(UserService.class);
    }

    public void RegistrasiAnggota(String nama,String email,String alamat, String nomorTelepon,String password, final ApiResponseCallback<Anggota> callback) {
        Call<Anggota> call = userService.RegistrasiAnggota( nama,email, alamat, nomorTelepon, password);
        call.enqueue(new Callback<Anggota>() {
            @Override
            public void onResponse(Call<Anggota> call, Response<Anggota> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    String errorBody = null;
                    try {
                        errorBody = response.errorBody().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    callback.onError("Registrasi gagal: " + response.code() + " - " + errorBody);
                }
            }

            @Override
            public void onFailure(Call<Anggota> call, Throwable t) {
                callback.onError("Network error: " + t.getMessage());
            }
        });
    }
    public void updateAnggota(String nomorAnggota, Anggota anggota, final ApiResponseCallback<Anggota> callback) {
        Call<Anggota> call = userService.updateAnggota(nomorAnggota, anggota);
        call.enqueue(new Callback<Anggota>() {
            @Override
            public void onResponse(Call<Anggota> call, Response<Anggota> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    String errorBody = null;
                    try {
                        errorBody = response.errorBody().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    callback.onError("Pembaruan anggota gagal: " + response.code() + " - " + errorBody);
                }
            }

            @Override
            public void onFailure(Call<Anggota> call, Throwable t) {
                callback.onError("Network error: " + t.getMessage());
            }
        });
    }

    public void getDataAnggota(String nomorAnggota, final ApiResponseCallback<Anggota> callback) {
        Call<Anggota> call = userService.GetAnggota(nomorAnggota);
        call.enqueue(new Callback<Anggota>() {
            @Override
            public void onResponse(Call<Anggota> call, Response<Anggota> response) {
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
            public void onFailure(Call<Anggota> call, Throwable t) {
                callback.onError("Network error: " + t.getMessage());
            }
        });
    }
    public void deleteAnggota(String anggotaId, final ApiAnggota.ApiResponseCallback<Void> callback) {
        Call<Void> call = userService.deleteAgggota(anggotaId);
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
                    callback.onError("Hapus annggota gagal: " + response.code() + " - " + errorBody);
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
