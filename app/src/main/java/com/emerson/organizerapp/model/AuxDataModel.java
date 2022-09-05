package com.emerson.organizerapp.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.emerson.organizerapp.beans.AuxData;
import com.emerson.organizerapp.connection.DadosOpenHelp;

import java.util.ArrayList;
import java.util.List;

public class AuxDataModel {
    private SQLiteDatabase conexao;
    private DadosOpenHelp dadosOpenHelp;
    private Context context;

    public AuxDataModel(Context context) {
        this.context = context;
        criarConexao();
    }

    private void criarConexao() {
        try {
            dadosOpenHelp = new DadosOpenHelp(context);
            conexao = dadosOpenHelp.getWritableDatabase();
            /*Snackbar.make(layoutContentActCadCliente, "CONEXÃO CRIADA COM SUCESSO!", Snackbar.LENGTH_SHORT)
                    .setAction("OK",null).show();*/

        } catch (SQLException ex) {
            Log.e("AuxDataModel", "criarConexao(): " + ex.getMessage());
        }
    }

    public long inserir(AuxData data) {
        long resultado;
        if (!ifNotExists(data.getDataEnvio(),data.getFkAnotacao())) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("DATA_ENVIO", data.getDataEnvio());
            contentValues.put("FK_ID_ANOTACAO", data.getFkAnotacao());

            resultado = conexao.insertOrThrow("AUX_DATA", null, contentValues);
        } else {
            resultado = whatId(data.getDataEnvio(),data.getFkAnotacao()).getIdData();
        }
        Log.i("AuxDataModel", "inserir(): " + resultado);
        return resultado;
    }

    private boolean ifNotExists(String exists, long anotacaoId) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ID_DATA, DATA_ENVIO, FK_ID_ANOTACAO");
        sql.append(" FROM AUX_DATA");
        sql.append(" WHERE FK_ID_ANOTACAO = " + anotacaoId);
        sql.append(" AND DATA_ENVIO = '" + exists + "'");
        Cursor resultado = conexao.rawQuery(sql.toString(), null);

        return (resultado.getCount() > 0);
    }

    private AuxData whatId(String exists, long anotacaoId) {

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ID_DATA, DATA_ENVIO, FK_ID_ANOTACAO");
        sql.append(" FROM AUX_DATA");
        sql.append(" WHERE FK_ID_ANOTACAO = " + anotacaoId);
        sql.append(" AND DATA_ENVIO = '" + exists + "'");

        Cursor resultado = conexao.rawQuery(sql.toString(), null);
        AuxData data = new AuxData();

        if (resultado.getCount() > 0) {
            resultado.moveToFirst();
            data.setIdData(resultado.getInt(resultado.getColumnIndexOrThrow("ID_DATA")));
            data.setDataEnvio(resultado.getString(resultado.getColumnIndexOrThrow("DATA_ENVIO")));
            data.setFkAnotacao(resultado.getInt(resultado.getColumnIndexOrThrow("FK_ID_ANOTACAO")));
        }

        return data;
    }

    public void deletar(String data) {
        String[] parametros = new String[1];
        parametros[0] = data;

        conexao.delete("AUX_DATA", "DATA_ENVIO = ?", parametros);


    }

    public List<AuxData> buscarTodos(long anotacao) {

        List<AuxData> dataList = new ArrayList<>();

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ID_DATA, DATA_ENVIO, FK_ID_ANOTACAO");
        sql.append(" FROM AUX_DATA");
        sql.append(" WHERE FK_ID_ANOTACAO = " + anotacao);

        Cursor resultado = conexao.rawQuery(sql.toString(), null);

        if (resultado.getCount() > 0) {
            resultado.moveToFirst();

            do {
                AuxData data = new AuxData();
                data.setIdData(resultado.getInt(resultado.getColumnIndexOrThrow("ID_DATA")));
                data.setDataEnvio(resultado.getString(resultado.getColumnIndexOrThrow("DATA_ENVIO")));
                data.setFkAnotacao(resultado.getInt(resultado.getColumnIndexOrThrow("FK_ID_ANOTACAO")));

                dataList.add(data);

            } while (resultado.moveToNext());
        }


        return dataList;
    }
}
