package com.example.appbanhang.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appbanhang.Interface.ImageClickListener;
import com.example.appbanhang.R;
import com.example.appbanhang.activity.GioHangActivity;
import com.example.appbanhang.activity.MainActivity;
import com.example.appbanhang.model.EventBus.TinhTongEvent;
import com.example.appbanhang.model.GioHang;
import com.example.appbanhang.retrofit.ApiBanHang;
import com.example.appbanhang.retrofit.RetrofitClient;
import com.example.appbanhang.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class GioHangAdapter extends RecyclerView.Adapter<GioHangAdapter.MyViewHolder> {

    Context context;
    List<GioHang> gioHangList;

    ApiBanHang apiBanHang;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    public GioHangAdapter(Context context, List<GioHang> gioHangList) {
        this.context = context;
        this.gioHangList = gioHangList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_giohang, parent, false);
        return new MyViewHolder(view);
    }

    private void UpdateDatabase(int khid, int masp, int sl, int flag) {
        if (flag == 2) {
            compositeDisposable.add(apiBanHang.giohang(khid, masp, sl, 2)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            khachHangModel -> {
                                if (khachHangModel.isSuccess()) {
                                    Toast.makeText(context, "Chỉnh thành công", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, khachHangModel.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            },
                            throwable -> {
                                Toast.makeText(context, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                    ));
        } else {
            compositeDisposable.add(apiBanHang.giohang(khid, masp, sl, 3)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            khachHangModel -> {
                                if (khachHangModel.isSuccess()) {
                                    Toast.makeText(context, "Đã xóa", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, khachHangModel.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            },
                            throwable -> {
                                Toast.makeText(context, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                    ));
        }
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        int idkh = Utils.kh_current.getKh_id();
        GioHang gioHang = gioHangList.get(position);
        holder.item_giohang_tensp.setText(gioHang.getSp_tensp());
        holder.item_giohang_soluong.setText(String.valueOf(gioHang.getGh_soluong()));
        Glide.with(context).load(gioHang.getSp_linkhinhanh()).into(holder.item_giohang_image);
        long giasp = gioHang.getSp_giatien() * gioHang.getGh_soluong();
        DecimalFormat df = new DecimalFormat("###,###,###");
        String format = df.format(gioHang.getSp_giatien());
        holder.item_giohang_gia.setText(format);
        String format1 = df.format(giasp);
        holder.item_giohang_giasp2.setText(format1);

        holder.setListener(new ImageClickListener() {
            @Override
            public void onImageClick(View view, int pos, int giatri) {
                //Lay ma san pham
                int msp = gioHangList.get(pos).getSp_id();
                if (giatri == 1){

                    //Kiem tra so long
                    if(gioHangList.get(pos).getGh_soluong() > 1){
                        int soluongmoi = gioHangList.get(pos).getGh_soluong()-1;
                        gioHangList.get(pos).setGh_soluong(soluongmoi);

                        holder.item_giohang_soluong.setText(String.valueOf(gioHangList.get(pos).getGh_soluong()));
                        long gia = gioHangList.get(pos).getGh_soluong() * gioHangList.get(pos).getSp_giatien();
                        String format2 = df.format(gia);
                        holder.item_giohang_giasp2.setText(format2);
                        EventBus.getDefault().postSticky(new TinhTongEvent());
                        UpdateDatabase(idkh, gioHangList.get(pos).getSp_id(), soluongmoi, 2);
                    }else if (gioHangList.get(pos).getGh_soluong() == 1){
                        AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
                        builder.setTitle("Thông báo");
                        builder.setMessage("Bạn có muốn xóa sản phẩm khỏi giỏ hàng");
                        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Utils.manggiohang.remove(pos);
                                notifyDataSetChanged();
                                EventBus.getDefault().postSticky(new TinhTongEvent());
                                //Cap nhat vao co so du lieu
                                UpdateDatabase(idkh, msp, 0, 3);
                            }
                        });
                        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        builder.show();

                    }
                }else if (giatri == 2){
                    if(gioHangList.get(pos).getGh_soluong() < 11){
                        int soluongmoi = gioHangList.get(pos).getGh_soluong()+1;
                        gioHangList.get(pos).setGh_soluong(soluongmoi);
                        UpdateDatabase(idkh, gioHangList.get(pos).getSp_id(), soluongmoi, 2);
                    }
                }else if (giatri == 3){
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
                    builder.setTitle("Thông báo");
                    builder.setMessage("Bạn có muốn xóa sản phẩm khỏi giỏ hàng");
                    builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Utils.manggiohang.remove(pos);
                            notifyDataSetChanged();
                            EventBus.getDefault().postSticky(new TinhTongEvent());
                            UpdateDatabase(idkh, msp, 0, 3);
                        }
                    });
                    builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    builder.show();
                }
                if (gioHangList.size() >= 1){
                    holder.item_giohang_soluong.setText(String.valueOf(gioHangList.get(pos).getGh_soluong()));
                    long gia = gioHangList.get(pos).getGh_soluong() * gioHangList.get(pos).getSp_giatien();
                    String format2 = df.format(gia);
                    holder.item_giohang_giasp2.setText(format2);
                    EventBus.getDefault().postSticky(new TinhTongEvent());
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return gioHangList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView item_giohang_image, imgtru, imgcong, img_xoa;
        TextView item_giohang_tensp, item_giohang_gia, item_giohang_soluong, item_giohang_giasp2;
        ImageClickListener listener;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
            item_giohang_image = itemView.findViewById(R.id.item_giohang_image);
            item_giohang_tensp = itemView.findViewById(R.id.item_giohang_tensp);
            item_giohang_gia = itemView.findViewById(R.id.item_giohang_gia);
            item_giohang_soluong = itemView.findViewById(R.id.item_giohang_soluong);
            item_giohang_giasp2 = itemView.findViewById(R.id.item_giohang_giasp2);
            imgtru = itemView.findViewById(R.id.item_giohang_tru);
            imgcong = itemView.findViewById(R.id.item_giohang_cong);
            img_xoa = itemView.findViewById(R.id.img_xoa);

            //event click
            imgcong.setOnClickListener(this);
            imgtru.setOnClickListener(this);
            img_xoa.setOnClickListener(this);
        }

        public void setListener(ImageClickListener listener) {
            this.listener = listener;
        }

        @Override
        public void onClick(View view) {
            if (view == imgtru){
                // 1 tru
                listener.onImageClick(view, getAdapterPosition(), 1);
            } else if(view == imgcong){
                // 2 cong
                listener.onImageClick(view, getAdapterPosition(), 2);
            } else if(view == img_xoa){
                // 3 xoa
                listener.onImageClick(view, getAdapterPosition(), 3);
            }
        }
    }
}
