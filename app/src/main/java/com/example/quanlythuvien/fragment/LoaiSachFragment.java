package com.example.quanlythuvien.fragment;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanlythuvien.MainActivity;
import com.example.quanlythuvien.R;
import com.example.quanlythuvien.adapter.LoaiSachAdapter;
import com.example.quanlythuvien.dao.LoaiSachDAO;
import com.example.quanlythuvien.database.DatabaseSingleton;
import com.example.quanlythuvien.model.LoaiSach;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;


public class LoaiSachFragment extends Fragment {
    Dialog dialog;
    private    RecyclerView recyclerView;
    private LoaiSachDAO loaiSachDAO;
    private LoaiSachAdapter loaiSachAdapter;
    private FloatingActionButton addNewLoaiSach;
    ArrayList<LoaiSach> listLoaiSach;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_loai_sach, container, false);
        listLoaiSach = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recycle_LoaiSach);
        SQLiteDatabase sqLiteDatabase = DatabaseSingleton.getDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM TheLoaiSach", null);
        cursor.moveToFirst();
        while (cursor.isAfterLast()==false){
            LoaiSach loaiSach = new LoaiSach(cursor.getInt(0), cursor.getString(1));
            listLoaiSach.add(loaiSach);
            cursor.moveToNext();
        }
        cursor.close();
        loaiSachAdapter = new LoaiSachAdapter(listLoaiSach);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setAdapter(loaiSachAdapter);

        addNewLoaiSach = view.findViewById(R.id.addNewLoaiSach);
        addNewLoaiSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogAddNew();
            }
        });
        loaiSachAdapter.setOnItemClickListener(new LoaiSachAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(LoaiSach loaiSach) {
                showDialog(loaiSach.getMaTheLoai());
            }
        });
        return view;
    }
    public  void showDialog(Integer ID){

        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.huy_dialog_loaisach_action);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable( ContextCompat.getDrawable(getContext(), R.drawable.custom_dialog));
        dialog.setCancelable(false);

        TextView txtSuaTen = dialog.findViewById(R.id.txtSuaTen);
        TextView txtXoa = dialog.findViewById(R.id.txtXoa);
        Button btnCancel = dialog.findViewById(R.id.btnDialogCancel);
        txtXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                showDialogDelete(ID);
            }
        });

        txtSuaTen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                showDialogUpdate(ID);
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public  void showDialogDelete(Integer ID){

        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.huy_dialog_xoa_loaisach);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable( ContextCompat.getDrawable(getContext(), R.drawable.custom_dialog));
        dialog.setCancelable(false);

        Button btnCancelXoa = dialog.findViewById(R.id.btnCancelXoa);
        Button btnXacNhanXoa = dialog.findViewById(R.id.btnXacNhanXoa);
        dialog.show();
        btnCancelXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnXacNhanXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loaiSachDAO = new LoaiSachDAO(getContext());
                Integer delete = loaiSachDAO.delete(ID);
                if(delete==0){
                    Toast.makeText(getContext(), "Kệ sách phải trống mới có thể xóa!", Toast.LENGTH_SHORT).show();
                }
                else{
                    listLoaiSach.clear();
                    SQLiteDatabase sqLiteDatabase = DatabaseSingleton.getDatabase();
                    Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM TheLoaiSach", null);
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast()) {
                       LoaiSach loaiSach = new LoaiSach(cursor.getInt(0), cursor.getString(1));
                        listLoaiSach.add(loaiSach);
                        cursor.moveToNext();
                    }
                    cursor.close();
                    loaiSachAdapter.notifyDataSetChanged();
                    Toast.makeText(getContext(), "Xóa kệ sách thành công!", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });

    }

    public  void showDialogUpdate(Integer ID){

        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.huy_dialog_sua_loaisach);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable( ContextCompat.getDrawable(getContext(), R.drawable.custom_dialog));
        dialog.setCancelable(false);

        Button btnCancelSua = dialog.findViewById(R.id.btnCancelSua);
        Button btnXacNhanSua = dialog.findViewById(R.id.btnXacNhanSua);
        TextInputEditText txtTenMoiLoaiSach = dialog.findViewById(R.id.txtTenMoiLoaiSach);
        dialog.show();
        btnCancelSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnXacNhanSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int masach;
                LoaiSach loaiSach = new LoaiSach();
                masach = ID;
                loaiSach.setMaTheLoai(masach);
                loaiSach.setTenTheLoai(txtTenMoiLoaiSach.getText().toString());
                loaiSachDAO = new LoaiSachDAO(getContext());
                long update = loaiSachDAO.update(loaiSach);
                if(update==0){
                    Toast.makeText(getContext(), "Sửa không thành công", Toast.LENGTH_SHORT).show();
                }
                else{
                    listLoaiSach.clear();
                    SQLiteDatabase sqLiteDatabase = DatabaseSingleton.getDatabase();
                    Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM TheLoaiSach", null);
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast()) {
                        loaiSach = new LoaiSach(cursor.getInt(0), cursor.getString(1));
                        listLoaiSach.add(loaiSach);
                        cursor.moveToNext();
                    }
                    cursor.close();
                    loaiSachAdapter.notifyDataSetChanged();
                    Toast.makeText(getContext(), "Sửa kệ sách thành công!", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });

    }

    public  void showDialogAddNew(){
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.huy_dialog_them_loaisach);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable( ContextCompat.getDrawable(getContext(), R.drawable.custom_dialog));
        dialog.setCancelable(false);

        Button btnCancelThem = dialog.findViewById(R.id.btnCancelThem);
        Button btnXacNhanThem = dialog.findViewById(R.id.btnXacNhanThem);
        TextInputEditText txtTenMoiLoaiSach = dialog.findViewById(R.id.txtTenThemMoiLoaiSach);
        dialog.show();
        btnCancelThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnXacNhanThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int masach;
                LoaiSach loaiSach = new LoaiSach();
                loaiSach.setTenTheLoai(txtTenMoiLoaiSach.getText().toString());
                loaiSachDAO = new LoaiSachDAO(getContext());
                long insert = loaiSachDAO.insert(loaiSach);
                if(insert==0){
                    Toast.makeText(getContext(), "Thêm không thành công", Toast.LENGTH_SHORT).show();
                }
                else{
                    listLoaiSach.clear();
                    SQLiteDatabase sqLiteDatabase = DatabaseSingleton.getDatabase();
                    Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM TheLoaiSach", null);
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast()) {
                        loaiSach = new LoaiSach(cursor.getInt(0), cursor.getString(1));
                        listLoaiSach.add(loaiSach);
                        cursor.moveToNext();
                    }
                    cursor.close();
                    loaiSachAdapter.notifyDataSetChanged();
                    Toast.makeText(getContext(), "Sửa kệ sách thành công!", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });

    }
}