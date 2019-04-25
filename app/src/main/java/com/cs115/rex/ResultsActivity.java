package com.cs115.rex;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class ResultsActivity extends MenuActivity implements ResultsFragment.Listener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void onClickResult() {
        //TODO add detail functionality

    }
}
