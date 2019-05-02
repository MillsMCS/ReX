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
import java.util.List;


/**
 *
 */
public class AllergyInfoFragment extends Fragment implements AdapterView.OnItemSelectedListener{
    private static final String TAG = "allergy_info_frag";
    String[] allFoods;
    String[] currentAllergies;
    List<String> newAllergies;
    Spinner foodsSpinner;
    Boolean isEditing;
    List<Button> AllergyButtons;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        isEditing = false;
        AllergyButtons = new ArrayList<>();
        return inflater.inflate(R.layout.fragment_allergy_info, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        Activity activity = getActivity();


        // TODO: Populate with database information. Ask Karena if she wrote getFoods method
        allFoods = new String[] {
                "Select a Food", "Alcohol", "Apple", "Apricot", "Coffee", "Marijuana", "Plum", "Watermelon"
        };

        // populate spinner with foods user can select and set on click listener
        foodsSpinner = activity.findViewById(R.id.all_foods_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                                                        activity,
                                                        android.R.layout.simple_spinner_item,
                                                        allFoods);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        foodsSpinner.setVisibility(View.GONE);
        foodsSpinner.setAdapter(adapter);
        foodsSpinner.setOnItemSelectedListener(this);

        // TODO: POPULATE WITH DATABASE INFORMATION
        currentAllergies = new String[] {
                "Banana", "Chicken"
        };

        // will hold allergies the user adds during this session
        newAllergies = new ArrayList<>();

        // create buttons for allergies the dog already has
        for (String allergen : currentAllergies){
            addItemToAllergiesList(activity, allergen);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> View, View selectedView, int position, long id) {
        String selectedFood = allFoods[position];
        // if not the first item [which is "Select a Food"] and the item is not already displayed as a button
        if (position != 0
            && !Arrays.asList(currentAllergies).contains(selectedFood)
            && !newAllergies.contains(selectedFood)) {

            // add Item to new Allergen List
            addItemToAllergiesList(getActivity(), selectedFood);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub. Look into what this does.
    }

    private void addItemToAllergiesList(Activity activity, String newAllergy) {
        // make a new button and add it to our list of Buttons
        Button button = new Button(activity);
        AllergyButtons.add(button);

        button.setText(newAllergy);
        button.setEnabled(isEditing);

        // add the button to the Linear Layout that holds them
        LinearLayout allergies_list = activity.findViewById(R.id.allergies_list);
        allergies_list.addView(button);

        // add String to list of new Allergies, to be sent to database on Save
        newAllergies.add(newAllergy);
    }

    // TODO: Method for saving new allergies onClick (called in ProfileActivity)
    public void makeEditable(){
        if (isEditing){
            foodsSpinner.setVisibility(View.GONE);
            for (Button b : AllergyButtons){
                b.setEnabled(false);
            }
        } else {
            foodsSpinner.setVisibility(View.VISIBLE);
            for (Button b : AllergyButtons){
                b.setEnabled(true);
            }
        }
        isEditing = !isEditing;
    }
}
