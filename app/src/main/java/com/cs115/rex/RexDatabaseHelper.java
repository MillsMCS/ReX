package com.cs115.rex;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class RexDatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = null;

    private static final String DB_NAME = "rex";
    private static final int DB_VERSION = 0;
    // Table names
    private static final String FOOD_TABLE = "food";
    private static final String DOG_TABLE = "dog";
    private static final String ALLERGIES_TABLE = "allergies";

    //column names FOOD_TABLE
    private static final Integer FOOD_ID = 0;
    private static final String FOOD_NAME = "food_name";
    private static final Integer FOOD_TOXICITY = 0;
    private static final Integer FOOD_IMAGE_RESOURCE_ID = 0;
    private static final Integer FOOD_QUOTE = 0;

    //column names DOG_TABLE
    private static final Integer DOG_ID = 0;
    private static final String DOG_NAME = "dog_name";
    private static final Integer DOG_WEIGHT = 0;
    private static final String  DOG_BREED = "dog_breed";
    private static final String  DOG_PHOTO = "dog_iamge";

    //column names ALLERGIES_TABLE
    private static final Integer ALLERGIE_ID = 0;


//
//    SQLiteDatabase db;
//    ContentResolver contentResolver;


    RexDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
//        contentResolver = context.getContentResolver();
//        db = this.getWritableDatabase();
        Log.d(TAG, "Created");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        updateMyDatabase(db, 0, DB_VERSION);
        Log.d(TAG, "Databases created successfully");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateMyDatabase(db, oldVersion, newVersion);

    }

//    //private static void updateDogName(SQLiteDatabase db, )
//
//
    private void updateMyDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 1) {
            //FOOD_TABLE create statement
            final String CREATE_TABLE_FOOD = "CREATE TABLE " + FOOD_TABLE + " (" +
                    FOOD_ID + "INTEGER PRIMARY KEY AUTOINCREMENT," +
                    FOOD_NAME + "TEXT," +
                    FOOD_TOXICITY + "INTEGER, " +
                    FOOD_IMAGE_RESOURCE_ID + "INTEGER," +
                    FOOD_QUOTE + "INTEGER);";

            // DOG_TABLE create statement
            final String CREATE_TABLE_DOG = "CREATE TABLE " + DOG_TABLE + " (" +
                    DOG_ID + "INTEGER PRIMARY KEY AUTOINCREMENT," +
                    DOG_NAME + " TEXT," +
                    DOG_WEIGHT + "INTEGER," +
                    DOG_BREED + "TEXT," +
                    DOG_PHOTO + " BLOB NOT NULL);";


            //ALLERGIES_TABLE create statement
            final String CREATE_TABLE_ALLERGIES = "CREATE TABLE " + ALLERGIES_TABLE + " (" +
                    ALLERGIE_ID + "INTEGER PRIMARY KEY AUTOINCREMENT," +
                    FOOD_ID + "INTEGER, " +
                    DOG_ID + "INTEGER);";

            db.execSQL(CREATE_TABLE_FOOD);
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

            db.execSQL(CREATE_TABLE_DOG);
//            insertDog(db, "Rex","120", "Scotish Terrier",);

            db.execSQL(CREATE_TABLE_ALLERGIES);

        }
    }
    private static void insertFood(SQLiteDatabase db, String name, int toxicity, int resourceId, int quote)throws SQLiteException {
        ContentValues foodValues = new ContentValues();
        foodValues.put("NAME", name);
        foodValues.put("TOXICITY", toxicity);
        foodValues.put("IMAGE_RESOURCE_ID", resourceId);
        foodValues.put("QUOTE", quote);
        db.insert("FOOD", null, foodValues);
    }
    private static void insertDog(SQLiteDatabase db, String name, String weight, String breed, byte[] photo) throws SQLiteException{
        ContentValues dogValues = new ContentValues();
        dogValues.put("NAME", name);
        dogValues.put("WEIGHT", weight);
        dogValues.put("BREED", breed);
        dogValues.put("PHOTO", photo);
        db.insert("DOG", null, dogValues);
    }
    private static void insertAllergie(SQLiteDatabase db, int foodId, int dogID)throws SQLiteException{
        ContentValues allergiesValues = new ContentValues();
        allergiesValues.put("FOOD_ID", foodId);
        allergiesValues.put("DOG_ID", dogID);
        db.insert("ALLERGIES", null, allergiesValues);
    }
}
