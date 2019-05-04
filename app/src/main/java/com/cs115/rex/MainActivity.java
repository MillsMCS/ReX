package com.cs115.rex;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;

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
        EditText searchBar = (EditText)findViewById(R.id.search_bar);
        String searchName = searchBar.getText().toString();

        //Capitalize the first letter of the search term so it will match the inputs in the database
        searchName = searchName.substring(0,1).toUpperCase() + searchName.substring(1);
        String[] searchResults = setResultList(this,searchName);

        View resultsContainer = findViewById(R.id.results_container);
        if (resultsContainer != null) {
            ResultsFragment results = new ResultsFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            results.setDataFromActivity(searchResults);
            ft.replace(R.id.results_container, results);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.addToBackStack(null);
            ft.commit();

        } else {

            Intent intent = new Intent(this, ResultsActivity.class);
            Bundle bundle = new Bundle();
            bundle.putStringArray("search_results",searchResults);
            intent.putExtras(bundle);
            Log.d("DebugLog: ", "MainActivity - Value: " + intent.getExtras().getStringArray("search_results")[0]);

            ResultsFragment results = new ResultsFragment();

            startActivity(intent);
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
            intent.putExtra(ResultsActivity.RESULT_ID, (int)id);
            startActivity(intent);
        }
    }
}