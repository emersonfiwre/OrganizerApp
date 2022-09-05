package com.emerson.organizerapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
            Log.i("ChatActiviy","anotacaoId: "+String.valueOf(anotacaoId));
            anotacaoName = extras.getString("anotacaoName");
            toolbar.setTitle(anotacaoName);
        }
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        llm.setStackFromEnd(true);
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
                Intent intent = new Intent(this, PreviewActivity.class);
                intent.putExtra("anotacaoId", anotacaoId);
                intent.putExtra("anotacaoName", anotacaoName);
                startActivity(intent);
                /*dataList.clear();
                dataList = presenter.buscarTodos(anotacaoId);
                AuxDataAdapter ad = new AuxDataAdapter(this,dataList);
                recyclerView.setAdapter(ad);*/
                break;

            case "btnDocuments":
                Toast.makeText(this, "Módulo em construção", Toast.LENGTH_SHORT).show();
                break;
        }

    }

/*
    private void sendImage(String uri) {

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





}
