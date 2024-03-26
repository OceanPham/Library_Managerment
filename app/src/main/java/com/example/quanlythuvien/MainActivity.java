package com.example.quanlythuvien;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.quanlythuvien.fragment.DoiMatKhauFragment;
import com.example.quanlythuvien.fragment.DoiThongTinCaNhanFragment;
import com.example.quanlythuvien.fragment.HomeFragment;
import com.example.quanlythuvien.fragment.QuanLyNXBFragment;
import com.example.quanlythuvien.fragment.QuanLyTacGiaFragment;
import com.example.quanlythuvien.fragment.QuyDinhFragment;
import com.example.quanlythuvien.fragment.ThongKeFragment;
import com.example.quanlythuvien.fragment.VeUngDungFragment;
import com.example.quanlythuvien.fragment.XemThongTinFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final int FRAGMENT_HOME = 0;
    private static final int FRAGMENT_XEMTHONGTIN = 1;
    private static final int FRAGMENT_THONGKE = 2;
    private static final int FRAGMENT_QLTG= 3;
    private static final int FRAGMENT_QLNXB = 4;
    private static final int FRAGMENT_VEUNGDUNG = 6;
    private static final int FRAGMENT_QUYDINH= 5;
    private static final int FRAGMENT_DOIMATKHAU = 7;
    private static final int FRAGMENT_DOITTCANHAN = 8;
    private int currentFragment = FRAGMENT_HOME;
    private DrawerLayout drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer, toolbar,R.string.navigation_drawer_open, R.string.navigation_drawer_close );
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
        replaceFragment(new HomeFragment());
        setTitle("Z-Library");
        navigationView.getMenu().findItem(R.id.nav_Home).setChecked(true);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.nav_Home){
            if(currentFragment!=FRAGMENT_HOME){
                replaceFragment(new HomeFragment());
                setTitle("Z-Library");
                currentFragment = FRAGMENT_HOME;
            }
        }else if(id == R.id.nav_XemThongTin){
            if(currentFragment!=FRAGMENT_XEMTHONGTIN){
                replaceFragment(new XemThongTinFragment());
                setTitle("Xem thông tin chi tiết");
                currentFragment = FRAGMENT_XEMTHONGTIN;
            }
        }else if(id == R.id.nav_ThongKe){
            if(currentFragment!=FRAGMENT_THONGKE){
                replaceFragment(new ThongKeFragment());
                setTitle("Thống kê");
                currentFragment = FRAGMENT_THONGKE;
            }
        }else if(id == R.id.nav_NhaXuatBan){
            if(currentFragment!=FRAGMENT_QLNXB){
                replaceFragment(new QuanLyNXBFragment());
                setTitle("Quản lý nhà xuất bản");
                currentFragment = FRAGMENT_QLNXB;
            }
        }else if(id == R.id.nav_TacGia){
            if(currentFragment!=FRAGMENT_QLTG){
                replaceFragment(new QuanLyTacGiaFragment());
                setTitle("Quản lý tác giả");
                currentFragment = FRAGMENT_QLTG;
            }
        }else if(id == R.id.nav_QuyDinh){
            if(currentFragment!=FRAGMENT_QUYDINH){
                replaceFragment(new QuyDinhFragment());
                setTitle("Quy định thư viên");
                currentFragment = FRAGMENT_QUYDINH;
            }
        }else if(id == R.id.nav_VeUngDung){
            if(currentFragment!=FRAGMENT_VEUNGDUNG){
                replaceFragment(new VeUngDungFragment());
                setTitle("Thông tin về ứng dụng");
                currentFragment = FRAGMENT_VEUNGDUNG;
            }
        }else if(id == R.id.nav_ChangePass){
            if(currentFragment!=FRAGMENT_DOIMATKHAU){
                replaceFragment(new DoiMatKhauFragment());
                setTitle("Đổi mật khẩu");
                currentFragment = FRAGMENT_DOIMATKHAU;
            }
        }else if(id == R.id.nav_DoiThongTinTaiKhoan){
            if(currentFragment!=FRAGMENT_DOITTCANHAN){
                replaceFragment(new DoiThongTinCaNhanFragment());
                setTitle("Đổi thông tin cá nhân");
                currentFragment = FRAGMENT_DOITTCANHAN;
            }
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
    }

    private void replaceFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, fragment);
        transaction.commit();
    }
}