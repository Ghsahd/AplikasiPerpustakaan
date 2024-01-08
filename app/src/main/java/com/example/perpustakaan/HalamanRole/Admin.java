package com.example.perpustakaan.HalamanRole;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.perpustakaan.LoginNew;
import com.example.perpustakaan.MenuFragment.DataAnggotaFragment;
import com.example.perpustakaan.MenuFragment.DataBukuDigitalFragment;
import com.example.perpustakaan.MenuFragment.DataBukuFragment;
import com.example.perpustakaan.MenuFragment.DataBukuMasukFragment;
import com.example.perpustakaan.MenuFragment.DataBukuRusakFragment;
import com.example.perpustakaan.MenuFragment.DataPeminjamanFragment;
import com.example.perpustakaan.MenuFragment.DataPeminjamanOnline;
import com.example.perpustakaan.MenuFragment.DataPengembalianFragment;
import com.example.perpustakaan.MenuFragment.HomeFragment;
import com.example.perpustakaan.MenuFragment.PeminjamanFragment;
import com.example.perpustakaan.MenuFragment.PeminjamanOnlineFragment;
import com.example.perpustakaan.MenuFragment.TambahBukuDigital;
import com.example.perpustakaan.MenuFragment.TambahBukuFragment;
import com.example.perpustakaan.MenuFragment.TambahBukuRusakFragment;
import com.example.perpustakaan.R;
import com.google.android.material.navigation.NavigationView;

public class Admin extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;
    private long backPress;
    private Toast backToast;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_admin);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(ContextCompat.getDrawable(this, R.drawable.hamburger_1));

        drawerLayout = findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                int id = item.getItemId();

                switch (id) {
                    case R.id.home:
                        selectedFragment = new HomeFragment();
                        break;
                    case R.id.tambah:
                        selectedFragment = new TambahBukuFragment();
                        break;
                    case R.id.tambahBukuDigital:
                        selectedFragment = new TambahBukuDigital();
                        break;
                    case R.id.tambahBukuRusak:
                        selectedFragment = new TambahBukuRusakFragment();
                        break;
                    case R.id.peminjaman:
                        selectedFragment = new PeminjamanFragment();
                        break;
                    case R.id.peminjamanOnline:
                        selectedFragment = new PeminjamanOnlineFragment();
                        break;
                }

                if (selectedFragment != null) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container_admin, selectedFragment)
                            .commit();
                }

                drawerLayout.closeDrawers();
                return true;
            }
        });

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_admin, new HomeFragment()).commit();
        }

        Menu menu = navigationView.getMenu();
        SubMenu dataSubMenu = menu.findItem(R.id.data_menu).getSubMenu();
        MenuItem spinnerMenuItem = dataSubMenu.findItem(R.id.spinner_menu_item);
        Spinner spinner = (Spinner) spinnerMenuItem.getActionView();

        String[] spinnerItems = getResources().getStringArray(R.array.drawer_menu_data);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,spinnerItems);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            private boolean firstSelection = true;
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (firstSelection) {
                    firstSelection = false;
                    return;
                }
                String selectedItem = spinner.getSelectedItem().toString();
                handleSpinnerItemSelected(selectedItem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Tidak ada tindakan khusus ketika tidak ada yang dipilih
            }
        });
    }


    private void handleSpinnerItemSelected(String selectedItem) {
        Fragment selectedFragment = null;
        if (TextUtils.isEmpty(selectedItem) || selectedItem.equals("Pilih Data")) {

        }else {
            switch (selectedItem) {
                case "Data Peminjaman":
                    selectedFragment = new DataPeminjamanFragment();
                    break;
                case "Data Peminjaman Online":
                    selectedFragment = new DataPeminjamanOnline();
                    break;
                case "Data Pengembalian":
                    selectedFragment = new DataPengembalianFragment();
                    break;
                case "Data Anggota":
                    selectedFragment = new DataAnggotaFragment();
                    break;
                case "Data Buku":
                    selectedFragment = new DataBukuFragment();
                    break;
                case "Data Buku Rusak":
                    selectedFragment = new DataBukuRusakFragment();
                    break;
                case "Data Buku Masuk":
                    selectedFragment = new DataBukuMasukFragment();
                    break;
                case "Data Buku Digital":
                    selectedFragment = new DataBukuDigitalFragment();
                    break;
            }
        }


        if (selectedFragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container_admin, selectedFragment)
                    .commit();
        }
        drawerLayout.closeDrawers();
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
                showProgressDialog();
                redirectToAdminPage();
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
        builder.create().show();
    }
    private void showProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Logout...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }
    private void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
    private void redirectToAdminPage() {
        hideProgressDialog();
        Intent intent = new Intent(this, LoginNew.class);
        startActivity(intent);
        finish();
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
