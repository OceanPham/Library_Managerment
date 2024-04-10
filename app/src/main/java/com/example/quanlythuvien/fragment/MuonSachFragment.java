package com.example.quanlythuvien.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.quanlythuvien.R;
import com.example.quanlythuvien.adapter.AdapterMuonSach.MuonSach;
import com.example.quanlythuvien.adapter.AdapterMuonSach.MyAdapterMuonSach;
import com.example.quanlythuvien.dao.DAOMuonSach;

import java.util.ArrayList;
import java.util.List;

public class MuonSachFragment extends Fragment {
    TextView txtsoluongdamuon, txtsoluongdangmuon, txtsoluongquahan, txttienphat;
    RecyclerView recyclerView;
    MyAdapterMuonSach adapter;
    DAOMuonSach daoMuonSach;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_muon_sach, container, false);
        anhXa(v);
        daoMuonSach = new DAOMuonSach(getContext());
        daoMuonSach = new DAOMuonSach(getContext());
        LinearLayoutManager liner = new LinearLayoutManager(v.getContext());
        recyclerView.setLayoutManager(liner);
        adapter = new MyAdapterMuonSach(v.getContext());
        adapter.setData(getData());
        recyclerView.setAdapter(adapter);
        txtsoluongdamuon.setText(daoMuonSach.getsosachdamuon(3)+"");
        txtsoluongdangmuon.setText(daoMuonSach.getsosachdangmuon(3)+"");
        txtsoluongquahan.setText(daoMuonSach.getsosachquahan(3)+"");
        txttienphat.setText((daoMuonSach.getsosachquahan(3)*10000)+"");
        return v;
    }

    private List<MuonSach> getData() {
        List<MuonSach> listmuon = new ArrayList<>();
        listmuon = daoMuonSach.getAllSachMuon(3);
        return listmuon;
    }


    private void anhXa(View v) {
        txtsoluongdamuon = v.findViewById(R.id.edtsosachdamuon);
        txtsoluongquahan = v.findViewById(R.id.edtsosachquahan);
        txttienphat = v.findViewById(R.id.edtsotienphaitra);
        txtsoluongdangmuon = v.findViewById(R.id.edtsodangmuon);
        recyclerView = v.findViewById(R.id.recycleviewsachmuon);
    }
}