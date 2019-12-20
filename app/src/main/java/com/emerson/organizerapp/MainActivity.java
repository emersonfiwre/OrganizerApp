package com.emerson.organizerapp;

import android.Manifest;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.emerson.organizerapp.adapters.AnotacaoAdapter;
import com.emerson.organizerapp.beans.Anotacao;
import com.emerson.organizerapp.interfaces.RecyclerViewOnClickListenerHack;
import com.emerson.organizerapp.presenter.AnotacaoPresenter;
import com.emerson.organizerapp.view.Alerts;

import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerViewOnClickListenerHack {
    private RecyclerView recyclerView;
    private List<Anotacao> anotacaoList;
    private Toolbar toolbar;
    private CoordinatorLayout layoutMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        setSupportActionBar(toolbar);
        //this.deleteDatabase("OrganizerDb");


        AnotacaoPresenter presenter = new AnotacaoPresenter(this);
        anotacaoList = presenter.buscarTodos();
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
        layoutMain =  findViewById(R.id.layoutContentMain);
        recyclerView = findViewById(R.id.rv_materias);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
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

        if (anotacaoList.get(position).getNome().equals("Adicionar nova anotação")){
            Alerts alerts = new Alerts(this);
            AnotacaoAdapter adapter = (AnotacaoAdapter) recyclerView.getAdapter();
            alerts.dialogAddSubject(adapter);

        }else{
            Intent intent = new Intent(this, ChatActivity.class);
            intent.putExtra("anotacaoId", anotacaoList.get(position).getIdAnotacao());
            intent.putExtra("anotacaoName", anotacaoList.get(position).getNome());
            startActivity(intent);
        }
    }

    @Override
    public void onLongPressClickListener(View view, int position) {
        if (!anotacaoList.get(position).getNome().equals("Adicionar nova anotação")) {
            Alerts alerts = new Alerts(this);
            AnotacaoAdapter adapter = (AnotacaoAdapter) recyclerView.getAdapter();
            alerts.Options(anotacaoList,position,adapter);

        }
    }






}
