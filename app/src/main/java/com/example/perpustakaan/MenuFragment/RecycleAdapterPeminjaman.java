package com.example.perpustakaan.MenuFragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.perpustakaan.Model.ApiPeminjaman;
import com.example.perpustakaan.Model.Buku;
import com.example.perpustakaan.Model.Peminjaman;
import com.example.perpustakaan.Preferences;
import com.example.perpustakaan.R;

import java.util.List;

public class RecycleAdapterPeminjaman extends RecyclerView.Adapter<RecycleAdapterPeminjaman.ViewHolder> {
    private List<Buku> dataBuku;
    private Context mContext;
    private int itemMarginTop =67;
    private Fragment fragment;

    public RecycleAdapterPeminjaman(List<Buku> dataBuku, Context mContext, Fragment fragment) {
        this.dataBuku = dataBuku;
        this.mContext = mContext;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_buku, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Buku buku = dataBuku.get(position);
        holder.JudulBuku.setText(buku.getJudulBuku());
        String jumlahBukuText = "Jumlah Buku : " + buku.getJumlah();
        holder.JumlahBuku.setText(jumlahBukuText);
        ImageView imageView = holder.itemView.findViewById(R.id.GambarBuku);
        imageView.setImageResource(R.drawable.ic_launcher_background);
        holder.JudulBuku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDetailDialog(buku);
            }
        });
        if (position == 0) {
            Resources resources = mContext.getResources();
            DisplayMetrics displayMetrics = resources.getDisplayMetrics();
            float dpToPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, itemMarginTop, displayMetrics);
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) holder.itemView.getLayoutParams();
            layoutParams.topMargin = (int) dpToPx;
        } else {
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) holder.itemView.getLayoutParams();
            layoutParams.topMargin = 0;
        }
    }

    @Override
    public int getItemCount() {
        return dataBuku.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView JudulBuku;
        TextView JumlahBuku;
        ImageView GambarBuku;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            JudulBuku = itemView.findViewById(R.id.JudulBuku);
            JumlahBuku = itemView.findViewById(R.id.JumlahBuku);
            GambarBuku = itemView.findViewById(R.id.GambarBuku);
        }
    }
    private void showDetailDialog(Buku buku) {
        ProgressDialog progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Memuat...");
        progressDialog.show();
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Detail Buku");
        String detailBuku = "Judul: " + buku.getJudulBuku() + "\n" +
                "Pengarang: " + buku.getPengarang() + "\n" +
                "Penerbit: " + buku.getPenerbit() + "\n" +
                "Tahun Terbit: " + buku.getTahunTerbit() + "\n" +
                "Kategori: " + buku.getKategori() + "\n" +
                "Jumlah: " + buku.getJumlah() + "\n" +
                "Lokasi Buku: " + buku.getLokasiBuku() + "\n" +
                "Lokasi Terbit: " + buku.getLokasiTerbit();

        builder.setMessage(detailBuku);

        builder.setPositiveButton("Pinjam", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton){
                ApiPeminjaman apiPeminjaman = new ApiPeminjaman();
                String NomorAnggota = Preferences.getKeyNomorAnggota(mContext);
                String kodeBuku = buku.getKodeBuku();

                if (fragment != null) {
                    if (NomorAnggota != null) {
                        apiPeminjaman.TambahPeminjaman(kodeBuku, NomorAnggota, new ApiPeminjaman.ApiResponseCallback<Peminjaman>() {
                            @Override
                            public void onSuccess(Peminjaman response) {
                                progressDialog.dismiss();
                                Toast.makeText(mContext, "Buku berhasil dipinjam!", Toast.LENGTH_SHORT).show();
                                Notifikasi.showNotification(mContext, "Peminjaman Berhasil", "Buku berhasil dipinjam!");
                            }

                            @Override
                            public void onError(String error) {
                                progressDialog.dismiss();
                                Toast.makeText(mContext, "Peminjaman gagal: " + error, Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(mContext, "Nomor Anggota Kosong", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(mContext, "Fragment adalah null", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Tutup", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                progressDialog.dismiss();
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
