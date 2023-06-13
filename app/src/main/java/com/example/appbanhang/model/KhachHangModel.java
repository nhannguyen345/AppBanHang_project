package com.example.appbanhang.model;

import java.util.List;

public class KhachHangModel {
    boolean success;
    String message;
    List<KhachHang> result;

    List<GioHang> result1;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<KhachHang> getResult() {
        return result;
    }

    public void setResult(List<KhachHang> result) {
        this.result = result;
    }

    public List<GioHang> getResult1() {
        return result1;
    }

    public void setResult1(List<GioHang> result1) {
        this.result1 = result1;
    }
}
