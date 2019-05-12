package com.cs115.rex;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends MenuActivity implements ResultsFragment.Listener  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public String[] setResultList(Context context, String searchName) {
        return RexDatabaseUtilities.getSelectedFoodNames(context, searchName);
    }

    //Activates search button, sending user to results
    public void onClickSearch(View view) {
        String searchName = null;
        String[] searchResults = null;

        EditText searchBar = (EditText)findViewById(R.id.search_bar);

        try {
            //Capitalize the first letter of the search term so it will match the inputs in the database
            searchName = searchBar.getText().toString();
            if(searchName.length() == 0) { throw new Exception(); }
            searchName = searchName.substring(0, 1).toUpperCase() + searchName.substring(1);
            //Log.d("DebugLog: ", "MainActivity - Value: " + searchName);

        //a more specific exception would be nice - looking to account for empty search input
        } catch (Exception e) {
            //show error toast if no input
            Toast toast = Toast.makeText(this, "Please enter a search term", Toast.LENGTH_SHORT);
            toast.show();

            //the following was an attempt to avoid the flash when the activity recreates (recreate() shows the flash)
            //does not work,  tested on fire
            /*
            finish();
            overridePendingTransition(0, 0);
            startActivity(getIntent());
            overridePendingTransition(0, 0);
             */
            //TODO delay recreate() with a runnable, attempt to suppress animations (screen flash)
            recreate();
        }

        try {
            searchResults = setResultList(this, searchName);
            Log.d("DebugLog: ", "MainActivity - Value: " + searchResults.length);
            if(searchResults.length == 0) { throw new Exception(); }

        //a more specific exception would be nice - looking to account for no search results found
        } catch (Exception e) {
            //show error toast if no results
            Toast toast = Toast.makeText(this, "No results found. Please try a different search.", Toast.LENGTH_SHORT);
            toast.show();

            //TODO delay recreate() with a runnable, attempt to suppress animations (screen flash)
            recreate();
        }

        View resultsContainer = findViewById(R.id.results_container);
        if (resultsContainer != null) {
            ResultsFragment results = new ResultsFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            results.setDataFromActivity(searchResults, searchName);
            ft.replace(R.id.results_container, results);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.addToBackStack(null);
            ft.commit();

        } else {

            Log.d("DebugLog: ", "MainActivity - name: " + searchName + "; " + "results: " + searchResults);
            if(searchName.equals("") || searchResults == null) {

                //clumsy, but restarts mainActivity with toast - recreate() does not have the desired effect here
                //recreate shows a search result with all of the available search items
                //TODO delay recreate() with a runnable, attempt to suppress animations (screen flash)
                Intent intent = new Intent(this, MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("search_name",searchName);
                intent.putExtras(bundle);
                startActivity(intent);
            } else {

                Intent intent = new Intent(this, ResultsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putStringArray("search_results", searchResults);
                bundle.putString("search_name", searchName);
                intent.putExtras(bundle);
                //Log.d("DebugLog: ", "MainActivity - Value: " + intent.getExtras().getStringArray("search_results")[0]);

                ResultsFragment results = new ResultsFragment();

                startActivity(intent);
            }
        }
    }

    //sends user to appropriate details when user clicks a result
    public void onClickResult(long id) {
        //TODO activate detail properly (via database) on a tablet screen with database
        View resultsContainer = findViewById(R.id.detail_container);
        if (resultsContainer != null) {
            DetailFragment detail = new DetailFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.detail_container, detail);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.addToBackStack(null);
            ft.commit();

        } else {
            //TODO activate detail properly (via database) on a phone screen
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra(DetailFragment.RESULT_ID, (int)id);
            startActivity(intent);
        }
    }
}