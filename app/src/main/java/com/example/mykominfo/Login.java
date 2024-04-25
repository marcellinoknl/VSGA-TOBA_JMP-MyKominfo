package com.example.mykominfo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mykominfo.helper.DatabaseHelper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Login extends AppCompatActivity {

    private EditText editTextEmail;
    private EditText editTextPassword;
    private CheckBox rememberCheckbox;
    private DatabaseHelper dbHelper;
    private boolean isPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize dbHelper
        dbHelper = new DatabaseHelper(this);

        editTextEmail = findViewById(R.id.editText1);
        editTextPassword = findViewById(R.id.editText2);
        rememberCheckbox = findViewById(R.id.rememberCheckbox);
        TextView passwordVisibilityToggle = findViewById(R.id.password_visibility_toggle);
        Button loginButton = findViewById(R.id.loginButton);
        TextView textViewRegister = findViewById(R.id.textView4);

        passwordVisibilityToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                togglePasswordVisibility();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString().trim(); // Trim email
                String password = editTextPassword.getText().toString();
                boolean rememberMeChecked = rememberCheckbox.isChecked();

                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    Toast.makeText(Login.this, "Kolom Email dan Password Harus di Isi", Toast.LENGTH_SHORT).show();
                    return; // Stop further execution if input fields are empty
                }

                // Proceed with authentication
                if (isCredentialsValid(email, password)) {
                    // Save rememberMeChecked status using SharedPreferences
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Login.this);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("rememberMe", rememberMeChecked);
                    editor.apply();

                    Intent intent = new Intent(Login.this, SplashActivity.class);
                    startActivity(intent);
                    finish(); // Close LoginActivity
                } else {
                    Toast.makeText(Login.this, "Email atau password salah", Toast.LENGTH_SHORT).show();
                }
            }
        });

        textViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to RegistrationActivity when "Daftar Disini" is clicked
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void togglePasswordVisibility() {
        if (!isPasswordVisible) {
            editTextPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            isPasswordVisible = true;
            // Change text to "Sembunyikan Password" when password is visible
            ((TextView) findViewById(R.id.password_visibility_toggle)).setText("Sembunyikan Password");
        } else {
            editTextPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            isPasswordVisible = false;
            // Change text to "Tampilkan Password" when password is hidden
            ((TextView) findViewById(R.id.password_visibility_toggle)).setText("Tampilkan Password");
        }
        // Move cursor to the end of the text
        editTextPassword.setSelection(editTextPassword.getText().length());
    }

    private boolean isCredentialsValid(String email, String password) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {DatabaseHelper.COLUMN_ID, DatabaseHelper.COLUMN_PASSWORD};
        String selection = DatabaseHelper.COLUMN_EMAIL + " = ?";
        String[] selectionArgs = {email};
        Cursor cursor = db.query(DatabaseHelper.TABLE_USERS,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null);
        boolean isValid = false;
        if (cursor != null && cursor.moveToFirst()) {
            int passwordIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_PASSWORD);
            if (passwordIndex != -1) {
                String hashedPasswordFromDB = cursor.getString(passwordIndex);
                // Hash the entered password
                String hashedPasswordEntered = hashPassword(password);
                if (hashedPasswordEntered != null && hashedPasswordEntered.equals(hashedPasswordFromDB)) {
                    isValid = true;
                }
            } else {
                // Password column not found
                Log.e("LoginActivity", "Password column not found in cursor.");
            }
            cursor.close(); // Tutup cursor
        }
        db.close(); // Close Koneksi ke DB
        return isValid;
    }

    private String hashPassword(String password) {
        try {
            // Membuat MessageDigest dengan SHA-256 algorithm
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            // Menambahkan password (bytes) ke digest
            md.update(password.getBytes());
            // Get: hash dalam byte
            byte[] hashedBytes = md.digest();
            // Konversi hashed bytes to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}

