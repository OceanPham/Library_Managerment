package com.example.quanlythuvien.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.quanlythuvien.database.DatabaseSingleton;
import com.example.quanlythuvien.model.VeUngDung;

public class VeUngDungDao {
    private Context context;
    SQLiteDatabase sqLiteDatabase = DatabaseSingleton.getDatabase();
    public VeUngDungDao(Context context){
        this.context = context;
    }
    public  long update(VeUngDung veUngDung){
        ContentValues values = new ContentValues();
        values.put("NoiDung", veUngDung.getNoiDung() );
        try {
            return sqLiteDatabase.update("ThongTinUngDung", values, "ID = ?", new String[]{String.valueOf(veUngDung.getID())});
        } catch (Exception e){
            Log.d("Không thể cập nhật", e.getMessage());
            return 0;
        }
    }
}
