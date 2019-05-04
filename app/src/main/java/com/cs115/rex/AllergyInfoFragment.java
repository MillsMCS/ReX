package com.cs115.rex;


import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

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
    private TextView addAnAllergyTV;
    private ViewGroup allergiesHolder;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            unpackBundle(savedInstanceState);
            isRestored = true;
        }
        return inflater.inflate(R.layout.fragment_allergy_info, container, false);
    }

    private void unpackBundle(Bundle savedInstanceState) {
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
        createSpinner();
        createSpinnerTitle();
        createAllergyButtonsHolder();
        createButtons();
    }

    private void setInstanceVariables() {
        allFoodNames = RexDatabaseUtilities.getAllFoodNames(activity);
        allFoodIds = RexDatabaseUtilities.getAllFoodId(activity);
        currentAllergies = RexDatabaseUtilities.getAllergyNames(activity, String.valueOf(RexDatabaseHelper.SINGLE_DOG_ID));
        addedAllergies = new ArrayList<>();
        deletedAllergies = new ArrayList<>();
    }

    private void createSpinner() {
        foodsSpinner = activity.findViewById(R.id.all_foods_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(activity,
                R.layout.spinner,
                allFoodNames);
        foodsSpinner.setAdapter(adapter);
        foodsSpinner.setOnItemSelectedListener(this);
        foodsSpinner.setVisibility(isEditing ? View.VISIBLE : View.GONE);
    }

    private void createSpinnerTitle(){
        addAnAllergyTV = activity.findViewById(R.id.add_an_allergy);
        addAnAllergyTV.setVisibility(isEditing ? View.VISIBLE : View.GONE);
    }

    private void createAllergyButtonsHolder(){
        allergiesHolder = activity.findViewById(R.id.allergies_list);
    }

    private void createButtons() {
        for (String allergen : currentAllergies) {
            if (!deletedAllergies.contains(allergen)) {
                displayAllergy(activity, allergen);
            }
        }
        for (String allergen : addedAllergies) {
            displayAllergy(activity, allergen);
        }
    }

    private void displayAllergy(Activity activity, String allergen) {
        Button button = new Button(activity);
        button.setOnClickListener(this);
        button.setEnabled(isEditing);
        button.setText(allergen);
        if (!isEditing){
            button.setBackgroundColor(Color.TRANSPARENT);
            button.setTextColor(Color.parseColor("#000000"));
        } else {
            button.setBackgroundResource(android.R.drawable.btn_default_small);
            button.setTextColor(Color.parseColor("#000000"));
        }
        // TODO: https://guides.codepath.com/android/Drawables
//        button.setBackgroundResource(R.drawable.png_test);

        // display the allergy
        allergiesHolder.addView(button);
    }

    public void onEditandSaveAction() {
        if (isEditing) {
            new UpdateAllergies().execute(activity, addedAllergies, deletedAllergies, allFoodNames, allFoodIds);
        }
        foodsSpinner.setVisibility(isEditing ? View.INVISIBLE : View.VISIBLE);
        addAnAllergyTV.setVisibility(isEditing ? View.INVISIBLE : View.VISIBLE);
        isEditing = !isEditing;
        toggleButtonEnabledStatus();
    }

    private void toggleButtonEnabledStatus() {
        LinearLayout buttonHolder = activity.findViewById(R.id.allergies_list);
        int numOfButtons = buttonHolder.getChildCount();
        for (int i = 0; i < numOfButtons; i++) {
            Button button = (Button) buttonHolder.getChildAt(i);
            button.setEnabled(isEditing);
            if (isEditing){
                button.setBackgroundResource(android.R.drawable.btn_default);
                button.setTextColor(Color.parseColor("#000000"));
            } else {
                button.setBackgroundColor(Color.TRANSPARENT);
                button.setTextColor(Color.parseColor("#000000"));
            }
//            b.setBackgroundColor(isEditing ? Color.parseColor("#d1d1d1") : Color.TRANSPARENT);
//            b.setTextColor(Color.parseColor(isEditing ? "#000000" : "#8e8e8e"));
        }
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

    @Override
    public void onClick(View v) {
        // get which allergy the user wants to remove
        String allergySelected = ((Button) v).getText().toString();

        // store that the allergy was removed
        deletedAllergies.add(allergySelected);
        addedAllergies.remove(allergySelected);

        // remove button from display
        allergiesHolder.removeView(v);
    }

    @Override
    public void onItemSelected(AdapterView<?> View, View selectedView, int position, long id) {
        // if block and Boolean needed to keep spinner from adding the first food to allergies upon initialization
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
        // Auto-generated method stub
    }

    private class UpdateAllergies extends AsyncTask<Object, Void, Void> {
        @Override
        protected Void doInBackground(Object... objects) {
            Activity activity = (Activity) objects[0];
            ArrayList<String> addedAllergies = (ArrayList<String>) objects[1];
            ArrayList<String> deletedAllergies = (ArrayList<String>) objects[2];
            String[] allFoodNames = (String[]) objects[3];
            int[] allFoodIds = (int[]) objects[4];

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
            return null;
        }

        @Override
        protected void onPostExecute(Void v){
            // TODO: where to put this for thread safety?
            addedAllergies.clear();
            deletedAllergies.clear();
        }
    }
}