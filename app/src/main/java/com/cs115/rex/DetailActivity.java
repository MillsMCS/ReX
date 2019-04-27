package com.cs115.rex;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class DetailActivity extends MenuActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    //TODO add toxicity and vet bar (calls contact) when the result calls for them
}
