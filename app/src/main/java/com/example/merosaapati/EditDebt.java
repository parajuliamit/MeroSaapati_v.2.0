package com.example.merosaapati;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class EditDebt extends AppCompatActivity {
//    private SimpleDateFormat dateFor;
    final Calendar calendar = Calendar.getInstance();
    SQLiteDatabase db;
    SQLiteOpenHelper openHelper;
    TextView setName, setAddress, setAmount, setRate, setDueDate, setComment;
    RadioButton debtStatus;
    RadioGroup radioDebt;
    ImageButton done, backButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editdebt);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        setName = (TextView) findViewById(R.id.contactNameEdit);
        setAddress = (TextView) findViewById((R.id.addressEditDebt));
        setAmount = (TextView) findViewById(R.id.amountEdit);
        setDueDate = (TextView) findViewById(R.id.dueDateEdit);
        setRate = (TextView) findViewById(R.id.interestRateEdit);
        setComment = (TextView) findViewById((R.id.commentEdit));
        done = (ImageButton) findViewById(R.id.doneEditDebt);
        backButton = (ImageButton) findViewById(R.id.backEditDebt);
        radioDebt=(RadioGroup)findViewById(R.id.radioEditDebt);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(EditDebt.this, contacts.class);
//                intent.putExtra("caller", "EditDebt");
//                startActivity(intent);
                finish();
            }
        });

        setName.setText(getIntent().getStringExtra("name"));
        setAddress.setText(getIntent().getStringExtra("address"));
        setAmount.setText(getIntent().getStringExtra("debt"));
        setComment.setText(getIntent().getStringExtra("comment"));
        setRate.setText(getIntent().getStringExtra("rate"));

        if (getIntent().getStringExtra("status").equals("TO PAY")){
            radioDebt.check(R.id.radioPayEdit);
        }else{
            radioDebt.check(R.id.radioReceiveEdit);
        }

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        setDueDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                DatePickerDialog datePickerDialog = new DatePickerDialog(EditDebt.this, date, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
            }

        });


        final String id = getIntent().getStringExtra("id");
        openHelper = new DatabaseHelper(this);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db=openHelper.getWritableDatabase();
                String debtAmount = setAmount.getText().toString();
//                dateFor = new SimpleDateFormat("MM/dd/yy");
//                String date = dateFor.format(calendar.getTime());
//                String transaction = date;
//                Log.d("heram hai",transaction);
                String interestRate = setRate.getText().toString();
                //String transaction = tDate.getText().toString();
                String due = setDueDate.getText().toString();
                String com = setComment.getText().toString();
                if(debtAmount.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Debt Amount can't be empty.",Toast.LENGTH_SHORT).show();
                }else{
                    int selectedId = radioDebt.getCheckedRadioButtonId();
                    debtStatus = (RadioButton) findViewById(selectedId);
                    if(selectedId==-1){
                        Toast.makeText(EditDebt.this,"Select Debt Type", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        ContentValues contentValues = new ContentValues();
                        contentValues.put(DatabaseHelper.COL_DebtAmount, debtAmount);
//                        contentValues.put(DatabaseHelper.COL_TransactionDate, transaction);
                        contentValues.put(DatabaseHelper.COL_DueDate, due);
                        contentValues.put(DatabaseHelper.COL_Rate, interestRate);
                        contentValues.put(DatabaseHelper.COL_COMMENT, com);
                        contentValues.put(DatabaseHelper.COL_DebtStatus, debtStatus.getText().toString());
                        long i = db.update(DatabaseHelper.CONTACTS_TABLE, contentValues,DatabaseHelper.COL_ID+" = ?",
                                new String[] { id});
                        db.close();
                        finish();
                        Toast.makeText(EditDebt.this, "Debt Edited", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        setDueDate.setText(sdf.format(calendar.getTime()));
    }
}
