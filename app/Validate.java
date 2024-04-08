package com.example.deso_1.Controller;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.text.SimpleDateFormat;
import java.util.Date;
public class Validate {
    public boolean checkUserPassword(String username, String password,SQLiteDatabase sqLiteDatabase  ) {
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM Account WHERE UserName = ? AND PassWord = ?", new String[]{username, password});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public  boolean checkAccountValid(String username, String password, SQLiteDatabase sqLiteDatabase){
        Cursor cursor= sqLiteDatabase.rawQuery("SELECT * FROM Account WHERE UserName = ? AND PassWord = ?", new String[]{username, password});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public boolean insertData(String tennhanvien, String username, String password, String createdate, SQLiteDatabase sqLiteDatabase) {
//        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("TenNhanVien", tennhanvien);
        contentValues.put("UserName", username);
        contentValues.put("PassWord", password);
        contentValues.put("CreateDate", createdate);
        long result = sqLiteDatabase.insert("Account", null, contentValues);
        return result != -1;
    }

    private String formatDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return dateFormat.format(date);
    }

}
