package com.example.perpustakaan.MenuFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.perpustakaan.Model.Anggota;
import com.example.perpustakaan.Model.ApiAnggota;
import com.example.perpustakaan.Preferences;
import com.example.perpustakaan.R;

public class EditProfileFragment extends Fragment {

    private EditText editNama, editEmail, editAlamat, editNomorTelepon, editPassword;
    private Button btnSave;

    private ApiAnggota anggotaApi;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        String NomorAnggota = Preferences.getKeyNomorAnggota(getContext());

        editNama = view.findViewById(R.id.editTextNama);
        editEmail = view.findViewById(R.id.editTextEmail);
        editAlamat = view.findViewById(R.id.editTextAlamat);
        editNomorTelepon = view.findViewById(R.id.editTextNomor);
        editPassword = view.findViewById(R.id.editTextPassword);
        btnSave = view.findViewById(R.id.buttonUpdate);

        anggotaApi = new ApiAnggota();
        anggotaApi.getDataAnggota(NomorAnggota, new ApiAnggota.ApiResponseCallback<Anggota>() {
            @Override
            public void onSuccess(Anggota response) {
                // Mengisi form dengan data anggota yang telah diambil
                editNama.setText(response.getNama());
                editEmail.setText(response.getEmail());
                editAlamat.setText(response.getAlamat());
                editNomorTelepon.setText(response.getNomorTelepon());
                editPassword.setText(response.getPassword());
            }

            @Override
            public void onError(String errorMessage) {
                Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ambil data yang diedit oleh pengguna
                String nama = editNama.getText().toString();
                String email = editEmail.getText().toString();
                String alamat = editAlamat.getText().toString();
                String nomorTelepon = editNomorTelepon.getText().toString();
                String password = editPassword.getText().toString();

                Anggota anggota = new Anggota();
                anggota.setNama(nama);
                anggota.setEmail(email);
                anggota.setAlamat(alamat);
                anggota.setPassword(password);
                anggota.setNomorTelepon(nomorTelepon);

                anggotaApi.updateAnggota(NomorAnggota, anggota, new ApiAnggota.ApiResponseCallback<Anggota>() {
                    @Override
                    public void onSuccess(Anggota response) {
                        Toast.makeText(getActivity(), "Profil berhasil diperbarui", Toast.LENGTH_SHORT).show();
                        ProfileFragment profileFragment = new ProfileFragment();
                        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragment_container_anggota, profileFragment);
                        transaction.commit();
                    }
                    @Override
                    public void onError(String errorMessage) {
                        Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        return view;
    }
}
