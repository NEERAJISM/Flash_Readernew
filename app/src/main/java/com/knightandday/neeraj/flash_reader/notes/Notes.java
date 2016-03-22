package com.knightandday.neeraj.flash_reader.notes;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.knightandday.neeraj.flash_reader.CustomListViewAdapter;
import com.knightandday.neeraj.flash_reader.R;
import com.knightandday.neeraj.flash_reader.RowItem;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.TimeZone;


/**
 * Created by Neeraj on 01-Mar-16.
 */
public class Notes extends ActionBarActivity {
    ListView lv;
    ArrayList<String> FilesInFolder = new ArrayList<String>();
    ArrayList<String> sizef = new ArrayList<String>();
    ArrayList<String> datef = new ArrayList<String>();
    List<RowItem> rowItemsf;
    String tmp,str;
    int flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notes_main);

        //setting up list view
        FilesInFolder = GetFiles("/sdcard/" + getString(R.string.folder_main) + "/" + getString(R.string.folder_main_notes));

        rowItemsf = new ArrayList<RowItem>();
        for (int i = 0; i < sizef.size(); i++) {
            RowItem item = new RowItem(FilesInFolder.get(i),sizef.get(i),datef.get(i));
            rowItemsf.add(item);
        }


        lv = (ListView) findViewById(R.id.lvnotes);
        CustomListViewAdapter adapterf = new CustomListViewAdapter(this, R.layout.custom_adapter, rowItemsf);
        lv.setAdapter(adapterf);

        //click actions
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                String s = (FilesInFolder.get(position)).toString();
                Scanner scanner = null;
                try {
                    scanner = new Scanner(new File("/sdcard/" + getString(R.string.folder_main) + "/" + getString(R.string.folder_main_notes) + "/" + s));
                    ArrayList<String> list = new ArrayList<String>();

                    str = "";
                    flag = 0;
                    while (scanner.hasNext()){
                        tmp = scanner.next();

                        if(tmp.equals("123456789)(*&^%$#@!"))
                        {
                            flag = 1;
                            list.add(str);
                            str = "";
                        }
                        else
                        {
                            str=str+(tmp+" ");
                            flag = 2;
                        }
                    }
                    scanner.close();

                    if(flag == 2)
                        list.add(str);

                    if (list.size() == 0) {
                        Toast.makeText(Notes.this, "No Notes Found for pdf: " + s, Toast.LENGTH_SHORT).show();
                    } else {
                        //Toast.makeText(Notes.this, "Notes Found for pdf:" + s, Toast.LENGTH_SHORT).show();
                        Intent nxt = new Intent(Notes.this, Notes_Card_View.class);
                        nxt.putStringArrayListExtra("Pdf_notes", list);
                        startActivity(nxt);
                    }

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });


        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){

            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l)
            {
                String s = (FilesInFolder.get(position)).toString();
                String path1 =  "/sdcard/" + getString(R.string.folder_main) + "/" + getString(R.string.folder_main_notes);
                final File file = new File(path1,s);
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
                startActivity(intent);
                return true;

            }
        });
    }

    //provides the list of all the folders in the folder "NOTES" from FLASH READER
    public ArrayList<String> GetFiles(String DirectoryPath) {
        ArrayList<String> MyFiles = new ArrayList<String>();
        File f = new File(DirectoryPath);
        f.mkdirs();
        File[] files = f.listFiles();
        if (files.length == 0)
            return null;
        else {
            for (int i=0; i<files.length; i++)
            {    if(files[i].getName().toLowerCase().endsWith("_flash.txt")) {
                     MyFiles.add(files[i].getName());
                     sizef.add(String.valueOf((float) (files[i].length())/1048576).substring(0, 5) + " MB");

                    //converting lasr modifed tio dd-mm-yyyy
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
                    sdf.setTimeZone(TimeZone.getDefault());
                    datef.add("Last modified: "+ sdf.format(files[i].lastModified()));
                }
            }
        }
        return MyFiles;
    }
}
