package com.yuri.quiorb.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.yuri.quiorb.R;
import com.yuri.quiorb.activitys.CartoesActivity;
import com.yuri.quiorb.helpers.AndroidHelper;
import com.yuri.quiorb.helpers.FirebaseHelper;

import java.text.NumberFormat;

public class HomeFragment extends Fragment {

    private TextView dinheiroTxt, cartoesTxt;
    private ImageButton viewMoney, cartaoimg;
    private boolean isEscondido = true;
    private LinearLayout cartoesBtn;
    private DatabaseReference reference;
    private FirebaseAuth auth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        pegarViewsPeloId(view);
        verifEscondidoDinheiro();

        cartoesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trocarParaIntentCartoes();
            }
        });

        cartaoimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trocarParaIntentCartoes();
            }
        });

        cartoesTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trocarParaIntentCartoes();
            }
        });

        viewMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifEscondidoDinheiro();
                Log.d("TAG", "onClick: " + isEscondido);
                isEscondido = !isEscondido;
            }
        });

        return view;
    }

    public void trocarParaIntentCartoes(){
        AndroidHelper.trocarIntent(getContext(), CartoesActivity.class);
    }

    public void verifEscondidoDinheiro(){
        if(isEscondido){
            dinheiroTxt.setText("*****");
        } else {
            reference.child(auth.getCurrentUser().getUid()).child("valorConta").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Long qntDinheiro = snapshot.getValue(Long.class);
                    if (qntDinheiro != null) {
                        // Formatar o valor monetário
                        String formattedDinheiro = formatarDinheiro(qntDinheiro);
                        dinheiroTxt.setText(formattedDinheiro);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.d("TAG", "onCancelled: " + error);

                }
            });
        }
    }

    // Método para formatar o valor do dinheiro
    private String formatarDinheiro(Long valor) {
        Log.d("TAGmONEY", "formatarDinheiro: " + valor.longValue());
        NumberFormat format = NumberFormat.getCurrencyInstance();
        String valorFormatado = format.format(valor);
        return valorFormatado;
    }

    private void pegarViewsPeloId(View view){
        dinheiroTxt = view.findViewById(R.id.dinheiroTxt);
        viewMoney = view.findViewById(R.id.viewMoney);
        reference = FirebaseHelper.getReferenceUsuarios();
        cartaoimg = view.findViewById(R.id.cartaoimg);
        cartoesTxt = view.findViewById(R.id.cartoesTxt);
        auth = FirebaseHelper.userGetAuth();
        cartoesBtn = view.findViewById(R.id.cartoesBtn);
    }

}