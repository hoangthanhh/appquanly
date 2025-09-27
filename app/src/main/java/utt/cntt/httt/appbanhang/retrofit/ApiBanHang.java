package utt.cntt.httt.appbanhang.retrofit;

import io.reactivex.rxjava3.core.Observable;

import retrofit2.http.GET;
import utt.cntt.httt.appbanhang.model.LoaiSpModel;
import utt.cntt.httt.appbanhang.model.SanPhamMoiModel;

public interface ApiBanHang {
    @GET("getloaisp.php")
    Observable<LoaiSpModel> getLoaiSp();

    @GET("getspmoi.php")
    Observable<SanPhamMoiModel> getSpMoi();
}
