package com.example.mykominfo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mykominfo.helper.DatabaseHelper;

import java.util.ArrayList;

public class DataPegawai extends AppCompatActivity {

    private RecyclerView recyclerViewPegawai;
    private DatabaseHelper databaseHelper;
    private PegawaiAdapter pegawaiAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_pegawai);

        // Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Back button ketika di klik maka kembali
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        recyclerViewPegawai = findViewById(R.id.recyclerViewPegawai);
        recyclerViewPegawai.setLayoutManager(new LinearLayoutManager(this));

        databaseHelper = new DatabaseHelper(this);
        ArrayList<Pegawai> listPegawai = databaseHelper.getAllPegawai();

        pegawaiAdapter = new PegawaiAdapter(this, listPegawai);
        recyclerViewPegawai.setAdapter(pegawaiAdapter);

        // item click listener pada adapter
        pegawaiAdapter.setOnItemClickListener(new PegawaiAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // Fungsi Kosong karena tidak ada aksi
            }

            @Override
            public void onItemClick(Pegawai pegawai) {
                showOptionsDialog(pegawai);
            }
        });
    }

    // Menampilkan dialog dengan pilihan lihat, update, dan hapus data
    private void showOptionsDialog(final Pegawai pegawai) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pilihan");
        String[] options = {"Lihat Data", "Update Data", "Hapus Data"};
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        Intent detailIntent = new Intent(DataPegawai.this, DetailDataPegawaiActivity.class);
                        detailIntent.putExtra("pegawai", pegawai);
                        startActivity(detailIntent);
                        break;
                    case 1:
                        Intent editIntent = new Intent(DataPegawai.this, EditDataPegawai.class);
                        editIntent.putExtra("pegawai", pegawai);
                        startActivity(editIntent);
                        break;
                    case 2:
                        showDeleteConfirmationDialog(pegawai);
                        break;
                }
            }
        });
        builder.show();
    }

    private void showDeleteConfirmationDialog(final Pegawai pegawai) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Konfirmasi Hapus");
        builder.setMessage("Apakah Anda yakin ingin menghapus data pegawai?");
        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Hapus data pegawai dari database
                databaseHelper.deletePegawai(pegawai.getId());
                // Refresh RecyclerView setelah penghapusan
                refreshRecyclerView();
            }
        });
        builder.setNegativeButton("Tidak", null);
        builder.show();
    }

    // Method untuk memperbarui RecyclerView setelah perubahan data
    private void refreshRecyclerView() {
        ArrayList<Pegawai> updatedList = databaseHelper.getAllPegawai();
        pegawaiAdapter.setData(updatedList);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}




