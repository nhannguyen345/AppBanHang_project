package com.example.appbanhang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appbanhang.Interface.TextViewClickListener;
import com.example.appbanhang.R;
import com.example.appbanhang.model.CTDonHang;
import com.example.appbanhang.model.GioHang;

import java.text.DecimalFormat;
import java.util.List;

public class ListHDAdapter extends RecyclerView.Adapter<ListHDAdapter.MyViewHolder> {

    Context context;
    List<GioHang> list;

    public ListHDAdapter(Context context, List<GioHang> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_dhsp, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        GioHang gh = list.get(position);
        Glide.with(context).load(gh.getSp_linkhinhanh()).into(holder.img_sp);
        holder.t_ten.setText(gh.getSp_tensp());
        DecimalFormat df = new DecimalFormat("###,###,###");
        String format = df.format(gh.getSp_giatien());
        holder.t_gia.setText("Giá tiền: "+format+"Đ");
        holder.t_soluong.setText("Số lượng: "+gh.getGh_soluong());
        String format1 = df.format(gh.getSp_giatien()*gh.getGh_soluong());
        holder.t_thanhtien.setText("Thành tiền: "+format1+"Đ");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView t_ten, t_gia, t_soluong, t_thanhtien;
        ImageView img_sp;
        private TextViewClickListener textViewClickListener;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            img_sp = itemView.findViewById(R.id.img_sp);
            t_ten = itemView.findViewById(R.id.t_ten);
            t_gia = itemView.findViewById(R.id.t_gia);
            t_soluong = itemView.findViewById(R.id.t_soluong);
            t_thanhtien = itemView.findViewById(R.id.t_thanhtien);
        }
    }
}
