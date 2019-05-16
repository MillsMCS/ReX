package com.cs115.rex;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;

/**
 * Detail-level activity that allows users to view food detail pages. The associated fragment's {@link DetailFragment} view
 * shows the appropriate food detail page based on the user's selection from the list of search results displayed by
 * the views associated with {@link ResultsActivity} and {@link ResultsFragment}.
 */
public class DetailActivity extends MenuHomeActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();

        Log.d("DebugLog: ", "DetailActivity - id from bundle: " +
                (Long) bundle.get(DetailFragment.RESULT_ID));

        long itemId = (Long) bundle.get(DetailFragment.RESULT_ID);

        DetailFragment details = new DetailFragment();
        details.setArguments(bundle);

        Log.d("DebugLog: ", "DetailActivity - id from arguments: " +
                details.getArguments().getLong(DetailFragment.RESULT_ID));

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        DetailFragment detailFrag = DetailFragment.newInstance(itemId);
        detailFrag.setDataFromActivity(itemId);
        ft.replace(R.id.detail_container, detailFrag);
        ft.commit();

        setContentView(R.layout.activity_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
}
