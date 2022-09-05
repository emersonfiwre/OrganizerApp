package com.emerson.organizerapp.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.emerson.organizerapp.beans.AuxData;
import com.emerson.organizerapp.beans.Mensagem;
import com.emerson.organizerapp.connection.DadosOpenHelp;

import java.util.ArrayList;
import java.util.List;


public class MensagemModel {
    private SQLiteDatabase conexao;
    private DadosOpenHelp dadosOpenHelp;
    private Context context;

    public MensagemModel(Context context){
        this.context = context;criarConexao();
    }

    private void criarConexao(){
        try{
            dadosOpenHelp = new DadosOpenHelp(context);
            conexao = dadosOpenHelp.getWritableDatabase();
            /*Snackbar.make(layoutContentActCadCliente, "CONEXÃO CRIADA COM SUCESSO!", Snackbar.LENGTH_SHORT)
                    .setAction("OK",null).show();*/

        }catch (SQLException ex){
            Log.e("MensagemModel","criarConexao(): " + ex.getMessage());
        }
    }

    public long inserir(Mensagem mensagem){
        long resultado;
        ContentValues contentValues = new ContentValues();
        contentValues.put("TEXTO",mensagem.getTexto());
        contentValues.put("IMAGEM",mensagem.getImagem());
        contentValues.put("DOCUMENTO",mensagem.getDocumento());
        contentValues.put("HORA_ENVIO",mensagem.getHora());
        contentValues.put("FK_ID_DATA",mensagem.getIdData());

        resultado = conexao.insertOrThrow("MENSAGEM",null,contentValues);
        return resultado;
    }
    public void deletar(long codigo){
        String[] parametros = new String[1];
        parametros[0] = String.valueOf(codigo);

        conexao.delete("MENSAGEM","ID_MENSAGEM = ?", parametros);
        Log.i("MensagemModel","deletar(): " + codigo);

    }

    public List<AuxData> buscarTodos(List<AuxData> dataList){

        for(AuxData data: dataList) {

            List<Mensagem> mensagemList = new ArrayList<>();

            StringBuilder sql = new StringBuilder();
            sql.append("SELECT ID_MENSAGEM, TEXTO,M.IMAGEM,DOCUMENTO,HORA_ENVIO,FK_ID_DATA");
            sql.append(" FROM MENSAGEM M INNER JOIN AUX_DATA AX");
            sql.append(" ON FK_ID_DATA = ID_DATA");
            sql.append("  AND DATA_ENVIO = '"+data.getDataEnvio()+"'");
            //sql.append(" WHERE DATA_ENVIO = " + data.getDataEnvio());
            sql.append("  INNER JOIN ANOTACAO A ");
            sql.append("  ON FK_ID_ANOTACAO = '"+ data.getFkAnotacao() +"'");
            sql.append("  GROUP BY ID_MENSAGEM");

            Cursor resultado = conexao.rawQuery(sql.toString(), null);

            if (resultado.getCount() > 0) {
                resultado.moveToFirst();

                do {
                    Mensagem mensagem = new Mensagem();
                    mensagem.setIdMensagem(resultado.getInt(resultado.getColumnIndexOrThrow("ID_MENSAGEM")));
                    mensagem.setTexto(resultado.getString(resultado.getColumnIndexOrThrow("TEXTO")));
                    mensagem.setImagem(resultado.getString(resultado.getColumnIndexOrThrow("IMAGEM")));
                    mensagem.setDocumento(resultado.getString(resultado.getColumnIndexOrThrow("DOCUMENTO")));
                    mensagem.setHora(resultado.getString(resultado.getColumnIndexOrThrow("HORA_ENVIO")));
                    mensagem.setIdData(resultado.getInt(resultado.getColumnIndexOrThrow("FK_ID_DATA")));

                    mensagemList.add(mensagem);
                } while (resultado.moveToNext());
            }
            data.setMensagemList(mensagemList);
        }


        return dataList;
    }

}
