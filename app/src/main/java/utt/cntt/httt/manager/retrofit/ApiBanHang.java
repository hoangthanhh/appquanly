package utt.cntt.httt.manager.retrofit;

import io.reactivex.rxjava3.core.Observable;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import utt.cntt.httt.manager.model.DonHangModel;
import utt.cntt.httt.manager.model.LoaiSpModel;
import utt.cntt.httt.manager.model.MessageModel;
import utt.cntt.httt.manager.model.SanPhamMoiModel;
import utt.cntt.httt.manager.model.ThongKeModel;
import utt.cntt.httt.manager.model.UserModel;

public interface ApiBanHang {
    // GET DATA
    @GET("getloaisp.php")
    Observable<LoaiSpModel> getLoaiSp();

    @GET("getspmoi.php")
    Observable<SanPhamMoiModel> getSpMoi();

    @GET("thongke.php")
    Observable<ThongKeModel> getThongKe();

    // POST DATA
    @POST("chitiet.php")
    @FormUrlEncoded
    Observable<SanPhamMoiModel> getSanPham(
      @Field("page") int page,
      @Field("loai") int loai);

    @POST("dangki.php")
    @FormUrlEncoded
    Observable<UserModel> dangKi(
            @Field("email") String email,
            @Field("pass") String pass,
            @Field("username") String username,
            @Field("mobile") String mobile
    );

    @POST("dangnhap.php")
    @FormUrlEncoded
    Observable<UserModel> dangNhap(
            @Field("email") String email,
            @Field("pass") String pass);

    @POST("donhang.php")
    @FormUrlEncoded
    Observable<UserModel> createOder(
            @Field("email") String email
            , @Field("sdt") String sdt
            , @Field("tongtien") String tongtien
            , @Field("iduser") int id
            , @Field("diachi") String diachi
            , @Field("soluong") int soluong
            , @Field("chitiet") String chitiet);

    @POST("xemdonhang.php")
    @FormUrlEncoded
    Observable<DonHangModel> xemDonHang(
            @Field("iduser") int id
    );

    @POST("timkiem.php")
    @FormUrlEncoded
    Observable<SanPhamMoiModel> search(
            @Field("search") String search
    );

    @POST("deleteorder.php")
    @FormUrlEncoded
    Observable<MessageModel> deleteOrder(
            @Field("iddonhang") int id
    );

    @POST("xoa.php")
    @FormUrlEncoded
    Observable<SanPhamMoiModel> xoaSanPham(
            @Field("id") int id
    );

    @POST("insertsp.php")
    @FormUrlEncoded
    Observable<MessageModel> insertSp(
            @Field("tensp") String tensp
            , @Field("gia") String gia
            , @Field("hinhanh") String hinhanh
            , @Field("mota") String mota
            , @Field("loai") int id
    );

    @POST("updatesp.php")
    @FormUrlEncoded
    Observable<MessageModel> updatesp(
            @Field("tensp") String tensp
            , @Field("gia") String gia
            , @Field("hinhanh") String hinhanh
            , @Field("mota") String mota
            , @Field("loai") int idloai
            , @Field("id") int id
    );

    @POST("updateorder.php")
    @FormUrlEncoded
    Observable<MessageModel> updateOrder(
            @Field("id") int id
            , @Field("trangthai") int trangthai
    );

    @Multipart
    @POST("upload.php")
    Call<MessageModel> uploadFile(@Part MultipartBody.Part file);
}
