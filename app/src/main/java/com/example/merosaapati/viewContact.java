package com.example.merosaapati;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class viewContact extends AppCompatActivity {
    SQLiteDatabase db;
    SQLiteOpenHelper openHelper;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewcontact);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        ImageView backButton;
        backButton = findViewById(R.id.imageButton3);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(viewContact.this,contacts.class);
                intent.putExtra("caller",getIntent().getStringExtra("viewContact"));
                finish();
            }
        });





        ImageView deleteButton;
        deleteButton = findViewById(R.id.deleteContactButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(viewContact.this, R.style.AlertDialogCustom );
                builder.setTitle("DELETE CONTACT")
                        .setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String uid = getIntent().getStringExtra("id");
                                openHelper=new DatabaseHelper(getBaseContext());
                                db = openHelper.getWritableDatabase();
                                db.execSQL("DELETE FROM "+DatabaseHelper.CONTACTS_TABLE+" WHERE "+DatabaseHelper.COL_ID+" ="+uid);
                                Toast.makeText(getBaseContext(), "Contact Deleted", Toast.LENGTH_SHORT).show();
                                finish();

                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                builder.show();
            }
        });

        ImageView editContact;
        editContact = findViewById(R.id.editContactButton);
        editContact.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(viewContact.this,EditContact.class);
                intent.putExtra("id",getIntent().getStringExtra("id"));
                intent.putExtra("name",getIntent().getStringExtra("name"));
                intent.putExtra("status",getIntent().getStringExtra("status"));
                intent.putExtra("address",getIntent().getStringExtra("address"));
                intent.putExtra("phone",getIntent().getStringExtra("phone"));
                intent.putExtra("email",getIntent().getStringExtra("email"));
//                intent.putExtra("debt",getIntent().getStringExtra("debt"));
//                intent.putExtra("transaction",getIntent().getStringExtra("transaction"));
//                intent.putExtra("due",getIntent().getStringExtra("due"));
//                intent.putExtra("comment",getIntent().getStringExtra("comment"));
//                intent.putExtra("rate",getIntent().getStringExtra("rate"));
                startActivity(intent);
            }
        });


        String name = getIntent().getStringExtra("name");
        TextView setName = (TextView) findViewById(R.id.nameContact);
        setName.setText(name);

        String status = getIntent().getStringExtra("status");
        Button setStatus = findViewById(R.id.debtStatusButton);
        if (status == null) {
            setStatus.setText("ADD  DEBT");
        } else{
            setStatus.setText("VIEW  DEBT");
        }

        String address = getIntent().getStringExtra("address");
        TextView setAddress = (TextView) findViewById(R.id.address2);
        setAddress.setText(address);

//        final String id = getIntent().getStringExtra("id");

        String phone = getIntent().getStringExtra("phone");
        TextView setPhone = (TextView) findViewById(R.id.phoneViewContact);
        setPhone.setText(phone);

        String email = getIntent().getStringExtra("email");
        TextView setEmail = (TextView) findViewById(R.id.emailViewContact);
        setEmail.setText(email);

        setStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getIntent().getStringExtra("status")==null) {
                    Intent intent = new Intent(viewContact.this, AddDebt.class);
                    intent.putExtra("id",getIntent().getStringExtra("id"));
                    intent.putExtra("name",getIntent().getStringExtra("name"));
                    intent.putExtra("address",getIntent().getStringExtra("address"));
                    intent.putExtra("status",getIntent().getStringExtra("status"));
                    intent.putExtra("phone",getIntent().getStringExtra("phone"));
                    intent.putExtra("email",getIntent().getStringExtra("email"));
                    startActivity(intent);
                    finish();
                }else{
                    Intent intent = new Intent(viewContact.this,viewDebt.class);
                    intent.putExtra("id",getIntent().getStringExtra("id"));
                    intent.putExtra("name",getIntent().getStringExtra("name"));
                    intent.putExtra("status",getIntent().getStringExtra("status"));
                    intent.putExtra("address",getIntent().getStringExtra("address"));
                    intent.putExtra("phone",getIntent().getStringExtra("phone"));
                    intent.putExtra("email",getIntent().getStringExtra("email"));
                    intent.putExtra("debt",getIntent().getStringExtra("debt"));
                    intent.putExtra("transaction",getIntent().getStringExtra("transaction"));
                    intent.putExtra("due",getIntent().getStringExtra("due"));
                    intent.putExtra("comment",getIntent().getStringExtra("comment"));
                    intent.putExtra("rate",getIntent().getStringExtra("rate"));
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(viewContact.this,contacts.class);
        intent.putExtra("caller",getIntent().getStringExtra("viewContact"));
        finish();
    }
}
