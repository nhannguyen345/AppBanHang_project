package com.example.appbanhang.model;

public class Menu {
    private String tenmuc;
    private String hinhanh;

    public Menu(String tenmuc, String hinhanh) {
        this.tenmuc = tenmuc;
        this.hinhanh = hinhanh;
    }

    public String getTenmuc() {
        return tenmuc;
    }

    public void setTenmuc(String tenmuc) {
        this.tenmuc = tenmuc;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }
}
