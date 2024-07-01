package com.example.mobilepose.Controller;

import android.content.Intent;
import android.os.Bundle;
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
import androidx.fragment.app.FragmentManager;

import com.example.mobilepose.AccountManagement;
import com.example.mobilepose.CouponManagement;
import com.example.mobilepose.Model.API.Entities.LoginResponse;
import com.example.mobilepose.Model.User;
import com.example.mobilepose.ProductManagement;
import com.example.mobilepose.R;
import com.example.mobilepose.ReportsManagement;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONException;
import org.json.JSONObject;

public class homeView extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigation;


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

        LoginResponse loginResponse = Utils.FromJson(jsonUserInfo, LoginResponse.class);;



        FragmentManager fragmentmanger=getSupportFragmentManager();
        fragmentmanger.beginTransaction()
                .replace(R.id.fragmentContainerView, Home.class,null)
                .setReorderingAllowed(true)
                .addToBackStack("name")
                .commit();

        drawerLayout=findViewById(R.id.drawer_layout);
        navigation=findViewById(R.id.nav_view);



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
                int itemId=menuItem.getItemId();

                if (itemId== R.id.nav_home){
                    FragmentManager fragmentmanger=getSupportFragmentManager();
                    fragmentmanger.beginTransaction()
                            .replace(R.id.fragmentContainerView, Home.class,null)
                            .setReorderingAllowed(true)
                            .addToBackStack("name")
                            .commit();
                }
                if (itemId== R.id.nav_accMng) {
                    FragmentManager fragmentmanger=getSupportFragmentManager();
                    fragmentmanger.beginTransaction()
                            .replace(R.id.fragmentContainerView, AccountManagement.class,null)
                            .setReorderingAllowed(true)
                            .addToBackStack("name")
                            .commit();

                }

                if (itemId== R.id.nav_prodMng) {
                    FragmentManager fragmentmanger=getSupportFragmentManager();
                    fragmentmanger.beginTransaction()
                            .replace(R.id.fragmentContainerView, ProductManagement.class,null)
                            .setReorderingAllowed(true)
                            .addToBackStack("name")
                            .commit();


                }
                if (itemId== R.id.nav_coupMng) {
                    FragmentManager fragmentmanger=getSupportFragmentManager();
                    fragmentmanger.beginTransaction()
                            .replace(R.id.fragmentContainerView, CouponManagement.class,null)
                            .setReorderingAllowed(true)
                            .addToBackStack("name")
                            .commit();

                }

                if (itemId== R.id.nav_reports) {
                    FragmentManager fragmentmanger=getSupportFragmentManager();
                    fragmentmanger.beginTransaction()
                            .replace(R.id.fragmentContainerView, ReportsManagement.class,null)
                            .setReorderingAllowed(true)
                            .addToBackStack("name")
                            .commit();

                }
                drawerLayout.close();
                return  false;
            }
        });

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

}