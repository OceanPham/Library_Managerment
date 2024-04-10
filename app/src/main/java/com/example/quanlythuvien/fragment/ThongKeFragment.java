package com.example.quanlythuvien.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanlythuvien.R;
import com.example.quanlythuvien.dao.LoaiSachDAO;
import com.example.quanlythuvien.dao.SachDAO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class ThongKeFragment extends Fragment {
    TextView txtTongSoSach, txtSoSachDangMuon, txtSoSachQuaHan, txtNgayBatDau, txtNgayKetThuc, txtSoLuongTheoThang;
    private SachDAO sachDAO;
    private SimpleDateFormat sdf;
    Button btnThongKe;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thong_ke, container, false);
         init(view);
        sachDAO = new SachDAO(getContext());
        sdf = new SimpleDateFormat("dd/MM/yyyy");

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        calendar.set(year, month, day);
         Date startDate = calendar.getTime();
         Date endDate = calendar.getTime();
        final Date[] startDateArray = {startDate, endDate};
        int sosachmuontrongTime = sachDAO.countBorrowingBetweenDate(startDate, endDate);
        int tongsoluong = sachDAO.countAll();
        int sosachdangmuon = sachDAO.countBorrowing();
        int sosachquahan = sachDAO.countOverDate();

        txtNgayBatDau.setText(sdf.format(startDate));
        txtNgayKetThuc.setText(sdf.format(endDate));
        txtTongSoSach.setText(tongsoluong+"");
        txtSoSachDangMuon.setText(sosachdangmuon+"");
        txtSoSachQuaHan.setText(sosachquahan+"");
        txtSoLuongTheoThang.setText(sosachmuontrongTime+"");

        txtNgayBatDau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar1  = Calendar.getInstance();
                int mYear = calendar1.get(Calendar.YEAR);
                int mMonth= calendar1.get(Calendar.MONTH);
                int mDay= calendar1.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), 0, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        GregorianCalendar calendar2 = new GregorianCalendar(year, month, dayOfMonth);
                        txtNgayBatDau.setText(sdf.format(calendar2.getTime()));
                        startDateArray[0] = calendar2.getTime();
//                        startDate = calendar2.getTime();
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        txtNgayKetThuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar1  = Calendar.getInstance();
                int mYear = calendar1.get(Calendar.YEAR);
                int mMonth= calendar1.get(Calendar.MONTH);
                int mDay= calendar1.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), 0, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        GregorianCalendar calendar2 = new GregorianCalendar(year, month, dayOfMonth);
                        txtNgayKetThuc.setText(sdf.format(calendar2.getTime()));
                        startDateArray[1] = calendar2.getTime();
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        btnThongKe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int sosachtrongTime = sachDAO.countBorrowingBetweenDate(startDateArray[0], startDateArray[1]);
                txtSoLuongTheoThang.setText(sosachtrongTime+"");
            }
        });

        return  view;
    }

    public void init(View view){
        txtTongSoSach = view.findViewById(R.id.tongSoSach);
        txtSoSachDangMuon = view.findViewById(R.id.txtSoLuongSachMuon);
        txtSoSachQuaHan = view.findViewById(R.id.txtSoSachQuaHan);
        txtNgayBatDau = view.findViewById(R.id.txtNgayBatDau);
        txtNgayKetThuc = view.findViewById(R.id.txtNgayKetThuc);
        txtSoLuongTheoThang = view.findViewById(R.id.txtSoLuongTheoThang);
        btnThongKe = view.findViewById(R.id.btnThongKe);
    }
}