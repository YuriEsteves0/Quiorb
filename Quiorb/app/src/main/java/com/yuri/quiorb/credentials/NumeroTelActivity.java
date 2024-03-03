package com.yuri.quiorb.credentials;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.hbb20.CountryCodePicker;
import com.yuri.quiorb.R;
import com.yuri.quiorb.activitys.OtpActivity;
import com.yuri.quiorb.helpers.AndroidHelper;

public class NumeroTelActivity extends AppCompatActivity {

    private ImageButton backBtn;
    private Button btnProximo;
    private EditText telefoneNumero;
    private CountryCodePicker CCP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numero_tel);

        backBtn = findViewById(R.id.backBtn);
        btnProximo = findViewById(R.id.btnProximo);
        telefoneNumero = findViewById(R.id.telefoneNumero);
        CCP = findViewById(R.id.CCP);

        CCP.registerCarrierNumberEditText(telefoneNumero);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnProximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String numero = telefoneNumero.getText().toString();

                if(numero.isEmpty()){
                    telefoneNumero.setError("Este campo é obrigatório!");
                }else{
                    if(!CCP.isValidFullNumber()){
                        telefoneNumero.setError("Numero não válido");
                        return;
                    }
                    Intent intent = new Intent(NumeroTelActivity.this, OtpActivity.class);
                    intent.putExtra("telefone", CCP.getFullNumberWithPlus());
                    startActivity(intent);
                }
            }
        });
    }
}