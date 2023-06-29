package com.example.appbanhang.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appbanhang.R;

public class LoadAppBanHangActivity extends AppCompatActivity {

    ImageView logo_vdt;
    TextView load_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_vdtactivity);
        initView();
        SetLogoAndText();
        //Chạy màn hình loading chuyển sang man hinh chinh
        Thread thread = new Thread(){
            @Override
            public void run() {
                try{
                    sleep(2500);
                }catch (Exception ex){

                }finally {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };
        thread.start();
    }

    private void SetLogoAndText(){
        logo_vdt.setImageResource(R.mipmap.ic_launcher);
        load_text.setText("Cảm ơn bạn đã mua hàng!! Vui lòng chờ vài giây để chuyển sang màn hình chính.");
    }

    private void initView() {
        logo_vdt = findViewById(R.id.logo_vdt);
        load_text= findViewById(R.id.load_text);
    }
}