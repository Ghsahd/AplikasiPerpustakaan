package com.example.perpustakaan.MenuFragment;

import android.app.ProgressDialog;
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
import com.example.perpustakaan.Model.PeminjamanOnline;
import com.example.perpustakaan.Preferences;
import com.example.perpustakaan.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RiwayatPeminjamanFragment extends Fragment {
    private RecycleAdapterRiwayatPeminjaman recycleAdapter;
    private RecyclerView recyclerView;
    private List<PeminjamanOnline> peminjamanList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.adapter_riwayat_peminjaman, container, false);

        SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDataPeminjaman();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recycleAdapter = new RecycleAdapterRiwayatPeminjaman(peminjamanList, getContext());
        recyclerView.setAdapter(recycleAdapter);
        getDataPeminjaman();

        return view;
    }

    private void getDataPeminjaman() {
        String NomorAnggota = Preferences.getKeyNomorAnggota(requireContext());
        ProgressDialog progressDialog = new ProgressDialog(requireActivity());
        progressDialog.setMessage("Memuat...");
        progressDialog.show();

        UserService apiService = ApiClient.getClient().create(UserService.class);
        Call<List<PeminjamanOnline>> call = apiService.getPeminjamanByNomorAnggota(NomorAnggota);

        call.enqueue(new Callback<List<PeminjamanOnline>>() {
            @Override
            public void onResponse(Call<List<PeminjamanOnline>> call, Response<List<PeminjamanOnline>> response) {
                progressDialog.dismiss();

                if (response.isSuccessful()) {
                    List<PeminjamanOnline> newPeminjamanList = response.body();
                    if (newPeminjamanList != null && !newPeminjamanList.isEmpty()) {
                        peminjamanList.clear();
                        peminjamanList.addAll(newPeminjamanList);
                        recycleAdapter.notifyDataSetChanged();
                    } else {
                        showError("Tidak ada peminjaman yang ditemukan.");
                    }
                } else {
                    int statusCode = response.code();
                    showError("Gagal mengambil data peminjaman. Kode Status: " + statusCode);
                }
            }

            @Override
            public void onFailure(Call<List<PeminjamanOnline>> call, Throwable t) {
                progressDialog.dismiss();
                showError("Permintaan Gagal. Error: " + t.getMessage());
            }
        });
    }

    private void showError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
