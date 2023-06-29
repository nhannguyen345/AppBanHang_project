package com.example.appbanhang.retrofit;

import io.reactivex.rxjava3.core.Observable;

import com.example.appbanhang.model.CTDonHangModel;
import com.example.appbanhang.model.DonHangModel;
import com.example.appbanhang.model.KhachHangModel;
import com.example.appbanhang.model.LoaiSpModel;
import com.example.appbanhang.model.SanPham;
import com.google.gson.Gson;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiBanHang {
    @GET("getloaisp.php")
    Observable<LoaiSpModel> getloaiSp();

    @POST("chitiet.php")
    @FormUrlEncoded
    Observable<LoaiSpModel> getSanPham(
            @Field("page") int page,
            @Field("loai") int loai
    );

    @POST("dangki.php")
    @FormUrlEncoded
    Observable<KhachHangModel> dangki(
            @Field("username") String username,
            @Field("password") String password,
            @Field("kh_hoten") String kh_hoten,
            @Field("kh_sdt") String kh_sdt,
            @Field("kh_email") String kh_email
    );

    @POST("dangnhap.php")
    @FormUrlEncoded
    Observable<KhachHangModel> dangnhap(
            @Field("username") String username,
            @Field("password") String password
    );

    @POST("updateinfo.php")
    @FormUrlEncoded
    Observable<KhachHangModel> updateuser(
            @Field("kh_id") int kh_id,
            @Field("username") String username,
            @Field("password") String password,
            @Field("kh_hoten") String kh_hoten,
            @Field("kh_sdt") String kh_sdt,
            @Field("kh_email") String kh_email
    );

    @POST("giohang.php")
    @FormUrlEncoded
    Observable<KhachHangModel> giohang(
            @Field("kh_id") int kh_id,
            @Field("gh_masp") int gh_masp,
            @Field("gh_soluong") int gh_soluong,
            @Field("flag") int flag
    );

    @POST("thanhtoan.php")
    @FormUrlEncoded
    Observable<KhachHangModel> thanhtoan(
            @Field("kh_id") int kh_id,
            @Field("dh_tongtien") long dh_tongtien,
            @Field("dh_ngaydat") String dh_ngaydat,
            @Field("dh_diachi") String dh_diachi,
            @Field("chitiet") String chitiet
    );

    @POST("getdonhang.php")
    @FormUrlEncoded
    Observable<DonHangModel> getdonhang(
            @Field("kh_id") int kh_id
    );

    @POST("getctdonhang.php")
    @FormUrlEncoded
    Observable<CTDonHangModel> getctdonhang(
            @Field("dh_id") int dh_id
    );

    @POST("timkiem.php")
    @FormUrlEncoded
    Observable<LoaiSpModel> timkiem(
            @Field("search") String search
    );

    @POST("reset.php")
    @FormUrlEncoded
    Observable<KhachHangModel> resetpass(
            @Field("email") String email
    );

    @POST("checksoluongtk.php")
    @FormUrlEncoded
    Observable<LoaiSpModel> checksoluongtk(
            @Field("sp_id") int sp_id
    );
}
