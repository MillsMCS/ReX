package com.cs115.rex;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Top-level activity for the application's profile function. Displays and allows editing of dog profile.
 * Does not have sub-activities, but calls {@link DogInfoFragment} and {@link AllergyInfoFragment}.
 */
public class ProfileActivity extends AppCompatActivity {

    private Button button;
    private String TAG = "profileActivity";
    private ImageView imageview;
    private int GALLERY = 1;
    private boolean isRestored, isEditing;
    private String contentURI;
    private DogInfoFragment dogInfoFrag;
    private AllergyInfoFragment allergyFrag;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        // check if we are restoring from a previous state
        if (savedInstanceState != null) {
            contentURI = savedInstanceState.getString("image");
            isRestored = savedInstanceState.getBoolean("isRestored");
            isEditing = savedInstanceState.getBoolean("isEditing");
        }

        // set toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // set Save / Edit Button
        final Button editAndSaveBtn = findViewById(R.id.edit_button);
        String edit_or_save = isEditing ? "Save" : "Edit";
        editAndSaveBtn.setText(edit_or_save);

        // get Fragments so we can set onclick listeners
        FragmentManager fm = getSupportFragmentManager();
        dogInfoFrag = (DogInfoFragment) fm.findFragmentById(R.id.dog_info_frag);
        allergyFrag = (AllergyInfoFragment) fm.findFragmentById(R.id.allergy_info_frag);
        editAndSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "edit / save button clicked " + Boolean.toString(isEditing));
                isEditing = !isEditing;
                String edit_or_save = isEditing ? "Save" : "Edit";
                editAndSaveBtn.setText(edit_or_save);
                dogInfoFrag.onEditandSaveClick();
                allergyFrag.onEditandSaveClick();
            }
        });
        // set Select Photo button
        button = (Button) findViewById(R.id.select_photo);
        imageview = (ImageView) findViewById(R.id.photo);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // if we have restored from a previous state, put in URI values
        if (isRestored && contentURI != null) {
            imageview.setImageURI(Uri.parse(contentURI));
            // otherwise, if this is the first time loading the Activity, load values in from database
        } else {
            Cursor cursor = RexDatabaseUtilities.getDog(this);
            if (cursor.moveToFirst()) {
                DatabaseUtils.dumpCursor(cursor);
                // put values in String variables so we can close cursor
                contentURI = cursor.getString(3);
                if (contentURI != null) {
                    // set URI from the string to be able to pass it to the imageView
                    imageview.setImageURI(Uri.parse(contentURI));
                    cursor.close();
                }
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("image", contentURI);
        savedInstanceState.putBoolean("isEditing", isEditing);
        savedInstanceState.putBoolean("isRestored", true);
    }

    //Menu - adds settings button from profile menu to app bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu to add items to the app bar.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //Menu - activates app bar menu settings button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_home:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Displays dialog for choosing photo from gallery
     * @author Gavin Erezuma
     */
    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery"};
        // add when camera works "Capture photo from camera"
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
            // left switch statement for future iteration that includes camera
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallery();
                        }
                    }
                });
        pictureDialog.show();
    }

    /**
     * Starts the activity to choose photo from gallery
     */
    public void choosePhotoFromGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri uri = data.getData();
                contentURI = uri.toString();
                Log.d("onActivityResult", contentURI);
                imageview.setImageURI(Uri.parse(contentURI));
                Toast.makeText(this, "Image Saved!", Toast.LENGTH_SHORT).show();

                RexDatabaseUtilities.updatePhoto(this, contentURI);
                Log.d(TAG, "Saving picture from gallery to database");
            }
        }
    }
}