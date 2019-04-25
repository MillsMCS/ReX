package com.cs115.rex;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class RexDatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "rex";
    private static final int DB_VERSION = 0;

    RexDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        updateMyDatabase(db, 0, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateMyDatabase(db, oldVersion, newVersion);

    }

    //private static void updateDogName(SQLiteDatabase db, )


    private void updateMyDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 1) {
            db.execSQL("CREATE TABLE FOOD (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "NAME TEXT, "
                    + "TOXICITY INTEGER, "
                    + "IMAGE_RESOURCE_ID INTEGER, "
                    + "QUOTE TEXT);");
            db.execSQL("CREATE TABLE DOG (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "NAME TEXT, "
                    + "WEIGHT TEXT, "
                    + "BREED TEXT, "
                    + "PHOTO TEXT)");
            db.execSQL("CREATE TABLE ALLERGIES (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "FOREIGN KEY(FOOD_ID) REFERENCES FOOD(_id),"
                    + "FOREIGN KEY(DOG_ID) REFERENCES DOG(_id));");
//            insertDrink(db, "Latte", "Espresso and steamed milk", R.drawable.latte);
//            insertDrink(db, "Cappuccino", "Espresso, hot milk and steamed-milk foam",R.drawable.cappuccino);
//            insertDrink(db, "Filter", "Our best drip coffee", R.drawable.filter);

        }
    }
}
