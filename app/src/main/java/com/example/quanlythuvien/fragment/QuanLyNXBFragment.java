package com.example.quanlythuvien.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanlythuvien.R;
import com.example.quanlythuvien.adapter.AdapterNhaXB.MyAdapterNhaXB;
import com.example.quanlythuvien.adapter.AdapterNhaXB.NhaXB;
import com.example.quanlythuvien.dao.DAONhaXB;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class QuanLyNXBFragment extends Fragment {
    LinearLayout line;
    ListView list;
    DAONhaXB daoNhaXB ;
    MyAdapterNhaXB myAdapterNhaXB;
    ArrayList<NhaXB> listnhaxb;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_quan_ly_n_x_b, container, false);
        anhXa(v);
        daoNhaXB = new DAONhaXB(v.getContext());
        listnhaxb = new ArrayList<>();
        listnhaxb = daoNhaXB.getAllNhaXB();
        myAdapterNhaXB = new MyAdapterNhaXB((Activity) v.getContext(),R.layout.item_nhaxb_layout,listnhaxb);
        list.setAdapter(myAdapterNhaXB);

        line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenFeedback();
            }
        });
        return v;
    }
    public void reloadData() {
        listnhaxb.clear();
        listnhaxb.addAll(daoNhaXB.getAllNhaXB());
        myAdapterNhaXB.notifyDataSetChanged();
    }

    private void OpenFeedback() {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.quan_dialog_them_nxb);
        Window win = dialog.getWindow();
        if (win == null) {
            return;
        }
        win.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        win.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowattribute = win.getAttributes();
        windowattribute.gravity = Gravity.CENTER;
        win.setAttributes(windowattribute);
        EditText edttennhaxb = dialog.findViewById(R.id.txtthemtennhaxb);
        EditText edtdiachinhaxb = dialog.findViewById(R.id.txtThemdiachinxb);
        EditText edtquocgianhaxb = dialog.findViewById(R.id.txtthemquocgianxb);
        Button btnthem = dialog.findViewById(R.id.btnThemnhaxb);
        Button btnhuy = dialog.findViewById(R.id.btnThoatthemnhaxb);
        dialog.show();
        btnhuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ten = edttennhaxb.getText().toString().trim();
                String diachi = edtdiachinhaxb.getText().toString().trim();
                String quocgia = edtquocgianhaxb.getText().toString().trim();
                boolean check = true;
                if(ten.isEmpty()){
                    check = false;
                    showDialogNotiFail("Tên nhà xuất bản không được để trống");
                }
                if(diachi.isEmpty()){
                    check = false;
                    showDialogNotiFail("Địa chỉ nhà xuất bản không được để trống");
                }
                if(quocgia.isEmpty()){
                    check = false;
                    showDialogNotiFail("Quốc gia nhà xuất bản không được để trống");
                }
                if(check){
                    NhaXB obj = new NhaXB();
                    obj.setTennxb(ten);
                    obj.setDiachi(diachi);
                    obj.setQuocgia(quocgia);
                    if(daoNhaXB.insert_nhaxb(obj)>0){
                        showDialogNotiSuccess("Thêm thông tin nhà xuất bản thành công");
                        dialog.dismiss();
                        QuanLyNXBFragment fragment = new QuanLyNXBFragment();
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.content_frame, fragment)
                                .addToBackStack(null)
                                .commit();
                    }
                    else{
                        showDialogNotiFail("Thêm thông tin thất bại");
                    }
                }
            }
        });
    }
    public void showDialogNotiFail(String notit){
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
        list = v.findViewById(R.id.listviewnhaxb);
        line = v.findViewById(R.id.btnthemnxb);
    }
}