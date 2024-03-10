package com.yuri.quiorb.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.yuri.quiorb.R;
import com.yuri.quiorb.fragments.ContaFragment;
import com.yuri.quiorb.fragments.HomeFragment;
import com.yuri.quiorb.helpers.AndroidHelper;
import com.yuri.quiorb.helpers.FirebaseHelper;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {


    HomeFragment homeFragment = new HomeFragment();
    ContaFragment contaFragment = new ContaFragment();

    private BottomNavigationView bottomNav;

    private String uidUsu = FirebaseHelper.getUID();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pegarIdsPelaView();

        if (bottomNav != null) {
            bottomNavbtns();
        } else {
            Log.e("MainActivity", "bottomNav is null");
        }

    }

    public void bottomNavbtns(){
        getSupportFragmentManager().beginTransaction().replace(R.id.contianerFrameLayout, homeFragment).commit();

        bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if(id == R.id.Home){
                    item.setEnabled(true);
                    getSupportFragmentManager().beginTransaction().replace(R.id.contianerFrameLayout, homeFragment).commit();
                }
                if(id == R.id.Conta){
                    item.setEnabled(true);
                    getSupportFragmentManager().beginTransaction().replace(R.id.contianerFrameLayout, contaFragment).commit();
                }
                return false;
            }
        });
    }

    public void pegarIdsPelaView(){

        bottomNav = findViewById(R.id.bottomNav);
    }
}