package com.yuri.quiorb.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.yuri.quiorb.R;
import com.yuri.quiorb.helpers.AndroidHelper;
import com.yuri.quiorb.helpers.FirebaseHelper;
import com.yuri.quiorb.model.CartaoModel;

public class EscolherCartaoActivity extends AppCompatActivity {

    private TextView topTxt;
    private ImageButton backBtn;
    private String cartaoSelecionado = "", nome, sobrenome, telefone;
    private Button addCartao;
    private FirebaseAuth auth = FirebaseHelper.userGetAuth();
    private DatabaseReference reference;
    private RelativeLayout cartaoRuby, cartaoBlack, cartaoEmerald;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escolher_cartao);

        pegarViewPeloId();
        pegarDadosPorIntent();

        selecionarBtn();

        addCartao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Inflar o layout do popup personalizado
                View popupView = getLayoutInflater().inflate(R.layout.popup_layout, null);

                // Encontrar as referências dos botões dentro do layout do popup
                Button btnNao = popupView.findViewById(R.id.btnNao);
                Button btnSim = popupView.findViewById(R.id.btnSim);

                // Criar o AlertDialog
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(EscolherCartaoActivity.this);
                alertDialogBuilder.setView(popupView);

                // Criar e exibir o AlertDialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

                // Definir os eventos de clique para os botões do popup
                btnNao.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Ação ao clicar no botão "Não" do popup
                        alertDialog.dismiss(); // Fechar o popup
                        // Adicione qualquer ação adicional aqui, se necessário
                    }
                });

                btnSim.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        alertDialog.dismiss(); // Fechar o popup

                        reference = FirebaseHelper.getReferenceUsuarios();
                        reference.child(auth.getCurrentUser().getUid()).child("valorConta").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.exists()){
                                    Long valorConta = snapshot.getValue(Long.class);

                                    Long formula = valorConta - 25;
                                    Log.d("TAG", "onDataChange: " + formula);
                                    if(formula <= 0){
                                        AndroidHelper.mostrarToast(getApplicationContext(), "SALDO INSUFICIENTE");

                                    }else{
                                        reference.child(auth.getCurrentUser().getUid()).child("valorConta").setValue(formula).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    fazerCartao();
                                                }
                                            }
                                        });
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                });
            }
        });

        AndroidHelper.configurarTopTxt(topTxt, "ESCOLHA SEU CARTÃO");
        AndroidHelper.fecharBtn(this, backBtn);
    }

    public void fazerCartao(){
        String cartaoUid = cartaoSelecionado + "_" + reference.push().getKey();

        CartaoModel cartaoModel = new CartaoModel();
        cartaoModel.setPrimeiroNome(nome);
        cartaoModel.setSegundoNome(sobrenome);
        switch (cartaoSelecionado){
            case "Black":
                cartaoModel.setMensalidade(1000);
                cartaoModel.setLimiteCartao(7000);
                break;
            case "Emerald":
                cartaoModel.setMensalidade(500);
                cartaoModel.setLimiteCartao(5000);
                break;
            case "Ruby":
                cartaoModel.setMensalidade(250);
                cartaoModel.setLimiteCartao(2500);
                break;
        }
        cartaoModel.setTipoCartao(cartaoSelecionado);

        reference = FirebaseHelper.getReferenceUsuarios().child(auth.getCurrentUser().getUid()).child("cartoes").child(cartaoUid);
        reference.setValue(cartaoModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    AndroidHelper.trocarIntent(getApplicationContext(), MainActivity.class);
                    AndroidHelper.mostrarToast(getApplicationContext(), "Compra efetuada com sucesso!");
                    finish();
                }else{
                    Log.d("TAG", "onComplete: ERROR CARTAO");
                }
            }
        });
    }

    public void pegarDadosPorIntent(){
        Intent intent = getIntent();
        nome = intent.getStringExtra("nome");
        sobrenome = intent.getStringExtra("sobrenome");
    }

    public void selecionarBtn(){
        cartaoBlack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartaoSelecionado = "Black";
                cartaoBlack.setBackgroundResource(R.drawable.btn_style_border_15);
                cartaoBlack.setBackgroundTintList(null);

                cartaoEmerald.setBackgroundResource(R.drawable.rounded_background_white_15);
                cartaoEmerald.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));

                cartaoRuby.setBackgroundResource(R.drawable.rounded_background_white_15);
                cartaoRuby.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
            }
        });

        cartaoEmerald.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartaoSelecionado = "Emerald";
                cartaoEmerald.setBackgroundResource(R.drawable.btn_style_border_15);
                cartaoEmerald.setBackgroundTintList(null);

                cartaoBlack.setBackgroundResource(R.drawable.rounded_background_white_15);
                cartaoBlack.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));

                cartaoRuby.setBackgroundResource(R.drawable.rounded_background_white_15);
                cartaoRuby.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
            }
        });

        cartaoRuby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartaoSelecionado = "Ruby";
                cartaoRuby.setBackgroundResource(R.drawable.btn_style_border_15);
                cartaoRuby.setBackgroundTintList(null);

                cartaoBlack.setBackgroundResource(R.drawable.rounded_background_white_15);
                cartaoBlack.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));

                cartaoEmerald.setBackgroundResource(R.drawable.rounded_background_white_15);
                cartaoEmerald.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
            }
        });
    }

    public void pegarViewPeloId(){
        cartaoRuby = findViewById(R.id.cartaoRuby);
        cartaoEmerald = findViewById(R.id.cartaoEmerald);
        addCartao = findViewById(R.id.addCartao);
        cartaoBlack = findViewById(R.id.cartaoBlack);
        topTxt = findViewById(R.id.nomePagina);
        backBtn = findViewById(R.id.backBtn);
    }
}