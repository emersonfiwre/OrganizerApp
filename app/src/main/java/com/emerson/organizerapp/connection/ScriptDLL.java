package com.emerson.organizerapp.connection;


public class ScriptDLL {

    public static String getCreateTableAnotacao(){

        StringBuilder sql = new StringBuilder();

        sql.append("CREATE TABLE IF NOT EXISTS ANOTACAO (");
        sql.append(     "ID_ANOTACAO INTEGER  PRIMARY KEY AUTOINCREMENT NOT NULL,");
        sql.append(     "IMAGE BLOB, ");
        sql.append(     "NOME VARCHAR(25) NOT NULL DEFAULT ('') ); ");

        return  sql.toString();

    }
    public static String getCreateTableMensagem(){

        StringBuilder sql = new StringBuilder();

        sql.append("CREATE TABLE IF NOT EXISTS MENSAGEM ( ");
        sql.append(     "ID_MENSAGEM INTEGER  PRIMARY KEY AUTOINCREMENT NOT NULL,");
        sql.append(     "TEXTO TEXT DEFAULT (''), ");
        sql.append(     "IMAGEM VARCHAR(200) DEFAULT (''), ");
        sql.append(     "DOCUMENTO VARCHAR(200) DEFAULT (''), ");
        sql.append(     "DATA_ENVIO VARCHAR (10) NOT NULL DEFAULT (''), ");
        sql.append(     "FK_ID_ANOTACAO INTEGER,");
        sql.append(     "FOREIGN KEY(FK_ID_ANOTACAO) REFERENCES MATERIA(ID_ANOTACAO) );");

        return  sql.toString();

    }

}
