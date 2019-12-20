package com.emerson.organizerapp.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.emerson.organizerapp.R;
import com.emerson.organizerapp.adapters.AnotacaoAdapter;
import com.emerson.organizerapp.adapters.MensagemAdapter;
import com.emerson.organizerapp.beans.Anotacao;
import com.emerson.organizerapp.beans.Mensagem;
import com.emerson.organizerapp.presenter.AnotacaoPresenter;
import com.emerson.organizerapp.presenter.MensagemPresenter;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Alerts extends Activity {
    private static final int SELECT_FILE = 123;
    private Activity activity;


    public Alerts(Activity activity){
        this.activity = activity;
    }

    public void dialogAddSubject(final AnotacaoAdapter adapter){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity, R.style.Dialog_New_Materia);
        dialogBuilder.setTitle(R.string.addSubject);
        LayoutInflater inflater = activity.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_annotation, null);
        dialogBuilder.setView(dialogView);
        final EditText edtNomeMateria = dialogView.findViewById(R.id.edt_nome_materia);
        final CircleImageView circleImgMateria = dialogView.findViewById(R.id.img_materia);

        circleImgMateria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                getActivity().startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem"), 123);
            }
        });
        dialogBuilder.setPositiveButton(R.string.create, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Anotacao anotacao = new Anotacao(edtNomeMateria.getText().toString());
                try {
                    AnotacaoPresenter presenter = new AnotacaoPresenter(activity);
                    anotacao.setIdAnotacao(presenter.inserir(anotacao, adapter));


                }catch (Exception ex){
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

    public Activity getActivity() {
        return activity;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
        }
    }

    private void onSelectFromGalleryResult(Intent data) {
        if (data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            //profileImage.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        }
    }

    public void Options(final List<Anotacao> anotacaoList, final int position, final AnotacaoAdapter adapter){
        String[] options = {"Remover"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity,R.style.Dialog);
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        AnotacaoPresenter presenter = new AnotacaoPresenter(activity);
                        presenter.deletar(anotacaoList.get(position).getIdAnotacao(), position,adapter);
                        break;
                }
            }
        }).show();
    }
    public void Options(final List<Mensagem> mensagemList, final int position, final MensagemAdapter adapter){
        String[] options = {"Remover"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity,R.style.Dialog);
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        MensagemPresenter presenter = new MensagemPresenter(activity);
                        presenter.deletar(mensagemList.get(position).getIdMensagem(),position,adapter);
                        break;
                }
            }
        }).show();
    }




}
