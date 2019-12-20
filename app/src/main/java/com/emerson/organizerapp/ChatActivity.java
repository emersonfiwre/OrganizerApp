package com.emerson.organizerapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.emerson.organizerapp.adapters.AuxDataAdapter;
import com.emerson.organizerapp.beans.AuxData;
import com.emerson.organizerapp.beans.Mensagem;
import com.emerson.organizerapp.fragments.BottomSheetDialog;
import com.emerson.organizerapp.presenter.AuxDataPresenter;
import com.emerson.organizerapp.presenter.MensagemPresenter;
import com.emerson.organizerapp.utilitarios.Tools;
import com.fxn.pix.Options;
import com.fxn.pix.Pix;
import com.fxn.utility.ImageQuality;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity implements BottomSheetDialog.BottomSheetListener{
    private RecyclerView recyclerView;
    private EditText edtMensagem;
    private FloatingActionButton fabSend;
    private Toolbar toolbar;
    private List<AuxData> dataList;
    private AuxDataAdapter dataAdapter;

    private long anotacaoId;
    private String anotacaoName;
    private static final int RequestCode = 100;
    private AuxDataPresenter presenter;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edtMensagem = findViewById(R.id.edt_message);
        fabSend = findViewById(R.id.fab_send);
        recyclerView = findViewById(R.id.rv_data);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            anotacaoId = extras.getLong("anotacaoId");
            anotacaoName = extras.getString("anotacaoName");
            toolbar.setTitle(anotacaoName);
        }
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        presenter = new AuxDataPresenter(this);
        dataList = presenter.buscarTodos(anotacaoId);
        dataAdapter = new AuxDataAdapter(this, dataList);
        recyclerView.setAdapter(dataAdapter);

        edtMensagem.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if(motionEvent.getRawX() >= (edtMensagem.getRight() - edtMensagem.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here
                        //Toast.makeText(context,"Módulo em construção",Toast.LENGTH_SHORT).show();
                        BottomSheetDialog bottomDialog = new BottomSheetDialog();
                        bottomDialog.show(getSupportFragmentManager(),"BottomSheetDialog");


                        return true;
                    }
                }
                return false;
            }
        });


    }

    public void sendMessage(View view){
        if(!edtMensagem.getText().toString().trim().isEmpty()){
            try{
                Mensagem newMensagem = new Mensagem(edtMensagem.getText().toString());
                MensagemPresenter mspresenter = new MensagemPresenter(this);
                mspresenter.inserir(newMensagem, anotacaoId);
                edtMensagem.getText().clear();

                dataList.clear();
                dataList = presenter.buscarTodos(anotacaoId);
                AuxDataAdapter ad = new AuxDataAdapter(this,dataList);
                recyclerView.setAdapter(ad);

            }catch (Exception ex){
                android.app.AlertDialog.Builder  dlg = new android.app.AlertDialog.Builder(this);
                dlg.setTitle("Erro de inserção!");
                dlg.setMessage(ex.getMessage());
                dlg.setNeutralButton("OK",null);
                dlg.show();
            }

        }
    }

    @Override
    public void onButtonClicked(String text) {
        switch (text) {
            case "btnCamera":
                //Library PixImagePicker
                Options options = Options.init()
                        .setRequestCode(RequestCode)                                            //Request code for activity results
                        .setCount(10)                                                           //Number of images to restict selection count
                        .setFrontfacing(false)                                                  //Front Facing camera on start
                        .setImageQuality(ImageQuality.HIGH)                                     //Image Quality
                        .setScreenOrientation(Options.SCREEN_ORIENTATION_PORTRAIT)              //Orientaion
                        .setPath(Tools.basePath + anotacaoName);                                //Custom Path For Image Storage                               //Custom Path For Image Storage

                Pix.start(ChatActivity.this, options);
                Log.i("onButtonClicked()",options.getPath());
                break;

            case "btnDocuments":
                Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
                break;
        }

    }

    /*private void sendImage(String uri) {

        Mensagem newMensagem = new Mensagem();
        newMensagem.setData(getDate());
        newMensagem.setImagem(uri);
        try{
            newMensagem.setMateria(anotacaoId);
            newMensagem.setIdMensagem(mensagemModel.inserir(newMensagem));
            mensagemAdapter.addView(newMensagem,mensagemList.size());
            edtMensagem.getText().clear();

            MensagemModel model = new MensagemModel(conexao);
            newMensagem.setIdMensagem(mensagemModel.inserir(newMensagem));
        }catch (SQLException ex){
            android.app.AlertDialog.Builder  dlg = new android.app.AlertDialog.Builder(this);
            dlg.setTitle("Erro de inserção!");
            dlg.setMessage(ex.getMessage());
            dlg.setNeutralButton("OK",null);
            dlg.show();
        }
    }

    private String getDate(){
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formataData = new SimpleDateFormat("dd/MM/yyyy");
        String dateNow = formataData.format(new Date());
        return dateNow;
    }
    private String getHour(){
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formataData = new SimpleDateFormat("HH:mm");
        String dateNow = formataData.format(new Date());
        return dateNow;
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == RequestCode) {
            ArrayList<String> imagesList = data.getStringArrayListExtra(Pix.IMAGE_RESULTS);
            /*for(String uri : imagesList){
                sendImage(uri);
            }*/

        }

    }
}
