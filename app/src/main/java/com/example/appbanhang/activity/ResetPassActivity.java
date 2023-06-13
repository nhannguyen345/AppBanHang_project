package com.example.appbanhang.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appbanhang.R;
import com.example.appbanhang.retrofit.ApiBanHang;
import com.example.appbanhang.retrofit.RetrofitClient;
import com.example.appbanhang.utils.Utils;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ResetPassActivity extends AppCompatActivity {
    Toolbar toolbar;
    EditText edtresetpass;
    AppCompatButton btnresetpass;
    ApiBanHang apiBanHang;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pass);
        initView();
        ActionToolBar();
        initControll();
    }

    private void initControll() {
        btnresetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edtresetpass.getText().toString();
                if (email.isEmpty()){
                    Toast.makeText(ResetPassActivity.this, "Hãy điền email của bạn để có thể đổi mật khẩu!!", Toast.LENGTH_SHORT).show();
                } else{
                    //post
                    compositeDisposable.add(apiBanHang.resetpass(email)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    khachHangModel -> {
                                        if( khachHangModel.isSuccess()){
                                            Toast.makeText(getApplicationContext(), "Đã gửi link đến mail! Hãy kiểm tra hộp thư của bạn!!",Toast.LENGTH_LONG).show();
                                            Intent intent = new Intent(getApplicationContext(), DangNhapActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }else{
                                            Toast.makeText(getApplicationContext(), "Có vẻ email cảu bạn là không đúng!!",Toast.LENGTH_SHORT).show();
                                        }
                                    },
                                    throwable -> {
                                        Toast.makeText(getApplicationContext(), throwable.getMessage(),Toast.LENGTH_SHORT).show();
                                    }
                            ));
                }
            }
        });
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
        toolbar = findViewById(R.id.toobar);
        edtresetpass = findViewById(R.id.edtresetpass);
        btnresetpass = findViewById(R.id.btnresetpass);

    }


}