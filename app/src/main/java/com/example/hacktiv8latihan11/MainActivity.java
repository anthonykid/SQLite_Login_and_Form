package com.example.hacktiv8latihan11;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQuery;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    EditText edUser, edPass;
    Button btnLogin;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Memberikan value
        edUser = (EditText) findViewById(R.id.edtUsername);
        edPass = (EditText) findViewById(R.id.edtPassword);
        btnLogin = (Button) findViewById(R.id.buttonLogin);

        btnLogin.setOnClickListener(this::onClick);

        //Membuat Database
        db = openOrCreateDatabase("LoginDB", Context.MODE_PRIVATE, null);
        db.execSQL("DROP TABLE IF EXISTS login;");
        db.execSQL("CREATE TABLE IF NOT EXISTS login(user VARCHAR, pass VARCHAR);");
    }
    public void onClick(View view){
        if (view == btnLogin){
            Cursor c =db.rawQuery(
                    "SELECT * FROM login WHERE user='" + edUser.getText()
                            + "' AND '" + edPass.getText() + "';", null);
            //SQLiteQuery = "INSERT INTO LoginDB (user,pass) VALUES ('" + edUser.getText() + "','" + edPass.getText() + "');";
            //db.execSQL(String.valueOf(SQLiteQuery));

            String defusername = "anthony";
            String defpassword = "123";
            String QueryHolder = "INSERT INTO login (user,pass) VALUES('"+defusername+"','"+defpassword+"');";
            db.execSQL(QueryHolder);

            if (c.getCount() == 0) {
                showMessage("Error", "No records found");
                return;
            }
            else gotoform();
        }
    }
    public void showMessage(String title, String message){
        Builder builder = new Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
    public void gotoform(){
        Intent explicit1 = new Intent(getApplicationContext(), MainMenu.class);
        startActivity(explicit1);
    }
}