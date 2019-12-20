package com.emerson.organizerapp.utilitarios;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Tools {
    //public static final String basePath = Environment.getExternalStorageDirectory() + "/OrganizeApp";
    public static final String basePath = "OrganizeApp/";



    public void salvarArquivo(String nomeArquivo, String conteudoArquivo, Context context){
        File folder = new File(Tools.basePath);

        if(!folder.exists()){
            folder.mkdir();
        }
        //String nomeArquivo = "File1";
        File arquivo = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "/OrganizeApp/" + nomeArquivo + ".txt");

        try{
            FileOutputStream fileOut = new FileOutputStream(arquivo);
            fileOut.write(conteudoArquivo.getBytes());
            Toast.makeText(context,"Arquivo salvo com sucesso!",Toast.LENGTH_SHORT).show();
            fileOut.close();
        }catch (FileNotFoundException e){
            e.printStackTrace();
            Log.i("LOG", "FileNotFoundException(): " + e.getMessage());
            Toast.makeText(context,"Erro ao salvar o arquivo, diretorio não encontrado",Toast.LENGTH_SHORT).show();
        }catch(IOException e){
            Log.i("LOG", "IOException(): " + e.getMessage());
            Toast.makeText(context,"Erro ao salvar o arquivo", Toast.LENGTH_SHORT).show();
        }
    }

}
