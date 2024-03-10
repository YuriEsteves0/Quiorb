package com.yuri.quiorb.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.yuri.quiorb.R;
import com.yuri.quiorb.model.CartaoModel;

import java.util.ArrayList;
import java.util.List;

public class CartoesAdapter extends RecyclerView.Adapter<CartoesAdapter.MyViewHolder> {

    private List<CartaoModel> cartaoList = new ArrayList<>();
    private Context context;

    public CartoesAdapter(Context context, List<CartaoModel> cartaoList){
        this.cartaoList = cartaoList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cartoes_rv, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        CartaoModel cartaoModel = cartaoList.get(position);
        holder.nomeUsuario.setText(cartaoModel.getPrimeiroNome());
        holder.sobrenome.setText(cartaoModel.getSegundoNome());
        holder.numeroTelefone.setText("QUIORB CARD'S");
        holder.tipoCartao.setText(cartaoModel.getTipoCartao());
        switch(cartaoModel.getTipoCartao()){
            case "Black":
                holder.tipoCartao.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.white));
                break;
            case "Emerald":
                holder.tipoCartao.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.emerald));
                break;
            case "Ruby":
                holder.tipoCartao.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.ruby));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return cartaoList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView numeroTelefone, tipoCartao, nomeUsuario, sobrenome;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.sobrenome = itemView.findViewById(R.id.sobrenome);
            this.numeroTelefone = itemView.findViewById(R.id.numeroTelefone);
            this.tipoCartao = itemView.findViewById(R.id.tipoCartao);
            this.nomeUsuario = itemView.findViewById(R.id.nomeUsuario);
        }
    }
}
