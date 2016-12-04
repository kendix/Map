package com.example.ambermirza.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.util.Calendar;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
public class PlaceDescription extends AppCompatActivity {
    static final int START_PUZZLE_REQUEST = 1337;
    Intent fromMapActivity;
    Bitmap mPlacePic;
    String mPlaceName;
    Double mLat, buidlingLat;
    Double mLng, buidlingLng;
    private LocationManager mLocationmanager;
    final String TAG = "PLACE_DESCRIPTION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_description);
        mPlacePic = BitmapFactory.decodeResource(getResources(), R.drawable.stub);
        mLocationmanager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        fromMapActivity = getIntent();
        TextView placeName = (TextView) findViewById(R.id.place_name);
        mPlaceName = fromMapActivity.getStringExtra("placeName");
        placeName.setText(mPlaceName);

        TextView completed = (TextView) findViewById(R.id.place_complete);
        if (fromMapActivity.getBooleanExtra("completed", false)) {
            completed.setText(R.string.completed);
        } else {
            completed.setText(R.string.incomplete);
        }
        mLat = fromMapActivity.getDoubleExtra("mLat",0);
        mLng = fromMapActivity.getDoubleExtra("mLng",0);
        buidlingLat = fromMapActivity.getDoubleExtra("lat", 0);
        buidlingLng = fromMapActivity.getDoubleExtra("lng", 0);

        GetPhotoAsynctask task = new GetPhotoAsynctask(this);
        task.execute(fromMapActivity.getStringExtra("placePic"));

        Button playButton = (Button) findViewById(R.id.play_puzzle);
        playButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "begin distance calculations");
                final int R = 6371; // Radius of the earth
                Double latDistance = Math.toRadians(mLat - buidlingLat);
                Double lonDistance = Math.toRadians(mLng - buidlingLng);
                Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                        + Math.cos(Math.toRadians(buidlingLat)) * Math.cos(Math.toRadians(mLat))
                        * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
                Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
                double distance = R * c * 1000; // convert to meters
                distance = Math.pow(distance, 2);
                Log.i(TAG, "finished distance calculations");



                if (Math.sqrt(distance) > 150) {
                    Toast.makeText(PlaceDescription.this,
                            "You are " + distance + " meters from the building.",
                            Toast.LENGTH_LONG).show();
                    Toast.makeText(PlaceDescription.this,
                            "You need to get closer to start this puzzle!",
                            Toast.LENGTH_LONG).show();
                } else {
                    // TODO - change puzzleactivity to Daniels activity
                    Intent startPuzzle = new Intent(PlaceDescription.this, PuzzleActivity.class);
                    startPuzzle.putExtra("placePic", mPlacePic);
                    startPuzzle.putExtra("placeName", mPlaceName);

                    startActivityForResult(startPuzzle, START_PUZZLE_REQUEST);
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == START_PUZZLE_REQUEST) {

            // Todo -- manage the result sent by Daniel's puzzle

            // Todo -- change completed to whatever key Daniel used
            boolean completed = data.getIntExtra("completed", 0) == 1;
            if (completed) {
                // Todo -- use the POSTAsyncTask to post that the puzzle was completed
            }
            Intent returnIntent = new Intent();
            returnIntent.putExtra("completed", completed);
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
            // Victory Screen??
        }
        if (resultCode == Activity.RESULT_CANCELED) {
            Intent returnIntent = new Intent();
            setResult(Activity.RESULT_CANCELED, returnIntent);
            finish();
        }
    }

    public void addPlacePic(Bitmap placePic) {
        ImageView placeImage = (ImageView) findViewById(R.id.place_image);
        placeImage.setImageBitmap(placePic);
        mPlacePic = placePic;
    }
}
