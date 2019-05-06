package com.cs115.rex;


import android.app.Activity;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;


/**
 *
 */
public class DogInfoFragment extends Fragment {
    private static final String TAG = "DOGFRAGMENT";
    private EditText nameET;
    private EditText weightET;
    private EditText breedET;

    private String oldName;
    private String oldBreed;
    private String oldWeight;

    private String name;
    private String breed;
    private String weight;

    private boolean isRestored;
    private boolean isEditing;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        // if we are restoring from Bundle, set instance variables from Bundle
        if (savedInstanceState != null) {
            unpackBundle(savedInstanceState);
            isRestored = true;
        }
        return inflater.inflate(R.layout.fragment_dog_info, container, false);
    }

    private void unpackBundle(Bundle savedInstanceState) {
        name = savedInstanceState.getString("name");
        breed = savedInstanceState.getString("breed");
        weight = savedInstanceState.getString("weight");
        isEditing = savedInstanceState.getBoolean("isEditing");
        oldName = savedInstanceState.getString("oldName");
        oldBreed = savedInstanceState.getString("oldBreed");
        oldWeight = savedInstanceState.getString("oldWeight");
    }

    @Override
    public void onStart() {
        super.onStart();

        // get Activity's view
        View view = getView();

        // set up EditTexts
        nameET = view.findViewById(R.id.nameView);
        breedET = view.findViewById(R.id.breedView);
        weightET = view.findViewById(R.id.WeightView);

        // if we have restored from a previous state, put in String values
        if (isRestored) {
            nameET.setText(name);
            weightET.setText(weight);
            breedET.setText(breed);

        // otherwise load values in from database
        } else {
            Cursor cursor = RexDatabaseUtilities.getDog(view.getContext());

            if (cursor != null && cursor.moveToFirst()) {
                // get values
                oldName = cursor.getString(0);
                oldWeight = cursor.getString(1);
                oldBreed = cursor.getString(2);

                // set EditTexts
                nameET.setText(oldName);
                weightET.setText(oldWeight);
                breedET.setText(oldBreed);

                cursor.close();
            }
        }
        nameET.setEnabled(isEditing);
        breedET.setEnabled(isEditing);
        weightET.setEnabled(isEditing);
    }

    /**
     * Called by ProfileActivity on Edit / Save button press.
     * //TODO write more Javadoc
     */
    public void activityButtonPress(){
        // if user pressed save, send data to thread for possible addition to db
        if (isEditing){
            new UpdateDogInfo().execute(nameET.getText().toString(),
                                  breedET.getText().toString(),
                                  weightET.getText().toString(),
                                  oldName,
                                  oldBreed,
                                  oldWeight,
                                  getActivity());
        }
        // change Button and editText states
        isEditing = !isEditing;
        nameET.setEnabled(isEditing);
        breedET.setEnabled(isEditing);
        weightET.setEnabled(isEditing);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        // save original EditText values
        savedInstanceState.putString("oldName", oldName);
        savedInstanceState.putString("oldBreed", oldBreed);
        savedInstanceState.putString("oldWeight", oldWeight);

        // save current EditText values
        savedInstanceState.putString("name", nameET.getText().toString());
        savedInstanceState.putString("breed", breedET.getText().toString());
        savedInstanceState.putString("weight", weightET.getText().toString());

        // save booleans
        savedInstanceState.putBoolean("isEditing", isEditing);
    }

    private class UpdateDogInfo extends AsyncTask<Object, Void, Activity>{
        @Override
        protected Activity doInBackground(Object... params) {
            String newName = (String) params[0];
            String newBreed = (String) params[1];
            String newWeight = (String) params[2];
            String oldName = (String) params[3];
            String oldBreed = (String) params[4];
            String oldWeight = (String) params[5];
            Activity activity = (Activity) params[6];

            // only update columns where the user has changed the value
            if (!oldName.equals(newName)) {
                RexDatabaseUtilities.updateName(activity, newName);
            }
            if (!oldBreed.equals(newBreed)){
                RexDatabaseUtilities.updateBreed(activity, newBreed);
            }
            if (!oldWeight.equals(newWeight)){
                RexDatabaseUtilities.updateWeight(activity, newWeight);
            }
            return activity;
        }

        @Override
        protected void onPostExecute(Activity activity) {
            Toast toast = Toast.makeText(activity, "Changes saved.", Toast.LENGTH_LONG);
            toast.show();
        }
    }
}
