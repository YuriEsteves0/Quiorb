package com.yuri.quiorb.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.yuri.quiorb.R;
import com.yuri.quiorb.credentials.Confirmar_dados_activity;
import com.yuri.quiorb.helpers.AndroidHelper;
import com.yuri.quiorb.helpers.FirebaseHelper;

import java.util.concurrent.TimeUnit;

public class OtpActivity extends AppCompatActivity {

    private ImageButton backBtn;
    private String numeroTelefone, codigoverificacao;
    private ProgressBar progressbarotp;
    PhoneAuthProvider.ForceResendingToken resendingToken;
    private Long TIME_OUT_SEGUNDOS = 60L;
    private TextView otpNumero, reenvieocodigo;
    private DatabaseReference reference;
    private Button verificarOTP;
    private FirebaseAuth auth = FirebaseHelper.userGetAuth();
    private ConstraintLayout CLinv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        numeroTelefone = getIntent().getExtras().getString("telefone");

        pegarViewsPeloId();
        configurarProgressBar();
        enviarOTP(numeroTelefone, false);
        Log.d("TAG", "onCreate: " + numeroTelefone);

        verificarOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String OtpEnviado = otpNumero.getText().toString();

                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codigoverificacao, OtpEnviado);
                signIn(credential);
            }
        });

//        AndroidHelper.mostrarToast(getApplicationContext(), numeroTelefone);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void enviarOTP(String numeroTelefone, boolean isResend){
        Log.d("TAG", "enviarOTP: foi até aqui 1");
        PhoneAuthOptions.Builder builder = PhoneAuthOptions.newBuilder(auth).setPhoneNumber(numeroTelefone).setTimeout(TIME_OUT_SEGUNDOS, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        signIn(phoneAuthCredential);
                        Log.d("TAG", "enviarOTP: foi até aqui 2");
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        AndroidHelper.mostrarToast(getApplicationContext(), "Verificação de SMS falhou!");
                        Log.d("TAG", "enviarOTP: foi até aqui 3");
                        finish();
                    }

                    @Override
                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(s, forceResendingToken);

                        codigoverificacao = s;
                        resendingToken = forceResendingToken;
                        AndroidHelper.mostrarToast(getApplicationContext(), "SMS enviado com sucesso!");
                        Log.d("TAG", "enviarOTP: foi até aqui 4");
                    }
                });
        if(isResend){
            PhoneAuthProvider.verifyPhoneNumber(builder.setForceResendingToken(resendingToken).build());
            Log.d("TAG", "enviarOTP: foi até aqui 5");
        }else{
            PhoneAuthProvider.verifyPhoneNumber(builder.build());
            Log.d("TAG", "enviarOTP: foi até aqui 6" + auth.getCurrentUser());

        }

    }

    public void signIn(PhoneAuthCredential phoneAuthCredential){
        //LOGIN E IR PARA OUTRA ACTIVITY

        auth.signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    criarUsuarioDB();
                    Log.d("TAG", "onComplete: " + auth.getCurrentUser().getPhoneNumber());
                }else{
                    AndroidHelper.mostrarToast(getApplicationContext(), "Código inserido não está correto!");
                }
            }
        });
    }

    private void criarUsuarioDB(){
        String uid = auth.getCurrentUser().getUid();

        reference = FirebaseHelper.getReference().child("usuarios");

        reference.child(uid);

        Intent intent = new Intent(OtpActivity.this, Confirmar_dados_activity.class);
        intent.putExtra("numeroTelefone", auth.getCurrentUser().getPhoneNumber());
        startActivity(intent);
    }

    private void pegarViewsPeloId(){
        backBtn = findViewById(R.id.backBtn);
        verificarOTP = findViewById(R.id.verificarOTP);
        otpNumero = findViewById(R.id.otpNumero);
        reenvieocodigo = findViewById(R.id.reenvieocodigo);
    }

    private void configurarProgressBar(){


    }
}