package com.cs115.rex;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class RexDatabaseUtilities {

    private static String TAG = "databaseUtilities";

    /**
     * Gets elements of dog.
     *
     * @param context
     * @return
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
     * Gets a cursor of all the allergies for a specific dog
     *
     * @param context
     * @return
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
     * Gets food name of each allergy for a certain dog.
     *
     * @param context
     * @param dogId
     * @return
     * @author Karena Huang
     */
    public static String[] getAllergyNames(Context context, String dogId) {
        try {
            SQLiteOpenHelper rexDatabaseHelper = new RexDatabaseHelper(context);
            SQLiteDatabase db = rexDatabaseHelper.getReadableDatabase();
            String query = "SELECT FOOD._id, NAME FROM FOOD INNER JOIN ALLERGIES ON ALLERGIES.FOOD_ID = FOOD._id WHERE ALLERGIES.DOG_ID=" + dogId;
            Cursor cursor = db.rawQuery(query, null);
            if (cursor != null) {
                DatabaseUtils.dumpCursor(cursor);
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
     * Gets all food names.
     *
     * @param context
     * @return all food names
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
            int theCount = 0;
            while (cursor.moveToNext()) {
                foodArray[theCount] = cursor.getString(0);
                theCount += 1;
            }
            cursor.close();
            return foodArray;

        } catch (SQLiteException e) {
            return null;
        }
    }

    /**
     * returns all food Ids
     *
     * @param context
     * @return food Ids
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
            int theCount = 0;
            while (cursor.moveToNext()) {
                intArray[theCount] = cursor.getInt(0);
                theCount += 1;
            }
            cursor.close();
            return intArray;
        } catch (SQLiteException e) {
            return null;
        }
    }

    /**
     * updates dog name
     *
     * @param context
     * @param newDogName
     * @return
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
            //make new content values
            //put new values with the contentvalues.put() method...
            //database update(table name, content vlaues, RexDatabaseHelper.ID + " = ?",
            //new String[]{Integer.toString(RexDatabaseHelper.SINGLE_DOG_ID)

            db.close();
            return true;
        } catch (SQLiteException e) {
            return false;
        }
    }

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

    public static boolean removeAllergy(Context context, String foodId, String dogId) {
        try {
            SQLiteOpenHelper rexDatabaseHelper = new RexDatabaseHelper(context);
            SQLiteDatabase db = rexDatabaseHelper.getReadableDatabase();

            //query where allergy equals allergy id
            db.delete("ALLERGIES", RexDatabaseHelper.FOOD_ID + " = ? AND " +
                            RexDatabaseHelper.DOG_ID + " = ? ",
                    new String[]{foodId, dogId});
            db.close();
            return true;
        } catch (SQLiteException e) {
            return false;
        }

    }
}