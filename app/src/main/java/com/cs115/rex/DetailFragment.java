package com.cs115.rex;


import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


/**
 *
 */
public class DetailFragment extends Fragment {
    public static final String RESULT_ID = "result_ID";
    private long itemId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Log.d("DebugLog: ", "DetailFragment - Value: " + getArguments().getLong(RESULT_ID));
            itemId = getArguments().getLong(RESULT_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.d("DebugLog: ", "DetailFragment - itemId: " + itemId);

        Cursor items = RexDatabaseUtilities.getFoodById(getActivity(), itemId);
        if(items.moveToFirst()) {

            Log.d("DebugLog: ", "DetailFragment - cursor: " + items.getString(0) + ", " + items.getInt(1)
                    + ", " + items.getInt(2) + ", " + items.getInt(3));

            //set name of selected food
            String nameText = items.getString(0);
            TextView name = (TextView) view.getRootView().findViewById(R.id.header);
            name.setText(nameText);

            //set background for appropriate toxicity
            int tox = items.getInt(1);
            int bgColor = (tox > 1) ? R.color.colorToxic : R.color.colorNonToxic;
            name.setBackgroundColor(getResources().getColor(bgColor));

            //Evaluate for allergy given particular dog and add blue to background if dog is allergic


            //set appropriate img
            int img = items.getInt(2);
            ImageView photo = (ImageView) view.getRootView().findViewById(R.id.detail_photo);
            photo.setImageResource(img);

            /*
            int quoteId = items.getInt(3);
            Integer quoteId = Integer.parseInt(quote);
            TextView desc = (TextView) view.getRootView().findViewById(R.id.detail_text);
            String descText = getString(quoteId);
            desc.setText(quote);
            */
        }
    }

    public static DetailFragment newInstance(long itemId){
        DetailFragment detailFragment = new DetailFragment();

        Bundle bundle = new Bundle();
        bundle.putLong(RESULT_ID, itemId);
        detailFragment.setArguments(bundle);

        return detailFragment;
    }

    public void setDataFromActivity(long itemId){
        this.itemId = itemId;
    }

    //TODO add selective toxicity info
    protected void checkToxicity() {

    }
}
