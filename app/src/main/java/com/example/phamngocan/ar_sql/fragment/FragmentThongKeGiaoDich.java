package com.example.phamngocan.ar_sql.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
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
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.phamngocan.ar_sql.ActionActivity;
import com.example.phamngocan.ar_sql.DAOManager;
import com.example.phamngocan.ar_sql.Instance;
import com.example.phamngocan.ar_sql.R;
import com.example.phamngocan.ar_sql.adapter.RecycleGiaoDichAdapter;
import com.example.phamngocan.ar_sql.model.GiaoDich;

import java.io.File;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class FragmentThongKeGiaoDich extends Fragment {
    @BindView(R.id.progressBasr)
    ProgressBar progressBar;
    @BindView(R.id.editTK)
    EditText editTK;
    @BindView(R.id.btnOk)
    Button btnOk;
    @BindView(R.id.recycler)
    RecyclerView recyclerView;

    DAOManager daoManager = ActionActivity.daoManager;
    RecycleGiaoDichAdapter adapter;
    long soducuoi = 0;
    String filename = "nv.xls";
    File directory;
    WritableSheet sheet;
    WritableWorkbook workbook;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_thongke_giaodich,container,false);
        ButterKnife.bind(this,view);
        init();
        action();
       // initFile();
        return view;
    }

    private void init(){
        adapter = new RecycleGiaoDichAdapter(getContext(),Instance.giaoDichList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

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
            initFile();
            sheet.mergeCells(0, 0, 4, 0);
            sheet.addCell(new Label(0, 0, "Thống kê giao dịch"));


            sheet.addCell(new Label(0, 1, "Số dư đầu"));
            sheet.addCell(new Label(1, 1, "Ngày"));
            sheet.addCell(new Label(2, 1, "Loại GD"));
            sheet.addCell(new Label(3, 1, "Số tiền"));
            sheet.addCell(new Label(4, 1, "Số dư sau"));
            int row = 2;

                for (int i = 0; i < Instance.giaoDichList.size(); i++) {
//                    sheet.addCell(new Label(i, row, rs.getString(Instance.columnNV.get(i))));
                    sheet.addCell(new Label(0, row, ""+Instance.giaoDichList.get(i).getSoduDau()));
                    sheet.addCell(new Label(1, row, ""+Instance.giaoDichList.get(i).getNgay()));
                    sheet.addCell(new Label(2, row, ""+Instance.giaoDichList.get(i).getLoaigd()));
                    sheet.addCell(new Label(3, row, ""+Instance.giaoDichList.get(i).getSotien()));
                    sheet.addCell(new Label(4, row, ""+Instance.giaoDichList.get(i).getSoduDau()));
                    row++;
                }

            workbook.write();
            workbook.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void action(){
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String sotk;
                sotk = editTK.getText().toString().trim();
                if(sotk.equals("")){
                    Toast.makeText(getContext(),"Chưa nhập tài khoản",Toast.LENGTH_SHORT).show();
                    return;
                }
                new asyncGetSoDu(sotk).execute();
            }
        });
    }

    class asyncGetSoDu extends AsyncTask<Void,Void,ResultSet>{

        String sotk;

        public asyncGetSoDu(String sotk) {
            this.sotk = sotk;
            modify();
        }

        private void modify(){
            while (this.sotk.length()<9){
                this.sotk = this.sotk.concat(" ");
            }
        }

        @Override
        protected ResultSet doInBackground(Void... voids) {
            ResultSet rs = null;
            try {
                rs = daoManager.stmt.executeQuery(
                        "SELECT * FROM [dbo].TaiKhoan WHERE SOTK = "+sotk+" "
                );

            } catch (SQLException e) {
                e.printStackTrace();
                Log.d("AAA","error query goirut: " + e.getMessage());
            }
            return rs;
        }

        @Override
        protected void onPostExecute(ResultSet resultSet) {
            if(resultSet!=null){
                try{
                    if(resultSet.next()){
                        soducuoi = resultSet.getLong(3);
                    }
                    new asyncGiaoDichGoiRut(this.sotk).execute();
                }catch (SQLException e){
                    e.printStackTrace();
                    Log.d("AAA","error query get soducuoi: " + e.getMessage());
                }
            }
        }

    }
    class asyncGiaoDichGoiRut extends AsyncTask<String,Void,ResultSet>{
        String sotk;

        public asyncGiaoDichGoiRut(String sotk) {
            this.sotk = sotk;
            modify();
        }
        private void modify(){
            while (this.sotk.length()<9){
                this.sotk = this.sotk.concat(" ");
            }
        }

        @Override
        protected ResultSet doInBackground(String... strings) {
            ResultSet rs = null;
            try {
                 rs = daoManager.stmt.executeQuery(
                         "SELECT * FROM [dbo].GD_GOIRUT WHERE SOTK = "+sotk+" "
                );

            } catch (SQLException e) {
                e.printStackTrace();
                Log.d("AAA","error query goirut: " + e.getMessage());
            }
            return rs;
        }

        @Override
        protected void onPostExecute(ResultSet resultSet) {
            Instance.giaoDichList.clear();
            if(resultSet!=null){
                try {
                    while (resultSet.next()) {
                        String ngay,loaigd;
                        int sotien;
                        ngay = resultSet.getString(4);
                        loaigd = resultSet.getString(3);
                        sotien = resultSet.getInt(5);
                        Instance.giaoDichList.add(new GiaoDich(ngay,loaigd,sotien));

                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
                        dateFormat.equals(ngay);
                    }
                    new asyncGiaoDichChuyentien(this.sotk).execute();
                }catch (SQLException e){

                }
            }
        }
    }

    class asyncGiaoDichChuyentien extends AsyncTask<Void,Void,ResultSet>{
        String sotk;

        public asyncGiaoDichChuyentien(String sotk) {
            this.sotk = sotk;
            modify();
        }

        private void modify(){
            while (this.sotk.length()<9){
                this.sotk = this.sotk.concat(" ");
            }
        }

        @Override
        protected ResultSet doInBackground(Void... strings) {
            ResultSet rs = null;
            try {
                rs = daoManager.stmt.executeQuery(
                        "SELECT * FROM [dbo].GD_CHUYENTIEN WHERE SOTK_CHUYEN = "+sotk+" " +
                                "or SOTK_NHAN = "+sotk+" "
                );

            } catch (SQLException e) {
                e.printStackTrace();
                Log.d("AAA","error query chuyentien: " + e.getMessage());
            }
            return rs;
        }

        @Override
        protected void onPostExecute(ResultSet resultSet) {
            if(resultSet!=null){
                try {
                    while (resultSet.next()){
                        String ngay,loaigd,tknhan;
                        int sotien;
                        ngay = resultSet.getString(3);
                        tknhan = resultSet.getString(5);
                        sotien = resultSet.getInt(4);
                        if(tknhan.equals(this.sotk)){
                            loaigd = "NT";
                        }else{
                            loaigd = "CT";
                        }
                        Instance.giaoDichList.add(new GiaoDich(ngay,loaigd,sotien));
                        Log.d("AAA","chuyentien: " + loaigd);
                    }

                    Collections.sort(Instance.giaoDichList, new Comparator<GiaoDich>() {
                        @Override
                        public int compare(GiaoDich o1, GiaoDich o2) {
                            return o1.getNgay().compareTo(o2.getNgay());
                        }
                    });
                    for(int i=Instance.giaoDichList.size()-1;i>=0;i--){
                        if(Instance.giaoDichList.get(i).getLoaigd().equals("GT")||
                                Instance.giaoDichList.get(i).getLoaigd().equals("NT")){
                            Instance.giaoDichList.get(i).setSoduSau(soducuoi);
                            soducuoi-=Instance.giaoDichList.get(i).getSotien();
                            Instance.giaoDichList.get(i).setSoduDau(soducuoi);
                        }else{
                            Instance.giaoDichList.get(i).setSoduSau(soducuoi);
                            soducuoi+=Instance.giaoDichList.get(i).getSotien();
                            Instance.giaoDichList.get(i).setSoduDau(soducuoi);
                        }
                    }

                    adapter.notifyDataSetChanged();
                    writeFile();
                    Log.d("AAA","notify: " + adapter.getItemCount());
                    progressBar.setVisibility(View.GONE);
                }catch (SQLException e){

                }
            }
        }
    }
}
