package com.example.perpustakaan.MenuFragment;

import android.app.ProgressDialog;
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
import com.example.perpustakaan.Model.Peminjaman;
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


public class DataPengembalianFragment extends Fragment {
    private List<Peminjaman> pengembalianList = new ArrayList<>();
    private View view;
    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_data_pengembalian, container, false);

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
                createExcel("Data Pengembalian", "Data Pengembalian.xlsx", pengembalianList);
            }
        });

        return view;
    }
    private void getDataPeminjaman() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        UserService apiService = ApiClient.getClient().create(UserService.class);
        Call<List<Peminjaman>> call = apiService.GetPengembalian();

        call.enqueue(new Callback<List<Peminjaman>>() {
            @Override
            public void onResponse(Call<List<Peminjaman>> call, Response<List<Peminjaman>> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    List<Peminjaman> newPeminjamanList = response.body();
                    if (newPeminjamanList != null && !newPeminjamanList.isEmpty()) {
                        pengembalianList.clear();
                        pengembalianList.addAll(newPeminjamanList);
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
                        addHeaderToRow(headerRow, "Denda");
                        tableLayout.addView(headerRow);
                        for (Peminjaman peminjaman : pengembalianList) {
                            TableRow row = new TableRow(getContext());

                            addDataToRow(row, String.valueOf(nomorUrut++)); // Nomor Urut (ditambahkan dan kemudian ditingkatkan)
                            addDataToRow(row, peminjaman.getKodeBuku()); // Kode Buku
                            addDataToRow(row, peminjaman.getNomorAnggota()); // Nomor Anggota
                            addDataToRow(row, peminjaman.getStatus()); // Status
                            addDataToRow(row, peminjaman.getTanggalPeminjaman()); // Tanggal Peminjaman
                            addDataToRow(row, peminjaman.getTanggalPengembalian()); // Tanggal Pengembalian
                            addDataToRow(row, peminjaman.getDenda()); // Denda
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
            public void onFailure(Call<List<Peminjaman>> call, Throwable t) {
                showError("Permintaan Gagal. Error: " + t.getMessage());
            }
        });
    }
    private void showError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
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
    public void createExcel(String fileName, String sheetName, List<Peminjaman> peminjamanList) {
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
        for (Peminjaman peminjaman : peminjamanList) {
            Row dataRow = sheet.createRow(rowNum++);
            dataRow.createCell(1).setCellValue(peminjaman.getKodeBuku());
            dataRow.createCell(2).setCellValue(peminjaman.getNomorAnggota());
            dataRow.createCell(3).setCellValue(peminjaman.getStatus());
            dataRow.createCell(4).setCellValue(peminjaman.getTanggalPeminjaman());
            dataRow.createCell(5).setCellValue(peminjaman.getTanggalPengembalian());
            dataRow.createCell(6).setCellValue(peminjaman.getDenda());
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