package com.yuri.quiorb.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.yuri.quiorb.R;
import com.yuri.quiorb.helpers.FirebaseHelper;

public class ContaFragment extends Fragment {
    private Button sairBtn;
    private FirebaseAuth auth;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_conta, container, false);

        pegarViewPeloId(view);

        sairBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sair();
            }
        });
        return view;
    }

    public void sair( ){
        auth.signOut();
        getActivity().finish();
    }

    public void pegarViewPeloId(View view){
        sairBtn = view.findViewById(R.id.sairBtn);
        auth = FirebaseHelper.userGetAuth();
    }
}