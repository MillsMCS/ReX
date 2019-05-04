package com.cs115.rex;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 */
public class AllergyInfoFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    private static final String TAG = "allergy_info_frag";
    private String[] allFoodNames;
    private int[] allFoodIds;
    private String[] currentAllergies;
    private ArrayList<String> addedAllergies;    // must be ArrayList because List is not serializable for Bundle
    private ArrayList<String> deletedAllergies;  // must be ArrayList because List is not serializable for Bundle
    private boolean isEditing;
    private boolean spinnerCheck;
    private boolean isRestored;
    private Spinner foodsSpinner;
    private Activity activity;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (savedInstanceState != null) {
            parseBundle(savedInstanceState);
            isRestored = true;
        }
        return inflater.inflate(R.layout.fragment_allergy_info, container, false);
    }

    private void parseBundle(Bundle savedInstanceState){
        allFoodNames = savedInstanceState.getStringArray("allFoodNames");
        currentAllergies = savedInstanceState.getStringArray("currentAllergies");
        allFoodIds = savedInstanceState.getIntArray("allFoodIds");
        addedAllergies = savedInstanceState.getStringArrayList("addedAllergies");
        deletedAllergies = savedInstanceState.getStringArrayList("deletedAllergies");
        isEditing = savedInstanceState.getBoolean("isEditing");
    }

    @Override
    public void onStart() {
        super.onStart();
        activity = getActivity();
        if (!isRestored) {
            setInstanceVariables();
        }
        makeSpinner();
        createButtons();
    }

    private void setInstanceVariables(){
        allFoodNames = RexDatabaseUtilities.getAllFoodNames(activity);
        allFoodIds = RexDatabaseUtilities.getAllFoodId(activity);
        currentAllergies = RexDatabaseUtilities.getAllergyNames(activity, String.valueOf(RexDatabaseHelper.SINGLE_DOG_ID));
        addedAllergies = new ArrayList<>();
        deletedAllergies = new ArrayList<>();
    }

    private void makeSpinner(){
        foodsSpinner = activity.findViewById(R.id.all_foods_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(activity,
                                                          R.layout.spinner,
                                                          allFoodNames);
        foodsSpinner.setAdapter(adapter);
        foodsSpinner.setOnItemSelectedListener(this);
        foodsSpinner.setVisibility(isEditing ? View.VISIBLE : View.GONE);
    }

    private void createButtons(){
        for (String allergen : currentAllergies) {
            if (!deletedAllergies.contains(allergen)) {
                displayAllergy(activity, allergen);
            }
        }
        for (String allergen : addedAllergies) {
            displayAllergy(activity, allergen);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> View, View selectedView, int position, long id) {
        // if block needed to keep spinner from adding the first food to allergies upon initialization
        // https://stackoverflow.com/questions/13397933/android-spinner-avoid-onitemselected-calls-during-initialization
        if (spinnerCheck) {
            String newAllergy = allFoodNames[position];
            if (deletedAllergies.contains(newAllergy)) {
                deletedAllergies.remove(newAllergy);
                displayAllergy(activity, newAllergy);
            } else if (!Arrays.asList(currentAllergies).contains(newAllergy) && !addedAllergies.contains(newAllergy)) {
                displayAllergy(activity, newAllergy);
                addedAllergies.add(newAllergy);
            }
        } else {
            spinnerCheck = true;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // Auto-generated method stub.
        Log.d(TAG, "nothingSelected");
    }

    private void displayAllergy(Activity activity, String allergen) {
        // make a new button
        Button button = new Button(activity);
        button.setOnClickListener(this);
        button.setEnabled(isEditing);
        button.setText(allergen);

        // add the button to the Linear Layout that holds them
        LinearLayout allergies_list = activity.findViewById(R.id.allergies_list);
        allergies_list.addView(button);
    }

    // TODO: Method for rendering on Edit click (called in ProfileActivity)
    public void onEditandSaveAction() {
        if (isEditing) {
            for (String food : addedAllergies) {
                int index = Arrays.asList(allFoodNames).indexOf(food);
                int foodId = allFoodIds[index];
                RexDatabaseUtilities.addAllergy(activity, foodId, RexDatabaseHelper.SINGLE_DOG_ID);
            }
            for (String food : deletedAllergies) {
                int index = Arrays.asList(allFoodNames).indexOf(food);
                int foodId = allFoodIds[index];
                RexDatabaseUtilities.removeAllergy(activity, String.valueOf(foodId), String.valueOf(RexDatabaseHelper.SINGLE_DOG_ID));
            }
            addedAllergies.clear();
            deletedAllergies.clear();
        }
        foodsSpinner.setVisibility(isEditing ? View.INVISIBLE : View.VISIBLE);
        isEditing = !isEditing;
        toggleButtonEnabledStatus();
    }

    private void toggleButtonEnabledStatus() {
        LinearLayout buttonHolder = activity.findViewById(R.id.allergies_list);
        int numOfButtons = buttonHolder.getChildCount();
        for (int i = 0; i < numOfButtons; i++) {
            Button b = (Button) buttonHolder.getChildAt(i);
            b.setEnabled(isEditing);
        }
    }

    @Override
    public void onClick(View v) {
        // get which allergy the user wants to remove
        String allergySelected = ((Button) v).getText().toString();

        // store that the allergy was removed
        deletedAllergies.add(allergySelected);
        addedAllergies.remove(allergySelected);

        // delete button from list
        LinearLayout container = (LinearLayout) v.getParent();
        container.removeView(v);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putStringArray("allFoodNames", allFoodNames);
        savedInstanceState.putStringArray("currentAllergies", currentAllergies);
        savedInstanceState.putIntArray("allFoodIds", allFoodIds);
        savedInstanceState.putStringArrayList("addedAllergies", addedAllergies);
        savedInstanceState.putStringArrayList("deletedAllergies", deletedAllergies);
        savedInstanceState.putBoolean("isEditing", isEditing);
    }
}