package com.emerson.organizerapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
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
import com.emerson.organizerapp.adapters.MensagemAdapter;
import com.emerson.organizerapp.beans.Mensagem;
import com.emerson.organizerapp.connection.DadosOpenHelp;
import com.emerson.organizerapp.fragments.BottomGalleryDialog;
import com.emerson.organizerapp.fragments.BottomSheetDialog;
import com.emerson.organizerapp.interfaces.RecyclerViewOnClickListenerHack;
import com.emerson.organizerapp.model.MensagemModel;
import com.emerson.organizerapp.utilitarios.Alerts;
import com.emerson.organizerapp.utilitarios.Tools;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ChatActivity extends AppCompatActivity implements RecyclerViewOnClickListenerHack , BottomSheetDialog.BottomSheetListener, BottomGalleryDialog.BottomGalleryListener{
    private static final int CAMERA_REQUEST = 0;
    private RecyclerView recyclerView;
    private EditText edtMessage;
    private FloatingActionButton fabSend;
    private Toolbar toolbar;
    private List<Mensagem> mensagemList;
    private MensagemAdapter chatAdapter;


    private SQLiteDatabase conexao;
    private DadosOpenHelp dadosOpenHelp;
    private MensagemModel mensagemModel;
    private long materiaId;
    private String materiaName;



    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edtMessage = findViewById(R.id.edt_message);
        fabSend = findViewById(R.id.fab_send);
        recyclerView = findViewById(R.id.rv_message);
        criarConexao();

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            materiaId = extras.getLong("materiaId");
            Log.i("LOG","materiaId: " + materiaId);
            materiaName = extras.getString("materiaName");
            toolbar.setTitle(materiaName);
        }
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        //mensagemList = new ArrayList<Mensagem>();
        mensagemModel = new MensagemModel(conexao);
        mensagemList = mensagemModel.buscarTodos(materiaId);
        chatAdapter = new MensagemAdapter(this, mensagemList);
        chatAdapter.setRecyclerViewOnClickListenerHack(this);
        recyclerView.setAdapter(chatAdapter);

        final Context context = this;
        edtMessage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if(motionEvent.getRawX() >= (edtMessage.getRight() - edtMessage.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
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
    private void criarConexao(){
        try{
            dadosOpenHelp = new DadosOpenHelp(this);
            conexao = dadosOpenHelp.getWritableDatabase();
            /*Snackbar.make(layoutContentActCadCliente, "CONEXÃO CRIADA COM SUCESSO!", Snackbar.LENGTH_SHORT)
                    .setAction("OK",null).show();*/

        }catch (SQLException ex){
            android.app.AlertDialog.Builder  dlg = new android.app.AlertDialog.Builder(this);
            dlg.setTitle("ERRO!");
            dlg.setMessage(ex.getMessage());
            dlg.setNeutralButton("OK",null);
            dlg.show();

        }
    }

    public void sendMessage(View view){
        if(!edtMessage.getText().toString().trim().isEmpty()){
            SimpleDateFormat formataData = new SimpleDateFormat("dd/MM/yyyy");
            Date data = new Date();
            String dateNow = formataData.format(data);
            Mensagem newMensagem = new Mensagem(edtMessage.getText().toString(),dateNow);
            try{
                newMensagem.setMateria(materiaId);
                newMensagem.setIdMensagem(mensagemModel.inserir(newMensagem));
                chatAdapter.addView(newMensagem,mensagemList.size());
                edtMessage.getText().clear();

            }catch (SQLException ex){
                android.app.AlertDialog.Builder  dlg = new android.app.AlertDialog.Builder(this);
                dlg.setTitle("Erro de inserção!");
                dlg.setMessage(ex.getMessage());
                dlg.setNeutralButton("OK",null);
                dlg.show();
            }

        }
    }

    @Override
    public void onClickListener(View view, int position) {

    }

    @Override
    public void onLongPressClickListener(View view, int position) {
        Alerts alerts = new Alerts(this,conexao);
        MensagemAdapter adapter = (MensagemAdapter) recyclerView.getAdapter();
        alerts.Options(mensagemList,position,adapter);
    }

    @Override
    public void onButtonClicked(String text) {
        if (text.equals("btnCamera")) {
            openCamera();
        } else if (text.equals("btnGallery")) {
            //Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
            BottomGalleryDialog bottomGalleryDialog = new BottomGalleryDialog();
            bottomGalleryDialog.show(getSupportFragmentManager(),"BotoomGalleryDialog");
        }else if(text.equals("btnDocuments")){
            Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
        }

    }

    private void sendImage(Bitmap image) {
        SimpleDateFormat formataData = new SimpleDateFormat("dd/MM/yyyy");
        Date data = new Date();
        String dateNow = formataData.format(data);
        Mensagem newMensagem = new Mensagem();
        newMensagem.setData(dateNow);
        Tools tools = new Tools();
        tools.saveImage(materiaName,image,newMensagem,this);
        try{
            newMensagem.setMateria(materiaId);
            newMensagem.setIdMensagem(mensagemModel.inserir(newMensagem));
            chatAdapter.addView(newMensagem,mensagemList.size());
            edtMessage.getText().clear();

        }catch (SQLException ex){
            android.app.AlertDialog.Builder  dlg = new android.app.AlertDialog.Builder(this);
            dlg.setTitle("Erro de inserção!");
            dlg.setMessage(ex.getMessage());
            dlg.setNeutralButton("OK",null);
            dlg.show();
        }
    }

    private void openCamera(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,CAMERA_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
        sendImage(bitmap);

    }
}
