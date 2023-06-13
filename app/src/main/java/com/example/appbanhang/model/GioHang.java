package com.example.appbanhang.model;

public class GioHang {
    int sp_id;
    String sp_tensp;
    long sp_giatien;
    String sp_linkhinhanh;
    int gh_soluong;

    public GioHang() {
    }

    public int getSp_id() {
        return sp_id;
    }

    public void setSp_id(int sp_id) {
        this.sp_id = sp_id;
    }

    public String getSp_tensp() {
        return sp_tensp;
    }

    public void setSp_tensp(String sp_tensp) {
        this.sp_tensp = sp_tensp;
    }

    public long getSp_giatien() {
        return sp_giatien;
    }

    public void setSp_giatien(long sp_giatien) {
        this.sp_giatien = sp_giatien;
    }

    public String getSp_linkhinhanh() {
        return sp_linkhinhanh;
    }

    public void setSp_linkhinhanh(String sp_linkhinhsp) {
        this.sp_linkhinhanh = sp_linkhinhsp;
    }

    public int getGh_soluong() {
        return gh_soluong;
    }

    public void setGh_soluong(int gh_soluong) {
        this.gh_soluong = gh_soluong;
    }
}
