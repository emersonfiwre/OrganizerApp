package com.emerson.organizerapp.connection;


public class ScriptDLL {

    public static String getCreateTableAnotacao(){

        StringBuilder sql = new StringBuilder();

        sql.append("CREATE TABLE IF NOT EXISTS ANOTACAO (");
        sql.append(     "ID_ANOTACAO INTEGER  PRIMARY KEY AUTOINCREMENT NOT NULL,");
        sql.append(     "IMAGEM VARCHAR(200) DEFAULT (''), ");
        sql.append(     "NOME VARCHAR(25) NOT NULL DEFAULT ('') ); ");

        return  sql.toString();

    }
    public static String getCreateTableAuxData(){

        StringBuilder sql = new StringBuilder();

        sql.append("CREATE TABLE IF NOT EXISTS AUX_DATA ( ");
        sql.append(     "ID_DATA INTEGER  PRIMARY KEY AUTOINCREMENT NOT NULL,");
        sql.append(     "DATA_ENVIO VARCHAR(10) NOT NULL,");
        sql.append(     "FK_ID_ANOTACAO INTEGER,");
        sql.append(     "FOREIGN KEY(FK_ID_ANOTACAO) REFERENCES ANOTACAO(ID_ANOTACAO) );");

        return  sql.toString();

    }
    public static String getCreateTableMensagem(){

        StringBuilder sql = new StringBuilder();

        sql.append("CREATE TABLE IF NOT EXISTS MENSAGEM ( ");
        sql.append(     "ID_MENSAGEM INTEGER  PRIMARY KEY AUTOINCREMENT NOT NULL,");
        sql.append(     "TEXTO TEXT DEFAULT (''), ");
        sql.append(     "IMAGEM VARCHAR(200) DEFAULT (''), ");
        sql.append(     "DOCUMENTO VARCHAR(200) DEFAULT (''), ");
        sql.append(     "HORA_ENVIO VARCHAR (10) NOT NULL DEFAULT (''), ");
        sql.append(     "FK_ID_DATA INTEGER,");
        sql.append(     "FOREIGN KEY(FK_ID_DATA) REFERENCES AUX_DATA(ID_DATA) );");

        return  sql.toString();

    }

}
