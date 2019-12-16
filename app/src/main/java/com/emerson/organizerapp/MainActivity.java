package com.emerson.organizerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.emerson.organizerapp.adapters.AnotacaoAdapter;
import com.emerson.organizerapp.beans.Anotacao;
import com.emerson.organizerapp.connection.DadosOpenHelp;
import com.emerson.organizerapp.interfaces.RecyclerViewOnClickListenerHack;
import com.emerson.organizerapp.model.AnotacaoModel;
import com.emerson.organizerapp.utilitarios.Alerts;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerViewOnClickListenerHack {
    private RecyclerView recyclerView;
    private List<Anotacao> anotacaoList;
    private Toolbar toolbar;

    private SQLiteDatabase conexao;
    private DadosOpenHelp dadosOpenHelp;

    private CoordinatorLayout layoutContentActCadCliente;
    private AnotacaoModel anotacaoModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        setSupportActionBar(toolbar);
        criarConexao();

        anotacaoModel = new AnotacaoModel(conexao);
        anotacaoList = anotacaoModel.buscarTodos();
        anotacaoList.add(0,new Anotacao("Adicionar nova anotação"));

        AnotacaoAdapter materiaAdapter = new AnotacaoAdapter(this, anotacaoList);
        materiaAdapter.setRecyclerViewOnClickListenerHack(this);
        recyclerView.setAdapter(materiaAdapter);

        //Código necessário para a permissão a memória Interna
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1000);

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1000:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //Toast.makeText(this,"Permissão Concedida!",Toast.LENGTH_SHORT).show();
                }else{
                    //Toast.makeText(this,"Permissão NÃO Concedida!",Toast.LENGTH_SHORT).show();
                    finish();
                }
        }
    }

    private void initView(){
        toolbar = findViewById(R.id.toolbar);
        layoutContentActCadCliente =  findViewById(R.id.layoutContentMain);
        recyclerView = findViewById(R.id.rv_materias);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView;
        MenuItem item = menu.findItem(R.id.action_searchable_activity);
        searchView = (SearchView) item.getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint(getResources().getString(R.string.search_hint));
        //searchView.setBackground();

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onClickListener(View view, int position) {

        if (anotacaoList.get(position).getTitulo().equals("Adicionar nova anotação")){
            Alerts alerts = new Alerts(this,conexao);
            AnotacaoAdapter adapter = (AnotacaoAdapter) recyclerView.getAdapter();
            alerts.dialogAddSubject(anotacaoList,adapter);

        }else{
            Intent intent = new Intent(this, ChatActivity.class);
            intent.putExtra("materiaId", anotacaoList.get(position).getIdMateria());
            intent.putExtra("materiaName", anotacaoList.get(position).getTitulo());
            startActivity(intent);
        }
    }

    @Override
    public void onLongPressClickListener(View view, int position) {
        if (!anotacaoList.get(position).getTitulo().equals("Adicionar nova anotação")) {
            Alerts alerts = new Alerts(this,conexao);
            AnotacaoAdapter adapter = (AnotacaoAdapter) recyclerView.getAdapter();
            alerts.Options(anotacaoList,position,adapter);

            //Toast.makeText(this, "Anotacao removida", Toast.LENGTH_SHORT).show();
        }
    }






}
