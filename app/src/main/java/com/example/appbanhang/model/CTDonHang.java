package com.example.appbanhang.model;

public class CTDonHang {
    String sp_tensp;
    Double sp_giatien;
    String sp_linkhinhanh;
    int sp_soluong;

    public CTDonHang(String sp_tensp, Double sp_giatien, String sp_linkhinhanh, int sp_soluong) {
        this.sp_tensp = sp_tensp;
        this.sp_giatien = sp_giatien;
        this.sp_linkhinhanh = sp_linkhinhanh;
        this.sp_soluong = sp_soluong;
    }

    public String getSp_tensp() {
        return sp_tensp;
    }

    public void setSp_tensp(String sp_tensp) {
        this.sp_tensp = sp_tensp;
    }

    public Double getSp_giatien() {
        return sp_giatien;
    }

    public void setSp_giatien(Double sp_giatien) {
        this.sp_giatien = sp_giatien;
    }

    public String getSp_linkhinhanh() {
        return sp_linkhinhanh;
    }

    public void setSp_linkhinhanh(String sp_linkhinhanh) {
        this.sp_linkhinhanh = sp_linkhinhanh;
    }

    public int getSp_soluong() {
        return sp_soluong;
    }

    public void setSp_soluong(int sp_soluong) {
        this.sp_soluong = sp_soluong;
    }
}
