package com.example.merosaapati;

import android.content.ContentValues;
import android.content.Intent;
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

public class register extends AppCompatActivity {
    SQLiteOpenHelper openHelper;
    SQLiteDatabase db;
    Button register;
    EditText username, password, repassword;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        openHelper = new DatabaseHelper(this);
        register = findViewById(R.id.register);
        username = findViewById(R.id.user);
        password = findViewById(R.id.password);
        repassword = findViewById(R.id.repassword);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db=openHelper.getWritableDatabase();
                String user = username.getText().toString();
                String pass = password.getText().toString();
                String repass = repassword.getText().toString();
                if(pass.isEmpty()||user.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Fill all details",Toast.LENGTH_SHORT).show();
                }else if(pass.equals(repass)){
                    insertdata(user,md5(pass));
                    Toast.makeText(getApplicationContext(), "Registered Successfully",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(register.this,home.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(), "Passwords didn't match",Toast.LENGTH_SHORT).show();
                }

            }
        });

//        TextView login;
//        login = findViewById(R.id.loginHere);
//        login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(register.this,login.class);
//                startActivity(intent);
//            }
//        });


    }
    public void insertdata(String user, String pass){

        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.COL_2, user);
        contentValues.put(DatabaseHelper.COL_3, pass);
        long id = db.insert(DatabaseHelper.TABLE_NAME, null, contentValues);
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
