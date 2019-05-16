package com.cs115.rex;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.Test;

import static org.junit.Assert.*;

public class DetailFragmentTest {

    private static long itemId = 1;
    private static DetailFragment detailFragment = DetailFragment.newInstance(itemId);

    @Test
    public void newInstance() {
        //item id corresponding to alcohol
        assertEquals(itemId, detailFragment.getArguments().getLong(DetailFragment.RESULT_ID));
    }

    @Test
    public void setDataFromActivity() {
        //item id corresponding to alcohol
        detailFragment.setDataFromActivity(itemId);
        assertEquals(itemId, detailFragment.itemId);
    }
}