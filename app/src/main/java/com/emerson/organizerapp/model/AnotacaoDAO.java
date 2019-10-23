package com.emerson.organizerapp.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.emerson.organizerapp.beans.Anotacao;

import java.util.ArrayList;
import java.util.List;

public class AnotacaoDAO {
    private SQLiteDatabase conexao;

    public AnotacaoDAO(SQLiteDatabase conexao){
        this.conexao = conexao;
    }

    public long inserir(Anotacao anotacao){
        long resultado;
        ContentValues contentValues = new ContentValues();
        contentValues.put("IMAGE", anotacao.getImgMateria());
        contentValues.put("NOME", anotacao.getTitulo());

        resultado = conexao.insertOrThrow("MATERIA",null,contentValues);
        Log.i("LOG","inserirMateria(): " + resultado);
        return resultado;
    }
    public void delete(long codigo){
        String[] parametros = new String[1];
        parametros[0] = String.valueOf(codigo);

        conexao.delete("MATERIA","ID_MATERIA = ?", parametros);


    }
    public void alterar(Anotacao anotacao){

        ContentValues contentValues = new ContentValues();
        contentValues.put("IMAGE", anotacao.getImgMateria());
        contentValues.put("NOME", anotacao.getTitulo());

        String[] parametros = new String[1];
        parametros[0] = String.valueOf(anotacao.getIdMateria());
        conexao.update("MATERIA",contentValues, "ID_MATERIA = ?",parametros);

    }
    public List<Anotacao> buscarTodos(){

        List<Anotacao> anotacaos = new ArrayList<>();

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ID_MATERIA, IMAGE, NOME");
        sql.append(" FROM MATERIA" );

        Cursor resultado = conexao.rawQuery(sql.toString(),null);

        if (resultado.getCount() >0 ){
            resultado.moveToFirst();

            do{
                Anotacao anotacao = new Anotacao();
                anotacao.setIdMateria(resultado.getInt(resultado.getColumnIndexOrThrow("ID_MATERIA") ));
                anotacao.setImgMateria(resultado.getBlob(resultado.getColumnIndexOrThrow("IMAGE")));
                anotacao.setTitulo(resultado.getString(resultado.getColumnIndexOrThrow("NOME")));

                anotacaos.add(anotacao);

            }while(resultado.moveToNext());
        }


        return anotacaos;
    }
}
