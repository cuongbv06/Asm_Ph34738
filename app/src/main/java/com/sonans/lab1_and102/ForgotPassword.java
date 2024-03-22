package com.sonans.lab1_and102;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText edtUsers;
    private Button btnRetrievePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        mAuth=FirebaseAuth.getInstance();
        edtUsers= findViewById(R.id.editName);
        btnRetrievePassword= findViewById(R.id.btnRetrievePassword);

        btnRetrievePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Forgot();
            }
        });
    }
    private void Forgot(){
        String emailAddress;
        emailAddress=edtUsers.getText().toString();
        mAuth.sendPasswordResetEmail(emailAddress).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ForgotPassword.this, "Vui lòng kiểm tra hộp thư để cập nhật mật khẩu", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ForgotPassword.this, HomePageActivity.class);
                    startActivity(intent);
                }else {
                    Log.w(TAG, "creatUserWithEmail:failure", task.getException());
                    Toast.makeText(ForgotPassword.this, "Lỗi gửi email", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}