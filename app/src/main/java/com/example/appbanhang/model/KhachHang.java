package com.example.appbanhang.model;

public class KhachHang {
    int kh_id;
    String username;
    String password;
    String kh_hoten;
    String kh_sdt;
    String kh_email;

    public int getKh_id() {
        return kh_id;
    }

    public void setKh_id(int kh_id) {
        this.kh_id = kh_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getKh_hoten() {
        return kh_hoten;
    }

    public void setKh_hoten(String kh_hoten) {
        this.kh_hoten = kh_hoten;
    }

    public String getKh_sdt() {
        return kh_sdt;
    }

    public void setKh_sdt(String kh_sdt) {
        this.kh_sdt = kh_sdt;
    }

    public String getKh_email() {
        return kh_email;
    }

    public void setKh_email(String kh_email) {
        this.kh_email = kh_email;
    }
}
