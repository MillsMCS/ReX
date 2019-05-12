package com.cs115.rex;


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
    private int itemId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Log.d("DebugLog: ", "DetailFragment - Value: " + getArguments().getStringArray(RESULT_ID)[0]);
            itemId = getArguments().getInt(RESULT_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        int id = itemId;

        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        return view;

        //return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String[] itemArray = RexDatabaseUtilities.getFoodByID(getActivity(),itemId);

        String nameText = itemArray[0];
        TextView name = view.findViewById(R.id.header);
        name.setText(nameText);

        String tox = itemArray[1];
        //DetailFragment.checkTox;

        String img = itemArray[2];
        ImageView photo = view.findViewById(R.id.detail_photo);
        int src = getActivity().getResources().getIdentifier(img, "drawable", getActivity().getPackageName());
        photo.setImageResource(src);

        String quote = itemArray[3];
        TextView desc = view.findViewById(R.id.detail_text);
        desc.setText(quote);

        Log.d("DebugLog: ", "DetailActivity - Value: " + itemArray[0]);

    }

    public static DetailFragment newInstance(int itemId){
        DetailFragment detailFragment = new DetailFragment();

        Bundle bundle = new Bundle();
        bundle.putInt(RESULT_ID, itemId);
        detailFragment.setArguments(bundle);

        return detailFragment;
    }

    public void setDataFromActivity(int itemId){
        this.itemId = itemId;
    }

    //TODO add selective toxicity info
    protected void checkToxicity() {

    }
}
