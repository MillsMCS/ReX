package com.cs115.rex;


import android.content.Context;
import android.database.Cursor;
import android.support.test.InstrumentationRegistry;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

public class RexDatabaseUtilitiesTest {

    private static final int DOG1_ID = 1;
    private static final String DOG1_NAME = "Benji";
    private static final int DOG1_WEIGHT = 220;
    private static final String DOG1_BREED = "Labrador";
    private static final int FOOD1_ID = 1;
    private static final String FOOD1_NAME = "Chocolate";
    private static final String FOOD1_BLURB = "Blah blah blah";
    private static final int FOOD1_TOX = 10;
    private static final int ALLERGY1_ID = 1;
    private static final String NEW_NAME = "Bruno";
    private SQLiteDatabase db;

    @Before
    public void setUp() throws Exception {
        // This creates a temporary context so database accesses in the tests are isolated.
        Context context =
                InstrumentationRegistry.getTargetContext();
        SQLiteOpenHelper rexDatabaseHelper = new RexDatabaseHelper(context);
        SQLiteDatabase db = rexDatabaseHelper.getWritableDatabase();

        // Create a dog, DOG1.
        long rowId = RexDatabaseUtilities.addDog(db, DOG1_ID, DOG1_NAME, DOG1_WEIGHT, DOG1_BREED);
        //Create a food, FOOD1.
        rowId = RexDatabaseUtilities.addFood(db, FOOD1_ID, FOOD1_NAME, FOOD1_BLURB, FOOD1_TOX );
        //Create an allergy, ALLERGY1.
        rowId = RexDatabaseUtilities.addAllergy(db, ALLERGY1_ID, DOG1_ID, FOOD1_NAME);

    }

    @After
    public void takeDown() {
        db.close();
    }


    @Test
    public void getDog() throws Exception {
        Cursor dogCursor = RexDatabaseUtilities.getDog(db, DOG1_ID);
        if (dogCursor != null){
            dogCursor.moveToFirst();
            assertEquals(DOG1_ID, dogCursor.getInt(0));
            assertEquals(DOG1_NAME, dogCursor.getString(1));
            assertEquals(DOG1_WEIGHT, dogCursor.getInt(2));
            assertEquals(DOG1_BREED, dogCursor.getString(3));
        } else {
            fail();
        }
    }

    @Test
    public void getAllergy() throws Exception {
        Cursor allergyCursor = RexDatabaseUtilities.getAllergy(db, ALLERGY1_ID);
        if (allergyCursor != null){
            allergyCursor.moveToFirst();
            assertEquals(ALLERGY1_ID, allergyCursor.getInt(0));
            assertEquals(DOG1_ID, allergyCursor.getString(1));
            assertEquals(FOOD1_NAME, allergyCursor.getString(2));
        } else {
            fail();
        }
    }
    @Test
    public void deleteAllergy() throws Exception {

    }


    @Test
    public void updateDogName() throws Exception {
        boolean success = RexDatabaseUtilities.updateDogName(db, DOG1_ID, NEW_NAME);
        assertEquals(true, success);
        Cursor dogNameCursor = RexDatabaseUtilities.getDog(db, DOG1_ID);
        if (dogNameCursor != null) {
            dogNameCursor.moveToFirst();
            assertEquals(NEW_NAME, dogNameCursor.getString(1));
        } else {
            fail();
        }
    }
    public boolean updateDogBreed
    public boolean updateDogWeight
}
