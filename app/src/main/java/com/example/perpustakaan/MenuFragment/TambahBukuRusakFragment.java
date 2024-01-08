package com.example.perpustakaan.MenuFragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.perpustakaan.Model.ApiBukuRusak;
import com.example.perpustakaan.Model.BukuRusak;
import com.example.perpustakaan.R;

public class TambahBukuRusakFragment extends Fragment {

    private EditText editTextKodeBuku;
    private EditText editTextStokAwal;
    private EditText editTextJumlahRusak;
    private EditText editTextKeterangan;
    private Button buttonTambahBukuRusak;
    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tambah_buku_rusak, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editTextKodeBuku = view.findViewById(R.id.editTextKodeBuku);
        editTextStokAwal = view.findViewById(R.id.editTextStokAwal);
        editTextJumlahRusak = view.findViewById(R.id.editTextJumlahRusak);
        editTextKeterangan = view.findViewById(R.id.editTextKeterangan);
        buttonTambahBukuRusak = view.findViewById(R.id.buttonTambahBukuRusak);

        buttonTambahBukuRusak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tambahBukuRusak();
            }
        });
    }

    private void tambahBukuRusak() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        String kodeBuku = editTextKodeBuku.getText().toString();
        String stokAwal = editTextStokAwal.getText().toString();
        String jumlahRusak = editTextJumlahRusak.getText().toString();
        String keterangan = editTextKeterangan.getText().toString();

        if (kodeBuku.isEmpty() || kodeBuku.isEmpty() || stokAwal.isEmpty() || jumlahRusak.isEmpty() || keterangan.isEmpty()) {
            Toast.makeText(requireContext(), "Semua field harus diisi", Toast.LENGTH_SHORT).show();
            return;
        }

        ApiBukuRusak apiBukuRusak = new ApiBukuRusak();
        apiBukuRusak.tambahBukuRusak(kodeBuku, stokAwal,jumlahRusak, keterangan, new ApiBukuRusak.ApiResponseCallback<BukuRusak>() {
            @Override
            public void onSuccess(BukuRusak response) {
                progressDialog.dismiss();
                Toast.makeText(requireContext(), "Buku Rusak berhasil ditambahkan", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String error) {
                progressDialog.dismiss();
                if (error.contains("Kode Buku not found")) {
                    Toast.makeText(requireContext(), "Kode Buku tidak ditemukan", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(requireContext(), "Gagal menambahkan buku rusak: " + error, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
