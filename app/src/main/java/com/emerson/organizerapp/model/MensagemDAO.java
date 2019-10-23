package com.emerson.organizerapp.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.emerson.organizerapp.beans.Mensagem;

import java.util.ArrayList;
import java.util.List;


public class MensagemDAO {
    private SQLiteDatabase conexao;

    public MensagemDAO(SQLiteDatabase conexao){
        this.conexao = conexao;
    }

    public long inserir(Mensagem mensagem){
        long resultado;
        ContentValues contentValues = new ContentValues();
        contentValues.put("TEXTO",mensagem.getMenssagem());
        contentValues.put("IMAGEM",mensagem.getImagem());
        contentValues.put("DOCUMENTO",mensagem.getDocumento());
        contentValues.put("DATA_ENVIO",mensagem.getData());
        contentValues.put("FK_ID_MATERIA",mensagem.getMateria());

        resultado = conexao.insertOrThrow("MENSAGEM",null,contentValues);
        Log.i("LOG","inserirMensagem(): " + resultado);
        return resultado;
    }
    public void delete(long codigo){
        String[] parametros = new String[1];
        parametros[0] = String.valueOf(codigo);

        conexao.delete("MENSAGEM","ID_MENSAGEM = ?", parametros);
        Log.i("LOG","deleteMensagem(): " + codigo);

    }

    public List<Mensagem> buscarTodos(long materia){

        List<Mensagem> mensagens = new ArrayList<>();

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ID_MENSAGEM, TEXTO,IMAGEM,DOCUMENTO,DATA_ENVIO,FK_ID_MATERIA");
        sql.append(" FROM MENSAGEM" );
        sql.append(" WHERE FK_ID_MATERIA = " + materia );

        Cursor resultado = conexao.rawQuery(sql.toString(),null);

        if (resultado.getCount() >0 ){
            resultado.moveToFirst();

            do{
                Mensagem mensagem = new Mensagem();
                mensagem.setIdMensagem(resultado.getInt(resultado.getColumnIndexOrThrow("ID_MENSAGEM") ));
                mensagem.setMenssagem(resultado.getString(resultado.getColumnIndexOrThrow("TEXTO")));
                mensagem.setImagem(resultado.getString(resultado.getColumnIndexOrThrow("IMAGEM")));
                mensagem.setDocumento(resultado.getString(resultado.getColumnIndexOrThrow("DOCUMENTO")));
                mensagem.setData(resultado.getString(resultado.getColumnIndexOrThrow("DATA_ENVIO")));
                mensagem.setMateria(resultado.getInt(resultado.getColumnIndexOrThrow("FK_ID_MATERIA")));

                mensagens.add(mensagem);

            }while(resultado.moveToNext());
        }


        return mensagens;
    }

}
