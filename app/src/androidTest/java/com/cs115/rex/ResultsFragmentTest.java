package com.cs115.rex;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.Test;

import static org.junit.Assert.*;

public class ResultsFragmentTest {
    private static String[] searchResults = {"Bay Leaf"};
    private static String searchName = "ba";

    private static String[] searchResults2 = {"Chicken", "Chive"};
    private static String searchName2 = "chi";

    private static ResultsFragment resultsFragment = ResultsFragment.newInstance(searchResults, searchName);

    @Test
    public void newInstance() {
        assertEquals(searchName, resultsFragment.getArguments().getString(ResultsFragment.SEARCH_NAME));
        assertEquals(searchResults[0], resultsFragment.getArguments().getStringArray(ResultsFragment.SEARCH_RESULTS)[0]);
    }

    @Test
    public void setDataFromActivity() {
        resultsFragment.setDataFromActivity(searchResults2, searchName2);
        assertEquals(searchName2, resultsFragment.searchName);
        assertEquals(searchResults2[0], resultsFragment.searchResults[0]);
        assertEquals(searchResults2[1], resultsFragment.searchResults[1]);
    }
}