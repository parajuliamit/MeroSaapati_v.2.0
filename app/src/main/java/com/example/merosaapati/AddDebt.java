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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddDebt extends AppCompatActivity {
    private SimpleDateFormat dateFor;
    final Calendar myCalendar = Calendar.getInstance();
    Calendar calendar = Calendar.getInstance();
    SQLiteOpenHelper openHelper;
    SQLiteDatabase db;
    EditText amount, rate, dDate, comment;
    ImageButton done;
    RadioButton debtStatusRadio;
    RadioGroup radioDebt;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adddebt);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        ImageView backButton;
        backButton = findViewById(R.id.backAddDebt);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddDebt.this,contacts.class);
//                intent.putExtra("name",getIntent().getStringExtra("name"));
//                intent.putExtra("address",getIntent().getStringExtra("address"));
//                intent.putExtra("status",getIntent().getStringExtra("status"));
//                intent.putExtra("phone",getIntent().getStringExtra("phone"));
//                intent.putExtra("email",getIntent().getStringExtra("email"));
                intent.putExtra("caller","AddDebt");
                startActivity(intent);
                finish();
            }
        });

        TextView name = findViewById(R.id.contactName);
        name.setText(getIntent().getStringExtra("name"));

        TextView address = findViewById(R.id.addressDebt);
        address.setText(getIntent().getStringExtra("address"));

        openHelper = new DatabaseHelper(this);
        done = findViewById(R.id.doneAddDebt);
        amount = findViewById(R.id.amount);
//        tDate = findViewById(R.id.transactionDate);
        dDate = findViewById(R.id.dueDate);
        comment = findViewById(R.id.comment);
        rate = findViewById(R.id.interestRateAdd);

        radioDebt=(RadioGroup)findViewById(R.id.radioDebt);


        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        dDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddDebt.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
            }

        });


        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db=openHelper.getWritableDatabase();
                String debtAmount = amount.getText().toString();
                dateFor = new SimpleDateFormat("MM/dd/yy");
                String date = dateFor.format(calendar.getTime());
                String transaction = date;
                String interestRate = rate.getText().toString();
                //String transaction = tDate.getText().toString();
                String due = dDate.getText().toString();
                String com = comment.getText().toString();
                String id = getIntent().getStringExtra("id");
                if(debtAmount.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Debt Amount can't be empty.",Toast.LENGTH_SHORT).show();
                }else{
                    int selectedId = radioDebt.getCheckedRadioButtonId();
                    debtStatusRadio = (RadioButton) findViewById(selectedId);
                    if(selectedId==-1){
                        Toast.makeText(AddDebt.this,"Select Debt Type", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        ContentValues contentValues = new ContentValues();
                        contentValues.put(DatabaseHelper.COL_DebtAmount, debtAmount);
                        contentValues.put(DatabaseHelper.COL_TransactionDate, transaction);
                        contentValues.put(DatabaseHelper.COL_DueDate, due);
                        contentValues.put(DatabaseHelper.COL_Rate, interestRate);
                        contentValues.put(DatabaseHelper.COL_COMMENT, com);
                        contentValues.put(DatabaseHelper.COL_DebtStatus, debtStatusRadio.getText().toString());
                        long i = db.update(DatabaseHelper.CONTACTS_TABLE, contentValues,DatabaseHelper.COL_ID+" = ?",
                                new String[] { id});
                        Log.d("hareram","Update Query Result: "+i+"  id  "+id);
                        db.close();
                        Intent intent = new Intent(AddDebt.this, contacts.class);
                        intent.putExtra("caller", "AddDebt");
                        startActivity(intent);
                        finish();
                        Toast.makeText(AddDebt.this, "Debt Added", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dDate.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(AddDebt.this,contacts.class);
        intent.putExtra("caller","AddDebt");
//        intent.putExtra("address",getIntent().getStringExtra("address"));
//        intent.putExtra("status",getIntent().getStringExtra("status"));
//        intent.putExtra("phone",getIntent().getStringExtra("phone"));
//        intent.putExtra("email",getIntent().getStringExtra("email"));
        startActivity(intent);
        finish();
    }
}
