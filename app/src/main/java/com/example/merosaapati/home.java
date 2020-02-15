package com.example.merosaapati;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class home extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.home);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabLayout);
        tabs.setupWithViewPager(viewPager);

        ImageView contactPage;
        contactPage = findViewById(R.id.switchContact);
        contactPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home.this,contacts.class);
                intent.putExtra("caller", "homeButton");
                startActivity(intent);
            }
        });

        ImageView addDebt;
        addDebt = findViewById(R.id.addDebtButton);
        addDebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home.this,contacts.class);
                intent.putExtra("caller", "home");
                startActivity(intent);
            }
        });

        TextView contactPageSwitch;
        contactPageSwitch = findViewById(R.id.textView13);
        contactPageSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home.this,contacts.class);
                intent.putExtra("caller", "homeText");
                startActivity(intent);
            }
        });

        ImageView moreTab;
        moreTab = findViewById(R.id.switchMore);
        moreTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home.this,more.class);
                startActivity(intent);
            }
        });

        TextView morePageSwitch;
        morePageSwitch = findViewById(R.id.textView14);
        morePageSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home.this,more.class);
                startActivity(intent);
            }
        });

//        Intent notifyIntent = new Intent(this,NotificationReceiver.class);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast
//                (home.this, 1, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//        AlarmManager alarmManager = (AlarmManager) this.getSystemService(this.ALARM_SERVICE);
//        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,  System.currentTimeMillis(),
//                 24*1000, pendingIntent);
    }
}
