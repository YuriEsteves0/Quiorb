package com.yuri.quiorb.model;

public class CartaoModel {
    private String primeiroNome, segundoNome, tipoCartao;
    private float mensalidade, limiteCartao;

    public CartaoModel() {
    }

    public CartaoModel(String numeroTelefone, String nomeUsuario, String tipoCartao, float valorCartao, float mensalidade) {
        this.primeiroNome = nomeUsuario;
        this.tipoCartao = tipoCartao;
        this.mensalidade = mensalidade;
    }

    public float getLimiteCartao() {
        return limiteCartao;
    }

    public void setLimiteCartao(float limiteCartao) {
        this.limiteCartao = limiteCartao;
    }

    public String getPrimeiroNome() {
        return primeiroNome;
    }

    public void setPrimeiroNome(String primeiroNome) {
        this.primeiroNome = primeiroNome;
    }

    public String getSegundoNome() {
        return segundoNome;
    }

    public void setSegundoNome(String segundoNome) {
        this.segundoNome = segundoNome;
    }

    public String getTipoCartao() {
        return tipoCartao;
    }

    public void setTipoCartao(String tipoCartao) {
        this.tipoCartao = tipoCartao;
    }

    public float getMensalidade() {
        return mensalidade;
    }

    public void setMensalidade(float mensalidade) {
        this.mensalidade = mensalidade;
    }
}
