package com.example.phamngocan.ar_sql.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.phamngocan.ar_sql.Instance;
import com.example.phamngocan.ar_sql.R;
import com.example.phamngocan.ar_sql.model.TaiKhoan;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecycleTaiKhoanAdapter extends RecyclerView.Adapter<RecycleTaiKhoanAdapter.Holder>{
    Context context;
    ArrayList<TaiKhoan>taiKhoanList;

    public RecycleTaiKhoanAdapter(Context context, ArrayList<TaiKhoan> taiKhoanList) {
        this.context = context;
        this.taiKhoanList = taiKhoanList;

    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_taikhoan,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.txtvCMND.setText(this.taiKhoanList.get(position).getCmnd());
        holder.txtvMaCN.setText(this.taiKhoanList.get(position).getMacn());
        holder.txtvSoDu.setText(this.taiKhoanList.get(position).getSoTien());
        holder.txtvSoTk.setText(this.taiKhoanList.get(position).getSotk());

    }

    @Override
    public int getItemCount() {
        return taiKhoanList.size();
    }


    public void changeChiNhanh(String cn){
        if(cn.trim().equals("BENTHANH")){
            taiKhoanList = Instance.taiKhoanBenThanhList;
        }else if(cn.trim().equals("TANDINH")){
            taiKhoanList = Instance.taiKhoanTanDinhList;
        }else{
            taiKhoanList = Instance.taiKhoanList;
        }
        Log.d("AAA","change cn");
        notifyDataSetChanged();
    }

    class Holder extends RecyclerView.ViewHolder{

        @BindView(R.id.txtvSoTk)
        TextView txtvSoTk;
        @BindView(R.id.txtvCMND)
        TextView txtvCMND;
        @BindView(R.id.txtvSoDu)
        TextView txtvSoDu;
        @BindView(R.id.txtvMaCN)
        TextView txtvMaCN;

        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
