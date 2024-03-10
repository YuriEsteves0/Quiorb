package com.yuri.quiorb.credentials;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.yuri.quiorb.R;
import com.yuri.quiorb.activitys.MainActivity;
import com.yuri.quiorb.helpers.AndroidHelper;
import com.yuri.quiorb.helpers.FirebaseHelper;
import com.yuri.quiorb.model.UsuariosModel;

public class Confirmar_dados_activity extends AppCompatActivity {
    private EditText NomeUsuInput, emailUsuInput, senhaUsuInput, senhaUsuInput2;
    private String telefone;
    private Button verificarDados;
    private FirebaseAuth auth;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmar_dados);

        pegarViewsPorId();
        pegarDadosIntent();

        verificarDados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference = FirebaseHelper.getReference().child("usuarios").child(auth.getCurrentUser().getUid());

                String nome = NomeUsuInput.getText().toString();
                String uid = auth.getCurrentUser().getUid();
                String email = emailUsuInput.getText().toString();
                String senha = senhaUsuInput.getText().toString();
                String senha2 = senhaUsuInput2.getText().toString();

                if(senha.equals(senha2)){
                    UsuariosModel usuariosModel = new UsuariosModel();
                    usuariosModel.setNome(nome);
                    usuariosModel.setEmail(email);
                    usuariosModel.setUid(uid);
                    usuariosModel.setTelefone(telefone);

                    reference.setValue(usuariosModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                AndroidHelper.trocarIntent(getApplicationContext(), MainActivity.class);
                                finish();
                            }
                        }
                    });
                }
            }
        });
    }

    private void pegarViewsPorId(){
        NomeUsuInput = findViewById(R.id.NomeUsuInput);
        emailUsuInput = findViewById(R.id.emailUsuInput);
        senhaUsuInput = findViewById(R.id.senhaUsuInput);
        senhaUsuInput2 = findViewById(R.id.senhaUsuInput2);
        verificarDados = findViewById(R.id.verificarDados);
        auth = FirebaseHelper.userGetAuth();
    }

    private void pegarDadosIntent(){
        Intent intent = getIntent();
        telefone = intent.getStringExtra("numeroTelefone");
    }
}