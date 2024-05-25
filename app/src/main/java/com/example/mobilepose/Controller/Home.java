package com.example.mobilepose.Controller;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mobilepose.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Home extends AppCompatActivity {

    private FloatingActionButton cartBtn;
    ConstraintLayout cons;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        cartBtn=findViewById(R.id.shopCartBtn);
        cons=findViewById(R.id.main);
        cartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater= (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                View overlay = findViewById(R.id.overlay);
                View popup=inflater.inflate(R.layout.shopping_cart_popup, null);

                DisplayMetrics displayMetrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

                PopupWindow popupWindow=new PopupWindow(popup,displayMetrics.widthPixels / 2, ViewGroup.LayoutParams.MATCH_PARENT,true);
                popupWindow.setOnDismissListener(() -> overlay.setVisibility(View.GONE));
                cons.post(new Runnable() {
                    @Override
                    public void run() {
                        popupWindow.showAtLocation(cons, Gravity.RIGHT,0,0);
                        overlay.setVisibility(View.VISIBLE);
                    }
                });

            }
        });
    }
}