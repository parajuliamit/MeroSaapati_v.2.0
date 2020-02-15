package com.example.merosaapati;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class viewDebt extends AppCompatActivity {
    private SimpleDateFormat dateFor;
    Calendar calendar = Calendar.getInstance();
    SQLiteDatabase db;
    SQLiteOpenHelper openHelper;
    ImageButton editDebt;
    Date startDateValue, endDateValue, dueDateValue;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewdebt);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        ImageView backButton;
        backButton = findViewById(R.id.backViewDebt);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button deleteButton;
        deleteButton = findViewById(R.id.deleteDebtButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(viewDebt.this, R.style.AlertDialogCustom );
                builder.setTitle("CLEAR DEBT")
                        .setPositiveButton("Clear", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String uid = getIntent().getStringExtra("id");
                                openHelper=new DatabaseHelper(getBaseContext());
                                db = openHelper.getWritableDatabase();
                                db.execSQL("UPDATE "+DatabaseHelper.CONTACTS_TABLE+" SET "+DatabaseHelper.COL_DebtStatus+" =NULL, "+DatabaseHelper.COL_DebtAmount+" = NULL, "+DatabaseHelper.COL_TransactionDate+" = NULL, "+DatabaseHelper.COL_DueDate+" = NULL, "+DatabaseHelper.COL_COMMENT+" = NULL WHERE "+DatabaseHelper.COL_ID+" ="+uid);
                                Toast.makeText(getBaseContext(), "Debt Deleted", Toast.LENGTH_SHORT).show();
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

        editDebt = findViewById(R.id.editDebt);
        editDebt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(viewDebt.this,EditDebt.class);
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
        });



        String name = getIntent().getStringExtra("name");
        TextView setName = (TextView) findViewById(R.id.nameViewDebt);
        setName.setText(name);

        String address = getIntent().getStringExtra("address");
        TextView setAddress = (TextView) findViewById(R.id.addressViewDebt);
        setAddress.setText(address);

        String debt = getIntent().getStringExtra("debt");
        TextView setDebt = (TextView) findViewById(R.id.textView10);
        setDebt.setText(debt);

        String transaction = getIntent().getStringExtra("transaction");
        TextView setTransaction = (TextView) findViewById(R.id.transactionDateView);
        setTransaction.setText(transaction);


        String due = getIntent().getStringExtra("due");
        TextView setDue = (TextView) findViewById(R.id.dueDateView);
        setDue.setText(due);

        String status = getIntent().getStringExtra("status");
        TextView setStatus = (TextView) findViewById(R.id.statusViewDebt);
        String statusType;
        if (status.equals("TO PAY")){
            statusType = "You are paying.";
        }else{
            statusType = "You are receiving.";
        }
        setStatus.setText(statusType);

        dateFor = new SimpleDateFormat("MM/dd/yy");
        String date = dateFor.format(calendar.getTime());
        startDateValue = new Date(transaction);
        endDateValue = new Date(date);

        long diff = endDateValue.getTime() - startDateValue.getTime();
        long seconds = diff / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = (hours / 24);

        TextView setInterest = (TextView)findViewById(R.id.calculateInterest);
        TextView setEstimate = (TextView)findViewById(R.id.estimateInterest);

        String rate = getIntent().getStringExtra("rate");
        TextView setRate = (TextView) findViewById(R.id.interestRate);
        TextView setRate2 = (TextView)findViewById(R.id.interestRate2);
        TextView daysRem = (TextView)findViewById(R.id.remainingDays);
        if (rate.isEmpty()) {
            setInterest.setText(null);
            setRate.setText("INTEREST INAPPLICABLE");
            setRate2.setText(("INTEREST INAPPLICABLE"));
            setEstimate.setText(null);
        }else {
            setRate.setText("at "+rate+"% p.a.");
            double rateDouble = Double.valueOf(rate);
            double debtDouble = Double.valueOf(debt);
            double calculateInterest = (debtDouble * days * rateDouble)/36500;
            calculateInterest = Math.floor(calculateInterest * 100) / 100;
            setInterest.setText(String.valueOf(calculateInterest));
        }


        if (!due.isEmpty()&&!rate.isEmpty()) {
            dueDateValue = new Date(due);
            long diff2 = dueDateValue.getTime() - startDateValue.getTime();
            long seconds2 = diff2 / 1000;
            long minutes2 = seconds2 / 60;
            long hours2 = minutes2 / 60;
            long days2 = (hours2 / 24);
            setRate2.setText("at "+rate+"% p.a.");
            double rateDouble = Double.valueOf(rate);
            double debtDouble = Double.valueOf(debt);
            double calculateInterest = (debtDouble * days2 * rateDouble)/36500;
            calculateInterest=Math.floor(calculateInterest * 100) / 100;
            setEstimate.setText(String.valueOf(calculateInterest));
        }else if (due.isEmpty()){
            setEstimate.setText(null);
            setRate2.setText("DUE DATE NOT SET");
            daysRem.setTypeface(Typeface.DEFAULT);
            daysRem.setText("NA");

        }

        if (!due.isEmpty()){
            dueDateValue = new Date(due);

            long diff3 = dueDateValue.getTime() - endDateValue.getTime();
            long seconds3 = diff3 / 1000;
            long minutes3 = seconds3 / 60;
            long hours3 = minutes3 / 60;
            long days3 = (hours3 / 24);
            if (days3<1){
                daysRem.setTextColor(Color.RED);
            }else {
                daysRem.setTextColor(Color.rgb(4,161,51));
            }
            daysRem.setText(String.valueOf(days3));
        }


        String phone = getIntent().getStringExtra("phone");
        TextView setPhone = (TextView) findViewById(R.id.phoneViewDebt);
        setPhone.setText(phone);

        String email = getIntent().getStringExtra("email");
        TextView setEmail = (TextView) findViewById(R.id.emailViewDebt);
        setEmail.setText(email);

        String comment = getIntent().getStringExtra("comment");
        TextView setComment = (TextView) findViewById(R.id.commentView);
        setComment.setText(comment);


    }
}
