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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class addContact extends AppCompatActivity {
    SQLiteOpenHelper openHelper;
    SQLiteDatabase db;
    Cursor cursor1;
    ImageButton done;
    EditText fname, address, phone, email;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addcontact);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        openHelper = new DatabaseHelper(this);

        ImageView backButton;
        backButton = findViewById(R.id.backContact);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(addContact.this,contacts.class);
                intent.putExtra("caller", "addContact");
                startActivity(intent);
            }
        });

        done = findViewById(R.id.imageButton4);
        fname = findViewById(R.id.fullContactName);
        address = findViewById(R.id.address);
        phone = findViewById(R.id.phone);
        email = findViewById(R.id.email);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db=openHelper.getWritableDatabase();
                String first = fname.getText().toString();
                String add = address.getText().toString();
                String ph = phone.getText().toString();
                String mail = email.getText().toString();

                if(first.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Name can't be empty.",Toast.LENGTH_SHORT).show();
                }else{
                    insertData(first,add,ph,mail);

                    Intent intent = new Intent(addContact.this,contacts.class);
                    intent.putExtra("caller", "addContact");
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
    public void insertData(String first, String add, String ph, String mail){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.COL_Name, first);
        contentValues.put(DatabaseHelper.COL_Address, add);
        contentValues.put(DatabaseHelper.COL_Phone, ph);
        contentValues.put(DatabaseHelper.COL_Email, mail);
//        db.execSQL("INSERT INTO "+ DatabaseHelper.CONTACTS_TABLE+"(`First Name`, `Last Name`, Address, Phone, Email) VALUES ("+first+", "+last+","+ add+","+ph+","+mail+")");
        long id = db.insert(DatabaseHelper.CONTACTS_TABLE, null, contentValues);
        Toast.makeText(getApplicationContext(), "Contact Added",Toast.LENGTH_LONG).show();
        db.close();
        db = openHelper.getReadableDatabase();
        cursor1 = db.rawQuery("SELECT * FROM " + DatabaseHelper.CONTACTS_TABLE, null);
        Log.d("STATUS", "Total contacts " + cursor1.getCount());
        db.close();
    }
}
