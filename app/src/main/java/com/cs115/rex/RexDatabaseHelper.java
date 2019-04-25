package com.cs115.rex;

import android.content.ContentValues;
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
            db.execSQL("CREATE TABLE FOOD (" +"_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "NAME TEXT, "
                    + "TOXICITY INTEGER, "
                    + "IMAGE_RESOURCE_ID INTEGER, "
                    + "QUOTE INTEGER);");
            insertFood(db,"Alcohol", 9, R.drawable.alcohol,R.string.symptom9);
            insertFood(db,"Apple", 8, R.drawable.apple,R.string.symptom8);
            insertFood(db,"Apricot", 8, R.drawable.apricot,R.string.symptom8);
            insertFood(db,"Avocado", 1, R.drawable.avocado,R.string.symptom1);
            insertFood(db,"Bay Leaf", 7, R.drawable.bay_leaf,R.string.symptom7);
            insertFood(db,"Beet", 1, R.drawable.beet,R.string.symptom1);
            insertFood(db,"Chamomile", 6, R.drawable.chamomile,R.string.symptom6);
            insertFood(db,"Cherry", 8, R.drawable.cherry,R.string.symptom8);
            insertFood(db,"Chicken", 1, R.drawable.chicken,R.string.symptom1);
            insertFood(db,"Chive", 7, R.drawable.chive,R.string.symptom7);
            insertFood(db,"Chocolate", 10, R.drawable.chocolate,R.string.symptom10);
            insertFood(db,"Citrus", 2, R.drawable.citrus,R.string.symptom2);
            insertFood(db,"Coconut", 3, R.drawable.coconut,R.string.symptom3);
            insertFood(db,"Coffee", 10, R.drawable.coffee,R.string.symptom10);
            insertFood(db,"Egg", 1, R.drawable.egg,R.string.symptom1);
            insertFood(db,"Fig", 4, R.drawable.fig,R.string.symptom4);
            insertFood(db,"Garlic", 8, R.drawable.garlic,R.string.symptom_onion_family);
            insertFood(db,"Grape", 7, R.drawable.grape,R.string.symptom_grape);
            insertFood(db,"Leek", 8, R.drawable.leek,R.string.symptom_onion_family);
            insertFood(db,"Marijuana", 8, R.drawable.marijuana,R.string.symptom_marijuana);
            insertFood(db,"Nut", 8, R.drawable.nuts,R.string.symptom8);
            insertFood(db,"Onion", 8, R.drawable.onion,R.string.symptom_onion_family);
            insertFood(db,"Peach", 8, R.drawable.peach,R.string.symptom8);
            insertFood(db,"Plum", 8, R.drawable.plum,R.string.symptom8);
            insertFood(db,"Pumpkin", 1, R.drawable.pumpkin,R.string.symptom1);
            insertFood(db,"Tomato", 1, R.drawable.tomato,R.string.symptom1);
            insertFood(db,"Watermelon", 1, R.drawable.watermelon,R.string.symptom1);
            insertFood(db,"Yeast Dough", 5, R.drawable.yeast_dough,R.string.symptom5);
            db.execSQL("CREATE TABLE DOG (" + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "NAME TEXT, "
                    + "WEIGHT TEXT, "
                    + "BREED TEXT, "
                    + "PHOTO TEXT)");
            db.execSQL("CREATE TABLE ALLERGIES (" + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "FOREIGN KEY(FOOD_ID) REFERENCES FOOD(_id),"
                    + "FOREIGN KEY(DOG_ID) REFERENCES DOG(_id));");
//            insertDrink(db, "Latte", "Espresso and steamed milk", R.drawable.latte);
//            insertDrink(db, "Cappuccino", "Espresso, hot milk and steamed-milk foam",R.drawable.cappuccino);
//            insertDrink(db, "Filter", "Our best drip coffee", R.drawable.filter);

        }
    }
    private static void insertFood(SQLiteDatabase db, String name, int toxicity, int resourceId, int quote){
        ContentValues foodValues = new ContentValues();
        foodValues.put("NAME", name);
        foodValues.put("TOXICITY", toxicity);
        foodValues.put("IMAGE_RESOURCE_ID", resourceId);
        foodValues.put("QUOTE", quote);
        db.insert("FOOD", null, foodValues);
    }
    private static void insertDog(SQLiteDatabase db, String name, String weight, String breed, String photo){
        ContentValues dogValues = new ContentValues();
        dogValues.put("NAME", name);
        dogValues.put("WEIGHT", weight);
        dogValues.put("BREED", breed);
        dogValues.put("PHOTO", photo);
        db.insert("DOG", null, dogValues);
    }
    private static void insertAllergie(SQLiteDatabase db, int foodId, int dogID){
        ContentValues allergiesValues = new ContentValues();
        allergiesValues.put("FOOD_ID", foodId);
        allergiesValues.put("DOG_ID", dogID);
        db.insert("ALLERGIES", null, allergiesValues);
    }
}
