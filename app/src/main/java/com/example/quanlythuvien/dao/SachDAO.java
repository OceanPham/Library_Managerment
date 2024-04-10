package com.example.quanlythuvien.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.example.quanlythuvien.database.DatabaseSingleton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SachDAO {
    private Context context;
    SQLiteDatabase sqLiteDatabase = DatabaseSingleton.getDatabase();

    public SachDAO() {
    }
    public SachDAO(Context context) {
        this.context = context;
    }
    public int countAll() {
        SQLiteDatabase sqLiteDatabase = DatabaseSingleton.getDatabase();
        int soluong = 0;

        try (Cursor cursor = sqLiteDatabase.rawQuery("SELECT SUM(SoLuong) FROM Sach", null)) {
            if (cursor.moveToFirst()) {
                soluong = cursor.getInt(0);
            }
        } catch (SQLiteException e) {
            Log.e("SachDAO", "Error counting total books:", e);
        }
        return soluong;
    }

    public int countWithTTacGia(Integer id) {
        SQLiteDatabase sqLiteDatabase = DatabaseSingleton.getDatabase();
        int soluong = 0;
        try (Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM Sach WHERE MaTacGia=?", new String[]{String.valueOf(id)})) {
            if (cursor.moveToFirst()) {
                soluong = cursor.getInt(0);
            }
        } catch (SQLiteException e) {
            Log.e("SachDAO", "Error counting book base ACtor:", e);
        }
        return soluong;
    }

    public int countBorrowing() {
//        String query = "SELECT COUNT(*) FROM MuonSach WHERE NgayTra IS NULL";
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM MuonSach WHERE NgayTra IS NULL", null);
        int soluong;
        soluong = cursor.getCount();
        cursor.close();
        return soluong;
    }

    public int countBorrowingBetweenDate(Date start, Date end) {
        // Chuyển đổi thời gian bắt đầu và kết thúc thành định dạng chuỗi ngày "yyyy-MM-dd"
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String startDateString = sdf.format(start);
        String endDateString = sdf.format(end);
        // Truy vấn SQL để lấy số lượng sách được mượn trong khoảng thời gian
        String query = "SELECT * FROM MuonSach WHERE NgayMuon BETWEEN ? AND ?";
        String[] selectionArgs = { startDateString, endDateString };
        Cursor cursor = sqLiteDatabase.rawQuery(query, selectionArgs);
        int soluong = cursor.getCount();
        cursor.close();
        return soluong;
    }

    public int countOverDate() {
        // Tính toán thời điểm 2 tháng trước
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -11);
        Date twoMonthsAgo = calendar.getTime();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String twoMonthsAgoStr = dateFormat.format(twoMonthsAgo);

        Cursor cursor = sqLiteDatabase.rawQuery( "SELECT COUNT(*) FROM MuonSach WHERE NgayTra IS NULL AND NgayMuon < ?", new String[]{twoMonthsAgoStr});
        int soluong = 0;
        if (cursor != null) {
            cursor.moveToFirst();
            soluong = cursor.getInt(0);
            cursor.close();
        }
        return soluong;
    }


}
