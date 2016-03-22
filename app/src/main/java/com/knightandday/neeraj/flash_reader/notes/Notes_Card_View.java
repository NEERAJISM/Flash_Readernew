package com.knightandday.neeraj.flash_reader.notes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.support.v7.app.ActionBarActivity;

import com.knightandday.neeraj.flash_reader.CustomListViewAdapter;
import com.knightandday.neeraj.flash_reader.CustomListViewAdapterCard;
import com.knightandday.neeraj.flash_reader.R;
import com.knightandday.neeraj.flash_reader.RowItem;
import com.knightandday.neeraj.flash_reader.RowItemCard;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Neeraj on 02-Mar-16.
 */
public class Notes_Card_View extends ActionBarActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    List<RowItemCard> rowItemsc;
    ListView lvc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notes_recycler_view);

        Intent i = getIntent();
        ArrayList<String> list = i.getStringArrayListExtra("Pdf_notes");


        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerAdapter(list);
        recyclerView.setAdapter(adapter);


        /*lvc = (ListView)findViewById(R.id.lvnrv);

        rowItemsc = new ArrayList<RowItemCard>();
        for (int it = 0; it < list.size(); it++) {
            RowItemCard item = new RowItemCard(list.get(it));
            rowItemsc.add(item);
        }

        CustomListViewAdapterCard adapterf = new CustomListViewAdapterCard(this, R.layout.custom_adapter_card, rowItemsc);
        lvc.setAdapter(adapterf);
*/

//*******************************************************************
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setLogo(R.drawable.ic_menu_send);
//*********************************************************************
/*
        //setting up list view
        ListView lv;
        lv = (ListView) findViewById(R.id.lvnrv);
        lv.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, list));
*/

    }
}