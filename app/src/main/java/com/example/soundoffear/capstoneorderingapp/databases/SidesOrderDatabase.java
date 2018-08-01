package com.example.soundoffear.capstoneorderingapp.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.soundoffear.capstoneorderingapp.contracts.SidesOrderContract;
import com.example.soundoffear.capstoneorderingapp.models.SidesModel;

import java.util.ArrayList;
import java.util.List;

public class SidesOrderDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "sides_DB";
    private static final int DATABASE_VERSION = 1;

    public SidesOrderDatabase (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_SIDES_TABLE = "CREATE TABLE " + SidesOrderContract.SidesContractEntry.SIDES_TABLE_NAME
                + "(" + SidesOrderContract.SidesContractEntry._ID + " INTEGER PRIMARY KEY, "
                + SidesOrderContract.SidesContractEntry.SIDES_ITEM_NAME + " TEXT NOT NULL, "
                + SidesOrderContract.SidesContractEntry.SIDES_ITEM_NUMBER + " TEXT NOT NULL, "
                + SidesOrderContract.SidesContractEntry.SIDES_ITEM_PRICE + " TEXT NOT NULL)";
        db.execSQL(CREATE_SIDES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + SidesOrderContract.SidesContractEntry.SIDES_TABLE_NAME);
        onCreate(db);
    }

    public void insertSidesToDB(SidesModel sidesModel) {
        SQLiteDatabase sidesDB = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(SidesOrderContract.SidesContractEntry.SIDES_ITEM_NAME, sidesModel.getSideName());
        cv.put(SidesOrderContract.SidesContractEntry.SIDES_ITEM_PRICE, sidesModel.getSidePrice());
        cv.put(SidesOrderContract.SidesContractEntry.SIDES_ITEM_NUMBER, sidesModel.getSideNumber());
        sidesDB.insert(SidesOrderContract.SidesContractEntry.SIDES_TABLE_NAME, null, cv);
        sidesDB.close();
    }

    public List<SidesModel> getAllSides() {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        List<SidesModel> sidesModels = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query(SidesOrderContract.SidesContractEntry.SIDES_TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);
        if(cursor != null) {
            if(cursor.moveToFirst()) {
                do{
                    String sideName = cursor.getString(cursor.getColumnIndex(SidesOrderContract.SidesContractEntry.SIDES_ITEM_NAME));
                    String sidePrice = cursor.getString(cursor.getColumnIndex(SidesOrderContract.SidesContractEntry.SIDES_ITEM_PRICE));
                    String sideNumber = cursor.getString(cursor.getColumnIndex(SidesOrderContract.SidesContractEntry.SIDES_ITEM_NUMBER));
                    SidesModel sidesModel = new SidesModel(sideName, sidePrice, sideNumber);
                    sidesModels.add(sidesModel);
                } while (cursor.moveToNext());
            }
        }

        return sidesModels;
    }

    public void deleteSidesTable(String tableName) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.delete(tableName, null, null);
        sqLiteDatabase.close();
    }
}
