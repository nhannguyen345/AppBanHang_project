package com.example.appbanhang.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbanhang.Interface.TextViewClickListener;
import com.example.appbanhang.R;
import com.example.appbanhang.activity.ChiTietActivity;
import com.example.appbanhang.activity.ChiTietDHActivity;
import com.example.appbanhang.model.DonHang;

import java.text.DecimalFormat;
import java.util.List;

public class DonHangAdapter extends RecyclerView.Adapter<DonHangAdapter.MyViewHolder>{

    Context context;
    List<DonHang> list;

    public DonHangAdapter(Context context, List<DonHang> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyc_dh, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DonHangAdapter.MyViewHolder holder, int position) {
        DonHang dh = list.get(position);
        String tendh = "Đơn hàng " + (int)(position+1);
        String ngaydh = "Thời gian đặt hàng: " + dh.getDh_ngaydat();
        String trangthai = "Trạng thái: " + dh.getDh_trangthai();
        DecimalFormat df = new DecimalFormat("###,###,###");
        String format = df.format(dh.getDh_tongtien());
        String ttien = String.valueOf(format);
        String tongtien = "Tổng tiền: " + ttien + "Đ";

        holder.texttendh.setText(tendh);
        holder.texttrangthai.setText(trangthai);
        holder.textthoigian.setText(ngaydh);
        holder.texttongtien.setText(tongtien);

        holder.setListener(new TextViewClickListener() {
            @Override
            public void onTextViewClick(View view, int pos) {
                Intent intent = new Intent(context, ChiTietDHActivity.class);
                intent.putExtra("vitridh", pos);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView texttendh, texttrangthai, textthoigian, texttongtien, textxemct;

        TextViewClickListener listener;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            texttendh = itemView.findViewById(R.id.texttendh);
            texttrangthai = itemView.findViewById(R.id.texttrangthai);
            textthoigian = itemView.findViewById(R.id.textthoigian);
            texttongtien = itemView.findViewById(R.id.texttongtien);
            textxemct = itemView.findViewById(R.id.textxemct);

            //set event
            textxemct.setOnClickListener(this);
        }

        public void setListener(TextViewClickListener listener) {this.listener = listener;};

        @Override
        public void onClick(View view) {
            listener.onTextViewClick(view, getAdapterPosition());
        }
    }
}
