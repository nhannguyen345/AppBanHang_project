package com.example.appbanhang.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.appbanhang.R;
import com.example.appbanhang.adapter.DonHangAdapter;
import com.example.appbanhang.model.DonHang;
import com.example.appbanhang.retrofit.ApiBanHang;
import com.example.appbanhang.retrofit.RetrofitClient;
import com.example.appbanhang.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class QLDonHangActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Toolbar toolbar;
    LinearLayoutManager linearLayoutManager;
    DonHangAdapter donHangAdapter;
    List<DonHang> donHangList = new ArrayList<>();
    ApiBanHang apiBanHang;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qldon_hang);
        initView();
        ActionToolBar();


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

    private void initView() {
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);

        //post and get data
        compositeDisposable.add(apiBanHang.getdonhang(Utils.kh_current.getKh_id())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        donHangModel -> {
                            if( donHangModel.isSuccess()){
                                //Toast.makeText(getApplicationContext(), "thành công",Toast.LENGTH_SHORT).show();
                                donHangList = donHangModel.getResult();
                                Utils.mangdonhang = donHangList;
                                Log.e("danh sach", String.valueOf(donHangList.get(0).getKh_id()));
                                recyclerView = findViewById(R.id.recycleview_dh);
                                linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
                                donHangAdapter = new DonHangAdapter(this, donHangList);
                                recyclerView.setAdapter(donHangAdapter);
                                recyclerView.setLayoutManager(linearLayoutManager);
                            }else{
                                Toast.makeText(getApplicationContext(), "Không có đơn hàng nào!!!",Toast.LENGTH_SHORT).show();
                            }
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(), throwable.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                ));

        toolbar = findViewById(R.id.toobar);

    }
    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}