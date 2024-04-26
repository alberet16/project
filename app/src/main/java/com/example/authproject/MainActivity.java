package com.example.authproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.authproject.helper.DatabaseHelper;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper dbHelper;
    private EditText etName, etAlamat, etTanggalLahir, etJenisKelamin;
    private Button btnSave, btnList, btnInformasi, btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);

        etName = findViewById(R.id.edt_name);
        etAlamat = findViewById(R.id.edt_alamat);
        etTanggalLahir = findViewById(R.id.edt_tgllahir);
        etJenisKelamin = findViewById(R.id.edt_jeniskelamin);
        btnSave = findViewById(R.id.btn_submit);
        btnList = findViewById(R.id.btn_list);
        btnInformasi = findViewById(R.id.btn_informasi);
        btnLogout = findViewById(R.id.btn_logout);

        btnSave.setOnClickListener(v -> {
            if (etName.getText().toString().isEmpty()  ) {
                Toast.makeText(MainActivity.this, "Error: Nim harus diisi!", Toast.LENGTH_SHORT).show();
            } else if (etName.getText().toString().isEmpty()) {
                Toast.makeText(MainActivity.this, "Error: Nama harus diisi!", Toast.LENGTH_SHORT).show();
            } else {
                dbHelper.addUserDetail(etName.getText().toString(),etTanggalLahir.getText().toString(),etAlamat.getText().toString(),etJenisKelamin.getText().toString());
                etName.setText("");
                etAlamat.setText("");
                etTanggalLahir.setText("");
                etJenisKelamin.setText("");
                Toast.makeText(MainActivity.this, "Simpan berhasil!", Toast.LENGTH_SHORT).show();
            }
        });

        btnList.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ListStudentsActivity.class);
            startActivity(intent);
        });

        btnInformasi.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Informasi.class);
            startActivity(intent);
        });

        btnLogout.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            Toast.makeText(MainActivity.this, "Anda Berhasil Logout", Toast.LENGTH_SHORT).show();
            startActivity(intent);
        });

    }
}