package com.example.merosaapati;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class load extends Activity {
    SQLiteDatabase db;
    SQLiteOpenHelper openHelper;
    Cursor cursor1;
    int count;
    private final int SPLASH_DISPLAY_LENGTH = 1200;            //time to load

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.load);

        openHelper = new DatabaseHelper(this);
        db = openHelper.getReadableDatabase();
        cursor1 = db.rawQuery("SELECT ID FROM " + DatabaseHelper.TABLE_NAME, null);
        Log.d("STATUS", "Total User " + cursor1.getCount());
        if (cursor1.getCount() == 0) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    /* Create an Intent that will start the Menu-Activity. */
                    Intent mainIntent = new Intent(load.this, register.class);
                    load.this.startActivity(mainIntent);
                    load.this.finish();
                }
            }, SPLASH_DISPLAY_LENGTH);
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    /* Create an Intent that will start the Menu-Activity. */
                    Intent mainIntent = new Intent(load.this, login.class);
                    load.this.startActivity(mainIntent);
                    load.this.finish();
                }
            }, SPLASH_DISPLAY_LENGTH);
        }
    }
}

