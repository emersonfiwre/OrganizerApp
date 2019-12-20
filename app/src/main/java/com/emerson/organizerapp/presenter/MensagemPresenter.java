package com.emerson.organizerapp.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.emerson.organizerapp.adapters.MensagemAdapter;
import com.emerson.organizerapp.beans.AuxData;
import com.emerson.organizerapp.beans.Mensagem;
import com.emerson.organizerapp.model.MensagemModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MensagemPresenter {
    private Context context;

    public MensagemPresenter(Context context){
        this.context = context;
    }

    public long inserir(Mensagem mensagem, long anotacaoId){
        mensagem.setIdData(inserirAuxData(anotacaoId));
        mensagem.setHora(getHour());
        Log.i("MensagemPresenter",getHour());
        MensagemModel model = new MensagemModel(context);
        return model.inserir(mensagem);
    }

    private long inserirAuxData(long anotacaoId){
        AuxDataPresenter axpresenter = new AuxDataPresenter(context);
        return axpresenter.inserir(new AuxData(getDate(),anotacaoId));
    }

    public void deletar(long id, int position, MensagemAdapter adapter){
        MensagemModel model = new MensagemModel(context);
        adapter.removeListItem(position);
        model.deletar(id);
    }

    public List<AuxData> buscarTodos(List<AuxData> dataList) {
        MensagemModel model = new MensagemModel(context);
        return model.buscarTodos(dataList);
    }

    private String getDate(){
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formataData = new SimpleDateFormat("dd/MM/yyyy");
        String dateNow = formataData.format(new Date());
        return dateNow;
    }
    private String getHour(){
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formataData = new SimpleDateFormat("HH:mm");
        String time = formataData.format(new Date());
        return time;
    }
}
