package com.cs115.rex;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import org.junit.Test;
import static org.junit.Assert.*;

public class ResultsFragmentTest {
    //temporary context to isolate test
    private Context context = InstrumentationRegistry.getTargetContext();

    @Test
    public void newInstance() {
        String[] searchResults = {"Bay Leaf"};
        String searchName = "ba";
        ResultsFragment resultsFragment = ResultsFragment.newInstance(searchResults,searchName);
        assertEquals(searchName, resultsFragment.getArguments().getString(ResultsFragment.SEARCH_NAME));
        assertEquals(searchResults[0], resultsFragment.getArguments().getStringArray(ResultsFragment.SEARCH_RESULTS)[0]);
    }

    @Test
    public void setDataFromActivity() {
        String[] searchResults = {"Chicken", "Chive"};
        String searchName = "chi";

        //item id corresponding to alcohol
        ResultsFragment resultsFragment = ResultsFragment.newInstance(searchResults,searchName);
        resultsFragment.setDataFromActivity(searchResults,searchName);
        assertEquals(searchName, resultsFragment.searchName);
        assertEquals(searchResults[0], resultsFragment.searchResults[0]);
        assertEquals(searchResults[1], resultsFragment.searchResults[1]);
    }
}