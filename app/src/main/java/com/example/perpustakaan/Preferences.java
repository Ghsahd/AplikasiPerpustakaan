package com.example.perpustakaan;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Preferences {
    static final String KEY_NOMOR_ANGGOTA = "nomor_anggota";
    static final String KEY_EMAIL = "email";
    static final String KEY_PASSWORD = "password";
    static final String KEY_ROLE = "role";
    static final String KEY_NAMA = "nama";
    static final String KEY_ALAMAT = "alamat";
    static final String KEY_NOMOR_TELEPON = "nomor_telepon";

    private static SharedPreferences getSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static String getKeyNomorAnggota(Context context) {
        return getSharedPreferences(context).getString(KEY_NOMOR_ANGGOTA, "");
    }

    // Simpan sesi login
    public static void saveLoginSession(Context context, String email, String password, String role,String NomorAnggota) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_PASSWORD, password);
        editor.putString(KEY_ROLE, role);
        editor.putString(KEY_NOMOR_ANGGOTA,NomorAnggota);
        editor.apply();
    }
    public static void saveDataRegister(Context context, String nama, String alamat, String nomorTelepon) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(KEY_NAMA, nama);
        editor.putString(KEY_ALAMAT, alamat);
        editor.putString(KEY_NOMOR_TELEPON, nomorTelepon);
        editor.apply();
    }
    public static void clearNomorAnggota(Context context) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.remove(KEY_NOMOR_ANGGOTA);
        editor.apply();
    }
    public static String getNama(Context context) {
        return getSharedPreferences(context).getString(KEY_NAMA, "");
    }
}

