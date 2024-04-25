package com.example.mykominfo;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mykominfo.helper.DatabaseHelper;

import java.util.ArrayList;

public class EditDataPegawai extends AppCompatActivity {

    private EditText editTextNama, editTextJabatan, editTextUmur, editTextTglLahir,
            editTextJenisKelamin, editTextAlamat, editTextGaji, editTextDurasiKerja;

    private Button buttonEdit;
    private DatabaseHelper databaseHelper;
    private Pegawai pegawai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data_pegawai);
        // Initialize Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Enable back button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        // Initialize views
        editTextNama = findViewById(R.id.editTextNama);
        editTextJabatan = findViewById(R.id.editTextJabatan);
        editTextUmur = findViewById(R.id.editTextUmur);
        editTextTglLahir = findViewById(R.id.editTextTglLahir);
        editTextJenisKelamin = findViewById(R.id.editTextJenisKelamin);
        editTextAlamat = findViewById(R.id.editTextAlamat);
        editTextGaji = findViewById(R.id.editTextGaji);
        editTextDurasiKerja = findViewById(R.id.editTextDurasiKerja);
        buttonEdit = findViewById(R.id.buttonEdit);

        // Initialize database helper
        databaseHelper = new DatabaseHelper(this);

        // Get data pegawai from intent
        pegawai = getIntent().getParcelableExtra("pegawai");

        // Set data pegawai to EditText
        assert pegawai != null;
        editTextNama.setText(pegawai.getNama());
        editTextJabatan.setText(pegawai.getJabatan());
        editTextUmur.setText(String.valueOf(pegawai.getUmur()));
        editTextTglLahir.setText(pegawai.getTanggalLahir());
        editTextJenisKelamin.setText(pegawai.getJenisKelamin());
        editTextAlamat.setText(pegawai.getAlamat());
        editTextGaji.setText(String.valueOf(pegawai.getGaji()));
        editTextDurasiKerja.setText(String.valueOf(pegawai.getDurasiKerja()));

        // Set click listener for buttonEdit
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get edited data from EditText
                String nama = editTextNama.getText().toString().trim();
                String jabatan = editTextJabatan.getText().toString().trim();
                int umur = Integer.parseInt(editTextUmur.getText().toString().trim());
                String tglLahir = editTextTglLahir.getText().toString().trim();
                String jenisKelamin = editTextJenisKelamin.getText().toString().trim();
                String alamat = editTextAlamat.getText().toString().trim();
                double gaji = Double.parseDouble(editTextGaji.getText().toString().trim());
                int durasiKerja = Integer.parseInt(editTextDurasiKerja.getText().toString().trim());

                // Update pegawai data in database
                boolean updated = databaseHelper.updatePegawai(pegawai.getId(), nama, umur, jabatan, tglLahir, jenisKelamin, alamat, gaji, durasiKerja);
                if (updated) {
                    // Data berhasil di update, kembali ke halaman(activity) sebelumnya
                    Toast.makeText(EditDataPegawai.this, "Data berhasil diperbarui", Toast.LENGTH_SHORT).show();
                    // Refresh the RecyclerView data after update
                    refreshRecyclerView();
                    Intent intentUpdateData = new Intent(EditDataPegawai.this, MainActivity.class);
                    startActivity(intentUpdateData);
                    finish();
                } else {
                    // Pesan error ketika gagal update data
                    Toast.makeText(EditDataPegawai.this, "Gagal memperbarui data", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Method to refresh RecyclerView data
    private void refreshRecyclerView() {
        // Get updated data from database
        ArrayList<Pegawai> updatedData = databaseHelper.getAllPegawai();

        // Notify the RecyclerView adapter with updated data
        RecyclerView recyclerView = findViewById(R.id.recyclerViewPegawai);
        if (recyclerView != null && recyclerView.getAdapter() instanceof PegawaiAdapter) {
            ((PegawaiAdapter) recyclerView.getAdapter()).setData(updatedData);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
