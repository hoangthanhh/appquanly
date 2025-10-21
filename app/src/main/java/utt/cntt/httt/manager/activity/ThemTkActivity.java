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

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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
import utt.cntt.httt.manager.databinding.ActivityThemTkBinding;
import utt.cntt.httt.manager.databinding.ActivityThemspBinding;
import utt.cntt.httt.manager.model.MessageModel;

import utt.cntt.httt.manager.model.User;
import utt.cntt.httt.manager.retrofit.ApiBanHang;
import utt.cntt.httt.manager.retrofit.RetrofitClient;
import utt.cntt.httt.manager.utils.Utils;

public class ThemTkActivity extends AppCompatActivity {

    Spinner spinner;
    Toolbar toobar;
    int loai = 0;
    ActivityThemTkBinding binding;
    ApiBanHang apiBanHang;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    String mediaPath;
    User userSua;
    boolean flag = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityThemTkBinding.inflate(getLayoutInflater());
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        setContentView(binding.getRoot());
        initView();
        initData();
        ActionToolBar();

        Intent intent = getIntent();
        userSua = (User) intent.getSerializableExtra("sua");
        if (userSua == null) {
            // them moi
            flag = false;
        } else {
            // sua
            flag = true;
            binding.btnthem.setText("Sửa tài khoản");
            binding.toobar.setTitle("Sửa tài khoản");
            // show data
            binding.email.setText(userSua.getEmail());
            binding.pass.setText(userSua.getPass());
            binding.username.setText(userSua.getUsername());
            binding.mobile.setText(userSua.getMobile());
            binding.spinnerLoaitk.setSelection(userSua.getRole());
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
        stringList.add("Vui lòng chọn loại tài khoản");
        stringList.add("User");
        stringList.add("Admin");
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
                    themtaikhoan();
                } else {
                    suaTaiKhoan();
                }
            }
        });

    }

    private void suaTaiKhoan() {
        String str_email = binding.email.getText().toString().trim();
        String str_pass = binding.pass.getText().toString().trim();
        String str_username = binding.username.getText().toString().trim();
        String str_mobile = binding.mobile.getText().toString().trim();
        if (TextUtils.isEmpty(str_email) || TextUtils.isEmpty(str_pass) || TextUtils.isEmpty(str_username) || TextUtils.isEmpty(str_mobile) || loai == 0) {
            Toast.makeText(getApplicationContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
        } else {
            compositeDisposable.add(apiBanHang.updatetk(str_email, str_pass, str_username, str_mobile, loai, userSua.getId())
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

    private void themtaikhoan() {
        String str_email = binding.email.getText().toString().trim();
        String str_pass = binding.pass.getText().toString().trim();
        String str_username = binding.username.getText().toString().trim();
        String str_mobile = binding.mobile.getText().toString().trim();
        if (TextUtils.isEmpty(str_email) || TextUtils.isEmpty(str_pass) || TextUtils.isEmpty(str_username) || TextUtils.isEmpty(str_mobile) || loai == 0) {
            Toast.makeText(getApplicationContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
        } else {
            compositeDisposable.add(apiBanHang.insertTk(str_email, str_pass, str_username, str_mobile, (loai))
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

    private void initView() {
        spinner = findViewById(R.id.spinner_loaitk);
        toobar = findViewById(R.id.toobar);
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}