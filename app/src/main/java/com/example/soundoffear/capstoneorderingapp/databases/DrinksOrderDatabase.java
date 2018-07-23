package com.example.soundoffear.capstoneorderingapp.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.soundoffear.capstoneorderingapp.contracts.DrinksOrderContract;
import com.example.soundoffear.capstoneorderingapp.models.DrinksModel;

import java.util.ArrayList;
import java.util.List;

public class DrinksOrderDatabase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Drinks_database";

    public DrinksOrderDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_TABLE = "CREATE TABLE " + DrinksOrderContract.DrinksOrderEntry.DRINKS_ORDER_TABLE_NAME + " (" +
                DrinksOrderContract.DrinksOrderEntry._ID + " INTEGER PRIMARY KEY, " +
                DrinksOrderContract.DrinksOrderEntry.DRINKS_NAME + " TEXT NOT NULL, " +
                DrinksOrderContract.DrinksOrderEntry.DRINKS_NUMBER + " TEXT NOT NULL, " +
                DrinksOrderContract.DrinksOrderEntry.DRINKS_PRICE + " TEXT NOT NULL )";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DrinksOrderContract.DrinksOrderEntry.DRINKS_ORDER_TABLE_NAME);
        onCreate(db);
    }

    public void insertDrinksDatatoDatabase(DrinksModel drinksModel) {
        SQLiteDatabase drinksDB = getWritableDatabase();
        ContentValues drinksCV = new ContentValues();
        drinksCV.put(DrinksOrderContract.DrinksOrderEntry.DRINKS_NAME, drinksModel.getName());
        drinksCV.put(DrinksOrderContract.DrinksOrderEntry.DRINKS_NUMBER, drinksModel.getValue());
        drinksCV.put(DrinksOrderContract.DrinksOrderEntry.DRINKS_PRICE, drinksModel.getPrice());
        drinksDB.insert(DrinksOrderContract.DrinksOrderEntry.DRINKS_ORDER_TABLE_NAME, null, drinksCV);
        drinksDB.close();
    }

    public List<DrinksModel> getAllDrinksData() {
        SQLiteDatabase drinksDB = getReadableDatabase();
        List<DrinksModel> drinksModelList = new ArrayList<>();
        Cursor cursor = drinksDB.query(DrinksOrderContract.DrinksOrderEntry.DRINKS_ORDER_TABLE_NAME, null,null,null,null,null,null);
        if(cursor != null) {
            if(cursor.moveToFirst()) {
                do {
                    String drinkName = cursor.getString(cursor.getColumnIndex(DrinksOrderContract.DrinksOrderEntry.DRINKS_NAME));
                    String drinkValue = cursor.getString(cursor.getColumnIndex(DrinksOrderContract.DrinksOrderEntry.DRINKS_NUMBER));
                    String drinkPrice = cursor.getString(cursor.getColumnIndex(DrinksOrderContract.DrinksOrderEntry.DRINKS_PRICE));
                    DrinksModel drinksModel = new DrinksModel(0, drinkName, drinkPrice, drinkValue);
                    drinksModelList.add(drinksModel);
                } while (cursor.moveToNext());
            }
        }

        return drinksModelList;
    }

    public void deleteDrinkDatabase(String tableName) {
        SQLiteDatabase database = getWritableDatabase();
        database.delete(DrinksOrderContract.DrinksOrderEntry.DRINKS_ORDER_TABLE_NAME, null,null);
        database.close();
    }

}
