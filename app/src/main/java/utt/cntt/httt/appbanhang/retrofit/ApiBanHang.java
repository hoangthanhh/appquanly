package utt.cntt.httt.appbanhang.retrofit;

import io.reactivex.rxjava3.core.Observable;

import io.reactivex.rxjava3.core.Observer;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import utt.cntt.httt.appbanhang.model.LoaiSpModel;
import utt.cntt.httt.appbanhang.model.SanPhamMoiModel;

public interface ApiBanHang {
    @GET("getloaisp.php")
    Observable<LoaiSpModel> getLoaiSp();

    @GET("getspmoi.php")
    Observable<SanPhamMoiModel> getSpMoi();

    @POST("chitiet.php")
    @FormUrlEncoded
    Observable<SanPhamMoiModel> getSanPham(
      @Field("page") int page,
      @Field("loai") int loai);
    ;
}
