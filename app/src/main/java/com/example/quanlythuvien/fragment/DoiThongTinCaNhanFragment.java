package com.example.quanlythuvien.fragment;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.quanlythuvien.R;
import com.example.quanlythuvien.database.DatabaseSingleton;

public class DoiThongTinCaNhanFragment extends Fragment {
    TextView txtMaQLTT, txtTenQLTT, txtGioiTinhQLTT, txtNgaySinhQLTT, txtEmailQLTT, txtSoDienThoaiQLTT, txtDiaChiQLTT;
    Button btnSuaThongTinQLTT;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_doi_thong_tin_ca_nhan, container, false);

        init(view);
        getLoggedInUserInfo();

        return view;
    }

    public void init(View view){
        txtMaQLTT = view.findViewById(R.id.txtMaQuanLyTT);
        txtTenQLTT = view.findViewById(R.id.txtTenQuanLyTT);
        txtGioiTinhQLTT = view.findViewById(R.id.txtGioiTinhQuanLyTT);
        txtNgaySinhQLTT = view.findViewById(R.id.txtNgaySinhQuanLyTT);
        txtEmailQLTT = view.findViewById(R.id.txtEmailQuanLyTT);
        txtSoDienThoaiQLTT = view.findViewById(R.id.txtSoDienThoaiQuanLyTT);
        txtDiaChiQLTT = view.findViewById(R.id.txtDiaChiQuanLyTT);
        btnSuaThongTinQLTT = view.findViewById(R.id.btnSuaThongTinQuanLy);

//        databaseHelper = new DatabaseHelper(getActivity());
    }

    private void getLoggedInUserInfo() {
        SQLiteDatabase sqLiteDatabase = DatabaseSingleton.getDatabase();
        Cursor cursorCurrent =  sqLiteDatabase.rawQuery("SELECT * FROM CurrentLogin", null);
        Integer currentID=0;
        while (cursorCurrent.isAfterLast()==false){
            currentID = cursorCurrent.getInt(0);
        }

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM QuanLy WHERE MaQuanLy=?", new String[]{String.valueOf(currentID)});
        if (cursor.isAfterLast()==false) {
            txtMaQLTT.setText(cursor.getInt(0));
            txtTenQLTT.setText(cursor.getString(1));
            txtNgaySinhQLTT.setText(cursor.getString(2));
            txtGioiTinhQLTT.setText(cursor.getString(3));
            txtDiaChiQLTT.setText(cursor.getString(4));
            txtSoDienThoaiQLTT.setText(cursor.getString(5));
            txtEmailQLTT.setText(cursor.getString(6));
            cursor.close();
        }
    }

}
