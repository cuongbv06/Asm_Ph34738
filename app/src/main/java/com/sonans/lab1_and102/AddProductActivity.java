package com.sonans.lab1_and102;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddProductActivity extends AppCompatActivity {

    private Uri selectedImageUri;
    private static final String SHARED_PREFS_KEY = "MyAppPreferences";
    private static final String IMAGE_URI_KEY = "image_uri";

    ImageView img, back;
    TextView title;
    EditText edName, edPrice, edColor;
    Button btnOK;
    API_service apiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
//        img = findViewById(R.id.img);
        back = findViewById(R.id.btnBack);
        title = findViewById(R.id.title);
        edName = findViewById(R.id.edName);
        edPrice = findViewById(R.id.edPrice);
        edColor = findViewById(R.id.edColor);
        btnOK = findViewById(R.id.btnOK);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_service.DOMAIN)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Tạo đối tượng API_service
        apiService = retrofit.create(API_service.class);

//        img.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                pickThuVienFuntion();
//            }
//        });

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            String iou = bundle.getString("CRUD");
            String id = bundle.getString("_id");
            String ten = bundle.getString("ten");
            int gia = bundle.getInt("gia");
            String mau = bundle.getString("mau");
            String imgUp = bundle.getString("img");

            if(iou.equals("update")) {
                title.setText("Update Oto");
                edName.setText(ten);
                edPrice.setText(String.valueOf(gia));
                edColor.setText(mau);
                Uri imgLink = Uri.parse(imgUp);
                Picasso.get().load(imgLink).placeholder(R.drawable.img).error(R.drawable.err).into(img);

//                if (imgUp != null && !imgUp.isEmpty()) {
//                    // Hiển thị ảnh từ URL bằng Glide
//                    Glide.with(this)
//                            .load(Uri.parse(imgUp)) // Đường dẫn URL của ảnh
//                            .placeholder(R.drawable.img) // Ảnh mặc định nếu không tìm thấy
//                            .error(R.drawable.err) // Ảnh mặc định nếu xảy ra lỗi
//                            .into(img);
//                } else {
//                    Toast.makeText(this, "Anh null", Toast.LENGTH_SHORT).show();
//                }
                btnOK.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Lấy dữ liệu từ các trường nhập
                        String name = edName.getText().toString();
                        int price = Integer.parseInt(edPrice.getText().toString());
                        String color = edColor.getText().toString();
                        String imageUriString = selectedImageUri != null ? selectedImageUri.toString() : "";
                        // Tạo đối tượng OtoModel từ dữ liệu nhập
                        OtoModel updatedCar = new OtoModel(name, color, price, imageUriString);

                        // Gửi yêu cầu UPDATE bằng Retrofit
                        Call<Void> call = apiService.updateCar(id, updatedCar);
                        call.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if (response.isSuccessful()) {
                                    // Xử lý khi cập nhật sản phẩm thành công
                                    Toast.makeText(AddProductActivity.this, "Cập nhật sản phẩm thành công", Toast.LENGTH_SHORT).show();
                                } else {
                                    // Xử lý khi cập nhật sản phẩm không thành công
                                    Toast.makeText(AddProductActivity.this, "Cập nhật sản phẩm thất bại", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                // Xử lý khi gặp lỗi
                                Toast.makeText(AddProductActivity.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                        Intent i = new Intent(AddProductActivity.this, HomePageActivity.class);
                        startActivity(i);
                        finish();
                    }
                });
            }

        }else {
                title.setText("Add Oto");

                btnOK.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Lấy dữ liệu từ các trường nhập
                        String name = edName.getText().toString();
                        int price = Integer.parseInt(edPrice.getText().toString());
                        String color = edColor.getText().toString();
                        String imageUriString = selectedImageUri != null ? selectedImageUri.toString() : null;
                        // Tạo đối tượng OtoModel từ dữ liệu nhập
                        OtoModel newCar = new OtoModel(name, color, price, imageUriString);

                        // Gửi yêu cầu POST bằng Retrofit
                        Call<Void> call = apiService.addCar(newCar);
                        call.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if (response.isSuccessful()) {
                                    // Xử lý khi thêm sản phẩm thành công
                                    Toast.makeText(AddProductActivity.this, "Thêm sản phẩm thành công", Toast.LENGTH_SHORT).show();
                                } else {
                                    // Xử lý khi thêm sản phẩm không thành công
                                    Toast.makeText(AddProductActivity.this, "Thêm sản phẩm thất bại", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                // Xử lý khi gặp lỗi
                                Toast.makeText(AddProductActivity.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                        Intent i = new Intent(AddProductActivity.this, HomePageActivity.class);
                        startActivity(i);
                        finish();
                    }
                });
        }



        // Xử lý sự kiện click vào nút Thêm sản phẩm


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddProductActivity.this, HomePageActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    public void pickThuVienFuntion() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        galleryActivityResult.launch(intent);
    }

    private ActivityResultLauncher<Intent> galleryActivityResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent intent = result.getData();
                        selectedImageUri = intent.getData();

                        try {
                            Picasso.get().load(selectedImageUri).placeholder(R.drawable.img).error(R.drawable.err).into(img);
                            // Lưu đường dẫn của ảnh đã chọn vào SharedPreferences
                            SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS_KEY, Activity.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            if (selectedImageUri != null) {
                                editor.putString(IMAGE_URI_KEY, selectedImageUri.toString());
                            } else {
                                editor.remove(IMAGE_URI_KEY);
                            }
                            editor.apply();
                        } catch (Exception e) {
                            Log.d("TAG", "onActivityResult: Không thể load ảnh " + e.getMessage());
                        }
                    }
                }
            }
    );
}