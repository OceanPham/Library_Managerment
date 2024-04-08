package com.example.quanlythuvien;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.os.BatteryManager;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.quanlythuvien.database.DatabaseSingleton;
import com.example.quanlythuvien.fragment.DangXuatFragment;
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

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {
    private static final int FRAGMENT_HOME = 0;
    private static final int FRAGMENT_XEMTHONGTIN = 1;
    private static final int FRAGMENT_THONGKE = 2;
    private static final int FRAGMENT_QLTG= 3;
    private static final int FRAGMENT_QLNXB = 4;
    private static final int FRAGMENT_VEUNGDUNG = 6;
    private static final int FRAGMENT_QUYDINH= 5;
    private static final int FRAGMENT_DOIMATKHAU = 7;
    private static final int FRAGMENT_DOITTCANHAN = 8;
    private static final int FRAGMENT_DANGXUAT = 9;
    private int currentFragment = FRAGMENT_HOME;
    private DrawerLayout drawer;
    SQLiteDatabase sqLiteDatabase = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sqLiteDatabase = openOrCreateDatabase("library_manager.db", MODE_PRIVATE, null);
        // Khởi tạo singleton database
        DatabaseSingleton.initialize(sqLiteDatabase);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer, toolbar,R.string.navigation_drawer_open, R.string.navigation_drawer_close );
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
        replaceFragment(new HomeFragment(), "Z-Library");
        navigationView.getMenu().findItem(R.id.nav_Home).setChecked(true);

        checkBatteryLevel();
    }
    private void checkBatteryLevel() {
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = registerReceiver(null, ifilter);
        if (batteryStatus != null) {
            int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
            float batteryPct = level / (float) scale * 100;

            if (batteryPct < 20) {
                // Hiển thị thông báo "Pin yếu!"
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Pin yếu!");
                builder.setMessage("Pin của bạn đang yếu. Vui lòng sạc pin để tiếp tục sử dụng.");
                builder.setIcon(R.drawable.baseline_battery_alert_24);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setCancelable(false);
                builder.show();
            }
        }
    }
    private String currentFragmentTitle = "Z-Library"; // Tiêu đề mặc định

    public void updateTitle(String title) {
        getSupportActionBar().setTitle(title);
        currentFragmentTitle = title;
    }

    public String getPreviousFragmentTitle() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            int index = getSupportFragmentManager().getBackStackEntryCount() - 2;
            FragmentManager.BackStackEntry backStackEntry = getSupportFragmentManager().getBackStackEntryAt(index);
            return backStackEntry.getName();
        }
        return null;
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.nav_Home){
            if(currentFragment!=FRAGMENT_HOME){
                replaceFragment(new HomeFragment(), "Z-Library");
                currentFragment = FRAGMENT_HOME;
            }
        }else if(id == R.id.nav_XemThongTin){
            if(currentFragment!=FRAGMENT_XEMTHONGTIN){
                replaceFragment(new XemThongTinFragment(), "Xem thông tin chi tiết");
                currentFragment = FRAGMENT_XEMTHONGTIN;
            }
        }else if(id == R.id.nav_ThongKe){
            if(currentFragment!=FRAGMENT_THONGKE){
                replaceFragment(new ThongKeFragment(), "Thống kê");
                currentFragment = FRAGMENT_THONGKE;
            }
        }else if(id == R.id.nav_NhaXuatBan){
            if(currentFragment!=FRAGMENT_QLNXB){
                replaceFragment(new QuanLyNXBFragment(), "Quản lý nhà xuất bản");
                currentFragment = FRAGMENT_QLNXB;
            }
        }else if(id == R.id.nav_TacGia){
            if(currentFragment!=FRAGMENT_QLTG){
                replaceFragment(new QuanLyTacGiaFragment(), "Quản lý tác giả");
                currentFragment = FRAGMENT_QLTG;
            }
        }else if(id == R.id.nav_QuyDinh){
            if(currentFragment!=FRAGMENT_QUYDINH){
                replaceFragment(new QuyDinhFragment(), "Quy định thư viên");
                currentFragment = FRAGMENT_QUYDINH;
            }
        }else if(id == R.id.nav_VeUngDung){
            if(currentFragment!=FRAGMENT_VEUNGDUNG){
                replaceFragment(new VeUngDungFragment(), "Thông tin về ứng dụng");
                currentFragment = FRAGMENT_VEUNGDUNG;
            }
        }else if(id == R.id.nav_ChangePass){
            if(currentFragment!=FRAGMENT_DOIMATKHAU){
                replaceFragment(new DoiMatKhauFragment(), "Đổi mật khẩu");
                currentFragment = FRAGMENT_DOIMATKHAU;
            }
        }else if(id == R.id.nav_DoiThongTinTaiKhoan){
            if(currentFragment!=FRAGMENT_DOITTCANHAN){
                replaceFragment(new DoiThongTinCaNhanFragment(), "Đổi thông tin cá nhân");
                currentFragment = FRAGMENT_DOITTCANHAN;
            }
        }
        else if(id == R.id.DangXuat){
            if(currentFragment!=FRAGMENT_DANGXUAT){
                replaceFragment(new DangXuatFragment(), "Đăng xuất");
                currentFragment = FRAGMENT_DANGXUAT;
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

private void replaceFragment(Fragment fragment, String title) {
    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
    transaction.replace(R.id.content_frame, fragment);
    transaction.addToBackStack(title); // Thêm fragment vào back stack
    transaction.commit();
    updateTitle(title);
}


}

