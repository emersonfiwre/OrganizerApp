package com.emerson.organizerapp.beans;

public class Anotacao {
    private long idAnotacao;
    private String imgAnotacao;
    private String nome;


    public Anotacao(String nome) {
        this.nome = nome;
    }
    public Anotacao(String imgAnotacao, String nome) {
        this.imgAnotacao = imgAnotacao;
        this.nome = nome;
    }
    public Anotacao(){}

    public long getIdAnotacao() {
        return idAnotacao;
    }

    public void setIdAnotacao(long idAnotacao) {
        this.idAnotacao = idAnotacao;
    }

    public String getImgAnotacao() {
        return imgAnotacao;
    }

    public void setImgAnotacao(String imgAnotacao) {
        this.imgAnotacao = imgAnotacao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }



}
