package com.example.phamngocan.ar_sql.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    ArrayList<Fragment> fragmentList;
    public ViewPagerAdapter(FragmentManager fm, ArrayList<Fragment> fms) {
        super(fm);
        fragmentList = fms;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if(position==0) return "Giao dịch";
        if(position==1) return "Nhân viên";
        if(position==2) return "Khách hàng";
        if(position==3) return "Tài Khoản";
        if(position==4) return "Thống kê";
        return "XX";
    }
}
