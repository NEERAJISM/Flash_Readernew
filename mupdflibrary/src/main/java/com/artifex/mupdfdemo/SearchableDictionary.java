package com.artifex.mupdfdemo;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;


/**
 * Created by User on 05-03-2016.
 */
public class SearchableDictionary extends Activity {
    Button sqlUpdate, sqlView;
    EditText sqlKey,sqlDef;
    BufferedReader br = null;

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);

        try{
            String line="";
            br = new BufferedReader(new FileReader("/sdcard/" + "Flash Reader/dictionary.txt" ));
            while ((line = br.readLine())!=null){
                Log.d("search","i am processing");
                String[] data = line.split(",");
                DictionaryDatabase entry = new DictionaryDatabase(SearchableDictionary.this);
                entry.open();
                entry.createEntry(data[0],data[1]);
                entry.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        Intent i = new Intent(SearchableDictionary.this,DictionaryProvider.class);
        String k = getIntent().getExtras().getString("key");
        i.putExtra("key",k);
        startActivity(i);
    }

}
