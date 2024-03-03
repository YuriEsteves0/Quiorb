package com.yuri.quiorb.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.yuri.quiorb.R;
import com.yuri.quiorb.helpers.AndroidHelper;
import com.yuri.quiorb.helpers.FirebaseHelper;

public class SplashActivity extends AppCompatActivity {

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        auth = FirebaseHelper.userGetAuth();
        if (auth.getCurrentUser() == null) {
            //NAO TEM CONTA

            AndroidHelper.trocarIntent(getApplicationContext(), CadastroActivity.class);
            Log.d("TAG", "onCreate: nao tem conta");
            finish();
        } else {
            AndroidHelper.trocarIntent(getApplicationContext(), MainActivity.class);
            finish();
            Log.d("TAG", "onCreate: tem conta");
        }
    }
}