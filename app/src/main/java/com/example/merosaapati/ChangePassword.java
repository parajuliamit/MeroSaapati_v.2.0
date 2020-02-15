package com.example.merosaapati;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ChangePassword extends AppCompatActivity {
    SQLiteDatabase db,b;
    SQLiteOpenHelper openHelper;
    Cursor cursor;
    Button confirm;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.changepassword);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        ImageButton back;
        back = findViewById(R.id.backMore);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChangePassword.this, more.class);
                startActivity(intent);
                finish();
            }
        });

        confirm = findViewById(R.id.confirm);
        openHelper=new DatabaseHelper(this);
        db = openHelper.getReadableDatabase();
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText oldPassword = findViewById(R.id.oldPassword);
                EditText newPassword = findViewById(R.id.newPassword);
                EditText reNewPassword = findViewById(R.id.reNewPassword);
                String oldpass = oldPassword.getText().toString();
                String newpass = newPassword.getText().toString();
                String repass = reNewPassword.getText().toString();
                String olden = md5(oldpass);
                String newen = md5(newpass);
                Log.d("STATUS","Entered repass is:"+repass);
                if (newpass.equals(repass)) {
                    if(newpass.isEmpty()){
                        Toast.makeText(getApplicationContext(), "Password can't be empty", Toast.LENGTH_SHORT).show();
                    }else{
                    cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_NAME + " WHERE " + DatabaseHelper.COL_3 + "=?", new String[]{olden});
                    if (cursor != null) {
                        if (cursor.getCount() > 0) {
                            b = openHelper.getWritableDatabase();
                            ContentValues contentValues = new ContentValues();
                            contentValues.put(DatabaseHelper.COL_3, newen);
                            long id = db.update(DatabaseHelper.TABLE_NAME, contentValues,DatabaseHelper.COL_3+" = ?",
                                    new String[] { olden});
                            if(id>0) {
                                Toast.makeText(getApplicationContext(), "Password Changed", Toast.LENGTH_SHORT).show();
                            }
                            Intent intent = new Intent(ChangePassword.this, more.class);
                            startActivity(intent);
                            finish();
                            b.close();
                        } else {
                            Toast.makeText(getApplicationContext(), "Existing Password didn't match", Toast.LENGTH_SHORT).show();
                        }
                    }
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Re-enter same password", Toast.LENGTH_SHORT).show();
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
