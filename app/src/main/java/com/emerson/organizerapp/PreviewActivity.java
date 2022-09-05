package com.emerson.organizerapp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.emerson.organizerapp.adapters.ImagemAdapter;
import com.emerson.organizerapp.beans.Mensagem;
import com.emerson.organizerapp.interfaces.RecyclerViewOnClickListenerHack;
import com.emerson.organizerapp.presenter.MensagemPresenter;
import com.emerson.organizerapp.utilitarios.Tools;
import com.fxn.pix.Options;
import com.fxn.pix.Pix;
import com.fxn.utility.ImageQuality;
import com.fxn.utility.PermUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class PreviewActivity extends AppCompatActivity implements RecyclerViewOnClickListenerHack {
    private EditText edtSubtitle;
    private RecyclerView rvImages;
    private ImageView backImage;
    private ImagemAdapter adapter;
    private ImageButton addImages;
    Options options;
    private ArrayList<String> imageList;
    private String[] edtSubtitleList;
    private long anotacaoId;
    private String anotacaoName;
    private static final int RequestCode = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        initView();
    }

    private void initView() {
        edtSubtitle = findViewById(R.id.edt_subtitle);
        rvImages = findViewById(R.id.rv_images);
        backImage = findViewById(R.id.preview_image);
        backImage = findViewById(R.id.preview_image);
        addImages = findViewById(R.id.add_images);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            anotacaoId = extras.getLong("anotacaoId");
            anotacaoName = extras.getString("anotacaoName");
        }

        options = Options.init()
                .setRequestCode(RequestCode)                                            //Request code for activity results
                .setCount(10)                                                           //Number of images to restict selection count
                .setFrontfacing(false)                                                  //Front Facing camera on start
                .setImageQuality(ImageQuality.REGULAR)                                     //Image Quality
                .setScreenOrientation(Options.SCREEN_ORIENTATION_PORTRAIT)              //Orientaion
                .setPath(Tools.basePath + anotacaoName);                                //Custom Path For Image Storage                               //Custom Path For Image Storage

        Pix.start(PreviewActivity.this, options);


        //Log.i("onButtonClicked()",options.getPath());
        /*if(this.getCurrentFocus().getId() == edtSubtitle.getId()){
            // your view is in focus
        }else{
            // not in the focus
        }
        edtSubtitle.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if ((hasFocus)) {
                    //Toast.makeText(getBaseContext(),"fucused",Toast.LENGTH_SHORT).show();
                    //rvImages.setVisibility(View.GONE);
                } else {
                    //rvImages.setVisibility(View.VISIBLE);
                }
            }
        });*/

    }

    public void addImages(View view) {
        options.setPreSelectedUrls(imageList);
        Pix.start(PreviewActivity.this, options);
    }

    private void setupImageBack(int position) {
        try {
            File f = new File(imageList.get(position));
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            backImage.setImageBitmap(b);

        } catch (FileNotFoundException e) {
            Log.i("Log", "FileNotFoundException() : " + e.getCause());
            e.printStackTrace();
        }
    }

    private void setupRecycler() {
        rvImages.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        //llm.setReverseLayout(true);
        //llm.setStackFromEnd(true);
        rvImages.setLayoutManager(llm);
        adapter = new ImagemAdapter(this, imageList);
        adapter.setRecyclerViewOnClickListenerHack(this);
        rvImages.setAdapter(adapter);
    }


    @Override
    public void onClickListener(View view, int position) {
        edtSubtitleList[adapter.selectedPosition] = edtSubtitle.getText().toString().trim();
        edtSubtitle.setText("");
        //Toast.makeText(this, "isEmpty:"+edtSubtitleList[position], Toast.LENGTH_SHORT).show();
        if (edtSubtitleList[position] != null) {
            edtSubtitle.setText(edtSubtitleList[position]);
            //Toast.makeText(this, "edtSubtitleList[position]"+edtSubtitleList[position], Toast.LENGTH_SHORT).show();
        }
        setupImageBack(position);
        //view.setBackground(getDrawable(R.drawable.shape_border_selected));
        adapter.selectedPosition = position;
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onLongPressClickListener(View view, int position) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PermUtil.REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Pix.start(this, Options.init().setRequestCode(RequestCode));
                } else {
                    Toast.makeText(this, "É preciso da permissão para poder utilizar a câmera.", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("onActivityResult", String.valueOf(requestCode));
        if (resultCode == RESULT_OK && requestCode == RequestCode) {
            imageList = data.getStringArrayListExtra(Pix.IMAGE_RESULTS);

            edtSubtitleList = new String[imageList.size()];
            setupImageBack(0);
            setupRecycler();
        }
        if (imageList == null) {
            super.onBackPressed();
        }

    }

    @Override
    public void onBackPressed() {
        if (!imageList.isEmpty()) {
            options.setPreSelectedUrls(imageList);
            Pix.start(PreviewActivity.this, options);
        }
        super.onBackPressed();
    }


    public void sendImage(View view) {
        for(int i=0; i<imageList.size();i++) {
            //Log.i("uri(1)",uri);
            //sendImage(uri);
            if (imageList.get(i) != null) {
                try {
                    Mensagem newMensagem = new Mensagem();
                    newMensagem.setImagem(imageList.get(i));
                    if(edtSubtitleList[i] != null){
                        if(!edtSubtitleList[i].isEmpty()){
                            newMensagem.setTexto(edtSubtitleList[i]);
                        }
                    }else if(!edtSubtitle.getText().toString().isEmpty()){
                        newMensagem.setTexto(edtSubtitle.getText().toString());
                    }
                    MensagemPresenter mspresenter = new MensagemPresenter(this);
                    mspresenter.inserir(newMensagem, anotacaoId);
                    openChat();
                } catch (Exception ex) {
                    android.app.AlertDialog.Builder dlg = new android.app.AlertDialog.Builder(this);
                    dlg.setTitle("Erro de inserção!");
                    dlg.setMessage(ex.getMessage());
                    ex.printStackTrace();
                    dlg.setNeutralButton("OK", null);
                    dlg.show();
                }

            }
        }
    }

    private void openChat(){
        Intent intent = new Intent(this, ChatActivity.class);
        intent.putExtra("anotacaoId", anotacaoId);
        intent.putExtra("anotacaoName", anotacaoName);
        startActivity(intent);
        finish();
    }
}