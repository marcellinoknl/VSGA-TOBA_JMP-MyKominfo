package com.example.mykominfo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class DetailDataPegawaiActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_data_pegawai);

        // Initialize Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        Button btnEdit = findViewById(R.id.buttonEditDetail);
        setSupportActionBar(toolbar);

        // Fungsi Back button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        // Accept data pegawai dari intent (libs parcel)
        Pegawai pegawai = getIntent().getParcelableExtra("pegawai");

        // Tampilkan data pegawai
        TextView textViewNama = findViewById(R.id.textViewNama);
        TextView textViewJabatan = findViewById(R.id.textViewJabatan);
        TextView textViewUmur = findViewById(R.id.textViewUmur);
        TextView textViewTglLahir = findViewById(R.id.textViewTglLahir);
        TextView textViewJenisKelamin = findViewById(R.id.textViewJenisKelamin);
        TextView textViewAlamat = findViewById(R.id.textViewAlamat);
        TextView textViewGaji = findViewById(R.id.textViewGaji);
        TextView textViewDurasiKerja = findViewById(R.id.textViewDurasiKerja);

        if (pegawai != null) {
            textViewNama.setText("Nama: " + pegawai.getNama());
            textViewJabatan.setText("Jabatan: " + pegawai.getJabatan());
            textViewUmur.setText("Umur: " + pegawai.getUmur() + " tahun");
            textViewTglLahir.setText("Tanggal Lahir: " + pegawai.getTanggalLahir());
            textViewJenisKelamin.setText("Jenis Kelamin: " + pegawai.getJenisKelamin());
            textViewAlamat.setText("Alamat: " + pegawai.getAlamat());
            textViewGaji.setText("Gaji: " + "Rp " + pegawai.getGaji());
            textViewDurasiKerja.setText("Durasi Kerja: " + pegawai.getDurasiKerja() + " tahun");
        }

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentToEditData = new Intent(DetailDataPegawaiActivity.this, EditDataPegawai.class);
                intentToEditData.putExtra("pegawai", pegawai);
                startActivity(intentToEditData);
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
