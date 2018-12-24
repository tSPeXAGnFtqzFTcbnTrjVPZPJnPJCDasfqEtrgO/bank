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
    @BindView(R.id.btnOk)
    Button btnOk;
    @BindView(R.id.progressBasr)
    ProgressBar progressBar;
    DAOManager daoManager = ActionActivity.daoManager;

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
    }

    class asyncChuyenTien extends AsyncTask<Void,Void,Boolean>{
        String tkChuyen,tkNhan,sotienString;
        int sotien;
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
            sotien = Integer.parseInt(this.sotienString);
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                Log.d("AAA","before exec");
                CallableStatement callableStatement = daoManager.conn.prepareCall("call [dbo].CHUYENTIEN(?,?,?,?)");
                callableStatement.setString(1,tkChuyen);
                callableStatement.setString(2,tkNhan);
                callableStatement.setInt(3,sotien);
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
            Log.d("AAA","reponse chuyentien: " + aBoolean);
            if(aBoolean){
                Toast.makeText(getActivity().getApplicationContext(),
                        "Chuyển tiền thành công",
                        Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getActivity().getApplicationContext(),
                        "Chuyển tiền thất bại",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
}


