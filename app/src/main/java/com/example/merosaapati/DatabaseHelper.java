package com.example.merosaapati;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "register.db";
    public static final String TABLE_NAME = "register";
    public static final String COL_1 = "id";
    public static final String COL_2 = "username";
    public static final String COL_3 = "password";

    public static final String CONTACTS_TABLE = "contacts";
    public static final String COL_ID = "id";
    public static final String COL_Name = "Name";
    public static final String COL_Address = "Address";
    public static final String COL_Phone = "Phone";
    public static final String COL_Email = "Email";
    public static final String COL_DebtStatus = "Debt_Status";
    public static final String COL_DebtAmount = "Debt_Amount";
    public static final String COL_TransactionDate = "Transaction_Date";
    public static final String COL_DueDate = "Due_Date";
//    public static final String COL_TotalInterest = "Total_Interest";
    public static final String COL_Rate = "Interest_Rate";
    public static  final String COL_COMMENT = "Comment";


    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" + COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_2 + " TEXT, " + COL_3 + " TEXT)");
        db.execSQL("CREATE TABLE " + CONTACTS_TABLE + " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_Name + "  TEXT, " + COL_Address + " TEXT, " + COL_Phone + " TEXT, " + COL_Email + " TEXT, " + COL_DebtStatus + " TEXT, " + COL_DebtAmount + " REAL, " + COL_TransactionDate + " DATE, " + COL_DueDate + " DATE, "+  COL_Rate + " REAL, " + COL_COMMENT + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CONTACTS_TABLE);
        //Drop older table if exists
        onCreate(db);
    }

//    public void deleteDebt(SQLiteDatabase db,String userID){
//        db.execSQL("DELETE "+COL_DebtStatus+", "+COL_DebtAmount+", "+COL_TransactionDate+", "+COL_DueDate+", "+COL_COMMENT+" FROM "+CONTACTS_TABLE+" WHERE "+COL_ID+" = "+userID);
//        Log.d("Heram","Delete bhayo yr");
//    }
}
