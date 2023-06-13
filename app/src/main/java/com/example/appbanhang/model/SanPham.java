package com.example.appbanhang.model;

import java.io.Serializable;

public class SanPham implements Serializable {
    private int sp_id;
    private String sp_tensp;
    private double sp_giatien;
    private String sp_cauhinh;
    private int sp_soluong;
    private String sp_linkhinhanh;
    private int sp_loaisp;

    public SanPham(int id, String tensp, double giatien, String linkhinh) {
        this.sp_id = id;
        this.sp_tensp = tensp;
        this.sp_giatien = giatien;
        this.sp_linkhinhanh = linkhinh;
    }

    public int getId() {
        return sp_id;
    }

    public void setId(int id) {
        this.sp_id = id;
    }

    public String getTensp() {
        return sp_tensp;
    }

    public void setTensp(String tensp) {
        this.sp_tensp = tensp;
    }

    public double getGiatien() {
        return sp_giatien;
    }

    public void setGiatien(double giatien) {
        this.sp_giatien = giatien;
    }

    public String getCauhinh() {
        return sp_cauhinh;
    }

    public void setCauhinh(String cauhinh) {
        this.sp_cauhinh = cauhinh;
    }

    public int getSoluong() {
        return sp_soluong;
    }

    public void setSoluong(int soluong) {
        this.sp_soluong = soluong;
    }

    public String getLinkhinh() {
        return sp_linkhinhanh;
    }

    public void setLinkhinh(String linkhinh) {
        this.sp_linkhinhanh = linkhinh;
    }

    public int getLoaisp() {
        return sp_loaisp;
    }

    public void setLoaisp(int loaisp) {
        this.sp_loaisp = loaisp;
    }
}
