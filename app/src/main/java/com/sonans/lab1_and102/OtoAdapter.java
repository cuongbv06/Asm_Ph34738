package com.sonans.lab1_and102;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OtoAdapter extends RecyclerView.Adapter<OtoAdapter.ViewHolder>{

    List<OtoModel> list;
    Context context;

    FirebaseFirestore database;

    String img;



    public OtoAdapter(List<OtoModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public OtoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_rcv_oto, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OtoAdapter.ViewHolder holder, int position) {
        holder.name.setText(list.get(position).getTen()+"");
        holder.price.setText(list.get(position).getGia()+"");
        holder.color.setText(list.get(position).getMau()+"");

//        img = list.get(position).getImage();


//        Glide.with(holder.itemView.getContext())
//                .load(Uri.parse(img))
//                .placeholder(R.drawable.car)
//                .error(R.drawable.img)
//                .into(holder.imgOto);



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    int positionz = position;
                    if (position != RecyclerView.NO_POSITION) {
                        itemClickListener.onItemClick(positionz);
                    }
                }
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                String id = list.get(position).get_id();
                deleteCarById(id);
                ((HomePageActivity) context).loadOtoListFromServer();
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name, price, color;
        Button btnUp, btnDe;
        ImageView imgOto;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
           // imgOto = itemView.findViewById(R.id.imgOto);
            name = itemView.findViewById(R.id.otoName);
            price = itemView.findViewById(R.id.otoPrice);
            color = itemView.findViewById(R.id.otoMau);



        }
    }

    public interface ItemClickListener {
        void onItemClick(int position);
    }


    private ItemClickListener itemClickListener;
    public void setItemClickListener(ItemClickListener listener) {
        this.itemClickListener = listener;
    }

//    private void deleteCarById(String carId) {
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(API_service.DOMAIN)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        API_service apiService = retrofit.create(API_service.class);
//        Call<Void> call = apiService.deleteCar(carId);
//        call.enqueue(new Callback<Void>() {
//            @Override
//            public void onResponse(Call<Void> call, Response<Void> response) {
//                if (response.isSuccessful()) {
//                    // Xóa thành công, xử lý logic tại đây (nếu cần)
//                    Toast.makeText(context, "Xóa xe thành công", Toast.LENGTH_SHORT).show();
//                    // Sau khi xóa, bạn có thể cập nhật danh sách hoặc giao diện nếu cần
//                } else {
//                    // Xóa không thành công, xử lý logic tại đây (nếu cần)
//                    Toast.makeText(context, "Xóa xe không thành công", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Void> call, Throwable t) {
//                // Xử lý khi có lỗi xảy ra trong quá trình gửi yêu cầu
//                Log.e("API Call", "Failed to delete car", t);
//                Toast.makeText(context, "Lỗi khi kết nối đến API", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    public void updateList(List<OtoModel> newList) {
        this.list = newList;
        notifyDataSetChanged();
    }

    private void deleteCarById(String carId) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_service.DOMAIN)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        API_service apiService = retrofit.create(API_service.class);
        Call<Void> call = apiService.deleteCar(carId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Xóa thành công, cập nhật danh sách sản phẩm
                    Toast.makeText(context, "Xóa xe thành công", Toast.LENGTH_SHORT).show();
                     // Gọi phương thức updateOtoList từ activity
                } else {
                    // Xóa không thành công
                    Toast.makeText(context, "Xóa xe không thành công", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Xử lý khi có lỗi xảy ra trong quá trình gửi yêu cầu
                Log.e("API Call", "Failed to delete car", t);
            }
        });
    }

}
