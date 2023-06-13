package com.example.appbanhang.utils;

import com.example.appbanhang.model.DonHang;
import com.example.appbanhang.model.GioHang;
import com.example.appbanhang.model.KhachHang;

import java.util.List;

public class Utils {
    public static final String BASE_URL="http://192.168.1.6/banhang/";
    public static List<GioHang> manggiohang;
    public static KhachHang kh_current = new KhachHang();
    public static List<DonHang> mangdonhang;
}
