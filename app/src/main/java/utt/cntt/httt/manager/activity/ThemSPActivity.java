package utt.cntt.httt.manager.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.github.dhaval2404.imagepicker.ImagePicker;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utt.cntt.httt.manager.R;
import utt.cntt.httt.manager.databinding.ActivityThemspBinding;
import utt.cntt.httt.manager.model.MessageModel;
import utt.cntt.httt.manager.model.SanPhamMoi;
import utt.cntt.httt.manager.retrofit.ApiBanHang;
import utt.cntt.httt.manager.retrofit.RetrofitClient;
import utt.cntt.httt.manager.utils.Utils;

public class ThemSPActivity extends AppCompatActivity {
    Spinner spinner;
    Toolbar toobar;
    int loai = 0;
    ActivityThemspBinding binding;
    ApiBanHang apiBanHang;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    String mediaPath;
    SanPhamMoi sanPhamSua;
    boolean flag = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityThemspBinding.inflate(getLayoutInflater());
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        setContentView(binding.getRoot());
        initView();
        initData();
        ActionToolBar();

        Intent intent = getIntent();
        sanPhamSua = (SanPhamMoi) intent.getSerializableExtra("sua");
        if (sanPhamSua == null) {
            // them moi
            flag = false;
        } else {
            // sua
            flag = true;
            binding.btnthem.setText("Sửa sản phẩm");
            binding.toobar.setTitle("Sửa sản phẩm");
            // show data
            binding.mota.setText(sanPhamSua.getMota());
            binding.giasp.setText(sanPhamSua.getGiasp());
            binding.tensp.setText(sanPhamSua.getTensp());
            binding.hinhanh.setText(sanPhamSua.getHinhanh());
            binding.spinnerLoai.setSelection(sanPhamSua.getLoai());
        }
    }
    private void ActionToolBar() {
        setSupportActionBar(toobar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toobar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initData() {
        List<String> stringList = new ArrayList<>();
        stringList.add("Vui lòng chọn loại");
        stringList.add("Điện thoại");
        stringList.add("Laptop");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,stringList);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loai = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        binding.btnthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag == false) {
                    themsanpham();
                } else {
                    suaSanPham();
                }
            }
        });

        binding.imgcamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(ThemSPActivity.this)
                        .crop()
                        .compress(1024)
                        .maxResultSize(1000,1080)
                        .start();
            }
        });
    }

    private void suaSanPham() {
        String str_ten = binding.tensp.getText().toString().trim();
        String str_gia = binding.giasp.getText().toString().trim();
        String str_mota = binding.mota.getText().toString().trim();
        String str_hinhanh = binding.hinhanh.getText().toString().trim();
        if (TextUtils.isEmpty(str_ten) || TextUtils.isEmpty(str_gia) || TextUtils.isEmpty(str_mota) || TextUtils.isEmpty(str_hinhanh) || loai == 0) {
            Toast.makeText(getApplicationContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
        } else {
            compositeDisposable.add(apiBanHang.updatesp(str_ten, str_gia, str_hinhanh, str_mota, loai, sanPhamSua.getId())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            messageModel -> {
                                if (messageModel.isSuccess()) {
                                    Toast.makeText(getApplicationContext(), messageModel.getMessage(), Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), messageModel.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            },
                            throwable -> {
                                Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                    ));
        }

    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        mediaPath = data.getDataString();
//        uploadMultipleFiles();
//        Log.d("log", "onActivityResult: " + mediaPath);
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            mediaPath = uri.toString();
            Log.d("log", "URI ảnh chọn: " + mediaPath);
            uploadMultipleFiles();
        } else {
            Toast.makeText(this, "Không chọn được ảnh", Toast.LENGTH_SHORT).show();
        }
    }



    private void themsanpham() {
        String str_ten = binding.tensp.getText().toString().trim();
        String str_gia = binding.giasp.getText().toString().trim();
        String str_mota = binding.mota.getText().toString().trim();
        String str_hinhanh = binding.hinhanh.getText().toString().trim();
        if (TextUtils.isEmpty(str_ten) || TextUtils.isEmpty(str_gia) || TextUtils.isEmpty(str_mota) || TextUtils.isEmpty(str_hinhanh) || loai == 0) {
            Toast.makeText(getApplicationContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
        } else {
            compositeDisposable.add(apiBanHang.insertSp(str_ten, str_gia, str_hinhanh, str_mota, (loai))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            messageModel -> {
                                if (messageModel.isSuccess()) {
                                    Toast.makeText(getApplicationContext(), messageModel.getMessage(), Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), messageModel.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            },
                            throwable -> {
                                Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                    ));
        }
    }

//    private String getPath(Uri uri) {
//        String result;
//        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
//        if (cursor == null) {
//            result = uri.getPath();
//        } else {
//            cursor.moveToFirst();
//            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
//            result = cursor.getString(idx);
//            cursor.close();
//        }
//        return result;
//    }

    private String getPath(Uri uri) {
        // Nếu URI là file:// thì lấy trực tiếp
        if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        // Nếu URI là content:// thì copy ra file tạm
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            try {
                File file = new File(getCacheDir(), "temp_image_" + System.currentTimeMillis() + ".jpg");
                try (java.io.InputStream inputStream = getContentResolver().openInputStream(uri);
                     java.io.OutputStream outputStream = new java.io.FileOutputStream(file)) {
                    byte[] buffer = new byte[1024];
                    int len;
                    while ((len = inputStream.read(buffer)) > 0) {
                        outputStream.write(buffer, 0, len);
                    }
                }
                return file.getAbsolutePath();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }



//    private void uploadMultipleFiles() {
//        Uri uri = Uri.parse(mediaPath);
//        File file = new File(getPath(uri));
//        RequestBody requestBody1 = RequestBody.create(MediaType.parse("*/*"), file);
//        MultipartBody.Part fileToUpload1 = MultipartBody.Part.createFormData("file", file.getName(), requestBody1);
//
//        Call<MessageModel> call = apiBanHang.uploadFile(fileToUpload1);
//        call.enqueue(new Callback< MessageModel >() {
//            @Override
//            public void onResponse(Call < MessageModel > call, Response< MessageModel > response) {
//                MessageModel serverResponse = response.body();
//                if (serverResponse != null) {
//                    if (serverResponse.isSuccess()) {
//                        binding.hinhanh.setText(serverResponse.getName());
//                    } else {
//                        Toast.makeText(getApplicationContext(), serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    Log.v("Response", serverResponse.toString());
//                }
//            }
//            @Override
//            public void onFailure(Call < MessageModel > call, Throwable t) {
//                Log.d("log", t.getMessage());
//            }
//        });
//    }

    private void uploadMultipleFiles() {
        if (mediaPath == null) {
            Toast.makeText(this, "Chưa có ảnh để upload", Toast.LENGTH_SHORT).show();
            return;
        }

        Uri uri = Uri.parse(mediaPath);
        File file = new File(getPath(uri));
        if (!file.exists()) {
            Toast.makeText(this, "File không tồn tại!", Toast.LENGTH_SHORT).show();
            return;
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestBody);

        Call<MessageModel> call = apiBanHang.uploadFile(fileToUpload);
        call.enqueue(new Callback<MessageModel>() {
            @Override
            public void onResponse(Call<MessageModel> call, Response<MessageModel> response) {
                MessageModel serverResponse = response.body();
                if (serverResponse != null && serverResponse.isSuccess()) {
                    binding.hinhanh.setText(serverResponse.getName());
                    Toast.makeText(getApplicationContext(), "Upload thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Upload thất bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MessageModel> call, Throwable t) {
                Log.e("upload", "Lỗi upload: " + t.getMessage());
            }
        });
    }


    private void initView() {
        spinner = findViewById(R.id.spinner_loai);
        toobar = findViewById(R.id.toobar);
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}