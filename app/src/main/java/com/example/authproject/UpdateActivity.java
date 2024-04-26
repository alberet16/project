package com.example.authproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.authproject.helper.DatabaseHelper;
import com.example.authproject.model.Student;

public class UpdateActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private EditText etName, etAlamat, etTanggalLahir, etJenisKelamin;
    private Button btnSave;
    private Student student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        dbHelper = new DatabaseHelper(this);

        etName = findViewById(R.id.upd_name);
        etAlamat = findViewById(R.id.upd_alamat);
        etTanggalLahir = findViewById(R.id.upd_tgllahir);
        etJenisKelamin = findViewById(R.id.upd_jeniskelamin);
        btnSave = findViewById(R.id.btn_submit);

        Intent intent = getIntent();
        student = (Student) intent.getSerializableExtra("user");

        etName.setText(student.getName());
        etAlamat.setText(student.getAlamat());
        etTanggalLahir.setText(student.getTanggallahir());
        etJenisKelamin.setText(student.getJeniskelamin());

        btnSave.setOnClickListener((View v) -> {
            dbHelper.updateUser(student.getId(), etName.getText().toString(), etAlamat.getText().toString(),etTanggalLahir.getText().toString(),etJenisKelamin.getText().toString());
            Toast.makeText(UpdateActivity.this, "Updated berhasil!", Toast.LENGTH_SHORT).show();
            finish();
        });

    }
}