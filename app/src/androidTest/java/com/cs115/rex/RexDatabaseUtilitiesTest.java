package com.cs115.rex;


import android.content.Context;
import android.database.Cursor;
import android.support.test.InstrumentationRegistry;

import org.junit.After;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;


public class RexDatabaseUtilitiesTest {

    private static final int DOG1_ID = 0;
    private static final String DOG1_NAME = "Bruno";
    private static final String DOG1_WEIGHT = "235";
    private static final String DOG1_BREED = "Retriever";
    private static final String DOG1_PHOTO = null;
    private static final String NEW1_PHOTO = "new photo";
    private static final int FOOD1_ID = 1;
    private static final String FOOD1_NAME = "Alcohol";
    private static final String NEW_NAME = "Benji";
    private static final String NEW_BREED = "Labrador";
    private static final String NEW_WEIGHT = "220";

    // This creates a temporary context so database accesses in the tests are isolated.
    private Context context = InstrumentationRegistry.getTargetContext();



//Running the updateDog() test will switch the old dog info to the new dog info. The next time
    //the getDog() test is run, the new info will be expected
    @Test
    public void getDog() throws Exception {
        Cursor dogCursor = RexDatabaseUtilities.getDog(context);
        if (dogCursor != null){
            dogCursor.moveToFirst();
            assertEquals(DOG1_NAME, dogCursor.getString(0));
            assertEquals(DOG1_WEIGHT, dogCursor.getString(1));
            assertEquals(DOG1_BREED, dogCursor.getString(2));
            assertEquals(DOG1_PHOTO, dogCursor.getString(3));
        } else {
            fail();
        }
    }


    @Test
    public void getAllFoodNames() throws Exception {
        String[] foodNames = RexDatabaseUtilities.getAllFoodNames(context);
        if (foodNames != null) {
            assertEquals(FOOD1_NAME, foodNames[0]);
        } else {
            fail();
        }
    }

    @Test
    public void getAllFoodId() throws Exception {
        int[] foodID = RexDatabaseUtilities.getAllFoodId(context);
        if (foodID != null) {
            assertEquals(FOOD1_ID, foodID[0]);
        } else {
            fail();
        }
    }

    @Test
    public void getAllergyNames() throws Exception {
        String[] allergyNames = RexDatabaseUtilities.getAllergyNames(context, Integer.toString(DOG1_ID));
        if (allergyNames != null) {
            assertEquals(FOOD1_NAME, allergyNames[0]);
        } else {
            fail();
        }
    }


    @Test
    public void updateName() throws Exception {
        boolean success = RexDatabaseUtilities.updateName(context, NEW_NAME);
        assertEquals(true, success);
        Cursor dogNameCursor = RexDatabaseUtilities.getDog(context);
        if (dogNameCursor != null) {
            dogNameCursor.moveToFirst();
            assertEquals(NEW_NAME, dogNameCursor.getString(0));
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
            assertEquals(NEW_BREED, dogBreedCursor.getString(2));
        } else {
            fail();
        }

    }

    @Test
    public void updateDogWeight() throws Exception {
        boolean success = RexDatabaseUtilities.updateWeight(context, NEW_WEIGHT);
        assertEquals(true, success);
        Cursor dogWeightCursor = RexDatabaseUtilities.getDog(context);
        if (dogWeightCursor != null) {
            dogWeightCursor.moveToFirst();
            assertEquals(NEW_WEIGHT, dogWeightCursor.getString(1));
        } else {
            fail();
        }
    }

    @Test
    public void updateDogPhoto() throws Exception {
        boolean success = RexDatabaseUtilities.updatePhoto(context, NEW1_PHOTO);
        assertEquals(true, success);
        Cursor dogPhotoCursor = RexDatabaseUtilities.getDog(context);
        if (dogPhotoCursor != null) {
            dogPhotoCursor.moveToFirst();
            assertEquals(NEW1_PHOTO, dogPhotoCursor.getString(3));
        } else {
            fail();
        }
    }



    @After
    public void takeDown() {
//        db.close();
        //TODO: where does this really go?
        //db.close();
    }
}
