package com.emerson.organizerapp.utilitarios;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.emerson.organizerapp.beans.Mensagem;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Tools {
    public static final String basePath = Environment.getExternalStorageDirectory() + "/OrganizeApp";

    public void createFolder(String titleFolder, Context context) {
        File folder = new File(Tools.basePath + File.separator + titleFolder);

        if (!folder.exists()) {
            if(!folder.mkdirs()) {
                Toast.makeText(context, "Falha ao criar diretório", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void saveImage(String folder, Bitmap imagem, Mensagem mensagem,Context context){
        File directory = new File(Tools.basePath + File.separator + folder);
        if(!directory.exists()){
            directory.mkdir();
        }

        SimpleDateFormat formataData = new SimpleDateFormat("yyyyMMddmmss");
        Date data = new Date();
        String dateNow = formataData.format(data);
        String nomeArquivo = "IMG" + dateNow;

        File arquivo = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "/OrganizeApp/" + folder + File.separator + nomeArquivo + ".jpg");
        Log.i("Log","Saved : "+ arquivo.toString() );
        mensagem.setImagem(arquivo.toString());

        try{
            FileOutputStream fileOut = new FileOutputStream(arquivo);
            imagem.compress(Bitmap.CompressFormat.JPEG, 100,fileOut);
            fileOut.flush();
            fileOut.close();

            //Toast.makeText(context,"Imagem salva com sucesso!",Toast.LENGTH_SHORT).show();
        }catch (FileNotFoundException e){
            e.printStackTrace();
            Log.i("LOG", "FileNotFoundException(): " + e.getMessage());
            Toast.makeText(context,"Erro ao salvar a foto, diretorio não encontrado",Toast.LENGTH_LONG).show();
        }catch(IOException e){
            e.printStackTrace();
            Log.i("LOG", "IOException(): " + e.getMessage());
            Toast.makeText(context,"Erro ao salvar foto", Toast.LENGTH_SHORT).show();
        }


    }

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
