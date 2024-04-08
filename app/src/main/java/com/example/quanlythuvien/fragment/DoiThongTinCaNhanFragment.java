package com.example.quanlythuvien.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanlythuvien.R;
import com.example.quanlythuvien.SignupActivity;
import com.example.quanlythuvien.Validate;
import com.example.quanlythuvien.dao.VeUngDungDao;
import com.example.quanlythuvien.database.DatabaseSingleton;
import com.example.quanlythuvien.model.VeUngDung;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DoiThongTinCaNhanFragment extends Fragment {

    TextView tvTenQuanLy,tvMaQuanLy,tvTenDangNhap,tvNgaySinh,tvGioiTinh,tvDiaChi,tvSoDienThoai,tvEmail;
    SQLiteDatabase sqLiteDatabase = null;
    Intent myIntent;
    private Dialog dialog;
    private SimpleDateFormat sdf;
    Spinner spinnerGioiTinh;

    Validate validate;
    Button btnSuaThongTinCaNhan;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_doi_thong_tin_ca_nhan, container, false);


        sdf = new SimpleDateFormat("dd/MM/yyyy");
        myIntent = getActivity().getIntent();
        String data = "";
        if (myIntent != null && myIntent.getExtras() != null) {
            data = myIntent.getStringExtra("tenTaiKhoan");
        }

        sqLiteDatabase = DatabaseSingleton.getDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM QuanLy where TenDangNhap =?", new String[]{data});

        tvMaQuanLy = view.findViewById(R.id.tvMaQuanLy);
        tvTenDangNhap = view.findViewById(R.id.tvTenDangNhap);
        tvTenQuanLy = view.findViewById(R.id.tvTenQuanLy);
        tvNgaySinh = view.findViewById(R.id.tvNgaySinh);
        tvGioiTinh = view.findViewById(R.id.tvGioiTinh);
        tvDiaChi = view.findViewById(R.id.tvDiaChi);
        tvSoDienThoai = view.findViewById(R.id.tvSoDienThoai);
        tvEmail = view.findViewById(R.id.tvEmail);

        btnSuaThongTinCaNhan = view.findViewById(R.id.btnSuaThongTinCaNhan);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int maQuanLy = cursor.getInt(0);
            tvMaQuanLy.setText(maQuanLy + "");

            String tenDangNhap = cursor.getString(2);
            tvTenDangNhap.setText(tenDangNhap);

            String tenQuanLy = cursor.getString(1);
            tvTenQuanLy.setText(tenQuanLy);

            String ngaySinhQuanLy = cursor.getString(3);
            tvNgaySinh.setText(ngaySinhQuanLy);

             String gioiTinhQuanLy = cursor.getString(4);
            tvGioiTinh.setText(gioiTinhQuanLy);

            String diaChiQuanLy = cursor.getString(5);
            tvDiaChi.setText(diaChiQuanLy);

            String soDienThoaiQuanLy = cursor.getString(6);
            tvSoDienThoai.setText(soDienThoaiQuanLy);

            String emailQuanLy = cursor.getString(7);
            tvEmail.setText(emailQuanLy);

            cursor.moveToNext();

        }

        cursor.close();

        String finalData = data;
        btnSuaThongTinCaNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDialog(finalData);

            }
        });

       return view;
    }

    public void showDialog(String tenDangNhap) {

        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.suathongtincanhan_dialog);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.custom_dialog));
        dialog.setCancelable(false);

        Button btnXacNhanSuaThongTinCaNhan = dialog.findViewById(R.id.btnXacNhanSuaThongTinCaNhan);
        Button btnThoatSuaThongTinCaNhan = dialog.findViewById(R.id.btnThoatSuaThongTinCaNhan);

        dialog.show();
        spinnerGioiTinh = dialog.findViewById(R.id.spinnerGioiTinh);
        String[] gioiTinhArray = {"Nam", "Nữ", "Khác"};
        ArrayAdapter<String> gioiTinhAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, gioiTinhArray);
        gioiTinhAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGioiTinh.setAdapter(gioiTinhAdapter);



        sqLiteDatabase = DatabaseSingleton.getDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM QuanLy where TenDangNhap =?", new String[]{tenDangNhap});

        TextView tvMaQuanLymoi, tvTenQuanLymoi, tvNgaySinhmoi, tvGioiTinhmoi, tvDiaChimoi, tvSoDienThoaimoi, tvEmailmoi;
        Spinner   spinnerGioiTinh;

        tvMaQuanLymoi = dialog.findViewById(R.id.tvMaQuanLymoi);
        tvTenQuanLymoi = dialog.findViewById(R.id.tvTenQuanLymoi);
        tvNgaySinhmoi = dialog.findViewById(R.id.tvNgaySinhmoi);
        spinnerGioiTinh = dialog.findViewById(R.id.spinnerGioiTinh);
        tvDiaChimoi = dialog.findViewById(R.id.tvDiaChimoi);
        tvSoDienThoaimoi = dialog.findViewById(R.id.tvSoDienThoaimoi);
        tvEmailmoi = dialog.findViewById(R.id.tvEmailmoi);


        String gioiTinhQuanLy="";
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int maQuanLy = cursor.getInt(0);
            tvMaQuanLymoi.setText(maQuanLy + "");


            String tenQuanLy = cursor.getString(1);
            tvTenQuanLymoi.setText(tenQuanLy);

            String ngaySinhQuanLy = cursor.getString(3);
            tvNgaySinhmoi.setText(ngaySinhQuanLy);

             gioiTinhQuanLy = cursor.getString(4);

            String diaChiQuanLy = cursor.getString(5);
            tvDiaChimoi.setText(diaChiQuanLy);

            String soDienThoaiQuanLy = cursor.getString(6);
            tvSoDienThoaimoi.setText(soDienThoaiQuanLy);

            String emailQuanLy = cursor.getString(7);
            tvEmailmoi.setText(emailQuanLy);


            cursor.moveToNext();

        }

        cursor.close();


        if (!gioiTinhQuanLy.isEmpty()) {
            int position = gioiTinhAdapter.getPosition(gioiTinhQuanLy);
            spinnerGioiTinh.setSelection(position);
        }
        tvNgaySinhmoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog d = new DatePickerDialog(getContext(), android.R.style.Theme_Holo_Light_Dialog,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                c.set(year, month, dayOfMonth);
                                tvNgaySinhmoi.setText(sdf.format(c.getTime()));
                            }
                        }, mYear, mMonth, mDay);
                d.show();
            }
        });

        btnXacNhanSuaThongTinCaNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy các thông tin mới từ dialog
                String tenQuanLyMoi = tvTenQuanLymoi.getText().toString();
                String ngaySinhMoi = tvNgaySinhmoi.getText().toString();
                String gioiTinhMoi = spinnerGioiTinh.getSelectedItem().toString();
                String diaChiMoi = tvDiaChimoi.getText().toString();
                String soDienThoaiMoi = tvSoDienThoaimoi.getText().toString();
                String emailMoi = tvEmailmoi.getText().toString();


                ContentValues values = new ContentValues();
                values.put("TenQuanLy", tenQuanLyMoi);
                values.put("NgaySinh", ngaySinhMoi);
                values.put("GioiTinh", gioiTinhMoi);
                values.put("DiaChi", diaChiMoi);
                values.put("SDT", soDienThoaiMoi);
                values.put("Gmail", emailMoi);

                Boolean phoneNew = validate.checkPhone(soDienThoaiMoi);
                Boolean emailquanLy = validate.checkGmail(emailMoi);
                if(soDienThoaiMoi == "" || emailMoi == ""){
                    Toast.makeText(getContext(), "Vui lòng nhập thông tin đầy đủ", Toast.LENGTH_SHORT).show();
                }else if(!phoneNew){
                    tvSoDienThoaimoi.setError("Số điện thoại không hợp lệ! Vui lòng nhập lại");
                }else if(!emailquanLy){
                    tvEmailmoi.setError("Email không hợp lệ! vui lòng nhập lai");
                }else {
                    int rowsAffected = sqLiteDatabase.update("QuanLy", values, "TenDangNhap = ?", new String[]{tenDangNhap});
                    if (rowsAffected > 0) {
                        // Nếu cập nhật thành công, thông báo và đóng dialog
//                        Toast.makeText(getContext(), "Cập nhật thông tin thành công", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();

                        showDialogNotifi("Cập nhật thông tin thành công");
                        reloadData(tenDangNhap);
                    } else {

                        Toast.makeText(getContext(), "Cập nhật thông tin thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        btnThoatSuaThongTinCaNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

    private void reloadData(String tenDangNhap) {
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM QuanLy where TenDangNhap =?", new String[]{tenDangNhap});
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int maQuanLy = cursor.getInt(0);
            tvMaQuanLy.setText(String.valueOf(maQuanLy));

            String tenDangNhapM = cursor.getString(2);
            tvTenDangNhap.setText(tenDangNhapM);

            String tenQuanLy = cursor.getString(1);
            tvTenQuanLy.setText(tenQuanLy);

            String ngaySinhQuanLy = cursor.getString(3);
            tvNgaySinh.setText(ngaySinhQuanLy);

            String gioiTinhQuanLy = cursor.getString(4);
            tvGioiTinh.setText(gioiTinhQuanLy);

            String diaChiQuanLy = cursor.getString(5);
            tvDiaChi.setText(diaChiQuanLy);

            String soDienThoaiQuanLy = cursor.getString(6);
            tvSoDienThoai.setText(soDienThoaiQuanLy);

            String emailQuanLy = cursor.getString(7);
            tvEmail.setText(emailQuanLy);

            cursor.moveToNext();
        }
        cursor.close();
    }
    public  void showDialogNotifi(String notit){
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.huy_dialog_success);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable( ContextCompat.getDrawable(getContext(), R.drawable.custom_dialog));
        dialog.setCancelable(true);
        Button btnCancelNotit = dialog.findViewById(R.id.btnCancelNotit);
        TextView txtnoti = dialog.findViewById(R.id.txtNotification);
        txtnoti.setText(notit);
        dialog.show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        }, 2000);
        btnCancelNotit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}
