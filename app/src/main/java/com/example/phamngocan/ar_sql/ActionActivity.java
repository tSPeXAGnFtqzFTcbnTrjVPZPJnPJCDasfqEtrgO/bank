package com.example.phamngocan.ar_sql;

import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.phamngocan.ar_sql.adapter.ViewPagerAdapter;
import com.example.phamngocan.ar_sql.fragment.FragmentChuyenTien;
import com.example.phamngocan.ar_sql.fragment.FragmentKhachHang;
import com.example.phamngocan.ar_sql.fragment.FragmentNhanVien;
import com.example.phamngocan.ar_sql.fragment.FragmentTaiKhoan;
import com.example.phamngocan.ar_sql.fragment.FragmentThongKeGiaoDich;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActionActivity extends AppCompatActivity {

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.txtvInfor)
    TextView txtvInfor;

    ViewPagerAdapter viewPagerAdapter;
    ArrayList<Fragment> fragments = new ArrayList<>();
    public static DAOManager daoManager = DAOManager.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action);
        ButterKnife.bind(this);

        new async().execute();
    }

    private void init() {
        txtvInfor.setText("Mã: " + Instance.userName + "; " +
                "Tên: " + Instance.hoten + "; "
                + "Nhóm: " + Instance.nhom);

        fragments.add(new FragmentChuyenTien());
        fragments.add(new FragmentNhanVien());
        fragments.add(new FragmentKhachHang());
        fragments.add(new FragmentTaiKhoan());
        fragments.add(new FragmentThongKeGiaoDich());
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));

    }
    class async extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            daoManager.open();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            init();
        }
    }
}
