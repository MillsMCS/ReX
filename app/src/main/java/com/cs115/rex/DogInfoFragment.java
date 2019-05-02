package com.cs115.rex;


import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


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
        if (savedInstanceState != null) {
            name = savedInstanceState.getString("name");
            breed = savedInstanceState.getString("breed");
            weight = savedInstanceState.getString("weight");
            isRestored = savedInstanceState.getBoolean("isRestored");
            isEditing = savedInstanceState.getBoolean("isEditing");
            oldName = savedInstanceState.getString("oldName");
            oldBreed = savedInstanceState.getString("oldBreed");
            oldWeight = savedInstanceState.getString("oldWeight");
        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dog_info, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        View view = getView();

        // set up EditText Views
        nameET = view.findViewById(R.id.nameView);
        nameET.setEnabled(isEditing);
        breedET = view.findViewById(R.id.breedView);
        breedET.setEnabled(isEditing);
        weightET = view.findViewById(R.id.WeightView);
        weightET.setEnabled(isEditing);

        // if we have restored a previous state, put in String values
        if (isRestored) {
            nameET.setText(name);
            weightET.setText(weight);
            breedET.setText(breed);

        // if this is the first time loading the Activity, load values from database
        } else {
            Cursor cursor = RexDatabaseUtilities.getDog(view.getContext());
            if (cursor.moveToFirst()) {
                // put values in String variables so we can close cursor
                oldName = cursor.getString(0);
                oldWeight = cursor.getString(1);
                oldBreed = cursor.getString(2);

                // set Edit Texts to values
                nameET.setText(oldName);
                weightET.setText(oldWeight);
                breedET.setText(oldBreed);

                cursor.close();
            }
        }
    }

    /**
     * Called by ProfileActivity on Edit / Save button press.
     * //TODO write more Javadoc
     */
    public void changeEditableStatus(){

        // if user is Editing, compare their new values to their old values
        if (isEditing){
//            String newName = nameET.getText().toString();
//            String newBreed = breedET.getText().toString();
//            String newWeight = weightET.getText().toString();

            new UpdateDogInfo().execute(nameET.getText().toString(),
                                        breedET.getText().toString(),
                                        weightET.getText().toString());

//            if (!oldName.equals(newName)) {
//                RexDatabaseUtilities.updateName(getActivity().getApplicationContext(), newName);
//            }
//            if (!oldBreed.equals(newBreed)){
//                RexDatabaseUtilities.updateBreed(getActivity().getApplicationContext(), newBreed);
//            }
//            if (!oldWeight.equals(newWeight)){
//                RexDatabaseUtilities.updateWeight(getActivity().getApplicationContext(), newWeight);
//            }
        }
        isEditing = !isEditing;
        nameET.setEnabled(isEditing);
        breedET.setEnabled(isEditing);
        weightET.setEnabled(isEditing);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        // save original values
        savedInstanceState.putString("oldName", oldName);
        savedInstanceState.putString("oldBreed", oldBreed);
        savedInstanceState.putString("oldWeight", oldWeight);

        // save current values
        savedInstanceState.putString("name", nameET.getText().toString());
        savedInstanceState.putString("breed", breedET.getText().toString());
        savedInstanceState.putString("weight", weightET.getText().toString());

        // save booleans
        savedInstanceState.putBoolean("isRestored", true);
        savedInstanceState.putBoolean("isEditing", true);
    }

    private class UpdateDogInfo extends AsyncTask<String, Void, Boolean>{
        @Override
        protected Boolean doInBackground(String... params) {
            String newName = params[0];
            String newBreed = params[1];
            String newWeight = params[2];

            if (!oldName.equals(newName)) {
                RexDatabaseUtilities.updateName(getActivity().getApplicationContext(), newName);
            }
            if (!oldBreed.equals(newBreed)){
                RexDatabaseUtilities.updateBreed(getActivity().getApplicationContext(), newBreed);
            }
            if (!oldWeight.equals(newWeight)){
                RexDatabaseUtilities.updateWeight(getActivity().getApplicationContext(), newWeight);
            }

            return null;
        }
    }
}




