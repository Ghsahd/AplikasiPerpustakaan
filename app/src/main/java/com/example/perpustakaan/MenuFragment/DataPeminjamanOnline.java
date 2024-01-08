package com.example.perpustakaan.MenuFragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.perpustakaan.API.ApiClient;
import com.example.perpustakaan.API.UserService;
import com.example.perpustakaan.Model.ApiPeminjamanOnline;
import com.example.perpustakaan.Model.PeminjamanOnline;
import com.example.perpustakaan.R;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DataPeminjamanOnline extends Fragment {
    private List<PeminjamanOnline> peminjamanList = new ArrayList<>();
    private View view;
    private ProgressDialog progressDialog;
    private ApiPeminjamanOnline peminjamanOnline = new ApiPeminjamanOnline();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_data_peminjaman_online, container, false);

        SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDataPeminjaman();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        getDataPeminjaman();

        ImageButton exportButton = view.findViewById(R.id.exportButton);
        exportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(getActivity());
                progressDialog.setMessage("Membuat Excel...");
                progressDialog.show();
                createExcel("DataPeminjaman", "Data Peminjaman.xlsx", peminjamanList);
            }
        });

        return view;
    }
    private void getDataPeminjaman() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        UserService apiService = ApiClient.getClient().create(UserService.class);
        Call<List<PeminjamanOnline>> call = apiService.GetPeminjamanOnline();

        call.enqueue(new Callback<List<PeminjamanOnline>>() {
            @Override
            public void onResponse(Call<List<PeminjamanOnline>> call, Response<List<PeminjamanOnline>> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    List<PeminjamanOnline> newPeminjamanList = response.body();
                    if (newPeminjamanList != null && !newPeminjamanList.isEmpty()) {
                        peminjamanList.clear();
                        peminjamanList.addAll(newPeminjamanList);
                        TableLayout tableLayout = view.findViewById(R.id.tableLayout2);
                        tableLayout.removeAllViews();
                        int nomorUrut = 1;

                        TableRow headerRow = new TableRow(getContext());
                        addHeaderToRow(headerRow, "No");
                        addHeaderToRow(headerRow, "Kode Buku");
                        addHeaderToRow(headerRow, "Nomor Anggota");
                        addHeaderToRow(headerRow, "Status");
                        addHeaderToRow(headerRow, "Tanggal Peminjaman");
                        addHeaderToRow(headerRow, "Tanggal Pengembalian");
                        tableLayout.addView(headerRow);

                        for (PeminjamanOnline peminjaman : peminjamanList) {
                            TableRow row = new TableRow(getContext());

                            addDataToRow(row, String.valueOf(nomorUrut++)); // Nomor Urut (ditambahkan dan kemudian ditingkatkan)
                            addDataToRow(row, peminjaman.getKodeBuku()); // Kode Buku
                            addDataToRow(row, peminjaman.getNomorAnggota()); // Nomor Anggota
                            addDataToRow(row, peminjaman.getStatus()); // Status
                            addDataToRow(row, peminjaman.getTanggalPeminjaman()); // Tanggal Peminjaman
                            addDataToRow(row, peminjaman.getTanggalPengembalian());
                            addActionsToRow(row, tableLayout.getContext(), peminjaman.getIdPeminjaman());
                            tableLayout.addView(row);
                        }
                    } else {
                        showError("Tidak ada peminjaman yang ditemukan.");
                    }
                } else {
                    int statusCode = response.code();
                    showError("Gagal mengambil data peminjaman. Kode Status: " + statusCode);
                }
            }

            @Override
            public void onFailure(Call<List<PeminjamanOnline>> call, Throwable t) {
                showError("Permintaan Gagal. Error: " + t.getMessage());
            }
        });
    }
    private void showError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
    private void addActionsToRow(TableRow row, final Context context, final String bookId) {
//        ImageButton editButton = new ImageButton(context);
//        editButton.setImageResource(R.drawable.baseline_edit_24);
//        editButton.setBackgroundColor(ContextCompat.getColor(context, R.color.purple_700));
//        editButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.d("EditButton", "Button clicked");
//                EditDataBuku editDataBukuFragment = new EditDataBuku();
//                editDataBukuFragment.setIdBuku(bookId);
//                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
//                transaction.replace(R.id.fragmentContainerDataBuku, new EditDataBuku());
//                transaction.addToBackStack(null);
//                transaction.commit();
//            }
//        });
//
//        TableRow.LayoutParams editButtonParams = new TableRow.LayoutParams(
//                getResources().getDimensionPixelSize(R.dimen.button_width),
//                getResources().getDimensionPixelSize(R.dimen.button_height),
//                getResources().getDimensionPixelSize(R.dimen.button_margin_bottom)
//        );
//        editButtonParams.setMargins(getResources().getDimensionPixelSize(R.dimen.button_margin), 2, getResources().getDimensionPixelSize(R.dimen.button_margin),getResources().getDimensionPixelSize(R.dimen.button_margin_bottom));
//        editButton.setLayoutParams(editButtonParams);

        ImageButton deleteButton = new ImageButton(context);
        deleteButton.setImageResource(R.drawable.delete);
        deleteButton.setBackgroundColor(ContextCompat.getColor(context, R.color.redColor));
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDelete(bookId);
            }
        });

        TableRow.LayoutParams deleteButtonParams = new TableRow.LayoutParams(
                getResources().getDimensionPixelSize(R.dimen.button_width),
                getResources().getDimensionPixelSize(R.dimen.button_height),
                getResources().getDimensionPixelSize(R.dimen.button_margin_bottom)
        );
        deleteButtonParams.setMargins(getResources().getDimensionPixelSize(R.dimen.button_margin), 2, getResources().getDimensionPixelSize(R.dimen.button_margin), getResources().getDimensionPixelSize(R.dimen.button_margin_bottom));
        deleteButton.setLayoutParams(deleteButtonParams);

//        row.addView(editButton);
        row.addView(deleteButton);

    }
    private void deletePeminjamanOnline(String peminjamanId) {
        peminjamanOnline.deletePeminjamanOnline(peminjamanId, new ApiPeminjamanOnline.ApiResponseCallback<Void>() {
            @Override
            public void onSuccess(Void response) {
                getDataPeminjaman();
            }
            @Override
            public void onError(String error) {
                Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void showDelete(final String peminjamanId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Anda yakin ingin menghapus buku")
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        deletePeminjamanOnline(peminjamanId);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        builder.create().show();
    }

    private void addDataToRow(TableRow row, String data) {
        TextView textView = new TextView(getContext());
        textView.setText(data);
        textView.setPadding(10, 10, 10, 10);
        textView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.whiteT));
        textView.setLayoutParams(getTableRowLayoutParams());
        row.addView(textView);
    }
    private void addHeaderToRow(TableRow row, String header) {
        TextView textView = new TextView(getContext());
        textView.setText(header);
        textView.setPadding(10, 10, 10, 10);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.gray));
        textView.setLayoutParams(getTableRowLayoutParams());
        row.addView(textView);
    }
    private TableRow.LayoutParams getTableRowLayoutParams() {
        TableRow.LayoutParams params = new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(2, 2, 2, 2); // Atur margin untuk garis
        return params;
    }
    public void createExcel(String fileName, String sheetName, List<PeminjamanOnline> peminjamanList) {
        File root = new File(getActivity().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), fileName);
        if(!root.exists()){
            root.mkdirs();
        }
        File path = new File(root, sheetName);
        Workbook workbook = new XSSFWorkbook();

        Sheet sheet = workbook.createSheet(sheetName);
        Row headerRow = sheet.createRow(0);
        String[] columnHeaders = {"Kode Buku", "Nomor Anggota", "Status","Tanggal Peminjaman","Tanggal Pengembalian","Denda"};

        for (int i = 0; i < columnHeaders.length; i++) {
            Cell headerCell = headerRow.createCell(i);
            headerCell.setCellValue(columnHeaders[i]);
        }
        int rowNum = 1;
        for (PeminjamanOnline peminjaman : peminjamanList) {
            Row dataRow = sheet.createRow(rowNum++);
            dataRow.createCell(1).setCellValue(peminjaman.getKodeBuku());
            dataRow.createCell(2).setCellValue(peminjaman.getNomorAnggota());
            dataRow.createCell(3).setCellValue(peminjaman.getStatus());
            dataRow.createCell(4).setCellValue(peminjaman.getTanggalPeminjaman());
            dataRow.createCell(5).setCellValue(peminjaman.getTanggalPengembalian());
        }
        try {
            FileOutputStream outputStream = new FileOutputStream(path);
            workbook.write(outputStream);
            outputStream.close();
            progressDialog.dismiss();
            Toast.makeText(getActivity(), "Berhasil export file", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}