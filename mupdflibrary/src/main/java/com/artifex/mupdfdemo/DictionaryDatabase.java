package com.artifex.mupdfdemo;

import android.app.SearchManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.provider.BaseColumns;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by User on 05-03-2016.
 */
public class DictionaryDatabase {

    public static final String KEY_ROWID = "KEY";
    public static final String KEY_MEAN = "mean";

    private static final String DatabaseName = "dict";
    private static final String DatabaseTable  = "eng";
    private static final int DatabaseVersion = 1;


    private DbHelper ourHelper;
    private final Context ourContext;
    private SQLiteDatabase ourDatabase;

    private static class DbHelper extends SQLiteOpenHelper{

        public DbHelper(Context context){
            super(context,DatabaseName,null,DatabaseVersion);
        }

        @Override
        public void onCreate(SQLiteDatabase db){
            db.execSQL("CREATE TABLE " + DatabaseTable + "(" +
                    KEY_ROWID + " TEXT PRIMARY KEY, " +
                    KEY_MEAN + " TEXT NOT NULL);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){
            db.execSQL("DROP TABLE IF EXISTS" + DatabaseTable);
            onCreate(db);
        }

    }
    public DictionaryDatabase(Context c){
        ourContext = c;
    }

    public DictionaryDatabase open(){
        ourHelper = new DbHelper(ourContext);
        ourDatabase = ourHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        ourHelper.close();

    }

    public long createEntry(String key,String def){
        ContentValues cv = new ContentValues();
        cv.put(KEY_ROWID,key);
        cv.put(KEY_MEAN, def);
        return ourDatabase.insert(DatabaseTable,null,cv);
    }

    public ArrayList<String> getData(String keys){
        String[] k = {keys};
        String[] column = new String[]{KEY_ROWID,KEY_MEAN};
        Cursor c = ourDatabase.query(DatabaseTable,column,KEY_ROWID+"=?",k,null,null,null);
        ArrayList<String> s = new ArrayList<>();

        int key = c.getColumnIndex(KEY_ROWID);
        int def = c.getColumnIndex(KEY_MEAN);

        for (c.moveToFirst(); !c.isAfterLast();c.moveToNext()){
            s.add(c.getString(key));
            s.add(c.getString(def));
        }

        return s;
    }
}