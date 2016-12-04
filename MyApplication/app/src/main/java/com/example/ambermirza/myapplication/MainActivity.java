package com.example.ambermirza.myapplication;

import android.app.Activity;
import android.os.Bundle;

import android.view.MenuInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.view.View;
import android.content.Intent;

/**
 * Created by ambermirza on 12/3/16.
 */

public class MainActivity extends Activity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    //function called when user hits menu item "MAP"
    public void map(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    //TODO finish
    //function called when user hits menu item "GALLERY"
    public void gallery(View view) {

    }

    //TODO finish
    //function called when user hits menu item "TAKE PHOTO"
    public void takePhoto(View view) {

    }



}
