package com.emerson.organizerapp.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.emerson.organizerapp.beans.Anotacao;
import com.emerson.organizerapp.connection.DadosOpenHelp;
import com.emerson.organizerapp.utilitarios.Tools;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AnotacaoModel {
    private SQLiteDatabase conexao;
    private DadosOpenHelp dadosOpenHelp;
    private Context context;

    public AnotacaoModel(Context context)
    {
        this.context = context;
        criarConexao();
    }

    private void criarConexao(){
        try{
            dadosOpenHelp = new DadosOpenHelp(context);
            conexao = dadosOpenHelp.getWritableDatabase();
            /*Snackbar.make(layoutContentActCadCliente, "CONEXÃO CRIADA COM SUCESSO!", Snackbar.LENGTH_SHORT)
                    .setAction("OK",null).show();*/

        }catch (SQLException ex){
            Log.e("AnotacaoModel","criarConexao(): " + ex.getMessage());
        }
    }

    public long inserir(Anotacao anotacao){
        long resultado;
        ContentValues contentValues = new ContentValues();
        contentValues.put("IMAGEM", anotacao.getImgAnotacao());
        contentValues.put("NOME", anotacao.getNome());

        resultado = conexao.insertOrThrow("ANOTACAO",null,contentValues);
        createFolder(anotacao.getNome());
        Log.i("AnotacaoModel","inserir(): " + resultado);
        return resultado;
    }

    private void createFolder(String titleFolder) {
        File folder = new File(Tools.basePath + File.separator + titleFolder);

        if (!folder.exists()) {
            if(!folder.mkdirs()) {
                Log.e("createFolder()", "Erro ao criar os diretorios");
            }
        }
    }

    public void deletar(long codigo){
        String[] parametros = new String[1];
        parametros[0] = String.valueOf(codigo);

        conexao.delete("ANOTACAO","ID_ANOTACAO = ?", parametros);


    }
    public void alterar(Anotacao anotacao){

        ContentValues contentValues = new ContentValues();
        contentValues.put("IMAGEM", anotacao.getImgAnotacao());
        contentValues.put("NOME", anotacao.getNome());

        String[] parametros = new String[1];
        parametros[0] = String.valueOf(anotacao.getIdAnotacao());
        conexao.update("ANOTACAO",contentValues, "ID_ANOTACAO = ?",parametros);

    }
    public List<Anotacao> buscarTodos(){

        List<Anotacao> anotacaos = new ArrayList<>();

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ID_ANOTACAO, IMAGEM, NOME");
        sql.append(" FROM ANOTACAO" );

        Cursor resultado = conexao.rawQuery(sql.toString(),null);

        if (resultado.getCount() >0 ){
            resultado.moveToFirst();

            do{
                Anotacao anotacao = new Anotacao();
                anotacao.setIdAnotacao(resultado.getInt(resultado.getColumnIndexOrThrow("ID_ANOTACAO") ));
                anotacao.setImgAnotacao(resultado.getString(resultado.getColumnIndexOrThrow("IMAGEM")));
                anotacao.setNome(resultado.getString(resultado.getColumnIndexOrThrow("NOME")));

                anotacaos.add(anotacao);

            }while(resultado.moveToNext());
        }


        return anotacaos;
    }
}
