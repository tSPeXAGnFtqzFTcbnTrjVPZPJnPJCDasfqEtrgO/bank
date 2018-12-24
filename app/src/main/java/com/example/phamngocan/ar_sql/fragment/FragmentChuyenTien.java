package com.example.phamngocan.ar_sql.fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentChuyenTien extends Fragment {

    @BindView(R.id.editTKchuyen)
    EditText editTKchuyen;
    @BindView(R.id.editTKnhan)
    EditText editTKnhan;
    @BindView(R.id.editSotien)
    EditText editSoTien;
    @BindView(R.id.editTK)
    EditText editTK;
    @BindView(R.id.editTienGuiRut)
    EditText editTienGuiRut;
    @BindView(R.id.btnOk)
    Button btnOk;

    @BindView(R.id.btnGui)
    Button btnGui;
    @BindView(R.id.btnRut)
    Button btnRut;

    @BindView(R.id.progressBasr)
    ProgressBar progressBar;
    DAOManager daoManager = ActionActivity.daoManager;
    long soducuoi = 0;

    @Override
    public void onAttachFragment(Fragment childFragment) {

        super.onAttachFragment(childFragment);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_chuyentien,container,false);
        ButterKnife.bind(this,view);
        progressBar.setVisibility(View.GONE);
        action();
        return view;
    }

    private void action(){
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tkChuyen,tkNhan,sotienString;


                tkChuyen = editTKchuyen.getText().toString();
                tkNhan = editTKnhan.getText().toString();
                sotienString = editSoTien.getText().toString().trim();
                if(!tkChuyen.equals("") && !tkNhan.equals("") && !sotienString.equals("")){
                    progressBar.setVisibility(View.VISIBLE);
                    new asyncChuyenTien(tkChuyen,tkNhan,sotienString).execute();
                }else{
                    Toast.makeText(getContext(),"Chưa đủ thông tin",Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnGui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String tk,tien;
                tk = editTK.getText().toString().trim();
                tien = editTienGuiRut.getText().toString().trim();
                if(tk.equals("")||tien.equals("")){
                    Toast.makeText(getContext(),"Chưa đủ thông tin",Toast.LENGTH_SHORT).show();
                }else{
                    new asyncGuiRut(tk,tien,"GT").execute();
                }
            }
        });
        btnRut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tk,tien;
                tk = editTK.getText().toString().trim();
                tien = editTienGuiRut.getText().toString().trim();
                if(tk.equals("")||tien.equals("")){
                    Toast.makeText(getContext(),"Chưa đủ thông tin",Toast.LENGTH_SHORT).show();
                }else{
                    progressBar.setVisibility(View.VISIBLE);
                    //new asyncGuiRut(tk,tien,"RT").execute();
                    new asyncGetSoDu(tk,Long.parseLong(tien)).execute();
                }
            }
        });

    }


    class asyncGuiRut extends AsyncTask<Void,Void,Boolean>{
        String tk,loaigd,sotienString;
        long sotien;
        public asyncGuiRut(String tk, String sotienString,String loaigd) {
            this.tk = tk;
            this.sotienString = sotienString;
            this.loaigd = loaigd;

            while(this.tk.length()<9){
                //Log.d("AAA",this.tkNhan.length()+" nhan");
                this.tk = this.tk.concat(" ");
            }
            this.sotienString = this.sotienString.trim();
            sotien = Long.parseLong(this.sotienString);
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                Log.d("AAA","before exec");
                CallableStatement callableStatement = daoManager.conn.prepareCall(
                        "call [dbo].[ADD_GD_GOIRUT](?,?,?,?)");
                callableStatement.setString(1,tk);
                callableStatement.setString(2,Instance.userName);
                callableStatement.setLong(3,sotien);
                callableStatement.setString(4,loaigd);

                Log.d("AAA","async ruttien: " + tk+"_"+Instance.userName+"_"+sotien+"_"+loaigd);
                callableStatement.execute();

                Log.d("AAA","before exec 2");
            } catch (SQLException e) {
                Log.d("AAA","error GOIRUT: " + e.getMessage());
               // Toast.makeText(getContext(),"Error: " + e.getMessage(),Toast.LENGTH_SHORT).show();
                e.printStackTrace();
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            progressBar.setVisibility(View.GONE);
            Log.d("AAA","reponse chuyentien: " + aBoolean);
            if(aBoolean){
                Toast.makeText(getActivity().getApplicationContext(),
                        "Thành công",
                        Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getActivity().getApplicationContext(),
                        "Thất bại",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
    class asyncChuyenTien extends AsyncTask<Void,Void,Boolean>{
        String tkChuyen,tkNhan,sotienString;
        long sotien;
        public asyncChuyenTien(String tkChuyen, String tkNhan, String sotienString) {
            this.tkChuyen = tkChuyen;
            this.tkNhan = tkNhan;
            this.sotienString = sotienString;
            while (this.tkChuyen.length()<9){
                //Log.d("AAA",this.tkChuyen.length()+" chuyen");
                this.tkChuyen = this.tkChuyen.concat(" ");
            }
            while(this.tkNhan.length()<9){
                //Log.d("AAA",this.tkNhan.length()+" nhan");
                this.tkNhan = this.tkNhan.concat(" ");
            }
            this.sotienString = this.sotienString.trim();
            sotien = Long.parseLong(this.sotienString);
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                Log.d("AAA","before exec");
                CallableStatement callableStatement = daoManager.conn.prepareCall("call [dbo].CHUYENTIEN(?,?,?,?)");
                callableStatement.setString(1,tkChuyen);
                callableStatement.setString(2,tkNhan);
                callableStatement.setLong(3,sotien);
                callableStatement.setString(4,Instance.userName);

                Log.d("AAA","before exec 1_"+tkChuyen+"_"+tkNhan+"_");
                callableStatement.execute();
                Log.d("AAA","before exec 2");
            } catch (SQLException e) {
                Log.d("AAA","error chuyen tien: " + e.getMessage());
                e.printStackTrace();
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            progressBar.setVisibility(View.GONE);
            Log.d("AAA","reponse guiruttien: " + aBoolean);
            if(aBoolean){
                Toast.makeText(getActivity().getApplicationContext(),
                        "Thành công",
                        Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getActivity().getApplicationContext(),
                        "Thất bại",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
    class asyncGetSoDu extends AsyncTask<Void,Void,ResultSet>{

        String sotk;
        long sotien;

        public asyncGetSoDu(String sotk,long sotien) {
            this.sotk = sotk;
            this.sotien=sotien;
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
                        Log.d("AAA","ruttien: " +soducuoi+"_"+sotien );
                        if(sotien>soducuoi){
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(getContext(),"Thất bại",Toast.LENGTH_SHORT).show();
                        }else{
                            new asyncGuiRut(sotk,String.valueOf(sotien),"RT").execute();
                        }
                    }

                }catch (SQLException e){
                    e.printStackTrace();

                    Log.d("AAA","error query get soducuoi: " + e.getMessage());
                }
            }
        }

    }
}


