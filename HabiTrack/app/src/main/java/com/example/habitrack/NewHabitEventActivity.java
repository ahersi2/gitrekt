package com.example.habitrack;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Handles the creation of New Habit Events
 */
public class NewHabitEventActivity extends AppCompatActivity {

    // connection status
    Boolean isConnected;
    // get controllers
    HabitEventController hec = new HabitEventController(this);
    HabitTypeController htc = new HabitTypeController(this);
    // declare components
    public Integer heID;
    public Integer htID;
    public String htEsID;
    TextView title;
    // Comment
    EditText comment;
    String commentString;
    Boolean isComment = Boolean.FALSE;
    //ImageView eventImage;
    Button addImage;
    private static final int PICK_IMAGE = 100;
    Uri imageUri;
    Button addEvent;
    Boolean isPhoto = Boolean.FALSE;
    Bitmap photo;

    //Map
    Button Map;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_habit_event);
        Intent intent = getIntent();
        //String titleString = intent.getStringExtra("HabitTitle");
        // Get incoming HT's ID
        heID = intent.getIntExtra("habitEventID", -1);
        htID = intent.getIntExtra("habitTypeID", -1);
        isConnected = intent.getBooleanExtra("connection", Boolean.FALSE);
        htEsID = intent.getStringExtra("habitTypeEsID");

        // Get interesting HT's attributes
        String titleString = hec.getHabitEventTitle(heID);
        //String titleString = hcc.getTitle();
        //Log.d("lt","this is "+htID.toString());

        /* initialize views */
        // Set title
        title = (TextView)findViewById(R.id.heTitleView);
        title.setText(titleString);

        // Get comment text box heCommenteditText
        comment = (EditText) findViewById(R.id.heCommentBox);
        // Image button
        addImage = (Button) findViewById(R.id.addImageGallery);

        // Add event button & its functionality
        addEvent = (Button) findViewById(R.id.addEventButton);
        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });



        Map = (Button) findViewById(R.id.mapButton);
        Map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToMap =  new Intent(NewHabitEventActivity.this, MapsActivity.class);
                //Log.d("COOL", String.valueOf(htID));
                goToMap.putExtra("htID", htID);
                goToMap.putExtra("heID", heID);
                goToMap.putExtra("htEsID",htEsID);
                startActivity(goToMap);

            }
        });


        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Verify comment
                if(isPhoto){
                    hec.setHabitEventDecodedPhoto(heID, photo);
                    ImageHandler.Compressor compressor = new ImageHandler.Compressor();
                    compressor.execute(hec.getHabitEvent(heID));
                }
                commentString = comment.getText().toString();
                if(commentString.length() > 0 && commentString.length() < 30){
                    hec.editHabitEventComment(heID, commentString, isConnected);
                }
//
//                    Toast.makeText(NewHabitEventActivity.this, "Error Adding Habit Event",
//                            Toast.LENGTH_SHORT).show();
//                }
                hec.completeHabitEvent(heID);
                if(isConnected) {
                    htc.incrementHTCurrentCounter(htEsID);
                } else {
                    HabitEvent he = hec.getHabitEvent(heID);
                    HabitEventStateManager.getHEStateManager().addCompletedOfflineHE(he);
                }
                finish();
            }
        });
    }


    /*
    Opens the gallery in phone to select a photo
    Proper way to do it derived from this video: https://www.youtube.com/watch?v=OPnusBmMQTw
     */
    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    /*
    Gets the data of the photo user has picked from gallery
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data.getData();
            InputStream inputStream;
            try {
                inputStream = getContentResolver().openInputStream(imageUri);
                photo = BitmapFactory.decodeStream(inputStream);
//                isPhoto = Boolean.TRUE;
                Integer imgSize = photo.getByteCount();
                if(imgSize > 65536){
                    Toast.makeText(NewHabitEventActivity.this, "Image is too large",
                            Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    isPhoto = Boolean.TRUE;
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }

    }

}
