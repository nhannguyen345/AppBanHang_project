package com.example.appbanhang.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appbanhang.R;
import com.example.appbanhang.adapter.CTDonHangAdapter;
import com.example.appbanhang.adapter.ListHDAdapter;
import com.example.appbanhang.model.GioHang;
import com.example.appbanhang.retrofit.ApiBanHang;
import com.example.appbanhang.retrofit.RetrofitClient;
import com.example.appbanhang.utils.Utils;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class ThanhToanActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    ListHDAdapter listHDAdapter;
    CTDonHangAdapter ctDonHangAdapter;
    List<GioHang> gioHangList = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    EditText name, phone, address;
    TextView text_tongtien;
    Button btnthanhtoan;
    String tong_tien;
    ApiBanHang apiBanHang;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanh_toan);
        Intent intent = getIntent();
        tong_tien = intent.getCharSequenceExtra("tongtien").toString();
        initView();
        text_tongtien.setText("Tổng tiền: "+tong_tien+"Đ");
        ActionToolBar();
        initControll();
    }

    private void initControll() {
        name.setText(Utils.kh_current.getKh_hoten());
        phone.setText(Utils.kh_current.getKh_sdt());

        btnthanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Hiển thị thông báo ở logcat
                Log.d("test", new Gson().toJson(Utils.manggiohang));
                //lấy mã khách hàng
                int khid = Utils.kh_current.getKh_id();
                //lấy tổng tiền
                long tongtien =Long.parseLong(tong_tien.replace(",", ""));
                //lấy ngày hiện tại
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
                String currentDate = dateFormat.format(calendar.getTime());
                Log.d("kiem tra ngay", currentDate);
                //lấy địa chỉ
                String dc = address.getText().toString();

                if (dc.isEmpty()){
                    Toast.makeText(ThanhToanActivity.this, "Hãy nhập địa chỉ giao hàng", Toast.LENGTH_SHORT).show();
                }else{
                    showCustomDialog();
                    //post
//                    compositeDisposable.add(apiBanHang.thanhtoan(khid, tongtien, currentDate, dc, new Gson().toJson(Utils.manggiohang))
//                            .subscribeOn(Schedulers.io())
//                            .observeOn(AndroidSchedulers.mainThread())
//                            .subscribe(
//                                    khachHangModel -> {
//                                        if( khachHangModel.isSuccess()){
//                                            Toast.makeText(getApplicationContext(), "thành công",Toast.LENGTH_SHORT).show();
//                                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                                            startActivity(intent);
//                                            finish();
//                                        }else{
//                                            Toast.makeText(getApplicationContext(), khachHangModel.getMessage(),Toast.LENGTH_SHORT).show();
//                                        }
//                                    },
//                                    throwable -> {
//                                        Toast.makeText(getApplicationContext(), throwable.getMessage(),Toast.LENGTH_SHORT).show();
//                                    }
//                            ));

                }
            }
        });
    }

    private void showCustomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.thanhtoan_dialog, null);

        // Lấy tham chiếu đến các phần tử trong tệp XML dialog_custom.xml
        RadioGroup radioGroup = dialogView.findViewById(R.id.radioGroup);
        RadioButton radioButton1 = dialogView.findViewById(R.id.radioButton1);
        RadioButton radioButton2 = dialogView.findViewById(R.id.radioButton2);

        // Thiết lập hành động cho nút "OK" trong thông báo
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Xử lý khi người dùng nhấp vào nút "OK"
                int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();
                // Kiểm tra xem radio button nào được chọn
                if (selectedRadioButtonId == radioButton1.getId()) {
                    // Xử lý khi radio button 1 được chọn
                    Intent intent = new Intent(getApplicationContext(), ViDienTuActivity.class);
                    //Gửi loại ví điện tử
                    intent.putExtra("loaividt", 1);
                    //Gửi địa chỉ
                    String dc = address.getText().toString();
                    intent.putExtra("diachi",dc);
                    //Gửi tổng tiền
                    long tongtien =Long.parseLong(tong_tien.replace(",", ""));
                    intent.putExtra("tongtien", tongtien);

                    startActivity(intent);
                } else if (selectedRadioButtonId == radioButton2.getId()) {
                    // Xử lý khi radio button 2 được chọn
                    Intent intent = new Intent(getApplicationContext(), ViDienTuActivity.class);
                    //Gửi loại ví điện tử
                    intent.putExtra("loaividt", 2);
                    //Gửi địa chỉ
                    String dc = address.getText().toString();
                    intent.putExtra("diachi",dc);
                    //Gửi tổng tiền
                    long tongtien =Long.parseLong(tong_tien.replace(",", ""));
                    intent.putExtra("tongtien", tongtien);

                    startActivity(intent);
                }
            }
        });

        // Thiết lập hành động cho nút "Cancel" trong thông báo
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Xử lý khi người dùng nhấp vào nút "Cancel"
                dialog.dismiss();
            }
        });

        builder.setView(dialogView);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void initView() {
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        gioHangList = Utils.manggiohang;
        toolbar = findViewById(R.id.toobar);
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        address = findViewById(R.id.address);
        text_tongtien = findViewById(R.id.text_tongtien);
        recyclerView = findViewById(R.id.list_dh);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        listHDAdapter = new ListHDAdapter(getApplicationContext(), gioHangList);
        recyclerView.setAdapter(listHDAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        btnthanhtoan = findViewById(R.id.btnthanhtoan);
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


}