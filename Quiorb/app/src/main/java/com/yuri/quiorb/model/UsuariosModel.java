package com.yuri.quiorb.model;

public class UsuariosModel {
    private String uid, nome, email, telefone;
    private float valorConta;
    private int qntPntsQuiorb;

    public UsuariosModel(String uid, String nome, String email, String telefone) {
        this.uid = uid;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.valorConta = 100;
        this.qntPntsQuiorb = 0;
    }

    public UsuariosModel() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public float getValorConta() {
        return valorConta;
    }

    public void setValorConta(float valorConta) {
        this.valorConta = valorConta;
    }

    public int getQntPntsQuiorb() {
        return qntPntsQuiorb;
    }

    public void setQntPntsQuiorb(int qntPntsQuiorb) {
        this.qntPntsQuiorb = qntPntsQuiorb;
    }
}
