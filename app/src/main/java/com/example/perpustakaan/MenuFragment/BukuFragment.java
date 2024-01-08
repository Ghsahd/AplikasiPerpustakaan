package com.example.perpustakaan.MenuFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.perpustakaan.API.ApiClient;
import com.example.perpustakaan.API.UserService;
import com.example.perpustakaan.Model.Buku;
import com.example.perpustakaan.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BukuFragment extends Fragment {

    private RecycleAdapterPeminjaman recycleAdapter;
    private RecyclerView recyclerView;
    private List<Buku> bukuList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.adapter_buku, container, false);

        SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayoutBuku);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getBukuData();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recycleAdapter = new RecycleAdapterPeminjaman(bukuList,getContext(),this);
        recyclerView.setAdapter(recycleAdapter);

        getBukuData();

        return view;
    }

    private void getBukuData() {
        UserService apiService = ApiClient.getClient().create(UserService.class);
        Call<List<Buku>> call = apiService.GetBuku();

        call.enqueue(new Callback<List<Buku>>() {
            @Override
            public void onResponse(Call<List<Buku>> call, Response<List<Buku>> response) {
                if (response.isSuccessful()) {
                    List<Buku> newBukuList = response.body();
                    if (newBukuList != null && !newBukuList.isEmpty()) {
                        bukuList.clear();
                        bukuList.addAll(newBukuList);
                        recycleAdapter.notifyDataSetChanged();
                    } else {
                        showError("Tidak ada buku yang ditemukan.");
                    }
                } else {
                    int statusCode = response.code();
                    showError("Gagal mengambil data buku. Kode Status: " + statusCode);
                }
            }

            @Override
            public void onFailure(Call<List<Buku>> call, Throwable t) {
                showError("Permintaan Gagal. Error: " + t.getMessage());
            }
        });
    }

    private void showError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

}
