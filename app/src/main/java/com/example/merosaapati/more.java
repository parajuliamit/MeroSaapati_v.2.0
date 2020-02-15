package com.example.merosaapati;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class more extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.more);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        ImageView contact;
        contact = findViewById(R.id.switchContact);
        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(more.this,contacts.class);
                intent.putExtra("caller", "more");
                startActivity(intent);
                finish();
            }
        });

        TextView contactPageSwitch;
        contactPageSwitch = findViewById(R.id.textView13);
        contactPageSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(more.this,contacts.class);
                intent.putExtra("caller", "more");
                startActivity(intent);
                finish();
            }
        });

        ImageView homeTab;
        homeTab = findViewById(R.id.switchHome);
        homeTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(more.this,home.class);
                startActivity(intent);
                finish();
            }
        });
        TextView homePageSwitch;
        homePageSwitch = findViewById(R.id.textView12);
        homePageSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(more.this,home.class);
                startActivity(intent);
                finish();
            }
        });

        Button changePassword;
        changePassword = findViewById(R.id.changePassword);
        changePassword.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(more.this, ChangePassword.class);
                startActivity(intent);
            }
        });

        Button logout;
        logout = findViewById(R.id.logoutButton);
        logout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(more.this, login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
