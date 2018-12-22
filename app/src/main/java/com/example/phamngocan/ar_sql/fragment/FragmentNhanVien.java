package com.example.phamngocan.ar_sql.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.phamngocan.ar_sql.ActionActivity;
import com.example.phamngocan.ar_sql.DAOManager;
import com.example.phamngocan.ar_sql.Instance;
import com.example.phamngocan.ar_sql.R;
import com.example.phamngocan.ar_sql.adapter.RecycleAdapter;
import com.example.phamngocan.ar_sql.model.NhanVien;

import java.sql.CallableStatement;
import java.sql.SQLException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FragmentNhanVien extends Fragment {
    @BindView(R.id.btnSave)
    Button btnSave;
    @BindView(R.id.btnUpdate)
    Button btnUpdate;
    @BindView(R.id.btnRevert)
    Button btnRevert;
    @BindView(R.id.recycler)
    RecyclerView recyclerView;
    @BindView(R.id.progressBasr)
    ProgressBar progressBar;

    RecycleAdapter adapter;
    DAOManager daoManager = ActionActivity.daoManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_nhanvien,container,false);
        ButterKnife.bind(this,view);
        init();
        action();
        return view;
    }
    private void init(){
        adapter = new RecycleAdapter(Instance.nhanvienList,getContext());
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
    }
    private void action(){
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean flag = false;
                for(NhanVien nv: Instance.nhanvienList){
                    if(nv.checkNull()){
                        flag = true;
                        break;
                    }
                }
                if(flag){
                    Toast.makeText(getContext(),"Không được bỏ trống!",Toast.LENGTH_SHORT).show();
                }else{
                    for(NhanVien nv: Instance.nhanvienList){
                        nv.update();
                    }
                    progressBar.setVisibility(View.VISIBLE);
                    new asyncUpdate().execute();
                }
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean modify = adapter.getModify();
                modify = !modify;
                adapter.changeStateUpdate(modify);
                if(modify) btnUpdate.setText("Hủy");
                else btnUpdate.setText("Cập nhật");
            }
        });
        btnRevert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.notifyDataSetChanged();
            }
        });
    }
    class asyncUpdate extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            for(int i=0;i<Instance.nhanvienList.size();i++) {
                try {
                    Log.d("AAA","manv: " + Instance.nhanvienList.get(i).getManv());
                    CallableStatement callableStatement = daoManager.conn.prepareCall(
                            "call [dbo].updnhanvien(?,?,?,?,?,?,?)"
                    );
                    callableStatement.setString(1, Instance.nhanvienList.get(i).getManv());
                    callableStatement.setString(2, Instance.nhanvienList.get(i).getHo());
                    callableStatement.setString(3, Instance.nhanvienList.get(i).getTen());
                    callableStatement.setString(4, Instance.nhanvienList.get(i).getDiachi());
                    callableStatement.setString(5, Instance.nhanvienList.get(i).getPhai());
                    callableStatement.setString(6, Instance.nhanvienList.get(i).getSodt());
                    callableStatement.setString(7, Instance.nhanvienList.get(i).getMacn());

                    callableStatement.execute();

                } catch (SQLException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(),e.getMessage()+"",Toast.LENGTH_SHORT).show();
                    Log.d("AAA", "error update nv: " + e.getMessage());
                }
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();
            Toast.makeText(getContext(),"update thành công",Toast.LENGTH_SHORT).show();
        }
    }
}
