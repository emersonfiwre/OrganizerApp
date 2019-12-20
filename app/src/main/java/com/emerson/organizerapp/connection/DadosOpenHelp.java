package com.emerson.organizerapp.connection;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DadosOpenHelp extends SQLiteOpenHelper {
    private static final String dbName = "OrganizerDb";
    private static final int version = 2;

    public DadosOpenHelp(Context context){
        super(context,dbName,null,version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(ScriptDLL.getCreateTableAnotacao());
        sqLiteDatabase.execSQL(ScriptDLL.getCreateTableAuxData());
        sqLiteDatabase.execSQL(ScriptDLL.getCreateTableMensagem());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS ANOTACAO");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS AUX_DATA");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS MENSAGEM");
    }
}
