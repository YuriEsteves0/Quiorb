package com.yuri.quiorb.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.yuri.quiorb.R;
import com.yuri.quiorb.helpers.AndroidHelper;

public class AdicionarCartaoActivity extends AppCompatActivity {

    private ImageButton backBtn;
    private TextView nomePagina;
    private Button addCartao;
    private EditText primeironome, ultimoNome;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_cartao);

        pegarViewsPeloId();

        addCartao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = primeironome.getText().toString();
                String sobrenome = ultimoNome.getText().toString();

                if(nome.isEmpty() || sobrenome.isEmpty()){
                    AndroidHelper.mostrarToast(getApplicationContext(), "PREENCHA TODOS OS CAMPOS!");
                }else{

                    Intent intent = new Intent(AdicionarCartaoActivity.this, EscolherCartaoActivity.class);
                    intent.putExtra("nome", nome);
                    intent.putExtra("sobrenome", sobrenome);
                    startActivity(intent);

                }
            }
        });
        AndroidHelper.fecharBtn(this,backBtn);
        AndroidHelper.configurarTopTxt(nomePagina, "ADICIONAR CARTÃO");
    }

    public void pegarViewsPeloId(){
        backBtn = findViewById(R.id.backBtn);
        ultimoNome = findViewById(R.id.ultimonome);
        primeironome = findViewById(R.id.primeironome);
        nomePagina = findViewById(R.id.nomePagina);
        addCartao = findViewById(R.id.addCartao);
    }
}