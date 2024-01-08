package com.example.perpustakaan.MenuFragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.perpustakaan.Model.ApiPeminjamanOnline;
import com.example.perpustakaan.Model.PeminjamanOnline;
import com.example.perpustakaan.R;

import java.util.List;

public class RecycleAdapterPeminjamanOnline extends RecyclerView.Adapter<RecycleAdapterPeminjamanOnline.ViewHolder> {
    private List<PeminjamanOnline> dataPeminjaman;
    private Context mContext;

    public RecycleAdapterPeminjamanOnline(List<PeminjamanOnline> dataPeminjaman, Context mContext) {
        this.dataPeminjaman = dataPeminjaman;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_peminjaman_online, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PeminjamanOnline peminjaman = dataPeminjaman.get(position);
        holder.NomorAngota.setText(peminjaman.getNomorAnggota());
        holder.KodeBuku.setText(peminjaman.getKodeBuku());
        holder.Status.setText(peminjaman.getStatus());

        // Tambahkan pendengar klik untuk tombol Diterima
        holder.buttonDiterima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateStatusPeminjaman(peminjaman, "Diterima");
            }
        });

        holder.buttonDitolak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateStatusPeminjaman(peminjaman, "Diajukan");
            }
        });

        holder.NomorAngota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDetailDialog(peminjaman);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataPeminjaman.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView KodeBuku;
        TextView NomorAngota;
        TextView Status;
        Button buttonDiterima;
        Button buttonDitolak;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            NomorAngota = itemView.findViewById(R.id.NomorAnggota);
            KodeBuku = itemView.findViewById(R.id.KodeBuku);
            Status = itemView.findViewById(R.id.status);
            buttonDiterima = itemView.findViewById(R.id.buttonDiterima);
            buttonDitolak = itemView.findViewById(R.id.buttonDitolak);

        }
    }


    private void updateStatusPeminjaman(PeminjamanOnline peminjaman, String status) {
        ProgressDialog progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Memuat...");
        progressDialog.show();

        ApiPeminjamanOnline apiPeminjaman = new ApiPeminjamanOnline();
        String idPeminjaman = peminjaman.getIdPeminjaman();

        if (idPeminjaman != null) {
            // Setel status baru sesuai dengan tombol yang ditekan
            peminjaman.setStatus(status);

            apiPeminjaman.updatePeminjamanOnline(idPeminjaman, peminjaman, new ApiPeminjamanOnline.ApiResponseCallback<PeminjamanOnline>() {
                @Override
                public void onSuccess(PeminjamanOnline response) {
                    progressDialog.dismiss();
                    Toast.makeText(mContext, "Peminjaman berhasil di" + status, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(String error) {
                    progressDialog.dismiss();
                    Toast.makeText(mContext, "Proses " + status + " gagal: " + error, Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            progressDialog.dismiss();
            Toast.makeText(mContext, "Id Peminjaman Kosong", Toast.LENGTH_SHORT).show();
        }
    }

    private void showDetailDialog(PeminjamanOnline peminjaman) {
        ProgressDialog progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Memuat...");
        progressDialog.show();
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Detail Peminjaman");
        String detailBuku = "Kode Buku: " + peminjaman.getKodeBuku() + "\n" +
                            "Nomor Anggota: " + peminjaman.getNomorAnggota() + "\n" +
                            "Status : " + peminjaman.getStatus() + "\n" +
                            "Tanggal Peminjaman: " + peminjaman.getTanggalPeminjaman() + "\n" ;

        builder.setMessage(detailBuku);

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
