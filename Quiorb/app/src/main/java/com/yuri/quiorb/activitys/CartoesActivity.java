package com.yuri.quiorb.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.yuri.quiorb.R;
import com.yuri.quiorb.adapter.CartoesAdapter;
import com.yuri.quiorb.helpers.AndroidHelper;
import com.yuri.quiorb.helpers.FirebaseHelper;
import com.yuri.quiorb.model.CartaoModel;

import org.checkerframework.checker.interning.qual.FindDistinct;

import java.util.ArrayList;
import java.util.List;

public class CartoesActivity extends AppCompatActivity {

    private RecyclerView RV;
    private FirebaseAuth auth;
    private List<CartaoModel> cartaoList = new ArrayList<>();
    private TextView nomePagina;
    private Button addCartao;
    private DatabaseReference reference;
    private ImageButton backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cartoes);

        pegarViewsPeloID();
        configRV();
        configDados();

        AndroidHelper.fecharBtn(this,backBtn);
        AndroidHelper.configurarTopTxt(nomePagina, "CARTÕES");

        addCartao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AndroidHelper.trocarIntent(getApplicationContext(), AdicionarCartaoActivity.class);
            }
        });
    }

    public void configDados(){

        reference = FirebaseHelper.getReferenceUsuarios();

        reference.child(auth.getCurrentUser().getUid()).child("cartoes").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                cartaoList.clear();
                Log.d("TAG", "onDataChange: " + snapshot.getChildrenCount());

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Log.d("TAG", "onDataChange: " + dataSnapshot.getValue());
                    CartaoModel cartaoModel = dataSnapshot.getValue(CartaoModel.class);
                    cartaoList.add(cartaoModel);
                }
                RV.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("TAG", "onCancelled: " + error);
            }
        });


    }

    public void configRV(){
        CartoesAdapter cartoesAdapter = new CartoesAdapter(getApplicationContext(), cartaoList);
        RV.setAdapter(cartoesAdapter);
        RV.setLayoutManager(new LinearLayoutManager(this));
        RV.setHasFixedSize(true);
    }

    public void pegarViewsPeloID(){
        auth = FirebaseHelper.userGetAuth();
        RV = findViewById(R.id.RV);
        addCartao = findViewById(R.id.addCartao);
        nomePagina = findViewById(R.id.nomePagina);
        backBtn = findViewById(R.id.backBtn);
    }
}