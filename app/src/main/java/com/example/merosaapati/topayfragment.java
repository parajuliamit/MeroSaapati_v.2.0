package com.example.merosaapati;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class topayfragment extends Fragment {
    DatabaseHelper db;
    SQLiteDatabase SQLITEDATABASE;
    Cursor cursor;
    DebtListAdapter ListAdapter ;
    TextView totalDebt;

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
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View topayfragment = inflater.inflate(R.layout.topayfragment,container,false);
        LISTVIEW = topayfragment.findViewById(R.id.paydebtlist);
        db = new DatabaseHelper(getContext());

        LISTVIEW.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int index, long viewId) {

                Intent intent = new Intent(getContext(), viewDebt.class);
                intent.putExtra("id", ID_ArrayList.get(index));
                intent.putExtra("name",NAME_ArrayList.get(index));
                intent.putExtra("status",STATUS_ArrayList.get(index));
                intent.putExtra("address",ADDRESS_ArrayList.get(index));
                intent.putExtra("phone",PHONE_ArrayList.get(index));
                intent.putExtra("email",EMAIL_ArrayList.get(index));
                intent.putExtra("debt",DEBT_ArrayList.get(index));
                intent.putExtra("transaction",TRANSACTION_ArrayList.get(index));
                intent.putExtra("due",DUE_ArrayList.get(index));
                intent.putExtra("comment",COMMENT_ArrayList.get(index));
                intent.putExtra("rate",INTEREST_ArrayList.get(index));
                startActivity(intent);
//                Toast.makeText(getBaseContext(),"List Item Clicked", Toast.LENGTH_SHORT).show();
            }
        });





        return topayfragment;
    }

    @Override
    public void onResume() {

        ShowSQLiteDBdata() ;

        super.onResume();

    }

    private void ShowSQLiteDBdata() {
        String amount;
        double netAmount = 0.0;
        String status = "TO PAY";
        SQLITEDATABASE = db.getReadableDatabase();
        cursor = SQLITEDATABASE.rawQuery("SELECT * FROM "+DatabaseHelper.CONTACTS_TABLE+" WHERE "+DatabaseHelper.COL_DebtStatus + "=?", new String[]{status});
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
//        Log.d("STATUS","Column index for total "+cursor.getColumnIndex("id")+" "+DatabaseHelper.COL_DebtStatus);
        if (cursor.moveToFirst()) {
            do {
                ID_ArrayList.add(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_ID)));
                NAME_ArrayList.add(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_Name)));
                ADDRESS_ArrayList.add(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_Address)));
                STATUS_ArrayList.add(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_DebtStatus)));
                PHONE_ArrayList.add(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_Phone)));
                EMAIL_ArrayList.add(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_Email)));
                amount = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_DebtAmount));
                DEBT_ArrayList.add(amount);
                TRANSACTION_ArrayList.add(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_TransactionDate)));
                DUE_ArrayList.add(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_DueDate)));
                INTEREST_ArrayList.add(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_Rate)));
                COMMENT_ArrayList.add(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_COMMENT)));

                netAmount += Double.parseDouble(amount);
            } while (cursor.moveToNext());
        }
        totalDebt = getActivity().findViewById(R.id.pay_number);
        totalDebt.setText(String.valueOf(netAmount));
        ListAdapter = new DebtListAdapter(getContext(),
                ID_ArrayList,
                NAME_ArrayList,
                STATUS_ArrayList,
                DEBT_ArrayList

        );
        LISTVIEW.setAdapter(ListAdapter);

        cursor.close();
        db.close();
    }
}
