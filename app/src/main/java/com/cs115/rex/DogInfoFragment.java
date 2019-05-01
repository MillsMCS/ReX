package com.cs115.rex;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


/**
 *
 */
public class DogInfoFragment extends Fragment {
    private static final String TAG = "DOGFRAGMENT";
    private String name;
    private String breed;
    private String weight;
    private boolean info;
    private boolean hasInfo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        if(savedInstanceState != null){
            name = savedInstanceState.getString("name");
            breed = savedInstanceState.getString("breed");
            weight = savedInstanceState.getString("weight");
            hasInfo = savedInstanceState.getBoolean("hasInfo");
        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dog_info, container, false);

    }
    @Override
    public void onStart(){
        super.onStart();
        if(hasInfo) {
            info = true;
            View view = getView();

            Log.d(TAG, "we made it here");
            Cursor cursor = RexDatabaseUtilities.getDog(view.getContext());
            Log.d(TAG, "we made it here");

            EditText name = view.findViewById(R.id.nameView);
            Log.d(TAG, "we made it here");

            EditText breed = view.findViewById(R.id.breedView);
            Log.d(TAG, "we made it here");

            EditText weight = view.findViewById(R.id.WeightView);
            Log.d(TAG, "we made it here");

            if (cursor.moveToFirst()) {
                name.setText(cursor.getColumnIndex("name"));
                breed.setText(cursor.getColumnIndex("breed"));
                weight.setText(cursor.getColumnIndex("weight"));
            }
        }

    }
    public void onInstanceState(Bundle savedInstanceState){
        savedInstanceState.putString("name", name);
        savedInstanceState.putString("breed", breed);
        savedInstanceState.putString("weight", weight);
        savedInstanceState.putBoolean("hasInfo", hasInfo);
    }

    @Override
    public void onStop(){
        super.onStop();
        hasInfo = info;
        info = false;
    }
    // get cursor of dog from database
    // load editTextView values from cursor
    // on save:
    // compare all editTextViews to cursor values
    // run appropriate methods (where values != )
}
