package com.example.phamngocan.ar_sql;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Login2Activity extends AppCompatActivity {

    @BindView(R.id.spinner)
    Spinner spinnerChiNhanh;
    @BindView(R.id.txtvId)
    TextView txtvId;
    @BindView(R.id.txtvPass)
    TextView txtvPass;
    @BindView(R.id.editId)
    EditText editId;
    @BindView(R.id.editPass)
    EditText editPass;
    @BindView(R.id.btnSignIn)
    Button btnSignIn;
    ArrayAdapter<String> adapterChiNhanh;

    DAOManager daoManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        ButterKnife.bind(this);

        init();
        action();
    }

    private void init() {
        daoManager = DAOManager.getInstance();
        Log.d("AAA","init: " + daoManager.conn+" " + daoManager.stmt);

        adapterChiNhanh = new ArrayAdapter<>(getApplicationContext(), R.layout.item_spinner, Instance.chiNhanhList);


        adapterChiNhanh.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerChiNhanh.setAdapter(adapterChiNhanh);


        spinnerChiNhanh.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Instance.serverport = Instance.port[position + 1];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void action() {
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Instance.loginName = editId.getText().toString();
                Instance.pass = editPass.getText().toString();
                Log.d("AAA","action: " + Instance.loginName+" " +Instance.pass);
                new asyncSignIn().execute();
            }
        });
    }

    class asyncSignIn extends AsyncTask<Void, Void, Pair<Boolean, String>> {

        @Override
        protected Pair<Boolean, String> doInBackground(Void... voids) {
            return daoManager.open();

        }

        @Override
        protected void onPostExecute(Pair<Boolean, String> booleanStringPair) {
            if (booleanStringPair.first == false) {
                Toast.makeText(getApplicationContext(),
                        "Đăng nhập thất bại" + booleanStringPair.second,
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "Đăng nhập thành công",
                        Toast.LENGTH_SHORT).show();
                new asyncQuery().execute("SELECT * FROM [dbo].KhachHang");
                new asyncGetInfor().execute();
            }
        }
    }

    class asyncQuery extends AsyncTask<String, Void, ResultSet> {

        @Override
        protected ResultSet doInBackground(String... strings) {
            ResultSet rs = null;
            try {
                rs = daoManager.stmt.executeQuery(strings[0]);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return rs;
        }

        @Override
        protected void onPostExecute(ResultSet resultSet) {
            if (resultSet != null) {

                try {
                    while (resultSet.next()) {
                        Log.d("AAA", "async query: " + resultSet.getString(1));
                    }
                } catch (SQLException e) {
                    Log.e("AAA", "error async query:" + e.getMessage());
                    e.printStackTrace();
                }

            }
        }
    }

    class asyncGetInfor extends AsyncTask<Void, Void, ResultSet> {

        @Override
        protected ResultSet doInBackground(Void... voids) {
            try {
                CallableStatement callableStatement = daoManager.conn.prepareCall(
                        "{ call [dbo].SP_DANGNHAP(?)}");
                callableStatement.setString(1, Instance.loginName);
                callableStatement.execute();
                return callableStatement.getResultSet();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ResultSet rs) {
            if (rs != null) {
                try {
                    while (rs.next()) {
                        Instance.userName = rs.getString(1);
                        Instance.hoten = rs.getString(2);
                        Instance.nhom = rs.getString(3);
                        Log.d("AAA", "getinfor: "+ Instance.hoten);
                    }
                } catch (SQLException e) {
                    Log.d("AAA", "error asycn call: " + e.getMessage());
                }
               startActivity(new Intent(Login2Activity.this,ActionActivity.class));
            }
        }
    }
}
