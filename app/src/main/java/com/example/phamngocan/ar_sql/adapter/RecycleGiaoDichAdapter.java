package com.example.phamngocan.ar_sql.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.phamngocan.ar_sql.R;
import com.example.phamngocan.ar_sql.model.GiaoDich;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecycleGiaoDichAdapter  extends RecyclerView.Adapter<RecycleGiaoDichAdapter.Holder>{
    Context context;
    ArrayList<GiaoDich> giaoDichList;

    public RecycleGiaoDichAdapter(Context context, ArrayList<GiaoDich> giaoDichList) {
        this.context = context;
        this.giaoDichList = giaoDichList;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_table_giaodich,parent,false);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.txtvNgay.setText(""+giaoDichList.get(position).getNgay());
        holder.txtvSoDuDau.setText(""+giaoDichList.get(position).getSoduDau());
        holder.txtvSoDuSau.setText(""+giaoDichList.get(position).getSoduSau());
        holder.txtvSotien.setText(""+giaoDichList.get(position).getSotien());
        holder.txtvLoaiGD.setText(""+giaoDichList.get(position).getLoaigd());
        Log.d("AAA","bind: " + giaoDichList.get(position).getLoaigd());
    }

    @Override
    public int getItemCount() {
        return giaoDichList.size();
    }


    class Holder extends RecyclerView.ViewHolder{
        @BindView(R.id.txtvSoDuDau)
        TextView txtvSoDuDau;
        @BindView(R.id.txtvNgay)
        TextView txtvNgay;
        @BindView(R.id.txtvSotien)
        TextView txtvSotien;
        @BindView(R.id.txtvSoDuSau)
        TextView txtvSoDuSau;
        @BindView(R.id.txtvLoaiGD)
        TextView txtvLoaiGD;

        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
