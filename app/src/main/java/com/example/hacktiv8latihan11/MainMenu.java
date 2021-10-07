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
import android.widget.TextView;

public class MainMenu extends AppCompatActivity {
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

        //Membuat Database
        db = openOrCreateDatabase("Form", Context.MODE_PRIVATE, null);
        db.execSQL("DROP TABLE IF EXISTS datapegawai");
        db.execSQL("CREATE TABLE IF NOT EXISTS datapegawai(nama VARCHAR, perusahaan VARCHAR, alamat VARCHAR, jenis VARCHAR)");

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                insertData(Nama, NamaPerusahaan, Alamat, checkJenis());
            }
        });

        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readData();
            }
        });
    }

    private void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    private void insertData(TextView namaInsert, TextView perusahaanInset, TextView alamatInsert, String jenis) {
        String QueryHolder = "INSERT INTO datapegawai (nama,perusahaan,alamat,jenis) " +
                "VALUES('" + namaInsert.getText().toString() + "'," +
                "'" + perusahaanInset.getText().toString() + "'," +
                "'" + alamatInsert.getText().toString() + "'," +
                "'" + jenis + "');";
        db.execSQL(QueryHolder);
    }

    private String checkJenis() {
        int selectedJenis = 0;

        if (Tetap.isChecked()) {
            return Tetap.getText().toString();
        } else if (Kontrak.isChecked()) {
            return Kontrak.getText().toString();
        } else {
            return null;
        }
    }

    private void readData() {
        db = getApplicationContext().openOrCreateDatabase("Form", Context.MODE_PRIVATE, null);
        StringBuffer sb = new StringBuffer();

        Cursor c = db.rawQuery(
                "SELECT * FROM datapegawai", null
        );

        if (c.getCount() == 0) {
            showMessage("Error", "Data kosong!");
        } else {
            while (c.moveToNext()) {
                sb.append("Nama : " + c.getString(0) + "\n");
                sb.append("Perusahaan : " + c.getString(1) + "\n");
                sb.append("Alamat : " + c.getString(2) + "\n");
                sb.append("Jenis Kontrak : " + c.getString(3) + "\n\n");
            }
            showMessage("Data Pegawai", sb.toString());
        }
    }
}
