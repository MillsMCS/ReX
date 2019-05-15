package com.cs115.rex;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import org.junit.Test;
import static org.junit.Assert.*;

public class DetailFragmentTest {
    //temporary context to isolate test
    private Context context = InstrumentationRegistry.getTargetContext();

    @Test
    public void newInstance() {
        //item id corresponding to alcohol
        long itemId = 1;
        DetailFragment detailFragment = DetailFragment.newInstance(itemId);
        assertEquals(itemId, detailFragment.getArguments().getLong(DetailFragment.RESULT_ID));
    }

    @Test
    public void setDataFromActivity() {
        //item id corresponding to alcohol
        long itemId = 1;
        DetailFragment detailFragment = DetailFragment.newInstance(itemId);
        detailFragment.setDataFromActivity(itemId);
        assertEquals(itemId, detailFragment.itemId);
    }
}
