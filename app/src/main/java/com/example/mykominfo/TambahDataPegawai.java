package com.example.mykominfo;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.mykominfo.helper.DatabaseHelper;

public class TambahDataPegawai extends AppCompatActivity {

    private EditText editTextNama, editTextUmur, editTextJabatan, editTextTanggalLahir, editTextJenisKelamin, editTextAlamat, editTextGajiBulanan, editTextLamaBekerja;
    private Button buttonTambah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_data_pegawai);
        // Initialize Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Enable back button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        // Inisialisasi views
        editTextNama = findViewById(R.id.editTextNama);
        editTextUmur = findViewById(R.id.editTextUmur);
        editTextJabatan = findViewById(R.id.editTextJabatan);
        editTextTanggalLahir = findViewById(R.id.editTextTanggalLahir);
        editTextJenisKelamin = findViewById(R.id.editTextJenisKelamin);
        editTextAlamat = findViewById(R.id.editTextAlamat);
        editTextGajiBulanan = findViewById(R.id.editTextGajiBulanan);
        editTextLamaBekerja = findViewById(R.id.editTextLamaBekerja);
        buttonTambah = findViewById(R.id.buttonTambah);

        // Menambahkan onClickListener untuk tombol Tambah Data
        buttonTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tambahDataPegawai();
            }
        });
    }

    private void tambahDataPegawai() {
        // Ambil data input dari EditText
        String nama = editTextNama.getText().toString().trim();
        String umurStr = editTextUmur.getText().toString().trim();
        String jabatan = editTextJabatan.getText().toString().trim();
        String tanggalLahir = editTextTanggalLahir.getText().toString().trim();
        String jenisKelamin = editTextJenisKelamin.getText().toString().trim();
        String alamat = editTextAlamat.getText().toString().trim();
        String gajiBulananStr = editTextGajiBulanan.getText().toString().trim();
        String lamaBekerjaStr = editTextLamaBekerja.getText().toString().trim();

        // Cek apakah kolom input kosong
        if (nama.isEmpty() || umurStr.isEmpty() || jabatan.isEmpty() || tanggalLahir.isEmpty() ||
                jenisKelamin.isEmpty() || alamat.isEmpty() || gajiBulananStr.isEmpty() || lamaBekerjaStr.isEmpty()) {
            // Tampilkan pesan kesalahan jika ada kolom input kosong
            Toast.makeText(this, "Harap isi semua kolom", Toast.LENGTH_SHORT).show();
            return;
        }

        int umur = Integer.parseInt(umurStr);
        double gajiBulanan = Double.parseDouble(gajiBulananStr);
        int lamaBekerja = Integer.parseInt(lamaBekerjaStr);

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        boolean isSuccess = databaseHelper.tambahDataPegawai(nama, umur, jabatan, tanggalLahir, jenisKelamin, alamat, gajiBulanan, lamaBekerja);

        // Tampilkan pesan berhasil atau gagal
        if (isSuccess) {
            Toast.makeText(this, "Data pegawai berhasil ditambahkan", Toast.LENGTH_SHORT).show();
            Intent intentDataPegawai = new Intent(TambahDataPegawai.this,DataPegawai.class);
            startActivity(intentDataPegawai);
            finish();
        } else {
            Toast.makeText(this, "Gagal menambahkan data pegawai", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
