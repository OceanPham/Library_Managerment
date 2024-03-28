package com.example.quanlythuvien.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.quanlythuvien.database.DatabaseSingleton;
import com.example.quanlythuvien.model.LoaiSach;

public class LoaiSachDAO {
    private Context context;
    SQLiteDatabase sqLiteDatabase = DatabaseSingleton.getDatabase();

    public LoaiSachDAO(Context context) {
        this.context = context;
    }
    public  long insert(LoaiSach loaiSach){
        ContentValues values = new ContentValues();
        values.put("TenTheLoai", loaiSach.getTenTheLoai());
        try {
            return sqLiteDatabase.insert("TheLoaiSach", null, values);
        } catch (Exception e){
            Log.d("Can't insert new The Loai Sach", e.getMessage());
            return 0;
        }
    }


    public  long update(LoaiSach loaiSach){
        ContentValues values = new ContentValues();
        values.put("TenTheLoai", loaiSach.getTenTheLoai());
        try {
            return sqLiteDatabase.update("TheLoaiSach", values, "MaTheLoai=?", new String[]{String.valueOf(loaiSach.getMaTheLoai())});
        } catch (Exception e){
            Log.d("Can't update new The Loai Sach", e.getMessage());
            return 0;
        }
    }

    public int delete(Integer id){
        SQLiteDatabase sqLiteDatabase = DatabaseSingleton.getDatabase();
        LoaiSach loaiSach = new LoaiSach();
        int soluong = loaiSach.countSach(id);
        if(soluong==0){
            return sqLiteDatabase.delete("TheLoaiSach", "MaTheLoai=?", new String[]{String.valueOf(id)});
        }else{
            return 0;
        }
    }


}
