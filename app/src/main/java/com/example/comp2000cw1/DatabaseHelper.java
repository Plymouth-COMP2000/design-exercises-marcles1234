package com.example.comp2000cw1;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    //MENU VARIABLES
    public static final String MENU = "menu";
    public static final String DISH_NAME = "dish_name";
    public static final String DISH_TYPE = "dish_type";
    public static final String DISH_DESCRIPTION = "dish_description";

    public static final String DISH_PRICE = "dish_price";
    public static final String DISH_ALLERGENS = "dish_allergens";
    public static final String DISH_IMAGE = "dish_image";

    //RESERVATION VARIABLES
    public static final String RESERVATIONS = "reservations";
    public static final String RESERVATION_ID = "reservation_id";
    public static final String RESERVATION_NAME = "reservation_name";
    public static final String RESERVATION_DATE = "reservation_date";
    public static final String RESERVATION_TIME = "reservation_time";
    public static final String RESERVATION_GUESTS = "reservation_guests";

    //CONNECT TO DATABASE
    public DatabaseHelper(@Nullable Context context) {
        super(context, "DATABASE_NAME.db", null, 1);
    }


    //CREATE TABLES IF THEY DON'T EXIST
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableMenu = "CREATE TABLE " + MENU + "(" +
                DISH_NAME + " TEXT PRIMARY KEY," +
                DISH_TYPE + " TEXT," +
                DISH_DESCRIPTION + " TEXT," +
                DISH_PRICE + " TEXT," +
                DISH_ALLERGENS + " TEXT," +
                DISH_IMAGE + " TEXT)";
        db.execSQL(createTableMenu);

        String createTableReservations = "CREATE TABLE " + RESERVATIONS + "(" +
                RESERVATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                RESERVATION_NAME + " TEXT," +
                RESERVATION_DATE + " TEXT," +
                RESERVATION_TIME + " TEXT," +
                RESERVATION_GUESTS + " TEXT)";
        db.execSQL(createTableReservations);
    }

    //UPDATE LOGIC
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MENU);
        db.execSQL("DROP TABLE IF EXISTS " + RESERVATIONS);
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

    public long addReservation(DataModelReservations dataModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(RESERVATION_NAME, dataModel.getReservationName());
        cv.put(RESERVATION_DATE, dataModel.getReservationDate());
        cv.put(RESERVATION_TIME, dataModel.getReservationTime());
        cv.put(RESERVATION_GUESTS, dataModel.getReservationGuests());

        long result = db.insert(RESERVATIONS, null, cv);
        db.close();
        return result;
    }

    public List<DataModelReservations> getAllReservations(String name){
        List<DataModelReservations> reservationList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + RESERVATIONS + " WHERE " + RESERVATION_NAME + " = ? ";
        Cursor cursor = db.rawQuery(query, new String[]{name});

        if(cursor.moveToFirst()) {
            do {
                int reservationId = cursor.getInt(0);
                String reservationName = cursor.getString(1);
                String reservationDate = cursor.getString(2);
                String reservationTime = cursor.getString(3);
                String reservationGuests = cursor.getString(4);

                DataModelReservations dataModel = new DataModelReservations(reservationId, reservationName, reservationDate, reservationTime, reservationGuests);
                reservationList.add(dataModel);
            } while (cursor.moveToNext());
        } else {
        }

        cursor.close();
        db.close();
        return reservationList;
    }

    public List<DataModelReservations> getAllReservationsByDate(String date){
        List<DataModelReservations> reservationList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM "
                + RESERVATIONS
                + " WHERE "
                + RESERVATION_DATE
                + " = ? ";
        Cursor cursor = db.rawQuery(query, new String[]{date});


        if(cursor.moveToFirst()) {
            do {
                int reservationId = cursor.getInt(0);
                String reservationName = cursor.getString(1);
                String reservationDate = cursor.getString(2);
                String reservationTime = cursor.getString(3);
                String reservationGuests = cursor.getString(4);

                DataModelReservations dataModel = new DataModelReservations(reservationId, reservationName, reservationDate, reservationTime, reservationGuests);
                reservationList.add(dataModel);
            } while (cursor.moveToNext());
        } else {
        }

        cursor.close();
        db.close();
        return reservationList;
    }

    public DataModel getDishByName(String dishName) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + MENU + " WHERE " + DISH_NAME + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{dishName});
        DataModel dataModel = null;
        if (cursor.moveToFirst()) {
            String dish_Name = cursor.getString(0);
            String dish_Type = cursor.getString(1);
            String dish_Description = cursor.getString(2);
            String dish_Price = cursor.getString(3);
            String dish_Allergens = cursor.getString(4);
            String dish_Image = cursor.getString(5);
            dataModel = new DataModel(dish_Name, dish_Type, dish_Description, dish_Price, dish_Allergens, dish_Image);
        }
        cursor.close();
        db.close();
        return dataModel;
    }

    public DataModelReservations getReservationByID(int ID) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + RESERVATIONS + " WHERE " + RESERVATION_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(ID)});
        DataModelReservations dataModel = null;
        if (cursor.moveToFirst()) {
            int reservationId = cursor.getInt(0);
            String reservationName = "";
            String reservationDate = cursor.getString(2);
            String reservationTime = cursor.getString(3);
            String reservationGuests = cursor.getString(4);
            dataModel = new DataModelReservations(reservationId, reservationName, reservationDate, reservationTime, reservationGuests);
        }
        cursor.close();
        db.close();
        return dataModel;
    }

    public List<String> getDishesByType(String dishName) {
        List<String> dishList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + DISH_NAME + " FROM " + MENU + " WHERE " + DISH_TYPE + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{dishName});
        if (cursor.moveToFirst()) {
            do {
                String dish_Name = cursor.getString(0);
                dishList.add(dish_Name);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return dishList;
    }

    public void updateDish(String oldName, DataModel newDish) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + MENU + " SET "
                + "dish_name = ?, "
                + "dish_type = ?, "
                + "dish_description = ?, "
                + "dish_price = ?, "
                + "dish_allergens = ?, "
                + "dish_image = ? "
                + "WHERE dish_name = ?";

        db.execSQL(query, new Object[]{
                newDish.getDishName(),
                newDish.getDishType(),
                newDish.getDishDescription(),
                newDish.getDishPrice(),
                newDish.getDishAllergens(),
                newDish.getDishImage(),
                oldName
        });
    }

    public void updateReservation(int id, DataModelReservations newRes) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + RESERVATIONS + " SET "
                + "reservation_date = ?, "
                + "reservation_time = ?, "
                + "reservation_guests = ? "
                + "WHERE RESERVATION_ID = ?";

        db.execSQL(query, new Object[]{
                newRes.getReservationDate(),
                newRes.getReservationTime(),
                newRes.getReservationGuests(),
                id
        });
    }

    public void deleteDish(String dishName) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + MENU + " WHERE " + DISH_NAME + " = ?";
        db.execSQL(query, new String[]{dishName});
    }

    public void deleteReservation(int reservationId) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + RESERVATIONS + " WHERE " + RESERVATION_ID + " = ?";
        db.execSQL(query, new String[]{String.valueOf(reservationId)});
    }
}
