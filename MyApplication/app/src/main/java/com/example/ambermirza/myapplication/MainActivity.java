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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_myactivity, menu);
        return true;
    }
    public void map(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }
    //jk not using this anymore
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Toast.makeText(getApplicationContext(),	item.getTitle() + " selected", Toast.LENGTH_SHORT).show();

        switch (item.getItemId()) {
            case R.id.mapMenu:
                // do something
                break;
            case R.id.favMenu:
                // do something
                break;
            case R.id.listMenu:
                // do something
                break;
            case R.id.settingsMenu:
                // do something
                break;
        }
        return true;
    }

}
