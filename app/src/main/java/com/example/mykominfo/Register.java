package com.example.mykominfo;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.text.InputType;

import com.example.mykominfo.helper.DatabaseHelper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Register extends AppCompatActivity {

    private EditText editTextUsername;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextConfirmPassword;
    private DatabaseHelper dbHelper;
    private boolean isPasswordVisible = false;
    private boolean isConfirmPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        dbHelper = new DatabaseHelper(this);

        editTextUsername = findViewById(R.id.editText1);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editText2);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        TextView passwordVisibilityToggle = findViewById(R.id.passwordTampilRegister);
        TextView confirmPasswordVisibilityToggle = findViewById(R.id.confirmPasswordTampilRegister);
        Button registerButton = findViewById(R.id.registerButton);
        TextView textViewLogin = findViewById(R.id.textView4);

        passwordVisibilityToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                togglePasswordVisibility();
            }
        });

        confirmPasswordVisibilityToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleConfirmPasswordVisibility();
            }
        });

        registerButton.setOnClickListener(v -> {
            String username = editTextUsername.getText().toString().trim();
            String email = editTextEmail.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();
            String confirmPassword = editTextConfirmPassword.getText().toString().trim();

            if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(Register.this, "Harap lengkapi semua kolom", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!password.equals(confirmPassword)) {
                Toast.makeText(Register.this, "Password tidak sesuai", Toast.LENGTH_SHORT).show();
                return;
            }

            // Check if the email is already registered
            if (isEmailRegistered(email)) {
                Toast.makeText(Register.this, "Email sudah terdaftar, gunakan email lain", Toast.LENGTH_SHORT).show();
                return;
            }

            // Hash the password
            String hashedPassword = hashPassword(password);

            // Save user data into the database
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.COLUMN_USERNAME, username);
            values.put(DatabaseHelper.COLUMN_EMAIL, email);
            values.put(DatabaseHelper.COLUMN_PASSWORD, hashedPassword);
            db.insert(DatabaseHelper.TABLE_USERS, null, values);
            db.close();

            Toast.makeText(Register.this, "Registrasi Akun Berhasil", Toast.LENGTH_SHORT).show();
            Intent intent2 = new Intent(Register.this, SplashActivity.class);
            startActivity(intent2);
            finish(); // Close Register activity
        });


        textViewLogin.setOnClickListener(v -> {
            // Navigate to LoginActivity when "Masuk" is clicked
            finish(); // Close Register activity
        });
    }

    @SuppressLint("SetTextI18n")
    private void togglePasswordVisibility() {
        if (!isPasswordVisible) {
            editTextPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            isPasswordVisible = true;
            // Change text to "Sembunyikan Password" when password is visible
            ((TextView) findViewById(R.id.passwordTampilRegister)).setText("Sembunyikan Password");
        } else {
            editTextPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            isPasswordVisible = false;
            // Change text to "Tampilkan Password" when password is hidden
            ((TextView) findViewById(R.id.passwordTampilRegister)).setText("Tampilkan Password");
        }
        // Move cursor to the end of the text
        editTextPassword.setSelection(editTextPassword.getText().length());
    }

    @SuppressLint("SetTextI18n")
    private void toggleConfirmPasswordVisibility() {
        if (!isConfirmPasswordVisible) {
            editTextConfirmPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            isConfirmPasswordVisible = true;
            // Ganti Text/Pesan ke "Sembunyikan Password" when confirm password is visible
            ((TextView) findViewById(R.id.confirmPasswordTampilRegister)).setText("Sembunyikan Password");
        } else {
            editTextConfirmPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            isConfirmPasswordVisible = false;
            // Ganti Text/Pesan ke "Tampilkan Password" when confirm password is hidden
            ((TextView) findViewById(R.id.confirmPasswordTampilRegister)).setText("Tampilkan Password");
        }
        // Cursor ke ujung text
        editTextConfirmPassword.setSelection(editTextConfirmPassword.getText().length());
    }

    private String hashPassword(String password) {
        try {
            // Create MessageDigest instance for SHA-256
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            // Add password bytes to digest
            md.update(password.getBytes());
            // Get the hashed bytes
            byte[] hashedBytes = md.digest();
            // Convert hashed bytes to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            Log.e("Register", "Algoritma Hash Tidak Ditemukan.", e);
            return null;
        }
    }

    private boolean isEmailRegistered(String email) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {DatabaseHelper.COLUMN_ID};
        String selection = DatabaseHelper.COLUMN_EMAIL + " = ?";
        String[] selectionArgs = {email};
        Cursor cursor = db.query(DatabaseHelper.TABLE_USERS,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null);
        boolean isRegistered = cursor != null && cursor.getCount() > 0;
        if (cursor != null) {
            cursor.close();
        }
        db.close();
        return isRegistered;
    }
}
