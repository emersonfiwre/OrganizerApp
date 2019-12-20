package com.emerson.organizerapp.presenter;

import android.content.Context;
import com.emerson.organizerapp.beans.AuxData;
import com.emerson.organizerapp.model.AuxDataModel;
import java.util.List;

public class AuxDataPresenter {
    private Context context;

    public AuxDataPresenter(Context context){
        this.context = context;
    }

    public long inserir(AuxData data){
        AuxDataModel model = new AuxDataModel(context);
        return model.inserir(data);
    }
    public void deletar(String data){
        AuxDataModel model = new AuxDataModel(context);
        model.deletar(data);
    }
    public List<AuxData> buscarTodos(long anotacao){
        AuxDataModel model = new AuxDataModel(context);
        return model.buscarTodos(anotacao);
    }
}
