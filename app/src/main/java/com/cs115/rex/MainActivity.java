package com.cs115.rex;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;

public class MainActivity extends MenuActivity implements ResultsFragment.Listener  {
    //TODO Activate search functionality

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    //TODO refactor to account for tablet and phone interfaces
    //Activates search button, sending user to results
    public void onClickSearch(View view) {
        View resultsContainer = findViewById(R.id.results_container);
        if (resultsContainer != null) {
            ResultsFragment results = new ResultsFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            //TODO finish activating result on a tablet with database
            ft.replace(R.id.results_container, results);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.addToBackStack(null);
            ft.commit();

        } else {
            Intent intent = new Intent(this, ResultsActivity.class);
            //TODO finish activating result functionality
            //intent.putExtra()
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
            //intent.putExtra();
            startActivity(intent);
        }
    }
}
