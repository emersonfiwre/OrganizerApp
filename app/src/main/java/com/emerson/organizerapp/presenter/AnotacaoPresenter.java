package com.emerson.organizerapp.presenter;

import android.content.Context;
import android.widget.Adapter;

import com.emerson.organizerapp.adapters.AnotacaoAdapter;
import com.emerson.organizerapp.beans.Anotacao;
import com.emerson.organizerapp.model.AnotacaoModel;

import java.util.List;

public class AnotacaoPresenter {
    private Context context;

    public AnotacaoPresenter(Context context){
        this.context = context;
    }

    public long inserir(Anotacao anotacao, AnotacaoAdapter adapter){
        AnotacaoModel model = new AnotacaoModel(context);
        adapter.addView(anotacao);
        return model.inserir(anotacao);
    }

    public void deletar(long id, int position, AnotacaoAdapter adapter){
        AnotacaoModel model = new AnotacaoModel(context);
        adapter.removeListItem(position);
        model.deletar(id);
    }

    public List<Anotacao> buscarTodos() {
        AnotacaoModel model = new AnotacaoModel(context);
        return model.buscarTodos();
    }
}
