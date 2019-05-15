package com.cs115.rex;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

/**
 *
 */
public class ResultsFragment extends ListFragment {
    public static final String SEARCH_RESULTS = "search_results";
    public static final String SEARCH_NAME = "search_name";
    private String searchName;
    private String[] searchResults;

    interface Listener {
        void onClickResult(long id);
    }

    private Listener listener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //Log.d("DebugLog: ", "ResultsFragment - Value: " + getArguments().getStringArray(SEARCH_RESULTS)[0]);
            searchResults = getArguments().getStringArray(SEARCH_RESULTS);
            searchName = getArguments().getString(SEARCH_NAME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        String[] results = searchResults;

        Cursor cursor = RexDatabaseUtilities.getSelectedFoodList(getActivity(), searchName);

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(getActivity(),
                android.R.layout.simple_list_item_1,
                cursor,
                new String[]{"NAME"},
                new int[]{android.R.id.text1},
                0);
        setListAdapter(adapter);

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public static ResultsFragment newInstance(String[] searchResults, String searchName){
        ResultsFragment resultsFragment = new ResultsFragment();

        Bundle bundle = new Bundle();
        bundle.putStringArray(SEARCH_RESULTS, searchResults);
        bundle.putString(SEARCH_NAME, searchName);
        resultsFragment.setArguments(bundle);
        return resultsFragment;
    }

    public void setDataFromActivity(String[] searchResults, String searchName){
        this.searchResults = searchResults;
        this.searchName = searchName;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.listener = (Listener) context;

    }

    //@Override
    public void onListItemClick(ListView listView, View itemView, int position, long id) {
        if (listener != null) {
            listener.onClickResult(id);
        }
    }
}
