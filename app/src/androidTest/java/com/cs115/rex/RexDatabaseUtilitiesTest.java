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
    private static final String DOG1_WEIGHT = "220";
    private static final String DOG1_BREED = "Labrador";
    private static final int FOOD1_ID = 1;
    private static final String FOOD1_NAME = "Chocolate";
    private static final String FOOD1_BLURB = "Blah blah blah";
    private static final int FOOD1_TOX = 10;
    private static final int ALLERGY1_ID = 1;
    private static final String NEW_NAME = "Bruno";
    private static final String NEW_BREED = "Retriever";
    private static final String NEW_WEIGHT = "235";
    private SQLiteDatabase db;
    // This creates a temporary context so database accesses in the tests are isolated.
    private Context context = InstrumentationRegistry.getTargetContext();

    @Before
    public void setUp() throws Exception {

        SQLiteOpenHelper rexDatabaseHelper = new RexDatabaseHelper(context);
        SQLiteDatabase db = rexDatabaseHelper.getWritableDatabase();

        // Create a dog, DOG1.
//        RexDatabaseHelper.insertDog(db, DOG1_ID, DOG1_NAME, DOG1_WEIGHT, DOG1_BREED, null);
        //Create a food, FOOD1.
        //rowId = RexDatabaseUtilities.addFood(db, FOOD1_ID, FOOD1_NAME, FOOD1_BLURB, FOOD1_TOX );
        //Create an allergy, ALLERGY1.
//        RexDatabaseUtilities.addAllergy(context, FOOD1_ID, DOG1_ID);

    }



    @Test
    public void getDog() throws Exception {
        Cursor dogCursor = RexDatabaseUtilities.getDog(context);
        if (dogCursor != null){
            dogCursor.moveToFirst();
            assertEquals(DOG1_ID, dogCursor.getInt(0));
            assertEquals(DOG1_NAME, dogCursor.getString(1));
            assertEquals(DOG1_WEIGHT, dogCursor.getString(2));
            assertEquals(DOG1_BREED, dogCursor.getString(3));
        } else {
            fail();
        }
    }

    @Test
    public void getAllergies() throws Exception {
        Cursor allergyCursor = RexDatabaseUtilities.getAllergies(context);
        if (allergyCursor != null){
            allergyCursor.moveToFirst();
            assertEquals(ALLERGY1_ID, allergyCursor.getInt(0));
            assertEquals(FOOD1_ID, allergyCursor.getString(1));
            assertEquals(DOG1_ID, allergyCursor.getString(2));
        } else {
            fail();
        }
    }
    @Test
    public void removeAllergy() throws Exception {
        RexDatabaseUtilities.removeAllergy(context, ALLERGY1_ID);
        Cursor allergyCursor = RexDatabaseUtilities.getAllergies(context);
        assertNull(allergyCursor);
    }


    @Test
    public void updateName() throws Exception {
        boolean success = RexDatabaseUtilities.updateName(context, NEW_NAME);
        assertEquals(true, success);
        Cursor dogNameCursor = RexDatabaseUtilities.getDog(context);
        if (dogNameCursor != null) {
            dogNameCursor.moveToFirst();
            assertEquals(NEW_NAME, dogNameCursor.getString(1));
        } else {
            fail();
        }
    }

    @Test
    public void updateDogBreed() throws Exception {
        boolean success = RexDatabaseUtilities.updateBreed(context, NEW_BREED);
        assertEquals(true, success);
        Cursor dogBreedCursor = RexDatabaseUtilities.getDog(context);
        if (dogBreedCursor != null) {
            dogBreedCursor.moveToFirst();
            assertEquals(NEW_BREED, dogBreedCursor.getString(3));
        } else {
            fail();
        }
        //TODO: where does this really go?
        db.close();
    }

    @Test
    public void updateDogWeight() throws Exception {
        boolean success = RexDatabaseUtilities.updateWeight(context, NEW_WEIGHT);
        assertEquals(true, success);
        Cursor dogWeightCursor = RexDatabaseUtilities.getDog(context);
        if (dogWeightCursor != null) {
            dogWeightCursor.moveToFirst();
            assertEquals(NEW_WEIGHT, dogWeightCursor.getString(2));
        } else {
            fail();
        }
    }


    @After
    public void takeDown() {
//        db.close();
    }
}
