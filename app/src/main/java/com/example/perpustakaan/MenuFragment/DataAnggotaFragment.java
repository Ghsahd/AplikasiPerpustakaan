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
import com.example.perpustakaan.Model.Anggota;
import com.example.perpustakaan.Model.ApiAnggota;
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


public class DataAnggotaFragment extends Fragment {
    private List<Anggota> anggotaList = new ArrayList<>();
    private View view;
    private ProgressDialog progressDialog;
    private ApiAnggota apiAnggota = new ApiAnggota();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_data_anggota, container, false);

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
                createExcel("Data Anggota", "Data Anggota.xlsx", anggotaList);
            }
        });

        return view;
    }
    private void getDataPeminjaman() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        UserService apiService = ApiClient.getClient().create(UserService.class);
        Call<List<Anggota>> call = apiService.GetAnggota();

        call.enqueue(new Callback<List<Anggota>>() {
            @Override
            public void onResponse(Call<List<Anggota>> call, Response<List<Anggota>> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    List<Anggota> newAnggotaList = response.body();
                    if (newAnggotaList != null && !newAnggotaList.isEmpty()) {
                        anggotaList.clear();
                        anggotaList.addAll(newAnggotaList);
                        TableLayout tableLayout = view.findViewById(R.id.tableLayout2);
                        tableLayout.removeAllViews();
                        int nomorUrut = 1;
                        TableRow headerRow = new TableRow(getContext());
                        addHeaderToRow(headerRow, "No");
                        addHeaderToRow(headerRow, "Nomor Anggota");
                        addHeaderToRow(headerRow, "Nama");
                        addHeaderToRow(headerRow, "Email");
                        addHeaderToRow(headerRow, "Alamat");
                        addHeaderToRow(headerRow, "Nomor Telepon");
                        addHeaderToRow(headerRow, "Password");
                        tableLayout.addView(headerRow);
                        for (Anggota anggota : anggotaList) {
                            TableRow row = new TableRow(getContext());

                            addDataToRow(row, String.valueOf(nomorUrut++));
                            addDataToRow(row, anggota.getnomorAnggota());
                            addDataToRow(row, anggota.getNama());
                            addDataToRow(row, anggota.getEmail());
                            addDataToRow(row, anggota.getAlamat());
                            addDataToRow(row, anggota.getNomorTelepon());
                            addDataToRow(row, anggota.getPassword());
                            addActionsToRow(row, tableLayout.getContext(), anggota.getIdAnggota());
                            tableLayout.addView(row);
                        }
                    } else {
                        showError("Tidak ada anggota yang ditemukan.");
                    }
                } else {
                    int statusCode = response.code();
                    showError("Gagal mengambil data anggota. Kode Status: " + statusCode);
                }
            }

            @Override
            public void onFailure(Call<List<Anggota>> call, Throwable t) {
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
    private void deleteAnggota(String anggotaId) {
        apiAnggota.deleteAnggota(anggotaId, new ApiAnggota.ApiResponseCallback<Void>(){
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
    private void showDelete(final String anggotaId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Anda yakin ingin menghapus buku")
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        deleteAnggota(anggotaId);
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
    public void createExcel(String fileName, String sheetName, List<Anggota> anggotaList) {
        File root = new File(getActivity().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), fileName);
        if(!root.exists()){
            root.mkdirs();
        }
        File path = new File(root, sheetName);
        Workbook workbook = new XSSFWorkbook();

        Sheet sheet = workbook.createSheet(sheetName);
        Row headerRow = sheet.createRow(0);
        String[] columnHeaders = {"Nomor Anggota", "Nama","Email","Alamat","Nomor Telepon","Password"};

        for (int i = 0; i < columnHeaders.length; i++) {
            Cell headerCell = headerRow.createCell(i);
            headerCell.setCellValue(columnHeaders[i]);
        }
        int rowNum = 1;
        for (Anggota anggota : anggotaList) {
            Row dataRow = sheet.createRow(rowNum++);
            dataRow.createCell(1).setCellValue(anggota.getnomorAnggota());
            dataRow.createCell(2).setCellValue(anggota.getNama());
            dataRow.createCell(3).setCellValue(anggota.getEmail());
            dataRow.createCell(4).setCellValue(anggota.getAlamat());
            dataRow.createCell(5).setCellValue(anggota.getNomorTelepon());
            dataRow.createCell(6).setCellValue(anggota.getPassword());
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