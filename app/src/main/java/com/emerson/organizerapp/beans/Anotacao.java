package com.emerson.organizerapp.beans;

public class Anotacao {
    private long idMateria;
    private byte[] imgMateria;
    private String titulo;


    public Anotacao(String titulo) {
        setTitulo( titulo);
    }
    public Anotacao() {}

    public byte[] getImgMateria() {
        return imgMateria;
    }

    public void setImgMateria(byte[] imgMateria) {
        this.imgMateria = imgMateria;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public long getIdMateria() {
        return idMateria;
    }

    public void setIdMateria(long idMateria) {
        this.idMateria = idMateria;
    }
}
