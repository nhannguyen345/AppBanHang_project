package com.example.appbanhang.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.example.appbanhang.R;
import com.example.appbanhang.model.GioHang;
import com.example.appbanhang.model.SanPham;
import com.example.appbanhang.retrofit.ApiBanHang;
import com.example.appbanhang.retrofit.RetrofitClient;
import com.example.appbanhang.utils.Utils;
import com.nex3z.notificationbadge.NotificationBadge;

import java.text.DecimalFormat;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ChiTietActivity extends AppCompatActivity {

    TextView tensp, giasp, mota;
    Button btnthem;
    ImageView imghinhanh;
    Spinner spinner;

    Toolbar toolbar;

    SanPham sanPham;
    NotificationBadge badge;
    ApiBanHang apiBanHang;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    int soluong_tk = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet);
        initView();
        ActionToolBar();
        initData();
        initControl();
    }

    private void initControl() {
        btnthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                themGioHang();
            }
        });
    }


    private void themGioHang() {
        int khid= Utils.kh_current.getKh_id();
        if (Utils.manggiohang.size() > 0){
            boolean flag = false;
            int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
            for (int i =0; i < Utils.manggiohang.size(); i++){
                if (Utils.manggiohang.get(i).getSp_id() == sanPham.getId()){
                    flag = true;
                    if (soluong_tk < (soluong + Utils.manggiohang.get(i).getGh_soluong())){
                        Toast.makeText(this, "Xin lỗi, hiện tại số lượng của sản phẩm này chỉ còn: "+(soluong_tk - Utils.manggiohang.get(i).getGh_soluong())+". Vui lòng chọn lại!!", Toast.LENGTH_LONG).show();
                    } else{
                        Utils.manggiohang.get(i).setGh_soluong(soluong + Utils.manggiohang.get(i).getGh_soluong());
                        long gia = Long.parseLong(String.format("%.0f",sanPham.getGiatien())) * Utils.manggiohang.get(i).getGh_soluong();
                        Utils.manggiohang.get(i).setSp_giatien(Long.parseLong(String.format("%.0f",sanPham.getGiatien())));

                        int masp = Utils.manggiohang.get(i).getSp_id();
                        int sl = soluong + Utils.manggiohang.get(i).getGh_soluong();
                        compositeDisposable.add(apiBanHang.giohang(khid, masp, sl, 2)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(
                                        khachHangModel -> {
                                            if (khachHangModel.isSuccess()){
                                                Toast.makeText(this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                                            }
                                        },
                                        throwable -> {
                                            Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                ));
                    }

                }
            }
            if ( flag == false){
                if (soluong_tk < soluong){
                    Toast.makeText(this, "Xin lỗi, hiện tại số lượng của sản phẩm này chỉ còn: "+(soluong_tk)+". Vui lòng chọn lại!!", Toast.LENGTH_LONG).show();
                } else{
                    long gia = Long.parseLong(String.format("%.0f",sanPham.getGiatien())) * soluong;
                    GioHang gioHang = new GioHang();
                    gioHang.setSp_giatien(Long.parseLong(String.format("%.0f",sanPham.getGiatien())));
                    gioHang.setGh_soluong(soluong);
                    gioHang.setSp_id(sanPham.getId());
                    gioHang.setSp_tensp(sanPham.getTensp());
                    gioHang.setSp_linkhinhanh(sanPham.getLinkhinh());
                    Utils.manggiohang.add(gioHang);

                    compositeDisposable.add(apiBanHang.giohang(khid, sanPham.getId(), gioHang.getGh_soluong(), 1)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    khachHangModel -> {
                                        if (khachHangModel.isSuccess()){
                                            Toast.makeText(this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                                        }
                                    },
                                    throwable -> {
                                        Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                            ));
                }

            }

        }else{
            int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
            if (soluong_tk < soluong){
                Toast.makeText(this, "Xin lỗi, hiện tại số lượng của sản phẩm này chỉ còn: "+(soluong_tk)+". Vui lòng chọn lại!!", Toast.LENGTH_LONG).show();
            } else{
                long gia = Long.parseLong(String.format("%.0f",sanPham.getGiatien())) * soluong;
                GioHang gioHang = new GioHang();
                gioHang.setSp_giatien(Long.parseLong(String.format("%.0f",sanPham.getGiatien())));
                gioHang.setGh_soluong(soluong);
                gioHang.setSp_id(sanPham.getId());
                gioHang.setSp_tensp(sanPham.getTensp());
                gioHang.setSp_linkhinhanh(sanPham.getLinkhinh());
                Utils.manggiohang.add(gioHang);

                compositeDisposable.add(apiBanHang.giohang(khid, sanPham.getId(), gioHang.getGh_soluong(), 1)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                khachHangModel -> {
                                    if (khachHangModel.isSuccess()){
                                        Toast.makeText(this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                                    }
                                },
                                throwable -> {
                                    Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                        ));
            }
        }
        int totalItem = 0;
        for (int i=0; i<Utils.manggiohang.size(); i++){
            totalItem = totalItem + Utils.manggiohang.get(i).getGh_soluong();
        }
        badge.setText(String.valueOf(totalItem));
    }

    private void initData() {
        //Lấy sản phẩm đã click
        sanPham = (SanPham) getIntent().getSerializableExtra("chitiet");
        KiemTrasltk(sanPham.getId());
        tensp.setText(sanPham.getTensp());
        mota.setText(sanPham.getCauhinh());
        Glide.with(getApplicationContext()).load(sanPham.getLinkhinh()).into(imghinhanh);

        //Chuyển đổi định dạng tiền
        DecimalFormat df = new DecimalFormat("###,###,###");
        String format = df.format(sanPham.getGiatien());
        giasp.setText("Giá tiền: "+ format +"Đ");
        Integer[] so = new Integer[]{1,2,3,4,5,6,7,8,9,10};

        //Tạo adapter
        ArrayAdapter<Integer> adapterspin = new ArrayAdapter<Integer>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, so){
            public View getView(int position, View convertView, ViewGroup parent) {

                View v = super.getView(position, convertView, parent);

                ((TextView) v).setTextSize(16);
                ((TextView) v).setGravity(Gravity.CENTER);
                return v;

            }

            public View getDropDownView(int position, View convertView,ViewGroup parent) {

                View v = super.getDropDownView(position, convertView,parent);

                ((TextView) v).setGravity(Gravity.CENTER);

                return v;

            }
        };
        spinner.setAdapter(adapterspin);
    }

    private void initView() {
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        tensp = findViewById(R.id.txttensp);
        giasp = findViewById(R.id.txtgiasp);
        mota = findViewById(R.id.txtmotachitiet);
        btnthem = findViewById(R.id.btnthemvaogiohang);
        spinner = findViewById(R.id.spinner);
        imghinhanh = findViewById(R.id.imgchitiet);
        toolbar = findViewById(R.id.toobar);
        badge = findViewById(R.id.menu_sl);
        FrameLayout frameLayoutgiohang = findViewById(R.id.framegiohang);
        frameLayoutgiohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent giohang = new Intent(getApplicationContext(), GioHangActivity.class);
                startActivity(giohang);
            }
        });
        if (Utils.manggiohang != null){
            int totalItem = 0;
            for (int i=0; i<Utils.manggiohang.size(); i++){
                totalItem = totalItem + Utils.manggiohang.get(i).getGh_soluong();
            }
            badge.setText(String.valueOf(totalItem));
        }
    }

    private void ActionToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void KiemTrasltk(int sp_id){
        compositeDisposable.add(apiBanHang.checksoluongtk(sp_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        loaiSpModel  -> {
                            if (loaiSpModel.isSuccess()){
                                soluong_tk =loaiSpModel.getResult().get(0).getSoluong();
                            } else{
                                Toast.makeText(this, "Không lấy được số lượng!!", Toast.LENGTH_SHORT).show();
                            }
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                ));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Utils.manggiohang != null){
            int totalItem = 0;
            for (int i=0; i<Utils.manggiohang.size(); i++){
                totalItem = totalItem + Utils.manggiohang.get(i).getGh_soluong();
            }
            badge.setText(String.valueOf(totalItem));
        }
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}