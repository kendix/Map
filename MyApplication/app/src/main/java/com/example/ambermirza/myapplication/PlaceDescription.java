package com.example.ambermirza.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class PlaceDescription extends AppCompatActivity {
    static final int START_PUZZLE_REQUEST = 1337;
    Intent fromMapActivity = getIntent();
    Bitmap mPlacePic;
    String mPlaceName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_description);
        mPlacePic = BitmapFactory.decodeResource(getResources(), R.drawable.stub);

        TextView placeName = (TextView) findViewById(R.id.place_name);
        mPlaceName = fromMapActivity.getStringExtra("placeName");
        placeName.setText(mPlaceName);

        GetPhotoAsynctask task = new GetPhotoAsynctask(this);
        task.execute(mPlaceName);

        Button playButton = (Button) findViewById(R.id.play_puzzle);
        playButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO - change puzzleactivity to Daniels activity
                Intent startPuzzle = new Intent(PlaceDescription.this, PuzzleActivity.class);
                startPuzzle.putExtra("placePic", mPlacePic);
                startPuzzle.putExtra("placeName", mPlaceName);

                startActivityForResult(startPuzzle, START_PUZZLE_REQUEST);
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
