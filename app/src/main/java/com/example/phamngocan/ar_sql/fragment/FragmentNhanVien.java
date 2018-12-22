package com.example.phamngocan.ar_sql.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.phamngocan.ar_sql.Instance;
import com.example.phamngocan.ar_sql.R;
import com.example.phamngocan.ar_sql.adapter.RecycleAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentNhanVien extends Fragment {
    @BindView(R.id.recycler)
    RecyclerView recyclerView;

    RecycleAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_nhanvien,container,false);
        ButterKnife.bind(this,view);
        init();
        return view;
    }
    private void init(){
        adapter = new RecycleAdapter(Instance.nhanvienList,getContext());
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
    }
}
