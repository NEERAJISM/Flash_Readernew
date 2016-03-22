package com.knightandday.neeraj.flash_reader;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.artifex.mupdfdemo.MuPDFActivity;
import com.knightandday.neeraj.flash_reader.notes.Notes;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static Context context;
    ArrayList<File> files_list = new ArrayList<File>();
    ArrayList<String> title = new ArrayList<>();
    ArrayList<String> size = new ArrayList<>();
    ArrayList<String> date = new ArrayList<>();
    ListView list;
    List<RowItem> rowItems;
    private final String TEMP_FILE_NAME = "flash.txt";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page_all);
        //***************CREATE FOLDERS****************************
        if(isExternalStorageWritable()) {
            //create app specific directory
            File f = new File(Environment.getExternalStorageDirectory(), getString(R.string.folder_main));
            if (!f.exists()) {
                f.mkdirs();
            }
            //create notes directory
            File f1 = new File(Environment.getExternalStorageDirectory() + "/" + getString(R.string.folder_main), getString(R.string.folder_main_notes));
            if (!f1.exists()) {
                f1.mkdirs();
            }
        }
        else{
            Toast.makeText(MainActivity.this,"External SD Card Not Avialble",Toast.LENGTH_LONG);
            finish();
        }

        //*****************************************************************************
        context = MainActivity.this;

        File cDir = getBaseContext().getCacheDir();
        File tempFile = new File(cDir.getPath()+ "/" + TEMP_FILE_NAME);
        String strline = "";

        readFromFile(tempFile.getAbsolutePath());
        //****************************SEE USER PREFERENCE FOR SORTING BY NAME OR SIZE ******************
        /*Collections.sort(title, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return s1.compareToIgnoreCase(s2);
            }
        });*/

        rowItems = new ArrayList<RowItem>();
        for (int i = 0; i < title.size(); i++) {
            RowItem item = new RowItem(title.get(i),size.get(i),date.get(i));
            rowItems.add(item);
        }

        //making a listview
        list = (ListView)findViewById(R.id.list);
        CustomListViewAdapter adapter = new CustomListViewAdapter(this, R.layout.custom_adapter, rowItems);
        list.setAdapter(adapter);

        //setting onitemclock listener
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(MainActivity.this,"skrjgbwg",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(MainActivity.this,MuPDFActivity.class);
                String s = (files_list.get(position)).getName();
                i.putExtra("FileNameKey",files_list.get(position).getAbsolutePath());
                i.putExtra("FileName",s.substring(0, s.length() - 4));
                i.putExtra("path",Environment.getExternalStorageDirectory() + "/" + getString(R.string.folder_main) + "/" + getString(R.string.folder_main_notes));
                startActivity(i);
            }
        });
        //**********************************************************************************************
        //floating button
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Read Your Notes", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(MainActivity.this,Notes.class);
                startActivity(i);
            }
        });
//****************************************************************************************************************************
        //test the first time visit of the app ADD THE TUTORAIL IN NAVIGATION DRAWER
        //toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Drawer
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void readFromFile(String fileName) {

        try{
            FileInputStream fis = new FileInputStream(fileName);
            ObjectInputStream ois = new ObjectInputStream(fis);
            files_list = (ArrayList<File>) ois.readObject();
            ois.close();
            for (File f:files_list){
                title.add(f.getName());
                size.add(String.valueOf((float) (f.length()) / 1048576).substring(0, 5) + " MB");

//converting last modified to dd-MM-yyyy
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
                sdf.setTimeZone(TimeZone.getDefault());
                date.add("Last modified on: " + sdf.format(f.lastModified()));
            }
        }catch (Exception e) {
//Log.e(TAG, "whoa there is an error");
            e.printStackTrace();
            walkdir(new File("/storage"));
            writeToFile(files_list, fileName);
        }
    }

    private void writeToFile(ArrayList<File> data,String fileName) {
        try {
            FileOutputStream fos = new FileOutputStream(fileName);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(data);
            oos.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    //////*****************************************************************
    //function to find all the pdf
    public void walkdir(File dir) {
        File listFile[];
        listFile = dir.listFiles();
        if (listFile != null) {
            for (int i = 0; i < listFile.length; i++) {
                if (listFile[i].isDirectory()) {
                    walkdir(listFile[i]);
                } else {
                    if (listFile[i].getName().toLowerCase().endsWith(".pdf")) {
                        files_list.add((listFile[i]));
                        title.add(listFile[i].getName());
                        size.add(String.valueOf((float)(listFile[i].length())/1048576).substring(0,5)+" MB");

                        //converting lasr modifed tio dd-mm-yyyy
                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
                        sdf.setTimeZone(TimeZone.getDefault());
                        date.add("Last modified: "+ sdf.format(listFile[i].lastModified()));
                    }
                }
            }
        } else {
            Toast.makeText(MainActivity.this, "file is empty", Toast.LENGTH_SHORT).show();
        }
    }

    //**************************************************************************************
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }
}
