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
        int itemId = (Integer) bundle.get(DetailFragment.RESULT_ID);

        //
        String[] itemArray = RexDatabaseUtilities.getFoodByID(this,itemId);

        Log.d("DebugLog: ", "DetailActivity - Value: " + itemArray[0]);

        String nameText = itemArray[0];
        TextView name = findViewById(R.id.header);
        name.setText(nameText);

        String tox = itemArray[1];
        //DetailFragment.checkTox;

        String img = itemArray[2];
        ImageView photo = findViewById(R.id.detail_photo);
        photo.setImageResource(getResources().getIdentifier("ImageName","drawable",getPackageName()));

        String quote = itemArray[3];
        TextView desc = findViewById(R.id.detail_text);
        //

        //Log.d("DebugLog: ", "DetailActivity - Value: " + itemArray[0]);

        DetailFragment details = new DetailFragment();
        details.setArguments(bundle);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        DetailFragment detailFrag = DetailFragment.newInstance(itemId);
        ft.replace(R.id.detail_container, detailFrag);
        ft.commit();

        setContentView(R.layout.activity_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    //TODO add toxicity and vet bar (calls contact) when the result calls for them

}
