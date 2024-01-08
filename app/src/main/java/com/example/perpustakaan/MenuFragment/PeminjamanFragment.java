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
import com.example.perpustakaan.Model.Peminjaman;
import com.example.perpustakaan.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PeminjamanFragment extends Fragment {
    private RecycleAdapterPengembalian recycleAdapter;
    private RecyclerView recyclerView;
    private List<Peminjaman> peminjamanList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.adapter_peminjaman, container, false);

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
        recycleAdapter = new RecycleAdapterPengembalian(peminjamanList, getContext());
        recyclerView.setAdapter(recycleAdapter);
        getDataPeminjaman();

        return view;
    }

    private void getDataPeminjaman() {
        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Memuat...");
        progressDialog.show();
        UserService apiService = ApiClient.getClient().create(UserService.class);
        Call<List<Peminjaman>> call = apiService.GetPeminjaman();

        call.enqueue(new Callback<List<Peminjaman>>() {
            @Override
            public void onResponse(Call<List<Peminjaman>> call, Response<List<Peminjaman>> response) {
                if (response.isSuccessful()) {
                    List<Peminjaman> newPeminjamanList = response.body();
                    if (newPeminjamanList != null && !newPeminjamanList.isEmpty()) {
                        peminjamanList.clear();
                        peminjamanList.addAll(newPeminjamanList);
                        recycleAdapter.notifyDataSetChanged();
                        progressDialog.dismiss();
                    } else {
                        progressDialog.dismiss();
                        showError("Tidak ada peminjaman yang ditemukan.");
                    }
                } else {
                    progressDialog.show();
                    int statusCode = response.code();
                    showError("Gagal mengambil data peminjaman. Kode Status: " + statusCode);
                }
            }

            @Override
            public void onFailure(Call<List<Peminjaman>> call, Throwable t) {
                showError("Permintaan Gagal. Error: " + t.getMessage());
            }
        });
    }


    private void showError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
