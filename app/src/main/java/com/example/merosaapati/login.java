package com.example.merosaapati;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class login extends AppCompatActivity {

    SQLiteDatabase db;
    SQLiteOpenHelper openHelper;
    Cursor cursor;
    Button login;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


        login = findViewById(R.id.login);
        openHelper=new DatabaseHelper(this);
        db = openHelper.getReadableDatabase();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText username = (EditText)findViewById(R.id.userid);
                EditText password = (EditText)findViewById(R.id.loginPassword);
                String userName = username.getText().toString();
                String pass1 = password.getText().toString();
                String pass = md5(pass1);
                cursor = db.rawQuery("SELECT *FROM " + DatabaseHelper.TABLE_NAME + " WHERE " + DatabaseHelper.COL_2 + "=? AND " + DatabaseHelper.COL_3 + "=?", new String[]{userName, pass});
                if (cursor != null) {
                    if (cursor.getCount() > 0) {
                        Toast.makeText(getApplicationContext(),"Login Successful",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(login.this,home.class);
                        startActivity(intent);
                        finish();
                        db.close();
                    }else{
                        Toast.makeText(getApplicationContext(), "Wrong Credentials",Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });

    }
    private static char[] hextable = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

    private static String md5(String s)
    {
        MessageDigest digest;
        try
        {
            digest = MessageDigest.getInstance("MD5");
            digest.update(s.getBytes(), 0, s.length());
            byte[] bytes = digest.digest();

            String hash = "";
            for (int i = 0; i < bytes.length; ++i)
            {
                int di = (bytes[i] + 256) & 0xFF;
                hash = hash + hextable[(di >> 4) & 0xF] + hextable[di & 0xF];
            }

            return hash;
        }
        catch (NoSuchAlgorithmException e)
        {
        }

        return "";
    }
}
