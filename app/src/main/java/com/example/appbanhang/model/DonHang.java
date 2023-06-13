package com.example.appbanhang.model;

public class DonHang {
    int dh_id;
    int kh_id;
    double dh_tongtien;
    String dh_ngaydat;
    String dh_diachi;
    String dh_trangthai;

    public DonHang() {
    }

    public int getDh_id() {
        return dh_id;
    }

    public void setDh_id(int dh_id) {
        this.dh_id = dh_id;
    }

    public int getKh_id() {
        return kh_id;
    }

    public void setKh_id(int kh_id) {
        this.kh_id = kh_id;
    }

    public double getDh_tongtien() {
        return dh_tongtien;
    }

    public void setDh_tongtien(double dh_tongtien) {
        this.dh_tongtien = dh_tongtien;
    }

    public String getDh_ngaydat() {
        return dh_ngaydat;
    }

    public void setDh_ngaydat(String dh_ngaydat) {
        this.dh_ngaydat = dh_ngaydat;
    }

    public String getDh_diachi() {
        return dh_diachi;
    }

    public void setDh_diachi(String dh_diachi) {
        this.dh_diachi = dh_diachi;
    }

    public String getDh_trangthai() {
        return dh_trangthai;
    }

    public void setDh_trangthai(String dh_trangthai) {
        this.dh_trangthai = dh_trangthai;
    }

}
