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

import com.example.perpustakaan.Model.ApiBukuDigital;
import com.example.perpustakaan.Model.BukuDigital;
import com.example.perpustakaan.R;

public class TambahBukuDigital extends Fragment {

    private EditText editTextKodeBuku;
    private EditText editTextJudulBuku;
    private EditText editTextPengarang;
    private EditText editTextPenerbit;
    private EditText editTextTahunTerbit;
    private EditText editTextKategori;
    private EditText editTextJumlah;
    private EditText editTextLink;
    private EditText editTextLinkSampul;
    private Button buttonTambahBuku;
    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tambah_buku_digital, container, false);
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
        editTextLink = view.findViewById(R.id.editTextLink);
        editTextLinkSampul = view.findViewById(R.id.editTextLinkSampul);
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
            progressDialog.dismiss();
            return;
        }
        // Tambahkan logika untuk memeriksa apakah kodeBuku sudah ada
        if (isKodeBukuAlreadyExists(kodeBuku)) {
            Toast.makeText(requireContext(), "Kode Buku sudah tersedia", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        } else {
            String judulBuku = editTextJudulBuku.getText().toString();
            String pengarang = editTextPengarang.getText().toString();
            String penerbit = editTextPenerbit.getText().toString();
            String tahunTerbit = editTextTahunTerbit.getText().toString();
            String kategori = editTextKategori.getText().toString();
            String jumlah = editTextJumlah.getText().toString();
            String link = editTextLink.getText().toString();
            String linkSampul = editTextLinkSampul.getText().toString();

            if (judulBuku.isEmpty() || pengarang.isEmpty() || penerbit.isEmpty() || tahunTerbit.isEmpty()
                    || kategori.isEmpty() || jumlah.isEmpty() || link.isEmpty()) {
                Toast.makeText(requireContext(), "Semua field harus diisi", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                return;
            }

            ApiBukuDigital apiBuku = new ApiBukuDigital();
            apiBuku.tambahBukuDigital(kodeBuku, judulBuku, pengarang, penerbit, tahunTerbit, kategori, jumlah, link, linkSampul, new ApiBukuDigital.ApiResponseCallback<BukuDigital>(){
                @Override
                public void onSuccess(BukuDigital response) {
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
