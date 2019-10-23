package com.emerson.organizerapp.beans;

public class Mensagem {

    private long idMensagem;
    private String menssagem;
    private String data;
    private String imagem;
    private String documento;
    private long materia;

    public Mensagem(String menssagem, String data) {
        this.setMenssagem(menssagem);
        this.setData(data);
    }
    public Mensagem(String menssagem,String photo, String data) {
        this.setMenssagem(menssagem);
        this.setImagem(photo);
        this.setData(data);
    }
    public Mensagem(){}

    public String getMenssagem() {
        return menssagem;
    }

    public void setMenssagem(String menssagem) {
        this.menssagem = menssagem;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public long getIdMensagem() {
        return idMensagem;
    }

    public void setIdMensagem(long idMensagem) {
        this.idMensagem = idMensagem;
    }


    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }
    public long getMateria() {
        return materia;
    }

    public void setMateria(long materia) {
        this.materia = materia;
    }
}
