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

    RexDatabaseUtilities() {
    }

    //Rex methods
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


    public static String[] getAllergiesRawQuery(Context context) {
        try {
            SQLiteOpenHelper rexDatabaseHelper = new RexDatabaseHelper(context);
            SQLiteDatabase db = rexDatabaseHelper.getReadableDatabase();
            String query = "SELECT FOOD._id, NAME FROM FOOD INNER JOIN ALLERGIES ON ALLERGIES.FOOD_ID = FOOD._id WHERE ALLERGIES.DOG_ID=" + String.valueOf(RexDatabaseHelper.SINGLE_DOG_ID);
            Cursor cursor = db.rawQuery(query, null);
            if (cursor != null) {
                DatabaseUtils.dumpCursor(cursor);
                int indexFoodNames = cursor.getColumnIndex(RexDatabaseHelper.NAME);
                Log.d(TAG, String.valueOf(indexFoodNames));
                String[] foodNames = new String[cursor.getCount()];
                int theCount = 0;
                while (cursor.moveToNext()) {
                    Log.d(TAG, cursor.getString(indexFoodNames));
                    foodNames[theCount] = cursor.getString(indexFoodNames);
                    theCount += 1;
                }
                cursor.close();
                return foodNames;
            }
        } catch (SQLiteException e) {
            Log.d(TAG, e.toString());
            return null;
        }
        String[] foodNames = new String[0];
        return foodNames;
    }


    // SELECT * FROM ALLERGIES, FOODS
    // WHERE ALLERGIES.DOG_ID = SINGLE_DOG_ID
    // GROUP BY ALLERGIES.FOOD_ID

    // FOOD_NAMES

    // make String[] of allergy names
    public static String[] getAllergyNames(Context context){
        Cursor cursor = getAllergies(context);
        if (cursor != null){
            String[] foodIds = new String[cursor.getCount()];
            int theCount = 0;
            while(cursor.moveToNext()) {
                foodIds[theCount] = cursor.getString(0);
                theCount += 1;
            }
            cursor.close();
            // [9, 3, 4, 6]
            // query the FOOD table and connect Ids to names
            return foodIds;
        } else {
            String[] names = new String[0];
            return names;
        }
    }


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
            while(cursor.moveToNext()) {
                foodArray[theCount] = cursor.getString(0);
                theCount += 1;

            }
            cursor.close();
            return foodArray;

        } catch (SQLiteException e) {
            return null;
        }
    }

    public static int[] getAllFoodId(Context context) {
        try {
            SQLiteOpenHelper rexDatabasehelper = new RexDatabaseHelper(context);
            SQLiteDatabase db = rexDatabasehelper.getReadableDatabase();
            Cursor cursor = db.query(RexDatabaseHelper.FOOD,
                    new String[] {
                            RexDatabaseHelper.ID},
                    null, null, null, null, null
                    );
            int[] intArray = new int[cursor.getCount()];
            int theCount = 0;
            while(cursor.moveToNext()) {
                intArray[theCount] = cursor.getInt(0);
                theCount += 1;
            }
            cursor.close();
            return intArray;
        } catch (SQLiteException e) {
            return null;
        }
    }

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

        public static int addAllergy(Context context,int foodId, int dogId){
            try {
                Log.d(TAG, "adding allergy...");

                SQLiteOpenHelper rexDatabaseHelper = new RexDatabaseHelper(context);
                SQLiteDatabase db = rexDatabaseHelper.getReadableDatabase();

                //adding record to allergy table
                ContentValues foodAllergy = new ContentValues();
                foodAllergy.put(RexDatabaseHelper.FOOD_ID, foodId);
                foodAllergy.put(RexDatabaseHelper.DOG_ID, dogId);
                Log.d(TAG, foodAllergy.toString());

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
                Log.d(TAG, String.valueOf(allergyId));
                return allergyId;
            } catch (SQLiteException e) {
                return -1;
            }
        }

        public static boolean removeAllergy(Context context,int allergyId){
            try {
                SQLiteOpenHelper rexDatabaseHelper = new RexDatabaseHelper(context);
                SQLiteDatabase db = rexDatabaseHelper.getReadableDatabase();

                //query where allergy equals allergy id
                db.delete("ALLERGIES", RexDatabaseHelper.ID + " = ?",
                        new String[]{Integer.toString(allergyId)});
                db.close();
                //delete
                return true;
            } catch (SQLiteException e) {
                return false;
            }

        }


    }