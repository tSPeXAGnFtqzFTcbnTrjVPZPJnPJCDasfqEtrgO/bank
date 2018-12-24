package com.example.phamngocan.ar_sql.fragment;

import android.app.Dialog;
import android.content.Context;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.phamngocan.ar_sql.ActionActivity;
import com.example.phamngocan.ar_sql.DAOManager;
import com.example.phamngocan.ar_sql.Instance;
import com.example.phamngocan.ar_sql.MainActivity;
import com.example.phamngocan.ar_sql.R;
import com.example.phamngocan.ar_sql.adapter.RecycleTaiKhoanAdapter;
import com.example.phamngocan.ar_sql.model.TaiKhoan;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentTaiKhoan extends Fragment {

    @BindView(R.id.progressBasr)
    private static ProgressBar progressBar;
    @BindView(R.id.spinnerChiNhanh)
    Spinner spinnerChiNhanh;

    @BindView(R.id.btnAddTK)
    Button btnAddTK;
    @BindView(R.id.btnRefresh)
    Button btnRefresh;

    @BindView(R.id.recycler)
    RecyclerView recyclerView;

    Dialog dialog;
    EditText editTK, editCMND;
    Button btnok;

    ArrayList<String> spinnerList = new ArrayList<>();
    RecycleTaiKhoanAdapter adapter;

    private static Context context;

    DAOManager daoManager = ActionActivity.daoManager;

    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_taikhoan, container, false);
        ButterKnife.bind(this, view);
        init();
        action();
        initDialog();
        return view;
    }

    private void init() {

        context = getContext();

        spinnerList.add("Tất cả");
        spinnerList.addAll(Instance.chiNhanhList);
        ArrayAdapter<String> adapterChiNhanh = new ArrayAdapter<>(getContext(), R.layout.item_spinner, spinnerList);
        adapterChiNhanh.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerChiNhanh.setAdapter(adapterChiNhanh);


        if (Instance.nhom.equals("CHINHANH")) {

            for (int i = 0; i < spinnerList.size(); i++) {
                if (spinnerList.get(i).equals(Instance.chinhanh)) {
                    spinnerChiNhanh.setSelection(i);
                    break;
                }
            }

            spinnerChiNhanh.setEnabled(false);
            Log.d("AAA", "compare3: " + Instance.chinhanh.trim().compareToIgnoreCase("BENTHANH"));
            if (Instance.chinhanh.trim().compareToIgnoreCase("BENTHANH") == 0) {
                Log.d("AAA", "benthanh");
                adapter = new RecycleTaiKhoanAdapter(getContext(), Instance.taiKhoanBenThanhList);
            } else {
                Log.d("AAA", "tandinh");
                adapter = new RecycleTaiKhoanAdapter(getContext(), Instance.taiKhoanTanDinhList);
            }

        } else {
            adapter = new RecycleTaiKhoanAdapter(getContext(), Instance.taiKhoanList);
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void initDialog() {
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.layout_dialog_addtk);

        editCMND = dialog.findViewById(R.id.editCMND);
        editTK = dialog.findViewById(R.id.editTK);
        btnok = dialog.findViewById(R.id.btnOk);
        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tk, cmnd;
                tk = editTK.getText().toString().trim();
                cmnd = editCMND.getText().toString().trim();

                if (tk.length() == 0 || cmnd.length() == 0) {
                    Toast.makeText(getContext(), "Chua du thong tin", Toast.LENGTH_SHORT).show();
                    return;
                }
                while (tk.length() < 9) {
                    //Log.d("AAA",this.tkChuyen.length()+" chuyen");
                    tk = tk.concat(" ");
                }
                while (cmnd.length() < 10) {
                    //Log.d("AAA",this.tkChuyen.length()+" chuyen");
                    cmnd = cmnd.concat(" ");
                }

                new asyncAddTK().execute(tk, cmnd);

            }

        });

    }

    private void action() {
        spinnerChiNhanh.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                adapter.changeChiNhanh(spinnerList.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                new asyncTaiKhoan().execute();
            }
        });
        btnAddTK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });
    }

    class asyncAddTK extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... strings) {
            FragmentTaiKhoan.progressBar.setVisibility(View.VISIBLE);
            try {
                CallableStatement callableStatement = daoManager.conn.prepareCall(
                        "call [dbo].ADD_TK(?,?,?)"
                );
                Log.d("AAA", strings[0] + "_" + strings[1] + "_" + Instance.chinhanh + "_");
                callableStatement.setString(1, strings[0]);
                callableStatement.setString(2, strings[1]);
                callableStatement.setString(3, Instance.chinhanh);
                callableStatement.execute();
            } catch (SQLException e) {
                Log.d("AAA", "error asyn adtk: " + e.getMessage());
                Toast.makeText(FragmentTaiKhoan.context,e.getMessage(),Toast.LENGTH_SHORT).show();
                return false;
            }

            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            FragmentTaiKhoan.progressBar.setVisibility(View.GONE);
            if (aBoolean) {
                Toast.makeText(FragmentTaiKhoan.context,"Them thanh cong",Toast.LENGTH_SHORT).show();
            }
        }
    }

    class asyncTaiKhoan extends AsyncTask<Void, Void, ResultSet> {

        @Override
        protected ResultSet doInBackground(Void... voids) {
            ResultSet rs = null;
            try {
                CallableStatement callableStatement = daoManager.conn.prepareCall("" +
                        "call [dbo].getTK(?)");
                callableStatement.setString(1, Instance.nhom);
                callableStatement.execute();
                rs = callableStatement.getResultSet();

            } catch (SQLException e) {
                e.printStackTrace();
                Log.d("AAA", "error async taikhoan: " + e.getMessage());
            }
            return rs;
        }

        @Override
        protected void onPostExecute(ResultSet resultSet) {
            if (resultSet != null) {
                try {
                    Instance.taiKhoanList.clear();
                    Instance.taiKhoanTanDinhList.clear();
                    Instance.taiKhoanBenThanhList.clear();
                    while (resultSet.next()) {
                        String sotk, cmnd, macn;
                        String soTien;
                        sotk = resultSet.getString(1);
                        cmnd = resultSet.getString(2);
                        soTien = resultSet.getString(3);
                        macn = resultSet.getString(4);
                        Instance.taiKhoanList.add(new TaiKhoan(sotk, cmnd, macn, soTien));
                        if (macn.charAt(0) == 'B')
                            Instance.taiKhoanBenThanhList.add(new TaiKhoan(sotk, cmnd, macn, soTien));
                        else
                            Instance.taiKhoanTanDinhList.add(new TaiKhoan(sotk, cmnd, macn, soTien));
                        Log.d("AAA", "taikhoan: " + soTien);
                    }
                } catch (SQLException e) {
                    Log.d("AAA", "error async tk postex: " + e.getMessage());
                }
            }
            adapter.notifyDataSetChanged();
            progressBar.setVisibility(View.GONE);
        }
    }
}
