package com.example.phamngocan.ar_sql;

import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.phamngocan.ar_sql.SQLite.EnumTableNV;

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

public class MainActivity extends AppCompatActivity {

    DAOManager daoManager;
    ResultSet rs;
    String filename = "nv.xlsx";
    File directory;
    WritableSheet sheet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        initFile();
        //action();
    }

    private void init() {
        for(int i = 0;i<EnumTableNV.values().length;i++){
            Instance.columnNV.add(EnumTableNV.values()[i].toString());
        }

        daoManager = new DAOManager();
        new async().execute();
    }

    private void action() {

        try {

            PreparedStatement preparedStatement = daoManager.conn.prepareStatement(
                    "SELECT * FROM [dbo].KhachHang");
//            PreparedStatement preparedStatement= daoManager.conn.prepareStatement(
//                    "SELECT * FROM NGANHANG.[dbo].KhachHang");
            CallableStatement callableStatement = daoManager.conn.prepareCall(
                    "{ call [dbo].getNV2}");

            preparedStatement.setEscapeProcessing(true);
            preparedStatement.setQueryTimeout(10);

            //callableStatement.setInt(1, 0);

            Log.d("AAA", "before exec");

            // new asyncCall().execute(callableStatement);
            new asyncStatement().execute("SELECT * FROM [dbo].getNV2");

//            rs = callableStatement.executeQuery();

        } catch (SQLException e) {
            Log.e("AAA", "error statement: " + e.getMessage());
            e.printStackTrace();
        }
    }
    private void initFile(){

        directory = new File("/mnt/shared/ShareAndroid");
        Log.d("AAA","file: "+Environment.getExternalStorageDirectory().getAbsolutePath());
        if(!directory.isDirectory()){

            directory.mkdir();
        }
        try{
            File file = new File(directory,filename);
            WorkbookSettings wbSetting = new WorkbookSettings();
            wbSetting.setLocale(new Locale("en","EN"));
            WritableWorkbook workbook = Workbook.createWorkbook(file,wbSetting);

             sheet = workbook.createSheet("NhanVien",0);

             for(int i=0;i<Instance.columnNV.size();i++){
                 sheet.addCell(new Label(i,0,Instance.columnNV.get(i)));
             }
        }catch (Exception e){
            Log.e("AAA","error create  file "+ e.getMessage());
        }
    }

    class asyncStatement extends AsyncTask<String, Void, ResultSet> {
        @Override
        protected ResultSet doInBackground(String... query) {
            try {
                Log.d("AAA", "async backgr");
                //  preparedStatements[0].execute();

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

                    int k = 0;
                    while (resultSet.next()) {
                        try {

   //                         Log.d("AAA","post exec: " + k++);
//                            Log.i("AAA", resultSet.getString(EnumTableNV.TEN.toString()));
 //                           Log.i("AAA", resultSet.getString(EnumTableNV.MACN.toString()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

            } catch (SQLException sqlEx) {
                Log.e("AAA", "async sql exception: " + sqlEx.getMessage());
            }
        }
    }

    class asyncCall extends AsyncTask<CallableStatement, Void, ResultSetMetaData> {

        @Override
        protected ResultSetMetaData doInBackground(CallableStatement... callableStatements) {
            try {
                Log.d("AAA", "async backgr");
                //callableStatements[0].execute();
                callableStatements[0].execute();
                return callableStatements[0].executeQuery().getMetaData();
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
        protected void onPostExecute(ResultSetMetaData resultSet) {
            super.onPostExecute(resultSet);
            try {
                if (resultSet != null) {
                    Log.d("AAA", "post exec: " + resultSet.getColumnCount());

//                    while (resultSet.) {
//                        try {
//                            Log.i("AAA", rs.getString(2));
//                        } catch (SQLException e) {
//                            e.printStackTrace();
//                        }
//                    }
                }

            } catch (SQLException sqlEx) {
                Log.e("AAA", "async sql exception: " + sqlEx.getMessage());
            }
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
            action();
        }
    }
}
