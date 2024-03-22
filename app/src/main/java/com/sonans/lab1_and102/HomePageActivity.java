package com.sonans.lab1_and102;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomePageActivity extends AppCompatActivity {

    FloatingActionButton fab;
    List<OtoModel> listOto;
    OtoAdapter otoAdapter;
    RecyclerView rcv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        fab = findViewById(R.id.btnAdd);
        rcv = findViewById(R.id.rcv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rcv.setLayoutManager(layoutManager);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddProductActivity();
            }
        });

        // Khởi tạo otoAdapter ở đây
        otoAdapter = new OtoAdapter(new ArrayList<>(), HomePageActivity.this);
        rcv.setAdapter(otoAdapter);

        loadOtoListFromServer();
    }

    private void openAddProductActivity() {
        Intent i = new Intent(HomePageActivity.this, AddProductActivity.class);
        startActivity(i);
    }

    public void loadOtoListFromServer() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_service.DOMAIN)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        API_service apiService = retrofit.create(API_service.class);
        Call<List<OtoModel>> call = apiService.getOto();

        call.enqueue(new Callback<List<OtoModel>>() {
            @Override
            public void onResponse(Call<List<OtoModel>> call, Response<List<OtoModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listOto = response.body();
                    // Cập nhật dữ liệu cho otoAdapter và gán item click listener
                    otoAdapter.updateList(listOto);
                    otoAdapter.setItemClickListener(new OtoAdapter.ItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            setupRecyclerView();
                        }
                    });
                } else {
                    showError("Không thể lấy danh sách sản phẩm từ máy chủ.");
                }
            }

            @Override
            public void onFailure(Call<List<OtoModel>> call, Throwable t) {
                Log.e("API Call", "Failed to fetch Oto list", t);
                showError("Lỗi kết nối đến máy chủ.");
            }
        });
    }

    private void setupRecyclerView() {
        otoAdapter = new OtoAdapter(listOto, HomePageActivity.this);
        rcv.setAdapter(otoAdapter);
        otoAdapter.setItemClickListener(new OtoAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int position) {
                openUpdateProductActivity(position);
            }
        });
    }

    private void openUpdateProductActivity(int position) {
        OtoModel selectedOto = listOto.get(position);
        Bundle bundle = new Bundle();
        bundle.putString("CRUD", "update");
        bundle.putString("_id", selectedOto.get_id());
        bundle.putString("ten", selectedOto.getTen());
        bundle.putString("mau", selectedOto.getMau());
        bundle.putString("img", selectedOto.getImage());
        bundle.putInt("gia", selectedOto.getGia());
        Intent i = new Intent(HomePageActivity.this, AddProductActivity.class);
        i.putExtras(bundle);
        startActivity(i);
    }

    private void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void updateOtoList() {
        // Gọi API hoặc thực hiện các thao tác khác để cập nhật danh sách mới từ máy chủ
        // Sau đó, cập nhật dữ liệu trong Adapter bằng cách tạo một danh sách mới và gọi phương thức updateList()
        // Ví dụ:
        // otoAdapter.updateList(newOtoList);
    }
}
