package com.cs115.rex;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * The top-level activity for the application, allowing the user to search and launching appropriate
 * activities or fragments based on user interaction.
 */
public class MainActivity extends MenuActivity implements ResultsFragment.Listener  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    /**
     * Gets appropriate results from the database food table when user
     * inputs a search item and clicks search.
     * @param context this context
     * @param searchName the user's input search term
     * @return String array containing all food items whose names start with the search term
     */
    public String[] setResultList(Context context, String searchName) {
        return RexDatabaseUtilities.getSelectedFoodNames(context, searchName);
    }

    /**
     * Activates search button, sending user to appropriate results {@link ResultsActivity},
     * {@link ResultsFragment} when user inputs a search item and clicks search.
     * @param view Associated view
     * @return void
     */
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

            recreate();
        }

        try {
            searchResults = setResultList(this, searchName);
            //Log.d("DebugLog: ", "MainActivity - Value: " + searchResults.length);
            if(searchResults.length == 0) { throw new Exception(); }

        //a more specific exception would be nice - looking to account for no search results found
        } catch (Exception e) {
            //show error toast if no results
            Toast toast = Toast.makeText(this, "No results found. Please try a different search.", Toast.LENGTH_SHORT);
            toast.show();

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

            //Log.d("DebugLog: ", "MainActivity - name: " + searchName + "; " + "results: " + searchResults);
            if(searchName.equals("") || searchResults == null) {

                Intent intent = new Intent(this, MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("search_name",searchName);
                intent.putExtras(bundle);
                overridePendingTransition(0, 0);
                startActivity(intent);
                overridePendingTransition(0, 0);
            } else {

                Intent intent = new Intent(this, ResultsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putStringArray("search_results", searchResults);
                bundle.putString("search_name", searchName);
                intent.putExtras(bundle);
                //Log.d("DebugLog: ", "MainActivity - Value: " + intent.getExtras().getStringArray("search_results")[0]);

                ResultsFragment results = new ResultsFragment();

                overridePendingTransition(0, 0);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        }
    }

    /**
     * Sends user to appropriate details {@link DetailActivity}, {@link DetailFragment} when user clicks a result
     * @param id Food table item id corresponding to list item that user has clicked
     * @return void
     */
    public void onClickResult(long id) {
        View resultsContainer = findViewById(R.id.detail_container);
        if (resultsContainer != null) {
            DetailFragment detail = new DetailFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            detail.setDataFromActivity(id);
            ft.replace(R.id.detail_container, detail);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.addToBackStack(null);
            ft.commit();

        } else {
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra(DetailFragment.RESULT_ID, id);
            startActivity(intent);
        }
    }
}