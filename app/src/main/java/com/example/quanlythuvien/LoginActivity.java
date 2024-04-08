package com.example.quanlythuvien;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.example.quanlythuvien.MainActivity;
import com.example.quanlythuvien.Validate;
import com.example.quanlythuvien.databinding.ActivityLoginBinding;


public class LoginActivity extends AppCompatActivity {
    //Declared value for copy db
    String DB_PATH_SUFFIX = "/databases/";
    SQLiteDatabase database=null;
    String DATABASE_NAME="library_manager.db";
    //End
    SQLiteDatabase sqLiteDatabase = null;
    ActivityLoginBinding binding;
    Validate validate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        validate = new Validate();
//        setContentView(R.layout.activity_login);
        sqLiteDatabase=openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
        binding.btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = binding.edtUserName.getText().toString();
                String password = binding.edtPassword.getText().toString();
                if(username.equals("") || password.equals("")){
                    Toast.makeText(LoginActivity.this, "Vui lòng nhập tên đăng nhập và mật khẩu", Toast.LENGTH_SHORT).show();
                }else{
                    Boolean checkUser =validate. checkUserPassword(username, password, sqLiteDatabase);
                    if(checkUser==true){
                        Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        Intent Home_intent = new Intent(getApplicationContext(), MainActivity.class);
                        Home_intent.putExtra("tenTaiKhoan",username);
                        startActivity(Home_intent);
                    }else {
                        Toast.makeText(LoginActivity.this, "Tài khoản hoặc mật khẩu không hợp lệ!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        binding.btnNewAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_SignUp = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(intent_SignUp);
            }
        });
        binding.txtForgotPassWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_ForgotPassWord = new Intent(getApplicationContext(), ForgotPasswordActivity.class);
                startActivity(intent_ForgotPassWord);
            }
        });
    }
}