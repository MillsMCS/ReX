package com.cs115.rex;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.LayoutInflater;

public class RexDatabaseUtilities {

    private static String TAG = "databaseUtilities";

    /**
     * Gets all elements of dog from dog table.
     *
     * @param context the context
     * @return cursor with all the different elements of the dog from dog table or null if there's an error
     * @author Karena Huang
     */
    public static Cursor getDog(Context context) {
        try {

            SQLiteOpenHelper rexDatabaseHelper = new RexDatabaseHelper(context);
            SQLiteDatabase db = rexDatabaseHelper.getReadableDatabase();
            return db.query(RexDatabaseHelper.DOG,
                    new String[]{
                            RexDatabaseHelper.NAME,
                            RexDatabaseHelper.WEIGHT,
                            RexDatabaseHelper.BREED,
                            RexDatabaseHelper.PHOTO},
                    RexDatabaseHelper.ID + " = ?",
                    new String[]{Integer.toString(RexDatabaseHelper.SINGLE_DOG_ID)},
                    null, null, null);
        } catch (SQLiteException e) {
            return null;
        }
    }

    // get cursor of all allergies

    /**
     * //TODO: See if this is used anywhere before due date.
     * //TODO: add argument for dogId
     * Gets all the allergies for a specific dog
     *
     * @param context the context
     * @return all the allergies tied to a specific dog or null if there's an error
     * @author Karena Huang
     */
    public static Cursor getAllergies(Context context) {
        try {
            SQLiteOpenHelper rexDatabaseHelper = new RexDatabaseHelper(context);
            SQLiteDatabase db = rexDatabaseHelper.getReadableDatabase();
            return db.query(RexDatabaseHelper.ALLERGIES,
                    new String[]{
                            RexDatabaseHelper.FOOD_ID,
                            RexDatabaseHelper.DOG_ID},
                    RexDatabaseHelper.ALLERGY_DOG_ID + " = ?",
                    new String[]{Integer.toString(RexDatabaseHelper.SINGLE_DOG_ID)},
                    null, null, null);
        } catch (SQLiteException e) {
            return null;
        }
    }

    /**
     * Gets name of each allergy for a certain dog.
     *
     * @param context the context
     * @param dogId the dog ID shared with allergy table and dog table
     * @return string array with all allergies associated with a specific dog
     * @author Karena Huang
     */
    public static String[] getAllergyNames(Context context, String dogId) {
        try {
            SQLiteOpenHelper rexDatabaseHelper = new RexDatabaseHelper(context);
            SQLiteDatabase db = rexDatabaseHelper.getReadableDatabase();
            String query = "SELECT FOOD._id, NAME FROM FOOD INNER JOIN ALLERGIES ON ALLERGIES.FOOD_ID = FOOD._id WHERE ALLERGIES.DOG_ID=" + dogId;
            Cursor cursor = db.rawQuery(query, null);
            if (cursor != null) {
                int indexFoodNames = cursor.getColumnIndex(RexDatabaseHelper.NAME);
                String[] foodNames = new String[cursor.getCount()];
                int theCount = 0;
                while (cursor.moveToNext()) {
                    foodNames[theCount] = cursor.getString(indexFoodNames);
                    theCount += 1;
                }
                cursor.close();
                return foodNames;
            }
        } catch (SQLiteException e) {
            return new String[0];
        }
        return new String[0];
    }

    /**
     * Gets all food names from food table.
     *
     * @param context the context
     * @return food Array containing all food names
     * @author Karena Huang
     */
    public static String[] getAllFoodNames(Context context) {
        try {
            SQLiteOpenHelper rexDatabaseHelper = new RexDatabaseHelper(context);
            SQLiteDatabase db = rexDatabaseHelper.getReadableDatabase();
            Cursor cursor = db.query(RexDatabaseHelper.FOOD,
                    new String[]{
                            RexDatabaseHelper.NAME},
                    null, null, null, null, null
            );
            String[] foodArray = new String[cursor.getCount()];
            int index = 0;
            while (cursor.moveToNext()) {
                foodArray[index] = cursor.getString(0);
                index += 1;
            }
            cursor.close();
            return foodArray;

        } catch (SQLiteException e) {
            return null;
        }
    }

    /**
     * Gets all food Ids from food table.
     *
     * @param context the context
     * @return array of ints containing food IDs
     * @author Karena Huang
     */
    public static int[] getAllFoodId(Context context) {
        try {
            SQLiteOpenHelper rexDatabasehelper = new RexDatabaseHelper(context);
            SQLiteDatabase db = rexDatabasehelper.getReadableDatabase();
            Cursor cursor = db.query(RexDatabaseHelper.FOOD,
                    new String[]{
                            RexDatabaseHelper.ID},
                    null, null, null, null, null
            );
            int[] intArray = new int[cursor.getCount()];
            int index = 0;
            while (cursor.moveToNext()) {
                intArray[index] = cursor.getInt(0);
                index += 1;
            }
            cursor.close();
            return intArray;
        } catch (SQLiteException e) {
            return null;
        }
    }

    /**
     *
     * @param context
     * @return
     * @author Zoe
     */
    public static Cursor getFood(Context context) {
        try {
            SQLiteOpenHelper rexDatabaseHelper = new RexDatabaseHelper(context);
            SQLiteDatabase db = rexDatabaseHelper.getReadableDatabase();
            return db.query(RexDatabaseHelper.ALLERGIES,
                    new String[]{
                            RexDatabaseHelper.FOOD_ID,
                            RexDatabaseHelper.DOG_ID},
                    RexDatabaseHelper.ALLERGY_DOG_ID + " = ?",
                    new String[]{Integer.toString(RexDatabaseHelper.SINGLE_DOG_ID)},
                    null, null, null);

        } catch (SQLiteException e) {
            return null;
        }
    }

    /**
     *
     * @param context
     * @param searchName
     * @return
     * @author Zoe
     */
    //get a cursor with all of the foods that match the given query
    public static Cursor getSelectedFoodList(Context context, String searchName){
        try {
            SQLiteOpenHelper rexDatabaseHelper = new RexDatabaseHelper(context);
            SQLiteDatabase db = rexDatabaseHelper.getReadableDatabase();

            //Finds anything that includes the search String input by the user
            return db.query(RexDatabaseHelper.FOOD,
                    new String[]{
                            "_id", RexDatabaseHelper.NAME},
                    "NAME" + " LIKE ?", new String[]{searchName + "%"}, null, null, null
            );

        } catch (SQLiteException e) {
            Log.d("DebugLog: ", "MainActivity - Value: " + "Database exception");
            return null;
        }
    }

    /**
     *
     * @param context
     * @param searchName
     * @return
     * @author Zoe
     */
    //get all of the foods that match the given query
    public static String[] getSelectedFoodNames(Context context, String searchName){
        try {
            SQLiteOpenHelper rexDatabaseHelper = new RexDatabaseHelper(context);
            SQLiteDatabase db = rexDatabaseHelper.getReadableDatabase();

            //Finds anything that includes the search String input by the user
            Cursor cursor = db.query(RexDatabaseHelper.FOOD,
                    new String[]{
                            "_id", RexDatabaseHelper.NAME},
                    "NAME" + " LIKE ?", new String[]{searchName + "%"}, null, null, null
            );

            String[] foodArray = new String[cursor.getCount()];
            int theCount = 0;
            while(cursor.moveToNext()) {
                foodArray[theCount] = cursor.getString(1);
                theCount += 1;
            }

            //return cursor;
            cursor.close();
            db.close();
            Log.d("DebugLog: ", "DatabaseUtils - Value: " + foodArray[foodArray.length-1]);
            return foodArray;

        } catch (SQLiteException e) {
            Log.d("DebugLog: ", "MainActivity - Value: " + "Database exception");
            return null;
        }
    }


    /**
     *
     * @param context
     * @param itemId
     * @return
     * @author Zoe
     */
    //get all of the database data for a particular food given that food's name
    public static Cursor getFoodById(Context context, long itemId){

        try {
            SQLiteOpenHelper rexDatabaseHelper = new RexDatabaseHelper(context);
            SQLiteDatabase db = rexDatabaseHelper.getReadableDatabase();
            return db.query(RexDatabaseHelper.FOOD,
                    new String[]{
                            RexDatabaseHelper.NAME, RexDatabaseHelper.TOXICITY, RexDatabaseHelper.IMAGE_RESOURCE_ID,
                            RexDatabaseHelper.QUOTE},
                    "_id = ?", new String[] {Long.toString(itemId)}, null, null, null
            );
            /*
            String[] thisFood = new String[4];
            int theCount = 0;
            if(cursor.moveToFirst()) {
                thisFood[0] = cursor.getString(0);
                thisFood[1] = cursor.getString(1);
                thisFood[2] = cursor.getString(2);
                thisFood[3] = cursor.getString(3);
            }
            cursor.close();
            db.close();
            return thisFood;
            */
        } catch (SQLiteException e) {
            //TODO Add toast - food not available
            return null;
        }
    }


    /**
     * Updates dog name in dog profile.
     *
     * @param context the context
     * @param newDogName new dog name to be updated to in dog profile and in dog table
     * @return true if successful, false otherwise
     * @author Karena Huang
     */
    public static boolean updateName(Context context, String newDogName) {
        try {
            SQLiteOpenHelper rexDatabaseHelper = new RexDatabaseHelper(context);
            SQLiteDatabase db = rexDatabaseHelper.getReadableDatabase();
            ContentValues dogNameValues = new ContentValues();
            dogNameValues.put(RexDatabaseHelper.NAME, newDogName);
            db.update(RexDatabaseHelper.DOG, dogNameValues,
                    RexDatabaseHelper.ID + " = ?",
                    new String[]{Integer.toString(RexDatabaseHelper.SINGLE_DOG_ID)});
            db.close();
            return true;
        } catch (SQLiteException e) {
            return false;
        }
    }

    /**
     * Updates breed in dog profile.
     *
     * @param context the context
     * @param newBreed new breed to be updated to in dog profile and in dog table
     * @return true if successful, false otherwise
     */
    public static boolean updateBreed(Context context, String newBreed) {
        try {
            SQLiteOpenHelper rexDatabaseHelper = new RexDatabaseHelper(context);
            SQLiteDatabase db = rexDatabaseHelper.getReadableDatabase();
            ContentValues breedValues = new ContentValues();
            breedValues.put(RexDatabaseHelper.BREED, newBreed);
            db.update(RexDatabaseHelper.DOG, breedValues,
                    RexDatabaseHelper.ID + " = ?",
                    new String[]{Integer.toString(RexDatabaseHelper.SINGLE_DOG_ID)});

            db.close();
            return true;
        } catch (SQLiteException e) {
            return false;
        }
    }

    /**
     * Updates weight of dog in dog profile.
     *
     * @param context the context
     * @param newWeight new weight to be updated to in dog profile and in dog table
     * @return true if successful, false otherwise
     */
    public static boolean updateWeight(Context context, String newWeight) {
        try {
            SQLiteOpenHelper rexDatabaseHelper = new RexDatabaseHelper(context);
            SQLiteDatabase db = rexDatabaseHelper.getReadableDatabase();
            ContentValues weightValues = new ContentValues();
            weightValues.put(RexDatabaseHelper.WEIGHT, newWeight);
            db.update(RexDatabaseHelper.DOG, weightValues,
                    RexDatabaseHelper.ID + " = ?",
                    new String[]{Integer.toString(RexDatabaseHelper.SINGLE_DOG_ID)});

            db.close();
            return true;
        } catch (SQLiteException e) {
            return false;
        }
    }

    /**
     * Updates photo of dog in dog profile.
     *
     * @param context the context
     * @param theImage image to be uploaded in dog profile and updated to in dog table
     * @return true if successful, false otherwise
     */
    public static boolean updatePhoto(Context context, String theImage) {
        try {
            SQLiteOpenHelper rexDatabaseHelper = new RexDatabaseHelper(context);
            SQLiteDatabase db = rexDatabaseHelper.getReadableDatabase();

            ContentValues dogPhoto = new ContentValues();
            dogPhoto.put(RexDatabaseHelper.PHOTO, theImage);
            db.update(RexDatabaseHelper.DOG, dogPhoto, RexDatabaseHelper.ID + " =?",
                    new String[]{Integer.toString(RexDatabaseHelper.SINGLE_DOG_ID)});

            db.close();
            return true;
        } catch (SQLiteException e) {
            return false;

        }
    }

    /**
     * Adds specified allergy with associated dog to allergy table.
     *
     * @param context the context
     * @param foodId specifies what food to add to allergy table in association with specific dog
     * @param dogId specifying what food with what specific dog to add to allergy table
     * @return ID of allergy that was added
     */
    public static int addAllergy(Context context, int foodId, int dogId) {
        try {
            SQLiteOpenHelper rexDatabaseHelper = new RexDatabaseHelper(context);
            SQLiteDatabase db = rexDatabaseHelper.getReadableDatabase();

            //adding record to allergy table
            ContentValues foodAllergy = new ContentValues();
            foodAllergy.put(RexDatabaseHelper.FOOD_ID, foodId);
            foodAllergy.put(RexDatabaseHelper.DOG_ID, dogId);
            db.insert("ALLERGIES", null, foodAllergy);
            Cursor cursor = db.query(RexDatabaseHelper.ALLERGIES,
                    new String[]{
                            RexDatabaseHelper.ID},
                    RexDatabaseHelper.ALLERGY_DOG_ID + " = ?"
                            + " AND " + RexDatabaseHelper.FOOD_ID + " = ?",
                    new String[]{Integer.toString(dogId), Integer.toString(foodId)},
                    null, null, null);
            int allergyId = 0;
            if (cursor.moveToFirst()) {
                allergyId = cursor.getInt(0);
            }
            db.close();
            cursor.close();
            return allergyId;
        } catch (SQLiteException e) {
            return -1;
        }
    }

    /**
     * Removes specified allergy with associated dog from allergy table.
     *
     * @param context the context
     * @param foodId specifies what food to remove from allergy table in association with a specific dog
     * @param dogId specifying what food with what specific dog to remove from allergy table
     * @return true if successful, false otherwise
     */
    public static boolean removeAllergy(Context context, int foodId, int dogId) {
        try {
            SQLiteOpenHelper rexDatabaseHelper = new RexDatabaseHelper(context);
            SQLiteDatabase db = rexDatabaseHelper.getReadableDatabase();

            //query where allergy equals allergy id
            db.delete("ALLERGIES", RexDatabaseHelper.FOOD_ID + " = ? AND " +
                            RexDatabaseHelper.DOG_ID + " = ? ",
                    new String[]{String.valueOf(foodId), String.valueOf(dogId)});
            db.close();
            return true;
        } catch (SQLiteException e) {
            return false;
        }

    }
}
