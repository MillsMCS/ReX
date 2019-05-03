package com.cs115.rex;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

public class ResultsActivity extends MenuHomeActivity implements ResultsFragment.Listener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    //TODO add selective toxicity info

    //sends user to appropriate details when user clicks a result
    public void onClickResult(long id) {
        //TODO activate detail properly (via database) on a phone screen
        Intent intent = new Intent(this, DetailActivity.class);
        //intent.putExtra();
        startActivity(intent);

    }
}
