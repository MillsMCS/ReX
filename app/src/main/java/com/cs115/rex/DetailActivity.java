package com.cs115.rex;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

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

    //TODO add toxicity and vet bar (calls contact) when the result calls for them

}
