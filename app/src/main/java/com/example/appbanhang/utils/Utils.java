package com.example.appbanhang.utils;

import com.example.appbanhang.model.DonHang;
import com.example.appbanhang.model.GioHang;
import com.example.appbanhang.model.KhachHang;

import java.util.List;

public class Utils {
    public static final String BASE_URL="http://192.168.1.3/banhang/client/";
    public static List<GioHang> manggiohang;
    public static KhachHang kh_current = new KhachHang();
    public static List<DonHang> mangdonhang;

    public static int kttk = 0;
    public static String ID_RECEIVED;
    public static final String SENDID = "idsend";
    public static final String RECEIVEDID = "idreceived";
    public static final String MESS = "message";
    public static final String DATETIME = "datetime";
    public static final String PATH_CHAT = "chat";
}
