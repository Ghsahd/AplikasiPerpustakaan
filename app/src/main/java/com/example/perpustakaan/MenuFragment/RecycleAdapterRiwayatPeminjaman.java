package com.example.perpustakaan.MenuFragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.perpustakaan.Model.PeminjamanOnline;
import com.example.perpustakaan.R;

import java.util.List;

public class RecycleAdapterRiwayatPeminjaman extends RecyclerView.Adapter<RecycleAdapterRiwayatPeminjaman.ViewHolder> {
    private List<PeminjamanOnline> dataPeminjaman;
    private Context mContext;

    public RecycleAdapterRiwayatPeminjaman(List<PeminjamanOnline> dataPeminjaman, Context mContext) {
        this.dataPeminjaman = dataPeminjaman;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_riwayat_peminjaman, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PeminjamanOnline peminjaman = dataPeminjaman.get(position);
        holder.judulBuku.setText(peminjaman.getJudulBuku());
        holder.TanggalPeminjaman.setText(peminjaman.getTanggalPeminjaman());
        holder.Status.setText(peminjaman.getStatus());

        holder.TanggalPeminjaman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ("Diterima".equals(peminjaman.getStatus())) {
                    showDetailDialog(peminjaman);
                } else {
                    Toast.makeText(mContext, "Status masih "  +  peminjaman.getStatus()  + " tunggu sampai diterima", Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.judulBuku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ("Diterima".equals(peminjaman.getStatus())) {
                    showDetailDialog(peminjaman);
                } else {
                    Toast.makeText(mContext, "Status masih "  +  peminjaman.getStatus()  + " tunggu sampai diterima", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataPeminjaman.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView judulBuku;
        TextView TanggalPeminjaman;
        TextView Status;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            judulBuku = itemView.findViewById(R.id.judulBuku);
            TanggalPeminjaman = itemView.findViewById(R.id.tanggalPeminjaman);
            Status = itemView.findViewById(R.id.Status);
        }
    }
    private void showDetailDialog(PeminjamanOnline peminjaman) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Link Buku");
        String detailBuku = "Link: " + peminjaman.getLink() + "\n";

        builder.setMessage(detailBuku);
        builder.setPositiveButton("Buka Link", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                openLinkInBrowser(peminjaman.getLink());
            }
        });

        builder.setNegativeButton("Tutup", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void openLinkInBrowser(String link) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
        if (intent.resolveActivity(mContext.getPackageManager()) != null) {
            mContext.startActivity(intent);
        } else {
            Toast.makeText(mContext, "Tidak ada aplikasi yang dapat menangani tautan.", Toast.LENGTH_SHORT).show();
        }
    }
}
