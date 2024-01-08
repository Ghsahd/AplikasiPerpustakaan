package com.example.perpustakaan;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.perpustakaan.HalamanRole.Admin;
import com.example.perpustakaan.HalamanRole.Anggota;

public class MainActivity extends AppCompatActivity {
    private boolean isAdmin = false;
    private boolean isAnggota = false;
    private int adminLayout;
    private int anggotaLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adminLayout = R.layout.fragment_admin;
        anggotaLayout = R.layout.fragment_anggota;

        Intent intent = getIntent();
        // Mengambil informasi peran pengguna yang telah diatur di LoginNew
        isAdmin = getIntent().getBooleanExtra("isAdmin", false);
        isAnggota = getIntent().getBooleanExtra("isAnggota", false);

        if (isAdmin) {
            isAdmin = true;
            // memanggil class Admin yang berada di package HalamanRole
            Intent adminIntent = new Intent(this, Admin.class);
            startActivity(adminIntent);
            finish();
        } else if (isAnggota) {
            isAnggota = true;
            // memanggil class Anggota yang berada di package HalamanRole
            Intent anggotaIntent = new Intent(this, Anggota.class);
            startActivity(anggotaIntent);
            finish();
        }
    }
}
