package com.example.perpustakaan;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.perpustakaan.Model.ApiUser;
import com.example.perpustakaan.Model.User;

public class LoginNew extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_new);

        // Inisialisasi elemen-elemen UI
        emailEditText = findViewById(R.id.editTextEmail);
        passwordEditText = findViewById(R.id.editTextPassword);
        loginButton = findViewById(R.id.buttonLogin);

        // Memberikan aksi klik pada tombol login
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                if(email.isEmpty() || email.equals("")) {
                    Toast.makeText(getApplicationContext(), "Email harus diisi", Toast.LENGTH_SHORT).show();
                } else if(password.isEmpty() || password.equals("")) {
                    Toast.makeText(getApplicationContext(), "Password harus diisi", Toast.LENGTH_SHORT).show();
                } else {
                    // Panggil metode login
                    loginUser(email, password);
                }

            }
        });
    }

    private void loginUser(String email, String password) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        ApiUser apiUser = new ApiUser();

        apiUser.loginUser(email, password, new ApiUser.ApiResponseCallback<User>() {
            @Override
            public void onSuccess(User user) {
                if (user != null) {

                    String getNomorAnggota = user.getNomorAnggota();

                    // Login berhasil, cek peran (role) pengguna
                    if (user.getRole().equals("Admin")) {
                        progressDialog.dismiss();
                        Preferences.clearNomorAnggota(LoginNew.this);
                        saveLoginSession(email, password, "Admin","");
                        showAdmin();
                    } else if (user.getRole().equals("Anggota")) {
                        progressDialog.dismiss();
                        if (!getNomorAnggota.isEmpty()) {
                            saveLoginSession(email, password, "Anggota", getNomorAnggota);
                            showAnggota();
                        } else {
                            Toast.makeText(LoginNew.this, "Tidak Mendapatkan Nomor Anggota", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // Peran tidak dikenali, tampilkan pesan kesalahan
                        Toast.makeText(LoginNew.this, "Peran tidak valid", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginNew.this, "Objek pengguna null", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(String error) {
                Log.e("LoginNew", "Login Error: " + error);
                // Tangani kesalahan login, misalnya, menampilkan pesan kesalahan.
                Toast.makeText(LoginNew.this, "Login gagal: " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveLoginSession(String email, String password, String role,String IdAnggota) {
        Preferences.saveLoginSession(LoginNew.this, email, password, role,IdAnggota);
    }

    private void showAdmin() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("isAdmin", true);
        startActivity(intent);
    }
    private void showAnggota() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("isAnggota", true);
        startActivity(intent);
    }
    public void openRegisterActivity(View view) {
        Intent intent = new Intent(this, RegisterNew.class);
        startActivity(intent);
    }


}
