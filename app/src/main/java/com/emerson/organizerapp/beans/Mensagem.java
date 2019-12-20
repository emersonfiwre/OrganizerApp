package com.emerson.organizerapp.beans;

public class Mensagem {

    private long idMensagem;
    private String texto;
    private String hora;
    private String imagem;
    private String documento;
    private long idData;

    public Mensagem(String menssagem) {
        this.texto = menssagem;
    }
    public Mensagem(String menssagem,String imagem, String hora) {
        this.setTexto(menssagem);
        this.imagem = imagem;
        this.hora = hora;
    }
    public Mensagem(){}

    public long getIdMensagem() {
        return idMensagem;
    }

    public void setIdMensagem(long idMensagem) {
        this.idMensagem = idMensagem;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String menssagem) {
        this.texto = menssagem;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
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

    public long getIdData() {
        return idData;
    }

    public void setIdData(long idData) {
        this.idData = idData;
    }
}
