package com.example.mobilepose.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.mobilepose.Model.API.Entities.LoginResponse;
import com.example.mobilepose.Model.API.Entities.UserAccountType;
import com.example.mobilepose.R;
import com.google.android.material.navigation.NavigationView;

public class homeView extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigation;
    LoginResponse loginResponse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.drawer_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        String jsonUserInfo = intent.getStringExtra("userinfo");
        loginResponse = Utils.FromJson(jsonUserInfo, LoginResponse.class);

        Bundle bundle = new Bundle();
        bundle.putString("loginUserInfo", jsonUserInfo);

        Home homeFragment = new Home();
        homeFragment.setArguments(bundle);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, homeFragment)
                .setReorderingAllowed(true)
                .addToBackStack("name")
                .commit();

        drawerLayout=findViewById(R.id.drawer_layout);
        navigation=findViewById(R.id.nav_view);

        if (loginResponse.accountType== UserAccountType.CASHIER){
            disableMenu();
        }



        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setLogo(R.drawable.logo);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.inflateMenu(R.menu.tool_menu);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenDrawer(drawerLayout);

            }
        });

        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                int itemId = menuItem.getItemId();
                Fragment fragment = null;

                if (itemId == R.id.nav_home) {
                    fragment = new Home();
                } else if (itemId == R.id.nav_accMng) {
                    fragment = new AccountManagement();
                } else if (itemId == R.id.nav_prodMng) {
                    fragment = new ProductManagement();
                } else if (itemId == R.id.nav_coupMng) {
                    fragment = new CouponManagement();
                } else if (itemId == R.id.nav_reports) {
                    fragment = new ReportsManagement();
                }

                if (fragment != null) {
                    fragment.setArguments(bundle); // Set the arguments
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragmentContainerView, fragment)
                            .setReorderingAllowed(true)
                            .addToBackStack("name")
                            .commit();
                }

                drawerLayout.close();
                return false;

            }
        });

    }

    private void disableMenu(){
        Menu menu = navigation.getMenu();
        MenuItem accountItem = menu.findItem(R.id.nav_accMng);
        MenuItem productItem = menu.findItem(R.id.nav_prodMng);
        MenuItem couponItem = menu.findItem(R.id.nav_coupMng);
        MenuItem reportItem = menu.findItem(R.id.nav_reports);
        accountItem.setVisible(false);
        productItem.setVisible(false);
        couponItem.setVisible(false);
        reportItem.setVisible(false);
        accountItem.setEnabled(false);
        productItem.setEnabled(false);
        couponItem.setEnabled(false);
        reportItem.setEnabled(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tool_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.logout) {
           Intent intent=new Intent(homeView.this, Login.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public static void OpenDrawer(DrawerLayout drawerLayout){
        drawerLayout.openDrawer(GravityCompat.START);
    }
    public static void CloseDrawer(DrawerLayout drawerLayout){
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        CloseDrawer(drawerLayout);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}