package com.cs115.rex;


import android.database.Cursor;
import android.graphics.drawable.Drawable;
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

import java.util.ArrayList;
import java.util.Arrays;


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
                    + ", " + items.getString(2) + ", " + items.getString(3));

            //set name of selected food
            String nameText = items.getString(0);
            TextView name = (TextView) view.getRootView().findViewById(R.id.header);
            name.setText(nameText);

            //set background for appropriate toxicity
            int tox = items.getInt(1);
            int bgColor = (tox > 1) ? R.color.colorToxic : R.color.colorNonToxic;
            name.setBackgroundColor(getResources().getColor(bgColor));

            //TODO Evaluate for allergy given particular dog and add blue to background if dog is allergic
            //get dog's allergies
            String[] allergyFoods = RexDatabaseUtilities.getAllergyNames(this.getActivity(), "1");
            ArrayList<String> allergyList = new ArrayList<String> (Arrays.asList(allergyFoods));


            //if food is in dog's allergies array, add border
            if(allergyList.contains(nameText)){
                View allergyIndicator = (View) view.getRootView().findViewById(R.id.allergyIndicator);
                allergyIndicator.setBackgroundColor(getResources().getColor(R.color.colorAllergic));
            }



            //set appropriate img
            String imgStr = items.getString(2);
            int imgId = getResources().getIdentifier(imgStr, "drawable", getActivity().getPackageName());
            ImageView photo = (ImageView) view.getRootView().findViewById(R.id.detail_photo);
            photo.setImageResource(imgId);


            String quoteStr = items.getString(3);
            int stringId = getResources().getIdentifier(quoteStr, "string", getActivity().getPackageName());
            TextView desc = (TextView) view.getRootView().findViewById(R.id.detail_text);
            desc.setText(stringId);
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
}
