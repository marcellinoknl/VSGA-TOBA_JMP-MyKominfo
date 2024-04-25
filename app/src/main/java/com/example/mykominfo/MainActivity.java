package com.example.mykominfo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.ComponentActivity;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mykominfo.helper.DatabaseHelper;

public class MainActivity extends AppCompatActivity {

    private Button logoutButton;
    private TextView usernameTextView;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inisialisasi DatabaseHelper
        dbHelper = new DatabaseHelper(this);

        // Inisialisasi Component
        logoutButton = findViewById(R.id.logoutButton);
        usernameTextView = findViewById(R.id.textViewUsername);
        Button btnLihatPegawai = findViewById(R.id.buttonLihatDataPegawai);
        Button btnTambahPegawai = findViewById(R.id.buttonInputPegawai);
        Button btnInformasi = findViewById(R.id.buttonInformasi);
        // Set username dari database ke dalam TextView
        setUsernameFromDatabase();

        // Setup onClickListener untuk tombol logout
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        btnLihatPegawai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentLihatData = new Intent(MainActivity.this, DataPegawai.class);
                startActivity(intentLihatData);
            }
        });

        btnInformasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentToIndformasi = new Intent(MainActivity. this, AboutApps.class);
                startActivity(intentToIndformasi);
            }
        });

        btnTambahPegawai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentTambahData = new Intent(MainActivity.this, TambahDataPegawai.class);
                startActivity(intentTambahData);
            }
        });
    }

    // Method untuk mendapatkan username dari database dan menampilkannya di TextView
    private void setUsernameFromDatabase() {
        // Buka database dalam mode membaca
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Definisikan kolom yang akan diambil dari tabel users
        String[] projection = {DatabaseHelper.COLUMN_USERNAME};

        // Lakukan query ke tabel users
        Cursor cursor = db.query(DatabaseHelper.TABLE_USERS, projection, null, null, null, null, null);

        // Cek apakah cursor memiliki data
        if (cursor.moveToFirst()) {
            // Ambil data username dari cursor
            @SuppressLint("Range") String username = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_USERNAME));

            // Set username ke dalam TextView
            usernameTextView.setText(username);
        }

        // Tutup cursor dan database
        cursor.close();
        db.close();
    }

    // Method untuk logout
    private void logout() {
        // Redirect user ke halaman login
        Intent intent = new Intent(MainActivity.this, Login.class);
        startActivity(intent);

        // Tutup activity saat ini dan hapus dari stack activity
        finish();
    }
}
