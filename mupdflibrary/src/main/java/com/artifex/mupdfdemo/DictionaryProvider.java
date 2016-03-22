package com.artifex.mupdfdemo;

import android.app.Activity;
import android.app.SearchManager;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by User on 05-03-2016.
 */
public class DictionaryProvider extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sqlview);
        TextView tvk = (TextView)findViewById(R.id.tvKey);
        TextView tvd = (TextView)findViewById(R.id.tvDef);
        DictionaryDatabase info = new DictionaryDatabase(this);
        info.open();
        String key = getIntent().getExtras().getString("key");
        ArrayList<String> k = info.getData(key);
        info.close();
        if(!k.isEmpty()) {
            tvk.setText(k.get(0));
            tvd.setText(k.get(1));
        }else{
            tvk.setText("not found");
        }
    }
}
