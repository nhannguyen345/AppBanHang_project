package com.example.appbanhang.model;

import java.util.List;

public class CTDonHangModel {
    boolean success;
    String message;
    List<CTDonHang> result;

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

    public List<CTDonHang> getResult() {
        return result;
    }

    public void setResult(List<CTDonHang> result) {
        this.result = result;
    }
}
