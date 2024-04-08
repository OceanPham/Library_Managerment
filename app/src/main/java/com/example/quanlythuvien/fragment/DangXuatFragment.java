package com.example.quanlythuvien.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.quanlythuvien.LoginActivity;
import com.example.quanlythuvien.MainActivity;
import com.example.quanlythuvien.R;

public class DangXuatFragment extends Fragment {
        private Dialog dialog;
        private FragmentManager fragmentManager;



        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_dang_xuat, container, false);
            showDialog();
            return view;
        }

        public void showDialog() {
            dialog = new Dialog(getContext());
            dialog.setContentView(R.layout.dangxuat_dialog);
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.custom_dialog));
            dialog.setCancelable(false);

            Button btnXacNhanDangXuat = dialog.findViewById(R.id.btnXacNhanDangXuat);
            Button btnHuyDangXuat = dialog.findViewById(R.id.btnHuyDangXuat);

            btnHuyDangXuat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Lấy ra Activity chứa Fragment
                    MainActivity mainActivity = (MainActivity) getActivity();
                    if (mainActivity != null) {
                        // Lấy tiêu đề của Fragment trước đó từ stack
                        String previousTitle = mainActivity.getPreviousFragmentTitle();

                        // Cập nhật tiêu đề của Activity với tiêu đề của Fragment trước đó
                        if (previousTitle != null) {
                            mainActivity.updateTitle(previousTitle);
                        }
                        // Quay lại Fragment trước đó trong stack
                        if (getFragmentManager() != null) {
                            getFragmentManager().popBackStack();
                        }
                    }
                    dialog.dismiss();
                }
            });





            btnXacNhanDangXuat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    getActivity().finish();
                }
            });

            dialog.show();
        }
    }

