package com.example.soundoffear.capstoneorderingapp.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.soundoffear.capstoneorderingapp.contracts.BuildSandwichContract;
import com.example.soundoffear.capstoneorderingapp.models.FinalSandwichModel;

import java.util.ArrayList;
import java.util.List;

public class FinalSandwichDataBase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "FINAL_SANDWICH";

    public FinalSandwichDataBase (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_TABLE = "CREATE TABLE " + BuildSandwichContract.BuildSandwichEntry.SANDWICH_TABLE_NAME +
                "(" + BuildSandwichContract.BuildSandwichEntry._ID + " INTEGER PRIMARY KEY, " +
                BuildSandwichContract.BuildSandwichEntry.SANDWICH_CARRIERS + " TEXT NOT NULL, " +
                BuildSandwichContract.BuildSandwichEntry.SANDWICH_NAME + " TEXT NOT NULL, " +
                BuildSandwichContract.BuildSandwichEntry.SANDWICH_BREAD + " TEXT, " +
                BuildSandwichContract.BuildSandwichEntry.SANDWICH_VEGETABLE + " TEXT NOT NULL, " +
                BuildSandwichContract.BuildSandwichEntry.SANDWICH_SAUCE + " TEXT NOT NULL, " +
                BuildSandwichContract.BuildSandwichEntry.SANDWICH_PAID_ADDON + " TEXT NOT NULL, " +
                BuildSandwichContract.BuildSandwichEntry.SANDWICH_FINAL_PRICE + " TEXT NOT NULL)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + BuildSandwichContract.BuildSandwichEntry.SANDWICH_TABLE_NAME);
        onCreate(db);
    }

    public void insertsDataIntoBuildSandwichDatabase(FinalSandwichModel finalSandwichModel) {

        SQLiteDatabase finalSandwichDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(BuildSandwichContract.BuildSandwichEntry.SANDWICH_CARRIERS, finalSandwichModel.getCarrier());
        contentValues.put(BuildSandwichContract.BuildSandwichEntry.SANDWICH_NAME, finalSandwichModel.getSandwich());
        contentValues.put(BuildSandwichContract.BuildSandwichEntry.SANDWICH_BREAD, finalSandwichModel.getBread());
        contentValues.put(BuildSandwichContract.BuildSandwichEntry.SANDWICH_VEGETABLE, finalSandwichModel.getVegetables());
        contentValues.put(BuildSandwichContract.BuildSandwichEntry.SANDWICH_SAUCE, finalSandwichModel.getSauces());
        contentValues.put(BuildSandwichContract.BuildSandwichEntry.SANDWICH_PAID_ADDON, finalSandwichModel.getPaidAddOns());
        contentValues.put(BuildSandwichContract.BuildSandwichEntry.SANDWICH_FINAL_PRICE, finalSandwichModel.getFinalPrice());
        finalSandwichDatabase.insert(BuildSandwichContract.BuildSandwichEntry.SANDWICH_TABLE_NAME, null, contentValues);
        finalSandwichDatabase.close();

    }

    public List<FinalSandwichModel> getAllFinalSandwichData() {
        SQLiteDatabase finalSandwichDB = getReadableDatabase();
        List<FinalSandwichModel> finalSandwichModelList = new ArrayList<>();
        Cursor cursor = finalSandwichDB.query(BuildSandwichContract.BuildSandwichEntry.SANDWICH_TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);
        if(cursor != null) {
            if(cursor.moveToFirst()) {
                do {
                    String carrier = cursor.getString(cursor.getColumnIndex(BuildSandwichContract.BuildSandwichEntry.SANDWICH_CARRIERS));
                    String sandwich = cursor.getString(cursor.getColumnIndex(BuildSandwichContract.BuildSandwichEntry.SANDWICH_NAME));
                    String bread = cursor.getString(cursor.getColumnIndex(BuildSandwichContract.BuildSandwichEntry.SANDWICH_BREAD));
                    String vegetable = cursor.getString(cursor.getColumnIndex(BuildSandwichContract.BuildSandwichEntry.SANDWICH_VEGETABLE));
                    String sauce = cursor.getString(cursor.getColumnIndex(BuildSandwichContract.BuildSandwichEntry.SANDWICH_SAUCE));
                    String paidAddOns = cursor.getString(cursor.getColumnIndex(BuildSandwichContract.BuildSandwichEntry.SANDWICH_PAID_ADDON));
                    String finalPrice = cursor.getString(cursor.getColumnIndex(BuildSandwichContract.BuildSandwichEntry.SANDWICH_FINAL_PRICE));
                    FinalSandwichModel finalSandwichModel = new FinalSandwichModel(carrier,
                            sandwich,
                            bread,
                            vegetable,
                            sauce,
                            paidAddOns,
                            finalPrice);
                    finalSandwichModelList.add(finalSandwichModel);
                } while (cursor.moveToNext());
            }
        }
        finalSandwichDB.close();

        return finalSandwichModelList;
    }

    public void deleteTable(String tableName) {
        SQLiteDatabase liteDatabase = getWritableDatabase();
        liteDatabase.delete(tableName, null,null);
        liteDatabase.close();
    }
}
