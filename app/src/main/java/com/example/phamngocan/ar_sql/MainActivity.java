package com.example.phamngocan.ar_sql;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.phamngocan.ar_sql.SQLite.EnumTableNV;
import com.example.phamngocan.ar_sql.model.TaiKhoan;

import java.io.File;
import java.sql.CallableStatement;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class MainActivity extends AppCompatActivity {

    DAOManager daoManager;
    ResultSet rs;
    String filename = "nv.xls";
    File directory;
    WritableSheet sheet;
    WritableWorkbook workbook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        initFile();
        setUpData();
        //action();
    }

    private void setUpData(){
        new asyncStatement().execute("SELECT * FROM [dbo].ChiNhanh");
    }

    private void init() {
        for (int i = 0; i < EnumTableNV.values().length; i++) {
            Instance.columnNV.add(EnumTableNV.values()[i].toString());
        }

        daoManager = DAOManager.getInstance();
        new async().execute();
    }

    private void action() {

        try {

            PreparedStatement preparedStatement = daoManager.conn.prepareStatement(
                    "SELECT * FROM [dbo].KhachHang");
//            PreparedStatement preparedStatement= daoManager.conn.prepareStatement(
//                    "SELECT * FROM NGANHANG.[dbo].KhachHang");
            CallableStatement callableStatement = daoManager.conn.prepareCall(
                    "{ call getNV(?)}");

            preparedStatement.setEscapeProcessing(true);
            preparedStatement.setQueryTimeout(10);

            //callableStatement.setInt(1, 0);

            Log.d("AAA", "before exec");

             new asyncCall().execute(callableStatement);
           // new asyncStatement().execute("SELECT * FROM [dbo].getNV2");

//            rs = callableStatement.executeQuery();

        } catch (SQLException e) {
            Log.e("AAA", "error statement: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void initFile() {

        directory = new File("/mnt/shared/ShareAndroid");
        Log.d("AAA", "file: " + Environment.getExternalStorageDirectory().getAbsolutePath());
        if (!directory.isDirectory()) {

            directory.mkdir();
        }
        try {
            File file = new File(directory, filename);
            WorkbookSettings wbSetting = new WorkbookSettings();
            wbSetting.setLocale(new Locale("en", "EN"));
            workbook = Workbook.createWorkbook(file, wbSetting);

            sheet = workbook.createSheet("NhanVien", 0);

        } catch (Exception e) {
            Log.e("AAA", "error create  file " + e.getMessage());
        }
    }

    private void writeFile() {
        try {
            sheet.mergeCells(0, 0, Instance.columnNV.size() - 1, 0);
            sheet.addCell(new Label(0, 0, "Satisify staff"));

            for (int i = 0; i < Instance.columnNV.size(); i++) {
                sheet.addCell(new Label(i, 1, Instance.columnNV.get(i)));
            }
            int row = 2;
            while (false && rs.next()) {
                Log.d("AAA", rs.getString(1));
                for (int i = 0; i < Instance.columnNV.size(); i++) {
                    sheet.addCell(new Label(i, row, rs.getString(Instance.columnNV.get(i))));
                }
                row++;
            }
            workbook.write();
            workbook.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class asyncStatement extends AsyncTask<String, Void, ResultSet> {
        @Override
        protected ResultSet doInBackground(String... query) {
            try {
                Log.d("AAA", "async backgr");
                //  preparedStatements[0].execute();
                Log.d("AAA","async stmt: " + daoManager.stmt);
                ResultSet resultSet = daoManager.stmt.executeQuery(query[0]);
                //  daoManager.conn.commit();
                return resultSet;

            } catch (SQLException e) {
                Log.e("AAA", "aync bg err: " + e.getMessage());
                e.printStackTrace();
            }
            return null;

        }

        @Override
        protected void onPostExecute(ResultSet resultSet) {
            super.onPostExecute(resultSet);
            try {
                if (resultSet != null) {
                    rs = resultSet;
                    Log.d("AAA", "post exec: " + resultSet.getRow());
                  //  writeFile();

                    Instance.chiNhanhList.clear();
                    while (resultSet.next()) {
                        try {
                            Log.d("AAA",resultSet.getString(1));
                            Instance.chiNhanhList.add(resultSet.getString(1));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    new asyncTaiKhoan().execute();

                }

            } catch (SQLException sqlEx) {
                Log.e("AAA", "async sql exception: " + sqlEx.getMessage());
            }
        }
    }
    class asyncTaiKhoan extends AsyncTask<Void,Void,ResultSet>{

        @Override
        protected ResultSet doInBackground(Void... voids) {
            ResultSet rs = null;
            try {
                rs = daoManager.stmt.executeQuery("SELECT * FROM [dbo].TaiKhoan");

            } catch (SQLException e) {
                e.printStackTrace();
                Log.d("AAA","error async taikhoan: " + e.getMessage());
            }
            return rs;
        }

        @Override
        protected void onPostExecute(ResultSet resultSet) {
            if(resultSet!=null){
                try {
                    Instance.taiKhoanList.clear();
                    while (resultSet.next()) {
                        String sotk,cmnd,macn;
                        String soTien;
                        sotk = resultSet.getString(1);
                        cmnd = resultSet.getString(2);
                        soTien = resultSet.getString(3);
                        macn = resultSet.getString(4);
                        Instance.taiKhoanList.add(new TaiKhoan(sotk,cmnd,macn,soTien));
                        if(macn.charAt(0)=='B') Instance.taiKhoanBenThanhList.add(new TaiKhoan(sotk,cmnd,macn,soTien));
                        else Instance.taiKhoanTanDinhList.add(new TaiKhoan(sotk,cmnd,macn,soTien));
                        Log.d("AAA","taikhoan: " + soTien);
                    }
                }catch (SQLException e){
                    Log.d("AAA","error async tk postex: " + e.getMessage());
                }
            }
            new asyncClose().execute();
        }
    }

    class asyncCall extends AsyncTask<CallableStatement, Void, ResultSet> {

        @Override
        protected ResultSet doInBackground(CallableStatement... callableStatements) {
            try {
                Log.d("AAA", "async backgr");
                //callableStatements[0].execute();
                callableStatements[0].setString(1,"BENTHANH");

                callableStatements[0].execute();

                return callableStatements[0].getResultSet();
            } catch (SQLException e) {
                Log.e("AAA", "aync bg err: " + e.getMessage());
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            Log.i("AAA", "progress: " + values.toString());
        }

        @Override
        protected void onPostExecute(ResultSet resultSet) {
            super.onPostExecute(resultSet);
            try {

                if (resultSet != null) {
                   // rs=resultSet;
                 //   writeFile();

                    while (resultSet.next()) {
                        try {
                            Log.i("AAA", "call: "+ resultSet.getString(1));
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }

            } catch (SQLException sqlEx) {
                Log.e("AAA", "async sql exception: " + sqlEx.getMessage());
            }
        }
    }

    class asyncClose extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            daoManager.close();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            startActivity(new Intent(MainActivity.this,Login2Activity.class));
        }
    }
    class async extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            Log.d("AAA", "connecting progress");
            daoManager.open();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.d("AAA", "" + daoManager);
           // action();
        }
    }
}
