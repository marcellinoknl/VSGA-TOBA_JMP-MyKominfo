package com.example.mykominfo.helper;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.mykominfo.Pegawai;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "mykominfo.db";
    private static final int DATABASE_VERSION = 2; // versi nomor db

    // Table name and columns for user data
    public static final String TABLE_USERS = "users";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASSWORD = "password";

    // Table name and columns for Pegawai data
    public static final String TABLE_OFFICER = "officer";
    public static final String COLUMN_OFFICER_ID = "officer_id";
    public static final String COLUMN_OFFICER_NAME = "officer_name";
    public static final String COLUMN_OFFICER_AGE = "officer_age";
    public static final String COLUMN_OFFICER_POSITION = "officer_position";
    public static final String COLUMN_OFFICER_DOB = "officer_dob";
    public static final String COLUMN_OFFICER_GENDER = "officer_gender";
    public static final String COLUMN_OFFICER_ADDRESS = "officer_address";
    public static final String COLUMN_OFFICER_SALARY = "officer_salary";
    public static final String COLUMN_OFFICER_WORK_DURATION = "officer_work_duration";

    // SQL statement to create the users table
    private static final String CREATE_USERS_TABLE =
            "CREATE TABLE " + TABLE_USERS + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_USERNAME + " TEXT," +
                    COLUMN_EMAIL + " TEXT," +
                    COLUMN_PASSWORD + " TEXT" + ")";

    // SQL statement to create the officer table
    private static final String CREATE_OFFICER_TABLE =
            "CREATE TABLE " + TABLE_OFFICER + "(" +
                    COLUMN_OFFICER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_OFFICER_NAME + " TEXT," +
                    COLUMN_OFFICER_AGE + " INTEGER," +
                    COLUMN_OFFICER_POSITION + " TEXT," +
                    COLUMN_OFFICER_DOB + " TEXT," +
                    COLUMN_OFFICER_GENDER + " TEXT," +
                    COLUMN_OFFICER_ADDRESS + " TEXT," +
                    COLUMN_OFFICER_SALARY + " REAL," +
                    COLUMN_OFFICER_WORK_DURATION + " INTEGER" + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create table
        db.execSQL(CREATE_USERS_TABLE);
        db.execSQL(CREATE_OFFICER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OFFICER);
        onCreate(db);
    }

    // Insert Data Pegawai to DB
    public boolean tambahDataPegawai(String name, int age, String position, String dob, String gender, String address, double salary, int workDuration) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_OFFICER_NAME, name);
        contentValues.put(COLUMN_OFFICER_AGE, age);
        contentValues.put(COLUMN_OFFICER_POSITION, position);
        contentValues.put(COLUMN_OFFICER_DOB, dob);
        contentValues.put(COLUMN_OFFICER_GENDER, gender);
        contentValues.put(COLUMN_OFFICER_ADDRESS, address);
        contentValues.put(COLUMN_OFFICER_SALARY, salary);
        contentValues.put(COLUMN_OFFICER_WORK_DURATION, workDuration);

        long result = db.insert(TABLE_OFFICER, null, contentValues);
        return result != -1;
    }
//Tampilkan (Show All) Data Pegawai
    public ArrayList<Pegawai> getAllPegawai() {
        ArrayList<Pegawai> pegawaiList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_OFFICER, null);
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(COLUMN_OFFICER_ID));
                @SuppressLint("Range") String nama = cursor.getString(cursor.getColumnIndex(COLUMN_OFFICER_NAME));
                @SuppressLint("Range") int umur = cursor.getInt(cursor.getColumnIndex(COLUMN_OFFICER_AGE));
                @SuppressLint("Range") String jabatan = cursor.getString(cursor.getColumnIndex(COLUMN_OFFICER_POSITION));
                @SuppressLint("Range") String dob = cursor.getString(cursor.getColumnIndex(COLUMN_OFFICER_DOB));
                @SuppressLint("Range") String gender = cursor.getString(cursor.getColumnIndex(COLUMN_OFFICER_GENDER));
                @SuppressLint("Range") String address = cursor.getString(cursor.getColumnIndex(COLUMN_OFFICER_ADDRESS));
                @SuppressLint("Range") double salary = cursor.getDouble(cursor.getColumnIndex(COLUMN_OFFICER_SALARY));
                @SuppressLint("Range") int workDuration = cursor.getInt(cursor.getColumnIndex(COLUMN_OFFICER_WORK_DURATION));

                Pegawai pegawai = new Pegawai(id, nama, jabatan, umur, dob, gender, address, salary, workDuration);
                pegawaiList.add(pegawai);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return pegawaiList;
    }

    //Delete data Pegawai
    public void deletePegawai(int officerId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_OFFICER, COLUMN_OFFICER_ID + "=" + officerId, null);
    }
    //Update Data Pegawai
    public boolean updatePegawai(int id, String nama, int umur, String jabatan, String dob, String gender, String address, double salary, int workDuration) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_OFFICER_NAME, nama);
        contentValues.put(COLUMN_OFFICER_AGE, umur);
        contentValues.put(COLUMN_OFFICER_POSITION, jabatan);
        contentValues.put(COLUMN_OFFICER_DOB, dob);
        contentValues.put(COLUMN_OFFICER_GENDER, gender);
        contentValues.put(COLUMN_OFFICER_ADDRESS, address);
        contentValues.put(COLUMN_OFFICER_SALARY, salary);
        contentValues.put(COLUMN_OFFICER_WORK_DURATION, workDuration);

        int affectedRows = db.update(TABLE_OFFICER, contentValues, COLUMN_OFFICER_ID + "=?", new String[]{String.valueOf(id)});
        return affectedRows > 0;
    }

}
