package com.example.comp2000cw1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String MENU = "menu";
    public static final String DISH_NAME = "dish_name";
    public static final String DISH_TYPE = "dish_type";
    public static final String DISH_DESCRIPTION = "dish_description";

    public static final String DISH_PRICE = "dish_price";
    public static final String DISH_ALLERGENS = "dish_allergens";
    public static final String DISH_IMAGE = "dish_image";

    public DatabaseHelper(@Nullable Context context) {
        super(context, "menu.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + MENU + "(" +
                DISH_NAME + " TEXT PRIMARY KEY," +
                DISH_TYPE + " TEXT," +
                DISH_DESCRIPTION + " TEXT," +
                DISH_PRICE + " TEXT," +
                DISH_ALLERGENS + " TEXT," +
                DISH_IMAGE + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTable = "DROP TABLE IF EXISTS " + MENU;
        db.execSQL(dropTable);
        onCreate(db);
    }

    public boolean addDish(DataModel dataModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(DISH_NAME, dataModel.getDishName());
        cv.put(DISH_TYPE, dataModel.getDishType());
        cv.put(DISH_DESCRIPTION, dataModel.getDishDescription());
        cv.put(DISH_PRICE, dataModel.getDishPrice());
        cv.put(DISH_ALLERGENS, dataModel.getDishAllergens());
        cv.put(DISH_IMAGE, dataModel.getDishImage());

        long result = db.insert(MENU, null, cv);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public List<DataModel> getAllDishes(){
        List<DataModel> dishList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + MENU;
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()) {
            do {
                String dishName = cursor.getString(0);
                String dishType = cursor.getString(1);
                String dishDescription = cursor.getString(2);
                String dishPrice = cursor.getString(3);
                String dishAllergens = cursor.getString(4);
                String dishImage = cursor.getString(5);

                DataModel dataModel = new DataModel(dishName, dishType, dishDescription, dishPrice, dishAllergens, dishImage);
                dishList.add(dataModel);
            } while (cursor.moveToNext());
        } else {
        }

        cursor.close();
        db.close();
        return dishList;
    }

}
