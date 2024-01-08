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

import com.example.perpustakaan.Model.ApiBuku;
import com.example.perpustakaan.Model.Buku;
import com.example.perpustakaan.R;

public class TambahBukuFragment extends Fragment {

    private EditText editTextKodeBuku;
    private EditText editTextJudulBuku;
    private EditText editTextPengarang;
    private EditText editTextPenerbit;
    private EditText editTextTahunTerbit;
    private EditText editTextKategori;
    private EditText editTextJumlah;
    private EditText editTextLokasiBuku;
    private EditText editTextLokasiTerbit;
    private Button buttonTambahBuku;
    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tambah_buku, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editTextKodeBuku = view.findViewById(R.id.editTextKodeBuku);
        editTextJudulBuku = view.findViewById(R.id.editTextJudulBuku);
        editTextPengarang = view.findViewById(R.id.editTextPengarang);
        editTextPenerbit = view.findViewById(R.id.editTextPenerbit);
        editTextTahunTerbit = view.findViewById(R.id.editTextTahunTerbit);
        editTextKategori = view.findViewById(R.id.editTextKategori);
        editTextJumlah = view.findViewById(R.id.editTextJumlah);
        editTextLokasiBuku = view.findViewById(R.id.editTextLokasiBuku);
        editTextLokasiTerbit = view.findViewById(R.id.editTextLokasiTerbit);
        buttonTambahBuku = view.findViewById(R.id.buttonTambahBuku);

        buttonTambahBuku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tambahBuku();
            }
        });
    }

    private void tambahBuku() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        String kodeBuku = editTextKodeBuku.getText().toString();
        if (kodeBuku.length() != 6) {
            Toast.makeText(requireContext(), "Kode Buku harus memiliki panjang 6 karakter", Toast.LENGTH_SHORT).show();
            return;
        }
        if (isKodeBukuAlreadyExists(kodeBuku)) {
            Toast.makeText(requireContext(), "Kode Buku sudah tersedia", Toast.LENGTH_SHORT).show();
        } else {
            String judulBuku = editTextJudulBuku.getText().toString();
            String pengarang = editTextPengarang.getText().toString();
            String penerbit = editTextPenerbit.getText().toString();
            String tahunTerbit = editTextTahunTerbit.getText().toString();
            String kategori = editTextKategori.getText().toString();
            String jumlah = editTextJumlah.getText().toString();
            String lokasiBuku = editTextLokasiBuku.getText().toString();
            String lokasiTerbit = editTextLokasiTerbit.getText().toString();

            if (kodeBuku.isEmpty() || judulBuku.isEmpty() || pengarang.isEmpty() || penerbit.isEmpty() || tahunTerbit.isEmpty()
                    || kategori.isEmpty() || jumlah.isEmpty() || lokasiBuku.isEmpty() || lokasiTerbit.isEmpty()) {
                Toast.makeText(requireContext(), "Semua field harus diisi", Toast.LENGTH_SHORT).show();
                return;
            }

            ApiBuku apiBuku = new ApiBuku();
            apiBuku.tambahBuku(kodeBuku, judulBuku, pengarang, penerbit, tahunTerbit, kategori, jumlah, lokasiBuku, lokasiTerbit, new ApiBuku.ApiResponseCallback<Buku>() {
                @Override
                public void onSuccess(Buku response) {
                    progressDialog.dismiss();
                    Toast.makeText(requireContext(), "Buku berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onError(String error) {
                    progressDialog.dismiss();
                    Toast.makeText(requireContext(), "Gagal menambahkan buku: " + error, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private boolean isKodeBukuAlreadyExists(String kodeBuku) {
        return false;
    }
}
