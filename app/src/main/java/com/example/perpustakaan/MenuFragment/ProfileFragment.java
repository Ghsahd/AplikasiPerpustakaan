package com.example.perpustakaan.MenuFragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.perpustakaan.Model.Anggota;
import com.example.perpustakaan.Model.ApiAnggota;
import com.example.perpustakaan.Preferences;
import com.example.perpustakaan.R;

public class ProfileFragment extends Fragment {
    private TextView textNomorAnggota;
    private TextView textNama;
    private TextView textEmail;
    private TextView textAlamat;
    private TextView textNomorTelepon;
    private TextView textPassword;
    private ApiAnggota anggotaApi;
    private ProgressDialog progressDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        String NomorAnggota = Preferences.getKeyNomorAnggota(getContext());

        textNomorAnggota = view.findViewById(R.id.textNomorAnggota);
        textNama = view.findViewById(R.id.textNama);
        textEmail = view.findViewById(R.id.textEmail);
        textAlamat = view.findViewById(R.id.textAlamat);
        textNomorTelepon = view.findViewById(R.id.textNomor);
        textPassword = view.findViewById(R.id.textPassword);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        setHasOptionsMenu(true);
        anggotaApi = new ApiAnggota();
        anggotaApi.getDataAnggota(NomorAnggota, new ApiAnggota.ApiResponseCallback<Anggota>() {
            @Override
            public void onSuccess(Anggota response) {
                progressDialog.dismiss();
                textNomorAnggota.setText(response.getnomorAnggota());
                textNama.setText(response.getNama());
                textEmail.setText(response.getEmail());
                textAlamat.setText(response.getAlamat());
                textNomorTelepon.setText(response.getNomorTelepon());
                textPassword.setText(response.getPassword());
            }

            @Override
            public void onError(String errorMessage) {
                Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
        progressDialog.dismiss();
        return view;
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_options, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Fragment selectedFragment = null;

        switch (id) {
            case R.id.edit:
                selectedFragment = new EditProfileFragment();
                break;
        }

        if (getActivity() != null) {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container_anggota, selectedFragment)
                    .commit();
        }
        return true;
    }
}
