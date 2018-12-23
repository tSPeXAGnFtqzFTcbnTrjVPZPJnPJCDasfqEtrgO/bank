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
import com.example.phamngocan.ar_sql.model.NhanVien;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecycleNhanVienAdapter extends RecyclerView.Adapter<RecycleNhanVienAdapter.Holder>{

    ArrayList<NhanVien> nhanVienList;
    Context context;
    boolean isModify = false;

    public RecycleNhanVienAdapter(ArrayList<NhanVien> nhanVienList, Context context) {
        this.nhanVienList = nhanVienList;
        this.context = context;

    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_table_nhanvien,parent,false);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
            holder.editHoTen.setText(nhanVienList.get(position).getHoten());
        holder.editDiaChi.setText(nhanVienList.get(position).getDiachi());
        holder.editMaNv.setText(nhanVienList.get(position).getManv());
        holder.editPhai.setText(nhanVienList.get(position).getPhai());
        holder.editSoDT.setText(nhanVienList.get(position).getSodt());
        for(int i=0;i<Instance.chiNhanhList.size();i++){
            if(Instance.chiNhanhList.get(i).equals(nhanVienList.get(position).getMacn())){
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
        return nhanVienList.size();
    }

    class Holder extends RecyclerView.ViewHolder{
        @BindView(R.id.editHoTen)
        EditText editHoTen;
        @BindView(R.id.editDiaChi)
        EditText editDiaChi;
        @BindView(R.id.editMaNv)
        EditText editMaNv;
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
            viewList.add(editMaNv);
            viewList.add(editPhai);
            viewList.add(editSoDT);
            viewList.add(spinnerChiNhanh);

            actionSpinner();
            actionChangeEdit();

        }

        public void setEndable(boolean flag){

            this.editMaNv.setEnabled(false);
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
                    Instance.nhanvienList.get(getLayoutPosition()).setHotenT(s.toString());
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
                    Instance.nhanvienList.get(getLayoutPosition()).setSodtT(s.toString());
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
                    Instance.nhanvienList.get(getLayoutPosition()).setPhaiT(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            editMaNv.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    Instance.nhanvienList.get(getLayoutPosition()).setManvT(s.toString());
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
                    Instance.nhanvienList.get(getLayoutPosition()).setDiachiT(s.toString());
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
                    nhanVienList.get(getLayoutPosition()).setMacnT(Instance.chiNhanhList.get(position));
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }
    }
}
