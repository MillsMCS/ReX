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
public class AllergyInfoFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener{
    private static final String TAG = "allergy_info_frag";
    String[] allFoods;
    String[] currentAllergies;
    List<String> addedAllergies;
    List<String> deletedAllergies;
    Spinner foodsSpinner;
    boolean isEditing;
    boolean spinnerCheck;
    Activity activity;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_allergy_info, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        activity = getActivity();
        allFoods = RexDatabaseUtilities.getAllFoodNames(activity);

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
        addedAllergies = new ArrayList<>();
        deletedAllergies = new ArrayList<>();

        // create buttons for allergies the dog already has
        for (String allergen : currentAllergies){
            displayAllergy(activity, allergen);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> View, View selectedView, int position, long id) {

        // if block statement needed to keep spinner from adding the first food to allergies upon initialization
        // https://stackoverflow.com/questions/13397933/android-spinner-avoid-onitemselected-calls-during-initialization
        if (spinnerCheck){
            String newAllergy = allFoods[position];
            // if not the first item [which is "Select a Food"] and the item is not already displayed as a button

            // cases:
            // food is in removeFood:
                // remove from removeFood and display
            // food is in currentAllergies:
                // do nothing
            // food is not in either:
                // add to addList and display

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
        button.setText(newAllergy);

        // add the button to the Linear Layout that holds them
        LinearLayout allergies_list = activity.findViewById(R.id.allergies_list);
        allergies_list.addView(button);
    }

    // TODO: Method for rendering on Edit click (called in ProfileActivity)
    public void renderButtons(){
        foodsSpinner.setVisibility(isEditing ? View.INVISIBLE : View.VISIBLE);
        isEditing = !isEditing;
        setButtonEnabledness();

        // explicitly set spinnerCheck to false in case user edits Allergies twice
        spinnerCheck = false;
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
}
