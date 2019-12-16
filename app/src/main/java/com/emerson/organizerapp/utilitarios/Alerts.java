package com.emerson.organizerapp.utilitarios;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.emerson.organizerapp.R;
import com.emerson.organizerapp.adapters.MensagemAdapter;
import com.emerson.organizerapp.adapters.AnotacaoAdapter;
import com.emerson.organizerapp.beans.Anotacao;
import com.emerson.organizerapp.beans.Mensagem;
import com.emerson.organizerapp.model.AnotacaoModel;
import com.emerson.organizerapp.model.MensagemModel;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Alerts {
    private Activity activity;
    private SQLiteDatabase conexao;

    public Alerts(Activity activity,final SQLiteDatabase conexao){
        this.activity = activity;
        this.conexao = conexao;
    }

    public void dialogAddSubject(final List<Anotacao> anotacaoList, final AnotacaoAdapter materiaAdapter){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity, R.style.Dialog_New_Materia);
        dialogBuilder.setTitle(R.string.addSubject);
        LayoutInflater inflater = activity.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_subject, null);
        dialogBuilder.setView(dialogView);
        final EditText edtNomeMateria = dialogView.findViewById(R.id.edt_nome_materia);
        final CircleImageView circleImgMateria = dialogView.findViewById(R.id.img_materia);
        dialogBuilder.setPositiveButton(R.string.create, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Anotacao anotacao = new Anotacao(edtNomeMateria.getText().toString());
                try {
                    AnotacaoModel dao = new AnotacaoModel(conexao);
                    anotacao.setIdMateria(dao.inserir(anotacao));
                    Tools tools = new Tools();
                    tools.createFolder(anotacao.getTitulo(),activity);

                    materiaAdapter.addView(anotacao, anotacaoList.size());

                }catch (SQLException ex){
                    android.app.AlertDialog.Builder  dlg = new android.app.AlertDialog.Builder(activity);
                    dlg.setTitle("Erro de inserção!");
                    dlg.setMessage(ex.getMessage());
                    dlg.setNeutralButton("OK",null);
                    dlg.show();

                }

                //Toast.makeText(activity,"Positive",Toast.LENGTH_LONG).show();
            }
        });
        dialogBuilder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Toast.makeText(activity,"Negative",Toast.LENGTH_LONG).show();
            }
        });

        //AlertDialog alertDialog = dialogBuilder.create();
        //alertDialog.show();
        dialogBuilder.show();
    }

    public void Options(final List<Anotacao> anotacaoList, final int position, final AnotacaoAdapter materiaAdapter){
        String[] options = {"Remover"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity,R.style.Dialog);
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        AnotacaoModel dao = new AnotacaoModel(conexao);
                        dao.delete(anotacaoList.get(position).getIdMateria());
                        materiaAdapter.removeListItem(position);
                        break;
                }
            }
        }).show();
    }
    public void Options(final List<Mensagem> mensagemList, final int position, final MensagemAdapter chatAdapter){
        String[] options = {"Remover"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity,R.style.Dialog);
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        MensagemModel dao = new MensagemModel(conexao);
                        dao.delete(mensagemList.get(position).getIdMensagem());
                        chatAdapter.removeListItem(position);
                        break;
                }
            }
        }).show();
    }




}
