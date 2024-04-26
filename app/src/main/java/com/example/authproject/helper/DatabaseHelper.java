package com.example.authproject.helper;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.authproject.model.Student;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String databaseName = "SignLog.db";
    private static final String DATABASE_NAME = "dbsiakad";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_STD = "students";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_ALAMAT = "alamat";
    private static final String KEY_JENISKELAMIN = "jk";
    private static final String KEY_TANGGALLAHIR = "tanggallahir";

    public DatabaseHelper(Context context) {
        super(context, databaseName, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDatabase) {
        // Query untuk membuat tabel users
        MyDatabase.execSQL("CREATE TABLE users(email TEXT PRIMARY KEY, password TEXT)");
        MyDatabase.execSQL("CREATE TABLE " + TABLE_STD + "(" +
                KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                KEY_NAME + " TEXT," +
                KEY_ALAMAT + " TEXT," +
                KEY_TANGGALLAHIR + " TEXT," +
                KEY_JENISKELAMIN + " TEXT)");

        // Tambahkan log untuk menandai bahwa pembuatan tabel telah berhasil
        Log.d("DatabaseHelper", "Tabel students berhasil dibuat");

    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int oldVersion, int newVersion) {
        MyDB.execSQL("DROP TABLE IF EXISTS users");
        MyDB.execSQL("DROP TABLE IF EXISTS " + TABLE_STD);
        onCreate(MyDB);
    }

    public Boolean insertData(String email, String password) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("password", password);
        long result = MyDatabase.insert("users", null, contentValues);

        return result != -1;
    }

    public Boolean checkEmail(String email) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("SELECT * FROM users WHERE email = ?", new String[]{email});
        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }

    public Boolean checkEmailPassword(String email, String password) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("SELECT * FROM users WHERE email = ? AND password = ?", new String[]{email, password});
        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }

    public long addUserDetail(String name, String tanggallahir, String alamat, String jeniskelamin) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name);
        values.put(KEY_ALAMAT, alamat);
        values.put(KEY_JENISKELAMIN, jeniskelamin);
        values.put(KEY_TANGGALLAHIR, tanggallahir);

        return db.insert(TABLE_STD, null, values);
    }

    @SuppressLint("Range")
    public ArrayList<Student> getAllUsers() {
        ArrayList<Student> userModelArrayList = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_STD;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                Student std = new Student();
                std.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                std.setName(c.getString(c.getColumnIndex(KEY_NAME)));
                std.setAlamat(c.getString(c.getColumnIndex(KEY_ALAMAT)));
                std.setJeniskelamin(c.getString(c.getColumnIndex(KEY_JENISKELAMIN)));
                std.setTanggallahir(c.getString(c.getColumnIndex(KEY_TANGGALLAHIR)));
                // adding to Students list
                userModelArrayList.add(std);
            } while (c.moveToNext());
        }
        c.close();
        return userModelArrayList;
    }

    public void deleteUser(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_STD, KEY_ID + " = ?", new String[]{String.valueOf(id)});
    }

    public int updateUser(int id, String name, String alamat, String tanggallahir, String jeniskelamin) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name);
        values.put(KEY_ALAMAT, alamat);
        values.put(KEY_JENISKELAMIN, jeniskelamin);
        values.put(KEY_TANGGALLAHIR, tanggallahir);

        return db.update(TABLE_STD, values, KEY_ID + " = ?", new String[]{String.valueOf(id)});
    }
}
