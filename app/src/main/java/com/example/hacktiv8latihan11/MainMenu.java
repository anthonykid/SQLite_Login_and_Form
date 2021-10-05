package com.example.hacktiv8latihan11;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

public class MainMenu extends AppCompatActivity implements View.OnClickListener{
    SQLiteDatabase db;
    EditText Nama, NamaPerusahaan, Alamat;
    RadioButton Tetap, Kontrak;
    Button btnSubmit, btnShow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        //Memberikan value
        Nama = (EditText) findViewById(R.id.edtNama);
        NamaPerusahaan = (EditText) findViewById(R.id.edtPerusahaan);
        Alamat = (EditText) findViewById(R.id.edtAlamat);

        Tetap = (RadioButton) findViewById(R.id.radioButtonTetap);
        Kontrak = (RadioButton) findViewById(R.id.radioButtonKontrak);

        btnSubmit = (Button) findViewById(R.id.buttonSubmit);
        btnShow = (Button) findViewById(R.id.buttonShow);

        btnSubmit.setOnClickListener(this);
        btnShow.setOnClickListener(this);

        //Membuat Database
        db = openOrCreateDatabase("DataDB", Context.MODE_PRIVATE, null);
        db.execSQL("DROP TABLE IF EXISTS data;");
        db.execSQL("CREATE TABLE IF NOT EXISTS login(nama VARCHAR, perusahaan VARCHAR, alamat VARCHAR);");


    }
    public void onClick(View view){
        if (view == btnSubmit){
            String nama = Nama.getText().toString();
            String perusahaan = NamaPerusahaan.getText().toString();
            String alamat = Alamat.getText().toString();
            String status = "";

            if(Tetap.isChecked()){
                status = "Tetap";
            }
            if(Kontrak.isChecked()){
                status = "Kontrak";
            }
            if(Tetap.isChecked() &&Kontrak.isChecked()){
                status = "Tetap Kontrak";
            }

            db.execSQL("INSERT INTO data (nama,perusahaan, alamat, status) VALUES('"+nama+"','"+perusahaan+"','"+alamat+"','"+status+"');");
            showMessage("Success", "Record Telah Ditambahkan");
        }
    
    }
    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

}