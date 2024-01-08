package com.example.perpustakaan.MenuFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.perpustakaan.Model.ApiBuku;
import com.example.perpustakaan.Model.Buku;
import com.example.perpustakaan.R;

public class EditDataBuku extends Fragment {
    private EditText kodeBuku,judulBuku,pengarang,penerbit,tahunTerbit,kategori,jumlah,lokasiBuku,lokasiTerbit;
    private Button btnUpdate;
    private ApiBuku dataBuku = new ApiBuku();
    private String idBuku;
    public void setIdBuku(String idBuku) {
        this.idBuku = idBuku;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_data_buku, container, false);
        kodeBuku = view.findViewById(R.id.editTextKodeBuku);
        judulBuku = view.findViewById(R.id.editTextJudulBuku);
        pengarang = view.findViewById(R.id.editTextPengarang);
        penerbit = view.findViewById(R.id.editTextPenerbit);
        tahunTerbit = view.findViewById(R.id.editTextTahunTerbit);
        kategori = view.findViewById(R.id.editTextKategori);
        jumlah = view.findViewById(R.id.editTextJumlah);
        lokasiBuku = view.findViewById(R.id.editTextLokasiBuku);
        lokasiTerbit = view.findViewById(R.id.editTextLokasiTerbit);
        dataBuku = new ApiBuku();
        dataBuku.getDataBuku(idBuku, new ApiBuku.ApiResponseCallback<Buku>() {
            @Override
            public void onSuccess(Buku response) {
                kodeBuku.setText(response.getKodeBuku());
                judulBuku.setText(response.getJudulBuku());
                pengarang.setText(response.getPengarang());
                penerbit.setText(response.getPenerbit());
                tahunTerbit.setText(response.getTahunTerbit());
                kategori.setText(response.getKategori());
                jumlah.setText(response.getJumlah());
                lokasiBuku.setText(response.getLokasiBuku());
                lokasiTerbit.setText(response.getLokasiTerbit());
            }

            @Override
            public void onError(String errorMessage) {
                Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String KB = kodeBuku.getText().toString();
                String JB = judulBuku.getText().toString();
                String Pengarang = pengarang.getText().toString();
                String Penerbit = penerbit.getText().toString();
                String TahunTerbit = tahunTerbit.getText().toString();
                String Kategori = kategori.getText().toString();
                String Jumlah = jumlah.getText().toString();
                String LokasiBuku = lokasiBuku.getText().toString();
                String LokasiTebit = lokasiTerbit.getText().toString();

                Buku buku = new Buku();
                buku.setKodeBuku(KB);
                buku.setJudulBuku(JB);
                buku.setPengarang(Pengarang);
                buku.setPenerbit(Penerbit);
                buku.setTahunTerbit(TahunTerbit);
                buku.setKategori(Kategori);
                buku.setJumlah(Jumlah);
                buku.setLokasiBuku(LokasiBuku);
                buku.setLokasiTerbit(LokasiTebit);

//                dataBuku.updateBuku(IdBuku, buku, new ApiBuku.ApiResponseCallback<Buku>() {
//                    @Override
//                    public void onSuccess(Buku response) {
//                        Toast.makeText(getActivity(), "Data Buku berhasil diperbarui", Toast.LENGTH_SHORT).show();
//                        DataBukuFragment dataBuku = new DataBukuFragment();
//                        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
//                        transaction.replace(R.id.fragmentContainerDataBuku, dataBuku);
//                        transaction.commit();
//                    }
//                    @Override
//                    public void onError(String errorMessage) {
//                        Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
//                    }
//                });
            }
        });

        return view;
    }
}