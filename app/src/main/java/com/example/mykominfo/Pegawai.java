package com.example.mykominfo;

import android.os.Parcel;
import android.os.Parcelable;

public class Pegawai implements Parcelable {
    private int id;
    private String nama;
    private String jabatan;
    private int umur;
    private String tanggalLahir;
    private String jenisKelamin;
    private String alamat;
    private double gaji;
    private int durasiKerja;

    public Pegawai(int id, String nama, String jabatan, int umur, String tanggalLahir, String jenisKelamin, String alamat, double gaji, int durasiKerja) {
        this.id = id;
        this.nama = nama;
        this.jabatan = jabatan;
        this.umur = umur;
        this.tanggalLahir = tanggalLahir;
        this.jenisKelamin = jenisKelamin;
        this.alamat = alamat;
        this.gaji = gaji;
        this.durasiKerja = durasiKerja;
    }

    protected Pegawai(Parcel in) {
        id = in.readInt();
        nama = in.readString();
        jabatan = in.readString();
        umur = in.readInt();
        tanggalLahir = in.readString();
        jenisKelamin = in.readString();
        alamat = in.readString();
        gaji = in.readDouble();
        durasiKerja = in.readInt();
    }

    public static final Parcelable.Creator<Pegawai> CREATOR = new Parcelable.Creator<Pegawai>() {
        @Override
        public Pegawai createFromParcel(Parcel in) {
            return new Pegawai(in);
        }

        @Override
        public Pegawai[] newArray(int size) {
            return new Pegawai[size];
        }
    };

    public Pegawai(String nama, String jabatan) {
    }

    public int getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public String getJabatan() {
        return jabatan;
    }

    public int getUmur() {
        return umur;
    }

    public String getTanggalLahir() {
        return tanggalLahir;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public String getAlamat() {
        return alamat;
    }

    public double getGaji() {
        return gaji;
    }

    public int getDurasiKerja() {
        return durasiKerja;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(nama);
        dest.writeString(jabatan);
        dest.writeInt(umur);
        dest.writeString(tanggalLahir);
        dest.writeString(jenisKelamin);
        dest.writeString(alamat);
        dest.writeDouble(gaji);
        dest.writeInt(durasiKerja);
    }
}
