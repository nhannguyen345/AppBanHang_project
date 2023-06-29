package com.example.appbanhang.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.appbanhang.R;
import com.example.appbanhang.retrofit.ApiBanHang;
import com.example.appbanhang.retrofit.RetrofitClient;
import com.example.appbanhang.utils.Utils;
import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ViDienTuActivity extends AppCompatActivity {

    View backgr1;
    ImageView logo;
    TextView tt_htthanhtoan, tt_taikhoan, tt_chuyentien, sodt, tenngdung, sodutk, sotiencc;
    Button btnxacnhan;
    ApiBanHang apiBanHang;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vidientu);
        initView();
        changeTheme();
        initData();
    }

    private void changeTheme() {
        Intent intent = getIntent();
        int loaitt = intent.getIntExtra("loaividt", 1);
        long tongtien = intent.getLongExtra("tongtien", 1);
        if (loaitt == 2){
            logo.setImageResource(R.drawable.rsz_2zalologo1);
            tt_htthanhtoan.setText("Ví điện tử ZaloPay");
            backgr1.setBackgroundColor(ContextCompat.getColor(this, R.color.blue));
            tt_taikhoan.setTextColor(ContextCompat.getColor(this, R.color.blue));
            tt_chuyentien.setTextColor(ContextCompat.getColor(this, R.color.blue));
            btnxacnhan.setBackgroundColor(ContextCompat.getColor(this, R.color.blue));
        }
        //Gán nội dung cho EditText
        sodt.setText(Utils.kh_current.getKh_sdt());
        tenngdung.setText(Utils.kh_current.getKh_hoten());
        //Tạo số dư tài khoản
        long sodtk = CreateSodu(tongtien);
        DecimalFormat df = new DecimalFormat("###,###,###");
        String format = df.format(sodtk);
        sodutk.setText(format + "Đ");
        //Gán nội dung cho số tiền cần chuyển
        sotiencc.setText(df.format(tongtien)+"Đ");
    }

    private long CreateSodu(long tongtien) {
        long minValue = tongtien;
        long maxValue = tongtien + 10000000L;

        Random random = new Random();
        long randomNumber = minValue + (long) (random.nextDouble() * (maxValue - minValue));
        return randomNumber;
    }

    private void initData() {
        btnxacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Gọi Dialog nhập mật khẩu
                DialogNhapmk();
            }
        });
    }

    private void DialogNhapmk(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_nhapmk, null);

        //Tham chiếu các phần tử trong tệp dialog_nhapmk
        EditText editText = dialogView.findViewById(R.id.editTextNumberPassword);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String mk = editText.getText().toString();
                if(mk.isEmpty()){
                    Toast.makeText(ViDienTuActivity.this, "Chưa nhập mật khẩu!!", Toast.LENGTH_SHORT).show();
                } else if (!mk.equals("234765")){
                    Toast.makeText(ViDienTuActivity.this, "Sai mật khẩu!!", Toast.LENGTH_SHORT).show();
                } else{
                    //lấy mã khách hàng
                    int khid = Utils.kh_current.getKh_id();
                    //lấy ngày hiện tại
                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
                    String currentDate = dateFormat.format(calendar.getTime());
                    Log.d("kiem tra ngay", currentDate);

                    Intent intent = getIntent();
                    //lấy diachi
                    String dc = intent.getStringExtra("diachi");
                    //lấy tổng tiền
                    long tongtien = intent.getLongExtra("tongtien", 1);
                    //post
                    compositeDisposable.add(apiBanHang.thanhtoan(khid, tongtien, currentDate, dc, new Gson().toJson(Utils.manggiohang))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    khachHangModel -> {
                                        if( khachHangModel.isSuccess()){
                                            Toast.makeText(getApplicationContext(), "Thanh toán thành công",Toast.LENGTH_SHORT).show();
                                            Utils.manggiohang.clear();
                                            Intent intent1 = new Intent(getApplicationContext(), LoadAppBanHangActivity.class);
                                            startActivity(intent1);
                                            finish();
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
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void initView() {
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        backgr1 = findViewById(R.id.backgr1);
        logo = findViewById(R.id.logo);
        tt_htthanhtoan = findViewById(R.id.tt_htthanhtoan);
        tt_taikhoan = findViewById(R.id.tt_taikhoan);
        tt_chuyentien = findViewById(R.id.tt_chuyentien);
        sodt = findViewById(R.id.sodt);
        tenngdung = findViewById(R.id.tenngdung);
        sodutk = findViewById(R.id.sodutk);
        sotiencc = findViewById(R.id.sotiencc);
        btnxacnhan = findViewById(R.id.btnxacnhan);
    }
}