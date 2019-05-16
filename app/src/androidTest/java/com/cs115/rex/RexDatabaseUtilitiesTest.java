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
    private static final String DOG2_ID = "1";
    private static final String DOG1_NAME = "Bruno";
    private static final String DOG1_WEIGHT = "235";
    private static final String DOG1_BREED = "Retriever";
    private static final String DOG1_PHOTO = null;
    private static final int FOOD1_ID = 1;
    private static final String FOOD1_NAME = "Alcohol";
    private static final String FOOD2_NAME = "Ch";
    private static final String FOOD3_NAME = "Chamomile";
    private static final String FOOD4_NAME = "Cherry";
    private static final String FOOD5_NAME = "Chicken";
    private static final String FOOD6_NAME = "Chive";
    private static final String FOOD7_NAME = "Chocolate";
    private static final String FOOD3_BLURB = "2131492924";
    private static final String FOOD3_TOX = "6";
    private static final String FOOD3_PHOTO = "2131099739";
    private static final int ALLERGY1_ID = 1;
    private static final String NEW_NAME = "Benji";
    private static final String NEW_BREED = "Labrador";
    private static final String NEW_WEIGHT = "220";
    private static final String NEW_PHOTO = "photo";

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

    //Still not passing
    @Test
    public void getAllergies() throws Exception {
        RexDatabaseUtilities.addAllergy(context, FOOD1_ID, DOG1_ID);
        Cursor allergyCursor = RexDatabaseUtilities.getAllergies(context);
        if (allergyCursor != null) {
            allergyCursor.moveToFirst();
            assertEquals("1", allergyCursor.getString(0));
            //DatabaseUtils.dumpCursorToString(allergyCursor);
        } else {
            fail();
        }
    }

    @Test
    public void getAllergyNames() throws Exception {
        RexDatabaseUtilities.addAllergy(context, FOOD1_ID, DOG1_ID);
        String[] allergyNames = RexDatabaseUtilities.getAllergyNames(context, "0");
        if (allergyNames != null) {
            assertEquals(FOOD1_NAME, allergyNames[1]);
        } else {
            fail();
        }
    }

    //Commented out test whose method has been removed
    /*
    //Same method as getAllergies(), also not passing
    @Test
    public void getFood() throws Exception {
        RexDatabaseUtilities.addAllergy(context, FOOD1_ID, DOG1_ID);
        Cursor allergyCursor = RexDatabaseUtilities.getFood(context);
        if (allergyCursor != null){
            allergyCursor.moveToFirst();
            //assertEquals(ALLERGY1_ID, );
            assertEquals(DOG2_ID, allergyCursor.getString(0));
            //assertEquals(DOG1_ID, allergyCursor.getInt(2));
        } else {
            fail();
        }
    }
    */


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


    //commented out non-compiling test
    @Test
    public void getSelectedFoodList() throws Exception {
        Cursor foodListCursor = RexDatabaseUtilities.getSelectedFoodList(context, FOOD2_NAME);
        if (foodListCursor != null){
            foodListCursor.moveToFirst();
            assertEquals(FOOD3_NAME, foodListCursor.getString(1));
            foodListCursor.moveToNext();
            assertEquals(FOOD4_NAME, foodListCursor.getString(1));
            foodListCursor.moveToNext();
            assertEquals(FOOD5_NAME, foodListCursor.getString(1));
            foodListCursor.moveToNext();
            assertEquals(FOOD6_NAME, foodListCursor.getString(1));
            foodListCursor.moveToNext();
            assertEquals(FOOD7_NAME, foodListCursor.getString(1));
        } else {
            fail();
        }
    }

    @Test
    public void getSelectedFoodName() throws Exception {
        String[] foodNames = RexDatabaseUtilities.getSelectedFoodNames(context, FOOD2_NAME);
        if (foodNames != null) {
            assertEquals(FOOD3_NAME, foodNames[0]);
            assertEquals(FOOD4_NAME, foodNames[1]);
            assertEquals(FOOD5_NAME, foodNames[2]);
            assertEquals(FOOD6_NAME, foodNames[3]);
            assertEquals(FOOD7_NAME, foodNames[4]);
        } else {
            fail();
        }
    }

    //Commented out test whose method has been removed
    /*
    @Test
    public void getFoodByName() throws Exception {
        String[] food = RexDatabaseUtilities.getFoodByName(context, FOOD3_NAME);
        if (food != null) {
            assertEquals(FOOD3_NAME, food[0]);
            assertEquals(FOOD3_TOX, food[1]);
            assertEquals(FOOD3_PHOTO, food[2]);
            assertEquals(FOOD3_BLURB, food[3]);
        } else {
            fail();
        }
    }
    */


    @Test
    public void removeAllergy() throws Exception {
        RexDatabaseUtilities.addAllergy(context, FOOD1_ID, DOG1_ID);
        //Cursor allergyCursor = RexDatabaseUtilities.getAllergies(context);
        RexDatabaseUtilities.removeAllergy(context, FOOD1_ID, DOG1_ID);

        //allergyCursor.moveToFirst();
        //assertEquals(false, allergyCursor);
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
        boolean success = RexDatabaseUtilities.updatePhoto(context, NEW_PHOTO);
        assertEquals(true, success);
        Cursor dogPhotoCursor = RexDatabaseUtilities.getDog(context);
        if (dogPhotoCursor != null) {
            dogPhotoCursor.moveToFirst();
            assertEquals(NEW_PHOTO, dogPhotoCursor.getString(3));
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
