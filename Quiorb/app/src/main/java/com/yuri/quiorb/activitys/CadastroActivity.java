package com.yuri.quiorb.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.yuri.quiorb.R;
import com.yuri.quiorb.credentials.NumeroTelActivity;
import com.yuri.quiorb.helpers.AndroidHelper;
import com.yuri.quiorb.helpers.FirebaseHelper;

public class CadastroActivity extends AppCompatActivity {

    private LinearLayout btnEntrarNumero, btnEntrarGmail, btnEntrarFacebook;
    private FirebaseAuth auth;
    private GoogleSignInClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        btnEntrarNumero = findViewById(R.id.btnEntrarNumero);
        btnEntrarGmail = findViewById(R.id.btnEntrarGmail);
        btnEntrarFacebook = findViewById(R.id.btnEntrarFacebook);
        auth = FirebaseHelper.userGetAuth();

        btnEntrarNumero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AndroidHelper.trocarIntent(getApplicationContext(), NumeroTelActivity.class);
            }
        });

        btnEntrarGmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AndroidHelper.mostrarToast(getApplicationContext(), "Adicionaremos essa função no futuro!");
            }
        });

        btnEntrarFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AndroidHelper.mostrarToast(getApplicationContext(), "Adicionaremos essa função no futuro!");
            }
        });
    }
}