package com.cs115.rex;


import android.database.Cursor;
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
        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dog_info, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        View view = getView();
        nameET = view.findViewById(R.id.nameView);
        nameET.setEnabled(isEditing);

        breedET = view.findViewById(R.id.breedView);
        breedET.setEnabled(isEditing);

        weightET = view.findViewById(R.id.WeightView);
        weightET.setEnabled(isEditing);

        if (isRestored) {
            nameET.setText(name);
            weightET.setText(weight);
            breedET.setText(breed);
        } else {
            Cursor cursor = RexDatabaseUtilities.getDog(view.getContext());
            if (cursor.moveToFirst()) {
                nameET.setText(cursor.getString(0));
                weightET.setText(cursor.getString(1));
                breedET.setText(cursor.getString(2));
            }
        }
    }

    /**
     * Called by ProfileActivity on Edit / Save button press.
     */
    public void changeEditableStatus(){
        isEditing = !isEditing;
        nameET.setEnabled(isEditing);
        breedET.setEnabled(isEditing);
        weightET.setEnabled(isEditing);
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString("name", nameET.getText().toString());
        savedInstanceState.putString("breed", breedET.getText().toString());
        savedInstanceState.putString("weight", weightET.getText().toString());
        savedInstanceState.putBoolean("isRestored", true);
        savedInstanceState.putBoolean("isEditing", true);
    }

    @Override
    public void onStop() {
        super.onStop();
    }
    // get cursor of dog from database
    // load editTextView values from cursor
    // on save:
    // compare all editTextViews to cursor values
    // run appropriate methods (where values != )
}




