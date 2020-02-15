package com.example.merosaapati;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class contacts extends AppCompatActivity {

    DatabaseHelper db;
    SQLiteDatabase SQLITEDATABASE;
    Cursor cursor;
    SQLiteListAdapter ListAdapter ;

    ArrayList<String> ID_ArrayList = new ArrayList<String>();
    ArrayList<String> NAME_ArrayList = new ArrayList<String>();
    ArrayList<String> STATUS_ArrayList = new ArrayList<String>();
    ArrayList<String> ADDRESS_ArrayList = new ArrayList<String>();
    ArrayList<String> PHONE_ArrayList = new ArrayList<String>();
    ArrayList<String> EMAIL_ArrayList = new ArrayList<String>();
    ArrayList<String> TRANSACTION_ArrayList = new ArrayList<String>();
    ArrayList<String> DUE_ArrayList = new ArrayList<String>();
    ArrayList<String> COMMENT_ArrayList = new ArrayList<String>();
    ArrayList<String> DEBT_ArrayList = new ArrayList<String>();
    ArrayList<String> INTEREST_ArrayList = new ArrayList<String>();
    ListView LISTVIEW;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts);

        LISTVIEW = (ListView) findViewById(R.id.contactList);
        db = new DatabaseHelper(this);



        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        ImageView addcontact;
        addcontact = findViewById(R.id.imageView5);
        addcontact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(contacts.this,addContact.class);
                startActivity(intent);
            }
        });

        ImageView homeTab;
        homeTab = findViewById(R.id.switchHome);
        homeTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(contacts.this,home.class);
                startActivity(intent);
            }
        });

        TextView homePageSwitch;
        homePageSwitch = findViewById(R.id.textView12);
        homePageSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(contacts.this,home.class);
                startActivity(intent);
            }
        });

        ImageView moreTab;
        moreTab = findViewById(R.id.switchMore);
        moreTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(contacts.this,more.class);
                startActivity(intent);
            }
        });

        TextView morePageSwitch;
        morePageSwitch = findViewById(R.id.textView14);
        morePageSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(contacts.this,more.class);
                startActivity(intent);
            }
        });

        LISTVIEW.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int index, long viewId) {
                String swi = getIntent().getStringExtra("caller");
                if (swi.equals("home")) {
                    Intent intent = new Intent(contacts.this, AddDebt.class);
                    intent.putExtra("id", ID_ArrayList.get(index));
                    intent.putExtra("name", NAME_ArrayList.get(index));
                    intent.putExtra("status", STATUS_ArrayList.get(index));
                    intent.putExtra("address", ADDRESS_ArrayList.get(index));
                    intent.putExtra("phone", PHONE_ArrayList.get(index));
                    intent.putExtra("email", EMAIL_ArrayList.get(index));
                    intent.putExtra("debt", DEBT_ArrayList.get(index));
                    intent.putExtra("transaction", TRANSACTION_ArrayList.get(index));
                    intent.putExtra("due", DUE_ArrayList.get(index));
                    intent.putExtra("comment", COMMENT_ArrayList.get(index));
                    intent.putExtra("rate", INTEREST_ArrayList.get(index));
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(contacts.this, viewContact.class);
                    intent.putExtra("id", ID_ArrayList.get(index));
                    intent.putExtra("name", NAME_ArrayList.get(index));
                    intent.putExtra("status", STATUS_ArrayList.get(index));
                    intent.putExtra("address", ADDRESS_ArrayList.get(index));
                    intent.putExtra("phone", PHONE_ArrayList.get(index));
                    intent.putExtra("email", EMAIL_ArrayList.get(index));
                    intent.putExtra("debt", DEBT_ArrayList.get(index));
                    intent.putExtra("transaction", TRANSACTION_ArrayList.get(index));
                    intent.putExtra("due", DUE_ArrayList.get(index));
                    intent.putExtra("comment", COMMENT_ArrayList.get(index));
                    intent.putExtra("rate", INTEREST_ArrayList.get(index));
                    startActivity(intent);
//                Toast.makeText(getBaseContext(),"List Item Clicked", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    protected void onResume() {

        ShowSQLiteDBdata() ;

        super.onResume();

    }

    private void ShowSQLiteDBdata() {

        SQLITEDATABASE = db.getReadableDatabase();
        cursor = SQLITEDATABASE.rawQuery("SELECT * FROM "+DatabaseHelper.CONTACTS_TABLE, null);
//        cursor = SQLITEDATABASE.rawQuery("SELECT "+DatabaseHelper.COL_ID+", "+DatabaseHelper.COL_Name+", "+DatabaseHelper.COL_DebtStatus+" FROM "+DatabaseHelper.CONTACTS_TABLE, null);
        ID_ArrayList.clear();
        NAME_ArrayList.clear();
        STATUS_ArrayList.clear();
        ADDRESS_ArrayList.clear();
        EMAIL_ArrayList.clear();
        PHONE_ArrayList.clear();
        DEBT_ArrayList.clear();
        TRANSACTION_ArrayList.clear();
        DUE_ArrayList.clear();
        INTEREST_ArrayList.clear();
        COMMENT_ArrayList.clear();


        Log.d("STATUS","Column index for total "+cursor.getColumnIndex("id")+" "+DatabaseHelper.COL_DebtStatus);
        if (cursor.moveToFirst()) {
            do {
                ID_ArrayList.add(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_ID)));
                NAME_ArrayList.add(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_Name)));
                ADDRESS_ArrayList.add(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_Address)));
                STATUS_ArrayList.add(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_DebtStatus)));
                PHONE_ArrayList.add(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_Phone)));
                EMAIL_ArrayList.add(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_Email)));
                DEBT_ArrayList.add(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_DebtAmount)));
                TRANSACTION_ArrayList.add(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_TransactionDate)));
                DUE_ArrayList.add(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_DueDate)));
                INTEREST_ArrayList.add(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_Rate)));
                COMMENT_ArrayList.add(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_COMMENT)));

            } while (cursor.moveToNext());
        }

        ListAdapter = new SQLiteListAdapter(contacts.this,
                ID_ArrayList,
                NAME_ArrayList,
                STATUS_ArrayList

        );
        LISTVIEW.setAdapter(ListAdapter);

        cursor.close();
        db.close();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}

