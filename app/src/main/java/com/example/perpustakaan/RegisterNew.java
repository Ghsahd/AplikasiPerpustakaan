package com.example.perpustakaan;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.perpustakaan.Model.Anggota;
import com.example.perpustakaan.Model.ApiAnggota;

public class RegisterNew extends AppCompatActivity {

    private EditText editTextNama, editTextEmail, editTextAlamat, editTextNomorTelepon, editTextPassword, editTextConfirmPassword;
    private Button buttonRegistrasi;
    private ApiAnggota apiAnggota;
    private TextView textViewLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_new);

        // Inisialisasi tampilan
        editTextNama = findViewById(R.id.editTextUserName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextAlamat = findViewById(R.id.editTextAlamat);
        editTextNomorTelepon = findViewById(R.id.editTextNomor);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        buttonRegistrasi = findViewById(R.id.buttonRegister);
        textViewLogin = findViewById(R.id.textViewLogin);

        // Inisialisasi ApiAnggota
        apiAnggota = new ApiAnggota();
        // Menangani tombol registrasi
        buttonRegistrasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mendapatkan data dari input pengguna
                String nama = editTextNama.getText().toString().trim();
                String email = editTextEmail.getText().toString().trim();
                String alamat = editTextAlamat.getText().toString().trim();
                String nomorTelepon = editTextNomorTelepon.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();
                String confirmPassword = editTextConfirmPassword.getText().toString().trim();

                // Memeriksa apakah semua field telah diisi
                if (email.isEmpty() || password.isEmpty() || nama.isEmpty() || alamat.isEmpty() || nomorTelepon.isEmpty() || confirmPassword.isEmpty()) {
                    Toast.makeText(RegisterNew.this, "Harap isi semua field", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(confirmPassword)) {
                    // Password dan Konfirmasi Password tidak cocok
                    Toast.makeText(RegisterNew.this, "Password dan Konfirmasi Password harus sama", Toast.LENGTH_SHORT).show();
                } else {
                    apiAnggota.RegistrasiAnggota(nama,email,alamat, nomorTelepon,password, new ApiAnggota.ApiResponseCallback<Anggota>() {
                        @Override
                        public void onSuccess(Anggota response) {
                            Preferences.saveDataRegister(RegisterNew.this, nama, alamat, nomorTelepon);
                            Toast.makeText(RegisterNew.this, "Registrasi berhasil", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        @Override
                        public void onError(String error) {
                            // Menampilkan pesan kesalahan jika registrasi gagal
                            Toast.makeText(RegisterNew.this, error, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        // Menambahkan onClickListener untuk kembali ke halaman login
        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToLogin();
            }
        });
    }

    // Metode untuk kembali ke halaman login
    private void backToLogin() {
        finish(); // Menutup Activity registrasi dan kembali ke halaman login
    }
}
