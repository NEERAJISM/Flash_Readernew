package com.knightandday.neeraj.flash_reader;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;

import java.io.File;

/**
 * Created by Neeraj on 02-Mar-16.
 */

public class Start extends Activity {
    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = getSharedPreferences("Start_Data", Context.MODE_PRIVATE);
        Boolean b = sharedPreferences.getBoolean("start_value", false);
        Intent i = new Intent(Start.this, MainActivity.class);

        if (b == true)
        {
            startActivity(i);
            //close Start
            finish();
        }
        else
        {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("start_value",true);
            editor.commit();
            setContentView(R.layout.start);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(Start.this, MainActivity.class);
                    startActivity(i);
                    //close Start
                    finish();
                }
            }, SPLASH_TIME_OUT);
        }

    }
}