package com.example.appbanhang.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appbanhang.R;
import com.example.appbanhang.adapter.CTDonHangAdapter;
import com.example.appbanhang.adapter.DonHangAdapter;
import com.example.appbanhang.model.CTDonHang;
import com.example.appbanhang.model.DonHang;
import com.example.appbanhang.retrofit.ApiBanHang;
import com.example.appbanhang.retrofit.RetrofitClient;
import com.example.appbanhang.utils.Utils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ChiTietDHActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    TextView texttendh, name, phone, ngaydh, diachi, tvtongtien;
    Toolbar toolbar;
    LinearLayoutManager linearLayoutManager;
    CTDonHangAdapter donHangAdapter;
    List<CTDonHang> ctdonHangList = new ArrayList<>();
    ApiBanHang apiBanHang;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    int vitridh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_dhactivity);
        //get mã đơn hàng
        vitridh = getIntent().getIntExtra("vitridh", 0);

        initView();
        ActionToolBar();
        initData();

    }

    private void initData() {
        texttendh.setText("CHI TIẾT ĐƠN HÀNG " + (int)(vitridh+1));
        name.setText("Tên: " + Utils.kh_current.getKh_hoten());
        phone.setText("Số điện thoại: " + Utils.kh_current.getKh_sdt());
        ngaydh.setText("Ngày đặt hàng: " + Utils.mangdonhang.get(vitridh).getDh_ngaydat());
        diachi.setText("Địa chỉ: " + Utils.mangdonhang.get(vitridh).getDh_diachi());
        DecimalFormat df = new DecimalFormat("###,###,###");
        String format = df.format(Utils.mangdonhang.get(vitridh).getDh_tongtien());
        tvtongtien.setText("Tổng tiền: "+format+"Đ");
    }

    private void initView() {
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        compositeDisposable.add(apiBanHang.getctdonhang(Utils.mangdonhang.get(vitridh).getDh_id())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        ctdonHangModel -> {
                            if( ctdonHangModel.isSuccess()){
                                //Toast.makeText(getApplicationContext(), "thành công",Toast.LENGTH_SHORT).show();
                                ctdonHangList = ctdonHangModel.getResult();
                                recyclerView = findViewById(R.id.recycler_dhsp);
                                linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
                                donHangAdapter = new CTDonHangAdapter(this, ctdonHangList);
                                recyclerView.setAdapter(donHangAdapter);
                                recyclerView.setLayoutManager(linearLayoutManager);
                            }else{
                                Toast.makeText(getApplicationContext(), ctdonHangModel.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(), throwable.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                ));
        toolbar = findViewById(R.id.toobar);
        texttendh = findViewById(R.id.texttendh);
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        ngaydh = findViewById(R.id.ngaydh);
        diachi = findViewById(R.id.diachi);
        tvtongtien = findViewById(R.id.tvtongtien);
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

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}