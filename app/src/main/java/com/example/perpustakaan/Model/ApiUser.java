package com.example.perpustakaan.Model;

import com.example.perpustakaan.API.ApiClient;
import com.example.perpustakaan.API.UserService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiUser {
    private UserService userService;

    public ApiUser() {
        userService = ApiClient.getClient().create(UserService.class);
    }

    public void loginUser(String email, String password, final ApiResponseCallback<User> callback) {
        Call<User> call = userService.loginUser(email, password);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Login failed: " + response.code());
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                callback.onError("Network error: " + t.getMessage());
            }
        });
    }
    public void getAnggota(String nomorAnggota, final ApiResponseCallback<Anggota> callback) {
        Call<Anggota> call = userService.GetAnggota(nomorAnggota);
        call.enqueue(new Callback<Anggota>() {
            @Override
            public void onResponse(Call<Anggota> call, Response<Anggota> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Nomor Anggota Tidak Ditemdukan: " + response.code());
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
                    callback.onError("Failed to update anggota data: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Anggota> call, Throwable t) {
                callback.onError("Network error: " + t.getMessage());
            }
        });
    }

    // Metode lain untuk operasi lainnya
    public interface ApiResponseCallback<T> {
        void onSuccess(T response);

        void onError(String error);
    }

}
