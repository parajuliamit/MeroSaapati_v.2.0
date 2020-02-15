package com.example.merosaapati;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ToPayListAdapter extends BaseAdapter {
    Context context;
    ArrayList<String> userID;
    ArrayList<String> UserName;
    ArrayList<String> DebtStatus;
    ArrayList<String> DebtAmount;

    public ToPayListAdapter(
            Context context2,
            ArrayList<String> id,
            ArrayList<String> name,
            ArrayList<String> status,
            ArrayList<String> amount
    )
    {

        this.context = context2;
        this.userID = id;
        this.UserName = name;
        this.DebtStatus = status;
        this.DebtAmount = amount;
    }


    public int getCount() {
        // TODO Auto-generated method stub
        return userID.size();
    }

    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    public View getView(int position, View child, ViewGroup parent) {

        ToPayListAdapter.Holder holder;

        LayoutInflater layoutInflater;

        if (child == null) {
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            child = layoutInflater.inflate(R.layout.debt_list, null);

            holder = new ToPayListAdapter.Holder();

            holder.textviewname = (TextView) child.findViewById(R.id.nameDebtList);
            holder.textviewamount = (TextView) child.findViewById(R.id.amountDebtList);

            child.setTag(holder);

        } else {

            holder = (ToPayListAdapter.Holder) child.getTag();
        }
        holder.textviewname.setText(UserName.get(position));
        holder.textviewamount.setText(DebtAmount.get(position));

        return child;
    }

    public class Holder {
        TextView textviewname;
        TextView textviewamount;
    }

}