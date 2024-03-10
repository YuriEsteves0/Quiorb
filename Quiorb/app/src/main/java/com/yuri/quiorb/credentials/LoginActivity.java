package com.yuri.quiorb.credentials;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.yuri.quiorb.R;
import com.yuri.quiorb.activitys.MainActivity;
import com.yuri.quiorb.helpers.AndroidHelper;
import com.yuri.quiorb.helpers.FirebaseHelper;

public class LoginActivity extends AppCompatActivity {

    private EditText emailUsuInput, senhaUsuInput;
    private Button verificarDados;
    private ConstraintLayout content;
    private ProgressBar progressBar;
    private ImageButton backBtn;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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
                String email = emailUsuInput.getText().toString();
                String senha = senhaUsuInput.getText().toString();
                fazerLogin(email, senha);
            }
        });
    }

    public void fazerLogin(String email, String senha){

        content.setVisibility(View.GONE);

        new CountDownTimer(3000, 1000){
            @Override
            public void onTick(long millisUntilFinished) {
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setAnimation(AnimationUtils.loadAnimation(LoginActivity.this, R.anim.fade_in));
            }

            @Override
            public void onFinish() {
                auth.signInWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            AndroidHelper.trocarIntent(getApplicationContext(), MainActivity.class);
                            finish();
                        }else{
                            AndroidHelper.mostrarToast(getApplicationContext(), "CONTA NAO EXISTENTE, CRIE SUA CONTA!");
                        }
                    }
                });
            }
        }.start();

    }

    public void pegarViewsPeloId(){
        backBtn = findViewById(R.id.backBtn);
        content = findViewById(R.id.content);
        senhaUsuInput = findViewById(R.id.senhaUsuInput);
        emailUsuInput = findViewById(R.id.emailUsuInput);
        progressBar = findViewById(R.id.progressBar);
        verificarDados = findViewById(R.id.verificarDados);
        auth = FirebaseHelper.userGetAuth();
    }
}