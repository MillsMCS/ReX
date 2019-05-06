package com.cs115.rex;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class ResultsActivity extends MenuHomeActivity implements ResultsFragment.Listener {
    public static final String RESULT_ID = "result_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();
        String[] searchResults = bundle.getStringArray(ResultsFragment.SEARCH_RESULTS);
        String searchName = bundle.getString(ResultsFragment.SEARCH_NAME);
        Log.d("DebugLog: ", "ResultsActivity - Value: " + searchResults[0]);

        ResultsFragment results = new ResultsFragment();
        results.setArguments(bundle);

        Log.d("DebugLog: ", "ResultsActivity - Value: " + results.getArguments().getStringArray(ResultsFragment.SEARCH_RESULTS)[0]);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ResultsFragment resultsFrag = ResultsFragment.newInstance(searchResults, searchName);
        ft.replace(R.id.results_container, resultsFrag);
        //ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();

        setContentView(R.layout.activity_results);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    //TODO add selective toxicity info
    protected void checkToxicity() {

    }

    //sends user to appropriate details when user clicks a result
    public void onClickResult(long id) {
        //TODO activate detail properly (via database) on a phone screen
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(ResultsActivity.RESULT_ID, (int)id);
        startActivity(intent);

    }
}
