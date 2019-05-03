package com.cs115.rex;


import android.app.Activity;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 *
 */
public class AllergyInfoFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener{
    private static final String TAG = "allergy_info_frag";
    String[] allFoodNames;
    int[] allFoodIds;
    String[] currentAllergies;
    ArrayList<String> addedAllergies;    // must be ArrayList because List is not serializable for Bundle
    ArrayList<String> deletedAllergies;  // must be ArrayList because List is not serializable for Bundle
    boolean isEditing;
    boolean spinnerCheck;
    boolean isRestored;
    Spinner foodsSpinner;
    Activity activity;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (savedInstanceState != null){
            allFoodNames = savedInstanceState.getStringArray("allFoodNames");
            currentAllergies = savedInstanceState.getStringArray("currentAllergies");
            allFoodIds = savedInstanceState.getIntArray("allFoodIds");
            addedAllergies = savedInstanceState.getStringArrayList("addedAllergies");
            deletedAllergies = savedInstanceState.getStringArrayList("deletedAllergies");
            isEditing = savedInstanceState.getBoolean("isEditing");
            isRestored = true;
        }
        return inflater.inflate(R.layout.fragment_allergy_info, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        activity = getActivity();
        if (!isRestored){
            allFoodNames = RexDatabaseUtilities.getAllFoodNames(activity);
            allFoodIds = RexDatabaseUtilities.getAllFoodId(activity);
            currentAllergies = RexDatabaseUtilities.getAllergyNames(activity, String.valueOf(RexDatabaseHelper.SINGLE_DOG_ID));

            // will hold allergies the user adds during this session
            addedAllergies = new ArrayList<>();
            deletedAllergies = new ArrayList<>();
        }

        // populate spinner with foods user can select and set on click listener
        foodsSpinner = activity.findViewById(R.id.all_foods_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                                                        activity,
                                                        R.layout.spinner,
                                                        allFoodNames);
        foodsSpinner.setAdapter(adapter);
        foodsSpinner.setOnItemSelectedListener(this);
        foodsSpinner.setVisibility(isEditing ? View.VISIBLE : View.GONE);

        // create buttons for allergies the dog already has
        for (String allergen : currentAllergies){
            if (!deletedAllergies.contains(allergen)) {
                displayAllergy(activity, allergen);
            }
        }
        for (String allergen : addedAllergies){
            displayAllergy(activity, allergen);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> View, View selectedView, int position, long id) {

        // if block statement needed to keep spinner from adding the first food to allergies upon initialization
        // https://stackoverflow.com/questions/13397933/android-spinner-avoid-onitemselected-calls-during-initialization
        if (spinnerCheck){
            String newAllergy = allFoodNames[position];
            if (deletedAllergies.contains(newAllergy)){
                deletedAllergies.remove(newAllergy);
                // restore food to list of allergens
                displayAllergy(activity, newAllergy);
            } else if (!Arrays.asList(currentAllergies).contains(newAllergy) &&
                       !addedAllergies.contains(newAllergy)) {
                // display button
                displayAllergy(activity, newAllergy);

                // add String to list of new Allergies, to be sent to database on Save
                addedAllergies.add(newAllergy);
            }
        } else {
            spinnerCheck = true;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub. Look into what this does.
    }

    private void displayAllergy(Activity activity, String newAllergy) {
        // make a new button and add it to our list of Buttons
        Button button = new Button(activity);
        button.setOnClickListener(this);
        button.setEnabled(isEditing);
        button.setText(newAllergy);

        // add the button to the Linear Layout that holds them
        LinearLayout allergies_list = activity.findViewById(R.id.allergies_list);
        allergies_list.addView(button);
    }

    // TODO: Method for rendering on Edit click (called in ProfileActivity)
    public void onEditandSaveAction(){
        if (isEditing){
            // user wants to save their state
            // check addAllergy array
            for (String food : addedAllergies){
                // find the index that it lives in allFoodNames Array
                int index = Arrays.asList(allFoodNames).indexOf(food);

                // get the matching integer from allFoodIds
                int foodId = allFoodIds[index];

                // call utilities.addAllergy(foodId)
                RexDatabaseUtilities.addAllergy(activity, foodId, RexDatabaseHelper.SINGLE_DOG_ID);
            }

            for (String food : deletedAllergies){
                // find the index that it lives in allFoodNames Array
                int index = Arrays.asList(allFoodNames).indexOf(food);

                // get the matching integer from allFoodIds
                int foodId = allFoodIds[index];

                // call utilities.addAllergy(foodId)
                RexDatabaseUtilities.removeAllergy(activity, String.valueOf(foodId), String.valueOf(RexDatabaseHelper.SINGLE_DOG_ID));
            }

            addedAllergies.clear();
            deletedAllergies.clear();
        }
        foodsSpinner.setVisibility(isEditing ? View.INVISIBLE : View.VISIBLE);
        isEditing = !isEditing;
        setButtonEnabledness();
    }

    private void setButtonEnabledness(){
        LinearLayout buttonHolder = activity.findViewById(R.id.allergies_list);
        int numOfButtons = buttonHolder.getChildCount();
        for (int i = 0; i < numOfButtons; i++) {
            Button b = (Button) buttonHolder.getChildAt(i);
            b.setEnabled(isEditing);
        }
    }

    // onClick for allergy buttons
    @Override
    public void onClick(View v) {
        Button btn = (Button) v;
        String food = btn.getText().toString();
        addedAllergies.remove(food);
        deletedAllergies.add(food);
        LinearLayout lv = (LinearLayout) btn.getParent();
        lv.removeView(v);
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