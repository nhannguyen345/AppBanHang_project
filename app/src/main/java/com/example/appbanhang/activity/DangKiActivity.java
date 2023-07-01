package com.example.appbanhang.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appbanhang.R;
import com.example.appbanhang.retrofit.ApiBanHang;
import com.example.appbanhang.retrofit.RetrofitClient;
import com.example.appbanhang.utils.Utils;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import okhttp3.internal.Util;

public class DangKiActivity extends AppCompatActivity {

    EditText name, phone, email, username, pass, repass;
    AppCompatButton button;

    ApiBanHang apiBanHang;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ki);
        initView();
        initControll();
    }

    private void initControll() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dangKi();
            }

        });
    }

    private void dangKi() {
        String str_name = name.getText().toString().trim();
        String str_phone = phone.getText().toString().trim();
        String str_email = email.getText().toString().trim();
        String str_username = username.getText().toString().trim();
        String str_pass = pass.getText().toString().trim();
        String str_repass = repass.getText().toString().trim();

        if(TextUtils.isEmpty(str_name)){
            Toast.makeText(getApplicationContext(), "Bạn chưa nhập Tên", Toast.LENGTH_SHORT).show();
        } else if(TextUtils.isEmpty(str_phone)){
            Toast.makeText(getApplicationContext(), "Bạn chưa nhập số điện thoại", Toast.LENGTH_SHORT).show();
        } else if(TextUtils.isEmpty(str_email)){
            Toast.makeText(getApplicationContext(), "Bạn chưa nhập Email", Toast.LENGTH_SHORT).show();
        } else if(TextUtils.isEmpty(str_username)){
            Toast.makeText(getApplicationContext(), "Bạn chưa nhập Username", Toast.LENGTH_SHORT).show();
        } else if(TextUtils.isEmpty(str_pass)){
            Toast.makeText(getApplicationContext(), "Bạn chưa nhập Pass", Toast.LENGTH_SHORT).show();
        } else if(TextUtils.isEmpty(str_repass)){
            Toast.makeText(getApplicationContext(), "Bạn chưa nhập Repass", Toast.LENGTH_SHORT).show();
        } else {
            if (str_pass.equals(str_repass)){
                //post data
                compositeDisposable.add(apiBanHang.dangki(str_username,str_pass,str_name,str_phone,str_email)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                khachHangModel -> {
                                    if( khachHangModel.isSuccess()){
                                        //Toast.makeText(getApplicationContext(), "thành công",Toast.LENGTH_SHORT).show();
                                        Utils.kh_current.setUsername(str_username);
                                        Utils.kh_current.setPassword(str_pass);
                                        Utils.kh_current.setKh_hoten(str_name);
                                        Utils.kh_current.setKh_sdt(str_phone);
                                        Utils.kh_current.setKh_email(str_email);

                                        Intent intent = new Intent(getApplicationContext(), DangNhapActivity.class);
                                        Toast.makeText(getApplicationContext(), "Đăng kí thành công", Toast.LENGTH_LONG).show();
                                        startActivity(intent);
                                        finish();
                                    }else{
                                        Toast.makeText(getApplicationContext(), khachHangModel.getMessage(),Toast.LENGTH_SHORT).show();
                                    }
                                },
                                throwable -> {
                                    Toast.makeText(getApplicationContext(), throwable.getMessage(),Toast.LENGTH_SHORT).show();
                                }
                        ));
            }else{
                Toast.makeText(getApplicationContext(), "Pass chưa khớp", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void initView() {
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);

        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        email = findViewById(R.id.email);
        username = findViewById(R.id.username);
        pass = findViewById(R.id.pass);
        repass = findViewById(R.id.repass);
        button = findViewById(R.id.btndangki);
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}