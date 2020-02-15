package com.example.merosaapati;
import java.util.ArrayList;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SQLiteListAdapter extends BaseAdapter {

    Context context;
    ArrayList<String> userID;
    ArrayList<String> UserName;
    ArrayList<String> DebtStatus ;


    public SQLiteListAdapter(
            Context context2,
            ArrayList<String> id,
            ArrayList<String> name,
            ArrayList<String> status
    )
    {

        this.context = context2;
        this.userID = id;
        this.UserName = name;
        this.DebtStatus = status;
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

        Holder holder;

        LayoutInflater layoutInflater;

        if (child == null) {
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            child = layoutInflater.inflate(R.layout.contact_list, null);

            holder = new Holder();

            holder.textviewname = (TextView) child.findViewById(R.id.nameTextViewID);
            holder.textviewstatus = (TextView) child.findViewById(R.id.statusTextViewID);

            child.setTag(holder);

        } else {

            holder = (Holder) child.getTag();
        }
        holder.textviewname.setText(UserName.get(position));
        holder.textviewstatus.setText(DebtStatus.get(position));
        if (DebtStatus.get(position)==null) {
            Log.d("Status","No Debt");
        }else{
            if (DebtStatus.get(position).equals("TO RECEIVE")) {
                holder.textviewstatus.setTextColor(Color.rgb(4, 161, 51));
            }
        }
        return child;
    }

    public class Holder {
        TextView textviewname;
        TextView textviewstatus;
    }

}