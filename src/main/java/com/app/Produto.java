package com.app;

public class Produto {

    public String hashID;
    public String nome;
    public String descricao;
    public double valor;

    public Produto(String nome, String descricao, double valor) {
        this.nome = nome;
        this.descricao = descricao;
        this.valor = valor;
    }

}
