package com.cs115.rex;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class RexDatabaseUtilities {
    RexDatabaseUtilities() {
    }

    //Rex methods
    //Do we need any get methods?
    //And what should

    static boolean updateDog(Context context) {
        try {
            SQLiteOpenHelper rexDatabaseHelper = new RexDatabaseHelper(context);
            SQLiteDatabase db = rexDatabaseHelper.getWritableDatabase();
            ContentValues dogValues = new ContentValues();

            db.close();
            return true;
        } catch (SQLiteException e) {
            return false;
        }
    }



}
