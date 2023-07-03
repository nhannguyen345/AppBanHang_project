package com.example.appbanhang.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appbanhang.R;
import com.example.appbanhang.model.EventBus.TinhTongEvent;
import com.example.appbanhang.retrofit.ApiBanHang;
import com.example.appbanhang.retrofit.RetrofitClient;
import com.example.appbanhang.utils.Utils;
import com.google.android.material.textfield.TextInputEditText;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.internal.Util;

public class UserInfoActivity extends AppCompatActivity {
    Toolbar toolbar;
    EditText text_name, text_email, text_phone, text_usernm, text_pass;
    CheckBox ck_hide_show;
    Button btnsavechange;
    ApiBanHang apiBanHang;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        initView();
        initControll();
    }

    private void initControll() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ck_hide_show.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b == true){
                    text_pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else{
                    text_pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        btnsavechange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
                builder.setTitle("Thông báo");
                builder.setMessage("Bạn có muốn thay đổi thông tin này không?");
                builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        UpdateUser();;
                    }
                });
                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        text_name.setText(Utils.kh_current.getUsername().toString());
                        text_email.setText(Utils.kh_current.getKh_email().toString());
                        text_phone.setText(Utils.kh_current.getKh_sdt().toString());
                        text_usernm.setText(Utils.kh_current.getUsername().toString());
                        text_pass.setText(Utils.kh_current.getPassword().toString());
                        dialogInterface.dismiss();
                    }
                });
                builder.show();
            }
        });
    }

    private void UpdateUser() {
        int str_id = Utils.kh_current.getKh_id();
        String str_name = text_name.getText().toString().trim();
        String str_phone = text_phone.getText().toString().trim();
        String str_email = text_email.getText().toString().trim();
        String str_username = text_usernm.getText().toString().trim();
        String str_pass = text_pass.getText().toString().trim();

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
        } else {
                compositeDisposable.add(apiBanHang.updateuser(str_id,str_username,str_pass,str_name,str_phone,str_email)
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

                                        Toast.makeText(getApplicationContext(), khachHangModel.getMessage(),Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(getApplicationContext(), khachHangModel.getMessage(),Toast.LENGTH_SHORT).show();
                                    }
                                },
                                throwable -> {
                                    Toast.makeText(getApplicationContext(), throwable.getMessage(),Toast.LENGTH_SHORT).show();
                                }
                        ));
        }
    }

    private void initView() {
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        text_name = findViewById(R.id.text_name);
        text_email = findViewById(R.id.text_email);
        text_phone = findViewById(R.id.text_phone);
        text_usernm = findViewById(R.id.text_usernm);
        text_pass = findViewById(R.id.text_pass);
        ck_hide_show = findViewById(R.id.ck_hide_show_pass);
        btnsavechange = findViewById(R.id.btnsavechange);
        toolbar = findViewById(R.id.toobar);

        text_name.setText(Utils.kh_current.getKh_hoten().toString());
        text_email.setText(Utils.kh_current.getKh_email().toString());
        text_phone.setText(Utils.kh_current.getKh_sdt().toString());
        text_usernm.setText(Utils.kh_current.getUsername().toString());
        text_pass.setText(Utils.kh_current.getPassword().toString());

    }

//    @Override
//    protected void onDestroy() {
//        compositeDisposable.clear();
//        super.onDestroy();
//    }
}