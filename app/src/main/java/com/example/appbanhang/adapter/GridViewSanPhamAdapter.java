package com.example.appbanhang.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.appbanhang.R;
import com.example.appbanhang.activity.ChiTietActivity;
import com.example.appbanhang.model.SanPham;

import java.text.DecimalFormat;
import java.util.List;

public class GridViewSanPhamAdapter extends BaseAdapter {
    private Context context;
    private int idLayout;
    private List<SanPham> sanphamList;

    public GridViewSanPhamAdapter(Context context, int idLayout, List<SanPham> sanphamList) {
        this.context = context;
        this.idLayout = idLayout;
        this.sanphamList = sanphamList;
    }

    @Override
    public int getCount() {
        return sanphamList.size();
    }

    @Override
    public Object getItem(int i) {
        return sanphamList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        if (view == null){
            view = LayoutInflater.from(parent.getContext()).inflate(idLayout, parent, false);
        }

        ImageView imageView = view.findViewById(R.id.image_view);
        TextView textView = view.findViewById(R.id.txt_ten);
        TextView textView1 = view.findViewById(R.id.txt_giatien);

        final SanPham sp = sanphamList.get(i);

        if (sanphamList != null && !sanphamList.isEmpty()){
            DecimalFormat df = new DecimalFormat("###,###,###");
            String format = df.format(sp.getGiatien());
            textView.setText(sp.getTensp());
            textView1.setText("Giá tiền: "+ format +"Đ");
            Glide.with(context).load(sp.getLinkhinh()).into(imageView);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ChiTietActivity.class);
                    intent.putExtra("chitiet", sp);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
        }
        return view;
    }


}
