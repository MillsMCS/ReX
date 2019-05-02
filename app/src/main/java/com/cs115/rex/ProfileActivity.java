package com.cs115.rex;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
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


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class ProfileActivity extends AppCompatActivity {
    private Button btn;
    private String TAG = "profileActivity";
    private ImageView imageview;
    private static final String IMAGE_DIRECTORY = " /directory";
    private int GALLERY = 1, CAMERA = 2;
    private boolean isEditing;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // set toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // set Save / Edit Button
        final Button editAndSaveBtn = findViewById(R.id.edit_button);
        String edit_or_save = isEditing ? "Save" : "Edit";
        editAndSaveBtn.setText(edit_or_save);

        // get Fragments so we can set onclick listeners
        FragmentManager fm = getSupportFragmentManager();
        final DogInfoFragment dogInfoFrag = (DogInfoFragment) fm.findFragmentById(R.id.dog_info_frag);
        final AllergyInfoFragment allergyFrag = (AllergyInfoFragment) fm.findFragmentById(R.id.allergy_info_frag);
        editAndSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "edit / save button clicked " + Boolean.toString(isEditing));
                isEditing = !isEditing;
                String edit_or_save = isEditing ? "Save" : "Edit";
                editAndSaveBtn.setText(edit_or_save);
                dogInfoFrag.makeEditable();
                allergyFrag.makeEditable();
            }
        });

        // set Select Photo button
        btn = (Button) findViewById(R.id.select_photo);
        imageview = (ImageView) findViewById(R.id.photo);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog();
            }
        });
    }

    //TODO consider refactoring this to avoid code repetition
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
            case R.id.action_profile_info:
                //TODO code to activate edit profile functionality
                return true;
            case R.id.action_home:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void showPictureDialog(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera" };
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallery();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {

            if (data != null) {
                Uri contentURI = data.getData();
                Log.d("onActivityResult", contentURI.toString());

                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    String path = saveImage(bitmap);
                    Toast.makeText(ProfileActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
                    imageview.setImageURI(Uri.parse(contentURI.toString()));

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(ProfileActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            imageview.setImageBitmap(thumbnail);
            saveImage(thumbnail);


            Toast.makeText(ProfileActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
        }
    }

    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::---&gt;" + f.getAbsolutePath());

            RexDatabaseUtilities util = new RexDatabaseUtilities();
            util.updatePhoto(this, f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }

}
