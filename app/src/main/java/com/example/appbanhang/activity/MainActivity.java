package com.example.appbanhang.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.example.appbanhang.R;
import com.example.appbanhang.adapter.GridViewSanPhamAdapter;
import com.example.appbanhang.adapter.MenuAdapter;
import com.example.appbanhang.model.Menu;
import com.example.appbanhang.model.SanPham;
import com.example.appbanhang.retrofit.ApiBanHang;
import com.example.appbanhang.retrofit.RetrofitClient;
import com.example.appbanhang.utils.Utils;
import com.google.android.material.navigation.NavigationView;
import com.nex3z.notificationbadge.NotificationBadge;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    ViewFlipper viewFipper;
    GridView gridviewmanhinhchinh;
    NavigationView navigationView;
    ListView listViewmanhinhchinh;
    DrawerLayout drawerLayout ;
    MenuAdapter menuAdapter;
    List<Menu> mangmenu;

    List<SanPham> mangsanpham;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiBanHang apiBanHang;

    List mangsp;
    GridViewSanPhamAdapter gridViewSanPhamAdapter;
    NotificationBadge badge;
    FrameLayout frameLayout;
    ImageView imgsearch, imageMess;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);


        Anhxa();
        Actionbar();
        if (isConnected(this)){
            Toast.makeText(getApplicationContext(), "Đăng nhập thành công", Toast.LENGTH_LONG).show();
            ActionViewFlipper();
            getLoaiSanPham();
            getEventClick();
            setIdAdmin();
        } else{
            Toast.makeText(getApplicationContext(), "khong co internet", Toast.LENGTH_LONG).show();
        }
    }

    private void setIdAdmin() {
        Utils.ID_RECEIVED = "0";
    }

    private void getEventClick() {
        listViewmanhinhchinh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        Intent trangchu = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(trangchu);
                        break;
                    case 1:
                        Intent dienthoai = new Intent(getApplicationContext(), DienThoaiActivity.class);
                        dienthoai.putExtra("loai", 1);
                        startActivity(dienthoai);
                        break;
                    case 2:
                        Intent laptop = new Intent(getApplicationContext(), DienThoaiActivity.class);
                        laptop.putExtra("loai", 0);
                        startActivity(laptop);
                        break;
                    case 3:
                        Intent chat = new Intent(getApplicationContext(), ChatActivity.class);
                        startActivity(chat);
                        break;
                    case 4:
                        Intent thongtin = new Intent(getApplicationContext(), UserInfoActivity.class);
                        startActivity(thongtin);
                        break;
                    case 5:
                        Intent donhang = new Intent(getApplicationContext(), QLDonHangActivity.class);
                        startActivity(donhang);
                        break;
                    case 6:
                        Intent dangxuat = new Intent(getApplicationContext(), DangNhapActivity.class);
                        startActivity(dangxuat);
                        break;
                }
            }
        });
    }

    private void getLoaiSanPham() {
        compositeDisposable.add(apiBanHang.getloaiSp()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        loaiSpModel -> {
                            if (loaiSpModel.isSuccess()){
                                mangsanpham = loaiSpModel.getResult();
                                gridViewSanPhamAdapter = new GridViewSanPhamAdapter(MainActivity.this, R.layout.item_sp_moi, mangsanpham);
                                gridviewmanhinhchinh.setAdapter(gridViewSanPhamAdapter);
                            }
                        }
                ));
    }

    private void ActionViewFlipper() {
        List<String> mangquangcao = new ArrayList<>();
        mangquangcao.add("http://mauweb.monamedia.net/thegioididong/wp-content/uploads/2017/12/banner-Le-hoi-phu-kien-800-300.png");
        mangquangcao.add("http://mauweb.monamedia.net/thegioididong/wp-content/uploads/2017/12/banner-HC-Tra-Gop-800-300.png");
        mangquangcao.add("http://mauweb.monamedia.net/thegioididong/wp-content/uploads/2017/12/banner-big-ky-nguyen-800-300.jpg");

        for(int i =0; i< mangquangcao.size(); i++){
            ImageView imageView = new ImageView(getApplicationContext());
            Glide.with(getApplicationContext()).load(mangquangcao.get(i)).into(imageView);
            imageView.setScaleType((ImageView.ScaleType.FIT_XY));
            viewFipper.addView(imageView);
        }
        viewFipper.setFlipInterval(3000);
        viewFipper.setAutoStart(true);
        Animation slide_in = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in_right);
        Animation slide_out = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_out_right);
        viewFipper.setInAnimation(slide_in);
        viewFipper.setOutAnimation(slide_out);

    }

    private void Actionbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void Anhxa(){
        imgsearch = findViewById(R.id.imgsearch);
        imageMess = findViewById(R.id.image_mess);
        toolbar= findViewById(R.id.toobarmanhinhchinh);
        viewFipper = findViewById(R.id.viewlipper);
        gridviewmanhinhchinh = findViewById(R.id.grid_view);
        listViewmanhinhchinh = findViewById(R.id.listviewmanhinhchinh);
        navigationView = findViewById(R.id.navigationview);
        drawerLayout = findViewById(R.id.drawerlayout);
        badge = findViewById(R.id.menu_sl);
        frameLayout = findViewById(R.id.framegiohang);
        //khoi tao list
        mangmenu = new ArrayList<>();
        mangmenu.add(new Menu("Trang chủ", "https://ngochieu.name.vn/img/home.png"));
        mangmenu.add(new Menu("Điện thoại", "https://ngochieu.name.vn/img/mobile.png"));
        mangmenu.add(new Menu("Laptop", "https://ngochieu.name.vn/img/laptop.png"));
        mangmenu.add(new Menu("Liên hệ", "https://ngochieu.name.vn/img/contact.png"));
        mangmenu.add(new Menu("Thông tin", "https://ngochieu.name.vn/img/info.png"));
        mangmenu.add(new Menu("Quản lý đơn hàng", "https://cdn-icons-png.flaticon.com/512/1037/1037332.png"));
        mangmenu.add(new Menu("Đăng xuất", "https://cdn-icons-png.flaticon.com/128/4400/4400828.png"));

        //Khoi tao Adapter
        menuAdapter = new MenuAdapter(mangmenu, getApplicationContext());
        listViewmanhinhchinh.setAdapter(menuAdapter);

        if (Utils.manggiohang == null){
            Utils.manggiohang = new ArrayList<>();
        } else{
            int totalItem = 0;
            for (int i=0; i<Utils.manggiohang.size(); i++){
                totalItem = totalItem + Utils.manggiohang.get(i).getGh_soluong();
            }
            badge.setText(String.valueOf(totalItem));
        }

        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent giohang = new Intent(getApplicationContext(), GioHangActivity.class);
                startActivity(giohang);
            }
        });

        imgsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                startActivity(intent );
            }
        });

        imageMess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
                startActivity(intent );
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        int totalItem = 0;
        for (int i=0; i<Utils.manggiohang.size(); i++){
            totalItem = totalItem + Utils.manggiohang.get(i).getGh_soluong();
        }
        badge.setText(String.valueOf(totalItem));
    }

    private boolean isConnected (Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if ((wifi != null && wifi.isConnected()) || (mobile != null) && mobile.isConnected()){
            return true;
        } else{
            return false;
        }
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}