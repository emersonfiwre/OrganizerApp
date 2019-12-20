package com.emerson.organizerapp.beans;

import java.util.List;

public class AuxData {

    private int idData;
    private String dataEnvio;
    private long fkAnotacao;
    private List<Mensagem> mensagemList;



    public AuxData(String dataEnvio, long fkAnotacao) {
        this.dataEnvio = dataEnvio;
        this.fkAnotacao = fkAnotacao;
    }
    public AuxData(){}

    public String getDataEnvio() {
        return dataEnvio;
    }

    public void setDataEnvio(String dataEnvio) {
        this.dataEnvio = dataEnvio;
    }

    public long getFkAnotacao() {
        return fkAnotacao;
    }

    public void setFkAnotacao(long fkAnotacao) {
        this.fkAnotacao = fkAnotacao;
    }

    public List<Mensagem> getMensagemList() {
        return mensagemList;
    }

    public void setMensagemList(List<Mensagem> mensagemList) {
        this.mensagemList = mensagemList;
    }
    public void setMensagemList(Mensagem mensagem) {
        this.mensagemList.add(mensagem);
    }


    public int getIdData() {
        return idData;
    }

    public void setIdData(int idData) {
        this.idData = idData;
    }
}
