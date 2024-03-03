package com.yuri.quiorb.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.yuri.quiorb.R;
import com.yuri.quiorb.helpers.AndroidHelper;

public class OtpActivity extends AppCompatActivity {

    private ImageButton backBtn;
    private String numeroTelefone;
    private TextView otpNumero, reenvieocodigo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        pegarViewsPeloId();

        numeroTelefone = getIntent().getExtras().getString("telefone");
        AndroidHelper.mostrarToast(getApplicationContext(), numeroTelefone);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void pegarViewsPeloId(){
        backBtn = findViewById(R.id.backBtn);
        otpNumero = findViewById(R.id.otpNumero);
        reenvieocodigo = findViewById(R.id.reenvieocodigo);
    }
}