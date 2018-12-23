package com.example.phamngocan.ar_sql.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.phamngocan.ar_sql.Instance;
import com.example.phamngocan.ar_sql.R;
import com.example.phamngocan.ar_sql.model.KhachHang;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecycleKhachHangAdapter extends RecyclerView.Adapter<RecycleKhachHangAdapter.Holder>  {

    ArrayList<KhachHang> khachHangList;
    Context context;
    boolean isModify = false;

    public RecycleKhachHangAdapter(ArrayList<KhachHang> khachHangList, Context context) {
        this.khachHangList = khachHangList;
        this.context = context;

    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_table_khachhang,parent,false);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.editHoTen.setText(khachHangList.get(position).getHoten());
        holder.editDiaChi.setText(khachHangList.get(position).getDiachi());
        holder.editCMND.setText(khachHangList.get(position).getCmnd());
        holder.editPhai.setText(khachHangList.get(position).getPhai());
        holder.editSoDT.setText(khachHangList.get(position).getSodt());
        for(int i=0;i<Instance.chiNhanhList.size();i++){
            if(Instance.chiNhanhList.get(i).equals(khachHangList.get(position).getMacn())){
                holder.spinnerChiNhanh.setSelection(i);
                break;
            }
        }


        holder.setEndable(isModify);

    }

    public void changeStateUpdate(boolean flag){
        this.isModify = flag;
        notifyDataSetChanged();
    }
    public boolean getModify(){
        return this.isModify;
    }
    @Override
    public int getItemCount() {
        return khachHangList.size();
    }

    class Holder extends RecyclerView.ViewHolder{
        @BindView(R.id.editHoTen)
        EditText editHoTen;
        @BindView(R.id.editDiaChi)
        EditText editDiaChi;
        @BindView(R.id.editCMND)
        EditText editCMND;
        @BindView(R.id.editPhai)
        EditText editPhai;
        @BindView(R.id.editSoDT)
        EditText editSoDT;
        @BindView(R.id.spinner)
        Spinner spinnerChiNhanh;
        ArrayAdapter<String> adapterChiNhanh;

        ArrayList<View> viewList = new ArrayList<>();

        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            viewList.add(editDiaChi);
            viewList.add(editHoTen);
            viewList.add(editCMND);
            viewList.add(editPhai);
            viewList.add(editSoDT);
            viewList.add(spinnerChiNhanh);

            actionSpinner();
            actionChangeEdit();

        }

        public void setEndable(boolean flag){

            this.editCMND.setEnabled(false);
            this.spinnerChiNhanh.setEnabled(false);

            this.editHoTen.setEnabled(flag);
            this.editDiaChi.setEnabled(flag);
            this.editPhai.setEnabled(flag);
            this.editSoDT.setEnabled(flag);

        }
        private void actionChangeEdit(){
            editHoTen.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    Instance.khachHangList.get(getLayoutPosition()).setHotenT(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            editSoDT.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    Instance.khachHangList.get(getLayoutPosition()).setSodtT(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            editPhai.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    Instance.khachHangList.get(getLayoutPosition()).setPhaiT(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            editCMND.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    Instance.khachHangList.get(getLayoutPosition()).setCmndT(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            editDiaChi.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    Instance.khachHangList.get(getLayoutPosition()).setDiachiT(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

        }
        private void actionSpinner(){
            adapterChiNhanh = new ArrayAdapter<>(context, R.layout.item_spinner, Instance.chiNhanhList);
            adapterChiNhanh.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerChiNhanh.setAdapter(adapterChiNhanh);


            spinnerChiNhanh.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    khachHangList.get(getLayoutPosition()).setMacnT(Instance.chiNhanhList.get(position));
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }
    }

}
