package com.example.quanlythuvien.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.quanlythuvien.LoginActivity;
import com.example.quanlythuvien.MainActivity;
import com.example.quanlythuvien.R;
import com.example.quanlythuvien.database.DatabaseSingleton;


public class DoiMatKhauFragment extends Fragment {
    SQLiteDatabase sqLiteDatabase = null;

    EditText edtpassHienTai, edtpassMoi1, edtpassMoi2;
    Button btnXacNhanDoiPass, btnThoatDoiMatKhau;
    Intent myIntent;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_doi_mat_khau, container, false);

        edtpassHienTai = view.findViewById(R.id.edtpassHienTai);
        edtpassMoi1 = view.findViewById(R.id.edtpassMoi1);
        edtpassMoi2 = view.findViewById(R.id.edtpassMoi2);

        btnXacNhanDoiPass = view.findViewById(R.id.btnXacNhanDoiPass);
        btnThoatDoiMatKhau = view.findViewById(R.id.btnThoatDoiMatKhau);


        myIntent = getActivity().getIntent();
        String data = "";
        if (myIntent != null && myIntent.getExtras() != null) {
            data = myIntent.getStringExtra("tenTaiKhoan");
        }
        sqLiteDatabase = DatabaseSingleton.getDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT MatKhau FROM QuanLy where TenDangNhap =?", new String[]{data});

        if (cursor != null && cursor.moveToFirst()) {
            String matkhau = cursor.getString(0);
            cursor.close();


            String finalData = data;
            btnXacNhanDoiPass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String passHienTai = edtpassHienTai.getText().toString().trim();
                    String passMoi1 = edtpassMoi1.getText().toString().trim();
                    String passMoi2 = edtpassMoi2.getText().toString().trim();

                    if (!passHienTai.equals(matkhau)) {
                        edtpassHienTai.setError("Mật khẩu không đúng!");
                        edtpassHienTai.requestFocus();
                    } else if (!passMoi1.equals(passMoi2)) {
                        edtpassMoi2.setError("Mật khẩu mới không trùng khớp!");
                        edtpassMoi2.requestFocus();
                    } else {

                        sqLiteDatabase.execSQL("UPDATE QuanLy SET MatKhau = ? WHERE TenDangNhap = ?", new String[]{passMoi1, finalData});
                        Toast.makeText(getActivity(), "Đổi mật khẩu thành công! Vui lòng đăng nhập lại", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                    }
                }
            });


            btnThoatDoiMatKhau.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    getActivity().finish();
                }
            });
        }
        return view;
    }
}