package com.cs115.rex;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

/**
 *
 */
public class ResultsFragment extends ListFragment {
    public static final String SEARCH_RESULTS = "search_results";
    private String[] searchResults;

    interface Listener {
        void onClickResult(long id);
    }

    private Listener listener;
    //private static String[] searchResults = new String[0];

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Log.d("DebugLog: ", "ResultsFragment - Value: " + getArguments().getStringArray(SEARCH_RESULTS)[0]);
            searchResults = getArguments().getStringArray(SEARCH_RESULTS);
        } else {
            getActivity().finish();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        String[] results = searchResults;

        //String[] results = {"Egg"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                inflater.getContext(), android.R.layout.simple_list_item_1,
                results);
        setListAdapter(adapter);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public static ResultsFragment newInstance(String[] searchResults){
        ResultsFragment resultsFragment = new ResultsFragment();

        Bundle bundle = new Bundle();
        bundle.putStringArray("search_results", searchResults);
        resultsFragment.setArguments(bundle);

        return resultsFragment;
    }

    public void setDataFromActivity(String[] searchResults){
        this.searchResults = searchResults;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.listener = (Listener) context;

    }

    //TODO add database functionality to get id of result and add that
    //@Override
    //TODO remove hardcoded example id
    public void onListItemClick(ListView listView, View itemView, int position, long id) {
        if (listener != null) {
            listener.onClickResult(id);
        }
    }
}
