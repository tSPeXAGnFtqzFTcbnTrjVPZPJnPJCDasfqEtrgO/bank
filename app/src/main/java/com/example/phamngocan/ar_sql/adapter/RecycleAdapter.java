package com.example.phamngocan.ar_sql.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
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

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.Holder>{

    ArrayList<NhanVien> nhanVienList;
    Context context;
    int positionModify = -1;

    public RecycleAdapter(ArrayList<NhanVien> nhanVienList, Context context) {
        this.nhanVienList = nhanVienList;
        this.context = context;

    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_table,parent,false);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
            holder.editHoTen.setText(nhanVienList.get(position).getHoten());
        holder.editDiaChi.setText(nhanVienList.get(position).getDiachi());
        holder.editMaNv.setText(nhanVienList.get(position).getMacn());
        holder.editPhai.setText(nhanVienList.get(position).getPhai());
        holder.editSoDT.setText(nhanVienList.get(position).getSodt());


        holder.setEndable(position==positionModify);


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

        }

        public void setEndable(boolean flag){
            this.editHoTen.setEnabled(flag);
            this.editDiaChi.setEnabled(flag);
            this.editMaNv.setEnabled(flag);
            this.editPhai.setEnabled(flag);
            this.editSoDT.setEnabled(flag);
            this.spinnerChiNhanh.setEnabled(flag);
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
