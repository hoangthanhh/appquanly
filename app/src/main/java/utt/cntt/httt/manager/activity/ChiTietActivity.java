package utt.cntt.httt.manager.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.nex3z.notificationbadge.NotificationBadge;

import java.text.DecimalFormat;

import io.paperdb.Paper;
import utt.cntt.httt.manager.R;
import utt.cntt.httt.manager.model.GioHang;
import utt.cntt.httt.manager.model.SanPhamMoi;
import utt.cntt.httt.manager.utils.Utils;

public class ChiTietActivity extends AppCompatActivity {
    TextView tensp, giasp, mota;
    Button btnthem;
    ImageView imghinhanh;
    Spinner spinner;
    Toolbar toolbar;
    SanPhamMoi sanPhamMoi;
    NotificationBadge badge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet);
        initView();
        ActionToolBar();
        initData();
        initControl();
    }

    private void initControl() {
        btnthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                themGioHang();
                Paper.book().write("giohang", Utils.manggiohang);
            }
        });
    }

    private void themGioHang() {
        if (Utils.manggiohang.size() > 0) {
            boolean flag = false;
            int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
            for (int i = 0; i < Utils.manggiohang.size(); i++) {
                if (Utils.manggiohang.get(i).getIdsp() == sanPhamMoi.getId()) {
                    Utils.manggiohang.get(i).setSoluong(soluong +  Utils.manggiohang.get(i).getSoluong());
                    flag = true;
                }
            }
            if (flag == false) {
                long gia = Long.parseLong(sanPhamMoi.getGiasp()) ;
                GioHang gioHang = new GioHang();
                gioHang.setGiasp(gia);
                gioHang.setSoluong(soluong);
                gioHang.setIdsp(sanPhamMoi.getId());
                gioHang.setTensp(sanPhamMoi.getTensp());
                gioHang.setHinhanh(sanPhamMoi.getHinhanh());
                Utils.manggiohang.add(gioHang);
            }
        } else {
            int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
            long gia = Long.parseLong(sanPhamMoi.getGiasp());
            GioHang gioHang = new GioHang();
            gioHang.setGiasp(gia);
            gioHang.setSoluong(soluong);
            gioHang.setIdsp(sanPhamMoi.getId());
            gioHang.setTensp(sanPhamMoi.getTensp());
            gioHang.setHinhanh(sanPhamMoi.getHinhanh());
            Utils.manggiohang.add(gioHang);
        }
        int totalItem = 0;
        for (int i = 0; i < Utils.manggiohang.size(); i++) {
            totalItem = totalItem + Utils.manggiohang.get(i).getSoluong();
        }
        badge.setText(String.valueOf(totalItem));
    }

//private void themGioHang() {
//    int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
//    boolean flag = false;
//
//    if (Utils.manggiohang.size() > 0) {
//        for (int i = 0; i < Utils.manggiohang.size(); i++) {
//            if (Utils.manggiohang.get(i).getIdsp() == sanPhamMoi.getId()) {
//                // Nếu sản phẩm đã có trong giỏ thì chỉ cộng thêm số lượng
//                Utils.manggiohang.get(i).setSoluong(
//                        Utils.manggiohang.get(i).getSoluong() + soluong
//                );
//                flag = true;
//                break;
//            }
//        }
//    }
//
//    // Nếu sản phẩm chưa có trong giỏ
//    if (!flag) {
//        GioHang gioHang = new GioHang();
//        gioHang.setIdsp(sanPhamMoi.getId());
//        gioHang.setTensp(sanPhamMoi.getTensp());
//        gioHang.setHinhanh(sanPhamMoi.getHinhanh());
//        gioHang.setSoluong(soluong);
//        gioHang.setGiasp(Long.parseLong(sanPhamMoi.getGiasp())); // CHỈ LƯU GIÁ GỐC
//        Utils.manggiohang.add(gioHang);
//    }
//
//    // Cập nhật badge (số lượng sản phẩm)
//    int totalItem = 0;
//    for (int i = 0; i < Utils.manggiohang.size(); i++) {
//        totalItem += Utils.manggiohang.get(i).getSoluong();
//    }
//    badge.setText(String.valueOf(totalItem));
//
//    Toast.makeText(this, "Đã thêm vào giỏ hàng!", Toast.LENGTH_SHORT).show();
//}



    private void initData() {
        sanPhamMoi = (SanPhamMoi) getIntent().getSerializableExtra("chitiet");
        tensp.setText(sanPhamMoi.getTensp());
        mota.setText(sanPhamMoi.getMota());
//        Glide.with(getApplicationContext()).load(sanPhamMoi.getHinhanh()).into(imghinhanh);
        Log.d("IMAGE_URL", "URL: " + sanPhamMoi.getHinhanh());
        if (sanPhamMoi.getHinhanh().contains("http")) {
            Glide.with(getApplicationContext()).load(sanPhamMoi.getHinhanh().trim()).into(imghinhanh);
        } else {
            String hinh = Utils.BASE_URL + "images/" + sanPhamMoi.getHinhanh().trim();
            Glide.with(getApplicationContext()).load(hinh).into(imghinhanh);
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        giasp.setText("Giá: " + decimalFormat.format(Double.parseDouble(sanPhamMoi.getGiasp())) + "Đ");
        Integer[] so = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> adapterspin = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, so);
        spinner.setAdapter(adapterspin);
    }

    private void initView() {
        tensp = findViewById(R.id.txttensp);
        giasp = findViewById(R.id.txtgiasp);
        mota = findViewById(R.id.txtmotachitiet);
        btnthem = findViewById(R.id.btnthemvaogiohang);
        spinner = findViewById(R.id.spinner);
        imghinhanh = findViewById(R.id.imgchitiet);
        toolbar = findViewById(R.id.toolbar);
        badge = findViewById(R.id.menu_sl);
        FrameLayout frameLayout = findViewById(R.id.framegiohang);
        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent giohang = new Intent(getApplicationContext(), GioHangActivity.class);
                startActivity(giohang);
            }
        });

        if (Utils.manggiohang != null) {
            int totalItem = 0;
            for (int i = 0; i < Utils.manggiohang.size(); i++) {
                totalItem = totalItem + Utils.manggiohang.get(i).getSoluong();
            }

            badge.setText(String.valueOf(totalItem));
        }
    }

    private void ActionToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Utils.manggiohang != null) {
            int totalItem = 0;
            for (int i = 0; i < Utils.manggiohang.size(); i++) {
                totalItem = totalItem + Utils.manggiohang.get(i).getSoluong();
            }

            badge.setText(String.valueOf(totalItem));
        }
    }
}