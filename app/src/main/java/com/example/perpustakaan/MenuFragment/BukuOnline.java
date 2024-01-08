package com.example.perpustakaan.MenuFragment;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.perpustakaan.API.ApiClient;
import com.example.perpustakaan.API.UserService;
import com.example.perpustakaan.Model.BukuDigital;
import com.example.perpustakaan.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BukuOnline extends Fragment {

    private RecycleAdapterBukuOnline recycleAdapter;
    private RecyclerView recyclerView;
    private List<BukuDigital> bukuList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.adapter_buku_online, container, false);

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
        recycleAdapter = new RecycleAdapterBukuOnline(bukuList, getContext(), this);
        recyclerView.setAdapter(recycleAdapter);

        getBukuData();

        return view;
    }

    private void getBukuData() {
        UserService apiService = ApiClient.getClient().create(UserService.class);
        Call<List<BukuDigital>> call = apiService.GetBukuDigital();

        call.enqueue(new Callback<List<BukuDigital>>() {
            @Override
            public void onResponse(Call<List<BukuDigital>> call, Response<List<BukuDigital>> response) {
                if (response.isSuccessful()) {
                    List<BukuDigital> newBukuList = response.body();
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
            public void onFailure(Call<List<BukuDigital>> call, Throwable t) {
                showError("Permintaan Gagal. Error: " + t.getMessage());
            }
        });
    }

    private void showError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    public void onLinkClicked(View view) {
        TextView textView = (TextView) view;
        String url = textView.getText().toString();
        openUrlInExternalApp(url);
    }

    private void openUrlInExternalApp(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        intent.addCategory(Intent.CATEGORY_BROWSABLE);

        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getContext(), "Tidak ada aplikasi yang dapat menangani tautan ini", Toast.LENGTH_SHORT).show();
        }
    }
}
