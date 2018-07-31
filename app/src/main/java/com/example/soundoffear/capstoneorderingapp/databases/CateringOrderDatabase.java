package com.example.soundoffear.capstoneorderingapp.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.soundoffear.capstoneorderingapp.contracts.CateringOrderContract;
import com.example.soundoffear.capstoneorderingapp.models.CateringModel;

import java.util.ArrayList;
import java.util.List;

public class CateringOrderDatabase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "catering_DB";

    public CateringOrderDatabase(Context context) {
        super (context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_CATERING_TABLE = "CREATE TABLE " + CateringOrderContract.CateringOrderEntry.CATERING_TABLE_NAME + "("
                + CateringOrderContract.CateringOrderEntry._ID + " INTEGER PRIMARY KEY, "
                + CateringOrderContract.CateringOrderEntry.CATERING_ITEM_NAME + " TEXT NOT NULL, "
                + CateringOrderContract.CateringOrderEntry.CATERING_ITEM_PRICE + " TEXT NOT NULL)";
        db.execSQL(CREATE_CATERING_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CateringOrderContract.CateringOrderEntry.CATERING_TABLE_NAME);
        onCreate(db);
    }

    public void insertCateringToDatabase(CateringModel cateringModel) {
        SQLiteDatabase cateringDB = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CateringOrderContract.CateringOrderEntry.CATERING_ITEM_NAME, cateringModel.getCateringName());
        contentValues.put(CateringOrderContract.CateringOrderEntry.CATERING_ITEM_PRICE, cateringModel.getCateringPrice());
        cateringDB.insert(CateringOrderContract.CateringOrderEntry.CATERING_TABLE_NAME, null, contentValues);
        cateringDB.close();
    }

    public List<CateringModel> getCateringData() {
        SQLiteDatabase cateringDB = getReadableDatabase();
        List<CateringModel> cateringModelList = new ArrayList<>();

        Cursor cursor = cateringDB.query(CateringOrderContract.CateringOrderEntry.CATERING_TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);

        if(cursor!=null) {
            if(cursor.moveToFirst()) {
                do {
                    String cateringName = cursor.getString(cursor.getColumnIndex(CateringOrderContract.CateringOrderEntry.CATERING_ITEM_NAME));
                    String cateringPrice = cursor.getString(cursor.getColumnIndex(CateringOrderContract.CateringOrderEntry.CATERING_ITEM_PRICE));
                    CateringModel cateringModel = new CateringModel(cateringName, cateringPrice);
                    cateringModelList.add(cateringModel);
                } while (cursor.moveToNext());
            }
        }

        return cateringModelList;
    }

    public void deleteDatabase(String tableName) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(tableName, null,null);
        db.close();
    }

}
