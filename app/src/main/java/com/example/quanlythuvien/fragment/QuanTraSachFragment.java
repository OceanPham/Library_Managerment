package com.example.quanlythuvien.fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.text.Editable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanlythuvien.R;
import com.example.quanlythuvien.dao.DAOMuonSach;

import java.text.SimpleDateFormat;
import java.util.Date;

public class QuanTraSachFragment extends Fragment {
    Button btnxacnhantra;
    EditText edtnhapma;
    DAOMuonSach daoMuonSach;
    Date currentDate = new Date();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    String formattedDate = dateFormat.format(currentDate);
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_quan_tra_sach, container, false);
        Bundle bundlenhan = getArguments();
        int manhan = bundlenhan.getInt("mamuon");
        anhXa(v);
        btnxacnhantra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ma = edtnhapma.getText().toString().trim();

                if (ma.isEmpty()) {
                    showDialogNotiFail("Vui lòng nhập mã để trả sách");
                }
                else {
                    int manhantra = Integer.parseInt(ma);
                    if (manhantra == manhan) {
                        if(daoMuonSach.update_trasach("3",bundlenhan.getString("tensach"), bundlenhan.getString("ngaymuon"),bundlenhan.getInt("soluongmuon"),formattedDate)>0){
                            showDialogNotiSuccess("Trả sách thành công");
                            HomeFragment fragment = new HomeFragment();
                            getActivity().getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.content_frame, fragment)
                                    .addToBackStack(null)
                                    .commit();
                        }
                        else {
                            showDialogNotiSuccess("Trả sách thất bại. Vui lòng kiểm tra lại thông tin");
                        }
                    }
                    else{
                        showDialogNotiFail("Bạn nhập sai mã trả sách");
                    }
                }
            }
        });
        return v;
    }

    public  void showDialogNotiFail(String notit){
        Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.quan_thongbao_thatbai);
        Window win = dialog.getWindow();
        if(win == null){
            return;
        }
        win.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        win.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams window = win.getAttributes();
        window.gravity = Gravity.CENTER;
        win.setAttributes(window);
        TextView txt = dialog.findViewById(R.id.txtFail);
        txt.setText(notit);
        Button btnthoat = dialog.findViewById(R.id.btnCancelFail);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Đóng dialog sau 2 giây
                dialog.dismiss();
            }
        }, 2000);
        dialog.show();
        btnthoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

    public  void showDialogNotiSuccess(String notit){
        Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.quan_layout_thongbao_ok);
        Window win = dialog.getWindow();
        if(win == null){
            return;
        }
        win.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        win.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams window = win.getAttributes();
        window.gravity = Gravity.CENTER;
        win.setAttributes(window);
        TextView txt = dialog.findViewById(R.id.txtNotification);
        txt.setText(notit);
        Button btnthoat = dialog.findViewById(R.id.btnCancelNotit);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Đóng dialog sau 2 giây
                dialog.dismiss();
            }
        }, 2000);
        dialog.show();
        btnthoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

    private void anhXa(View v) {
        btnxacnhantra = v.findViewById(R.id.btnxacnhantrasach);
        edtnhapma = v.findViewById(R.id.edtnhapmatrasach);
        daoMuonSach = new DAOMuonSach(v.getContext());
    }
}