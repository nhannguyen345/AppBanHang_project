package com.example.appbanhang.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.appbanhang.R;

public class LoadVDTActivity extends AppCompatActivity {

    ImageView logo_vdt;
    TextView load_text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_vdtactivity);
        initView();

        //Lấy giá trị từ intent
        Intent intent = getIntent();
        int loaitt = intent.getIntExtra("loaividt", 1);
        long tongtien = intent.getLongExtra("tongtien", 1);
        String dc = intent.getStringExtra("diachi");

        //Set trường hợp ví điện tử Zalopay
        if (loaitt == 2){
            logo_vdt.setImageResource(R.drawable.rsz_2zalologo1);
            load_text.setText("Liên kết ví điện tử Zalopay");
        } else{
            load_text.setText("Liên kết ví điện tử MoMo");
        }

        //Chạy màn hình loading chuyển sang ví điện tử
        Thread thread = new Thread(){
            @Override
            public void run() {
                try{
                    sleep(2000);
                }catch (Exception ex){

                }finally {
                    Intent intent = new Intent(getApplicationContext(), ViDienTuActivity.class);
                    intent.putExtra("diachi",dc);
                    intent.putExtra("tongtien", tongtien);
                    intent.putExtra("loaividt", loaitt);
                    startActivity(intent);
                    finish();
                }
            }
        };
        thread.start();
    }

    private void initView() {
        logo_vdt = findViewById(R.id.logo_vdt);
        load_text= findViewById(R.id.load_text);
    }
}