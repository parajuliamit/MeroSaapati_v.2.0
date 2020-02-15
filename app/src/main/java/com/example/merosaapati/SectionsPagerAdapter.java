package com.example.merosaapati;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class SectionsPagerAdapter extends FragmentPagerAdapter {
    private final Context mContext;
    public SectionsPagerAdapter(Context context , FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;

        switch(position){
            case 0:
                fragment = new allfragment();
                break;
            case 1:
                fragment = new topayfragment();
                break;
            case 2:
                fragment = new toreceivefragment();
        }
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "All";
            case 1:
                return "To Pay ";
            case 2:
                return "To Receive";
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
