package com.cs115.rex;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 *
 */
public class ResultsFragment extends ListFragment {

    interface Listener {
        void onClickResult(long id);
    }

    private Listener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_results, container, false);

        //TODO replace example for layout/design purposes with database search functionality
        String[] results = {"Chocolate", "Chamomile", "Tea", "Butter"};

        //TODO investigate further styling for the ListFragment
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                inflater.getContext(), android.R.layout.simple_list_item_1,
                results);
        setListAdapter(adapter);
        return super.onCreateView(inflater, container, savedInstanceState);
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
            listener.onClickResult(0);
        }
    }
}
