package com.yuri.quiorb.credentials;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.hbb20.CountryCodePicker;
import com.yuri.quiorb.R;
import com.yuri.quiorb.activitys.MainActivity;
import com.yuri.quiorb.helpers.AndroidHelper;
import com.yuri.quiorb.helpers.FirebaseHelper;
import com.yuri.quiorb.model.UsuariosModel;

public class Cadastro_email extends AppCompatActivity {

    private EditText NomeUsuInput, emailUsuInput, senhaUsuInput, senhaUsuInput2,telefoneNumero;
    private CountryCodePicker CCP;
    private DatabaseReference reference;
    private ImageButton backBtn;
    private Button verificarDados;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_email);

        pegarViewsPeloId();

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        verificarDados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = NomeUsuInput.getText().toString();
                String email = emailUsuInput.getText().toString();
                String senha = senhaUsuInput.getText().toString();
                String senha2 = senhaUsuInput2.getText().toString();
                String telefone = telefoneNumero.getText().toString();

                if(nome.isEmpty() || email.isEmpty() || senha.isEmpty() || senha2.isEmpty() || telefone.isEmpty()){
                    AndroidHelper.mostrarToast(getApplicationContext(), "PREENCHA TODOS OS CAMPOS");
                    return;
                }

                if(senha.equals(senha2)){
                    criarUsuario(email, senha, nome, telefone, CCP);
                }else{
                    AndroidHelper.mostrarToast(getApplicationContext(), "SENHAS NAO COINCIDEM");
                }
            }
        });
    }

    public void criarUsuario(String email, String senha, String nome, String telefone, CountryCodePicker CCP){
        auth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    UsuariosModel usuariosModel = new UsuariosModel();
                    usuariosModel.setNome(nome);
                    usuariosModel.setEmail(email);
                    usuariosModel.setTelefone(CCP.getFullNumberWithPlus());
                    usuariosModel.setUid(auth.getCurrentUser().getUid());
                    usuariosModel.setValorConta(100);

                    reference = FirebaseHelper.getReferenceUsuarios();

                    reference.child(auth.getCurrentUser().getUid()).setValue(usuariosModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                AndroidHelper.trocarIntent(getApplicationContext(), MainActivity.class);
                            }else{
                                AndroidHelper.mostrarToast(getApplicationContext(), "HOUVE UM ERRO, TENTE NOVAMENTE MAIS TARDE!");
                                finish();
                            }
                        }
                    });
                }
            }
        });
    }

    public void pegarViewsPeloId(){
        telefoneNumero = findViewById(R.id.telefoneNumero);
        NomeUsuInput = findViewById(R.id.NomeUsuInput);
        emailUsuInput = findViewById(R.id.emailUsuInput);
        senhaUsuInput = findViewById(R.id.senhaUsuInput);
        senhaUsuInput2 = findViewById(R.id.senhaUsuInput2);
        CCP = findViewById(R.id.CCP);
        CCP.registerCarrierNumberEditText(telefoneNumero);
        verificarDados = findViewById(R.id.verificarDados);
        backBtn = findViewById(R.id.backBtn);
        auth = FirebaseHelper.userGetAuth();
    }
}