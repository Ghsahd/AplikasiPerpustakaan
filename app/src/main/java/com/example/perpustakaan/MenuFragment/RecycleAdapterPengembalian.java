package com.example.perpustakaan.MenuFragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.perpustakaan.Model.ApiPeminjaman;
import com.example.perpustakaan.Model.Peminjaman;
import com.example.perpustakaan.R;

import java.util.List;

public class RecycleAdapterPengembalian extends RecyclerView.Adapter<RecycleAdapterPengembalian.ViewHolder> {
    private List<Peminjaman> dataPeminjaman;
    private Context mContext;

    public RecycleAdapterPengembalian(List<Peminjaman> dataPeminjaman, Context mContext) {
        this.dataPeminjaman = dataPeminjaman;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_peminjaman, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Peminjaman peminjaman = dataPeminjaman.get(position);
        holder.NomorAngota.setText(peminjaman.getNomorAnggota());
        holder.KodeBuku.setText(peminjaman.getKodeBuku());

        holder.NomorAngota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Tampilkan modal atau dialog di sini
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            NomorAngota = itemView.findViewById(R.id.NomorAnggota);
            KodeBuku = itemView.findViewById(R.id.KodeBuku);
        }
    }
    private void showDetailDialog(Peminjaman peminjaman) {
        ProgressDialog progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Memuat...");
        progressDialog.show();
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Detail Peminjaman");
        String detailBuku = "Kode Buku: " + peminjaman.getKodeBuku() + "\n" +
                            "Nomor Anggota: " + peminjaman.getNomorAnggota() + "\n" +
                            "Status : " + peminjaman.getStatus() + "\n" +
                            "Tanggal Peminjaman: " + peminjaman.getTanggalPeminjaman() + "\n" +
                            "Tanggal Pengembalian: " + peminjaman.getTanggalPengembalian() + "\n" +
                            "Denda: " + peminjaman.getDenda();

        builder.setMessage(detailBuku);

        builder.setPositiveButton("Pengembalian", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                ApiPeminjaman apiPeminjaman = new ApiPeminjaman();
                String IdPeminjaman = peminjaman.getIdPeminjaman();
                if (IdPeminjaman != null){
                    apiPeminjaman.updatePeminjaman(IdPeminjaman,peminjaman, new ApiPeminjaman.ApiResponseCallback<Peminjaman>() {
                        @Override
                        public void onSuccess(Peminjaman response) {
                            progressDialog.dismiss();
                            Toast.makeText(mContext, "Buku berhasil Dikembalikan!", Toast.LENGTH_SHORT).show();
//                            if (isReturnLate(response.getTanggalPengembalian())) {
//                                // Jika iya, tampilkan notifikasi
//                                showLateReturnNotification();
//                            }
                        }
                        @Override
                        public void onError(String error) {
                            progressDialog.dismiss();
                            Toast.makeText(mContext, "Proses Kembali  gagal: " + error, Toast.LENGTH_SHORT).show();
                        }
                    });
                }else {
                    progressDialog.dismiss();
                    Toast.makeText(mContext, "Id Peminjaman Kosong", Toast.LENGTH_SHORT).show();
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
//    private boolean isReturnLate(String returnDate) {
//        // Implementasi logika penentuan keterlambatan, misalnya:
//        // Ambil tanggal hari ini
//        String currentDate = getCurrentDate();
//
//        // Bandingkan tanggal pengembalian dengan tanggal hari ini
//        return compareDates(returnDate, currentDate) < 0;
//    }
//
//    private void showLateReturnNotification() {
//        // Implementasi tampilan notifikasi untuk pengembalian telat
//        Notifikasi.showNotification(mContext, "Pengembalian Telat",
//                "Anda telah mengembalikan buku dengan keterlambatan. Harap periksa tanggal pengembalian.");
//    }

}
