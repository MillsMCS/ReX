package com.cs115.rex;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class RexDatabaseUtilities {
    RexDatabaseUtilities() {
    }

    //Rex methods
    public static Cursor getDog(Context context) {
        try {

            SQLiteOpenHelper rexDatabaseHelper = new RexDatabaseHelper(context);
            SQLiteDatabase db = rexDatabaseHelper.getReadableDatabase();
            return db.query(RexDatabaseHelper.DOG,
                new String[] {
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

    public static Cursor getAllergies(Context context) {
        try {
            SQLiteOpenHelper rexDatabaseHelper = new RexDatabaseHelper(context);
            SQLiteDatabase db = rexDatabaseHelper.getReadableDatabase();
            return db.query(RexDatabaseHelper.ALLERGIES,
                    new String[] {
                            RexDatabaseHelper.FOOD_ID,
                            RexDatabaseHelper.DOG_ID},
                    RexDatabaseHelper.ALLERGY_DOG_ID + " = ?",
                    new String[]{Integer.toString(RexDatabaseHelper.SINGLE_DOG_ID)},
                    null, null, null);

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

//    public static boolean updatePhoto(Context context, Blob theImage) {
//        SQLiteOpenHelper rexDatabaseHelper = new RexDatabaseHelper(context);
//        SQLiteDatabase db = rexDatabaseHelper.getReadableDatabase();
//        //holdback
//    }

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
                    new String[] {
                            RexDatabaseHelper.ID},
                    RexDatabaseHelper.ALLERGY_DOG_ID + " = ?"
                            +" AND " + RexDatabaseHelper.FOOD_ID+ " = ?",
                    new String[]{Integer.toString(dogId), Integer.toString(foodId)},
                    null, null, null);
            int allergyId = 0;
            if(cursor.moveToFirst()) {
                allergyId = cursor.getInt(0);
            }

            db.close();
            cursor.close();
            return allergyId;
        } catch (SQLiteException e) {
            return -1;
        }
    }

    public static boolean removeAllergy(Context context, int allergyId) {
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
