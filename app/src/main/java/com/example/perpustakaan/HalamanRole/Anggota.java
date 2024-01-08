package com.example.perpustakaan.HalamanRole;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.perpustakaan.LoginNew;
import com.example.perpustakaan.MenuFragment.BukuFragment;
import com.example.perpustakaan.MenuFragment.BukuOnline;
import com.example.perpustakaan.MenuFragment.HomeFragment;
import com.example.perpustakaan.MenuFragment.ProfileFragment;
import com.example.perpustakaan.MenuFragment.RiwayatPeminjamanFragment;
import com.example.perpustakaan.Model.ApiAnggota;
import com.example.perpustakaan.Preferences;
import com.example.perpustakaan.R;
import com.google.android.material.navigation.NavigationView;

public class Anggota extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;
    private long backPress;
    private Toast backToast;
    private TextView user;
    private ApiAnggota anggotaApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_anggota);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(ContextCompat.getDrawable(this, R.drawable.hamburger_1));

        drawerLayout = findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,toolbar,R.string.open_drawer, R.string.close_drawer);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        navigationView = findViewById(R.id.navigation_view);

        String NomorAnggota = Preferences.getKeyNomorAnggota(this);
        user = findViewById(R.id.usernameTextView);
        anggotaApi = new ApiAnggota();
        anggotaApi.getDataAnggota(NomorAnggota, new ApiAnggota.ApiResponseCallback<com.example.perpustakaan.Model.Anggota>() {
            @Override
            public void onSuccess(com.example.perpustakaan.Model.Anggota response) {
                user.setText(response.getNama());
            }

            @Override
            public void onError(String errorMessage) {
                Toast.makeText(Anggota.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                Fragment selectedFragment = null;
                int id = item.getItemId();
                switch (id) {
                    case R.id.home:
                        selectedFragment = new HomeFragment();
                        break;
                    case R.id.buku:
                        selectedFragment = new BukuFragment();
                        break;
                    case R.id.bukuOnline:
                        selectedFragment = new BukuOnline();
                        break;
                    case R.id.riwayatPeminjaman:
                        selectedFragment = new RiwayatPeminjamanFragment();
                        break;
                    case R.id.profil:
                        selectedFragment = new ProfileFragment();
                        break;
                }
                if (selectedFragment != null) {
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container_anggota, selectedFragment);
                    transaction.commit();
                }
                drawerLayout.closeDrawers();

                return true;
            }
        });
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_anggota, new HomeFragment()).commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void handleKeluarAction(View view) {
        performLogout();
    }

    private void performLogout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Konfirmasi Keluar");
        builder.setMessage("Apakah Anda ingin keluar?");

        // Tombol "OK" untuk logout
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Lakukan logout
                // Misalnya, Anda dapat melakukan tindakan logout di sini.
                redirectToLoginPage(); // Ganti dengan halaman login yang sesuai
            }
        });

        // Tombol "Batal" untuk membatalkan
        builder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Tidak melakukan logout, biarkan pengguna tetap di halaman
                dialog.dismiss();
            }
        });

        // Tampilkan dialog verifikasi
        builder.create().show();
    }

    private void redirectToLoginPage() {
        // Lakukan pengalihan ke halaman login di sini
        // Misalnya, Anda dapat menggunakan intent atau metode lainnya.
        Intent intent = new Intent(this, LoginNew.class);
        startActivity(intent);
        finish(); // Menutup halaman saat kembali ke halaman login
    }

    @Override
    public void onBackPressed() {
        // Jika pengguna berada di halaman lain, tampilkan pesan "Klik dua kali untuk keluar"
        if (backPress + 2000 > System.currentTimeMillis()) {
            backToast.cancel();
            finishAffinity();
            System.exit(0);
            super.onBackPressed();
        } else {
            backToast = Toast.makeText(getBaseContext(), "Klik dua kali untuk keluar", Toast.LENGTH_SHORT);
            backToast.show();
        }
        backPress = System.currentTimeMillis();
    }
}

