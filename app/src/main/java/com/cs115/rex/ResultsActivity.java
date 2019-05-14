package com.cs115.rex;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

public class ResultsActivity extends MenuHomeActivity implements ResultsFragment.Listener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();
        String[] searchResults = bundle.getStringArray(ResultsFragment.SEARCH_RESULTS);
        String searchName = bundle.getString(ResultsFragment.SEARCH_NAME);
        //Log.d("DebugLog: ", "ResultsActivity - Value: " + searchResults[0]);

        ResultsFragment results = new ResultsFragment();
        results.setArguments(bundle);

        //Log.d("DebugLog: ", "ResultsActivity - Value: " + results.getArguments().getStringArray(ResultsFragment.SEARCH_RESULTS)[0]);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ResultsFragment resultsFrag = ResultsFragment.newInstance(searchResults, searchName);
        ft.replace(R.id.results_container, resultsFrag);
        ft.commit();

        setContentView(R.layout.activity_results);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    //gets the name of the chosen food based on the list id


    //sends user to appropriate details when user clicks a result
    public void onClickResult(long id) {

        //TODO activate detail properly (via database) on a tablet screen with database
        View detailContainer = findViewById(R.id.detail_container);
        if (detailContainer != null) {
            DetailFragment detail = new DetailFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            detail.setDataFromActivity(id);
            ft.replace(R.id.detail_container, detail);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.addToBackStack(null);
            ft.commit();

        } else {
            //TODO activate detail properly (via database) on a phone screen
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra(DetailFragment.RESULT_ID, id);
            startActivity(intent);
        }
    }
}