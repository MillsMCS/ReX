package com.cs115.rex;


import android.content.Context;
import android.database.Cursor;
import android.support.test.InstrumentationRegistry;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

public class RexDatabaseUtilitiesTest {

    private static final int DOG1_ID = 0;
    private static final String DOG1_NAME = "Bruno";
    private static final String DOG1_WEIGHT = "235";
    private static final String DOG1_BREED = "Retriever";
    private static final String DOG1_PHOTO = null;
    private static final int FOOD1_ID = 1;
    private static final String FOOD1_NAME = "Alcohol";
    private static final String FOOD1_TOX = "9";
    private static final String FOOD2_NAME = "Ch";
    private static final String FOOD3_NAME = "Chamomile";
    private static final String FOOD4_NAME = "Cherry";
    private static final String FOOD5_NAME = "Chicken";
    private static final String FOOD6_NAME = "Chive";
    private static final String FOOD7_NAME = "Chocolate";
    private static final String FOOD1_BLURB = "2131492927";
    private static final String FOOD1_PHOTO = "2131099733";
    private static final String ALLERGY_ID = "12";
    private static final String NEW_NAME = "Benji";
    private static final String NEW_BREED = "Labrador";
    private static final String NEW_WEIGHT = "220";
    private static final String NEW_PHOTO = "photo";
    private Context context = InstrumentationRegistry.getTargetContext();


    //Expects the updated dog values if run more than once
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
    public void getAllergies() throws Exception {
        RexDatabaseUtilities.addAllergy(context, FOOD1_ID, DOG1_ID);
        Cursor allergyCursor = RexDatabaseUtilities.getAllergies(context);
        if (allergyCursor != null) {
            allergyCursor.moveToFirst();
            assertEquals(ALLERGY_ID, allergyCursor.getString(0));
        } else {
            fail();
        }
    }

    //Fails: values from added record still present, not removed
    @Test
    public void removeAllergy() throws Exception {
        RexDatabaseUtilities.addAllergy(context, FOOD1_ID, DOG1_ID);
        RexDatabaseUtilities.removeAllergy(context, FOOD1_ID, DOG1_ID);
        Cursor allergyCursor2 = RexDatabaseUtilities.getAllergies(context);
        allergyCursor2.moveToFirst();
        assertNull(allergyCursor2.getString(0));
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

    @Test
    public void getFoodById() throws Exception {
        Cursor foodId = RexDatabaseUtilities.getFoodById(context, FOOD1_ID);
        if (foodId != null) {
            foodId.moveToFirst();
            assertEquals(FOOD1_NAME, foodId.getString(0));
            assertEquals(FOOD1_TOX, foodId.getString(1));
            assertEquals(FOOD1_PHOTO, foodId.getString(2));
            assertEquals(FOOD1_BLURB, foodId.getString(3));
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

}
