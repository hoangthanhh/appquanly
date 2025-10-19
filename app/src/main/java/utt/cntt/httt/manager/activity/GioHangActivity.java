package utt.cntt.httt.manager.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;

import utt.cntt.httt.manager.R;
import utt.cntt.httt.manager.adapter.GioHangAdapter;
import utt.cntt.httt.manager.model.EventBus.TinhTongEvent;
import utt.cntt.httt.manager.utils.Utils;

public class GioHangActivity extends AppCompatActivity {
    TextView giohangtrong, tongtien;
    Toolbar toolbar;
    RecyclerView recyclerView;
    Button btnmuahang;
    GioHangAdapter adapter;
    long tongtiensp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);
        initView();
        initControl();

        if (Utils.mangmuahang != null) {
            Utils.mangmuahang.clear();
        }
        tinhTongTien();
    }

//    private void tinhTongTien() {
//        tongtiensp = 0;
//        for (int i = 0; i < Utils.mangmuahang.size(); i++) {
//            tongtiensp = tongtiensp + (Utils.mangmuahang.get(i).getGiasp() * Utils.mangmuahang.get(i).getSoluong());
//        }
//        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
//        tongtien.setText(decimalFormat.format(tongtiensp));
//
//    }

    private void tinhTongTien() {
        tongtiensp = 0;
        if (Utils.mangmuahang != null && Utils.mangmuahang.size() > 0) {
            for (int i = 0; i < Utils.mangmuahang.size(); i++) {
                tongtiensp += Utils.mangmuahang.get(i).getGiasp() * Utils.mangmuahang.get(i).getSoluong();
            }
        }

        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tongtien.setText(decimalFormat.format(tongtiensp) + " Đ");
    }


    private void initControl() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        if (Utils.manggiohang.size() == 0) {
            giohangtrong.setVisibility(View.VISIBLE);
        } else {
            adapter = new GioHangAdapter(getApplicationContext(),Utils.manggiohang);
            recyclerView.setAdapter(adapter);
        }

        btnmuahang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.mangmuahang == null || Utils.mangmuahang.size() == 0 || tongtiensp == 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(GioHangActivity.this);
                    builder.setTitle("Thông báo");
                    builder.setMessage("Giỏ hàng của bạn đang trống. Vui lòng thêm sản phẩm trước khi mua hàng.");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.show();
                    return;
                }
                Intent intent = new Intent(getApplicationContext(), ThanhToanActivity.class);
                intent.putExtra("tongtien", tongtiensp);
//                Utils.manggiohang.clear();

                startActivity(intent);
            }
        });

//        btnmuahang.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Kiểm tra giỏ hàng trống
//                if (Utils.mangmuahang == null || Utils.mangmuahang.size() == 0 || tongtiensp == 0) {
//                    AlertDialog.Builder builder = new AlertDialog.Builder(GioHangActivity.this);
//                    builder.setTitle("Thông báo");
//                    builder.setMessage("Giỏ hàng của bạn đang trống. Vui lòng thêm sản phẩm trước khi mua hàng.");
//                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                        }
//                    });
//                    builder.show();
//                    return;
//                }
//
//                Intent intent = new Intent(getApplicationContext(), ThanhToanActivity.class);
//                intent.putExtra("tongtien", tongtiensp);
//                Utils.manggiohang.clear();
//
//                startActivity(intent);
//            }
//        });


    }

    private void initView() {
        giohangtrong = findViewById(R.id.txtgiohangtrong);
        tongtien = findViewById(R.id.txttongtien);
        toolbar = findViewById(R.id.toobar);
        recyclerView = findViewById(R.id.recycleviewgiohang);
        btnmuahang = findViewById(R.id.btnmuahang);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void eventTinhTien(TinhTongEvent event) {
        if (event != null) {
            tinhTongTien();
        }

    }
}