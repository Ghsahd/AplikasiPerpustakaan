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
import com.example.perpustakaan.Model.Buku;
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


public class DataBukuMasukFragment extends Fragment {
    private List<Buku> bukuList = new ArrayList<>();
    private View view;
    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_data_buku_masuk, container, false);

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
                createExcel("Data Pengembalian", "Data Pengembalian.xlsx", bukuList);
            }
        });

        return view;
    }
    private void getDataPeminjaman() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        UserService apiService = ApiClient.getClient().create(UserService.class);
        Call<List<Buku>> call = apiService.GetBukuMasuk();

        call.enqueue(new Callback<List<Buku>>() {
            @Override
            public void onResponse(Call<List<Buku>> call, Response<List<Buku>> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    List<Buku> newBukuList = response.body();
                    if (newBukuList != null && !newBukuList.isEmpty()) {
                        bukuList.clear();
                        bukuList.addAll(newBukuList);
                        TableLayout tableLayout = view.findViewById(R.id.tableLayout2);
                        tableLayout.removeAllViews();
                        int nomorUrut = 1;

                        TableRow headerRow = new TableRow(getContext());
                        addHeaderToRow(headerRow, "No");
                        addHeaderToRow(headerRow, "Kode Buku");
                        addHeaderToRow(headerRow, "Judul Buku");
                        addHeaderToRow(headerRow, "Pengarang");
                        addHeaderToRow(headerRow, "Penerbit");
                        addHeaderToRow(headerRow, "Tahun terbit");
                        addHeaderToRow(headerRow, "Kategori");
                        addHeaderToRow(headerRow, "Jumlah");
                        addHeaderToRow(headerRow, "Lokasi Buku");
                        addHeaderToRow(headerRow, "Lokasi Terbit");
                        tableLayout.addView(headerRow);
                        for (Buku buku : bukuList) {
                            TableRow row = new TableRow(getContext());

                            addDataToRow(row, String.valueOf(nomorUrut++));
                            addDataToRow(row, buku.getKodeBuku());
                            addDataToRow(row, buku.getJudulBuku());
                            addDataToRow(row, buku.getPengarang());
                            addDataToRow(row, buku.getPenerbit());
                            addDataToRow(row, buku.getTahunTerbit());
                            addDataToRow(row, buku.getKategori());
                            addDataToRow(row, buku.getJumlah());
                            addDataToRow(row, buku.getLokasiBuku());
                            addDataToRow(row, buku.getLokasiTerbit());
                            tableLayout.addView(row);
                        }
                    } else {
                        showError("Tidak ada peminjaman yang ditemukan.");
                    }
                } else {
                    int statusCode = response.code();
                    showError("Gagal mengambil data buku. Kode Status: " + statusCode);
                }
            }

            @Override
            public void onFailure(Call<List<Buku>> call, Throwable t) {
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
    public void createExcel(String fileName, String sheetName, List<Buku> bukuList) {
        File root = new File(getActivity().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), fileName);
        if(!root.exists()){
            root.mkdirs();
        }
        File path = new File(root, sheetName);
        Workbook workbook = new XSSFWorkbook();

        Sheet sheet = workbook.createSheet(sheetName);
        Row headerRow = sheet.createRow(0);
        String[] columnHeaders = {"Kode Buku", "Judul Buku", "Pengarang","Penerbit","Tahun Terbit","Kategori","Jumlah","Lokasi Buku","Lokasi Terbit"};

        for (int i = 0; i < columnHeaders.length; i++) {
            Cell headerCell = headerRow.createCell(i);
            headerCell.setCellValue(columnHeaders[i]);
        }
        int rowNum = 1;
        for (Buku buku : bukuList) {
            Row dataRow = sheet.createRow(rowNum++);
            dataRow.createCell(1).setCellValue(buku.getKodeBuku());
            dataRow.createCell(2).setCellValue(buku.getJudulBuku());
            dataRow.createCell(3).setCellValue(buku.getPengarang());
            dataRow.createCell(4).setCellValue(buku.getPenerbit());
            dataRow.createCell(5).setCellValue(buku.getTahunTerbit());
            dataRow.createCell(6).setCellValue(buku.getKategori());
            dataRow.createCell(7).setCellValue(buku.getJumlah());
            dataRow.createCell(8).setCellValue(buku.getLokasiBuku());
            dataRow.createCell(9).setCellValue(buku.getLokasiTerbit());
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