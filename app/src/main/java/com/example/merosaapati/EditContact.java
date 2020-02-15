package com.example.merosaapati;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class EditContact extends AppCompatActivity {
    SQLiteDatabase db;
    SQLiteOpenHelper openHelper;
    TextView setName, setAddress, setPhone, setEmail;
    ImageButton done, backButton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editcontact);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        setName = (TextView)findViewById(R.id.editContactName);
        setAddress = (TextView)findViewById((R.id.addressEdit));
        setEmail = (TextView)findViewById(R.id.emailEdit);
        setPhone = (TextView)findViewById(R.id.phoneEdit);
        done = (ImageButton)findViewById(R.id.editDoneContact);
        backButton = (ImageButton)findViewById(R.id.backEditContact);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(EditContact.this,contacts.class);
//                intent.putExtra("caller","EditContact");
//                startActivity(intent);
                finish();
            }
        });

        setName.setText(getIntent().getStringExtra("name"));
        setAddress.setText(getIntent().getStringExtra("address"));
        setPhone.setText(getIntent().getStringExtra("phone"));
        setEmail.setText(getIntent().getStringExtra("email"));

        final String id = getIntent().getStringExtra("id");
        openHelper = new DatabaseHelper(this);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db=openHelper.getWritableDatabase();
                String first = setName.getText().toString();
                String add = setAddress.getText().toString();
                String ph = setPhone.getText().toString();
                String mail = setEmail.getText().toString();

                if(first.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Name can't be empty.",Toast.LENGTH_SHORT).show();
                }else{

                    ContentValues contentValues = new ContentValues();
                    contentValues.put(DatabaseHelper.COL_Name, first);
                    contentValues.put(DatabaseHelper.COL_Address, add);
                    contentValues.put(DatabaseHelper.COL_Phone, ph);
                    contentValues.put(DatabaseHelper.COL_Email, mail);
                    long i = db.update(DatabaseHelper.CONTACTS_TABLE, contentValues,DatabaseHelper.COL_ID+" = ?",
                            new String[] { id});
                    db.close();

                    Intent intent = new Intent(EditContact.this,contacts.class);
                    intent.putExtra("caller", "EditContact");
                    startActivity(intent);
                    finish();
                    Toast.makeText(EditContact.this, "Contact Edited", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        Intent intent = new Intent(EditContact.this,contacts.class);
//        intent.putExtra("caller","EditContact");
//        startActivity(intent);
        finish();
    }
 }
